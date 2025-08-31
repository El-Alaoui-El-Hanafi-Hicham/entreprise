package com.enterprise.controller;

import com.enterprise.dto.TaskDTO.TaskCreateDTO;
import com.enterprise.dto.TaskDTO.TaskSearchDTO;
import com.enterprise.entity.Task;
import com.enterprise.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path="/api/task")
@RestController

public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService= taskService;
    }

    @GetMapping("")
    private String getTasks1(){
        return "SOMETHING";
    }
    @PostMapping("/search")
    private Page<Task> getTasks(@RequestParam(required = false, defaultValue = "0") int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestBody(required = false) TaskSearchDTO task){
        System.out.println("found it");
        return this.taskService.getTask(pageNumber,pageSize,task);
    }
    @PostMapping("")
    private ResponseEntity<String> addTask(@RequestBody TaskCreateDTO task){
//        return ResponseEntity.ok(task.toString());
        return this.taskService.addTask(task);
    }
    @GetMapping("/assignEmployee")
    private ResponseEntity<String> assignEmployee(@RequestParam(name = "user_id") long user_id,@RequestParam(name="id") long id){
        return this.taskService.assignToEmployee(user_id,id);
    }
//    @GetMapping("/changeStatus")
//    private ResponseEntity<String> changeStatus(@RequestParam(name="id") long id){
//        return this.taskService.ToggleStatus(id);
//    }

}
