import { createEffect, ofType } from '@ngrx/effects';
import { Actions } from '@ngrx/effects';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { EmployeeService } from '../../services/employee.service';
import * as fromActions from './user.actions';
import { Injectable } from '@angular/core';

@Injectable()
export class DataEffects {
  loadData$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadData), // Trigger on loadData action
      mergeMap(() =>
        this.dataService.me().pipe(
          map((data) => {
            console.log(data)
            return fromActions.setUser( {user:data} )
          }),
          catchError((error) => of(fromActions.loadDataFailure()))
        )
      )
    )
  );

  constructor(private actions$: Actions, private dataService: EmployeeService) {}
}
