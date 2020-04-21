import { createAction, props } from '@ngrx/store';
import { Transport } from 'src/app/_models';

export const FetchTransport = createAction('[Transport] Fetch Tranports');
export const FetchTransportSuccess = createAction(
    '[Transport] Fetch Transport Success',
    props<{ transports: Transport[] }>()
);
export const FetchTransportFailure = createAction(
    '[Transport] Fetch Transport Failure',
    props<{ error: string }>()
);
