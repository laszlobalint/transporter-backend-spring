import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import * as fromRoot from '../../store';
import { EMAIL_REGEX } from '../../app.constants';
import { InputValidation } from '../../_models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  public form = new FormGroup({});

  constructor(private readonly formBuilder: FormBuilder, private readonly rootStore: Store<fromRoot.State>) {}

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email, Validators.pattern(EMAIL_REGEX)]],
      plainPassword: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(80)]],
    });
  }

  public onLogin(): void {
    const loginPassenger = this.form.getRawValue();
    this.rootStore.dispatch(
      fromRoot.LoginPassenger({
        loginPassenger,
      }),
    );
  }

  public showInputValidityStatus(field: string): InputValidation {
    let isFieldValid: boolean;
    if (this.form.controls[field].dirty && this.form.controls[field].touched) {
      isFieldValid = this.form.controls[field].valid;
      return {
        'is-invalid': !isFieldValid,
        'is-valid': isFieldValid,
      };
    }
  }
}
