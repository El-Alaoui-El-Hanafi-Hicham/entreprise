package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@ToString
public class Employee implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber; // Use String, not int

    @Column
    private Date hireDate;

    @Column(name="job_title")
    private String jobTitle;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Nullable
    private Status status;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;



    @ManyToMany(mappedBy = "employeeList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> taskList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employee_subtask",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "subtask_id")
    )
    @JsonIgnore
    private List<SubTask> subTaskList = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "project_employees",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @JsonIgnore
    private List<Project> projectsList = new ArrayList<>();

    private Department getDepartement(){
    return this.department;
}
    public Employee(String firstName, String lastName, String email, Date hire_date, String phone_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hire_date;
        this.phoneNumber=phone_number;
    }

    public Employee(String firstName,String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean addProject(Project project) {
        if (projectsList == null) {
            projectsList = new ArrayList<>();
        }
        Boolean isPresent = this.projectsList.stream().anyMatch(proj -> proj.equals(project));
        if (isPresent) {
            return false;
        }
        projectsList.add(project);
        if(!project.getEmployeesList().contains(this)) {
            project.getEmployeesList().add(this);
        }
        return true;
    }

    public Boolean removeProject(Project project) {
        if (this.projectsList == null) {
            this.projectsList = new ArrayList<>();
        }
        Boolean isPresent = this.projectsList.stream().anyMatch(proj -> proj.equals(project));
        if (!isPresent) {
            return false;
        }
        this.projectsList.remove(project);
        project.getEmployeesList().remove(this);
        return true;
    }

    public Boolean addTask(Task task) {
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        Boolean isPresent = this.taskList.stream().anyMatch(t -> t.equals(task));
        if (isPresent) {
            return false;
        }
        taskList.add(task);
        if(!task.getEmployeeList().contains(this)) {
            task.getEmployeeList().add(this);
        }
        return true;
    }

    public Boolean removeTask(Task task) {
        if (this.taskList == null) {
            this.taskList = new ArrayList<>();
        }
        Boolean isPresent = this.taskList.stream().anyMatch(t -> t.equals(task));
        if (!isPresent) {
            return false;
        }
        this.taskList.remove(task);
        task.getEmployeeList().remove(this);
        return true;
    }

}
