import { Action } from '@ngrx/store';

import { Transport } from '../../_models/transport.model';

export enum TransportActionTypes {
    FetchTransport = '[Transport] Fetch Tranports',
    FetchTransportSuccess = '[Transport] Fetch Transport Success',
    FetchTransportFailure = '[Transport] Fetch Transport Failure',
}
export class FetchTransport implements Action {
    public readonly type = TransportActionTypes.FetchTransport;
}

export class FetchTransportSuccess implements Action {
    public readonly type = TransportActionTypes.FetchTransportSuccess;

    constructor(public payload: Transport[]) {}
}

export class FetchTransportFailure implements Action {
    public readonly type = TransportActionTypes.FetchTransportFailure;

    constructor(public payload: string) {}
}

export type TransportActions =
    | FetchTransport
    | FetchTransportSuccess
    | FetchTransportFailure;
