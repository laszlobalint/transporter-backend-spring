import { ActionReducerMap } from '@ngrx/store';
import * as fromTansport from '../store/reducers/transport.reducer';
import * as fromAuth from '../store/reducers/auth.reducer';

export interface State {
  auth: fromAuth.AuthState;
  transports: fromTansport.TransportState;
}

export const reducers: ActionReducerMap<State> = {
  auth: fromAuth.reducer,
  transports: fromTansport.reducer,
};

export * from './actions';
export * from './effects';
