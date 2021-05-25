import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisteredUserResponse } from '../models/registered-user-response.model';
import { UserAuthenticationResponse } from '../models/user-authentication-response.model';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { LocalStorageService } from 'ngx-webstorage';

interface UserRoles {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-register-login',
  templateUrl: './register-login.component.html',
  styleUrls: ['./register-login.component.css']
})
export class RegisterLoginComponent implements OnInit {

  value = 'abc@xyz.com';
  userRoles: UserRoles[] = [
    {value: 'user', viewValue: 'USER'},
    {value: 'admin', viewValue: 'ADMIN'}
  ];
  selectedValue: string = this.userRoles[0].value;
  isLogin = false;
  registeredUser: RegisteredUserResponse;
  authenticatedUser: UserAuthenticationResponse;

  constructor(
    private userAuthService: UserAuthenticationService,
    private localStorageService: LocalStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
  }

  onSubmit (form: NgForm) {
    if(!form.valid) {
      return;
    }
    let email = this.value;
    let password = form.value.password;
    let firstName = form.value.firstName;
    let lastName = form.value.lastName;
    let userRole = this.selectedValue;
    form.reset();
    if(!this.isLogin) {
      this.userAuthService
        .registerUser(email, firstName+" "+lastName, password, userRole)
        .subscribe(
          data => {
            console.log(data);
            this.registeredUser = data;
          },
          error => {
            return "Registration failed.";
          }
        );
    } else {
      this.userAuthService.loginUser(email, password)
        .subscribe(
          data => {
            console.log(data);
            this.authenticatedUser = data;
            this.localStorageService.store('token', this.authenticatedUser.jwt);
            localStorage.setItem('token', this.authenticatedUser.jwt);
            this.router.navigate(['homepage']);
          },
          error => {
            return "Login failed.";
          }
        );
    }
  }

}
