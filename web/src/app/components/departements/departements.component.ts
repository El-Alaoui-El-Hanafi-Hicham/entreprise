import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MenuItem } from 'primeng/api';
import { FileSelectEvent, FileUploadEvent } from 'primeng/fileupload';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { departmentService } from 'src/app/services/department/department.service';

@Component({
  selector: 'app-departements',
  templateUrl: './departements.component.html',
  styleUrls: ['./departements.component.css']
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
constructor(private departmentService:departmentService,private snackBar:MatSnackBar,){

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
this.departmentService.uploadFileToBE(this.file).subscribe((value: any)=>{
  if(Boolean(value['Status'])){
    this.snackBar.open(value['message'])
    this.closeUploadFile(true);
    this.getDepartments();
    this.file=null
  }else{
    this.snackBar.open(value['message'])

  }
})
}
  removeFile() {
    this.file=null;
    }
delete(department: Department) {
  this.departmentService.deleteDepartment(department.id).subscribe((value: any)=>{
    if(Boolean(value['Status'])){
      this.snackBar.open(value['message'])
      this.departments= this.departments.filter((el:Department)=>el.id!=department.id);
    }else{
      this.snackBar.open(value['message'])

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
