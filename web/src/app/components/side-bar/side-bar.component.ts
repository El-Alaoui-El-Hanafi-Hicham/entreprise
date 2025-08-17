import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
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
export class SideBarComponent implements OnInit {
openedSideBar:boolean=true;
activeUser$: Observable<any>
activeRoute: string = '';
menuItems: MenuItem[] = [
  {
    label: 'Home',
    icon: 'pi pi-home',
    routerLink: ''
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
  },
  {
    label: 'Tasks',
    icon: 'pi pi-check-circle',
    routerLink: '/tasks'
  },
  {
    label: 'Projects',
    icon: 'pi pi-hammer',
    routerLink: '/projects'
  }
];

toggleSideBar(){
  this.openedSideBar=!this.openedSideBar
}
constructor(private store : Store<AppState>,private router:Router, protected ActivatedRoute:ActivatedRoute) {
  this.activeUser$ = this.store.select(selectActiveUser)

 }
 ngOnInit(): void {

  this.router.events.subscribe(() => {
    this.activeRoute = this.router.url;
  });
 }

 logout() {
  localStorage.clear();
  this.router.navigate(['authentication/login'])
}
}
