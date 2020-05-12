import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Subscription, Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { InputValidation, Passenger } from '../../_models';
import { EMAIL_REGEX } from '../../app.constants';
import { checkPasswords } from '../../shared/utils/validators.utils';
import * as fromRoot from '../../store';
import { PassengerService } from '../../_services/passenger.service';

@Component({
  selector: 'app-edit-passenger',
  templateUrl: './edit-passenger.component.html',
})
export class EditPassengerComponent implements OnInit {
  public form = new FormGroup({});
  public passenger$: Observable<Passenger>;
  public passengerSubscription: Subscription;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly passengerService: PassengerService,
    private readonly toastrService: ToastrService,
    private readonly router: Router,
    private readonly rootStore: Store<fromRoot.State>,
  ) {
    this.passenger$ = this.rootStore.select('auth').pipe(map((state) => state.passenger));
  }

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      id: [''],
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(80)]],
      email: ['', [Validators.required, Validators.email, Validators.pattern(EMAIL_REGEX)]],
      phoneNumber: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      passwordGroup: this.formBuilder.group(
        {
          password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(80)]],
          passwordConfirm: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(80)]],
        },
        { validator: checkPasswords },
      ),
    });

    this.passengerSubscription = this.passenger$.pipe(filter((passenger) => !!passenger)).subscribe(({ id, name, phoneNumber, email }) => {
      this.form.patchValue({
        id,
        name,
        phoneNumber,
        email,
      });
    });
  }

  public ngOnDestroy(): void {
    this.passengerSubscription.unsubscribe();
  }

  public onSubmit(): void {
    const updatePassenger = { ...this.form.getRawValue(), password: this.form.controls['passwordGroup'].value.password };
    delete updatePassenger.passwordGroup;
    console.log(updatePassenger);
    this.passengerService.update(updatePassenger).subscribe((response) => {
      this.toastrService.success('', `Sikeres módosítás! Jelentkezz be újra...`);
      setTimeout(() => {
        this.router.navigate(['/logout']);
      }, 5000);
    });
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
