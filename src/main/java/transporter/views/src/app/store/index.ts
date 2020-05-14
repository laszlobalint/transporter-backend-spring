import { ActionReducerMap } from '@ngrx/store';
import * as fromAuth from '../store/reducers/auth.reducer';
import * as fromBooking from '../store/reducers/booking.reducer';
import * as fromTransport from '../store/reducers/transport.reducer';

export interface State {
  auth: fromAuth.AuthState;
  bookings: fromBooking.BookingState;
  transports: fromTransport.TransportState;
}

export const reducers: ActionReducerMap<State> = {
  auth: fromAuth.reducer,
  bookings: fromBooking.reducer,
  transports: fromTransport.reducer,
};

export * from './actions';
export * from './effects';
