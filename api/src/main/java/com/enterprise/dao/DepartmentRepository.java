package com.enterprise.dao;

import com.enterprise.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepartmentRepository extends JpaRepository<Department,Long> {
//    @Query(value = "select department_name,manager from department where id= :id",
//            nativeQuery = true)
//    ListfindById(@Param("id") Long id)
}
