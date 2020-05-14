import { NgModule } from '@angular/core';
import { DigitPipe } from './pipes/digit.pipe';
import { WeekdayPipe } from './pipes/weekday.pipe';

@NgModule({
  declarations: [DigitPipe, WeekdayPipe],
  exports: [DigitPipe, WeekdayPipe],
})
export class SharedModule {}
