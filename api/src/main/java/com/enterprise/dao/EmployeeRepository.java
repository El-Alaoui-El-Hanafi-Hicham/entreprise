package com.enterprise.dao;

import com.enterprise.entity.Employee;
import com.enterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findById(long userId);

    Employee save(Employee employee);
}
