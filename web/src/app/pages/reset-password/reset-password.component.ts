import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { RegisterService } from 'src/app/services/auth/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  hide = true;
  id:string | null='';
  userForm :any ;
  password:String='';
  constructor(private formBuilder: FormBuilder, private authService:RegisterService,private  messageService: MessageService, private router: Router, private route: ActivatedRoute){
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
    });

}
ngOnInit(): void {
  this.userForm = this.formBuilder.group({
    password: ['', Validators.required],

  });
}



  formValid(){
    return this.password.length==0
  }
  resetPassword(){
    if(this.id){
      this.authService.resetPassword(this.id?this.id:'',this.password).subscribe((value)=>{
        if(value.status){
          this.messageService.add({severity:'success', summary:'Success', detail:value.Message});
          this.router.navigate(['login']);
        }else{
          this.messageService.add({severity:'error', summary:'Error', detail:value.Message});
        }
      })
    } else{
      this.router.navigate(['login']);
    }
  }
}
