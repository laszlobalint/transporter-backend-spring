import { Injectable } from '@angular/core';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { map, catchError, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import * as fromActions from '../actions';
import { BookingService } from '../../_services/booking.service';

@Injectable()
export class BookingEffects {
  public fetchBookings$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.LoginPassengerSuccess),
      mergeMap(() =>
        this.bookingService.fetch().pipe(
          map((bookings) => {
            this.toastrService.success('', 'Foglalások betöltve!');
            return fromActions.FetchBookingsSuccess({
              bookings,
            });
          }),
          catchError((error) => {
            this.toastrService.error('', error.error);
            return of(fromActions.FetchBookingsFailure({ error }));
          }),
        ),
      ),
    ),
  );

  constructor(
    private readonly actions$: Actions,
    private readonly bookingService: BookingService,
    private readonly toastrService: ToastrService,
  ) {}
}
