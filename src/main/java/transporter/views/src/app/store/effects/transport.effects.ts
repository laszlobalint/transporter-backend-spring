import { Injectable } from '@angular/core';
import { Actions, Effect, ofType, createEffect } from '@ngrx/effects';
import { map, catchError, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { TransportService } from '../../_services/transport.service';
import * as fromActions from '../actions';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class TransportEffects {
  @Effect()
  public fetchTransport$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.FetchTransport),
      mergeMap(() =>
        this.transportService.fetch().pipe(
          map((transports) => {
            this.toastrService.success('', 'Fuvarok betöltve!');
            return fromActions.FetchTransportSuccess({
              transports,
            });
          }),
          catchError((error) => {
            this.toastrService.error('', error.error);
            return of(fromActions.FetchTransportFailure({ error }));
          }),
        ),
      ),
    ),
  );

  constructor(
    private readonly actions$: Actions,
    private readonly transportService: TransportService,
    private readonly toastrService: ToastrService,
  ) {}
}
