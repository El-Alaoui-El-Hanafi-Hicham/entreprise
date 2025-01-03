import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationRoutingModule } from './authentication-routing.module';
import { AuthenticationComponent } from './authentication.component';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterComponent } from '../pages/register/register.component';
import { ResetPasswordComponent } from '../pages/reset-password/reset-password.component';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatCard, MatCardContent, MatCardFooter, MatCardHeader } from '@angular/material/card';


@NgModule({
  declarations: [
    AuthenticationComponent,
    LoginComponent,
    RegisterComponent,
    ResetPasswordComponent,

  ],
  imports: [
    CommonModule,
    AuthenticationRoutingModule,
    //  BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    MatIconModule,
    MatLabel,
    MatFormField,
    MatCardFooter,
    MatCardContent,
    MatCardHeader,
    MatCard,
    MatIcon,
    MatIconModule
  ]
})
export class AuthenticationModule { }
