import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  ElementRef,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { MenuItem } from 'primeng/api';
import { TaskService } from 'src/app/services/task/task.service';
import Calendar, { EventObject, TZDate } from '@toast-ui/calendar';
import { TasksModule } from './tasks.module';

@Component({
  selector: 'app-tasks',
  standalone: false,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent implements OnInit, AfterViewChecked {
  @ViewChild('calendar', { static: false }) calendarRef!: ElementRef;
  calendar!: Calendar;

  customSort($event: any) {
    throw new Error('Method not implemented.');
  }
  tasks: any[] = [
    {
      id: 1,
      name: 'Task 1',
      description: 'Description for Task 1',
      status: 'Pending',
    },
    {
      id: 2,
      name: 'Task 2',
      description: 'Description for Task 2',
      status: 'In Progress',
    },
    {
      id: 3,
      name: 'Task 3',
      description: 'Description for Task 3',
      status: 'Completed',
    },
  ];
  selectedTask!: any;
  selectedTasks: any[] = [];
  page: number = 0;
  size: number = 10;
  keyword: String = '';
  selectedView: string = 'list';
  selectedCalendarView: 'day' | 'week' | 'month' = 'week';
  loading: boolean = true;
  isModalOpen: boolean = false;
  calendarInitialized = false;
  isEdit: boolean = false;
  PeriodOptions: any[] = [
        { name: 'Day', value: 'day' },
        { name: 'Week', value: 'week' },
        { name: 'Month', value: 'month' },
    ];


  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.getTasks();
  }

  ngAfterViewChecked() {
    if (
      this.selectedView === 'calendar' &&
      this.calendarRef &&
      !this.calendarInitialized
    ) {
      this.initCalendar();
      this.calendarInitialized = true; // <-- prevent re-running
    }
     if (this.selectedView !== 'calendar' && this.calendarInitialized) {
    this.destroyCalendar();
  }
  }


  initCalendar() {
  this.calendar = new Calendar(this.calendarRef.nativeElement, {
    defaultView: this.selectedCalendarView,
    useDetailPopup: true,
    calendars: [
      {
        id: '1',
        name: 'Tasks',
        color: '#ffffff',
        borderColor: '#00a9ff',
      },
    ],
  });

  const events = this.mapTasksToEvents(this.tasks)

  this.calendar.createEvents(events as any); // cast to any if TS complains
}
  mapTasksToEvents(tasks: any) {
    return tasks.map((task: any) => ({
      id: task.id.toString(),
      calendarId: '1', // you can group by project or assignee later
      title: `${task.task_name} (${task.manager?.firstName ?? 'Unassigned'})`,
      category: 'time',
      start: new Date(task.start_date),
      end: new Date(task.end_date),
    }));
  }

  openTaskModal(isEdit: boolean = false, task: any = {}) {
    this.isModalOpen = true;
    this.isEdit = isEdit;
    if (task) {
      this.selectedTask = task;
    }
  }
  closeTaskDialog(s: boolean) {
    this.isModalOpen = false;
    if (s) {
      this.getTasks();
    }
  }
  getInitials(firstName: String = '', lastName: String = ''): string {
    if (!firstName || !lastName) return '??';
    return (firstName.charAt(0) + lastName.charAt(0)).toUpperCase();
  }

  // Helper method to generate avatar colors based on user ID
  getAvatarColor(userId: number): string {
    const colors = [
      '#667eea',
      '#764ba2',
      '#f093fb',
      '#f5576c',
      '#4facfe',
      '#00f2fe',
      '#43e97b',
      '#38f9d7',
      '#ffecd2',
      '#fcb69f',
      '#a8edea',
      '#fed6e3',
      '#ff9a9e',
      '#fecfef',
      '#ffecd2',
      '#fcb69f',
    ];
    return colors[userId % colors.length];
  }
  getTasks() {
    this.loading = true;
    this.taskService.test().subscribe({});
    this.taskService.getTasks(this.page, this.size, this.keyword).subscribe({
      next: (response) => {
        this.tasks = response.content;
        this.loading = false;
      },
    });
  }
  getMenuItems(project: any): MenuItem[] {
    return [
      {
        label: 'Edit',
        icon: 'pi pi-pencil',
        // command: () => this.editProject(project)
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
        separator: true,
      },
      {
        label: 'Delete',
        icon: 'pi pi-trash',
        // command: () => this.deleteProject(project.id)
      },
    ];
  }
  changeSelectedView(view: string) {
    this.selectedView = view;
  }
  destroyCalendar() {
  if (this.calendar) {
    this.calendar.destroy();
    this.calendarInitialized = false;
  }
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
   changeCalendarView() {
    this.calendar.changeView(this.selectedCalendarView);
  }

  // --- Date Navigation ---
  prevPeriod() {
    this.calendar.prev();
  }

  nextPeriod() {
    this.calendar.next();
  }

  goToday() {
    this.calendar.today();
  }
}
