import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { TransportService } from './services/transport.service';

@NgModule({
    declarations: [AppComponent, HomePageComponent],
    imports: [BrowserModule, AppRoutingModule],
    providers: [TransportService],
    bootstrap: [AppComponent],
})
export class AppModule {}
