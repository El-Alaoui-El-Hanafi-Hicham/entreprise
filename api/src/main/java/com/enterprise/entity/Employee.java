package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @ManyToMany(mappedBy = "employeeList")
    private List<Task> taskList ;


    @ManyToMany
    @JoinTable(
            name = "employee_subtask",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "subtask_id")
    )
    private List<SubTask> subTaskList;  // FIXED type!

    @ManyToMany
    @JoinTable(
            name = "employee_project",  // join table name
            joinColumns = @JoinColumn(name = "employee_id"), // foreign key in join table pointing to Employee
            inverseJoinColumns = @JoinColumn(name = "project_id") // foreign key pointing to Project
    )
    private List<Project> projectsList ;
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

}
