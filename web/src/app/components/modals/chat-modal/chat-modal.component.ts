import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { EventEmitter } from "@angular/core";
import { MessageService } from 'primeng/api';
import { filter, map } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { EmployeeService } from 'src/app/services/employee.service';


@Component({
  selector: 'app-chat-modal',
  templateUrl: './chat-modal.component.html',
  styleUrl: './chat-modal.component.css'
})
export class ChatModalComponent implements OnChanges{
  isLoading: boolean = false;
  page: number = 0;
  size: number = 50;
  total: number = 0;
  totalPages: number = 0;
  selectedDepartement: any;
  employees: Array<EmployeeModule> = [];
  filteredEmployees: Array<EmployeeModule> = [];
  selectedEmployee: EmployeeModule | null = null;
  searchTerm: string = '';

  @Input() visible!: boolean;
  @Output() closeM: EventEmitter<any> = new EventEmitter();
  @Output() contactSelected: EventEmitter<EmployeeModule> = new EventEmitter();

  constructor(private employeeService: EmployeeService, private messageService: MessageService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.visible?.valueOf() === true) {
      this.getEmployees();
      this.resetSelection();
    }
  }

  closeModal(arg0: boolean) {
    this.closeM.emit(arg0);
    this.resetSelection();
  }

  resetSelection() {
    this.selectedEmployee = null;
    this.searchTerm = '';
    this.filteredEmployees = [...this.employees];
  }

  getEmployees() {
    this.isLoading = true;
    this.employeeService.getEmployees(this.page, this.size).subscribe(
      (value) => {
        this.employees = value.content || [];
        this.filteredEmployees = [...this.employees];
        this.isLoading = false;
      },
      (error) => {
        console.log(error);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to fetch employees. Please try again later.'
        });
        this.isLoading = false;
      }
    );
  }

  selectContact(employee: EmployeeModule) {
    this.selectedEmployee = employee;
  }

  filterContacts() {
    if (!this.searchTerm.trim()) {
      this.filteredEmployees = [...this.employees];
      return;
    }

    const searchLower = this.searchTerm.toLowerCase();
    this.filteredEmployees = this.employees.filter(employee =>
      employee.first_name?.toLowerCase().includes(searchLower) ||
      employee.last_name?.toLowerCase().includes(searchLower) ||
      employee.email?.toLowerCase().includes(searchLower) ||
      employee.job_title?.toLowerCase().includes(searchLower)
    );
  }

  getInitials(firstName: String="", lastName: String=""): string {
    const first = firstName?.charAt(0)?.toUpperCase() || '';
    const last = lastName?.charAt(0)?.toUpperCase() || '';
    return first + last;
  }

  startConversation() {
    if (this.selectedEmployee) {
      this.contactSelected.emit(this.selectedEmployee);
      this.closeModal(true);
    }
  }
}
