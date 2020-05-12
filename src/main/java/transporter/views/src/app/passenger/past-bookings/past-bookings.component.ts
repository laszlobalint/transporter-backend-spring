import { Component, OnInit } from '@angular/core';
import { Booking, LocationHungary, LocationSerbia } from './../../_models';
import { BookingService } from '../../_services/booking.service';

@Component({
  selector: 'app-past-bookings',
  templateUrl: './past-bookings.component.html',
})
export class PastBookingsComponent implements OnInit {
  public pastBookings?: Booking[];
  public LocationHungary = LocationHungary;
  public LocationSerbia = LocationSerbia;

  constructor(private readonly bookingService: BookingService) {}

  public ngOnInit(): void {
    this.bookingService.fetchInactive().subscribe((pastBookings) => (this.pastBookings = pastBookings));
  }
}
