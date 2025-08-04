import {Component, OnInit, Signal} from '@angular/core';
import {Store} from '@ngrx/store';
import {Interface} from 'readline';
import {filter, Observable, take} from 'rxjs';
import {EmployeeModule} from 'src/app/modules/employeeModule/employee/employee.module';
import {EmployeeService} from 'src/app/services/employee.service';
import {selectActiveUser, selectActiveUserId, selectLoading} from "../../stores/user/user.selectors";
import { AppState } from 'src/app/stores/app.state';
import * as SockJS from 'sockjs-client';
import * as StompJs from 'stompjs';
import { ActiveUserState } from 'src/app/stores/user/user.reducer';
import { ChatService } from 'src/app/services/chat/chat.service';
import { validateHeaderName } from 'http';
import * as moment from 'moment';
@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})

export class ChatComponent implements OnInit {


  isModalOpen:boolean=false;
  count$!: Observable<number>;
  userId$: Observable<any>;
  unReadMessages: Array<any> = [];
  id:number|undefined;
  message:string|undefined;
  activeUser$!: Observable<any>
  employees!: Array<EmployeeModule>
  private apiKey:string="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxZWxhbGFvdWllbGhhbmFmaWhpY2hhbUBnbWFpbC5jb20iLCJpYXQiOjE3MjkxMDkwODMsImV4cCI6MzYwMDAwMDAwMDAwMDB9.TgzFNLQVXgxhqpcqAsbMYq9UV3EPLApogQrdgZ0mkFk";
  contactedEmployees: Array<EmployeeModule | undefined> = [];
  loading$!:Observable<any>;
  isLoading:boolean=false;
  socket:any;
  selectedChatUser: EmployeeModule | undefined;
  actualConversationMessages!: Array<any>;
  chatRooms: any = [
  ];

  ngOnInit(): void {
    //  console.log(this.count$)
    this.employeeService.getEmployees(1, 0).subscribe((val) => {
      this.employees = val.content;
      this.getConversation();
    });
    let ws= new SockJS('http://localhost:8080/ws')
this.socket=StompJs.over(ws)
this.socket.connect({'Authorization:':"Bearer "+this.apiKey},()=>{
this.socket.subscribe(`/user/${this.id}/queue/messages`,(e:any)=>{
  let data=JSON.parse(e.body);
  console.log(data)
  if(data.sender==this.selectedChatUser?.id){
    let obj = {
      message: data.message,
      message_append_date:new Date(),
      sender: {
        id: this.selectedChatUser?.id,
        "first_name": this.selectedChatUser?.first_name,
        "last_name": this.selectedChatUser?.last_name,
        "phone_number": this.selectedChatUser?.phone_number,
        "hire_date": this.selectedChatUser?.hire_date,
        "job_title": this.selectedChatUser?.job_title,
        "email": this.selectedChatUser?.email,
        },
      recipient: {
        id: this.id},
      created_at: data.date,
      chat_id:this.selectedChatUser?.id+'_'+this.id
    }
    this.unReadMessages.push(obj);

    let number=document.querySelector(".UnreadMessages")?.scrollHeight
    document.querySelector(".UnreadMessages")?.scrollTo(0,number!+100);
  }else{
    console.log("DONG SOMETHING ELSE");
  }
});
})
  }


constructor(private store: Store<AppState>, private employeeService: EmployeeService,private chatservice:ChatService) {
  this.activeUser$ = this.store.select(selectActiveUser)
this.userId$ = this.store.select(selectActiveUserId)
// If you need to log the userId:
this.loading$ = this.store.select(selectLoading);
this.loading$.subscribe((val) => {
 this.isLoading=val;
})
this.userId$.subscribe((id) => {
  this.id=id;
  // console.log('User ID:', this.id);
});
}
openDialog() {
  this.isModalOpen=true;

}
closeModal($event: any) {
  this.isModalOpen=false;
  }
sendMessage(arg0: string|undefined) {
  console.log("sender id is "+this.id+" recipient id is "+this.selectedChatUser?.id);
  this.chatservice.sendMessage(arg0,this.id,this.selectedChatUser?.id).subscribe(val=>{
    if(val.Status){
      let obj = {
        message: this.message,
        sender: {
          id: this.id,
          "first_name": this.selectedChatUser?.first_name,
          "last_name": this.selectedChatUser?.last_name,
          "phone_number": this.selectedChatUser?.phone_number,
          "hire_date": this.selectedChatUser?.hire_date,
          "job_title": this.selectedChatUser?.job_title,
          "email": this.selectedChatUser?.email,
          },
        recipient: {
          id: this.selectedChatUser?.id,
          "first_name": this.selectedChatUser?.first_name,
          "last_name": this.selectedChatUser?.last_name,
          "phone_number": this.selectedChatUser?.phone_number,
          "hire_date": this.selectedChatUser?.hire_date,
          "job_title": this.selectedChatUser?.job_title,
          "email": this.selectedChatUser?.email,
        },
        created_at: new Date(),
        chat_id: this.id+'_'+this.selectedChatUser?.id
      }
      this.message="";
      this.actualConversationMessages.push(obj);
      // this.chatRooms.
    }
  });
  }
  getConversation(){
    this.chatservice.getConversations(this.id).subscribe(val=>this.chatRooms=val)
    .add(() => this.filter());
  }
filter() {
    let uniqueRoomsMap = [];
    let ids = new Set();
    for (let index = 0; index < this.chatRooms.length; index++) {
      const element = this.chatRooms[index];
      const doesntInclude = (el: number| undefined) => {
        return ids.has(el);
      }

      if (uniqueRoomsMap.length == 0 || (element.recipient?.id != this.id && !doesntInclude(element.recipient?.id)) || (element.sender.id != this.id && !doesntInclude(element.sender?.id))) {
        ids.add(element.recipient?.id != this.id ? element.recipient?.id : element.sender.id)
        console.log(this.employees)
        ids.forEach(id => {

            if (this.contactedEmployees.every(el => el?.id != id)) {

              this.contactedEmployees.push(this.employees.find(el => el.id == id))
            }
          }
        )
      } else {
      }
    }
  }
  chooseConversation(selectedId:number|undefined) {
    this.selectedChatUser=this.employees.find(el=>el.id==selectedId);
    this.chatservice.getMessages(this.id,selectedId).subscribe((val:any)=>
    {
      console.log(val)
    this.actualConversationMessages=val.map((item: any) => {
     return {...item, created_at: this.formatTime(item.date)}
    })
    }
    )
  }

  // Helper method to get initials for avatars
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

  // Helper method to format message time
  formatTime(timestamp: any): string {
    if (!timestamp) return '';

    const date = new Date(timestamp);
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);

    if (minutes < 1) {
      return 'Just now';
    } else if (minutes < 60) {
      return `${minutes}m ago`;
    } else if (hours < 24) {
      return `${hours}h ago`;
    } else if (days < 7) {
      return `${days}d ago`;
    } else {
      return date.toLocaleDateString();
    }
  }
}

interface ChatRoom {
  id: string | null,
  sender: {
    id: number|undefined;
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
  recipient: {
    id: number|undefined;
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
