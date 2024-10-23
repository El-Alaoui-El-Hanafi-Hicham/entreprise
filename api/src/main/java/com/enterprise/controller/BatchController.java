//package com.enterprise.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class BatchController {
//    private final JobLauncher jobLauncher;
//    private final Job job;
//    @GetMapping("/startJob")
//    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        Map<String, JobParameter> params = new HashMap<>();
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        JobExecution jobExecution = jobLauncher.run(job,jobParameters);
//        while (jobExecution.isRunning()){
//            System.out.println("-----------------------------");
//            System.out.println(jobExecution.getStatus());
//            System.out.println("-----------------------------");
//        }
//        return jobExecution.getStatus();
//    }
//}
