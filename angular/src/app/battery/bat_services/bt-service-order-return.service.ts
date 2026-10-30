import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtServiceOrderReturnService {

  baseUrl = 'bt/api/serviceorder/return';
  constructor(private _http:HttpClient) { }

  public getPendingLocation():Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseUrl}/pendinglocation`);
  }
  public getPendingItems(locatinId:number):Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseUrl}/pendingitems/${locatinId}`);
  }

  public postReturn(request):Observable<any> {
    return this._http.post<any>(this.baseUrl,request);
  }
  public getPage(filter?: any, pageInfo?: string): Observable<Page<any>> {
    let url = this.baseUrl;
    if (filter) {
      url = `${url}?${filter}`;
    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<any>>(url);
  }
  
  public getSingle(id):Observable<any>{
    return this._http.get(`${this.baseUrl}/${id}`);
  }
  public receiveReturnOrder(request):Observable<any>{
    return this._http.put(`${this.baseUrl}`,request);
  }

  public getItems(id,filter):Observable<any>{
    return this._http.get<any>(`${this.baseUrl}/items/${id}?${filter}`);
  }

  public getpendingitemtoreceive():Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/pendingitemtoreceive`);
  }

}
