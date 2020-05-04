import { Passenger } from './../_models/passenger.model';
import { map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import * as fromRoot from '../store';
import { Store } from '@ngrx/store';
import { Transport } from '../_models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  public passenger$: Observable<Passenger>;
  public transports$: Observable<Transport[]>;

  constructor(private readonly rootStore: Store<fromRoot.State>) {
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
    this.transports$ = this.rootStore.select('transports').pipe(map((state) => state.transports));
  }

  public ngOnInit(): void {
    this.rootStore.dispatch(fromRoot.FetchTransport());
  }
}
