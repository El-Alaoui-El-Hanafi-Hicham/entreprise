import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class RegisterModule {

    email:String | undefined ;

   password:String | undefined ;

    first_name:String | undefined;
    last_name:String | undefined;
    hire_date:Date | undefined;
 }
