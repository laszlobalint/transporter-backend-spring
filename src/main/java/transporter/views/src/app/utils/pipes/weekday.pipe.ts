import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'weekday',
})
export class WeekdayPipe implements PipeTransform {
    transform(value: string): string {
        if (value) {
            return `${this.WEEKDAYS[value.toUpperCase()]}`;
        } else {
            return value;
        }
    }

    private WEEKDAYS = {
        MONDAY: 'hétfő',
        TUESDAY: 'kedd',
        WEDNESDAY: 'szerda',
        THURSDAY: 'csütörtök',
        FRIDAY: 'péntek',
        SATURDAY: 'szombat',
        SUNDAY: 'vasárnap',
    };
}
