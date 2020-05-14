import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { map } from 'rxjs/operators';
import { Observable, Subscription } from 'rxjs';
import { Passenger, LocationHungary, Transport, LocationSerbia } from './../_models';
import * as fromRoot from '../store';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
})
export class BookingComponent implements OnInit, OnDestroy {
  public passenger$: Observable<Passenger>;
  public transports$: Observable<Transport[]>;
  public transport?: Transport;
  private transportSubscription: Subscription;
  public readonly keys = Object.keys;
  public LocationHungary = LocationHungary;
  public LocationSerbia = LocationSerbia;
  public selectedLocationHungary: string;
  public selectedLocationSerbia: string;

  constructor(private readonly rootStore: Store<fromRoot.State>, private readonly route: ActivatedRoute) {
    this.transports$ = this.rootStore.select('transports').pipe(map((state) => state.transports));
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
  }

  public ngOnInit(): void {
    this.transportSubscription = this.transports$
      .pipe(map((transports) => (this.transport = transports.find((t) => t.id === Number(this.route.snapshot.paramMap.get('id'))))))
      .subscribe();
  }

  public ngOnDestroy(): void {
    this.transportSubscription.unsubscribe();
  }

  public onBook(): void {
    if (confirm('Biztosan le akarod foglalni a fuvart?')) {
      this.rootStore.dispatch(
        fromRoot.SaveBooking({
          booking: {
            transport: this.transport,
            locationHungary: this.selectedLocationHungary,
            locationSerbia: this.selectedLocationSerbia,
          },
        }),
      );
    }
  }
}
