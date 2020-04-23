import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'digit',
})
export class DigitPipe implements PipeTransform {
  public transform(value: number): string {
    if (value.toString().length <= 1) return `0${value}`;
    return value.toString();
  }
}
