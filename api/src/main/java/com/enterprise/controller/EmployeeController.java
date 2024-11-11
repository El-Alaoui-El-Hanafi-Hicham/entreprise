package com.enterprise.controller;

import com.enterprise.config.JwtService;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Status;
import com.enterprise.service.EmailService;
import com.enterprise.service.EmployeeeService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/employees")
public class EmployeeController {
    private final  EmailService emailService;
    private final EmployeeeService employeeeService;
    private final JwtService jwtService;
@Autowired
    public EmployeeController(EmailService emailService, EmployeeeService employeeeService,JwtService jwtService) {
    this.emailService = emailService;
    this.employeeeService = employeeeService;
    this.jwtService= jwtService;
    }
    @GetMapping("")
    public ResponseEntity<Page<Employee>> employees(@RequestParam(required=true) int pageNumber, @RequestParam(required=true) int pageSize){

        return this.employeeeService.allEmployees(pageNumber, pageSize);
    }
@PostMapping("/employee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) throws MessagingException {

        return this.employeeeService.AddEmployee(employee);
    }
    @GetMapping("/setDepartement")
    public ResponseEntity<String> setDepartement(@RequestParam(name = "id") long userid,@RequestParam(name = "dep_id") long id){
    return this.employeeeService.setDepartement(userid,id);
    };
    @GetMapping("/me")
    public Employee me(@RequestHeader (name="Authorization") String token){
        Employee emp = this.employeeeService.getCurrentUser(token);
//        this.emp.getUsername
        return emp;
    };
    @MessageMapping("/user.setOnline")
    @SendTo("/user/topic")
    public Employee setOnlineUser(@Payload Employee employee){
employeeeService.setStatus(employee,Status.ONLINE);
        return employee;
    };
    @MessageMapping("/user.setOffline")
    @SendTo("/user/topic")
    public Employee setDisconnectUser(@Payload Employee employee){
        employeeeService.setStatus(employee,Status.OFFLINE);
        return employee;
    };
}
