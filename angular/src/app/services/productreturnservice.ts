import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LpReturnOrderResponse, LpReturnOrderItemResponse, Page, LpReturnOrderReceiveRequest, LpReturnLineItemResponse, ROlineItems } from './model';

@Injectable({ providedIn: 'root' })
export class ProductReturnService {
    private baseUrl = 'api/reurnorder';
    transferItemUrl = 'api/reurnorder/transferLineitem';
    constructor(private _http: HttpClient) { }
    public getReturnOrder(returnorderId): Observable<LpReturnOrderResponse> {
        return this._http.get<LpReturnOrderResponse>(`${this.baseUrl}/${returnorderId}`);
    }
    public getPenditItemsToReceive(returnorderId: number): Observable<Array<LpReturnOrderItemResponse>> {
        return this._http.get<Array<LpReturnOrderItemResponse>>(`${this.baseUrl}/${returnorderId}/items/pendingtoreceive`)
    }
    public getReturnOrders(filtername?: string, pageInfo?: string): Observable<Page<LpReturnOrderResponse>> {
        let url = `${this.baseUrl}?`;
        if (filtername) {
            url = `${url}&${filtername}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this._http.get<Page<LpReturnOrderResponse>>(url);
    }
    public getItems(returnorderId: number,filter?): Observable<Page<LpReturnOrderItemResponse>> {
        let url=`${this.baseUrl}/${returnorderId}/items`;
        if(filter!=null){
        url=`${this.baseUrl}/${returnorderId}/items`+filter
        }
        return this._http.get<Page<LpReturnOrderItemResponse>>(url);
    }
    public receiveItems(returnorderid: number, items: LpReturnOrderReceiveRequest): Observable<LpReturnOrderReceiveRequest> {
        return this._http.post<LpReturnOrderReceiveRequest>(`${this.baseUrl}/${returnorderid}/receive`, items);
    }
    // shagufta
    public getLReturnLineItemById(lineItem: number): Observable<LpReturnLineItemResponse> {
        return this._http.get<LpReturnLineItemResponse>(`${this.transferItemUrl}/${lineItem}`);
    }
    public addItem(returnorderid, productLineItemids: Array<any>): Observable<any> {
        return this._http.put(`${this.baseUrl}/${returnorderid}/additems`, productLineItemids);
    }
    public deleteReturnOrderLineItem(returnorderid, lineitemid): Observable<any> {
        return this._http.delete(`${this.baseUrl}/${returnorderid}/items/${lineitemid}`);
    }
    public submitReturnOrder(returnorderid): Observable<any> {
        return this._http.put(`${this.baseUrl}/${returnorderid}/submit`, {});
    }
    // return Order details

    public getROItems(returnorderId: number,filter?,pageInfo?): Observable<Page<ROlineItems>> {
        let url=`${this.baseUrl}/${returnorderId}/items/page`;
        if(filter!=null){
        url=`${url}`+filter;
        }
        if(pageInfo!=null){
            url=`${url}`+pageInfo;  
        }
        return this._http.get<Page<ROlineItems>>(url);
    }
    public getSearchResult(searchparam): Observable<Array<LpReturnOrderResponse>> {
        return this._http.get<Array<LpReturnOrderResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
      }
      public getSearchResultROItems(searchparam,id): Observable<Array<ROlineItems>> {
        return this._http.get<Array<ROlineItems>>(`${this.baseUrl}/${id}/items/page/advancesearch`, {params:searchparam});
      }
      public getStatusForList():Observable<Array<string>>{
        return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
    }
}
