import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { RegisterModule } from 'src/app/modules/register/register.module';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']

})
export class AddUserComponent {
  @Input() visible: boolean = false;
  @Input() userData: any;
  @Output() closeModal = new EventEmitter<any>();

  user:EmployeeModule={
    id:undefined,
    email: '',
    firstName: '',
    lastName: '',
    hire_date: undefined,
    job_title: undefined,
    phone_number: undefined,
    department_id: undefined
  };
  date = new FormControl(new Date());
  isLoading: boolean = false;

  constructor(private employeeService:EmployeeService) {

  }

  ngOnInit() {
    if (this.userData) {
      this.user.email = this.userData.userForm?.email || '';
      this.user.firstName = this.userData.userForm?.firstName || '';
      this.user.lastName = this.userData.userForm?.lastName || '';
      this.user.hire_date = this.userData.userForm?.hire_date;
      this.user.job_title = this.userData.userForm?.job_title;
    }
  }

  onNoClick(): void {
    this.closeModal.emit("Dialog Closed with Data");
  }

  addEmployee(){
    this.isLoading = true;
    this.employeeService.addEmployee(this.user).subscribe({
      next: (response:any) => {
        console.log("Employee added successfully:", response.message);
        this.closeModal.emit("Employee Added Succesfully");
        this.isLoading = false;
      },
      error: (error:any) => {
        console.error("Error adding employee:", error.message );
        this.closeModal.emit("Error Adding Employee");
      }
    });
  }
}
