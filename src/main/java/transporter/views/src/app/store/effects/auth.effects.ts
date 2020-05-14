import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { map, mergeMap } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import * as fromActions from '../actions';
import { AuthService } from '../../_services/auth.service';
import { PassengerService } from '../../_services/passenger.service';

@Injectable()
export class AuthEffects {
  public login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.LoginPassenger),
      mergeMap(({ loginPassenger }) =>
        this.authService.login(loginPassenger).pipe(
          map((token) => {
            this.toastrService.success('', 'Sikeres bejelentkezés!');
            return fromActions.LoginPassengerSuccess({
              token,
            });
          }),
        ),
      ),
    ),
  );

  public logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.LogoutPassenger),
      map(() => {
        this.router.navigate(['/login']);
        return fromActions.DoNothing();
      }),
    ),
  );

  public fetchInfo$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.LoginPassengerSuccess),
      mergeMap(({ token }) =>
        this.passengerService.fetch(this.jwtHelper.decodeToken(token).sub).pipe(
          map((passenger) => {
            this.router.navigate(['/']);
            return fromActions.GetPassengerInfoSuccess({
              passenger,
            });
          }),
        ),
      ),
    ),
  );

  constructor(
    private readonly actions$: Actions,
    private readonly toastrService: ToastrService,
    private readonly authService: AuthService,
    private readonly passengerService: PassengerService,
    private readonly router: Router,
    private readonly jwtHelper: JwtHelperService,
  ) {}
}
