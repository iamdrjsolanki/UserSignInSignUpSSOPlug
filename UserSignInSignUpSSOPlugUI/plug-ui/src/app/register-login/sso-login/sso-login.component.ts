import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { UserSsoAuthenticationService } from 'src/app/services/user-sso-authentication.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-sso-login',
  templateUrl: './sso-login.component.html',
  styleUrls: ['./sso-login.component.css']
})
export class SsoLoginComponent implements OnInit {

  ssoProvider: string;

  constructor(
    private userSsoAuthService: UserSsoAuthenticationService
  ) {}

  ngOnInit(): void {
  }

  onGoogleLogin() {
    this.ssoProvider = "google";
    this.userSsoAuthService.onLogin(this.ssoProvider);
    // this.userAuthService
    //   .googleLogin()
    //   .subscribe(data => {
    //     console.log(data)
    //   })
  }

  onGithubLogin() {
    this.ssoProvider = "github";
    this.userSsoAuthService.onLogin(this.ssoProvider);
    // this.userSsoAuthService
    //   .githubLogin()
    //   .subscribe(data => {
    //     console.log(data)
    //   })
  }

}
