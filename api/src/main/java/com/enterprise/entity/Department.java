package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String department_name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonManagedReference
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "department_project",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projectsList;  // FIXED type!
    public Boolean addEmployee(Employee employee){
    if(employees==null){
       employees = new ArrayList<>();
    }
    Boolean isPresent = this.employees.stream().anyMatch(employee1 -> employee1.equals(employee));
    if(isPresent){
        return false;
    }
  employees.add(employee);
    employee.setDepartment(this);
    return true;
};
    public Boolean removeEmployee(Employee employee){
        if(this.employees==null){
            this.employees = new ArrayList<>();
        }
        Boolean isPresent = this.employees.stream().anyMatch(employee1 -> employee1.equals(employee));
        if(!isPresent){
            return false;
        }
        this.employees.remove(employee);
        System.out.println(this.employees.stream().anyMatch(element->element.equals(employee)));
        employee.setDepartment(null);
        return true;
    };

    public Department(long id) {
        this.id = id;
    }

    public  void setManager(Employee employee){
        this.manager= employee;
    }
    public  void removeManager(){
        this.manager= null;
    }
}
