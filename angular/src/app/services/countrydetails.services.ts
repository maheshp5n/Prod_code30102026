import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CountryDetails, StateProvinceDetails } from './model';

@Injectable({ providedIn: 'root' })
export class CountryDetailsServices {
    constructor(private _http: HttpClient) { }
    public getAllCountry(): Observable<Array<CountryDetails>> {
        return this._http.get<Array<CountryDetails>>('api/country');
    }
    public getCountryById(countryId: string): Observable<Array<CountryDetails>> {
        return this._http.get<Array<CountryDetails>>('api/country/' + countryId);
    }
    public getStateByCountryCode(countryId: any): Observable<Array<StateProvinceDetails>> {
        if (countryId != null) {
            return this._http.get<Array<StateProvinceDetails>>('api/country/' + countryId + '/state');
        }
    }
} 
