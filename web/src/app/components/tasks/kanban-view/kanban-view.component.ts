import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-kanban-view',
  templateUrl: './kanban-view.component.html',
  styleUrl: './kanban-view.component.css'
})
export class KanbanViewComponent {
getPrioritySeverity(arg0: any): "success"|"info"|"warning"|"danger"|"secondary"|"contrast"|undefined {
throw new Error('Method not implemented.');
}
openNewTaskModal() {
throw new Error('Method not implemented.');
}

  @Input() tasks: any[] = [];
  @Output() editTaskEvent = new EventEmitter<any>();


  getStatusSeverity(status: string): string {
    switch (status) {
      case 'To Do':
        return 'info';
      case 'In Progress':
        return 'warning';
      case 'Done':
        return 'success';
      default:
        return 'default';
    }
  }

  getMenuItems(task: any) {
    return [
      { label: 'Edit', icon: 'pi pi-pencil', command: () => this.editTask(task) },
      { label: 'Delete', icon: 'pi pi-trash', command: () => this.deleteTask(task) }
    ];
  }

  editTask(task: any) {
    // Implement edit logic
    console.log('Editing task:', task);
  }

  deleteTask(task: any) {
    // Implement delete logic
    console.log('Deleting task:', task);
  }

  isOverdue(date: string): boolean {
    if (!date) return false;
    const dueDate = new Date(date);
    const today = new Date();
    return dueDate < today;
  }


}
