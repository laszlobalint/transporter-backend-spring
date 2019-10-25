import { Component, OnInit } from '@angular/core';

import { Locations } from '../_models/transport.model';

@Component({
    selector: 'app-booking',
    templateUrl: './booking.component.html',
    styleUrls: ['./booking.component.scss'],
})
export class BookingComponent implements OnInit {
    hungarianLocations = Locations.HUNGARY;
    serbianLocations = Locations.SERBIA;

    constructor() {}

    ngOnInit() {}
}
