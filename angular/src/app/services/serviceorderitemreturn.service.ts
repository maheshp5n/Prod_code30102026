import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
@Injectable({ providedIn: 'root' })
export class ServiceOrderitemReturnService {
    private baseurl = 'api/serviceorderreturn';
    constructor(private http: HttpClient) { }

    public getPendingToReturn(): Observable<Array<any>> {
        return this.http.get<Array<any>>(this.baseurl);
    }
    public postPendingToReturn(data: any): Observable<any> {
        return this.http.post(this.baseurl, data);
    }
    public getPendingToReceivve(): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/pending`);
    }
    public postPendingToReceive(data: Array<any>): Observable<any> {
        return this.http.post(`${this.baseurl}/pending`, data);
    }
}
