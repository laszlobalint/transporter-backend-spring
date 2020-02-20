import { PassengerService } from './../_services/passenger.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { checkPasswords } from '../_utils/validators.utils';
import { Passenger } from '../_models';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
    public form = new FormGroup({});

    constructor(
        private readonly formBuilder: FormBuilder,
        private readonly passengerService: PassengerService
    ) {}

    public ngOnInit(): void {
        this.form = this.formBuilder.group({
            name: [
                '',
                [
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(100),
                ],
            ],
            plainPassword: ['', [Validators.required, Validators.plainPassword]],
            phoneNumber: [
                '',
                [
                    Validators.required,
                    Validators.minLength(6),
                    Validators.maxLength(20),
                ],
            ],
            passwordGroup: this.formBuilder.group(
                {
                    password: [
                        '',
                        [
                            Validators.required,
                            Validators.minLength(8),
                            Validators.maxLength(100),
                        ],
                    ],
                    passwordConfirm: [
                        '',
                        [
                            Validators.required,
                            Validators.minLength(8),
                            Validators.maxLength(100),
                        ],
                    ],
                },
                { validator: checkPasswords }
            ),
        });
    }

    public onRegister(): void {
        this.passengerService
            .savePassenger({
                name: this.form.value.name,
                plainPassword: this.form.value.plainPassword,
                password: this.form.controls['passwordGroup'].value.password,
            })
            .subscribe((response: Passenger) => {
                console.log(response);
            });
    }
}
