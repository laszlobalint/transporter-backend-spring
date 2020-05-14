import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { InputValidation } from '../../_models';
import { EMAIL_REGEX } from '../../app.constants';
import { AuthService } from '../../_services/auth.service';
import { checkPasswords } from '../../shared/utils/validators.utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  public form = new FormGroup({});

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly authService: AuthService,
    private readonly toastrService: ToastrService,
    private readonly router: Router,
  ) {}

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
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
  }

  public onRegister(): void {
    const registerPassenger = { ...this.form.getRawValue(), password: this.form.controls['passwordGroup'].value.password };
    delete registerPassenger.passwordGroup;
    this.authService.register(registerPassenger).subscribe((response) => {
      this.toastrService.success('', `${response.message} Hamarosan átirányítunk a belépési oldalra...`);
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 7000);
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
