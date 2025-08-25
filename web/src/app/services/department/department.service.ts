import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, OnInit, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class departmentService  {


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

     addDepartment(DepartmentName:String){
      let payload:object={
        "departmentName":DepartmentName,
      }
        return this.httpClient.post(this.baseUrl+"department", payload,{ headers: this.httpHeaders});
     }
     getDepartments(page:number,size:number,keyword:String,employee_ids:number[]=[]):Observable<any>{
      let params = new HttpParams()
            .set('pageNumber', String(page)) // Reassign the result
            .set('pageSize', String(size));  // Reassign the result
            if(keyword&&keyword.trim().length>0){
              params=params.set('keyword',String(keyword))
            }
              if(employee_ids&&employee_ids.length>0){
              params=params.set('employee_ids',employee_ids.join(','))
            }
        return this.httpClient.get(this.baseUrl+"department", { headers: this.httpHeaders,params});
     }

     addUserToDepartment(DepartmentId:number,userId:number){
      return this.httpClient.get(this.baseUrl+"department/employees/add?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
   }
   removeUserToDepartment(DepartmentId:number,userId:number){
    return this.httpClient.get(this.baseUrl+"department/employees/remove?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
 }
 editDepartment(id: number, value:Department ) {
  const payload= {departmentName:value['departmentName']}
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
  // Don't set Content-Type manually for FormData - let the browser set it with boundary
  const HttpUploadOptions = {
    headers: new HttpHeaders({ "Accept":"application/json",
        "Authorization":"Bearer "+this.jwtKey })
  }
  const formData = new FormData();
  if(file) formData.append('file', file, file.name);
  return this.httpClient.post(this.baseUrl+"department/bulk",formData, HttpUploadOptions)
}
    }
    interface Department {
      id:number;
      departmentName: String;
      manager: Object|null
      employees: Array<EmployeeModule>
      // Add other properties if needed
    }
