import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message, Passenger, ContactHelp, UpdatePassengerDto } from './../_models';

@Injectable()
export class PassengerService {
  public readonly PASSENGER_API_URL = 'http://localhost:8080/passenger';

  constructor(private readonly http: HttpClient) {}

  public fetch(id: string): Observable<Passenger> {
    return this.http.get<Passenger>(`${this.PASSENGER_API_URL}/${+id}`);
  }

  public update(updatePassengerDto: UpdatePassengerDto): Observable<Passenger> {
    return this.http.put<Passenger>(`${this.PASSENGER_API_URL}/${updatePassengerDto.id}`, updatePassengerDto);
  }

  public sendMessage(contactHelp: ContactHelp): Observable<Message> {
    return this.http.post<Message>(`${this.PASSENGER_API_URL}/contact`, contactHelp);
  }
}
