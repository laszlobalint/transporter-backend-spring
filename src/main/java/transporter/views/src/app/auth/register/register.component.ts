import { PassengerService } from '../../_services/passenger.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Passenger, InputValidation } from '../../_models';
import { checkPasswords } from '../../_utils/pipes/validators.utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  public form = new FormGroup({});

  constructor(private readonly formBuilder: FormBuilder, private readonly passengerService: PassengerService) {}

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: [
        '',
        [Validators.required, Validators.email, Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)],
      ],
      phoneNumber: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      passwordGroup: this.formBuilder.group(
        {
          password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(100)]],
          passwordConfirm: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(100)]],
        },
        { validator: checkPasswords },
      ),
    });
  }

  public onRegister(): void {
    this.passengerService
      .save({
        ...this.form.value,
        password: this.form.controls['passwordGroup'].value.password,
      })
      .subscribe((response: Passenger) => {});
  }

  public showInputValidityStatus(field: string): InputValidation {
    let isFieldValid: boolean;
    if (field === 'password' || field === 'passwordConfirm') {
      if (
        this.form.controls['passwordGroup'].get('passwordConfirm').dirty &&
        this.form.controls['passwordGroup'].get('passwordConfirm').touched
      ) {
        this.form.controls['passwordGroup'].get('passwordConfirm').errors ? (isFieldValid = false) : (isFieldValid = true);
        return {
          'is-invalid': !isFieldValid,
          'is-valid': isFieldValid,
        };
      }
    } else {
      if (this.form.controls[field].dirty && this.form.controls[field].touched) {
        isFieldValid = this.form.controls[field].valid;
        return {
          'is-invalid': !isFieldValid,
          'is-valid': isFieldValid,
        };
      }
    }
  }
}
