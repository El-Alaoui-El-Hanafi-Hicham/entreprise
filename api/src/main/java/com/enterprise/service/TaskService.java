package com.enterprise.service;

import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dao.TaskRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    TaskRepository taskRepository;
    @Autowired
    public TaskService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, TaskRepository taskRepository) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
    }
    public ResponseEntity<String> addTask(Task task){
        Task new_task = new Task(task.getTask_name(),task.getDescription(),task.getStart_date(),task.getEnd_date());
        this.taskRepository.save(task);
        return ResponseEntity.ok("Task Created Successfuly");
    }
    public ResponseEntity<String> assignToEmployee(long user_id,long id){
       Optional<Employee> employee=this.employeeRepository.findById(user_id);
       if(employee.isEmpty()){
           return ResponseEntity.badRequest().body("Employee not found");
       }else{
        Optional<Task> task = this.taskRepository.findById(id);
        if(task.isEmpty()){
            return ResponseEntity.badRequest().body("task not found");
        }else{

        task.get().addEmployeeToEmployee(employee.get());
        this.taskRepository.save(task.get());
        return ResponseEntity.ok("Task " + task.get().getTask_name()+ " is assigned to "+ employee.get().getFirstName()+" "+ employee.get().getLastName());
       }
       }
    }
    public ResponseEntity<String> ToggleStatus(long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            return ResponseEntity.badRequest().body("task not found");

        }else{
            if(task.get().getStatus()==null){
                task.get().setStatus(true);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Completed\"");
            }else{


            if(task.get().getStatus()){
            task.get().setStatus(false);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Not Completed\"");

            }else{

                task.get().setStatus(true);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Completed\"");

            }
            }
        }
    }

}
