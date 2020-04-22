import { map } from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import * as fromRoot from '../store';
import { Store } from '@ngrx/store';
import { Transport } from '../_models';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit, OnDestroy {
    public transports: Transport[];
    private transportsSubscription: Subscription;

    constructor(private readonly rootStore: Store<fromRoot.State>) {}

    public ngOnInit(): void {
        this.rootStore.dispatch(fromRoot.FetchTransport());

        this.transportsSubscription = this.rootStore
            .select('transports')
            .pipe(
                map((state) => {
                    this.transports = state.transports;
                })
            )
            .subscribe();
    }

    public ngOnDestroy(): void {
        this.transportsSubscription.unsubscribe();
    }
}
