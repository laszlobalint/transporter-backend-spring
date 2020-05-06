import { Passenger } from './passenger.model';
import { Transport } from './transport.model';

export interface Booking {
  id?: number;
  departureTime?: Date;
  locationSerbia: string;
  locationHungary: string;
  passenger?: Passenger;
  transport?: Transport;
}

export interface DeleteBookingDto {
  transportId: number;
  bookingId: number;
}
