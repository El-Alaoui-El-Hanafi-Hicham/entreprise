import { createAction, props } from '@ngrx/store';

export const setUser = createAction('[User Component] setUser',props<{ user: object; }>());
