import { createAction, props } from '@ngrx/store';
import { Passenger, LoginPassengerDto } from './../../_models/passenger.model';

export const LoginPassenger = createAction('[Auth] Login Passenger', props<{ loginPassenger: LoginPassengerDto }>());
export const LoginPassengerSuccess = createAction('[Auth] Login Passenger Success', props<{ token: string }>());
export const GetPassengerInfo = createAction('[Auth] Get Passenger Information');
export const GetPassengerInfoSuccess = createAction('[Auth] Get Passenger Information Success', props<{ passenger: Passenger }>());
export const LogoutPassenger = createAction('[Auth] Logout Passenger');
export const DoNothing = createAction('[Auth] Do Nothing');
