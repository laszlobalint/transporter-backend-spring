import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking, DeleteBookingDto } from '../_models';

@Injectable()
export class BookingService {
  public readonly BOOKING_API_URL = 'http://localhost:8080/booking';

  constructor(private readonly http: HttpClient) {}

  public fetchActive(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.BOOKING_API_URL}`);
  }

  public fetchInactive(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.BOOKING_API_URL}/past/all`);
  }

  public save(booking: Booking): Observable<Booking> {
    return this.http.post<Booking>(`${this.BOOKING_API_URL}`, booking);
  }

  public delete(id: number): Observable<DeleteBookingDto> {
    return this.http.delete<DeleteBookingDto>(`${this.BOOKING_API_URL}/${id}`);
  }
}
