import { Component } from '@angular/core';
import { RegisterService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],


})
export class RegisterComponent {
  hide = true;
  email:String="";
  password:String="";
  firstName:String="";
  lastName:String="";
  hire_date:Date|undefined;
constructor(private registerService:RegisterService){

}
  register(){
  this.registerService.register({email:this.email,password:this.password,firstName:this.firstName,lastName:this.lastName,hire_date:this.hire_date})
  }
}
