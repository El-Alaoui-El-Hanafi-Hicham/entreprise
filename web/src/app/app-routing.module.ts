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
import { ChatComponent } from './components/chat/chat.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { ProjectsComponent } from './components/projects/projects.component';


const routes: Routes = [   {
  path:'',component: HomeComponent,title:"Home",canActivate: [AuthGuard],
  children: [
    {
      path: '', // child route path
      component: LandComponent, // child route component that the router renders
      canActivateChild: [AuthGuard]
    },
    {
      path: 'employees', // child route path
      component: UsersComponent, // child route component that the router renders
      loadChildren: () => import('./components/users/users.module').then(m => m.EmployeesModule)
    },
    {
      path: 'departements', // child route path
      component: DepartementsComponent, // child route component that the router renders
      loadChildren: () => import('./components/departements/departements.module').then(m => m.DepartementsModule)
    },
    {
      path: 'chats', // child route path
      component: ChatComponent, // child route component that the router renders
      loadChildren: () => import('./components/chat/chat.module').then(m => m.ChatModule)
    },
     {
      path: 'tasks', // child route path
      component: TasksComponent, // child route component that the router renders
      loadChildren: () => import('./components/tasks/tasks.module').then(m => m.TasksModule)
    },
       {
      path: 'projects', // child route path
      component:ProjectsComponent, // child route component that the router renders
      loadChildren: () => import('./components/projects/projects.module').then(m => m.ProjectsModule)
    },
  ]
},
{
  path:'reset-password/:id',component:ResetPasswordComponent,title:"Reset Password"
},

  { path: 'authentication', loadChildren: () => import('./authentication/authentication.module').then(m => m.AuthenticationModule) },
{ path: '**', component: PageNotFoundComponentComponent  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
