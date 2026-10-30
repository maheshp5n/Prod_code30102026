import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserForList } from './model';

@Injectable({providedIn:'root'})
export class UserforlistService {

  constructor(private _http: HttpClient) { }
  private baseUrl = 'api/users/forlist';

  public getUserForList(): Observable<Array<UserForList>> {
    return this._http.get<Array<UserForList>>(this.baseUrl);
  }
  public getUserForListBT(): Observable<Array<UserForList>> {
    return this._http.get<Array<UserForList>>('bt/api/users/forlist');
  }
}
