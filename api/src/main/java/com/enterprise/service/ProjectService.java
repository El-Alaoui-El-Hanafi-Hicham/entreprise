package com.enterprise.service;

//import com.enterprise.dao.DepartmentRepository;
//import com.enterprise.dao.EmployeeRepository;
//import com.enterprise.dao.ProjectRepository;
//import com.enterprise.dao.TaskRepository;
//import com.enterprise.entity.Department;
//import com.enterprise.entity.Employee;
//import com.enterprise.entity.Project;
//import com.enterprise.entity.Task;
import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dao.ProjectRepository;
import com.enterprise.dao.TaskRepository;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public ResponseEntity<HashMap<String,String>> addProject(Project project){
        Optional<Employee> employee = this.employeeRepository.findById(project.getManager().getId());
        Optional<Employee> owner = this.employeeRepository.findById(project.getOwner().getId());
        HashMap<String,String> response = new HashMap<>();
        if(employee.isEmpty()){
            response.put("status","false");
            response.put("message","Manager not found");
            return ResponseEntity.badRequest().body(response);
        }else{
        if(owner.isEmpty()){
            response.put("status","false");
            response.put("message","Owner not found");
            return ResponseEntity.badRequest().body(response);

        }else{
            Project new_project = new Project(project.getProject_name(),project.getDescription(),project.getStart_date(),project.getEnd_date(),project.getManager(),project.getOwner());

            if((long) project.getDepartments().size() >0){
                for (int i = 0; i < project.getDepartments().size(); i++) {
                    Optional<Department> OptDepartment = this.departmentRepository.findById(project.getDepartments().get(i).getId());
                    if(OptDepartment.isEmpty()){
                        response.put("status","false");
                        response.put("message","Department not found");
                        return ResponseEntity.badRequest().body(response);

                    }else{
                      new_project.addDepartment(OptDepartment.get());
                    }
                }

            }
            if(!project.getEmployeeList().isEmpty()){
                for (int i = 0; i < project.getEmployeeList().size(); i++) {
                    Optional<Employee> OptEmployee = this.employeeRepository.findById(project.getEmployeeList().get(i).getId());
                    if(OptEmployee.isEmpty()){
                        response.put("status","false");
                        response.put("message","Employee"+project.getEmployeeList().get(i).getFirstName()+" "+project.getEmployeeList().get(i).getLastName()+" not found");
                        return ResponseEntity.badRequest().body(response);

                    }else{
                        new_project.addEmployee(OptEmployee.get());
                    }
                }

            }


            Project d = this.projectRepository.save(new_project);
if(d.equals(new_project)){

    response.put("status","true");
    response.put("message","Project Added Succesfuly");
    return ResponseEntity.ok().body(response);
}else{
    response.put("status","false");
    response.put("message","Something went bad, please try Again");
    return ResponseEntity.badRequest().body(response);
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
//    public ResponseEntity<String> ToggleStatus(long id){
//        Optional<Task> task = taskRepository.findById(id);
//        if(task.isEmpty()){
//            return ResponseEntity.badRequest().body("task not found");
//
//        }else{
//            if(task.get().getStatus()==null){
//                task.get().setStatus(true);
//                taskRepository.save(task.get());
//                return ResponseEntity.ok().body("task status changed to \"Completed\"");
//            }else{
//
//
//            if(task.get().getStatus()){
//            task.get().setStatus(false);
//                taskRepository.save(task.get());
//                return ResponseEntity.ok().body("task status changed to \"Not Completed\"");
//
//            }else{
//
//                task.get().setStatus(true);
//                taskRepository.save(task.get());
//                return ResponseEntity.ok().body("task status changed to \"Completed\"");
//
//            }
//            }
//        }
    }

//}
