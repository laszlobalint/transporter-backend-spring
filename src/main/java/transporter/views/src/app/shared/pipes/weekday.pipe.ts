import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'weekday',
})
export class WeekdayPipe implements PipeTransform {
  public transform(value: Date): string {
    return `${this.WEEKDAYS[new Date(value[0], value[1], value[2]).getDay()]}`;
  }

  private WEEKDAYS = {
    1: 'hétfő',
    2: 'kedd',
    3: 'szerda',
    4: 'csütörtök',
    5: 'péntek',
    6: 'szombat',
    7: 'vasárnap',
  };
}
