import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatComponent } from './chat.component';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { ChatModalComponent } from '../modals/chat-modal/chat-modal.component';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';


@NgModule({
  declarations: [
    ChatComponent,
    ChatModalComponent
  ],
  imports: [
    CommonModule,
    ChatRoutingModule,
    // BrowserModule,
    DialogModule,
    ButtonModule,
    FormsModule
  ]
})
export class ChatModule { }
