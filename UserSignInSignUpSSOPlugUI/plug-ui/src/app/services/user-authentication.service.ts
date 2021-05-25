import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { AuthenticationRequest } from "../models/authentication-request.model";
import { RegisterUser } from "../models/register-user.model";
import { map, catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { RegisteredUserResponse } from "../models/registered-user-response.model";
import { UserAuthenticationResponse } from "../models/user-authentication-response.model";

@Injectable({providedIn: 'root'})
export class UserAuthenticationService {

    private SERVER_URL: string = environment.serverUrl;

    constructor(
        private http: HttpClient
    ) {}

    registerUser(email: string, displayName: string, password: string, userRole: string): Observable<RegisteredUserResponse> {
        const user: RegisterUser = {
            email: email,
            displayName: displayName,
            password: password,
            role: userRole,
            provider: 'appDefault'
        };
        console.log(user);

        const httpOptions = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
          };
        const body = JSON.stringify(user);
        console.log("JSON="+body);
        
        return this.http
            .post<RegisteredUserResponse>(`${this.SERVER_URL}/api/register-user`, body, httpOptions)
            .pipe(
                map((data: RegisteredUserResponse) => {
                    return data;
                }),
                catchError(error => {
                    console.log(error)
                    return throwError('Something went wrong registering user');
                })
            );
    }

    loginUser(email: string, password: string) {
        const authenticationReq: AuthenticationRequest = {
            username: email,
            password: password
        }
        console.log(authenticationReq);

        const httpOptions = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
          };
        const body = JSON.stringify(authenticationReq);
        console.log("JSON="+body);
        
        return this.http
            .post<UserAuthenticationResponse>(`${this.SERVER_URL}/api/authenticate`, body, httpOptions)
            .pipe(
                map((data: UserAuthenticationResponse) => {
                    return data;
                }),
                catchError(error => {
                    console.log(error)
                    return throwError('Something went wrong authenticating user');
                })
            );
    }

}