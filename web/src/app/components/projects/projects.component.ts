import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-projects',
  standalone: false,
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.css'
})
export class ProjectsComponent implements OnInit {
  getSeverity(status: string) {
        switch (status) {
            case 'On Hold':
                return 'danger';

            case 'In Progress':
                return 'success';

            case 'Completed':
                return 'info';

            case 'Not Started':
                return 'warning';

            case 'renewal':
                return undefined;
            default:
                return undefined;
        }
      }

  departments: any[] = []
  selectedDepartments: any[] = []
addProjectVisible:boolean=false
selectedStatus: any[] = [];
selectedProjects: any[] = [];
status: any[]=[];
  projects:any[]= [
  {
      id: 1,
      name: 'E-Commerce Platform',
      status: 'In Progress',
      owner: 'Alice Johnson',
      startDate: '2025-01-10',
      endDate: '2025-06-20',
      budget: 120000
    },
    {
      id: 2,
      name: 'Mobile Banking App',
      status: 'Completed',
      owner: 'David Smith',
      startDate: '2024-05-01',
      endDate: '2025-02-15',
      budget: 95000
    },
    {
      id: 3,
      name: 'AI Chatbot',
      status: 'On Hold',
      owner: 'Sophia Lee',
      startDate: '2025-03-05',
      endDate: '2025-11-30',
      budget: 60000
    },
    {
      id: 4,
      name: 'CRM System Upgrade',
      status: 'In Progress',
      owner: 'Michael Brown',
      startDate: '2025-02-12',
      endDate: '2025-08-12',
      budget: 80000
    },
    {
      id: 5,
      name: 'Healthcare Portal',
      status: 'Not Started',
      owner: 'Emma Wilson',
      startDate: '2025-09-01',
      endDate: '2026-03-15',
      budget: 150000
    }
];

  constructor(private messageService: MessageService){

  }

  ngOnInit(): void {}

  showDialog():void{
    this.addProjectVisible=true
  }

  onAddProjectClose(response:any){
    console.log(response)
    if(response=='Employee Added Succesfully'){
      this.messageService.add({severity:'success', summary: 'Success', detail: 'Employee Added Successfully'});
    }
    this.addProjectVisible=false;
  }
}
