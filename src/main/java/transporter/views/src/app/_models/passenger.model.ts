export interface Passenger {
    id: number;
    name: string;
    phoneNumber: string;
    email: string;
    picture?: string[];
    isActivated: boolean;
    bookingCount: number;
}

export interface Register {
    name: string;
    email: string;
    password: string;
    picture?: Blob;
}

export interface PasswordValidation {
    notSame: boolean;
}
