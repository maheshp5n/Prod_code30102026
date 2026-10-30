import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
@Injectable({ providedIn: 'root' })
export class PagefilterService {
    private baseurl = 'api/page';
    constructor(private http: HttpClient) { }

    getPagefilter(pageurl): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/filter/${pageurl}`);
    }
    pinExisting(id): Observable<any> {
        return this.http.post(`${this.baseurl}/pin/${id}`, {});
    }
}
