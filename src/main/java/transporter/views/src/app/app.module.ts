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
import { DigitPipe } from './utils/pipes/digit.pipe';
import { WeekdayPipe } from './utils/pipes/weekday.pipe';
import { BookingComponent } from './booking/booking.component';
import { HeaderComponent } from './header/header.component';
import { AuthModule } from './auth/auth.module';
import { AuthInterceptor } from './auth/interceptors/auth.interceptor';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        DigitPipe,
        WeekdayPipe,
        BookingComponent,
        HeaderComponent,
    ],
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
        EffectsModule.forRoot(effects),
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        },
        TransportService,
        PassengerService,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
