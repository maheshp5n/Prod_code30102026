import { Observable } from 'rxjs';
import { ObjectDefinition } from './model';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class ReportService {

  constructor(private _http: HttpClient) { }

  public getFetch(tname, startDate, endDate): Observable<any> {
    return this._http.get<any>('api/zone/fetchData/' + tname + '/' + startDate + '/' + endDate);
  }

  public getObjectNames(): Observable<Array<ObjectDefinition>> {
    return this._http.get<Array<ObjectDefinition>>('api/object');
  }

  downloadFileSystem(tname, startDate, endDate): Observable<HttpResponse<string>> {
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'application/octet-stream; charset=utf-8');
    return this._http.get('api/zone/fetchData/' + tname + '/' + startDate + '/' + endDate, {
      headers: headers,
      observe: 'response',
      responseType: 'text'
    });
  }

}
