package com.enterprise.service;

import com.enterprise.batch.DepartmentJob;
import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartementService {
    EmployeeRepository employeeRepository;
    JobLauncher jobLauncher;
    DepartmentRepository departmentRepository;
    Job jobDe;

    @Autowired
    public DepartementService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, JobLauncher jobLauncher, Job departmentJob) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobLauncher = jobLauncher;
        this.jobDe = departmentJob;
    }
    @ResponseBody

    public ResponseEntity<List<Department>> getDepartements() {
        return new ResponseEntity<>(
                this.departmentRepository.findAll(),
                HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Object> AddDepartement(Department department) {
        Department s = this.departmentRepository.save(department);
        HashMap response = new HashMap<String, String>();
        if (s.equals(department)) {
            response.put("message", "Department Added Succesfully");
            response.put("Status", true);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK);
        } else {
            response.put("message", "Something Wrong Happened");
            response.put("Status", false);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST);
        }
    }
@ResponseBody
    public ResponseEntity<List<Employee>> getDepartment(long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        } else {
            return ResponseEntity.ok(department.get().getEmployees());
        }
    }
    @Transactional
    public ResponseEntity<Map<String, String>> addEmployee(long user_id, long id) {
        Map<String, String> response = new HashMap<>();

        Optional<Department> department = departmentRepository.findById(id);
        Optional<Employee> employee = employeeRepository.findById(user_id);
        if (employee.isPresent()) {


            if (department.isEmpty()) {
                response.put("Status", "false");
                response.put("message", "Department Not Found");
                return ResponseEntity.badRequest().body(response);
            } else {
                if (department.get().addEmployee(employee.get())) {
                    this.departmentRepository.save(department.get());
                    response.put("Status", "true");
                    response.put("message", "Employee Added Successfully");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("Status", "false");
                    response.put("message", "Employee is already in this departement");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        } else {
            response.put("Status", "false");
            response.put("message", "Employee not found");
            return ResponseEntity.badRequest().body(response);

        }
    }
    @Transactional
    public ResponseEntity<Map<String, String>> removeEmployee(long userid, long id) {
        Map<String, String> response = new HashMap<>();

        Optional<Department> department = departmentRepository.findById(id);
        Optional<Employee> employee = employeeRepository.findById(userid);
        if (employee.isPresent()) {


            if (department.isEmpty()) {
                response.put("Status", "false");
                response.put("message", "Department Not Found");
                return ResponseEntity.badRequest().body(response);
            } else {
                if (department.get().removeEmployee(employee.get())) {
                    this.departmentRepository.save(department.get());
                    response.put("Status", "true");
                    response.put("message", "Employee removed Successfully");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("Status", "false");
                    response.put("message", "Employee is already not part of this departement");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        } else {
            response.put("Status", "false");
            response.put("message", "Employee not found");
            return ResponseEntity.badRequest().body(response);

        }
    }
    @Transactional
    public ResponseEntity<Map<String, String>> setManager(long userId, long id) {
        Map<String, String> response = new HashMap<>();

        Optional<Department> department = this.departmentRepository.findById(id);
        if (department.isEmpty()) {
            response.put("Status", "false");
            response.put("message", "Department not found");
            return ResponseEntity.badRequest().body(response);
        } else {
            Optional<Employee> employee = this.employeeRepository.findById(userId);
            if (employee.isEmpty()) {
                response.put("Status", "false");
                response.put("message", "Employee not found");
                return ResponseEntity.badRequest().body(response);
            } else {
                if (department.get().getManager() != null && department.get().getManager().equals(employee.get())) {
                    response.put("Status", "false");
                    response.put("message", employee.get().getFirst_name() + " is already the manager " + department.get().getDepartment_name() + " departement .");
                    return ResponseEntity.ok(response);
                } else {

                    department.get().setManager(employee.get());
                    this.departmentRepository.save(department.get());
                    response.put("Status", "true");
                    response.put("message", employee.get().getFirst_name() + " is the new manager for " + department.get().getDepartment_name() + " departement");
                    return ResponseEntity.ok().body(response);
                }

            }
        }
    }
    @Transactional
    public ResponseEntity<Map<String, String>> removeManager(long id) {
        Map<String, String> response = new HashMap<>();

        Optional<Department> department = this.departmentRepository.findById(id);
        if (department.isEmpty()) {
            response.put("Status", "false");
            response.put("message", "Department not found");
            return ResponseEntity.badRequest().body(response);
        } else {

            if (department.get().getManager() == null) {
                response.put("Status", "false");
                response.put("message", department.get().getDepartment_name() + " departement doesn't have a manager yet.");
                return ResponseEntity.ok(response);
            } else {
                department.get().removeManager();
                this.departmentRepository.save(department.get());
                response.put("Status", "true");
                response.put("message", department.get().getDepartment_name() + " departement's manager has been removed.");
                return ResponseEntity.ok().body(response);
            }

        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> editDepartment(Long id, Department department) {

        Optional<Department> old_department = this.departmentRepository.findById(id);
        Map<String, String> response = new HashMap<>();

        if (old_department.isEmpty()) {
            response.put("status", "false");
            response.put("message", "Department not found");
            return ResponseEntity.badRequest().body(response);
        }
        old_department.get().setDepartment_name(department.getDepartment_name());
        this.departmentRepository.save(old_department.get());
        response.put("status", "true");
        response.put("message", "Department Updated Successfully");
        return ResponseEntity.ok(response);
    }
@Transactional
    public ResponseEntity<Map<String, String>> deleteDepartment(long id) {
        Map<String, String> response = new HashMap<>();
        Optional<Department> department = this.departmentRepository.findById(id);
        if (department.isEmpty()) {
            response.put("Status", "false");
            response.put("message", "Department not found");
            return ResponseEntity.badRequest().body(response);
        }
    for (Employee employee : department.get().getEmployees()) {
        employee.setDepartment(null);
        employeeRepository.save(employee);
    }
        this.departmentRepository.delete(department.get());
        response.put("Status", "true");
        response.put("message", "Department Deleted Successfully");
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Map<String, String>> bulk(MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        // Define the target directory where files will be saved
        String uploadDir = "uploads/";
        if (file.isEmpty()) {
            response.put("Status", "false");
            response.put("message", "File is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            String filePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            File tempFile = new File(filePath);
            file.transferTo(tempFile);

            // Launch the batch job with the file path as a job parameter
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", filePath)
                    .addString("run.id", String.valueOf(System.currentTimeMillis())) // Makes each run unique
                    .toJobParameters();
            jobLauncher.run(jobDe, jobParameters);


            response.put("Status", "true");
            response.put("message", "Department Deleted Successfully");
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Status", "true");
            response.put("message", "Department Deleted Successfully");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        }
    }
}
