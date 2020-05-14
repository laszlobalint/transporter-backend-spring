import { Booking } from './booking.model';

export interface Transport {
  id: number;
  route: string;
  departureTime: Date;
  freeSeats: number;
  bookings?: Booking[];
}

export const LocationSerbia = {
  NEW_CITY_HALL: 'Szabadka, Makszim Gorkij utca és Đure Đaković sarka, Új Városháza előtti buszmegálló',
  MARKET_LIDL: 'Szabadka, Szegedi út és Pap Pál utca sarka, Lidl áruház előtti buszmegálló',
  POLICE_STATION: 'Szabadka, Szegedi út és Bože Šarčević utca sarka, rendőrkapitányság előtti buszmegálló',
  MARKET_024: 'Szabadka, Szegedi út és Partizán bázisok utca sarka, 024 Market és Solid pálya előtti buszmegálló',
  RADANOVAC: 'Nagyradanovác, Szegedi út és Testvériség-egység körút sarka, nagyradanováci buszmegálló',
  PALIC_WATERTOWER: 'Palics, Horgosi út, vasúti átjáró környéke, víztorony előtti buszmegálló',
  RESTAURANT_ABRAHAM: 'Palics, Horgosi út és Ludasi utca sarka, Ábrahám vendéglő előtti parkoló',
};

export const LocationHungary = {
  MC_DONALDS_DRIVE_THROUGH: "Szeged, Rókusi körúti autós McDonald's gyorsétterem előtti buszmegálló",
  SING_SING_MUSIC_HALL: 'Szeged, Mars téri városi piac mögött található Sing Sing szórakozóhely előtti parkoló',
  MARKET_SMALL_TESCO: 'Szeged, Dugonics téri TESCO Expressz ("Kis Tesco") előtti autóparkoló',
  BAKERY_BUREK: 'Szeged, Petőfi Sándor sugárút és Nemes Takács utca sarkán lévő Burek Pékség előtt',
  GRINGOS_BUS_STOP: 'Szeged, Petőfi Sándor sugárút és Rákóczi utca sarkán lévő Gringos étterem közelében lévő buszmegálló',
  ICERINK: 'Szeged, Szabadkai úton lévő Városi Műjégpálya és Shell töltöállomás előtti buszmegálló',
};
