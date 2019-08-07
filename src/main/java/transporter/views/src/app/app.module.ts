import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { TransportService } from './services/transport.service';
import { reducers, resetStateMetaReducer, effects } from './store';
import { EffectsModule } from '@ngrx/effects';

@NgModule({
    declarations: [AppComponent, HomePageComponent],
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
