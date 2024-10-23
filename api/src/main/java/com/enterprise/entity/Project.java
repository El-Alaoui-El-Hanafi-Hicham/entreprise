package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.Managed;

import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name="manager_id")
    private Employee manager;

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
        this.department = newDep;
        this.manager = newMan;
    }
//@Override
//    void setManager(Employee manager){
//        this.manager = manager;
//    }

}
