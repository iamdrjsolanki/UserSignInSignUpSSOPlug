import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LocalStorageService } from "ngx-webstorage";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        private localStorageService: LocalStorageService
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.localStorageService.retrieve('token');
        console.log("JWT: " + token);
        if(token) {
            const clonedReq = req.clone({
                headers: req.headers.set("Authorization", "Bearer " + token)
            });
            return next.handle(clonedReq);
        } else {
            return next.handle(req);
        }
    }

}