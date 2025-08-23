package com.enterprise.dao;

import com.enterprise.entity.Department;
import com.enterprise.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProjectRepository extends ListPagingAndSortingRepository<Project,Long> {

    Project save(Project newProject);

    Optional<Project> findById(long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
