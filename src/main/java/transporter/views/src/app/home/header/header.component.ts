import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Passenger } from '../../_models/passenger.model';
import * as fromRoot from '../../store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  public passenger$: Observable<Passenger>;

  constructor(private readonly rootStore: Store<fromRoot.State>) {
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
  }
}
