import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { map, take } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as fromRoot from '../../store';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private readonly router: Router,
    private readonly rootStore: Store<fromRoot.State>,
    private readonly jwtHelper: JwtHelperService,
  ) {}

  public canActivate(): boolean {
    let isAuthenticated = true;
    this.rootStore
      .select('auth')
      .pipe(
        take(1),
        map((state) => {
          if (this.jwtHelper.isTokenExpired(state.token)) {
            isAuthenticated = false;
            this.router.navigate(['/login']);
          }
        }),
      )
      .subscribe();
    return isAuthenticated;
  }
}
