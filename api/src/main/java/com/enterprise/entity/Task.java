package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column
    private String task_name;
    @Column
    private String description;
    @Column
    private LocalDate start_date;
    @Column
    private LocalDate end_date;
    @Column
    private String status;
    @Column
    private Priority priority;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employee_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonIgnore
    private List<Employee> employeeList = new ArrayList<>();
    @Column
    private LocalDateTime created_at ;
    @Column
    private LocalDateTime updated_at;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SubTask> subTaskList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Employee createdBy;

    public Task(String task_name, String description, LocalDate start_date, LocalDate end_date,String status,Priority priority) {
        this.task_name = task_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.priority = priority;
    }
    public boolean addEmployee(Employee employee){
        if(this.employeeList == null){
            this.employeeList = new ArrayList<>();
        }
        if(this.employeeList.stream().anyMatch(element->element.equals(employee))){
            return false;
        }
        this.employeeList.add(employee);
        // Update the other side of the relationship
        if(!employee.getTaskList().contains(this)) {
            employee.getTaskList().add(this);
        }
        return true;
    }
    
    public boolean removeEmployee(Employee employee){
        if(this.employeeList == null || this.employeeList.isEmpty()){
            return false;
        }
        if(!this.employeeList.stream().anyMatch(element->element.equals(employee))){
            return false;
        }
        this.employeeList.remove(employee);
        // Update the other side of the relationship
        employee.getTaskList().remove(this);
        return true;
    }

    public void addEmployeeToEmployee(Employee employee) {

    }
}
