import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { ProjectsComponent } from './projects.component';
import { RippleModule } from 'primeng/ripple';
import { CardModule } from 'primeng/card';
import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { AddProjectComponent } from '../modals/add-project/add-project.component';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
@NgModule({
  declarations: [
    ProjectsComponent,
    AddProjectComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    ToolbarModule,
    ButtonModule,
    MultiSelectModule,
    RippleModule,
    IconFieldModule,
    InputIconModule,
    TableModule,
    DialogModule,
    CalendarModule,
    DropdownModule,
    InputTextModule,
    TagModule,
    MessageModule,
    CardModule,
    FormsModule,             // 👈 Add this
    ReactiveFormsModule      // 👈 Add this if using reactive forms

  ]
})
export class ProjectsModule { }
