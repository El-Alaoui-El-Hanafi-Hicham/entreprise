package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
@NoArgsConstructor
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
    private Date start_date;
    @Column
    private Date end_date;
    @Column
    private Boolean status;
    @Column
    private Priority priority;
    @ManyToMany
    @JoinTable(
            name = "employee_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employeeList;
    @Column
    private Timestamp created_at ;
    @Column
    private Timestamp updated_at;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTask> subTaskList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Employee createdBy;

    public Task(String task_name, String description, Date start_date, Date end_date) {
        this.task_name = task_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = false;
    }
    public boolean addEmployeeToEmployee(Employee employee){
        if(this.employeeList == null||this.employeeList.isEmpty()){
            this.employeeList = new ArrayList<>();
        }else{
            if(this.employeeList.stream().anyMatch(element->element.equals(employee))){
                return false;
            }
        }
        this.employeeList.add(employee);
        return true;

    }
}
