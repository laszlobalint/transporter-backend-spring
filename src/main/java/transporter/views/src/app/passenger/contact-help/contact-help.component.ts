import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EMAIL_REGEX } from '../../app.constants';
import { ContactHelp, InputValidation } from './../../_models/passenger.model';
import { PassengerService } from '../../_services/passenger.service';

@Component({
  selector: 'app-contact-help',
  templateUrl: './contact-help.component.html',
})
export class ContactHelpComponent implements OnInit {
  public now = new Date();
  public form = new FormGroup({});

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly passengerService: PassengerService,
    private readonly toastrService: ToastrService,
    private readonly router: Router,
  ) {}

  public ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(80)]],
      email: ['', [Validators.required, Validators.email, Validators.pattern(EMAIL_REGEX)]],
      subject: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      message: ['', [Validators.required]],
    });
  }

  public onSend(): void {
    this.passengerService.sendMessage(this.form.getRawValue() as ContactHelp).subscribe((response) => {
      this.toastrService.success('', `${response.message} Átirányítunk a főoldalra...`);
      setTimeout(() => {
        this.router.navigate(['/']);
      }, 5000);
    });
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
