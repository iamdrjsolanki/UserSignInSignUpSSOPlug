import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LocalStorageService } from "ngx-webstorage";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({providedIn: 'root'})
export class UserSsoAuthenticationService {

    ssoProvider: string;

    private googleAuthorizationEndpoint = "/oauth2/authorization/google";
    private googleTokenEndpoint = "/login/oauth2/code/google";

    private githubAuthorizationEndpoint = "/oauth2/authorization/github";
    private githubTokenEndpoint = "/login/oauth2/code/github";

    private base_sso_url = environment.ssoUrl;
    tokenKey = 'token';

    constructor(
        private localStorageService: LocalStorageService,
        private http: HttpClient
    ) {}
    
    updateToken(token: any) {
        this.localStorageService.store(this.tokenKey, token);
    }

    fetchToken(code: any, state: any) : Observable<any> {
        let tokenEndpoint: string = "";
        if(this.ssoProvider == "google") {
            tokenEndpoint = this.googleTokenEndpoint;
        } else if(this.ssoProvider == "github") {
            tokenEndpoint = this.githubTokenEndpoint;
        }
        return this.http
                .get(this.base_sso_url + tokenEndpoint + '?code=' + code + '&state=' + state);
    }

    getToken() {
        return this.localStorageService.retrieve(this.tokenKey);
    }

    isLoggedIn() : boolean {
        const token = this.getToken();
        console.log("in isloggedin & token: "+ token)
        return token != null;
    }

    onLogin(provider: string) {
        this.ssoProvider = provider;

        if(this.ssoProvider == "google") {
            window.open(this.base_sso_url + this.googleAuthorizationEndpoint, '_self');
        } else if(this.ssoProvider == "github") {
            window.open(this.base_sso_url + this.githubAuthorizationEndpoint, '_self');
        }
    }

}