import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatComponent } from './chat.component';
import { FormsModule } from '@angular/forms';
import { ChatModalComponent } from '../modals/chat-modal/chat-modal.component';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { BadgeModule } from 'primeng/badge';
import { TooltipModule } from 'primeng/tooltip';
import { SkeletonModule } from 'primeng/skeleton';
import { DividerModule } from 'primeng/divider';
import { CardModule } from 'primeng/card';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { MenuModule } from 'primeng/menu';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TabViewModule } from 'primeng/tabview';
import { SplitterModule } from 'primeng/splitter';
import { DataViewModule } from 'primeng/dataview';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TagModule } from 'primeng/tag';

@NgModule({
  declarations: [ChatComponent, ChatModalComponent],
  imports: [
    CommonModule,
    ChatRoutingModule,
    DialogModule,
    ButtonModule,
    FormsModule,
    InputTextModule,
    ScrollPanelModule,
    AvatarModule,
    AvatarGroupModule,
    BadgeModule,
    ProgressSpinnerModule,
    TooltipModule,
    SkeletonModule,
    DividerModule,
    CardModule,
    InputTextareaModule,
    OverlayPanelModule,
    MenuModule,
    ToastModule,
    ConfirmDialogModule,
    TabViewModule,
    SplitterModule,
    DataViewModule,
    TagModule,
  ],
})
export class ChatModule {}
