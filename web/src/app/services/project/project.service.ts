import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class ProjectService  {


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

     addProject(project:object){

        return this.httpClient.post(this.baseUrl+"project", project,{ headers: this.httpHeaders});
     }
  }
