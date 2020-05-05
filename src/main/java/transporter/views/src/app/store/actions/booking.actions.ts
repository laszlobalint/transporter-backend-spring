import { createAction, props } from '@ngrx/store';
import { Booking } from '../../_models';

export const FetchBookings = createAction('[Transport] Fetch Bookings');
export const FetchBookingsSuccess = createAction('[Transport] Fetch Bookings Success', props<{ bookings: Booking[] }>());
export const FetchBookingsFailure = createAction('[Transport] Fetch Bookings Failure', props<{ error: string }>());
