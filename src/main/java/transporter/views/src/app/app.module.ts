import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { reducers, resetStateMetaReducer, effects } from './store';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';

import { TransportService } from './services/transport.service';

import { DigitPipe } from './utils/pipes/digit.pipe';
import { WeekdayPipe } from './utils/pipes/weekday.pipe';

@NgModule({
    declarations: [AppComponent, HomePageComponent, DigitPipe, WeekdayPipe],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        StoreModule.forRoot(reducers, {
            metaReducers: [resetStateMetaReducer],
        }),
        EffectsModule.forRoot(effects),
    ],
    providers: [TransportService],
    bootstrap: [AppComponent],
})
export class AppModule {}
