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
  getEmployees(page:number,size:number){
  let params = new HttpParams()
    .set('pageNumber', String(page)) // Reassign the result
    .set('pageSize', String(size));  // Reassign the result

  // Log params to see the output
  console.log(params.toString());

  // Make the GET request with headers and params
  return this.http.get<any>(this.BASE_URL+"", { headers: this.httpHeaders, params });
  }
  addEmployee(employee :EmployeeModule){
    return this.http.post<any>(this.BASE_URL+"/employee",employee,{headers:this.httpHeaders})
  }
}
