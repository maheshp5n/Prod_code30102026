import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { LocationProductRequestResponse, LPItemRequest, Page, LocationProductRequestItemResponse, OkItemResponse, LPTransferRequest, LocationProductRequestPendingToReceive, LocationProductTransferReceiveRequest, LocationProductTransferItemResponse } from './model';
@Injectable({ providedIn: 'root' })
export class LocationProductRequestService {
    private baseURl = 'api/productrequest';
    private baseUrlLineItems = 'api/productrequest/lineitem'
    constructor(private _http: HttpClient) { }

    public getLocationProductRequest(productRequestId: number): Observable<LocationProductRequestResponse> {
        return this._http.get<LocationProductRequestResponse>(`${this.baseURl}/${productRequestId}`);
    }
    public submitRequest(productrequestid: number) {
        return this._http.put(`${this.baseURl}/${productrequestid}/submit`, '');
    }
    public addItems(productrequestId: number, lpItem: LPItemRequest): Observable<LPItemRequest> {
        return this._http.put<LPItemRequest>(`${this.baseURl}/${productrequestId}/additem`, lpItem);
    }
    public getItems(productrequestId: number, filters?: string,pageInfo?: string): Observable<Page<LocationProductRequestItemResponse>> {
        let url = `${this.baseURl}/${productrequestId}/items`;
        if (filters) {
            url = `${url}?${filters}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        // if(page)
        return this._http.get<Page<LocationProductRequestItemResponse>>(url);
    }
    public getOkQuantity(productrequestid: number, itemId: number): Observable<OkItemResponse> {
        return this._http.get<OkItemResponse>(`${this.baseURl}/${productrequestid}/okitems/${itemId}`);
    }
    public postProductTransfer(productrequestid: number, productTransfer: LPTransferRequest): Observable<LPTransferRequest> {
        return this._http.post<LPTransferRequest>(`${this.baseURl}/${productrequestid}/transferitem`, productTransfer);
    }
    public getProductRequest(filter?: string, pageInfo?: string): Observable<Page<LocationProductRequestResponse>> {
        let url = `${this.baseURl}?`;
        if (filter) {
            url = `${url}&${filter}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this._http.get<Page<LocationProductRequestResponse>>(url);
    }
    public getPendingItemToReceive(prId: number): Observable<Array<LocationProductRequestPendingToReceive>> {
        return this._http.get<Array<LocationProductRequestPendingToReceive>>(`${this.baseURl}/${prId}/pendingtoreceiveitem`)
    }
    public receiveItems(productrequestid: number, items: Array<LocationProductTransferReceiveRequest>) {
        return this._http.post(`${this.baseURl}/${productrequestid}/receiveitem`, items);
    }
    public getTransferItems(productrequestId: number, filter?: string): Observable<Page<LocationProductTransferItemResponse>> {
       let url='';
        if(filter!=null){
       url=`${this.baseURl}/${productrequestId}/transferitem`+filter
        }
        else{
        url=`${this.baseURl}/${productrequestId}/transferitem`;
        }
        return this._http.get<Page<LocationProductTransferItemResponse>>(url);
    
}
    // shagufta
    public getLProductLineItemsById(id: number): Observable<LocationProductRequestItemResponse> {
        return this._http.get<LocationProductRequestItemResponse>(`${this.baseUrlLineItems}/${id}`);
    }
    public deleteProductLineItemsById(id: number): Observable<any> {
        return this._http.delete(`${this.baseURl}/deleteitem/${id}`);
    }
    public updatesoruceLocation(prdid,locationid){
        return this._http.put(`${this.baseURl}/updatesourcelocation/${prdid}/${locationid}`,{});
    }

    public updatePartcode(pritemid,partcode){
        return this._http.put(`${this.baseURl}/cahngepartcodeofpr/${pritemid}`,{partCode:partcode});
    }

    public submitRequesttoSap(productrequestid: number) {
        return this._http.put(`${this.baseURl}/submittosap/${productrequestid}`, '');
    }

    public disableProductRequestLineItem(productrequestLineItemId){
        return this._http.put(`${this.baseURl}/disabllineitem/${productrequestLineItemId}`,'');
    }

    public getItemsfortransfer(productrequestId: number): Observable<Array<LocationProductRequestItemResponse>> {
        let url = `${this.baseURl}/${productrequestId}/items/fortransfer`;
        
        // if(page)
        return this._http.get<Array<LocationProductRequestItemResponse>>(url);
    }

    public getSearchResult(searchparam): Observable<Array<LocationProductRequestResponse>> {
        return this._http.get<Array<LocationProductRequestResponse>>(`${this.baseURl}/advancesearch`, {params:searchparam});
      }
      public getSearchResultPRLineitems(searchparam,id): Observable<Array<LocationProductRequestItemResponse>> {
        return this._http.get<Array<LocationProductRequestItemResponse>>(`${this.baseURl}/${id}/items/advancesearch`, {params:searchparam});
      }
      public getSearchPT(searchparam,id): Observable<Array<LocationProductTransferItemResponse>> {
        return this._http.get<Array<LocationProductTransferItemResponse>>(`${this.baseURl}/${id}/transferitem/advancesearch`, {params:searchparam});
      }
      getPRStatusForList():Observable<Array<string>>{
          return this._http.get<Array<string>>(this.baseURl+'/status/forlist')
      }
    //   shgufta short close
    public shortClose(productrequestid: number,itemId:number,forPR:string) {
        if(forPR=='allPR'){
            return this._http.put(`${this.baseURl}/shortclose/${productrequestid}`, '');
        }else{
        return this._http.put(`${this.baseURl}/shortclose/${productrequestid}/item?productRequestLineItemId=${itemId}`, '');
        }
    }

    // short close PR
      //   shgufta short close
      public shortClosePR(productrequestid: number) {
        return this._http.put(`${this.baseURl}/shortclose/${productrequestid}`, '');
    }
    public getOwnerdetails(prid:number) :Observable<any>{
        return this._http.get<any>(this.baseURl+'/getOwnerdetails/'+prid);
    }
}