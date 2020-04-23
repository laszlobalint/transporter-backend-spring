import { BrowserModule } from '@angular/platform-browser';
import { AuthService } from 'src/app/_services/auth.service';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [RegisterComponent, LoginComponent, LogoutComponent],
  imports: [BrowserModule, CommonModule, RouterModule, FormsModule, ReactiveFormsModule],
  providers: [AuthService],
  exports: [RegisterComponent, LoginComponent, LogoutComponent],
})
export class AuthModule {}
