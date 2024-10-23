import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  constructor(private formBuilder: FormBuilder, private authService:RegisterService,private  snackBar: MatSnackBar, private router: Router, private route: ActivatedRoute){
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      console.log(this.id); // Use the id as needed
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
    let snack = this.snackBar;
    let router=this.router;

    if(this.id){

    
    this.authService.resetPassword(this.id?this.id:'',this.password).subscribe((value)=>{
      if(value.status){
        snack.open(value.Message, 'Close', { duration: 3000 })
        this.router.navigate(['login']);
      }else{
        snack.open(value.Message, 'Close', { duration: 3000 })
      }
        
    })
  } else{
    this.router.navigate(['login']);

  }
  }
}
