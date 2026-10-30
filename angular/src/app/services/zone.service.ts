import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Zonedetails, Page } from './model';
import { Observable } from 'rxjs';

@Injectable()
export class ZoneService {
  private baseUrl = 'api/zone';
  private baseUrlForList = 'api/zone/forlist';

  constructor(private _http: HttpClient) { }

  public getZone(filter?: string, pageInfo?: string): Observable<Page<Zonedetails>> {
    let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
    return this._http.get<Page<Zonedetails>>(url);
  }

  public getZoneForList(): Observable<Array<Zonedetails>> {
    return this._http.get<Array<Zonedetails>>(this.baseUrlForList);
  }

  public postZone(zone: Zonedetails): Observable<Zonedetails> {
    return this._http.post<Zonedetails>(this.baseUrl, zone);
  }


}
