import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeesRoutingModule } from './users-routing.module';
import {  UsersComponent} from './users.component';
import { AddUserComponent } from '../modals/add-user/add-user.component';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MenuModule } from 'primeng/menu';
import { MessageService } from 'primeng/api';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  declarations: [
    UsersComponent,
    AddUserComponent
  ],
  imports: [
    CommonModule,
    // BrowserModule,
    EmployeesRoutingModule,
    ProgressSpinnerModule,
    TableModule,
    PaginatorModule,
    MenuModule,
    ButtonModule,
    FileUploadModule,
    DialogModule,
    ToastModule,
    InputTextModule,
    CalendarModule,
    FormsModule
  ]
})
export class EmployeesModule { }
