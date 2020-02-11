import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { reducers, resetStateMetaReducer, effects } from './store';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { TransportService } from './_services/transport.service';

import { DigitPipe } from './utils/pipes/digit.pipe';
import { WeekdayPipe } from './utils/pipes/weekday.pipe';
import { BookingComponent } from './booking/booking.component';
import { RegisterComponent } from './register/register.component';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        DigitPipe,
        WeekdayPipe,
        BookingComponent,
        RegisterComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        StoreModule.forRoot(reducers, {
            metaReducers: [resetStateMetaReducer],
        }),
        EffectsModule.forRoot(effects),
    ],
    providers: [TransportService],
    bootstrap: [AppComponent],
})
export class AppModule {}
