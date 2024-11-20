import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class EmployeeModule  {
  id:number | undefined;
email:String | undefined;
hire_date:Date| undefined;
first_name:String| undefined;
last_name:String| undefined;
phone_number:Number | undefined;
job_title:String|undefined;
department_id:BigInt|undefined;
 }
