import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { map, mergeMap } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import * as fromActions from '../actions';
import { BookingService } from '../../_services/booking.service';

@Injectable()
export class BookingEffects {
  public fetchBookings$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.FetchBookings, fromActions.LoginPassengerSuccess),
      mergeMap(() =>
        this.bookingService.fetchActive().pipe(
          map((bookings) => {
            this.toastrService.success('', 'Foglalások betöltve!');
            return fromActions.FetchBookingsSuccess({
              bookings,
            });
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
