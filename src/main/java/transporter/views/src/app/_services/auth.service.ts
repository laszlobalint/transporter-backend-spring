import { ToastrService } from 'ngx-toastr';
import { LoginPassengerDto, RegisterPassengerDto } from './../_models/passenger.model';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, catchError } from 'rxjs/operators';

@Injectable()
export class AuthService {
  public readonly AUTH_API_URL = 'http://localhost:8080/passenger';

  constructor(private readonly http: HttpClient, private readonly toastrService: ToastrService) {}

  public login(loginPassenger: LoginPassengerDto): Observable<string> {
    return this.http.post<string>(`${this.AUTH_API_URL}/login`, loginPassenger, {
      responseType: 'text' as 'json',
    });
  }

  public register(registerPassenger: RegisterPassengerDto): Observable<string> {
    return this.http
      .post<string>(`${this.AUTH_API_URL}`, registerPassenger, {
        responseType: 'text' as 'json',
      })
      .pipe(
        map((response) => response),
        catchError((e: any) => {
          this.toastrService.error('', `Nem sikerült a regisztráció!`);
          return throwError(e);
        }),
      );
  }
}
