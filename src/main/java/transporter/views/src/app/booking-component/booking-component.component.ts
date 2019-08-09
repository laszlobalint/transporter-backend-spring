import { Component, OnInit } from '@angular/core';

import { Locations } from './../models/transport.model';

@Component({
    selector: 'app-booking-component',
    templateUrl: './booking-component.component.html',
    styleUrls: ['./booking-component.component.scss'],
})
export class BookingComponentComponent implements OnInit {
    hungarianLocations = Locations.HUNGARY;
    serbianLocations = Locations.SERBIA;

    constructor() {}

    ngOnInit() {}
}
