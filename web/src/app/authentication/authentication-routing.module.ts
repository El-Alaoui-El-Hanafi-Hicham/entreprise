import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from './authentication.component';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterComponent } from '../pages/register/register.component';

const routes: Routes = [{ path: '', component: AuthenticationComponent }
  ,
  {
    path:'login',component:LoginComponent,title:"Login",
  }
  ,
  {
    path:'register',component:RegisterComponent,title:"register"
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
