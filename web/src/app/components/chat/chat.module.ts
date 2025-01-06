import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatComponent } from './chat.component';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon';
import { ChatModalComponent } from '../modals/chat-modal/chat-modal.component';
import { DialogModule } from 'primeng/dialog';


@NgModule({
  declarations: [
    ChatComponent,
    ChatModalComponent
  ],
  imports: [
    CommonModule,
    ChatRoutingModule,
    // BrowserModule,
    MatIconModule,
    DialogModule,
    FormsModule
  ]
})
export class ChatModule { }
