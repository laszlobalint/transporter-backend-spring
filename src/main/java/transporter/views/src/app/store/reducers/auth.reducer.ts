import { Action, createReducer, on } from '@ngrx/store';
import { Passenger } from './../../_models/passenger.model';
import * as fromActions from '../actions';

export interface AuthState {
  token?: string;
  passenger?: Passenger;
}

export const initialState: AuthState = {
  token: undefined,
  passenger: undefined,
};

const reducerFunction = createReducer(
  initialState,
  on(fromActions.LoginPassengerSuccess, (state, { token }) => ({
    ...state,
    token,
  })),
  on(fromActions.GetPassengerInfoSuccess, (state, { passenger }) => ({
    ...state,
    passenger,
  })),
  on(fromActions.LogoutPassenger, () => ({
    token: undefined,
    passenger: undefined,
  })),
);

export interface State {
  auth: AuthState;
}

export const authStateFeatureKey = 'auth';

export function reducer(state: AuthState | undefined, action: Action): AuthState {
  return reducerFunction(state, action);
}
