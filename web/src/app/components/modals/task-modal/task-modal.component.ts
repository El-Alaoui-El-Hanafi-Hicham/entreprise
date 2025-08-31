import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { departmentService } from 'src/app/services/department/department.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { ProjectService } from 'src/app/services/project/project.service';
import { TaskService } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-task-modal',
  standalone: false,
  templateUrl: './task-modal.component.html',
  styleUrl: './task-modal.component.css',
})
export class TaskModalComponent implements OnInit {
  onNoClick() {
    this.closeModal.emit(false);
    this.taskForm.reset();
    this.taskData = null;
    this.isLoading = false;
  }

  @Input() visible: boolean = false;
  @Input() taskData: any;
  @Input() isEdit: boolean = false;
  @Output() closeModal = new EventEmitter<any>();
  taskForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  ManagerOptions: any[] = [];
  employees: any[] = [];
  departments: any[] = [];
  projects: any[] = [];
  keyword: string = '';
  priorities: any[] = [
    { label: 'Low', value: 'Low' },
    { label: 'Medium', value: 'Medium' },
    { label: 'High', value: 'High' },
    { label: 'Critical', value: 'Critical' },
  ];
  statusOptions: any[] = [
    { label: 'Pending', value: 'Pending' },
    { label: 'In Progress', value: 'In Progress' },
    { label: 'Completed', value: 'Completed' },
    { label: 'On Hold', value: 'On Hold' },
  ];
  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private messageService: MessageService,
    private employeeService: EmployeeService,
    private departmentService: departmentService,
    private taskService: TaskService
  ) {
    // Initialize the form with default values if needed
    this.taskForm = this.fb.group({
      id: ['', [Validators.required, Validators.minLength(4)]],
      task_name: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required]],
      status: [null, [Validators.required]],
      priority: [ { label: 'Pending', value: 'Pending' }, [Validators.required]],
      project: [null, []],
      manager: [null, [Validators.required]],
      employees: [null, [Validators.required]],
      start_date: ['', [Validators.required]],
      end_date: ['', [Validators.required]],
    });
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['visible']?.currentValue) {
      this.getDepartments();
      this.getEmployees();
    }
  }
  ngOnInit(): void {
    this.getProjects();
  }

  addTask() {
    this.taskService.createTask(this.taskForm.value).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Task Created Successfully',
        });
        this.closeModal.emit('Task Created Successfully');
        this.taskForm.reset();
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to create task. Please try again later.',
        });
      },
    });
  }

  createTask() {
    this.isLoading = true;
    // Logic to create a task
    // After successful creation, emit the closeModal event
    console.log(this.taskForm.value);
    this.taskService.createTask(this.taskForm.value).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Task Created Successfully',
        });
        this.closeModal.emit('Task Created Successfully');
        this.taskForm.reset();
        this.taskData = null;
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to create task. Please try again later.',
        });
      },
    });

    this.isLoading = false;
  }
  getProjects(keyword: string = '') {
    this.projectService.getProjects(0, 100, '').subscribe({
      next: (response: any) => {
        this.projects = response.content.map((proj: any) => ({
          label: proj.project_name,
          value: proj.id,
        }));
      },
    });
  }
  getDepartments(keyword: string = '') {
    this.departmentService.getDepartments(0, 20, keyword).subscribe((val) => {
      console.log('the val ===>', val);
      this.departments = val.content || [];
    });
  }
  getEmployees(keyword: string = '') {
    this.employeeService.getEmployees(0, 100, keyword).subscribe((val: any) => {
      this.employees =
        val.content.map((el: any) => ({
          ...el,
          label: el?.firstName + ' ' + el?.lastName,
        })) || [];
    });
  }
  findProject($event: AutoCompleteCompleteEvent) {
    this.getProjects($event.query);
  }
  findEmployee($event: AutoCompleteCompleteEvent) {
    this.getEmployees($event.query);
  }
  findDepartment($event: AutoCompleteCompleteEvent) {
    this.getDepartments($event.query);
  }
}
