import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, OnInit, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {



  jwtKey: String | null;

  private JwtKey: String | boolean = "";
  private httpHeaders: HttpHeaders = new HttpHeaders({
    "Content-Type": "application/json",
    "Accept": "application/json",
  });
  private authSecretKey = 'key';
  baseUrl = "http://localhost:8080/api/project"
  constructor(private httpClient: HttpClient, private store: Store<{ count: number }>) {
    this.jwtKey = localStorage.getItem('key');

    this.httpHeaders = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": "Bearer " + this.jwtKey
    });
  }

  addProject(project: any) {
    let payload = {
      department_ids: project?.departmentsList?.map((el: any) => el.id)||[],
      employee_ids: project?.employeesList?.map((el: any) => el.id)||[],
      owner_id: project?.owner.id,
      manager_id: project?.manager.id,
      "project_name": project.project_name,
      "description": project.description,
      "start_date": project.start_date,
      "end_date": project.end_date,
      "status": project.status||'Not Started',
    }
    return this.httpClient.post(this.baseUrl , payload, { headers: this.httpHeaders });
  }


  getProjects(page: number=0, size: number=10, keyword:String,department_ids:number[]=[],employee_ids:number[]=[],statuses:string[]=[],rangeDates:Date[]=[]): Observable<any> {
      const httpUploadHeaders = new HttpHeaders({
    "Content-Type":"application/json",
    "Authorization":"Bearer "+this.jwtKey
  });
    let params = new HttpParams()
      .set('pageNumber', String(page)) // Reassign the result
      .set('pageSize', String(size));  // Reassign the result
      console.log(rangeDates);
      let payload:{
        keyword:string,
        department_ids:number[],
        employee_ids:number[],
        statuses?:string[],
        startDate?:String,
        endDate?:String
      }
      ={
        keyword:"",
        department_ids:[],
        employee_ids:[],
        statuses:[],
        startDate:undefined,
        endDate:undefined
      }
      if(keyword&&keyword.trim().length>0){
        payload.keyword=String(keyword)
      }
        if(employee_ids&&employee_ids.length>0){
              payload.employee_ids=employee_ids
            }
              if(department_ids&&department_ids.length>0){
              payload.department_ids=department_ids
            }
             if(statuses&&statuses.length>0){
              payload.statuses=statuses
            }
             if(rangeDates&&rangeDates[0]){
              payload.startDate=rangeDates[0].toISOString().slice(0, 19).replace('T', ' ').split(" ")[0];
            }
              if(rangeDates&&rangeDates[1]){
                payload.endDate=rangeDates[1].toISOString().slice(0, 19).replace('T', ' ').split(" ")[0];
              }
    return this.httpClient.post(this.baseUrl+"/search" , payload,{ headers: httpUploadHeaders, params });
  }
 getProjectEmployees(id:number,filter:string): Observable<any> {
      const httpUploadHeaders = new HttpHeaders({
    "Content-Type":"application/json",
    "Authorization":"Bearer "+this.jwtKey
  });
    let params = new HttpParams()
      .set('id', String(id)) // Reassign the result
      .set('filter', String(filter));  // Reassign the result
    return this.httpClient.get(this.baseUrl , { headers: httpUploadHeaders, params });
  }

  deleteProject(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + `/${id}`, { headers: this.httpHeaders });
  }
}
