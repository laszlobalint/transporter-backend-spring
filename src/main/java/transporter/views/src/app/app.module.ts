import { AuthGuard } from './auth/guards/auth.guard';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { reducers, effects } from './store';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PassengerService } from './_services/passenger.service';
import { TransportService } from './_services/transport.service';
import { BookingComponent } from './booking/booking.component';
import { HeaderComponent } from './header/header.component';
import { AuthModule } from './auth/auth.module';
import { AuthInterceptor } from './auth/interceptors/auth.interceptor';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { WeekdayPipe } from './_utils/pipes/weekday.pipe';
import { DigitPipe } from './_utils/pipes/digit.pipe';

@NgModule({
  declarations: [AppComponent, HomeComponent, DigitPipe, WeekdayPipe, BookingComponent, HeaderComponent],
  imports: [
    AuthModule,
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
    StoreDevtoolsModule.instrument({
      maxAge: 20,
    }),
    EffectsModule.forRoot(effects),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    TransportService,
    PassengerService,
    AuthGuard,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
