package com.enterprise.dao;

import com.enterprise.entity.Department;
import com.enterprise.entity.Project;
import com.enterprise.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);
}
