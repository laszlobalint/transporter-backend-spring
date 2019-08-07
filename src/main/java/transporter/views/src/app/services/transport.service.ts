import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transport } from '../models';

@Injectable()
export class TransportService {
    public readonly TRANSPORT_API_URL = 'http://localhost:8080/transport/all';

    constructor(private readonly http: HttpClient) {}

    public fetchTransports(): Observable<Transport[]> {
        return this.http.get<Transport[]>(this.TRANSPORT_API_URL);
    }
}
