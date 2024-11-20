import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { RegisterService } from '../../services/auth/auth.service';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  userForm :any ;
    constructor(private formBuilder: FormBuilder, private authService:RegisterService,private  snackBar: MatSnackBar, private router: Router){


  }
  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],

    });
  }
login(){
  let snack = this.snackBar;
let router=this.router;
  // let payload = {email:this.userForm.get('email'),password:this.userForm.get('password')}
  this.authService.login(this.userForm?.value).subscribe({
    next(value) {
      if(value.JwtKey!=null && value.Status){
        localStorage.setItem("key",value.JwtKey);
        snack.open(value.Message)
        router.navigate(['home']);
        return true;
      }else{
        snack.open(value.Message)
        return false;
      }

    },
    error(err) {
      throw new Error(err);

   },
  })


}
formValid(){
  return !this.userForm?.valid
}

}
