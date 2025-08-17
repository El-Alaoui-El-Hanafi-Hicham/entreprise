package com.enterprise.controller;

import com.enterprise.entity.Task;
import com.enterprise.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()

public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService= taskService;
    }

    @PostMapping("/task")
    private ResponseEntity<String> addTask(@RequestBody Task task){
        return this.taskService.addTask(task);
    }
    @GetMapping("/task/assignEmployee")
    private ResponseEntity<String> assignEmployee(@RequestParam(name = "user_id") long user_id,@RequestParam(name="id") long id){
        return this.taskService.assignToEmployee(user_id,id);
    }
    @GetMapping("/task/changeStatus")
    private ResponseEntity<String> changeStatus(@RequestParam(name="id") long id){
        return this.taskService.ToggleStatus(id);
    }

}
