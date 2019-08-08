import { Booking } from './booking.model';

export interface Transport {
    id: number;
    route: string;
    departureTime: Date;
    freeSeats: number;
    bookings?: Booking;
}
