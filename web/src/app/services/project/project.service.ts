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


  getProjects(page: number, size: number): Observable<any> {
      const httpUploadHeaders = new HttpHeaders({
    "Content-Type":"application/json",
    "Authorization":"Bearer "+this.jwtKey
  });
    let params = new HttpParams()
      .set('pageNumber', String(page)) // Reassign the result
      .set('pageSize', String(size));  // Reassign the result
    return this.httpClient.get(this.baseUrl , { headers: httpUploadHeaders, params });
  }
}
