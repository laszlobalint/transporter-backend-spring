import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { reducers, effects } from './store';
import { AuthGuard } from './auth/guards/auth.guard';
import { AppComponent } from './app.component';
import { PassengerService } from './_services/passenger.service';
import { TransportService } from './_services/transport.service';
import { BookingComponent } from './booking/booking.component';
import { AuthModule } from './auth/auth.module';
import { AuthInterceptor } from './auth/interceptors/auth.interceptor';
import { environment } from '../environments/environment';
import { BookingService } from './_services/booking.service';
import { PassengerModule } from './passenger/passenger.module';
import { HomeModule } from './home/home.module';
import { SharedModule } from './shared/shared.module';
import { ErrorInterceptorProvider } from './auth/interceptors/http-response.interceptor';

@NgModule({
  declarations: [AppComponent, BookingComponent],
  imports: [
    AuthModule,
    HomeModule,
    PassengerModule,
    SharedModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
      maxOpened: 1,
    }),
    StoreModule.forRoot(reducers, {
      runtimeChecks: {
        strictStateImmutability: false,
        strictActionImmutability: false,
      },
    }),
    !environment.production ? StoreDevtoolsModule.instrument() : [],
    EffectsModule.forRoot(effects),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    ErrorInterceptorProvider,
    JwtHelperService,
    TransportService,
    PassengerService,
    BookingService,
    AuthGuard,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
