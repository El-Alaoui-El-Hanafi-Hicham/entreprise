import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { departmentService } from 'src/app/services/department/department.service';

@Component({
  selector: 'app-department-modal',
  templateUrl: './department-modal.component.html',
  styleUrls: ['./department-modal.component.css']
})

export class DepartmentModalComponent {
  @Input() visible!: boolean;
  @Output() closeM: EventEmitter<any> = new EventEmitter();
  DepartmentName:String="";
  messages:Array<any>=[]

  
  constructor(private departmentService : departmentService, private snackBar: MatSnackBar){

  }
  closeModal(s:boolean){
    console.log
    this.closeM.emit(s);
  }
  addDepartment(){
    this.departmentService.addDepartment(this.DepartmentName).subscribe((value:any)=>
      {
        if(Boolean(value['Status'])){
          this.snackBar.open(value['message'])
          this.DepartmentName=''
          this.closeModal(true);
        }else{
          this.snackBar.open(value['message'])
          
        }
        
   
      });
  }
}
