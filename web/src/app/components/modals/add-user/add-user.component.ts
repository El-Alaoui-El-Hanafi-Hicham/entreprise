import { Component, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { RegisterModule } from 'src/app/modules/register/register.module';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
  
})
export class AddUserComponent {
  user:EmployeeModule={
    id:undefined,
    email: '',
    first_name: '',
    last_name: '',
    hire_date: undefined,
    job_title: undefined,
    phone_number: undefined,
    department_id: undefined
  };
  date = new FormControl(new Date());

  constructor( public dialogRef: MatDialogRef<AddUserComponent>,@Inject(MAT_DIALOG_DATA) public data:any| undefined,private employeeService:EmployeeService) {
    this.user.email=data.userForm.email
    this.user.first_name=data.userForm.first_name
    this.user.last_name=data.userForm.last_name
    this.user.hire_date=data.userForm.hire_date
    this.user.job_title=data.userForm.job_title
  }


  onNoClick(): void {
    this.dialogRef.close("Dialog Closed with Data"); // Pass the data you want to return here
  }
  addEmployee(){
this.employeeService.addEmployee(this.user).subscribe((value)=>{ console.log(value); this.dialogRef.close(value)});
  }
}
