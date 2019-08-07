import { Transport } from './../models/transport.model';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import * as fromCore from '../store';
import { TransportState } from '../store/reducers/transport.reducer';

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit {
    transports: any;
    constructor(private readonly coreStore: Store<fromCore.CoreState>) {
        this.transports = this.coreStore.select<TransportState>(
            state => state.transports
        );
        this.transports.subscribe((transportState: Transport[]) =>
            console.log(transportState)
        );
    }

    ngOnInit() {
        this.coreStore.dispatch(new fromCore.FetchTransport());
    }
}
