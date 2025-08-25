import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EmployeeModule } from '../modules/employeeModule/employee/employee.module';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
jwtKey:String|null;
employees:Array<Object>=[];
private httpHeaders: HttpHeaders| any;
private  BASE_URL:String ="http://localhost:8080/api/employees"
  constructor(private http:HttpClient) {
this.jwtKey=localStorage.getItem('key');
this.httpHeaders= new HttpHeaders({
  "Content-Type":"application/json",
  "Accept":"application/json",
  "Authorization":"Bearer "+this.jwtKey
});
   }
   me(){
    return this.http.get<any>(this.BASE_URL+"/me", { headers: this.httpHeaders });
    }
  getEmployees(page:number=0,size:number=100,keyword:String=""){
  let params = new HttpParams()
    .set('pageNumber', String(page)) // Reassign the result
    .set('pageSize', String(size));  // Reassign the result
    if(keyword&&keyword.trim().length>0){
        params=params.set('keyword',String(keyword))
      }
  // Make the GET request with headers and params
  return this.http.get<any>(this.BASE_URL+"", { headers: this.httpHeaders, params });
  }
  addEmployee(employee :EmployeeModule){
    return this.http.post<any>(this.BASE_URL+"/employee",employee,{headers:this.httpHeaders})
  }
  removeUsers(employees:Array<Object>){
    return this.http.post<any>(this.BASE_URL+"/bDEL",employees.map((el:any)=>{
      return el.id;
    }),{headers:this.httpHeaders})
  }
  updateEmployee(employee:EmployeeModule){
    return this.http.put<any>(this.BASE_URL+"/employee",employee,{headers:this.httpHeaders})
  }
  getEmployeeById(id:number){
    return this.http.get<any>(this.BASE_URL+"/employee/"+id,{headers:this.httpHeaders})
  }
  uploadFileToBE(file: File | null) {
  // Don't set Content-Type manually for FormData - let the browser set it with boundary
  const httpUploadHeaders = new HttpHeaders({
    "Accept":"application/json",
    "Authorization":"Bearer "+this.jwtKey
  });
  const formData = new FormData();
  if (file) {
    formData.append('file', file, file.name);
  }
  return this.http.post(this.BASE_URL+"/bulk",formData, { headers: httpUploadHeaders })
  }

}
