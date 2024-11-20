import { createAction, props } from '@ngrx/store';
import { ActiveUserState } from './user.reducer';
import { EmployeeModule } from 'src/app/modules/employeeModule/employee/employee.module';
export const loadData = createAction(
    '[Data] Load Data' // Action type
  );
  
export const setUser = createAction('[User Component] setUser', props<{ user: any }>() );
export const loadDataFailure = createAction('[User Component] loadDataFailure');
