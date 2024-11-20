import { ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Obj } from '@popperjs/core';
import { filter, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { departmentService } from 'src/app/services/department/department.service';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-departements-users',
  templateUrl: './departements-users.component.html',
  styleUrl: './departements-users.component.css'
})

export class DepartementsUsersComponent implements OnInit, OnChanges{



  @Input() visible !:boolean
  @Input() isEdit !:boolean
  @Input() isSetManager !:boolean
  newDep!: FormGroup;
  isLoading: Boolean=false;
  isEditChecked: Boolean= false;
  employees:Array<EmployeeModule>=[];
  size:number=19;
  page:number=1;
  total:number=0;
  totalPages:number=0;
  @Input() selectedDepartement !:Department;
  @Output() closeAddEdit: EventEmitter<any> = new EventEmitter();
  constructor(private fb : FormBuilder, private cdRef: ChangeDetectorRef,private employeeService:EmployeeService,private snackBar: MatSnackBar,private departementService: departmentService){
    
  }

showDialog() {
throw new Error('Method not implemented.');
}
closeModal(s:boolean){

  this.closeAddEdit.emit(s);
}
ngOnInit() {
  // Initialize the form with default values if needed
  this.newDep = this.fb.group({
    id: ['', [Validators.required, Validators.minLength(4)]],
    department_name: ['', [Validators.required, Validators.minLength(4)]],
    manager: ['', [Validators.required]],
    employees: [ this.fb.array([]), [Validators.required]]
  });
}
ngOnChanges(changes: SimpleChanges) {
  if(changes['visible']&&this.visible){
    this.getEmployees();
    this.newDep.patchValue({
      id: this.selectedDepartement.id,
      department_name: this.selectedDepartement.department_name,
      manager: this.selectedDepartement.manager,
      employees: this.selectedDepartement.employees
    });
  }
  if (changes['selectedDepartement']) {
  }
  this.cdRef.markForCheck();

}
getEmployees() {
  let notInEmp: Array<EmployeeModule>=[];
  this.isLoading=true
  this.employeeService.getEmployees(this.page-1, this.size).pipe(
    map(data => data.content),  // Assuming `data.content` holds the list of employees
    filter(employees => {
      // Filter out employees already in members
      return employees.filter((employee:EmployeeModule) => 
        !this.selectedDepartement.employees.map((el:EmployeeModule) => el.id).includes(employee.id)
      );
    })
  ).subscribe((value) => {
    this.employees = value.filter((employee:EmployeeModule) => {
      // Filter out employees already in members
   return     !this.selectedDepartement.employees.map((el:EmployeeModule) => el.id).includes(employee.id)
  });
 
  },
  (error) => {
    this.snackBar.open("Failed to fetch employees. Please try again later.")
  },()=>{
    this.isLoading=false;
  }
);
return this.employees;
}
setManager(id: number| undefined) {
  if(id){

    this.departementService.setManager(id,this.selectedDepartement.id).subscribe((value:any)=>{
      if(Boolean(value['Status'])){
        this.snackBar.open(value['message'])
  
        this.closeModal(true);
      }else{
        this.snackBar.open(value['message'])
        
      }});
    }
  }
get members() :Array<EmployeeModule>{
  return this.newDep.get('employees')?.value;
}
get membersLength(): number {
  return (this.newDep.get('employees')?.value).length;
}

addMember(UserId: number|undefined) {
  if(UserId){

    this.departementService.addUserToDepartment(this.selectedDepartement.id,UserId).subscribe((value:any)=>{
      if(Boolean(value['Status'])){
        let employee:EmployeeModule | undefined =this.employees.find(el=>el.id==UserId);
     this.newDep.get('employees')?.value.push(employee);
        this.employees =employee ?  this.employees.filter(el=>el.id!=employee.id) :this.employees
    this.snackBar.open(value['message'])
      }
    })
  }
}
removeMember(UserId: number|undefined) {
  if(UserId){

    this.departementService.removeUserToDepartment(this.selectedDepartement.id,UserId).subscribe((value: any)=>{
      if(Boolean(value['Status'])){
        let employee:EmployeeModule | undefined =this.selectedDepartement.employees.find(el=>el.id==UserId);
         employee &&  this.newDep.get('employees')?.setValue(this.newDep.get('employees')?.value.filter((el:EmployeeModule)=>el.id!=employee.id))
        employee && this.employees.push(employee);
    this.snackBar.open(value['message'])
      }
    })
  }
}
editDepartment() {
  this.departementService.editDepartment(this.selectedDepartement.id,this.newDep.value).subscribe((value: any)=>{
    if(Boolean(value['status'])){
    this.snackBar.open(value['message'])
     this.closeModal(true);
    }
  })
}
removeManager() {
  this.departementService.removeManager(this.selectedDepartement.id).subscribe((value: any)=>{
    if(Boolean(value['Status'])){
      this.snackBar.open(value['message'])

      this.closeModal(true);
    }else{
      this.snackBar.open(value['message'])
      
    }
  })
}

}

interface Department {
  id:number;
  department_name: string;
  manager: EmployeeModule|null
  employees: Array<EmployeeModule>
  // Add other properties if needed
}
interface Response {
  success:boolean;
  message: string;
}