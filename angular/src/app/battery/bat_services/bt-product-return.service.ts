import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../services/model';
import { BtMyInventoryResponse, BtProductReturnAddItemRequest, BtProductReturnItemsResponse, BtProductReturnReceiveRequest, BtProductReturnRequest, BtProductReturnResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtProductReturnService {

  private baseUrl = "bt/api/returnorder";

  constructor(private _http: HttpClient) { }


  public btPendingItemToReturn(): Observable<Array<BtMyInventoryResponse>> {
    return this._http.get<Array<BtMyInventoryResponse>>(`${this.baseUrl}/pendingitemtoreturn`);
  }
  public btPostProductReturnOrder(req: BtProductReturnRequest): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}`, req);
  }
  public btProductReturnList(filtername?: string, pageInfo?: string): Observable<Page<BtProductReturnResponse>> {
    let url = `${this.baseUrl}?`;
    if (filtername) {
      url = `${url}&${filtername}`;
    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BtProductReturnResponse>>(url);
  }
  public btProductRetrunAdvanceSearch(searchparam): Observable<Array<BtProductReturnResponse>> {
    return this._http.get<Array<BtProductReturnResponse>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }
  getProductReturnOrderById(returnId: number): Observable<BtProductReturnResponse> {
    return this._http.get<BtProductReturnResponse>(`${this.baseUrl}/${returnId}`)
  }

  getProductReturnItems(returnID): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${returnID}/items`)
  }
  changeStatusOfProductReturn(returnId, statusValue: string): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/${returnId}/changestatus`, { status: statusValue, returnOrderId: returnId });
  }
  btPendingReturnReceiveItems(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/pendingitemtoreceive`);
  }
  postBtPendingReturnReceiveItems(req: Array<BtProductReturnReceiveRequest>): Observable<any> {
    return this._http.post<BtProductReturnReceiveRequest>(`${this.baseUrl}/pendingitemtoreceive`, req);
  }
  btPendingReturnRecieveItemsByReturnId(returnId): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/pendingitemtoreceive?returnorderid=${returnId}`);
  }
  advanceSearchForBtReturnItems(returnId,searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${returnId}/items/advancesearch`,{params:searchparam});
  }
  addBtReturnItems(returnId,req:BtProductReturnAddItemRequest): Observable<any>{
    return this._http.post<any>(`${this.baseUrl}/${returnId}/items`,req);
  }
  deteteReturnItems(returnId,returnItemId):Observable<any>{
    return this._http.delete<any>(`${this.baseUrl}/${returnId}/items/${returnItemId}`)
  }

}

