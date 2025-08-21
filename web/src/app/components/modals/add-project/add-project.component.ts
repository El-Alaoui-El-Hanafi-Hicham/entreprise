import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { departmentService } from 'src/app/services/department/department.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-add-project',
  standalone: false,
  templateUrl: './add-project.component.html',
  styleUrl: './add-project.component.css'
})
export class AddProjectComponent implements OnInit,OnChanges {
isInvalid(arg0: string) {
throw new Error('Method not implemented.');
}
onNoClick() {
throw new Error('Method not implemented.');
}
addTask() {
throw new Error('Method not implemented.');
}

 @Input() visible: boolean = false;
  @Input() taskData: any;
  @Input() isEdit: boolean=false;
  @Output() closeModal = new EventEmitter<any>();
  projectForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  ManagerOptions: any[] = [];
  employees: any[] = [];
  departments: any[] = [];
  constructor(private fb: FormBuilder,private projectService:ProjectService,private messageService: MessageService,private employeeService:EmployeeService,private departmentService: departmentService) {
    // Initialize the form with default values if needed
    this.projectForm = this.fb.group({
      project_name: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required]],
      manager: [null, []],
      owner: [null, []],
      employeeList: [null, [Validators.required]],
      departments: [null, [Validators.required]],
      start_date: ['', [Validators.required]],
      end_date: ['', [Validators.required]]
    });

  }
  ngOnChanges(changes: SimpleChanges): void {
      if(changes['visible'].currentValue){
        this.getDepartments();
        this.getEmployees();
      }
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  getDepartments() {
    this.departmentService.getDepartments().subscribe((val) => {
      this.departments = val||[];
    })
  }
  getEmployees() {
    this.employeeService.getEmployees().subscribe((val) => {
      this.employees = val.content.map((el:any)=>({...el,label:el.firstName+" "+el.lastName}))||[];
    })
  }

  addProject() {
    this.projectService.addProject(this.projectForm.value).subscribe({
      next:(value:any) => {
  if(Boolean(value['status'])){

    this.closeModal.emit(true);
  }
  this.messageService.add({severity:'success', summary:'Success', detail:value['message']});
      },
      error:(error) => {
        this.messageService.add({severity:'error', summary:'Error', detail:'Failed to add project. Please try again later.'});
      }
    })
  }
}
