import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BtInventoryStock, BtInventoryStockByProductId, BtSericeOrderReceiveItemReq, BtSericeOrderRequest, BtServiceOrderRecieveItems, BtServiceOrderRepairCenterLIst, Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtServiceOrderService {
  private baseUrl = 'bt/api/serviceorder';

  constructor(private _http: HttpClient) { }
  public getBtServiceOrders(filter?: any, pageInfo?: string): Observable<Page<any>> {
    let url = this.baseUrl;
    if (filter) {
      url = `${url}?${filter}`;
    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<any>>(url);
  }
  public getBtSearchResult(searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }
  public getServiceOrderByID(serviceOrderId): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/${serviceOrderId}`);
  }
  postBtSeriveOrder(req: BtSericeOrderRequest): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}`, req);
  }
  public getPendingStock(): Observable<Array<BtInventoryStock>> {
    return this._http.get<Array<BtInventoryStock>>(`${this.baseUrl}/pendingstoc`);
  }
  public btCustomerInventoryPendingStockById(productId): Observable<Array<BtInventoryStockByProductId>> {
    return this._http.get<Array<BtInventoryStockByProductId>>(`${this.baseUrl}/pendingstoc/customerinventory/${productId}`);
  }

  public btCustomerInventoryPendingStockByIdAndChallanNumber(productId,challannumber): Observable<Array<BtInventoryStockByProductId>> {
    return this._http.get<Array<BtInventoryStockByProductId>>(`${this.baseUrl}/pendingstoc/customerinventorybychallannumber/${productId}?challanNumber=${challannumber}`);
  }

  public btServiceOrderRepairCenter(): Observable<Array<BtServiceOrderRepairCenterLIst>> {
    return this._http.get<Array<BtServiceOrderRepairCenterLIst>>(`${this.baseUrl}/servicerepaircenter`);
  }
  public btItemsForServiceOrder(serviceOrderId, type: any): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${serviceOrderId}/items?list=${type}`);
  }
  public btItemsForPendingToReceive(): Observable<Array<BtServiceOrderRecieveItems>> {
    return this._http.get<Array<BtServiceOrderRecieveItems>>(`${this.baseUrl}/items/pendingtoreceive`);
  }
  public updatebtItemsForPendingToReceive(req: Array<BtSericeOrderReceiveItemReq>): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/items/pendingtoreceive`, req);
  }
  public btItemsForPendingToReceiveBySeriveOrderId(ServiceOrderId): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/items/pendingtoreceive/${ServiceOrderId}`);
  }
  public updatebtItemsForPendingToReceiveById(req: Array<BtSericeOrderReceiveItemReq>, serviceOrderId): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/${serviceOrderId}/items/pendingtoreceive`, req);
  }
  public btSeriveOrderItemsAdvancesearch(serviceOrderId, searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${serviceOrderId}/advancesearch`, { params: searchparam })
  }
  public btSOItemsForAssign(serviceOrderId?: number): Observable<Array<BtServiceOrderRecieveItems>> {
    if (serviceOrderId) {
      return this._http.get<Array<BtServiceOrderRecieveItems>>(`${this.baseUrl}/items/pendingtoassign/${serviceOrderId}`);
    }
    return this._http.get<Array<BtServiceOrderRecieveItems>>(`${this.baseUrl}/items/pendingtoassign`);
  }
}
