export interface Passenger {
    id: number;
    name: string;
    phoneNumber: string;
    email: string;
    picture?: string[];
    isActivated: boolean;
    bookingCount: number;
}
