import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MSLItemRequestRequest, MSLItemRequestResponse, MSLRequest, MSLRequestResponse, MSLRequestStatus, Page, WareHouseForList } from '../../services/model';
import { BTMslCreateRequest, BtMSLItemRequestRequest, BtMslItemResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtMslrequestService {
  baseUrl = 'bt/api/mslrequest';
  recentVisitUrl = 'bt/api/mslrequest/recentvisit';
  relatedURL = 'bt/api/mslrequest/releted';
  constructor(private http: HttpClient) { }

  public getMSLRequestForList(): Observable<Array<MSLRequestStatus>> {
    return this.http.get<Array<MSLRequestStatus>>(`api/mslrequest/status`)
  }
  public getWhereHouseList(): Observable<Array<WareHouseForList>> {
      return this.http.get<Array<WareHouseForList>>('api/warehouse/forlist')
  }
  public saveMsl(request: BTMslCreateRequest): Observable<MSLRequestResponse> {

      return this.http.post<MSLRequestResponse>(this.baseUrl, request);

  }
  public getMslTable(filter?:string,pageInfo?:string): Observable<Page<MSLRequestResponse>> {
      let url = `${this.baseUrl}?`;
      if (filter) {
  
          url = `${url}&${filter}`;
         
      }
      if (pageInfo) {
          url = `${url}&${pageInfo}`;
      }
      return this.http.get<Page<MSLRequestResponse>>(url);
   
  }
  public getRecentVisit(filter?:string,pageInfo?:string): Observable<Page<MSLRequestResponse>> {
      let url = `${this.recentVisitUrl}?`;
      if (filter) {
  
          url = `${url}&${filter}`;
         
      }
      if (pageInfo) {
          url = `${url}&${pageInfo}`;
      }
      return this.http.get<Page<MSLRequestResponse>>(url);

  }
  public getMslById(mslId: string): Observable<MSLRequestResponse> {
      return this.http.get<MSLRequestResponse>(`${this.baseUrl}/${mslId}`);
  }
  public updateMsl(req: MSLRequest): Observable<MSLRequest> {

      return this.http.put<MSLRequest>(`${this.baseUrl}`, req);

  }

  public getLineItemsById(mslId: number, page: number, column: string, order: string): Observable<Page<BtMslItemResponse>> {
      return this.http.get<Page<BtMslItemResponse>>(this.relatedURL + '/' + mslId + '/lineitems' + '?page=' + page + '&size=20');//&'+"sort="+column+","+order);
  }
  public addItems(mslrequestId:number,item:BtMSLItemRequestRequest):Observable<MSLItemRequestRequest>{
      return this.http.post<MSLItemRequestRequest>(`${this.baseUrl}/${mslrequestId}/additem`,item);
  }
  public submitForApproval(mslrequestid:number,remarks:string){
      return this.http.put(`${this.baseUrl}/${mslrequestid}/submitforapproval?remarks=${encodeURI(remarks)}`,'');
  }
  public getSearchResult(searchparam): Observable<Array<MSLRequestResponse>> {
      return this.http.get<Array<MSLRequestResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
    }
}
