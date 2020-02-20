export interface Passenger {
    id: number;
    name: string;
    phoneNumber: string;
    plainPassword: string;
    isActivated: boolean;
    bookingCount: number;
}

export interface Register {
    name: string;
    plainPassword: string;
    password: string;
}

export interface PasswordValidation {
    notSame: boolean;
}
