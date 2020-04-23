import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import * as fromRoot from '../../store';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  public form = new FormGroup({});

  constructor(private readonly formBuilder: FormBuilder, private readonly rootStore: Store<fromRoot.State>) {}

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      plainPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(100)]],
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
}
