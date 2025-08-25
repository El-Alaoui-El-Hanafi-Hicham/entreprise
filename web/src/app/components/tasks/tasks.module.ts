import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TasksComponent } from './tasks.component';
import { TasksRoutingModule } from './tasks-routing.module';
import { ToolbarModule } from 'primeng/toolbar';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TaskModalComponent } from '../modals/task-modal/task-modal.component';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { AutoCompleteModule } from 'primeng/autocomplete';


@NgModule({
  declarations: [
    TasksComponent,
    TaskModalComponent
  ],
  imports: [
    CommonModule,
  TasksRoutingModule,
  ToolbarModule,
  InputTextModule,
  IconFieldModule,
InputIconModule,
ButtonModule,
  ReactiveFormsModule,
    FormsModule,
    DropdownModule,
    TableModule,
    CalendarModule,
    AutoCompleteModule,
DialogModule
  ]
})
export class TasksModule { }
