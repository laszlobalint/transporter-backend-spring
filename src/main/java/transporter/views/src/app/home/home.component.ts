import { map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as fromRoot from '../store';
import { Transport, Passenger, Booking, LocationHungary, LocationSerbia } from './../_models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  public passenger$: Observable<Passenger>;
  public bookings$: Observable<Booking[]>;
  public transports$: Observable<Transport[]>;
  LocationHungary = LocationHungary;
  LocationSerbia = LocationSerbia;

  constructor(private readonly rootStore: Store<fromRoot.State>) {
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
    this.bookings$ = this.rootStore.select('bookings').pipe(map((state) => state.bookings));
    this.transports$ = this.rootStore.select('transports').pipe(map((state) => state.transports));
  }

  public ngOnInit(): void {
    this.rootStore.dispatch(fromRoot.FetchTransports());
  }
}
