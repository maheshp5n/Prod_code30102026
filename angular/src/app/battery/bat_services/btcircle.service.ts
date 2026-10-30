import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CirclesForList, CirclesRequest, CirclesResponse, ContactForList, Page, SSAResponse, Zonedetails } from '../../services/model';
import { BtCirclesRequest, BtCirclesResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtcircleService {
  private baseUrl = 'bt/api/circle';
  private baseUrlRecent = 'bt/api/circle/recent';
  private baseUrlContactsForList = 'bt/api/contacts/forlist/serviceresource/cirlce';


  constructor(private _http: HttpClient) { }

  public getCircles(filter?:string,pageInfo?:string): Observable<Page<BtCirclesResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BtCirclesResponse>>(url);
  }

  public getCircleById(circleName): Observable<BtCirclesResponse> {
    return this._http.get<BtCirclesResponse>(this.baseUrl + '/' + circleName);
  }

  public postCircles(circles: BtCirclesRequest): Observable<BtCirclesResponse> {
    return this._http.post<BtCirclesResponse>(this.baseUrl, circles);
  }

  public getRecentCircles(page: number, column: string, order: string): Observable<Page<BtCirclesResponse>> {
    return this._http.get<Page<BtCirclesResponse>>(this.baseUrlRecent + '?page=' + page + '&size=20' + '&' + 'sort=' + column + ',' + order);
  }

  public putCircles(id: string, circlesRequest: BtCirclesRequest): Observable<BtCirclesResponse> {
    return this._http.put<BtCirclesResponse>(this.baseUrl + '/' + id, circlesRequest);
  }

  public getContactListForCircle(): Observable<Array<ContactForList>> {
    return this._http.get<Array<ContactForList>>(this.baseUrlContactsForList);
  }


  public getSSAListByCircleName(circleName: string, page: number): Observable<Page<SSAResponse>> {
    return this._http.get<Page<SSAResponse>>(this.baseUrl + '/related?circleName=' + encodeURI(circleName) + '&page=' + page + '&size=5');
  }
  public getAllSSAListByCircleName(circleName: string): Observable<Page<SSAResponse>> {
    return this._http.get<Page<SSAResponse>>(this.baseUrl + '/related?circleName=' + encodeURI(circleName));
  }
  public getCircleListByLegalEnt(id:number): Observable<Array<CirclesForList>> {
    return this._http.get<Array<CirclesForList>>(this.baseUrl+'/circle/legalentity/'+id);
  }
  // search

  public getSearchResult(searchparam): Observable<Array<BtCirclesResponse>> {
    return this._http.get<Array<BtCirclesResponse>>(`${this.baseUrl}/advancesearch`,{params:searchparam});
  }
  public getBatteryZoneForList(domainid): Observable<Array<string>> {
    return this._http.get<Array<string>>('api/zone/forlist/bydomain/'+domainid);
  }

  // method add by pawan for customer inventory
  public getCircleForList(): Observable<Array<CirclesForList>> {
    return this._http.get<Array<CirclesForList>>(this.baseUrl+'/forlist');
  }
}
