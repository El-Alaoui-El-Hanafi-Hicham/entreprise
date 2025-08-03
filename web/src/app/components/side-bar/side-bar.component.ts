import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/app/stores/app.state';
import { selectActiveUser, selectActiveUserId } from 'src/app/stores/user/user.selectors';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent {
openedSideBar:boolean=true;
activeUser$: Observable<any>
menuItems: MenuItem[] = [
  {
    label: 'Home',
    icon: 'pi pi-home',
    routerLink: '/'
  },
  {
    label: 'Users',
    icon: 'pi pi-users',
    routerLink: '/employees'
  },
  {
    label: 'Departments',
    icon: 'pi pi-sitemap',
    routerLink: '/departements'
  },
  {
    label: 'Chat',
    icon: 'pi pi-comments',
    routerLink: '/chats'
  }
];

toggleSideBar(){
  this.openedSideBar=!this.openedSideBar
}
constructor(private store : Store<AppState>,private router:Router) {
  this.activeUser$ = this.store.select(selectActiveUser)

 }

 logout() {
  localStorage.clear();
  this.router.navigate(['/login'])
}
}
