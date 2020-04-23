import { map } from 'rxjs/operators';
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import * as fromRoot from '../store';
import { Store } from '@ngrx/store';
import { Transport } from '../_models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent {
  public transports$: Observable<Transport[]>;

  constructor(private readonly rootStore: Store<fromRoot.State>) {
    this.rootStore.dispatch(fromRoot.FetchTransport());
    this.transports$ = this.rootStore.select('transports').pipe(map((state) => state.transports));
  }
}
