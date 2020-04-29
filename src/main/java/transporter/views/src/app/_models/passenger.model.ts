export interface Passenger {
  id: number;
  name: string;
  password: string;
  phoneNumber: string;
  email: string;
  isActivated: boolean;
  bookingCount: number;
}

export interface RegisterPassengerDto {
  name: string;
  email: string;
  password: string;
  phoneNumber: string;
}

export interface LoginPassengerDto {
  email: string;
  plainPassword: string;
}

export interface PasswordValidation {
  notSame: boolean;
}

export interface InputValidation {
  'is-invalid': boolean;
  'is-valid': boolean;
}
