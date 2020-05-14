import { createAction, props } from '@ngrx/store';
import { Transport } from '../../_models';

export const FetchTransports = createAction('[Transport] Fetch Tranports');
export const FetchTransportsSuccess = createAction('[Transport] Fetch Tranports Success', props<{ transports: Transport[] }>());
