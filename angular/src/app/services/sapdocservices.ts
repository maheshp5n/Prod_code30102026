import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class SapDocServices {
    private baseurl = 'api/sapdoc';
    constructor(private http: HttpClient) { }
    public getStkHdr(crmdocnumber): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/stkhdrdoc/${crmdocnumber}`);
    }
    public getStoHdr(crmdocnumber): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/stohdrdoc/${crmdocnumber}`);
    }
}
