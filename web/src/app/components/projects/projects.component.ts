import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { MultiSelectFilterEvent } from 'primeng/multiselect';
import { departmentService } from 'src/app/services/department/department.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-projects',
  standalone: false,
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.css'
})
export class ProjectsComponent implements OnInit, OnChanges {

  selectedProject: any;

  departments: any[] = []
  selectedDepartments: any[] = []
  addProjectVisible: boolean = false
  selectedStatus: any[] = [];
  selectedProjects: any[] = [];
  selectedEmployees: any[] = [];
  employees: any[] = [];
  rangeDates: Date[] = [];

  status: any[] = [
    {label:'Not Started', value: 'Not Started'},
    {label:'Pending', value: 'Pending'},
    {label:'In Progress', value: 'In Progress'},
    {label:'Completed', value: 'Completed'},
    {label:'On Hold', value: 'On Hold'}];
  page: number = 0;
  keyword: string = "";
  size: number = 10;
  isLoading: boolean = false;
  items: string[] = [];       // results shown in dropdown
  selectedItem: string = '';  // model binding
  projects: any[] = [
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

  constructor(private messageService: MessageService, private departmentService: departmentService, private employeeService: EmployeeService, private projectService: ProjectService) {

  }
  ngOnChanges(changes: SimpleChanges): void {
    if(changes['keyword']||changes['selectedDepartments']||changes['selectedStatus']||changes['page']||changes['size']){
  console.log(changes)
  this.page=0;
  this.getProjects()
}
  }

  ngOnInit(): void {
    this.getProjects();
  }

  showDialog(): void {
    this.addProjectVisible = true
  }

  reset(): void {
    this.keyword = "";
    this.selectedDepartments = [];
    this.selectedEmployees = [];
    this.selectedStatus = [];
    this.page = 0;
    this.size = 10;
    this.getProjects();
  }
  refresh(){
    this.getProjects();
  }

  onAddProjectClose(response: any) {
    console.log(response)
    if (response == 'Employee Added Succesfully') {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Employee Added Successfully' });
      this.page = 0
      this.size = 10
      this.getProjects();
    }
    this.addProjectVisible = false;
  }
  getInitials(firstName: String="", lastName: String=""): string {
    if (!firstName || !lastName) return '??';
    return (firstName.charAt(0) + lastName.charAt(0)).toUpperCase();
  }

  // Helper method to generate avatar colors based on user ID
  getAvatarColor(userId: number): string {
    const colors = [
      '#667eea', '#764ba2', '#f093fb', '#f5576c',
      '#4facfe', '#00f2fe', '#43e97b', '#38f9d7',
      '#ffecd2', '#fcb69f', '#a8edea', '#fed6e3',
      '#ff9a9e', '#fecfef', '#ffecd2', '#fcb69f'
    ];
    return colors[userId % colors.length];
  }
  filterDepartment($event: AutoCompleteCompleteEvent) {
    this.getDepartments($event.query);}
  filterEmployees($event: AutoCompleteCompleteEvent) {
    this.employeeService.getEmployees(0,100,$event.query).subscribe((val:any)=>{
      this.employees=val.content.map((el:any)=>({...el,label:el?.firstName+" "+el?.lastName,value:el.id}))||[];
    })
  }
  getProjects() {
    this.isLoading = true
    this.projectService.getProjects(this.page, this.size,this.keyword,this.selectedDepartments,this.selectedEmployees, this.selectedStatus.map((el:any)=>el.value),this.rangeDates).subscribe((response: any) => {
      this.projects = response.content;
      this.isLoading = false
    })
  }
  getDepartments(filter: string = "") {
    this.departmentService.getDepartments(0, 100, filter).subscribe((response: any)=> {
        this.departments = response.content.map((dep: any) => ({ label: dep.departmentName, value: dep.id }))
      }
    );
  }
  deleteProject(id: number) {
    this.projectService.deleteProject(id).subscribe((response: any) => {
      if (Boolean(response['Status'])) {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: response
          ['message'] });
        this.projects = this.projects.filter((el: any) => el.id != id);
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: response
          ['message'] });
      }
    })
  }
  search(event: any) {
    const query = event.query.toLowerCase();
    const allItems = ['Apple', 'Banana', 'Orange', 'Mango', 'Grapes'];
    this.items = allItems.filter(item => item.toLowerCase().includes(query));
  }
  getMenuItems(project: any): MenuItem[] {
      return [
        {
          label: 'Edit',
          icon: 'pi pi-pencil',
          command: () => this.editProject(project)

        },
        {
          label: 'Add Employee',
          icon: 'pi pi-user-plus',
        },
        {
          label: 'Set Manager',
          icon: 'pi pi-user',
        },
        {
          separator: true
        },
        {
          label: 'Delete',
          icon: 'pi pi-trash',
          command: () => this.deleteProject(project.id)
        }
      ];
    }
  editProject(project: any): void {
    this.addProjectVisible=true;
    this.selectedProject=project;
  }
  customSort($event: any) {
throw new Error('Method not implemented.');
}

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
}
