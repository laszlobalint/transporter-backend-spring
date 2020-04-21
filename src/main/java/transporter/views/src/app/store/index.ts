import { ActionReducerMap } from '@ngrx/store';
import * as fromTansport from '../store/reducers/transport.reducer';

export interface State {
    transports: fromTansport.TransportState;
}

export const reducers: ActionReducerMap<State> = {
    transports: fromTansport.reducer,
};

export * from './actions';
export * from './effects';
