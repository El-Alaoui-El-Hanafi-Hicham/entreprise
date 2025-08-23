package com.enterprise.controller;

import com.enterprise.dto.ProjectCreateDto;
import com.enterprise.dto.ProjectDto;
import com.enterprise.dto.ProjectUpdateDto;
import com.enterprise.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("")
    private ResponseEntity<Page<ProjectDto>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return projectService.getAllProjects(page, size);
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

    // Legacy endpoints for backward compatibility
    @GetMapping("/all")
    @Deprecated
    public ResponseEntity<Page<ProjectDto>> getProjectsLegacy(
            @RequestParam(name = "pageNumber") int page,
            @RequestParam(name = "pageSize") int size) {
        return projectService.getAllProjects(page, size);
    }

    @GetMapping("/manager")
    @Deprecated
    public ResponseEntity<String> changeManagerLegacy(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "id") Long id) {
        return projectService.changeManager(userId, id);
    }
}
