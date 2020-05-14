import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as fromRoot from '../../store';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private readonly rootStore: Store<fromRoot.State>) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = '';

    this.rootStore
      .select('auth')
      .pipe(
        take(1),
        map((state) => {
          token = state.token;
        }),
      )
      .subscribe();

    req = req.clone({
      setHeaders: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });

    return next.handle(req);
  }
}
