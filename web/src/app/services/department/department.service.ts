import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class departmentService implements OnInit {

 
    jwtKey:String|null;

    private JwtKey:String| boolean ="";
    private httpHeaders: HttpHeaders = new HttpHeaders({
      "Content-Type":"application/json",
      "Accept":"application/json",
  });
    private authSecretKey = 'key';
  baseUrl="http://localhost:8080/api/"
    constructor(private httpClient:HttpClient,private store: Store<{count:number}> ) {
this.jwtKey=localStorage.getItem('key');

      this.httpHeaders= new HttpHeaders({
        "Content-Type":"application/json",
        "Accept":"application/json",
        "Authorization":"Bearer "+this.jwtKey
      });
     }
  ngOnInit(): void {
    // console.log("THIS>>>> is")
    // console.log(this.store)
  }
     addDepartment(DepartmentName:String){
      let payload:object={
        "department_name":DepartmentName,
      }
        return this.httpClient.post(this.baseUrl+"department", payload,{ headers: this.httpHeaders});
     }
     getDepartments(){
        return this.httpClient.get(this.baseUrl+"department", { headers: this.httpHeaders});
     }

     addUserToDepartment(DepartmentId:number,userId:number){
      return this.httpClient.get(this.baseUrl+"department/employees/add?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
   }
   removeUserToDepartment(DepartmentId:number,userId:number){
    return this.httpClient.get(this.baseUrl+"department/employees/remove?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
 }
 editDepartment(id: number, value:Department ) {
  const payload= {department_name:value['department_name']}
  return this.httpClient.put(this.baseUrl+"department?dep_id=" +id,payload, { headers: this.httpHeaders});

}
setManager(id: number, value:number ) {
  return this.httpClient.get(this.baseUrl+"department/setManager?id=" +id+"&dep_id="+value, { headers: this.httpHeaders});

}
removeManager(id: number) {
  return this.httpClient.get(this.baseUrl+"department/removeManager?id=" +id, { headers: this.httpHeaders});

}
deleteDepartment(id: number) {
  return this.httpClient.delete(this.baseUrl+"department?id=" +id, { headers: this.httpHeaders});

}
uploadFileToBE(file:File|null){
  let n =this.httpHeaders.set("Content-Type","multipart/form-data");
  const HttpUploadOptions = {
    headers: new HttpHeaders({ "Content-Type": "multipart/form-data","Accept":"application/json",
        "Authorization":"Bearer "+this.jwtKey })
  }
  console.log(n)
  const formData = new FormData();
  if(file) formData.append('file', file, file.name);
  console.log(formData)
  return this.httpClient.post(this.baseUrl+"department/bulk",formData)
}


    }
    interface Department {
      id:number;
      department_name: String;
      manager: Object|null
      employees: Array<EmployeeModule>
      // Add other properties if needed
    }