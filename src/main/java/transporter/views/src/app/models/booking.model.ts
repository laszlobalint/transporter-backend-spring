import { Passenger } from './passenger.model';

export interface Booking {
    id: number;
    departureTime: Date;
    locationSerbia: string;
    locationHungary: string;
    passenger: Passenger;
}
