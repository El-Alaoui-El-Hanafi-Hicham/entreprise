import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { RegisterService } from '../../services/auth/auth.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { loadData } from 'src/app/stores/user/user.actions';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/stores/app.state';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  userForm :any ;
    constructor(private formBuilder: FormBuilder, private authService:RegisterService, private router: Router,
      private store:Store<AppState>){


  }
  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],

    });
  }
login(){
let router=this.router;
let store = this.store;
  // let payload = {email:this.userForm.get('email'),password:this.userForm.get('password')}
  this.authService.login(this.userForm?.value).subscribe({
    next(value) {
      if(value.JwtKey!=null && value.Status){
        localStorage.setItem("key",value.JwtKey);
    store.dispatch(loadData()); // Load necessary data
        router.navigate(['']);
        return true;
      }else{
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
