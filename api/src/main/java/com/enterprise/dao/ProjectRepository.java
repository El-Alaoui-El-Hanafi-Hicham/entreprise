package com.enterprise.dao;

import com.enterprise.entity.Department;
import com.enterprise.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProjectRepository extends ListPagingAndSortingRepository<Project,Long> {

    Project save(Project newProject);

    Optional<Project> findById(long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Project> findAll();

    @Query(
            value = """
            SELECT DISTINCT p
            FROM Project p
            LEFT JOIN p.departmentsList d
            LEFT JOIN p.employeesList e
            WHERE\s
                     (:statuses IS NULL OR p.status IN :statuses) \s
                        AND
                                    (:filter IS NULL OR
                   LOWER(p.project_name) LIKE LOWER(CONCAT('%', :filter, '%')) OR
                   LOWER(p.description)  LIKE LOWER(CONCAT('%', :filter, '%')))
              AND (:departmentIds IS NULL OR d.id IN :departmentIds)
              AND (:employeeIds   IS NULL OR e.id IN :employeeIds)
           \s""",
            countQuery = """
            SELECT COUNT(DISTINCT p)
            FROM Project p
            LEFT JOIN p.departmentsList d
            LEFT JOIN p.employeesList e
            WHERE (:filter IS NULL OR
                   LOWER(p.project_name) LIKE LOWER(CONCAT('%', :filter, '%')) OR
                   LOWER(p.description)  LIKE LOWER(CONCAT('%', :filter, '%')))
              AND (:departmentIds IS NULL OR d.id IN :departmentIds)
              AND (:employeeIds   IS NULL OR e.id IN :employeeIds) """
    )
    Page<Project> searchProjects(@Param("filter") String filter,
                                 @Param("departmentIds") List<Long> departmentIds,
                                 @Param("employeeIds") List<Long> employeeIds,
                                 @Param("statuses") List<String> statuses,
                                 Pageable pageable);


}
