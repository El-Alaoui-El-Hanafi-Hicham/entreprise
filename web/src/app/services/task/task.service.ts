import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
jwtKey:String|null;
employees:Array<Object>=[];
private httpHeaders: HttpHeaders| any;
private  BASE_URL:String ="http://localhost:8080/api/task"
  constructor(private http:HttpClient,private datePipe: DatePipe) {
this.jwtKey=localStorage.getItem('key');
this.httpHeaders= new HttpHeaders({
  "Content-Type":"application/json",
  "Accept":"application/json",
  "Authorization":"Bearer "+this.jwtKey
});
   }

   createTask(task: any) {
    console.log("task to create",task)
    let payload = {
      employeesIds: task?.employees?.map((el: any) => el.id)||[],
      // manager_id: task?.manager.id,
      "task_name": task.task_name,
      "description": task.description,
      "priority": task.priority?.value||'Low',
      "projectId": task?.project?.value,
      "status": task.status?.value||'Pending',
      "startDate": this.datePipe.transform(task.start_date, 'yyyy-MM-dd'),
      "endDate":   this.datePipe.transform(task.end_date, 'yyyy-MM-dd'),
    }
    return this.http.post<any>(this.BASE_URL+"",payload,{headers:this.httpHeaders})
   }
   getTasks(page:number=0,size:number=100,keyword:String=""){
    let params = new HttpParams()
      .set('pageNumber', String(page)) // Reassign the result
      .set('pageSize', String(size));  // Reassign the result
      let payload= {keyword:keyword};

    // Make the GET request with headers and params
    return this.http.post<any>(this.BASE_URL+"/search", payload ,{ headers: this.httpHeaders, params });
    }
    test(){
      return this.http.get<any>(this.BASE_URL+"",{headers:this.httpHeaders})
    }
    updateTask(task:any){
      return this.http.put<any>(this.BASE_URL+"/tasks",task,{headers:this.httpHeaders})
    }
    getTaskById(id:number){
      return this.http.get<any>(this.BASE_URL+"/tasks/"+id,{headers:this.httpHeaders})
    }
    deleteTask(id:number){
      return this.http.delete<any>(this.BASE_URL+"/tasks/"+id,{headers:this.httpHeaders})
    }


}
