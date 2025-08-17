import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, booleanAttribute } from '@angular/core';
import { RegisterModule } from '../../modules/register/register.module';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private isAuthenticated = false;
  private JwtKey:String| boolean ="";
  private httpHeaders: HttpHeaders = new HttpHeaders({
    "Content-Type":"application/json",
    "Accept":"application/json",
    "Authorization":"Bearer "+this.JwtKey
  });
  private authSecretKey = 'key';
baseUrl="http://localhost:8080/api/auth/"
  constructor(private httpClient:HttpClient) {
    this.isAuthenticatedFn();
    this.JwtKey = !!localStorage.getItem(this.authSecretKey);
   }
  register(user:RegisterModule){

  // Step
    return this.httpClient.post<any>(this.baseUrl+"register",user,{headers:this.httpHeaders}).subscribe({
      next(value) {
        console.log(Object.keys(value).includes("JwtKey"))
        console.log(value)
        if(Object.keys(value).includes("JwtKey")){
          const {JwtKey} = value
          localStorage.setItem("key",JwtKey);
        }
      },
      error(err) {
         console.log(err)

      },
    })
  }
  isAuthenticatedFn() {
  // Step
  let s:boolean=false;
   return this.httpClient.get<any>(this.baseUrl + "isAuthenticated", { headers: this.httpHeaders })
  //  .subscribe((status: boolean) =>this.isAuthenticated=status)
}
  login(payload:Object | undefined){
    console.log(payload)
    return this.httpClient.post<any>(this.baseUrl+"login",payload,{headers:this.httpHeaders})
  }
  isAuthenticatedUser(): boolean {
      return this.JwtKey && this.isAuthenticated;
  }

  logout(): void {
    localStorage.removeItem(this.authSecretKey);
    this.isAuthenticated = false;
  }
  resetPassword(id:String,password:String){
   return this.httpClient.post<any>(this.baseUrl+"resetPassord/"+id,{password:password},{headers:this.httpHeaders})
  }
}
