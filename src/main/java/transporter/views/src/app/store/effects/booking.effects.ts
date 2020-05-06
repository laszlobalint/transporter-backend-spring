import { Router } from '@angular/router';
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
      ofType(fromActions.FetchBookings, fromActions.LoginPassengerSuccess),
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

  public saveBooking$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.SaveBooking),
      mergeMap(({ booking }) =>
        this.bookingService.save(booking).pipe(
          map((booking) => {
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 2000);
            this.toastrService.success('', 'Fuvar sikeresen mentve! Ellenőrizd az e-mail fiókodat.');
            return fromActions.SaveBookingSuccess({
              booking,
            });
          }),
          catchError((error) => {
            this.toastrService.error('', error.error);
            return of(fromActions.SaveBookingFailure({ error }));
          }),
        ),
      ),
    ),
  );

  public deleteBooking$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.DeleteBooking),
      mergeMap(({ id }) =>
        this.bookingService.delete(id).pipe(
          map((deleteBookingDto) => {
            this.toastrService.success('', 'Foglalásod sikeresen törölve!');
            return fromActions.DeleteBookingSuccess({
              deleteBookingDto,
            });
          }),
          catchError((error) => {
            this.toastrService.error('', error.error);
            return of(fromActions.DeleteBookingFailure({ error }));
          }),
        ),
      ),
    ),
  );

  constructor(
    private readonly actions$: Actions,
    private readonly bookingService: BookingService,
    private readonly toastrService: ToastrService,
    private readonly router: Router,
  ) {}
}
