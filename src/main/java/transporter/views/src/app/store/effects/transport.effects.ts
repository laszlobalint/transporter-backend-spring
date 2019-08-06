import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { TransportService } from '../../services/transport.service';
import * as fromTransport from '../actions';
import { map, switchMap } from 'rxjs/operators';

@Injectable()
export class TransportEffects {
    @Effect()
    public fetchTransport$ = this.actions$.pipe(
        ofType(fromTransport.TransportActionTypes.FetchTransport),
        switchMap(() =>
            this.transportService
                .fetchTransports()
                .pipe(map((transports: Transport[]) => console.log(transports)))
        )
    );

    constructor(
        private readonly actions$: Actions,
        private readonly transportService: TransportService
    ) {}
}
