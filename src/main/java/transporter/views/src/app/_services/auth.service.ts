import { LoginPassengerDto, Passenger } from './../_models/passenger.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {
  public readonly PASSENGER_API_URL = 'http://localhost:8080/passenger';

  constructor(private readonly http: HttpClient) {}

  public login(loginPassenger: LoginPassengerDto): Observable<string> {
    return this.http.post<string>(`${this.PASSENGER_API_URL}/login`, loginPassenger, {
      responseType: 'text' as 'json',
    });
  }

  public fetchInfo(): Observable<Passenger> {
    return this.http.get<Passenger>(`${this.PASSENGER_API_URL}`);
  }
}
