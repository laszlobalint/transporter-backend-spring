import { Action } from '@ngrx/store';

import * as fromTransport from '../actions';

import { Transport } from '../../_models';

export interface TransportState {
    transports: Transport[];
}

export const initialState: TransportState = {
    transports: [],
};

function reducerFunction(
    state: TransportState = initialState,
    action: fromTransport.TransportActions
): TransportState {
    switch (action.type) {
        case fromTransport.TransportActionTypes.FetchTransportSuccess: {
            return {
                ...state,
                transports: action.payload,
            };
        }
        default:
            return state;
    }
}

export function reducer(
    state: TransportState | undefined,
    action: Action
): TransportState {
    return reducerFunction(state, action as fromTransport.TransportActions);
}

export const getTransports = (state: TransportState) => state.transports;
