import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { HomeComponent } from './home/home/home.component';
import { BookingComponent } from './booking/booking.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './auth/guards/auth.guard';
import { LogoutComponent } from './auth/logout/logout.component';
import { EditPassengerComponent } from './passenger/edit-passenger/edit-passenger.component';
import { PastBookingsComponent } from './passenger/past-bookings/past-bookings.component';
import { ContactHelpComponent } from './passenger/contact-help/contact-help.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'home', component: HomeComponent },
  {
    path: 'booking/:id',
    component: BookingComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'passenger/edit',
    component: EditPassengerComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'passenger/past',
    component: PastBookingsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'passenger/contact',
    component: ContactHelpComponent,
    canActivate: [AuthGuard],
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' },
];

const config: ExtraOptions = {
  useHash: false,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
