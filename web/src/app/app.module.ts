import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { CommonModule, DatePipe } from '@angular/common';
import { AppComponent } from './app.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PageNotFoundComponentComponent } from './pages/page-not-found-component/page-not-found-component.component';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HomeComponent } from './pages/home/home.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { LandComponent } from './components/land/land.component';
import { UsersComponent } from './components/users/users.component';

// PrimeNG Imports
import { MessagesModule } from 'primeng/messages';
import { TableModule } from 'primeng/table';
import { SplitButtonModule } from 'primeng/splitbutton';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { TagModule } from 'primeng/tag';
import { SpeedDialModule } from 'primeng/speeddial';
import { FileUploadModule } from 'primeng/fileupload';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CalendarModule } from 'primeng/calendar';
import { ToastModule } from 'primeng/toast';
import { SidebarModule } from 'primeng/sidebar';
import { MenuModule } from 'primeng/menu';
import { ToolbarModule } from 'primeng/toolbar';
import { PaginatorModule } from 'primeng/paginator';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DividerModule } from 'primeng/divider';
import { PasswordModule } from 'primeng/password';
import { PanelMenuModule } from 'primeng/panelmenu';
import { MenubarModule } from 'primeng/menubar';
import { FloatLabelModule } from 'primeng/floatlabel';


// PrimeNG Dialog service
import { MessageService, ConfirmationService } from 'primeng/api';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { DepartmentModalComponent } from './components/modals/department-modal/department-modal.component';
import { DepartementsUsersComponent } from './components/modals/departements-users/departements-users.component';
import { DepartementsComponent } from './components/departements/departements.component';
import { jWTKeyInterceptor } from './jwtkey.interceptor';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { ChatComponent } from './components/chat/chat.component';
import { userReducer } from './stores/user/user.reducer';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { EffectsModule, provideEffects } from '@ngrx/effects';
import {  reducers,metaReducers, appEffects } from './stores/app.state';


@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponentComponent,
    HomeComponent,
    SideBarComponent,
    LandComponent,

  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    // PrimeNG Modules
    MessagesModule,
    TableModule,
    SplitButtonModule,
    DialogModule,
    ButtonModule,
    InputTextModule,
    InputSwitchModule,
    TagModule,
    SpeedDialModule,
    FileUploadModule,
    CardModule,
    DropdownModule,
    InputTextareaModule,
    CalendarModule,
    ToastModule,
    SidebarModule,
    MenuModule,
    ToolbarModule,
    PaginatorModule,
    ProgressSpinnerModule,
    DividerModule,
    PasswordModule,
    PanelMenuModule,
    MenubarModule,
    DialogModule,
    FloatLabelModule,
    // Store Modules
    StoreModule.forRoot(reducers, { metaReducers }),
    EffectsModule.forRoot(appEffects),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: false })
  ],
  providers: [
    HttpClient,
    MessageService,
    ConfirmationService,
    provideHttpClient(withInterceptors([jWTKeyInterceptor])),
    provideAnimationsAsync(),
    DatePipe,
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
