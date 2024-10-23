import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';

@Injectable({
  providedIn: 'root'
})
export class departmentService {
 
    jwtKey:String|null;

    private JwtKey:String| boolean ="";
    private httpHeaders: HttpHeaders = new HttpHeaders({
      "Content-Type":"application/json",
      "Accept":"application/json",
  });
    private authSecretKey = 'key';
  baseUrl="http://localhost:8080/api/"
    constructor(private httpClient:HttpClient) {
this.jwtKey=localStorage.getItem('key');

      this.httpHeaders= new HttpHeaders({
        "Content-Type":"application/json",
        "Accept":"application/json",
        "Authorization":"Bearer "+this.jwtKey
      });
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

     addUserToDepartment(DepartmentId:BigInteger,userId:BigInteger){
      return this.httpClient.get(this.baseUrl+"department/employees/add?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
   }
   removeUserToDepartment(DepartmentId:BigInteger,userId:BigInteger){
    return this.httpClient.get(this.baseUrl+"department/employees/remove?id=" +userId+"&dep_id="+DepartmentId, { headers: this.httpHeaders});
 }
 editDepartment(id: BigInteger, value:Department ) {
  const payload= {department_name:value['department_name']}
  return this.httpClient.put(this.baseUrl+"department?dep_id=" +id,payload, { headers: this.httpHeaders});

}
setManager(id: BigInteger, value:BigInteger ) {
  return this.httpClient.get(this.baseUrl+"department/setManager?id=" +id+"&dep_id="+value, { headers: this.httpHeaders});

}
removeManager(id: BigInteger) {
  return this.httpClient.get(this.baseUrl+"department/removeManager?id=" +id, { headers: this.httpHeaders});

}
deleteDepartment(id: BigInteger) {
  return this.httpClient.delete(this.baseUrl+"department?id=" +id, { headers: this.httpHeaders});

}
    }
    interface Department {
      id:BigInteger;
      department_name: String;
      manager: Object|null
      employees: Array<EmployeeModule>
      // Add other properties if needed
    }