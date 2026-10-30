import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ServiceReportStatus } from './model';

@Injectable({providedIn:'root'})
export class ServiceReportService {
    constructor(private _http: HttpClient) { }

    public getStatus(): Observable<Array<ServiceReportStatus>> {
        return this._http.get<Array<ServiceReportStatus>>(`api/servicereport/status`);
    }
    
}
