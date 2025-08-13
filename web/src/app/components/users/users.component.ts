import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'src/app/services/employee.service';
import { MessageService, MenuItem } from 'primeng/api';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileSelectEvent } from 'primeng/fileupload';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  providers: [MessageService]
})
export class UsersComponent implements OnInit {


  employees:Array<Object>=[];
  userForm:FormGroup|any=[]
  isLoading:Boolean=false;
  isEdit:boolean=false;
  visible:boolean=false
  addUserVisible:boolean=false;
  size:number=10;
  page:number=0;
  file!:File|null;
  total:number=0;
  totalPages:number=0;
  selectedEmployees:Array<Object> = [];
  IsUploadModalopen:boolean=false;
  tempItems: MenuItem[]=[
  {id:'1',label:"Upload File",title:"Upload File",command:()=>this.openUploadFile(),icon:'pi pi-upload'},
  {id:'2',label:"Download Template",title:"Upload File",command:()=>this.downloadDepartmentTemplate(),iconClass:"pi-address-book",icon:"pi pi-download"}
];
  items: MenuItem[] =  [
            {
                label: 'File',
                icon: 'pi pi-file',
                items: [
                    {
                        label: 'New',
                        icon: 'pi pi-plus',
                        items: [
                            {
                                label: 'Document',
                                icon: 'pi pi-file'
                            },
                            {
                                label: 'Image',
                                icon: 'pi pi-image'
                            },
                            {
                                label: 'Video',
                                icon: 'pi pi-video'
                            }
                        ]
                    },
                    {
                        label: 'Open',
                        icon: 'pi pi-folder-open'
                    },
                    {
                        label: 'Print',
                        icon: 'pi pi-print'
                    }
                ]
            },
            {
                label: 'Edit',
                icon: 'pi pi-file-edit',
                items: [
                    {
                        label: 'Copy',
                        icon: 'pi pi-copy'
                    },
                    {
                        label: 'Delete',
                        icon: 'pi pi-times'
                    }
                ]
            },
            {
                label: 'Search',
                icon: 'pi pi-search'
            },
            {
                separator: true
            },
            {
                label: 'Share',
                icon: 'pi pi-share-alt',
                items: [
                    {
                        label: 'Slack',
                        icon: 'pi pi-slack'
                    },
                    {
                        label: 'Whatsapp',
                        icon: 'pi pi-whatsapp'
                    }
                ]
            }
        ]

  constructor(private employeeService:EmployeeService,private messageService: MessageService,private form:FormBuilder){
    this.userForm=this.form.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      hire_date:['',Validators.required]

    });
  }

  ngOnInit(): void {
    this.getEmployees()
this.items = [
            {
                label: 'Options',
                items: [
                    {
                        label: 'Refresh',
                        icon: 'pi pi-refresh'
                    },
                    {
                        label: 'Export',
                        icon: 'pi pi-upload'
                    }
                ]
            }
        ];

  }
openUploadFile(){
  this.IsUploadModalopen=true;
  // console.log("It should be open by now!!")
}
  showDialog():void{
    this.addUserVisible=true
  }

  onAddUserClose(response:any){
    console.log(response)
    if(response=='Employee Added Succesfully'){
      this.getEmployees()
      this.messageService.add({severity:'success', summary: 'Success', detail: 'Employee Added Successfully'});
    }
    this.addUserVisible=false;
  }

  getEmployees(){
    this.isLoading=true
    this.employeeService.getEmployees(this.page, this.size).subscribe((value) => {
      this.employees = value.content;
      this.total=value.totalElements
    },
    (error) => {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Failed to fetch employees. Please try again later.'});
    },()=>{
      this.isLoading=false;
    }
  );
  }
downloadDepartmentTemplate(): void {
  const link = document.createElement('a');
  link.href = 'assets/UsersTemplate.csv'; // Path to the file in assets
  link.download = 'EmployeeTemplate.csv'; // Set the desired file name for download
  document.body.appendChild(link); // Append to the body
  link.click(); // Programmatically trigger a click to download
  document.body.removeChild(link); // Remove the link after download

}
onUpload($event: FileSelectEvent) {
this.file=$event.currentFiles[0];
}
 removeFile() {
    this.file=null;
    }
closeUploadFile(refresh:boolean){
  this.removeFile();
  this.IsUploadModalopen=false;
}
UploadFile() {
this.employeeService.uploadFileToBE(this.file).subscribe((value: any)=> {
  if(Boolean(value['Status'])){
    this.messageService.add({severity:'success', summary: 'Success', detail: value['message']});
    this.closeUploadFile(true);
    this.getEmployees();
    this.file=null
  }else{
    this.messageService.add({severity:'error', summary: 'Error', detail: value['message']});

  }
})
}
  deleteUsers() {
    if (this.selectedEmployees.length != 0) {
this.employeeService.removeUsers(this.selectedEmployees).subscribe(
        {
          next: (response) => {
            this.messageService.add({severity:'success', summary: 'Success', detail: response.message || 'Users deleted successfully.'});
            this.getEmployees(); // Refresh the employee list after deletion
            this.selectedEmployees = []; // Clear the selection after deletion
          },
          error: (error) => {
            this.messageService.add({severity:'error', summary: 'Error', detail:  error.message || 'Failed to delete users. Please try again later.'});
          }
        }
      );
    }
          }

  onPageChange(event: any): void {
    this.page = event.page;
    this.size = event.rows;
    this.getEmployees();
  }
  getMenuItems(rowData: any): MenuItem[] {
  return [
    {
      label: 'Actions',
      items: [
        {
          label: 'Edit',
          icon: 'pi pi-pencil',
          // command: () => this.editRow(rowData)
        },
        {
          label: 'Delete',
          icon: 'pi pi-trash',
          // command: () => this.deleteRow(rowData)
        }
      ]
    }
  ];
}
}
