import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DepartementsRoutingModule } from './departements-routing.module';
import { DepartementsComponent } from './departements.component';
import { DepartmentModalComponent } from '../modals/department-modal/department-modal.component';
import { DepartementsUsersComponent } from '../modals/departements-users/departements-users.component';
import { FileUploadModule } from 'primeng/fileupload';
import { SpeedDialModule } from 'primeng/speeddial';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    DepartementsComponent,
    DepartmentModalComponent,
    DepartementsUsersComponent
  ],
  imports: [
    // BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    DepartementsRoutingModule,
    SpeedDialModule,
    FileUploadModule,
    DialogModule,
    TableModule,
    TagModule,
    ButtonModule,
    MenuModule
  ]
})
export class DepartementsModule { }
