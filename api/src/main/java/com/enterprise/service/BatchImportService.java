package com.enterprise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BatchImportService {

    private final JobLauncher jobLauncher;
    private final Job departmentJob;
    private final Job employeeJob;

    public BatchImportService(
            @Qualifier("jobDe") Job departmentJob,
            @Qualifier("EmployeeJob") Job employeeJob,JobLauncher jobLauncher) {
        this.departmentJob = departmentJob;
        this.employeeJob = employeeJob;
        this.jobLauncher=jobLauncher;
    }
    public Map<String, Object> processDepartmentsCsv(String filePath) {
        return processFile(filePath, departmentJob, "departments");
    }

    public Map<String, Object> processEmployeesCsv(String filePath) {
        return processFile(filePath, employeeJob, "employees");
    }

    private Map<String, Object> processFile(String filePath, Job job, String type) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Validate file exists
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                result.put("success", false);
                result.put("error", "File not found: " + filePath);
                return result;
            }

            // Create job parameters
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", filePath)
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            // Execute job
            log.info("Starting batch job for {} with file: {}", type, filePath);
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            // Wait for completion
            while (jobExecution.isRunning()) {
                Thread.sleep(100);
            }

            // Prepare result
            result.put("jobId", jobExecution.getId());
            result.put("status", jobExecution.getStatus().toString());
            result.put("type", type);

            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                result.put("success", true);
                result.put("message", "File processed successfully");
                
                // Get write count from step execution
                for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
                    result.put("recordsProcessed", stepExecution.getWriteCount());
                    result.put("recordsRead", stepExecution.getReadCount());
                    break; // Assuming single step
                }
                
                log.info("Batch job completed successfully for {}", type);
            } else {
                result.put("success", false);
                result.put("message", "File processing failed");
                
                // Collect error messages
                if (!jobExecution.getAllFailureExceptions().isEmpty()) {
                    result.put("errors", jobExecution.getAllFailureExceptions().stream()
                            .map(Throwable::getMessage)
                            .toList());
                }
                
                log.error("Batch job failed for {} with status: {}", type, jobExecution.getStatus());
            }

        } catch (JobExecutionAlreadyRunningException e) {
            result.put("success", false);
            result.put("error", "Job is already running for this type");
            log.warn("Job already running for {}", type);
        } catch (JobInstanceAlreadyCompleteException e) {
            result.put("success", false);
            result.put("error", "Job instance already completed");
            log.warn("Job instance already completed for {}", type);
        } catch (JobParametersInvalidException e) {
            result.put("success", false);
            result.put("error", "Invalid job parameters: " + e.getMessage());
            log.error("Invalid job parameters for {}: {}", type, e.getMessage());
        } catch (JobRestartException e) {
            result.put("success", false);
            result.put("error", "Job restart exception: " + e.getMessage());
            log.error("Job restart exception for {}: {}", type, e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            result.put("success", false);
            result.put("error", "Job was interrupted");
            log.error("Job was interrupted for {}", type);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "Unexpected error: " + e.getMessage());
            log.error("Unexpected error for {}: {}", type, e.getMessage(), e);
        }

        return result;
    }

    /**
     * Validate CSV file format
     */
    public boolean validateCsvFormat(String filePath, String expectedType) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                return false;
            }

            String firstLine = Files.readAllLines(path).get(0);
            
            switch (expectedType.toLowerCase()) {
                case "departments":
                    return firstLine.equals("departmentName");
                case "employees":
                    return firstLine.equals("firstName,lastName,email,job_title,phone_number,hire_date");
                default:
                    return false;
            }
        } catch (IOException e) {
            log.error("Error validating CSV format: {}", e.getMessage());
            return false;
        }
    }
}
