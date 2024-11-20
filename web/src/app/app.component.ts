import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import * as SockJs from 'sockjs-client';
import * as StompJs from 'stompjs';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import { EmployeeModule } from './modules/employeeModule/employee/employee.module';
import { EmployeeService } from './services/employee.service';
import { loadData, setUser } from './stores/user/user.actions';
import { ActiveUserState } from './stores/user/user.reducer';
import {selectActiveUserId, selectLoading} from "./stores/user/user.selectors";
import { AppState } from './stores/app.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})

export class AppComponent implements OnInit{
  user$!:Observable<ActiveUserState>;
  title = 'web';
  private socket: any=null;
  private apiKey:string="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxZWxhbGFvdWllbGhhbmFmaWhpY2hhbUBnbWFpbC5jb20iLCJpYXQiOjE3MjkxMDkwODMsImV4cCI6MzYwMDAwMDAwMDAwMDB9.TgzFNLQVXgxhqpcqAsbMYq9UV3EPLApogQrdgZ0mkFk";
  private UserSubscription:any=null;

  constructor(private employeeService : EmployeeService,private store:Store<AppState>) {

let ws= new SockJs('http://localhost:8080/ws')
this.socket=StompJs.over(ws)
this.socket.connect({'Authorization:':"Bearer "+this.apiKey},()=>{
  this.UserSubscription=this.socket.subscribe("/user",(e:any)=>{
  });
  this.UserSubscription=this.socket.subscribe("/user/2/queue/messages",(e:any)=>{
    
  });
});
  }

  ngOnInit(): void {

          // this.employeeService.me().subscribe(val=>{
          // })
          this.store.dispatch(loadData())
   }



}
