import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Obj } from '@popperjs/core';
import { EmployeeService } from 'src/app/services/employee.service';
import { AddUserComponent } from '../modals/add-user/add-user.component';

import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogClose,
} from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit, AfterViewInit {

  employees:Array<Object>=[];
  userForm:FormGroup|any=[]
  isLoading:Boolean=false;
  isEdit:boolean=false;
  visible:boolean=false
  size:number=19;
  page:number=1;
  total:number=0;
  totalPages:number=0;
  displayedColumns: string[] = ['first_name', 'last_name',"score"];
  dataSource =    new MatTableDataSource<Object>(this.employees);
  
  constructor(private employeeService:EmployeeService,private  snackBar: MatSnackBar,public dialog: MatDialog,private form:FormBuilder){
    this.userForm=this.form.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      hire_date:['',Validators.required]
      
    });
  }
  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;


  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.getEmployees()
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(AddUserComponent, {data: {isEdit: this.isEdit, userForm: this.userForm}});
    dialogRef.afterClosed().subscribe(result => {
      if(result=='Employee Added Succesfully'){
this.getEmployees();
      }

    });
  }
  showDialog():void{
    this.visible=true
   }
  getEmployees(){
    this.isLoading=true
    this.employeeService.getEmployees(this.page-1, this.size).subscribe((value) => {
      this.employees = value.content;
      this.total=value.totalElements 
      this.dataSource.data=this.employees
      
    },
    (error) => {
      this.snackBar.open("Failed to fetch employees. Please try again later.")
    },()=>{
      this.isLoading=false;
    }
  );
  }
  onPageChange(event: any): void {
    this.page = event.pageIndex + 1; // Adjust to 1-based index
    this.size = event.pageSize;
    this.getEmployees();
  }
}
