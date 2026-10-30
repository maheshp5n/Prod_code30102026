import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page, Notifications } from './model';

@Injectable({providedIn:'root'})
export class LayoutService {

  constructor(private _http: HttpClient) { }
  public getPrevNot(): Observable<Page<Notifications>> {
    return this._http.get<Page<Notifications>>(this.baseUrl);
  }
  public getPrevNotsById(id:number): Observable<Page<Notifications>> {
    return this._http.get<Page<Notifications>>(`${this.baseUrl}/${id}`);
  }

  public readAllNotification(): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}`,{});
  }

  private baseUrl = 'api/notification';
  

}
