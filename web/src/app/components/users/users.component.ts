import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'src/app/services/employee.service';
import { MessageService } from 'primeng/api';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  providers: [MessageService]
})
export class UsersComponent implements OnInit {

  employees:Array<Object>=[];
  userForm:FormGroup|any=[]
  isLoading:Boolean=false;
  isEdit:boolean=false;
  visible:boolean=false
  addUserVisible:boolean=false;
  size:number=10;
  page:number=0;
  total:number=0;
  totalPages:number=0;
  
  constructor(private employeeService:EmployeeService,private messageService: MessageService,private form:FormBuilder){
    this.userForm=this.form.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      hire_date:['',Validators.required]
      
    });
  }

  ngOnInit(): void {
    this.getEmployees()
  }
  
  showDialog():void{
    this.addUserVisible=true
  }  

  onAddUserClose(response:any){
    if(response=='Employee Added Succesfully'){
      this.getEmployees()
      this.messageService.add({severity:'success', summary: 'Success', detail: 'Employee Added Successfully'});
    }
    this.addUserVisible=false;
  }
  
  getEmployees(){
    this.isLoading=true
    this.employeeService.getEmployees(this.page, this.size).subscribe((value) => {
      this.employees = value.content;
      this.total=value.totalElements 
    },
    (error) => {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Failed to fetch employees. Please try again later.'});
    },()=>{
      this.isLoading=false;
    }
  );
  }
  
  onPageChange(event: any): void {
    this.page = event.page;
    this.size = event.rows;
    this.getEmployees();
  }
}
