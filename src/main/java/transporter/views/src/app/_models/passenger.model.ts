export interface Passenger {
  id: number;
  name: string;
  phoneNumber: string;
  plainPassword: string;
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
