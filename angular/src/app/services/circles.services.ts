import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Page, CirclesRequest, CirclesResponse, ContactForList, Zonedetails, SSAResponse, CirclesForList } from './model';
import { Observable } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class CirclesService {
  private baseUrl = 'api/circle';
  private baseUrlRecent = 'api/circle/recent';
  private baseUrlContactsForList = 'api/contacts/forlist/serviceresource/cirlce';


  constructor(private _http: HttpClient) { }

  public getCircles(filter?:string,pageInfo?:string): Observable<Page<CirclesResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<CirclesResponse>>(url);
  }

  public getCircleById(circleName): Observable<CirclesResponse> {
    return this._http.get<CirclesResponse>(this.baseUrl + '/' + circleName);
  }

  public postCircles(circles: CirclesRequest): Observable<CirclesResponse> {
    return this._http.post<CirclesResponse>(this.baseUrl, circles);
  }

  public getRecentCircles(page: number, column: string, order: string): Observable<Page<CirclesResponse>> {
    return this._http.get<Page<CirclesResponse>>(this.baseUrlRecent + '?page=' + page + '&size=20' + '&' + 'sort=' + column + ',' + order);
  }

  public putCircles(id: string, circlesRequest: CirclesRequest): Observable<CirclesResponse> {
    return this._http.put<CirclesResponse>(this.baseUrl + '/' + id, circlesRequest);
  }

  public getContactListForCircle(): Observable<Array<ContactForList>> {
    return this._http.get<Array<ContactForList>>(this.baseUrlContactsForList);
  }


  public getSSAListByCircleName(circleName: string, page: number): Observable<Page<SSAResponse>> {
    return this._http.get<Page<SSAResponse>>(this.baseUrl + '/related?circleName=' + encodeURI(circleName) + '&page=' + page + '&size=5');
  }
  public getCircleListByLegalEnt(id:number): Observable<Array<CirclesForList>> {
    return this._http.get<Array<CirclesForList>>(this.baseUrl+'/circle/legalentity/'+id);
  }
  // search

  public getSearchResult(searchparam): Observable<Array<CirclesResponse>> {
    return this._http.get<Array<CirclesResponse>>(`${this.baseUrl}/advancesearch`,{params:searchparam});
  }
 

}
