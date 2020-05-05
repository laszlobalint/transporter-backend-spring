import { Injectable } from '@angular/core';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { map, catchError, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { TransportService } from '../../_services/transport.service';
import * as fromActions from '../actions';

@Injectable()
export class TransportEffects {
  public fetchTransport$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.FetchTransports),
      mergeMap(() =>
        this.transportService.fetch().pipe(
          map((transports) => {
            this.toastrService.success('', 'Fuvarok betöltve!');
            return fromActions.FetchTransportsSuccess({
              transports,
            });
          }),
          catchError((error) => {
            this.toastrService.error('', error.error);
            return of(fromActions.FetchTransportsFailure({ error }));
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
