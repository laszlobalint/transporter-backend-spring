import { Booking } from './booking.model';

export interface Transport {
    id: number;
    route: string;
    departureTime: Date;
    freeSeats: number;
    bookings?: Booking;
}

export class Locations {
    public static SERBIA = [
        'Szabadka, Mars téri városi piac mögött található Sing Sing szórakozóhely előtti parkoló',
        'Szabadka, Szegedi út és Pap Pál utca sarka, Lidl áruház előtti buszmegálló',
        'Szabadka, Szegedi út és Bože Šarčević utca sarka, rendőrkapitányság előtti buszmegálló',
        'Szabadka, Szegedi út és Partizán bázisok utca sarka, 024 Market és Solid pálya előtti buszmegálló',
        'Nagyradanovác, Szegedi út és Testvériség-egység körút sarka, nagyradanováci buszmegálló',
        'Palics, Horgosi út, vasúti átjáró környéke, víztorony előtti buszmegálló',
        'Palics, Horgosi út és Ludasi utca sarka, Ábrahám vendéglő előtti parkoló',
    ];
    public static HUNGARY = [
        'Szeged, Makszim Gorkij utca és Đure Đaković sarka, Új Városháza előtti buszmegálló',
        'Szeged, Dugonics téri TESCO Expressz ("Kis Tesco") előtti autóparkoló',
        'Szeged, Petőfi Sándor sugárút és Nemes Takács utca sarkán lévő Burek Pékség előtt',
        'Szeged, Petőfi Sándor sugárút és Rákóczi utca sarkán lévő Gringos étterem közelében lévő buszmegálló',
        'Szeged, Szabadkai úton lévő Városi Műjégpálya és Shell töltöállomás előtti buszmegálló',
    ];
}
