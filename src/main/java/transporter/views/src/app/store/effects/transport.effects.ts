import { Transport } from '../../models';
import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { TransportService } from '../../services/transport.service';
import * as fromTransport from '../actions';
import { map, switchMap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class TransportEffects {
    @Effect()
    public fetchTransport$ = this.actions$.pipe(
        ofType(fromTransport.TransportActionTypes.FetchTransport),
        switchMap(() =>
            this.transportService.fetchTransports().pipe(
                map(
                    (transports: Transport[]) =>
                        new fromTransport.FetchTransportSuccess(transports)
                ),
                catchError(error =>
                    of(new fromTransport.FetchTransportFailure(error))
                )
            )
        )
    );

    constructor(
        private readonly actions$: Actions,
        private readonly transportService: TransportService
    ) {}
}
