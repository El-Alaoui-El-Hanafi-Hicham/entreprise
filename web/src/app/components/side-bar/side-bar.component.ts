import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/app/stores/app.state';
import { selectActiveUser, selectActiveUserId } from 'src/app/stores/user/user.selectors';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent {
logout() {
throw new Error('Method not implemented.');
}
openedSideBar:boolean=true;
activeUser$: Observable<any>
toggleSideBar(){
  this.openedSideBar=!this.openedSideBar
}
constructor(private store : Store<AppState>) {
  this.activeUser$ = this.store.select(selectActiveUser)

 }

}
