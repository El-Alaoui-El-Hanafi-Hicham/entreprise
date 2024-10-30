import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PageNotFoundComponentComponent } from './pages/page-not-found-component/page-not-found-component.component';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatSidenavModule} from '@angular/material/sidenav';
import { HomeComponent } from './pages/home/home.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import {MatListModule} from '@angular/material/list';
import { LandComponent } from './components/land/land.component';
import { UsersComponent } from './components/users/users.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatPaginatorModule} from '@angular/material/paginator';
import { MatTableModule} from '@angular/material/table';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { AddUserComponent } from './components/modals/add-user/add-user.component';
import { MatNativeDateModule } from '@angular/material/core';
import { MessagesModule } from 'primeng/messages';
import {MatMenuModule} from '@angular/material/menu';
import { TableModule } from 'primeng/table';
import { SplitButtonModule } from 'primeng/splitbutton';;
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { TagModule } from 'primeng/tag';
import { SpeedDialModule } from 'primeng/speeddial';
import { FileUploadModule } from 'primeng/fileupload';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogClose,
  MatDialogModule
} from '@angular/material/dialog';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { DepartmentModalComponent } from './components/modals/department-modal/department-modal.component';
import { DepartementsUsersComponent } from './components/modals/departements-users/departements-users.component';
import { DepartementsComponent } from './components/departements/departements.component';
import { jWTKeyInterceptor } from './jwtkey.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    PageNotFoundComponentComponent,
    HomeComponent,
    SideBarComponent,
    LandComponent,
    UsersComponent,
    DepartementsComponent,
    AddUserComponent,
    ResetPasswordComponent,
    DepartmentModalComponent,
    DepartementsUsersComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatSelectModule,
    MessagesModule,
    DialogModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatButtonModule,
    ButtonModule,
    MatDividerModule,
    MatDatepickerModule,
     MatTableModule,
     MatPaginatorModule,
    HttpClientModule,
    MatInputModule,
    TableModule,
    TagModule,
    MatSnackBarModule,
    MatButtonModule,
    MatDialogModule,
    SplitButtonModule,
    MatMenuModule,
    InputSwitchModule,
    SpeedDialModule,
    FileUploadModule,
    MatNativeDateModule, DialogModule, ButtonModule, InputTextModule // Import the date module

  ],
  providers: [    HttpClient,
    provideHttpClient(withInterceptors([jWTKeyInterceptor]))

  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
