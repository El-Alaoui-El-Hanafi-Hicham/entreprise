package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public  class Employee implements UserDetails {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
@Column
private String first_name;
@Column()
@Nullable
    private Status status;
@Column
private String last_name;
@Column
private int phone_number;
@Column
private Date hire_date;
@Column
private  String job_title;
    @Column(unique = true,length = 100,nullable = false)
    private String email;
    @Column(nullable = false)
    private  String password;

        @CreationTimestamp
        @Column(updatable = false)
        private Date created_at;;
        @UpdateTimestamp
        @Column
        private Date updated_at;
@ManyToOne(fetch = FetchType.LAZY)
@JsonBackReference
@JoinColumn(name = "department_id")

private Department department;
@OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
private List<Task> taskList;
@OneToMany(mappedBy = "manager",fetch = FetchType.LAZY)
private List<Project> projectList;

private Department getDepartement(){
    return this.department;
}
    public Employee(String first_name,String last_name, String email, Date hire_date,int phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.hire_date = hire_date;
        this.phone_number=phone_number;
    }

    public Employee(String first_name,String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
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
