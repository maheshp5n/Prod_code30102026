import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
@Injectable({ providedIn: 'root' })
export class PagefiltersetupService {
    private baseurl = 'api/profilefiltersetup';
    constructor(private http: HttpClient) {

    }

    getAllUrls(): Observable<Array<any>> {

        return this.http.get<Array<any>>(`${this.baseurl}/allurl`);
    }
    getAllProfile(): Observable<Array<any>> {

        return this.http.get<Array<any>>(`${this.baseurl}/profile`);

    }
    getPageFilterByUrlAndProfile(profileid, nvId) {

        return this.http.get<Array<any>>(`${this.baseurl}/urlwithprofile?profileid=${profileid}&navigationid=${nvId}`);

    }
    saveDetails(data) {
        return this.http.post(`${this.baseurl}`, data);
    }


}
