import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-land',
  templateUrl: './land.component.html',
  styleUrls: ['./land.component.css']
})
export class LandComponent implements OnInit {
  employees:Array<Object>=[];
  constructor(private employeeService:EmployeeService, private messageService: MessageService){

  }
  ngOnInit(): void {
    this.getEmployees()
  }
  getEmployees(){
    this.employeeService.getEmployees(1,34).subscribe((value) => {
      this.employees = value; 
    },
    (error) => {
      this.messageService.add({severity:'error', summary:'Error', detail:'Failed to fetch employees. Please try again later.'});
    });
  }
}
