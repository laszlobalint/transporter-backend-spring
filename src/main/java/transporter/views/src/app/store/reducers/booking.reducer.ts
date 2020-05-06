import { Action, createReducer, on } from '@ngrx/store';
import * as fromActions from '../actions';
import { Booking } from '../../_models';

export interface BookingState {
  bookings?: Booking[];
}

export const initialState: BookingState = {
  bookings: [],
};

const reducerFunction = createReducer(
  initialState,
  on(fromActions.FetchBookingsSuccess, (state, { bookings }) => ({
    ...state,
    bookings,
  })),
  on(fromActions.SaveBookingSuccess, (state, { booking }) => ({
    ...state,
    bookings: [...state.bookings, booking],
  })),
  on(fromActions.DeleteBookingSuccess, (state, { deleteBookingDto }) => ({
    ...state,
    bookings: [...state.bookings.filter((b) => b.id !== deleteBookingDto.bookingId)],
  })),
);

export interface State {
  bookings: BookingState;
}

export const bookingsFeatureKey = 'bookings';

export function reducer(state: BookingState | undefined, action: Action): BookingState {
  return reducerFunction(state, action);
}
