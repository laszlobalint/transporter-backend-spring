import { Action } from '@ngrx/store';
import { Transport } from '../../models/transport.model';

export enum TransportActionTypes {
    FetchTransport = '[Transport] Fetch Tranports',
    FetchTransportSuccess = '[Transport] Fetch Transport Success',
    FetchTransportFailure = '[Transport] Fetch Transport Failure',
}
export class FetchTransport implements Action {
    readonly type = TransportActionTypes.FetchTransport;
}

export class FetchTransportSuccess implements Action {
    readonly type = TransportActionTypes.FetchTransport;

    constructor(payload: Transport[]) {}
}

export class FetchTransportFailure implements Action {
    readonly type = TransportActionTypes.FetchTransport;

    constructor(payload: string) {}
}

export type TransportActions =
    | FetchTransport
    | FetchTransportSuccess
    | FetchTransportFailure;
