package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.Managed;

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
    private Date start_date;
    @Column
    private Date end_date;
    @ManyToMany(mappedBy = "projectsList") // mappedBy points to the field in Employee
    private List<Department> departments;
    @ManyToOne
    @JoinColumn(name="manager_id")
    private Employee manager;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private Employee owner;
    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY)
    private List<Task> tasksList;
    @ManyToMany(mappedBy = "projectsList") // mappedBy points to the field in Employee
    private List<Employee> employeeList;
//    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY)
//    private List<SubTask> subTasksList;

    public Project(String project_name, String description, Date start_date, Date end_date) {
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Project(String project_name, String description, Date start_date, Date end_date,Employee Manager,Employee Owner) {
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.owner=Owner;
        this.manager = Manager;
    }

    public boolean addDepartment(Department department){
        if(departments==null){
            departments = new ArrayList<>();
        }
        boolean isPresent = this.departments.stream().anyMatch(employee1 -> employee1.equals(department));
        if(isPresent){
            return false;
        }
        departments.add(department);
        return true;
    }
    public boolean addEmployee(Employee employee){
        if(employeeList==null){
            employeeList = new ArrayList<>();
        }
        boolean isPresent = this.employeeList.stream().anyMatch(employee1 -> employee1.equals(employee));
        if(isPresent){
            return false;
        }
        employeeList.add(employee);
        return true;
    }


}
