import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AuthService } from '../_services/auth.service';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [RegisterComponent, LoginComponent, LogoutComponent],
  imports: [BrowserModule, CommonModule, RouterModule, FormsModule, ReactiveFormsModule],
  providers: [AuthService],
  exports: [RegisterComponent, LoginComponent, LogoutComponent],
})
export class AuthModule {}
