import { RegisterPassengerDto, Passenger } from './../_models/passenger.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class PassengerService {
  public readonly PASSENGER_API_URL = 'http://localhost:8080/passenger';

  constructor(private readonly http: HttpClient) {}

  public save(registerPassenger: RegisterPassengerDto): Observable<Passenger> {
    return this.http.post<Passenger>(this.PASSENGER_API_URL, registerPassenger);
  }
}
