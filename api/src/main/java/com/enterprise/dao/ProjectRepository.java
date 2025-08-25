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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProjectRepository extends ListPagingAndSortingRepository<Project, Long> {

    Project save(Project newProject);

    Optional<Project> findById(long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Project> findAll();

    @Query(
            value = """
        SELECT DISTINCT p.*
        FROM project p
        LEFT JOIN department_projects pd ON p.id = pd.project_id
        LEFT JOIN department d ON d.id = pd.department_id
        LEFT JOIN project_employees pe ON p.id = pe.project_id
        LEFT JOIN employee e ON e.id = pe.employee_id
        WHERE
          (:filter IS NULL OR LOWER(p.project_name) LIKE LOWER(CONCAT('%', :filter, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :filter, '%')))
      AND (:statuses IS NULL OR p.status IN :statuses)
                                          AND (:departmentIds IS NULL OR d.id IN (:departmentIds))
          AND (:employeeIds IS NULL OR e.id IN (:employeeIds))
          AND (:start_date IS NULL OR p.start_date >= :start_date)
          AND (:end_date IS NULL OR p.end_date <= :end_date)
    """,
            countQuery = """
        SELECT COUNT(DISTINCT p.id)
        FROM project p
        LEFT JOIN department_projects pd ON p.id = pd.project_id
        LEFT JOIN department d ON d.id = pd.department_id
        LEFT JOIN project_employees pe ON p.id = pe.project_id
        LEFT JOIN employee e ON e.id = pe.employee_id
        WHERE
          (:filter IS NULL OR LOWER(p.project_name) LIKE LOWER(CONCAT('%', :filter, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :filter, '%')))
          AND (:statuses IS NULL OR p.status IN (:statuses))
          AND (:departmentIds IS NULL OR d.id IN (:departmentIds))
          AND (:employeeIds IS NULL OR e.id IN (:employeeIds))
          AND (:start_date IS NULL OR p.start_date >= :start_date)
          AND (:end_date IS NULL OR p.end_date <= :end_date)
    """,
            nativeQuery = true
    )
    Page<Project> searchProjects(
            @Param("filter") String filter,
            @Param("departmentIds") List<Long> departmentIds,
            @Param("employeeIds") List<Long> employeeIds,
            @Param("statuses") List<String> statuses,
            @Param("start_date") String startDate, // format: 'YYYY-MM-DD'
            @Param("end_date") String endDate,
            Pageable pageable
    );







}
