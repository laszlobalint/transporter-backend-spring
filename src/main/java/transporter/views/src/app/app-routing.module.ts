import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePageComponent } from './home-page/home-page.component';
import { BookingComponentComponent } from './booking-component/booking-component.component';
import { RegisterComponentComponent } from './register-component/register-component.component';

const routes: Routes = [
    { path: 'booking/:id', component: BookingComponentComponent },
    { path: 'register', component: RegisterComponentComponent },
    { path: '', component: HomePageComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
