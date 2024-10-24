package com.enterprise.dao;

import com.enterprise.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query(value = "select count(*) from department where department_name= :name",
            nativeQuery = true)
    Integer coutDepartmentName(@Param("name") String department_name);
//    @Query(value = "select department_name,manager from department where id= :id",
//            nativeQuery = true)
//    ListfindById(@Param("id") Long id)
}
