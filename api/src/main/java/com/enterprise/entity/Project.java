package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.Managed;

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
    @ManyToMany(mappedBy = "departmentList") // mappedBy points to the field in Employee
    private List<Department> departments;
    @ManyToOne
    @JoinColumn(name="manager_id")
    private Employee manager;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private Employee Owner;
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

    public Project(String project_name, String description, Date start_date, Date end_date, long department_id, long manager_id) {
        this.project_name = project_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        Department newDep = new Department();
        newDep.setId(department_id);
        Employee newMan = new Employee();
        newMan.setId(manager_id);
        this.manager = newMan;
    }
//@Override
//    void setManager(Employee manager){
//        this.manager = manager;
//    }

}
