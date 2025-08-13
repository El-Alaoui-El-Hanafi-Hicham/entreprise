package com.enterprise.service;

import com.enterprise.config.EmployeesBatchConfig;
import com.enterprise.config.JwtService;
import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Status;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class EmployeeeService {
    private final JobRepository jobRepository;
    EmployeeRepository employeeRepository;
    EmailService emailService;
    DepartmentRepository departmentRepository;
    JobLauncher jobLauncher;
    EmployeesBatchConfig employeesBatchConfig;
    JavaMailSender mailSender;
    private final JwtService jwtService;
    @Autowired
    @Qualifier("EmployeeJob")
    private Job employeeJob;
    @Autowired
    public EmployeeeService(JavaMailSender mailSender, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmailService emailService, JwtService jwtService, EmployeesBatchConfig employeesBatchConfig, JobRepository jobRepository,JobLauncher jobLauncher
    ) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository = departmentRepository;
        this.emailService = emailService;
        this.mailSender= mailSender;
        this.jwtService = jwtService;
        this.employeesBatchConfig=employeesBatchConfig;
        this.jobRepository = jobRepository;
        this.jobLauncher=jobLauncher;
    }

    public ResponseEntity<HashMap<String,String>> AddEmployee(Employee employee) throws MessagingException {
        String password =employee.getPassword();
        HashMap<String, String> response = new HashMap<>();

        employee.setPassword("");
        Optional<Employee> employee1=this.employeeRepository.findByEmail(employee.getEmail());
        if(employee1.isEmpty()){

        Employee s = this.employeeRepository.save(employee);
        if(s.equals(employee)){
            if(password== null){
                MimeMessage message = mailSender.createMimeMessage();
                String recipient = employee.getUsername();
                System.out.println(s.getId());
                System.out.println(Long.toString(s.getId()).toString());
                System.out.println(Base64.getEncoder().encodeToString(Long.toString(s.getId()).toString().getBytes()));
                String id = Base64.getEncoder().encodeToString(Long.toString(s.getId()).toString().getBytes());
                String subject = "Hello, "+employee.getFirst_name() ;
                String template = "Hello, ${firstName}!\n\n"
                        + "<p>This is a message just for you, "+employee.getFirst_name() +" "+employee.getLast_name()+"</p>\n"
                        + "<p>This is the Link to reset the password </p>\n"
                        +"<a href='http://localhost:4200/reset-password/"+id+"'>Click Here!</a>\n"
                        + "<p>We hope you're having a great day!</p>\n\n"
                        + "<p>Best regards,</p>\n"
                        + "<p>The Spring Boot Team</p>"
                        ;

                Map<String, Object> variables = new HashMap<>();
                variables.put("firstName", employee.getFirst_name());
                variables.put("lastName", employee.getLast_name());

                emailService.sendEmail(recipient, subject, template);
            }
            response.put("message", "Employee Added Successfully");
            response.put("status", HttpStatus.OK.toString());
            return ResponseEntity.ok().body(response);
        }else{
            response.put("message", "Something Wrong Happened");
            response.put("status", HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.badRequest().body(response);
        }        }else {
            response.put("message", "Employee Already Exists");
            response.put("status", HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.badRequest().body(response);

        }

    }
    public ResponseEntity<String> setDepartement(long userId,long id){
Optional<Employee> employee = employeeRepository.findById(userId);

        if(employee.isPresent()){
if(employee.get().getDepartment()!=null){
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("User Has A Department Already");

}else{
    Optional<Department> departement = this.departmentRepository.findById(id);
    if(departement.isPresent()){
        employee.get().setDepartment(departement.get());
        employeeRepository.save(employee.get());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Employee "+employee.get().getFirst_name() +" " +employee.get().getLast_name() +" is now in the "+departement.get().getDepartment_name() + " departement");

    }else{
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Department Not Found");
    }
}
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Employee not found");

        }
    };


    public ResponseEntity<Page<Employee>> allEmployees(int pageNumber, int pageSize) {
        Pageable firstPageWithTwoElements = PageRequest.of(pageSize==0? 0:pageNumber,pageSize==0? Integer.MAX_VALUE:pageSize);
        System.out.println("the page size is ====> "+firstPageWithTwoElements.getPageSize());
        System.out.println("the page size is ====> "+firstPageWithTwoElements.getPageNumber());

        Page<Employee> allEmployees=   employeeRepository.findAll(firstPageWithTwoElements);// Collect the stream into a list



        return ResponseEntity.ok().body(allEmployees);
    }
    public Employee setStatus(Employee employee, Status status){
        employee.setStatus(status);

Employee employee1 = employeeRepository.save(employee);
        return employee1;
    }
    public Employee getCurrentUser(String token) {
        String email = this.jwtService.extractEmail(token.substring(7));
    Optional<Employee> employee =this.employeeRepository.findByEmail(email);
    return  employee.get();
    }

    @Transactional
    public ResponseEntity<HashMap<String,String>> deleteEmployees(Long[] employees) {
        HashMap<String, String> response = new HashMap<>();
        for (Long employeeId : employees) {
            Optional<Employee> employee = this.employeeRepository.findById(employeeId);
            if (employee.isPresent()) {
                if(employee.get().getDepartment()!=null){

                    Optional<Department> department= this.departmentRepository.findById(employee.get().getDepartment().getId());
                    if(department.isPresent()){
                        department.get().getEmployees().remove(employee.get());
                        this.departmentRepository.save(department.get());
                    }
                }
                this.employeeRepository.deleteById(employee.get().getId());
            } else {
                response.put("message", "Employee Not Found");
                response.put("status", HttpStatus.NOT_FOUND.toString());
                return new ResponseEntity<>(
                        response,
                        HttpStatus.NOT_FOUND);

            }
        };
        response.put("message", "Employees deleted successfully");
        response.put("status", HttpStatus.OK.toString());
        return ResponseEntity.ok().body(response);

    }

    public ResponseEntity<Map<String, String>> bulk(MultipartFile file) {
            String extention = FilenameUtils.getExtension(file.getOriginalFilename());
            Map<String, String> response = new HashMap<>();

            // Define the target directory where files will be saved
            String uploadDir = "uploads/";
            if (file.isEmpty()) {
                response.put("Status", "false");
                response.put("message", "File is empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else if(!extention.isEmpty() &&!extention.equalsIgnoreCase("csv")){
                response.put("Status", "false");
                response.put("message", "File's extention is not correct, please upload a csv file");
                return ResponseEntity.badRequest().body(response);
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
                this.jobLauncher.run(employeeJob, jobParameters);


                response.put("Status", "true");
                response.put("message", "Employees added Successfully");
                return ResponseEntity.ok().body(response);
            } catch (Exception e) {
                e.printStackTrace();
                response.put("Status", "false");
                response.put("message", "An error occurred while processing the file: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
}
