export interface Passenger {
    id: number;
    name: string;
    phoneNumber: string;
    plainPassword: string;
    isActivated: boolean;
    bookingCount: number;
}

export interface RegisterPassenger {
    name: string;
    email: string;
    password: string;
    phoneNumber: string;
}

export interface LoginPassenger {
    email: string;
    plainPassword: string;
}

export interface PasswordValidation {
    notSame: boolean;
}
