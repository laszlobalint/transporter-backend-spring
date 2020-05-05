import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from '../_models';

@Injectable()
export class BookingService {
  public readonly BOOKING_API_URL = 'http://localhost:8080/booking';

  constructor(private readonly http: HttpClient) {}

  public fetch(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.BOOKING_API_URL}`);
  }

  public save(booking: Booking): Observable<string> {
    return this.http.post<string>(`${this.BOOKING_API_URL}`, booking, {
      responseType: 'text' as 'json',
    });
  }
}
