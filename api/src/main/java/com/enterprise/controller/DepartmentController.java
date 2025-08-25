package com.enterprise.controller;

import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.service.DepartmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Page<Department> getDepartments(@RequestParam(name= "pageNumber", defaultValue = "0",required = false) int pageNumber,@RequestParam(name="pageSize",defaultValue = "10",required = false) int pageSize, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name="employee_ids",required = false) String emp_id) {

        if(emp_id==null || emp_id.isEmpty()){
            return this.departmentService.getDepartments(pageNumber, pageSize, keyword, null);

        }else{
            List<Long> emps_ids = Arrays.stream(emp_id.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            return this.departmentService.getDepartments(pageNumber, pageSize, keyword, emps_ids);

        }
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
