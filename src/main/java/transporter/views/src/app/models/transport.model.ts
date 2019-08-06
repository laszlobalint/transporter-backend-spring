import { Booking } from './booking.model';

export interface Transport {
    id: number;
    departureTime: Date;
    freeSeats: number;
    bookings?: Booking;
}
