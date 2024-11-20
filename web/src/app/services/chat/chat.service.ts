import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  jwtKey:String|null;

  private JwtKey:String| boolean ="";
  private httpHeaders: HttpHeaders = new HttpHeaders({
    "Content-Type":"application/json",
    "Accept":"application/json",
});
  private authSecretKey = 'key';
baseUrl="http://localhost:8080/api/messages"
  constructor(private httpClient:HttpClient) {
this.jwtKey=localStorage.getItem('key');

    this.httpHeaders= new HttpHeaders({
      "Content-Type":"application/json",
      "Accept":"application/json",
      "Authorization":"Bearer "+this.jwtKey
    });
   }

   getConversations(id :number|undefined){
    return this.httpClient.get(this.baseUrl+"/chatRooms/user/"+id);
   }
   getMessages(user1:number|undefined,user2:number|undefined
   ){
    return this.httpClient.get(this.baseUrl+`/both/${user1}/${user2}`);
   }
   sendMessage(message:string|undefined,user1:number|undefined,user2:number|undefined
   ){
    let payload={
      sender:{
        id:user1
      },
      recipient:{
        id:user2
      },
      message:message,
      chatRoom:{
        id:user1+"_"+user2
      }
    }
    let formdata =new FormData();
    formdata.append('message',message?message:"");
    return this.httpClient.post<any>(this.baseUrl+`/${user1}/${user2}`,formdata);
   }
}
