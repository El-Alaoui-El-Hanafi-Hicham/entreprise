package com.enterprise.dao;

import com.enterprise.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface DepartmentRepository extends ListPagingAndSortingRepository<Department,Long> {
    @Query(value = "select count(*) from department where departmentName= :name",
            nativeQuery = true)
    Integer coutDepartmentName(@Param("name") String departmentName);

    Optional<Department> findById(long id);

    Department save(Department department);

    boolean delete(Department department);

    List<Department> findAll();

    Page<Department> findByDepartmentNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Department> findByEmployees_IdIn(List<Long> employeeIds, Pageable pageable);

    Page<Department> findByDepartmentNameContainingIgnoreCaseAndEmployees_IdIn(
            String name, List<Long> employeeIds, Pageable pageable
    );//    @Query(value = "select departmentName,manager from department where id= :id",
//            nativeQuery = true)
//    ListfindById(@Param("id") Long id)
}
