import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PastBookingsComponent } from './past-bookings/past-bookings.component';
import { EditPassengerComponent } from './edit-passenger/edit-passenger.component';
import { ContactHelpComponent } from './contact-help/contact-help.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [PastBookingsComponent, EditPassengerComponent, ContactHelpComponent],
  imports: [BrowserModule, CommonModule, RouterModule, FormsModule, ReactiveFormsModule, SharedModule],
})
export class PassengerModule {}
