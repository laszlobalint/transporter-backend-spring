import { PasswordValidation } from './../_models/passenger.model';
import { FormGroup } from '@angular/forms';

export function checkPasswords(group: FormGroup): PasswordValidation {
  const password = group.get('password').value;
  const passwordConfirm = group.get('passwordConfirm').value;
  return password === passwordConfirm ? null : { notSame: true };
}
