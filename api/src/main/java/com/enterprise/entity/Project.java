package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String project_name;
    @Column
    private String description;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_date;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;

    @ManyToMany(mappedBy = "projectsList")
    @JsonIgnore
    private List<Employee> employeesList = new ArrayList<>();

    @ManyToMany(mappedBy = "projectsList")
    @JsonIgnore
    private List<Department> departmentsList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="manager_id")
    @JsonIgnoreProperties({"projectsList", "tasksList"}) // avoids recursion but keeps manager visible
    private Employee manager;
    @ManyToOne
    @JoinColumn(name="owner_id")
    @JsonIgnoreProperties({"projectsList", "tasksList"}) // avoids recursion but keeps manager visible
    private Employee owner;
    @Column(nullable = true)
    private String status;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<Task> tasksList = new ArrayList<>();


    public Project(String project_name, String description, LocalDate start_date, LocalDate end_date) {
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Project(String project_name, String description, LocalDate start_date, LocalDate end_date,Employee Manager,Employee Owner,String status) {
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.owner=Owner;
        this.manager = Manager;
        this.status= status;
    }

    public boolean addDepartment(Department department){
        if(departmentsList==null){
            departmentsList = new ArrayList<>();
        }
        boolean isPresent = this.departmentsList.stream().anyMatch(dept -> dept.equals(department));
        if(isPresent){
            return false;
        }
        departmentsList.add(department);
        // Update the other side of the relationship
        if(!department.getProjectsList().contains(this)) {
            department.getProjectsList().add(this);
        }
        return true;
    }
    
    public boolean removeDepartment(Department department){
        if(departmentsList == null || departmentsList.isEmpty()){
            return false;
        }
        boolean isPresent = this.departmentsList.stream().anyMatch(dept -> dept.equals(department));
        if(!isPresent){
            return false;
        }
        departmentsList.remove(department);
        // Update the other side of the relationship
        department.getProjectsList().remove(this);
        return true;
    }
    
    public boolean addEmployee(Employee employee){
        if(employeesList==null){
            employeesList = new ArrayList<>();
        }
        boolean isPresent = this.employeesList.stream().anyMatch(emp -> emp.equals(employee));
        if(isPresent){
            return false;
        }
        employeesList.add(employee);
        // Update the other side of the relationship
        if(!employee.getProjectsList().contains(this)) {
            employee.getProjectsList().add(this);
        }
        return true;
    }
    
    public boolean removeEmployee(Employee employee){
        if(employeesList == null || employeesList.isEmpty()){
            return false;
        }
        boolean isPresent = this.employeesList.stream().anyMatch(emp -> emp.equals(employee));
        if(!isPresent){
            return false;
        }
        employeesList.remove(employee);
        // Update the other side of the relationship
        employee.getProjectsList().remove(this);
        return true;
    }


}
