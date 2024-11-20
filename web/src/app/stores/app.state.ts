import { ActionReducerMap, MetaReducer } from '@ngrx/store';
import * as fromUser from './user/user.reducer'; // Import user reducer
import { DataEffects } from './user/user.effects';

export interface AppState {
  activeUser: fromUser.ActiveUserState; // Ensure this matches the reducer state
}

export const reducers: ActionReducerMap<AppState> = {
  activeUser: fromUser.userReducer,
};
export const appEffects = [DataEffects];

export const metaReducers: MetaReducer<AppState>[] = [];
