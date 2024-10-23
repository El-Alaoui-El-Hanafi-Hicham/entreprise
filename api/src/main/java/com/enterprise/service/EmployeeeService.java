package com.enterprise.service;

import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Status;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeeService {
    EmployeeRepository employeeRepository;
    EmailService emailService;
    DepartmentRepository departmentRepository;
    JavaMailSender mailSender;
    @Autowired
    public EmployeeeService(JavaMailSender mailSender,EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,EmailService emailService) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository = departmentRepository;
        this.emailService = emailService;
        this.mailSender= mailSender;
    }

    public ResponseEntity<String> AddEmployee(Employee employee) throws MessagingException {
        String password =employee.getPassword();
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
            return new ResponseEntity<>(
                    "Employee Added Succesfully",
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    "Something Wrong Happened" ,
                    HttpStatus.BAD_REQUEST);
        }        }else {
            return new ResponseEntity<>(
                    "User Already Exist" ,
                    HttpStatus.BAD_REQUEST);
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
        Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, pageSize);


        Page<Employee> allEmployees=   employeeRepository.findAll(firstPageWithTwoElements);// Collect the stream into a list



        return ResponseEntity.ok().body(allEmployees);
    }
    public Employee setStatus(Employee employee, Status status){
        employee.setStatus(status);

Employee employee1 = employeeRepository.save(employee);
        return employee1;
    }


}
