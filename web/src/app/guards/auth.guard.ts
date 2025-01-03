import { CanActivate, CanActivateChild, CanActivateFn, Router } from '@angular/router';
import { RegisterService } from '../services/auth/auth.service';
import { Injectable } from '@angular/core';
import { loadData } from '../stores/user/user.actions';
import { AppState } from '../stores/app.state';
import { Store } from '@ngrx/store';
import { selectActiveUser } from '../stores/user/user.selectors';
import { map, Observable, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
 export class AuthGuard implements CanActivate, CanActivateChild {

   constructor(private authService: RegisterService, private router: Router,private store: Store<AppState>) {}

    canActivate(): boolean | Observable<boolean> {
      console.log(this.router.url);
      return this.checkAuth();
    }
  
    canActivateChild(): boolean | Observable<boolean> {
      return this.accessChilds();
    }
  accessChilds(): boolean | Observable<boolean> {
  console.log("childs are been accessed");
    return true;
  }
    // canDeactivate(component: ProudctRatingComponent): boolean {
    //   if (component.hasUnsavedChanges()) {
    //     return window.confirm('You have unsaved changes. Do you really want to leave?');
    //   }
    //   return true;
    // }
  
    canLoad(): boolean | Observable<boolean> {
      return this.checkAuth();
    }

    private checkAuth(): boolean | Observable<boolean> {
      if(!localStorage.getItem('key')&&this.router.url != '/login')               this.router.navigate(['/login']); // Redirect to the home page      ;  
      if (this.router.url === '/login' && !localStorage.getItem('key')) {
        // Handle '/login' route
        return this.store.select(selectActiveUser).pipe(
          take(1), // Automatically unsubscribe after one emission
          map((user) => {
            if (user?.id) {
              console.log('User is already logged in');
              this.router.navigate(['']); // Redirect to the home page
              return false; // Block access to the login page
            }
            return true; // Allow access to the login page
          })
        );
      } else {
        // Handle all other routes
        return this.authService.isAuthenticatedFn().pipe(
          take(1), // Automatically unsubscribe after one emission
          map((isAuthenticated) => {
            console.log("isAuthenticated",isAuthenticated);
            if (isAuthenticated && localStorage.getItem('key')) {
              return true; // Allow access
            } else {
              localStorage.removeItem('key'); // Remove the token from local storage
              this.router.navigate(['/login']); // Redirect to the login page
              return false; // Block access
            }
          })
        );
      }
    }
    
};