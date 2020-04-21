import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { BookingComponent } from './booking/booking.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
    { path: 'booking/:id', component: BookingComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'home', component: HomeComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
