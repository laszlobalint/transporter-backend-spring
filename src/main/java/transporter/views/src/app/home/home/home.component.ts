import { map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as fromRoot from '../../store';
import { Transport, Passenger, Booking, LocationHungary, LocationSerbia } from '../../_models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  public passenger$: Observable<Passenger>;
  public bookings$: Observable<Booking[]>;
  public transports$: Observable<Transport[]>;
  public LocationHungary = LocationHungary;
  public LocationSerbia = LocationSerbia;

  constructor(private readonly rootStore: Store<fromRoot.State>) {
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
    this.bookings$ = this.rootStore.select('bookings').pipe(map((state) => this.sortByDate(state.bookings)));
    this.transports$ = this.rootStore.select('transports').pipe(map((state) => this.sortByDate(state.transports)));
  }

  public ngOnInit(): void {
    this.rootStore.dispatch(fromRoot.FetchTransports());
  }

  public onDeleteBooking(id: number): void {
    if (confirm('Biztosan törölni akarod a lefoglalt fuvart?')) {
      this.rootStore.dispatch(fromRoot.DeleteBooking({ id }));
    }
  }

  private sortByDate(array: any[]): any[] {
    return array.sort(
      (a, b) =>
        new Date(a.departureTime[0], a.departureTime[1] - 1, a.departureTime[2], a.departureTime[3], a.departureTime[4]).getTime() -
        new Date(b.departureTime[0], b.departureTime[1] - 1, b.departureTime[2], b.departureTime[3], a.departureTime[4]).getTime(),
    );
  }
}
