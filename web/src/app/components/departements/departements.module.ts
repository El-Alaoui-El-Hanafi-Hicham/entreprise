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
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToolbarModule } from 'primeng/toolbar';
import { InputSwitchModule } from 'primeng/inputswitch';
import { MessagesModule } from 'primeng/messages';
import { SkeletonModule } from 'primeng/skeleton';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';


@NgModule({
  declarations: [
    DepartementsComponent,
    DepartmentModalComponent,
    DepartementsUsersComponent
  ],
  imports: [
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
    MenuModule,
    InputTextModule,
    DropdownModule,
    ToastModule,
    AvatarModule,
    ConfirmDialogModule,
    ToolbarModule,
    InputSwitchModule,
    MessagesModule,
    SkeletonModule,
    ProgressSpinnerModule,
    CardModule,
    DividerModule,
    BadgeModule
  ]
})
export class DepartementsModule { }
