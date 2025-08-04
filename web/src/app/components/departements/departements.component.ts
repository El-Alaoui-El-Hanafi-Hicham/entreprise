import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MenuItem, MessageService } from 'primeng/api';
import { FileSelectEvent, FileUploadEvent } from 'primeng/fileupload';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { departmentService } from 'src/app/services/department/department.service';

@Component({
  selector: 'app-departements',
  templateUrl: './departements.component.html',
  styleUrls: ['./departements.component.css'],
  providers: [MessageService]
})
export class DepartementsComponent implements OnInit {



uploadedFiles: any;

items: MenuItem[]=[
  {id:'1',label:"Upload File",title:"Upload File",command:()=>this.openUploadFile(),icon:'pi pi-upload'},
  {id:'2',label:"Download Template",title:"Upload File",command:()=>this.downloadDepartmentTemplate(),iconClass:"pi-address-book",icon:"pi pi-download"}
];
IsUploadModalopen:boolean=false
  file!:File|null;
  ngOnInit(): void {
    this.getDepartments()
    // this.wsService.connectSocket("JELLS");
    // this.wsService.subscribeToNotification();
  }
  openDepartmentModal:boolean=false
  departments:any=[]
  isModalOpen:boolean=false;
  isEdit:boolean=false;
  selectedDepartement!:Department
  isSetManager:boolean=false
constructor(private departmentService:departmentService,private messageService:MessageService,){

}
update(department: Department) {
}
openUploadFile(){
  this.IsUploadModalopen=true;
  // console.log("It should be open by now!!")
}
closeUploadFile(refresh:boolean){
  this.removeFile();
  this.IsUploadModalopen=false;
}

downloadDepartmentTemplate(): void {
  const link = document.createElement('a');
  link.href = 'assets/Empty.csv'; // Path to the file in assets
  link.download = 'departmentTemplate.csv'; // Set the desired file name for download
  document.body.appendChild(link); // Append to the body
  link.click(); // Programmatically trigger a click to download
  document.body.removeChild(link); // Remove the link after download

}
UploadFile() {
this.departmentService.uploadFileToBE(this.file).subscribe((value: any)=> {
  if(Boolean(value['Status'])){
    this.messageService.add({severity:'success', summary: 'Success', detail: value['message']});
    this.closeUploadFile(true);
    this.getDepartments();
    this.file=null
  }else{
    this.messageService.add({severity:'error', summary: 'Error', detail: value['message']});

  }
})
}
  removeFile() {
    this.file=null;
    }
delete(department: Department) {
  this.departmentService.deleteDepartment(department.id).subscribe((value: any)=> {
    if(Boolean(value['Status'])){
      this.messageService.add({severity:'success', summary: 'Success', detail: value['message']});
      this.departments= this.departments.filter((el:Department)=> el.id!=department.id);
    }else{
      this.messageService.add({severity:'error', summary: 'Error', detail: value['message']});

    }
  })
}
onUpload($event: FileSelectEvent) {
console.log($event.currentFiles[0])
this.file=$event.currentFiles[0];
console.log(this.file)
console.log($event.currentFiles)
}
openEditModal(selectedDepartement: Department) {
  this.isEdit=true;
  this.isModalOpen=true;
  if(this.isEdit){
    this.selectedDepartement=selectedDepartement;
  }
  }
  openIsSetManagerModal(selectedDepartement: Department) {
    this.isEdit=true;
    this.isModalOpen=true;
    this.isSetManager=true;
    if(this.isEdit){
      this.selectedDepartement=selectedDepartement;
    }
    }
// Dropdown menu for each department
  departmentDropDown(department: Department): MenuItem[] {

    return [
      {
        label: 'Add Employees',
        command: () => this.update(department)
      },
      {
        label: 'Delete',
        command: () => this.delete(department)
      },
      {
        label: 'Set Manager',
        command: () => this.setManager(department) // Passing the specific department to set manager
      },
      { separator: true },
      {
        label: 'Upload',
        routerLink: ['/fileupload'] // Example route for file upload
      }
    ];
  }

  getMenuItems(department: Department): MenuItem[] {
    return [
      {
        label: 'Edit',
        icon: 'pi pi-pencil',
        command: () => this.openEditModal(department)
      },
      {
        label: 'Add Employee',
        icon: 'pi pi-user-plus',
        command: () => this.openEditModal(department)
      },
      {
        label: 'Set Manager',
        icon: 'pi pi-user',
        command: () => this.setManager(department)
      },
      {
        separator: true
      },
      {
        label: 'Delete',
        icon: 'pi pi-trash',
        command: () => this.delete(department)
      }
    ];
  }
getDepartments(){
  this.departments=[];
  this.departmentService.getDepartments().subscribe((val)=>{
this.departments=val
  })
}



  setManager(department:Department) {
    this.openIsSetManagerModal(department)
}
getArrayLength(arr:Array<any>){
  return String(arr.length).toString();
}
openDepartmentDialog(){
  this.openDepartmentModal=true;
}

openAddDepartmentModal(){
  this.openDepartmentModal=true;
}
closeDepartmentDialog(s:boolean){
  this.openDepartmentModal=false;
  if(s){
this.getDepartments();
  }
}
closeAddEditDep(s:boolean){

  this.isEdit=false;
  this.isModalOpen=false;
  this.isSetManager=false;
  if(s){
this.getDepartments();
  }
}
}
interface Department {
  id:number;
  department_name: string;
  manager: EmployeeModule|null
  employees: Array<EmployeeModule>
  // Add other properties if needed
}
