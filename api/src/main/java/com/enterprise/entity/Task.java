package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Task(String task_name, String description, Date start_date, Date end_date) {
        this.task_name = task_name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = false;
    }
}
