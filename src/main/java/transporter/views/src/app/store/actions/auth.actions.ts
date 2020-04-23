import { Passenger, LoginPassengerDto } from './../../_models/passenger.model';
import { createAction, props } from '@ngrx/store';

export const LoginPassenger = createAction('[Auth] Login Passenger', props<{ loginPassenger: LoginPassengerDto }>());
export const LoginPassengerSuccess = createAction('[Auth] Login Passenger Success', props<{ token: string }>());
export const LoginPassengerFailure = createAction('[Auth] Login Passenger Failure', props<{ error: string }>());
export const GetPassengerInfo = createAction('[Auth] Get Passenger Information');
export const GetPassengerInfoSuccess = createAction('[Auth] Get Passenger Information Success', props<{ passenger: Passenger }>());
export const GetPassengerInfoFailure = createAction('[Auth] Get Passenger Information Failure', props<{ error: string }>());
export const LogoutPassenger = createAction('[Auth] Logout Passenger');
export const DoNothing = createAction('[Auth] Do Nothing');
