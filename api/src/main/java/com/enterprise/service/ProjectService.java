package com.enterprise.service;

import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dao.ProjectRepository;
import com.enterprise.dao.TaskRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Project;
import com.enterprise.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    TaskRepository taskRepository;
    ProjectRepository projectRepository;

    @Autowired
    public ProjectService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, TaskRepository taskRepository,ProjectRepository projectRepository) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
        this.projectRepository=projectRepository;
    }
    public ResponseEntity<String> addProject(Project project){
        Optional<Employee> employee = this.employeeRepository.findById(project.getManager().getId());
        Optional<Department> department = this.departmentRepository.findById(project.getDepartment().getId());
        if(employee.isEmpty()){
            return ResponseEntity.badRequest().body("Manager not found");
        }else{
        if(this.departmentRepository.findById(project.getDepartment().getId()).isEmpty()){
            return ResponseEntity.badRequest().body("Departement not found");

        }else{

            Project new_project = new Project(project.getProject_name(),project.getDescription(),project.getStart_date(),project.getEnd_date(),project.getDepartment().getId(),project.getManager().getId());
            Project d = this.projectRepository.save(new_project);
if(d.equals(new_project)){

    return ResponseEntity.ok("Project Added Succesfuly");
}else{
    return ResponseEntity.ok("Something Bad happened");
}
        }
        }
    }
    public ResponseEntity<String> changeManager(long user_id,long id){
       Optional<Employee> employee=this.employeeRepository.findById(user_id);
       if(employee.isEmpty()){
           return ResponseEntity.badRequest().body("Manager not found");
       }else{
           Optional<Project> project = this.projectRepository.findById(id);

        if(project.isEmpty()){
            return ResponseEntity.badRequest().body("Project not found");
        }else{

            project.get().setManager(employee.get());
        this.projectRepository.save(project.get());
        return ResponseEntity.ok("Project " + project.get().getProject_name()+ " is assigned to "+ employee.get().getFirstName()+" "+ employee.get().getLastName()+" now.");
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
