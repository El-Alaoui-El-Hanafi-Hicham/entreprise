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
this.closeModal.emit(false)
this.projectForm.reset();
this.selectedProject=null;
this.isLoading=false;

}
addTask() {
throw new Error('Method not implemented.');
}

 @Input() visible: boolean = false;
  @Input() selectedProject: any;
  @Input() isEdit: boolean=false;
  @Output() closeModal = new EventEmitter<any>();
  projectForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  ManagerOptions: any[] = [];
  employees: any[] = [];
  page: number = 0;
  size: number = 10;
  keyword: string = "";
  departments: any[] = [];
  constructor(private fb: FormBuilder,private projectService:ProjectService,private messageService: MessageService,private employeeService:EmployeeService,private departmentService: departmentService) {
    // Initialize the form with default values if needed
    this.projectForm = this.fb.group({
      project_name: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required]],
      manager: [null, []],
      owner: [null, []],
      employeesList: [null, [Validators.required]],
      departmentsList: [null, [Validators.required]],
      start_date: ['', [Validators.required]],
      end_date: ['', [Validators.required]]
    });

  }
  ngOnChanges(changes: SimpleChanges): void {
  if (changes['visible']?.currentValue) {
    this.getDepartments();
    this.getEmployees();

    if (this.selectedProject) {
      console.log(this.selectedProject);

      // Patch the form
      this.projectForm.patchValue({
        project_name: this.selectedProject.project_name,
        description: this.selectedProject.description,
        manager: this.selectedProject.manager,
        owner: this.selectedProject.owner,
        employeesList: this.selectedProject.employees?.map((emp: any) => ({
          ...emp,
          label: emp.full_name
        })),
        departmentsList: this.selectedProject.departments?.map((dep: any) => ({
          ...dep,
          departmentName: dep.departmentName
        })),
        start_date: this.selectedProject.start_date ? new Date(this.selectedProject.start_date) : null,
        end_date: this.selectedProject.end_date ? new Date(this.selectedProject.end_date) : null
      });

      console.log(this.projectForm.value);

      // Manage ManagerOptions
      this.ManagerOptions = [];
      if (this.selectedProject.manager) this.ManagerOptions.push(this.selectedProject.manager);
      if (this.selectedProject.owner) this.ManagerOptions.push(this.selectedProject.owner);

      this.selectedProject.employees?.forEach((emp: any) => {
        if (!this.ManagerOptions.find((el: any) => el.id === emp.id)) {
          this.ManagerOptions.push(emp);
        }
      });

      // Mark selected departments
      this.departments = this.departments.map((dep: any) => ({
        ...dep,
        selected: this.selectedProject.departments?.some((d: any) => d.id === dep.id) || false
      }));

      // Mark selected employees
      this.employees = this.employees.map((emp: any) => ({
        ...emp,
        selected: this.selectedProject.employees?.some((e: any) => e.id === emp.id) || false
      }));

    } else {
      this.projectForm.reset();
      this.ManagerOptions = [];
    }
  }
}

  ngOnInit(): void {

  }
  getDepartments() {
    this.departmentService.getDepartments(this.page,this.size,this.keyword).subscribe((val) => {
      this.departments = val.content||[];
    })
  }
  getEmployees() {
    this.employeeService.getEmployees().subscribe((val:any) => {
      this.employees = val.content.map((el:any)=>({...el,label:el?.firstName+" "+el?.lastName}))||[];
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
