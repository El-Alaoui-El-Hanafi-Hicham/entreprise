package com.enterprise.controller;

import com.enterprise.entity.Project;
import com.enterprise.entity.Task;
import com.enterprise.service.ProjectService;
import com.enterprise.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService= projectService;
    }

    @PostMapping("/project")
    private ResponseEntity<String> addProject(@RequestBody Project project){
        return this.projectService.addProject(project);
    }
    @GetMapping("/project")
    @ResponseBody
    private ResponseEntity<String> changeManager(@RequestParam(name = "user_id" )long user_id,@RequestParam(name = "id" )long id){
     return   this.projectService.changeManager(user_id,id);
    }
//    @GetMapping("/tasks/assignEmployee")
//    private ResponseEntity<String> assignEmployee(@RequestParam(name = "user_id") long user_id,@RequestParam(name="id") long id){
//        return this.taskService.assignToEmployee(user_id,id);
//    }
//    @GetMapping("/tasks/changeStatus")
//    private ResponseEntity<String> changeStatus(@RequestParam(name="id") long id){
//        return this.taskService.ToggleStatus(id);
//    }

}
