import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page, PODetailsbywcctypeID, WccRequest, WCCResponse, WccSA, WccTypes } from '../../services/model';
import { BtWCCResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtWccServiceService {
  constructor(private http: HttpClient) { }
  baseUrl: string = 'bt/api/wcc';
  public getType(): Observable<Array<WccTypes>> {
    return this.http.get<Array<WccTypes>>(this.baseUrl + '/type');
  }
  public getStatus(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.baseUrl + '/status');
  }
  public getPendingSA(typeid, datefrom, dateTo): Observable<Array<WccSA>> {
    return this.http.get<Array<WccSA>>(this.baseUrl + '/pendingsa' + '?type=' + typeid + '&datefrom=' + datefrom + '&dateto=' + dateTo);
  }
  public getWccList(filter?: string, pageInfo?: string): Observable<Page<WCCResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

      url = `${url}&${filter}`;

    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<WCCResponse>>(url);
  }
  public getWccById(id: number): Observable<BtWCCResponse> {
    return this.http.get<BtWCCResponse>(this.baseUrl + '/' + id);
  }
  public getSAbyWccId(id: number): Observable<Array<WccSA>> {
    return this.http.get<Array<WccSA>>(this.baseUrl + '/' + id + '/sa');
  }
  public postWCC(request: WccRequest): Observable<WCCResponse> {
    return this.http.post<WCCResponse>(this.baseUrl, request);

  }
  public updateWCC(request: WccRequest, wccid: number): Observable<WCCResponse> {
    return this.http.put<WCCResponse>(this.baseUrl + '/' + wccid, request);

  }
  public submitForApproval(wccid: number): Observable<boolean> {
    return this.http.post<boolean>(this.baseUrl + '/' + wccid + '/forapproval', '');
  }
  public getPObyTypeId(id: number): Observable<Array<PODetailsbywcctypeID>> {
    return this.http.get<Array<PODetailsbywcctypeID>>(this.baseUrl + '/' + 'po' + '/' + id);
  }

  public getVendorInvocieByWccId(wccId: any): Observable<Array<any>> {
    return this.http.get<Array<any>>(`${this.baseUrl}/${wccId}/invoice`);
  }
  public getSearchResult(searchparam): Observable<Array<WCCResponse>> {
    return this.http.get<Array<WCCResponse>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }
}
