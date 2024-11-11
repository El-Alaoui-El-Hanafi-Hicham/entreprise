// app.reducer.ts
import { ActionReducerMap, MetaReducer } from '@ngrx/store';
import { environment } from '../../environments/environment';

// Import feature reducers
import * as fromActiveUser from './activeUser.reducer';
import * as fromContactedEmployees from './contactedEmployees.reducer';

// Define the AppState interface
export interface AppState {
  activeUser: fromActiveUser.ActiveUserState;
  contactedEmployees: fromContactedEmployees.ContactedEmployeesState;
  // Add other feature states as needed
}

// Combine all feature reducers into one root reducer
export const reducers: ActionReducerMap<AppState> = {
  activeUser: fromActiveUser.activeUserReducer,
  contactedEmployees: fromContactedEmployees.contactedEmployeesReducer,
  // Add other reducers here
};

// Optional: Meta-reducers, e.g., logging, hydration, etc.
export const metaReducers: MetaReducer<AppState>[] =[];
