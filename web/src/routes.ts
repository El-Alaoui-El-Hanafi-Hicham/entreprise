import {RouterModule, Routes} from '@angular/router'
import { LoginComponent } from './app/pages/login/login.component'
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app/app.component';
import { RegisterComponent } from './app/pages/register/register.component';
import { PageNotFoundComponentComponent } from './app/pages/page-not-found-component/page-not-found-component.component';
import { AuthGuard } from './app/guards/auth.guard';


const routerConfig:Routes = [
    {
        path:'',component: AppComponent,title:"Home",canActivate: [AuthGuard]
    }
    // ,
    // {
    //     path:'login',component:LoginComponent,title:"Login"
    // }
    ,
    {
        path:'register',component:RegisterComponent,title:"register"
    },
    { path: '**', component: PageNotFoundComponentComponent }
];
// @NgModule({
//     imports: [BrowserModule, RouterModule.forRoot(routerConfig)],
//     exports: [RouterModule],
//   })

    export default routerConfig;
