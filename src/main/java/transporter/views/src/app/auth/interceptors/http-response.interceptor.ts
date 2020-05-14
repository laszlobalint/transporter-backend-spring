import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable()
export class HttpResponseInterceptor implements HttpInterceptor {
  constructor(private readonly toastrService: ToastrService) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap((event) => event),
      catchError((error: HttpErrorResponse) => {
        if (error.error) this.toastrService.error(error.error, 'Hiba lépett fel!');
        else this.toastrService.error(error.error.message, 'Hiba lépett fel!');
        throw error;
      }),
    );
  }
}

export const ErrorInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: HttpResponseInterceptor,
  multi: true,
};
