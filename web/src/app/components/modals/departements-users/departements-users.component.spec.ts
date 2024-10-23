import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartementsUsersComponent } from './departements-users.component';

describe('DepartementsUsersComponent', () => {
  let component: DepartementsUsersComponent;
  let fixture: ComponentFixture<DepartementsUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartementsUsersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepartementsUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
