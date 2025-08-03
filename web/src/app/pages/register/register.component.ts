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
  first_name:String="";
  last_name:String="";
  hire_date:Date|undefined;
constructor(private registerService:RegisterService){

}
  register(){
  this.registerService.register({email:this.email,password:this.password,first_name:this.first_name,last_name:this.last_name,hire_date:this.hire_date})
  }
}
