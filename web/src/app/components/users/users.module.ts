import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeesRoutingModule } from './users-routing.module';
import {  UsersComponent} from './users.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';


@NgModule({
  declarations: [
    UsersComponent
  ],
  imports: [
    CommonModule,
    // BrowserModule,
    EmployeesRoutingModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatPaginatorModule,
  ]
})
export class EmployeesModule { }
