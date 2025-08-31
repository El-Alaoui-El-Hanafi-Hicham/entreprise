package com.enterprise.service;

import com.enterprise.dao.DepartmentRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dao.ProjectRepository;
import com.enterprise.dao.TaskRepository;
import com.enterprise.dto.DepartmentDTO.DepartmentBasicDto;
import com.enterprise.dto.TaskDTO.TaskBasicDto;
import com.enterprise.dto.employeeDTO.EmployeeBasicDto;
import com.enterprise.dto.projectDTO.ProjectCreateDto;
import com.enterprise.dto.projectDTO.ProjectDto;
import com.enterprise.dto.projectDTO.ProjectUpdateDto;
import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Project;
import com.enterprise.entity.Task;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    // Create a new project using ProjectCreateDto
    public ResponseEntity<ProjectDto> createProject(ProjectCreateDto createDto) {
        try {
            // Validate manager
            Optional<Employee> manager = employeeRepository.findById(createDto.getManagerId());
            if (manager.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Validate owner
            Optional<Employee> owner = employeeRepository.findById(createDto.getOwnerId());
            if (owner.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Project project = new Project();
            project.setProject_name(createDto.getProjectName());
            project.setDescription(createDto.getDescription());
            project.setStart_date(createDto.getStartDate());
            project.setEnd_date(createDto.getEndDate());
            project.setStatus(createDto.getStatus() != null ? createDto.getStatus() : "PENDING");
            project.setManager(manager.get());
            project.setOwner(owner.get());

            if (createDto.getEmployeeIds() != null && !createDto.getEmployeeIds().isEmpty()) {
                for (Long employeeId : createDto.getEmployeeIds()) {
                    Optional<Employee> employee = employeeRepository.findById(employeeId);
                    if (employee.isPresent()) {
                        project.addEmployee(employee.get());
                    }
                }
            }

            if (createDto.getDepartmentIds() != null && !createDto.getDepartmentIds().isEmpty()) {
                for (Long departmentId : createDto.getDepartmentIds()) {
                    Optional<Department> department = departmentRepository.findById(departmentId);
                    if (department.isPresent()) {
                        project.addDepartment(department.get());
                    }
                }
            }

            Project savedProject = projectRepository.save(project);

            ProjectDto projectDto = convertToDto(savedProject);
            return ResponseEntity.ok(projectDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get all projects with pagination
    public Page<ProjectDto> getAllProjects(int page, int size, String filter, List<Long> department_ids, List<Long> employee_ids, List<String> statuses, String startDate, String endDate) {
       try {

           Specification<Project> spec = (root, query, cb) -> {
               query.distinct(true);
               List<Predicate> preds = new ArrayList<>();

               if (filter != null && !filter.isBlank()) {
                   String like = "%" + filter.toLowerCase() + "%";
                   Predicate p1 = cb.like(cb.lower(root.get("project_name")), like);
                   Predicate p2 = cb.like(cb.lower(root.get("description")), like);
                   preds.add(cb.or(p1, p2));
               }

               if (statuses != null && !statuses.isEmpty()) {
                   preds.add(root.get("status").in(statuses));
               }

               if (department_ids != null && !department_ids.isEmpty()) {
                   Join<Project, Department> djoin = root.join("departmentsList", JoinType.LEFT);
                   preds.add(djoin.get("id").in(department_ids));
               }

               if (employee_ids != null && !employee_ids.isEmpty()) {
                   Join<Project, Employee> ejoin = root.join("employeesList", JoinType.LEFT);
                   preds.add(ejoin.get("id").in(employee_ids));
               }

               if (startDate != null) {
                   preds.add(cb.greaterThanOrEqualTo(root.get("start_date"), startDate));
               }

               if (endDate != null) {
                   preds.add(cb.lessThanOrEqualTo(root.get("end_date"), endDate));
               }

               return cb.and(preds.toArray(new Predicate[0]));
           };
           System.out.println(spec.toString());
          List<Project> projects= projectRepository.findAll(spec);

           return projects.stream().map(this::convertToDto).collect(Collectors.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, PageRequest.of(page, size), projects.size())));
        } catch (Exception e) {
           throw new RuntimeException(e);
       }
            }

    private ProjectDto convertToDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProject_name())
                .description(project.getDescription())
                .startDate(project.getStart_date())
                .endDate(project.getEnd_date())
                .status(project.getStatus())
                .manager(convertToEmployeeBasicDto(project.getManager()))
                .owner(convertToEmployeeBasicDto(project.getOwner()))
                .employees(project.getEmployeesList().stream()
                        .map(this::convertToEmployeeBasicDto)
                        .collect(Collectors.toList()))
                .departments(project.getDepartmentsList().stream()
                        .map(this::convertToDepartmentBasicDto)
                        .collect(Collectors.toList()))
                .tasks(project.getTasksList().stream()
                        .map(this::convertToTaskBasicDto)
                        .collect(Collectors.toList()))
                .employeeCount(project.getEmployeesList().size())
                .departmentCount(project.getDepartmentsList().size())
                .taskCount(project.getTasksList().size())
                .build();

    }

    // Get project by ID
    public ResponseEntity<ProjectDto> getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            ProjectDto projectDto = convertToDto(project.get());
            return ResponseEntity.ok(projectDto);
        }
        return ResponseEntity.notFound().build();
    }

    // Update project
    public ResponseEntity<ProjectDto> updateProject(Long id, ProjectUpdateDto updateDto) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Project project = existingProject.get();
        
        // Update fields if provided
        if (updateDto.getProjectName() != null) {
            project.setProject_name(updateDto.getProjectName());
        }
        if (updateDto.getDescription() != null) {
            project.setDescription(updateDto.getDescription());
        }
        if (updateDto.getStartDate() != null) {
            project.setStart_date(updateDto.getStartDate());
        }
        if (updateDto.getEndDate() != null) {
            project.setEnd_date(updateDto.getEndDate());
        }
        if (updateDto.getStatus() != null) {
            project.setStatus(updateDto.getStatus());
        }
        if (updateDto.getManagerId() != null) {
            Optional<Employee> manager = employeeRepository.findById(updateDto.getManagerId());
            if (manager.isPresent()) {
                project.setManager(manager.get());
            }
        }
        if (updateDto.getOwnerId() != null) {
            Optional<Employee> owner = employeeRepository.findById(updateDto.getOwnerId());
            if (owner.isPresent()) {
                project.setOwner(owner.get());
            }
        }

        Project savedProject = projectRepository.save(project);
        ProjectDto projectDto = convertToDto(savedProject);
        return ResponseEntity.ok(projectDto);
    }

    // Delete project
    public ResponseEntity<Void> deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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
    public List<Employee> getProjectEmployees(Long projectId,String filter) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        List<Employee> employeeList= projectOpt.map(Project::getEmployeesList).orElse(Collections.emptyList());
        employeeList.addAll(projectOpt.get().getDepartmentsList().stream().flatMap(department -> department.getEmployees().stream()).collect(Collectors.toList()));
        employeeList.add(projectOpt.get().getManager());
        employeeList.add(projectOpt.get().getOwner());
        employeeList.stream().filter(Objects::isNull).filter(employee -> {
            if(employee.getFirstName().startsWith(filter)||employee.getLastName().startsWith(filter)||employee.getEmail().startsWith(filter)||employee.getJobTitle().startsWith(filter)){
                return true;
            }
                return false;

        }).forEach(employee -> employeeList.remove(employee));
        return employeeList;
    }


    private EmployeeBasicDto convertToEmployeeBasicDto(Employee employee) {
        if (employee == null) return null;
        return EmployeeBasicDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .jobTitle(employee.getJobTitle())
                .fullName(employee.getFirstName() + " " + employee.getLastName())
                .build();
    }

    private DepartmentBasicDto convertToDepartmentBasicDto(Department department) {
        if (department == null) return null;
        String managerName = department.getManager() != null ? 
            department.getManager().getFirstName() + " " + department.getManager().getLastName() : null;
        
        return DepartmentBasicDto.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .managerName(managerName)
                .employeeCount(department.getEmployees().size())
                .build();
    }

    private TaskBasicDto convertToTaskBasicDto(Task task) {
        if (task == null) return null;
        return TaskBasicDto.builder()
                .id(task.getId())
                .taskName(task.getTask_name())
                .description(task.getDescription())
                .status(task.getStatus())
                .startDate(task.getStart_date())
                .endDate(task.getEnd_date())
                .priority(task.getPriority() != null ? task.getPriority().toString() : null)
                .build();
    }


}
