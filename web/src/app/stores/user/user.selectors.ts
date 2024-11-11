// Assuming you already have a selector for `activeUser`
import { createSelector } from '@ngrx/store';

export const selectActiveUser = (state: AppState) => state.activeUser;

export const selectActiveUserId = createSelector(
  selectActiveUser,
  (activeUser) => activeUser?.id
);
