import { Register } from './../_models/passenger.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class PassengerService {
    public readonly PASSENGER_API_URL = 'http://localhost:8080/passenger';

    constructor(private readonly http: HttpClient) {}

    public savePassenger(passenger: Register): Observable<string> {
        return this.http.post<string>(this.PASSENGER_API_URL, passenger, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            }),
        });
    }
}
