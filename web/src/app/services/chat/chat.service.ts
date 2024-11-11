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
baseUrl="http://localhost:8080/apii/messages"
  constructor(private httpClient:HttpClient) {
this.jwtKey=localStorage.getItem('key');

    this.httpHeaders= new HttpHeaders({
      "Content-Type":"application/json",
      "Accept":"application/json",
      "Authorization":"Bearer "+this.jwtKey
    });
   }

   getConversations(){
    return this.httpClient.get(this.baseUrl+"/chatRooms/user/1");
   }
}
