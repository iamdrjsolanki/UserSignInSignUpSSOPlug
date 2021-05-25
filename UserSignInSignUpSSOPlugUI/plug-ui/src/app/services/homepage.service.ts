import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";

@Injectable({providedIn: 'root'})
export class HomepageService {

    private SERVER_URL: string = environment.serverUrl;

    constructor(
        private http: HttpClient
    ) {}

    helloWorld() {
        const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
        return this.http
            .get(`${this.SERVER_URL}/`, { headers, responseType: 'text' });
    }

    restricted() {
        const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
        return this.http
            .get(`${this.SERVER_URL}/restricted`, { headers, responseType: 'text' });
    }

}