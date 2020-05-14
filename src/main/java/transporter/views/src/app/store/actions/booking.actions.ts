import { createAction, props } from '@ngrx/store';
import { Booking, DeleteBookingDto } from '../../_models';

export const FetchBookings = createAction('[Booking] Fetch Bookings');
export const FetchBookingsSuccess = createAction('[Booking] Fetch Bookings Success', props<{ bookings: Booking[] }>());
export const SaveBooking = createAction('[Booking] Save Booking', props<{ booking: Booking }>());
export const SaveBookingSuccess = createAction('[Booking] Save Booking Success', props<{ booking: Booking }>());
export const DeleteBooking = createAction('[Booking] Delete Booking', props<{ id: number }>());
export const DeleteBookingSuccess = createAction('[Booking] Delete Booking Success', props<{ deleteBookingDto: DeleteBookingDto }>());
