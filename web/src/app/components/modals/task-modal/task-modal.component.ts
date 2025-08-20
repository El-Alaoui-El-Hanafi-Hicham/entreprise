import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-task-modal',
  standalone: false,
  templateUrl: './task-modal.component.html',
  styleUrl: './task-modal.component.css'
})
export class TaskModalComponent implements OnInit {
onNoClick() {
throw new Error('Method not implemented.');
}
addTask() {
throw new Error('Method not implemented.');
}

 @Input() visible: boolean = false;
  @Input() taskData: any;
  @Input() isEdit: boolean=false;
  @Output() closeModal = new EventEmitter<any>();
  taskForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  ManagerOptions: any[] = [];
  employees: any[] = [];
  departments: any[] = [];
  priorities: any[] = [
    {label:'Low', value: 'Low'},
    {label:'Medium', value: 'Medium'},
    {label:'High', value: 'High'},
    {label:'Critical', value: 'Critical'}];
  statusOptions: any[] = [
    {label:'Pending', value: 'Pending'},
    {label:'In Progress', value: 'In Progress'},
    {label:'Completed', value: 'Completed'},
    {label:'On Hold', value: 'On Hold'}];
  constructor(private fb: FormBuilder) {
    // Initialize the form with default values if needed
    this.taskForm = this.fb.group({
      id: ['', [Validators.required, Validators.minLength(4)]],
      task_name: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required]],
      status: ['', [Validators.required]],
      priority: ['Pending', [Validators.required]],
      manager: [null, []],
      employees: [null, [Validators.required]],
      departments: [null, [Validators.required]],
      start_date: ['', [Validators.required]],
      end_date: ['', [Validators.required]]
    });

  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
