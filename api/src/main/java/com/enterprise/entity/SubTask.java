package com.enterprise.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @ManyToMany(mappedBy = "subTaskList") // must match Employee.subTaskList
    private List<Employee> employeeList;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
