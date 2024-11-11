import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Interface } from 'readline';
import { Observable } from 'rxjs';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})

export class ChatComponent implements OnInit{
  count$!: Observable<number>;
  userId:number=2;
  activeUser$!: Observable<EmployeeModule>
  employees!:Array<EmployeeModule>
  contactedEmployees:Array<EmployeeModule | undefined>=[];
  chatRooms: Array<ChatRoom> = [
    {
        "id": "1_2",
        "sender": {
            "id": 1,
            "first_name": "Test",
            "status": null,
            "last_name": "User",
            "phone_number": 0,
            "hire_date": null,
            "job_title": null,
            "email": "TestUser@gmail.com",
            "password": null,
            "created_at": null,
            "updated_at": null,
            "taskList": null,
            "projectList": null,
            "enabled": true,
            "username": "TestUser@gmail.com",
            "authorities": [],
            "accountNonLocked": true,
            "accountNonExpired": true,
            "credentialsNonExpired": true
        },
        "recipient": {
            "id": 2,
            "first_name": "Test",
            "status": null,
            "last_name": "User",
            "phone_number": 0,
            "hire_date": null,
            "job_title": null,
            "email": "1elalaouielhanafihicham@gmail.com",
            "password": null,
            "created_at": null,
            "updated_at": null,
            "taskList": null,
            "projectList": null,
            "enabled": true,
            "username": "1elalaouielhanafihicham@gmail.com",
            "authorities": [],
            "accountNonLocked": true,
            "accountNonExpired": true,
            "credentialsNonExpired": true
        }
    },
    {
        "id": "2_1",
        "sender": {
            "id": 2,
            "first_name": "Test",
            "status": null,
            "last_name": "User",
            "phone_number": 0,
            "hire_date": null,
            "job_title": null,
            "email": "1elalaouielhanafihicham@gmail.com",
            "password": null,
            "created_at": null,
            "updated_at": null,
            "taskList": null,
            "projectList": null,
            "enabled": true,
            "username": "1elalaouielhanafihicham@gmail.com",
            "authorities": [],
            "accountNonLocked": true,
            "accountNonExpired": true,
            "credentialsNonExpired": true
        },
        "recipient": {
            "id": 1,
            "first_name": "Test",
            "status": null,
            "last_name": "User",
            "phone_number": 0,
            "hire_date": null,
            "job_title": null,
            "email": "TestUser@gmail.com",
            "password": null,
            "created_at": null,
            "updated_at": null,
            "taskList": null,
            "projectList": null,
            "enabled": true,
            "username": "TestUser@gmail.com",
            "authorities": [],
            "accountNonLocked": true,
            "accountNonExpired": true,
            "credentialsNonExpired": true
        }
    }
];

  ngOnInit(): void {
    this.count$=this.store.select("count");
    //  console.log(this.count$)
    this.employeeService.getEmployees(1,0).subscribe((val)=>{
      this.employees=val.content;
      this.filter();
    });
    this.activeUser$=this.store.select(state=>state?.user);

     

  }
constructor(private store: Store<{
  user: any;count:number
}>,private employeeService:EmployeeService){
}
filter(){
let uniqueRoomsMap=[];
let ids=new Set();
for (let index = 0; index < this.chatRooms.length; index++) {
  const element = this.chatRooms[index];
  const doesntInclude=(el:number)=>{
    return ids.has(el);
  }

if(uniqueRoomsMap.length==0||(element.recipient?.id!=this.userId &&!doesntInclude(element.recipient?.id)) || (element.sender.id!=this.userId &&!doesntInclude(element.sender?.id)))  {
ids.add(element.recipient?.id!=this.userId?element.recipient?.id:element.sender.id)
console.log(this.employees)
ids.forEach(id=>{

  if(this.contactedEmployees.every(el=>el?.id!=id)){

    this.contactedEmployees.push(this.employees.find(el=>el.id==id))}
  }
)
console.log("contacted employees are ======>")
console.table(this.contactedEmployees);
}else{
}
}
// ids.forEach(el=>console.log(this.employees.find(emp=>emp.id==el)))
}
}
interface ChatRoom{
  id:string | null,
  sender:{
    id:number;
"first_name": string | null,
            "status": string | null,
            "last_name": string | null,
            "phone_number": number,
            "hire_date": string | null,
            "job_title": string | null,
            "email": string | null,
            "password": string | null,
            "created_at": string | null,
            "updated_at": string | null,
            "taskList": string | null,
            "projectList": string | null,
            "enabled": boolean,
            "username": string | null,
            "authorities": Array<any>,
            "accountNonLocked": boolean,
            "accountNonExpired": boolean,
            "credentialsNonExpired": boolean
  },
  recipient:{
    id:number;
"first_name": string | null,
            "status": string | null,
            "last_name": string | null,
            "phone_number": number,
            "hire_date": string | null,
            "job_title": string | null,
            "email": string | null,
            "password": string | null,
            "created_at": string | null,
            "updated_at": string | null,
            "taskList": string | null,
            "projectList": string | null,
            "enabled": boolean,
            "username": string | null,
            "authorities": Array<any>,
            "accountNonLocked": boolean,
            "accountNonExpired": boolean,
            "credentialsNonExpired": boolean
  };
  };