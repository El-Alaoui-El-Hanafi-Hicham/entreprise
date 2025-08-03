import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationRoutingModule } from './authentication-routing.module';
import { AuthenticationComponent } from './authentication.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterComponent } from '../pages/register/register.component';
import { ResetPasswordComponent } from '../pages/reset-password/reset-password.component';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { PasswordModule } from 'primeng/password';
import { CalendarModule } from 'primeng/calendar';
import { FloatLabelModule } from 'primeng/floatlabel';

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
    ReactiveFormsModule,
    FormsModule,
    DividerModule,
    ButtonModule,
    InputTextModule,
    CardModule,
    PasswordModule,
    CalendarModule,
    FloatLabelModule
  ]
})
export class AuthenticationModule { }
