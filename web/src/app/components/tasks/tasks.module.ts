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
import { MultiSelectModule } from 'primeng/multiselect';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { TagModule } from 'primeng/tag';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ButtonGroupModule } from 'primeng/buttongroup';
import { CardModule } from 'primeng/card';
import { KanbanViewComponent } from './kanban-view/kanban-view.component';
import { TabViewModule } from 'primeng/tabview';
import { FilterByStatusPipe } from "./filterByStatus.pipe";
import { PanelModule } from 'primeng/panel';

@NgModule({
  declarations: [TasksComponent, TaskModalComponent,KanbanViewComponent],
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
    MultiSelectModule,
    DialogModule,
    OverlayPanelModule,
    AvatarModule,
    MenuModule,
    TagModule,
    SelectButtonModule,
    PanelModule,
    ButtonGroupModule,
    CardModule,
    FilterByStatusPipe
],
})
export class TasksModule {}
