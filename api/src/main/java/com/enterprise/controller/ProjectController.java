package com.enterprise.controller;

import com.enterprise.dto.ProjectCreateDto;
import com.enterprise.dto.ProjectDto;
import com.enterprise.dto.ProjectSearchRequest;
import com.enterprise.dto.ProjectUpdateDto;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Project;
import com.enterprise.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api/project")
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Create a new project
    @PostMapping("")
    private ResponseEntity<ProjectDto> createProject(@RequestBody ProjectCreateDto createDto) {
        return projectService.createProject(createDto);
    }

    // Get all projects with pagination
    @PostMapping("/search")
    private Page<ProjectDto> getAllProjects(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestBody(required = false) ProjectSearchRequest request
            ) {
        String keyword = request != null ? request.getKeyword() : null;
        List<Long> departmentIds = request != null ? request.getDepartmentIds() : null;
        List<Long> employeeIds = request != null ? request.getEmployeeIds() : null;
        List<String> statuses = request != null ? request.getStatuses() : null;
        return projectService.getAllProjects(pageNumber, pageSize,keyword,departmentIds,employeeIds,statuses);
    }

    // Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // Update project
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateDto updateDto) {
        return projectService.updateProject(id, updateDto);
    }

    // Delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }

    // Change project manager (legacy endpoint)
    @PutMapping("/{id}/manager")
    public ResponseEntity<String> changeManager(
            @PathVariable Long id,
            @RequestParam(name = "user_id") Long userId) {
        return projectService.changeManager(userId, id);
    }
    // Get project by ID
    @GetMapping("/{id}/employees/{filter}")
    public List<Employee> getEm(@PathVariable Long id, @PathVariable String filter) {
        return projectService.getProjectEmployees(id,filter);
    }

}
