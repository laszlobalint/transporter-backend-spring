import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map } from 'rxjs/operators';
import { Observable, Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Passenger, LocationHungary, Transport, LocationSerbia } from './../_models';
import * as fromRoot from '../store';
import { BookingService } from '../_services/booking.service';

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
  LocationHungary = LocationHungary;
  LocationSerbia = LocationSerbia;
  public selectedLocationHungary: string;
  public selectedLocationSerbia: string;

  constructor(
    private readonly bookingService: BookingService,
    private readonly rootStore: Store<fromRoot.State>,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly toastrService: ToastrService,
  ) {
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
      this.bookingService
        .save({
          transport: this.transport,
          locationHungary: this.selectedLocationHungary,
          locationSerbia: this.selectedLocationSerbia,
        })
        .subscribe((response) => {
          this.router.navigate(['/']);
          this.toastrService.success('', response);
        });
    }
  }
}
