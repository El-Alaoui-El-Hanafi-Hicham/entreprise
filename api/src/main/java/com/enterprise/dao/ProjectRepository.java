package com.enterprise.dao;

import com.enterprise.entity.Department;
import com.enterprise.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
