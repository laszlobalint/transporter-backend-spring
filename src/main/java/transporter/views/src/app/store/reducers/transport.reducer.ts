import { Action, createReducer, on } from '@ngrx/store';
import * as fromActions from '../actions';
import { Transport } from '../../_models';

export interface TransportState {
    transports?: Transport[];
}

export const initialState: TransportState = {
    transports: [],
};

const reducerFunction = createReducer(
    initialState,
    on(fromActions.FetchTransportSuccess, (state, { transports }) => ({
        ...state,
        transports,
    }))
);

export interface State {
    transports: TransportState;
}

export const transportsFeatureKey = 'transports';

export function reducer(
    state: TransportState | undefined,
    action: Action
): TransportState {
    return reducerFunction(state, action);
}
