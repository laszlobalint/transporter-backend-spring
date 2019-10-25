import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import * as fromCore from '../store';

import { TransportState } from '../store/reducers/transport.reducer';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
    transports = [];

    constructor(private readonly coreStore: Store<fromCore.CoreState>) {
        this.coreStore
            .select<TransportState>(state => state.transports)
            .subscribe((transportState: TransportState) =>
                transportState.transports.length > 0
                    ? (this.transports = transportState.transports)
                    : (this.transports = undefined)
            );
    }

    ngOnInit() {
        this.coreStore.dispatch(new fromCore.FetchTransport());
    }
}
