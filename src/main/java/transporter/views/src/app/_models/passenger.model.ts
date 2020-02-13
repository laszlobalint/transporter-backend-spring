export interface Passenger {
    id: number;
    name: string;
    phoneNumber: string;
    email: string;
    isActivated: boolean;
    bookingCount: number;
}

export interface Register {
    name: string;
    email: string;
    password: string;
}

export interface PasswordValidation {
    notSame: boolean;
}
