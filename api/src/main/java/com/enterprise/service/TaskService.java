package com.enterprise.service;

import com.enterprise.dao.*;
import com.enterprise.dto.TaskDTO.TaskBasicDto;
import com.enterprise.dto.TaskDTO.TaskCreateDTO;
import com.enterprise.dto.TaskDTO.TaskSearchDTO;
import com.enterprise.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final UserRepository userRepository;
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    TaskRepository taskRepository;
    ProjectRepository projectRepository;
    AuthenticationService authenticationService;
    @Autowired
    public TaskService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, TaskRepository taskRepository, ProjectRepository projectRepository, ProjectService projectService, AuthenticationService authenticationService, UserRepository userRepository) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.authenticationService=authenticationService;
        this.userRepository = userRepository;
    }
    public ResponseEntity<String> addTask(TaskCreateDTO task){
        System.out.println(task.toString());
//        return ResponseEntity.ok("Task Created Successfully");
        try {

        Employee getCurrentUser=authenticationService.getCurrentUser();
        if(getCurrentUser==null){
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
      Task  task1 =Task.builder().task_name(task.getTaskName()).description(task.getDescription()).created_at(LocalDateTime.now()).priority(Priority.valueOf(task.getPriority())).status(task.getStatus()).createdBy(getCurrentUser).build();
        if(task.getProjectId()!=null){
            Optional<Project> project= projectRepository.findById(task.getProjectId());
            if(project.isPresent()){
                task1.setProject(project.get());
            }else{
                return ResponseEntity.badRequest().body("Project not found");
            }
        }
        if(!task.getEmployeesIds().isEmpty()){
            for(Long employeeId:task.getEmployeesIds()){
                Optional<Employee> employee=employeeRepository.findById(employeeId);
                if(employee.isPresent()){
                    assert task1 != null;
                    task1.addEmployee(employee.get());
                }else{
                    return ResponseEntity.badRequest().body("Employee not found");
                }
            }
        }
        task1.setStart_date(task.getStartDate());
        task1.setEnd_date(task.getEndDate());
        task1.setManager(task.getManagerId()!=null?employeeRepository.findById(task.getManagerId()).orElse(getCurrentUser):getCurrentUser);
//        task1.setManager(task);
        this.taskRepository.save(task1);
            return ResponseEntity.ok("Task Created Successfuly");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the task");
        }


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
    public ResponseEntity<String> ToggleStatus(long id,String status){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            return ResponseEntity.badRequest().body("task not found");

        }else{
            if(task.get().getStatus()==null){
                task.get().setStatus(status);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Completed\"");
            }else{


            if(task.get().getStatus().isEmpty()){
            task.get().setStatus(status);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Not Completed\"");

            }else{

                task.get().setStatus(status);
                taskRepository.save(task.get());
                return ResponseEntity.ok().body("task status changed to \"Completed\"");

            }
            }
        }
    }

    public Page<Task> getTask(int pageNumber, int pageSize, TaskSearchDTO task) {
        Specification<Task> spec = (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> preds = new ArrayList<>();

            if (task.getKeyword() != null && !task.getKeyword().isBlank()) {
                String like = "%" + task.getKeyword().toLowerCase() + "%";
                Predicate p1 = cb.like(cb.lower(root.get("task_name")), like);
                Predicate p2 = cb.like(cb.lower(root.get("description")), like);
                preds.add(cb.or(p1, p2));
            }


            return cb.and(preds.toArray(new Predicate[0]));
        };
        System.out.println(spec.toString());
        Page<Task> task1 = taskRepository.findAll(spec, PageRequest.of(pageNumber, pageSize));
        Page<TaskBasicDto> taskBasicDtos = task1.map(el -> {
            TaskBasicDto taskBasicDto = TaskBasicDto.builder().id(el.getId())
                    .taskName(el.getTask_name())
                    .description(el.getDescription())
                    .startDate(el.getStart_date())
                    .endDate(el.getEnd_date())
                    .status(el.getStatus())
                    .priority(el.getPriority().name())
                    .build();

            return taskBasicDto;
        });
        return task1;
    }
}
