import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import * as SockJs from 'sockjs-client';
import * as StompJs from 'stompjs';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})

export class AppComponent {
  title = 'web';
  private socket: any=null;
  private apiKey:string="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxZWxhbGFvdWllbGhhbmFmaWhpY2hhbUBnbWFpbC5jb20iLCJpYXQiOjE3MjkxMDkwODMsImV4cCI6MzYwMDAwMDAwMDAwMDB9.TgzFNLQVXgxhqpcqAsbMYq9UV3EPLApogQrdgZ0mkFk";
  private UserSubscription:any=null;
  constructor() { 
    
let ws= new SockJs('http://localhost:8080/ws')
this.socket=StompJs.over(ws)
this.socket.connect({'Authorization:':"Bearer "+this.apiKey},()=>{
  console.log("CONNECTING TO WEBSOCKET")
  this.UserSubscription=this.socket.subscribe("/user",(e:any)=>{
    console.log("SUBSCRIIIIIIIIIIIBED")
    console.log(e.body)
  });
});
  }

  ngOnInit(): void {
    // this.websocketService.connectSocket("HELLo");
   }
  

 
}
