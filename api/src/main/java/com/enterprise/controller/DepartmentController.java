package com.enterprise.controller;

import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.service.DepartmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/department")
public class DepartmentController {

    DepartmentService departmentService;


    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("")
    public ResponseEntity<Object> AddDepartement(@RequestBody Department department) {

        return this.departmentService.AddDepartement(department);
    }

    @GetMapping("")
    public ResponseEntity<List<Department>> getDepartments() {


        return this.departmentService.getDepartements();
    }
    @PutMapping("")
    public ResponseEntity<Map<String, String>>  editDepartment(@RequestParam(name = "dep_id") Long id ,@RequestBody Department department) {
        System.out.println(id);
        System.out.println(department);
        return this.departmentService.editDepartment(id,department);
    }
    @GetMapping("/department/employees")
    public ResponseEntity<List<Employee>> getEmployees(long id) {
        return this.departmentService.getDepartment(id);
    }

    @GetMapping("/employees/add")
    public ResponseEntity<Map<String, String>> addEmployee(@RequestParam(name = "id") long userid, @RequestParam(name = "dep_id") long id) {
        return this.departmentService.addEmployee(userid, id);
    }
    @GetMapping("/employees/remove")
    public ResponseEntity<Map<String, String>> removeEmployee(@RequestParam(name = "id") long userid, @RequestParam(name = "dep_id") long id) {
        return this.departmentService.removeEmployee(userid, id);
    }


    @GetMapping("/removeManager")
    public ResponseEntity<Map<String, String>> removeManager(@RequestParam(name = "id") long userid) {
        return this.departmentService.removeManager(userid);
    }
    @GetMapping("/setManager")
    public ResponseEntity<Map<String, String>> setManager(@RequestParam(name = "id") long userid, @RequestParam(name = "dep_id") long id) {
        return this.departmentService.setManager(userid, id);
    }
    @PostMapping("/bulk")
    public ResponseEntity<Map<String, String>> bulk(@RequestParam("file") MultipartFile file){
        return this.departmentService.bulk(file);
    }
    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> deleteDepartment(@RequestParam(name = "id") long id) {
        return this.departmentService.deleteDepartment(id);
    }

    ;
}
