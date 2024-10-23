import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { PageNotFoundComponentComponent } from './pages/page-not-found-component/page-not-found-component.component';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './pages/home/home.component';
import { LandComponent } from './components/land/land.component';
import { UsersComponent } from './components/users/users.component';
import { DepartementsComponent } from './components/departements/departements.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';


const routes: Routes = [   {
  path:'',component: HomeComponent,title:"Home",canActivate: [AuthGuard],
  children: [
    {
      path: '', // child route path
      component: LandComponent, // child route component that the router renders
    },
    {
      path: 'employees', // child route path
      component: UsersComponent, // child route component that the router renders
    },
    {
      path: 'departements', // child route path
      component: DepartementsComponent, // child route component that the router renders
    },
  ]
},
{
  path:'reset-password/:id',component:ResetPasswordComponent,title:"Reset Password"
},
{
  path:'login',component:LoginComponent,title:"Login"
}
,
{
  path:'register',component:RegisterComponent,title:"register"
},
{ path: '**', component: PageNotFoundComponentComponent  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
