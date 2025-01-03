import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/stores/app.state';
import { loadData } from 'src/app/stores/user/user.actions';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  title = 'web';
  constructor(private store: Store<AppState>) {
  }
  ngOnInit(): void {
    this.store.dispatch(loadData()); // Load necessary data
  
  }

}
