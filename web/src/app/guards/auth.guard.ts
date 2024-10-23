import { CanActivate, CanActivateChild, CanActivateFn, Router } from '@angular/router';
import { RegisterService } from '../services/auth/auth.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
 export class AuthGuard implements CanActivate, CanActivateChild {

   constructor(private authService: RegisterService, private router: Router) {}

    canActivate(): boolean {
      return this.checkAuth();
    }
  
    canActivateChild(): boolean {
      return this.checkAuth();
    }
    // canDeactivate(component: ProudctRatingComponent): boolean {
    //   if (component.hasUnsavedChanges()) {
    //     return window.confirm('You have unsaved changes. Do you really want to leave?');
    //   }
    //   return true;
    // }
  
    canLoad(): boolean {
      return this.checkAuth();
    }

private checkAuth(): boolean {
  return true;
  this.authService.isAuthenticatedFn()
  if (this.authService.isAuthenticatedUser()) {
  
    return true;
  } else {
    // Redirect to the login page if the user is not authenticated
    this.router.navigate(['/login']);
    return false;
  }
}
};