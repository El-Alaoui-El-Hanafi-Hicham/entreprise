import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-project',
  standalone: false,
  templateUrl: './add-project.component.html',
  styleUrl: './add-project.component.css'
})
export class AddProjectComponent implements OnInit {

  @Input() visible: boolean = false;
  @Input() projectData!: any;
  @Output() closeModal = new EventEmitter<any>();
    data!:FormGroup;


    ngOnInit(): void {
      this.data= new FormGroup({
        project_name: new FormControl(''),
        description: new FormControl(''),
     start_date: new FormControl(''),
    end_date: new FormControl(''),
    department:new FormControl([]),
    manager:new FormControl(),
    Owner:new FormControl({}),
    employeeList:new FormControl([]),
      })
    }
}
