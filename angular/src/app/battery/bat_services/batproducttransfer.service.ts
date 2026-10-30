import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BtPendingItemLIstASPerLocation, BtProductTransferItems, BtProductTransferRequest, BtProductTransferResponse, GetProductTransferItems, PendingLocationLIstForTransfer } from '../bt_model';
import { Page } from '../../services/model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BatproducttransferService {
  baseUrl = '/bt/api/producttransfer';
  baseUrl1 = '/bt/api/productrequest';

  constructor(private _http: HttpClient) { }

  public getPendinglocationforitemtransfer(): Observable<Array<PendingLocationLIstForTransfer>> {
    return this._http.get<Array<PendingLocationLIstForTransfer>>(`${this.baseUrl}/pendinglocationforitemtransfer`);
  }

  getPendignITemsForTransfer(locationId): Observable<Array<BtPendingItemLIstASPerLocation>> {
    return this._http.get<Array<BtPendingItemLIstASPerLocation>>(`${this.baseUrl}/pendingitemfortransfer/${locationId}`);
  }
  postRequestForProductTransfer(req: BtProductTransferRequest): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}`, req);
  }

  public getProuctTransferItems(id: number, page): Observable<Page<GetProductTransferItems>> {
    return this._http.get<Page<GetProductTransferItems>>(`${this.baseUrl}/${id}/items`);
  }
  public getAllProuctTransferItems(id: number): Observable<Page<GetProductTransferItems>> {
    return this._http.get<Page<GetProductTransferItems>>(`${this.baseUrl}/${id}/items`);
  }
  public getPendingToRecieve(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/pendingitemtoreceive/v1`);
  }
  public postPendingToRecieve(req: Array<any>): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}/pendingitemtoreceive/v1`, req);
  }

  public getProductTransferReq(btPtId: number, req: BtProductTransferRequest): Observable<BtProductTransferRequest> {
    return this._http.post<BtProductTransferRequest>(`${this.baseUrl1}/${btPtId}/transferitem`, req);
  }
  public getProductTransferLIstByFilter(filter?: string, pageInfo?: string): Observable<Page<BtProductTransferResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
      url = `${url}&${filter}`;
    }
    if (pageInfo) { 
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BtProductTransferResponse>>(url);
  }
  public getProductTransferByPtId(btptId: number): Observable<BtProductTransferResponse> {
    return this._http.get<BtProductTransferResponse>(`${this.baseUrl}/${btptId}`);
  }
  public getItemByPtId(id: number): Observable<GetProductTransferItems> {
    return this._http.get<GetProductTransferItems>(`${this.baseUrl}/${id}/items`);
  }
  public fetchdataByAdvanceSearch(searchparam): Observable<Array<BtProductTransferResponse>> {
    return this._http.get<Array<BtProductTransferResponse>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }
  public fetchProductTransferItemsByAdvanceSearch(productTransferId, searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${productTransferId}/items/advancesearch`, { params: searchparam })
  }
// eway 
  getEwayBillByTransferId(transferId: number) {
  return this._http.get<any>(
    `/bt/api/producttransfer/getby/${transferId}`
  );
}



}
