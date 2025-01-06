import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { EventEmitter } from "@angular/core";
import { MatSnackBar } from '@angular/material/snack-bar';
import { filter, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { EmployeeService } from 'src/app/services/employee.service';


@Component({
  selector: 'app-chat-modal',
  templateUrl: './chat-modal.component.html',
  styleUrl: './chat-modal.component.css'
})
export class ChatModalComponent implements OnChanges{
  isLoading: boolean =false;
  page: number = 1;
  size: number = 19;
  total: number = 0;
  totalPages: number = 0;
  selectedDepartement: any;
  employees: Array<EmployeeModule> = [];
  @Input() visible!: boolean;
  @Output() closeM: EventEmitter<any> = new EventEmitter();


  constructor(private employeeService:EmployeeService,private snackBar: MatSnackBar){

  }
  ngOnChanges(changes: SimpleChanges): void {
   if(this.visible.valueOf()==true){
    this.getEmployees();
   }else{
    console.log("false")
   }
  }
closeModal(arg0: boolean) {
this.closeM.emit(arg0);
}
  getEmployees() {
  let notInEmp: Array<EmployeeModule>=[];
  this.isLoading=true
  this.employeeService.getEmployees(this.page-1, this.size).subscribe((value) => {
this.isLoading=false

    this.employees = value.content;
  },
  (error) => {
    console.log(error)
    this.snackBar.open("Failed to fetch employees. Please try again later.")
  },()=>{
    this.isLoading=false;
  }
);
return this.employees;
}

}
