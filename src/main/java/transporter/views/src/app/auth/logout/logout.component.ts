import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import * as fromRoot from '../../store';

@Component({
  selector: 'app-logout',
  template: ` <div></div> `,
})
export class LogoutComponent implements OnInit {
  constructor(private readonly rootStore: Store<fromRoot.State>) {}

  public ngOnInit(): void {
    this.rootStore.dispatch(fromRoot.LogoutPassenger());
  }
}
