import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tasks',
  standalone: false,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent implements OnInit {
customSort($event: any) {
throw new Error('Method not implemented.');
}
  tasks: any[] = [
    { id: 1, name: 'Task 1', description: 'Description for Task 1', status: 'Pending' },
    { id: 2, name: 'Task 2', description: 'Description for Task 2', status: 'In Progress' },
    { id: 3, name: 'Task 3', description: 'Description for Task 3', status: 'Completed' }
  ];
  selectedTask!: any;
  selectedTasks: any[] = [];
  loading: boolean = true;
    isModalOpen:boolean=false;
  isEdit:boolean=false;
  constructor() { }

  ngOnInit(): void {
  }
openTaskModal(isEdit: boolean = false, task: any={}) {
  this.isModalOpen=true;
  this.isEdit=isEdit;
  if (task) {
    this.selectedTask = task;
  }
}
closeTaskDialog(s:boolean){
  this.isModalOpen=false;
  if(s){
this.getTasks();
  }
}
  getTasks() {
    throw new Error('Method not implemented.');
  }
}
