// Assuming you already have a selector for `activeUser`
import { createFeatureSelector, createSelector,Store } from '@ngrx/store';
import { AppState,  } from '../app.state';
import { ActiveUserState } from './user.reducer';

export const selectState = (state: AppState) => state.activeUser;

export const selectActiveUser = createSelector(
  selectState,
  (activeUser:ActiveUserState) => activeUser.emp
);
export const selectActiveUserId = createSelector(
  selectState,
  (activeUser:ActiveUserState) => activeUser.emp.id
);
export const selectStoreItems = (state: AppState) => state.activeUser;

export const selectLoading = createSelector(
  selectState,
  (state:ActiveUserState) => {
    console.log(state.emp)
    return state.loading;
  }
);
