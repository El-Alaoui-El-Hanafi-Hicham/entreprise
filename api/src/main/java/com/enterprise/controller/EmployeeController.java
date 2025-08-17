package com.enterprise.controller;

import com.enterprise.config.JwtService;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Status;
import com.enterprise.service.EmailService;
import com.enterprise.service.EmployeeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("Page Number: " + pageNumber + ", Page Size: " + pageSize);
        return this.employeeeService.allEmployees(pageNumber, pageSize);
    }
   @PostMapping("/employee")
    public ResponseEntity<HashMap<String,String>> addEmployee(@RequestBody Employee employee) {

        return this.employeeeService.AddEmployee(employee);
    }
    @PostMapping("/bDEL")
    public ResponseEntity<HashMap<String,String>> deleteEmployees(@RequestBody Long[] employees_id)  {
        System.out.println("this is the list of employees to delete: "+employees_id);
        return this.employeeeService.deleteEmployees(employees_id);
    }
    @GetMapping("/setDepartement")
    public ResponseEntity<String> setDepartement(@RequestParam(name = "id") long userid,@RequestParam(name = "dep_id") long id){
    return this.employeeeService.setDepartement(userid,id);
    };
    @GetMapping("/me")
    public Employee me(@RequestHeader (name="Authorization") String token){
        Employee emp = this.employeeeService.getCurrentUser(token);
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
       @PostMapping("/bulk")
       public ResponseEntity<Map<String,String>> addEmployeesBulk(@RequestParam("file") MultipartFile file) {
           System.out.println("this is the file: "+file);
           return this.employeeeService.bulk(file);
       }
}
