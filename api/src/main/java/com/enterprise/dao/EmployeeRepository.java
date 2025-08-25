package com.enterprise.dao;

import com.enterprise.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findById(long userId);

    Employee save(Employee employee);

    void removeEmployeeById(long id);

    void deleteById(long id);

    Page<Employee> findByFirstNameContainsIgnoreCaseOrLastNameContainingIgnoreCaseOrDepartment_DepartmentNameContainingIgnoreCase(String firstName, String lastName, String departmentDepartmentName, Pageable  firstPageWithTwoElements);
    @Query(
            value = """
        SELECT DISTINCT e.*
        FROM employee e
        WHERE (:filter IS NULL 
               OR LOWER(e.first_name) LIKE LOWER(CONCAT('%', :filter, '%'))
               OR LOWER(e.last_name) LIKE LOWER(CONCAT('%', :filter, '%')))
    """,
            countQuery = """
        SELECT COUNT(DISTINCT e.id)
        FROM employee e
        WHERE (:filter IS NULL 
               OR LOWER(e.first_name) LIKE LOWER(CONCAT('%', :filter, '%'))
               OR LOWER(e.last_name) LIKE LOWER(CONCAT('%', :filter, '%')))
    """,
            nativeQuery = true
    )
    Page<Employee> searchEmployee(@Param("filter") String keyword, Pageable pageable);


}
