import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MessageService } from 'primeng/api';
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

  
  constructor(private departmentService : departmentService, private messageService: MessageService){

  }
  closeModal(s:boolean){
    console.log
    this.closeM.emit(s);
  }
  addDepartment(){
    this.departmentService.addDepartment(this.DepartmentName).subscribe((value:any)=>
      {
        if(Boolean(value['Status'])){
          this.messageService.add({severity:'success', summary:'Success', detail:value['message']});
          this.DepartmentName=''
          this.closeModal(true);
        }else{
          this.messageService.add({severity:'error', summary:'Error', detail:value['message']});
        }
        
   
      });
  }
}
