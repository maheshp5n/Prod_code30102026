import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddItemtoPrductRequest, BatNewProductRequest, BatteryProductRequestDetail, BtProductList, BtProductRequestResponse, GetPrTransferItemsResponse, BtItemListByPrId, BtProductTransferItemList} from '../bt_model';
import { Page } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtproductrequestService {
  
  private baseURl = 'bt/api/productrequest';
  private baseUrl1 = 'bt/api/products';
   

  constructor(private _http: HttpClient) { }

  public CreateNewProductRequest(req: BatNewProductRequest):Observable<any>{
    return this._http.post<any>(`${this.baseURl}`,req);
  }
  public updateBtProductReq(req: BatNewProductRequest,prId):Observable<BtProductRequestResponse>{
    return this._http.put<BtProductRequestResponse>(`${this.baseURl}/${prId}`,req);
  }
  public productStatus():Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseURl}/status/forlist`);
  }
  public fetchProductList(searchKey):Observable<Array<BtProductList>>{
    return this._http.get<Array<BtProductList>>(`${this.baseUrl1}/forlist?searchkey=${searchKey}`);
  }
  public fetchBtProductReq(filter?:string,pageInfo?:string): Observable<Page<BtProductRequestResponse>> {
    let url = `${this.baseURl}?`;
    if (filter) {
        url = `${url}&${filter}`;    
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    } 
    return this._http.get<Page<BtProductRequestResponse>>(url);
  }
  public fetchBtProductReqById(id: number):Observable<BtProductRequestResponse>{
    return this._http.get<BtProductRequestResponse>(`${this.baseURl}/${id}`);
  }
  public getDateOfPrSubmitted(productrequestid):Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseURl}/${productrequestid}/submitted/date`);
  }
  public itemByPrId(id: number):Observable<Page<BtItemListByPrId>>{
    return this._http.get<Page<BtItemListByPrId>>(`${this.baseURl}/${id}/items`);
  }
  public fetchdataByAdvanceSearch(searchparam):Observable<Array<BtProductRequestResponse>>{
    return this._http.get<Array<BtProductRequestResponse>>(`${this.baseURl}/advancesearch`, {params:searchparam});
  }
  public addItemReq(req: AddItemtoPrductRequest,prId): Observable<any>{
    return this._http.post<any>(`${this.baseURl}/${prId}/items`,req);
  }
  public updateAddItemReq(req: AddItemtoPrductRequest,prId,itemId):Observable<any>{
    return this._http.put<any>(`${this.baseURl}/${prId}/items/${itemId}`,req);
  }
  public advanceSearchForPrItems(searchparam,productrequestId:number):Observable<Array<BtItemListByPrId>>{
    return this._http.get<Array<BtItemListByPrId>>(`${this.baseURl}/${productrequestId}/items/advancesearch`, {params:searchparam});
   }
  public changeStatusOfProductRequest(productrequestid:number,statusName):Observable<any>{
    return this._http.put<any>(`${this.baseURl}/${productrequestid}/changestatus`,{status:statusName});
  }
  public changeStatusOfProductReqItem(prId,prItemId,itemstatusName):Observable<any>{
    return this._http.put<any>(`${this.baseURl}/${prId}/changestatus/${prItemId}`,{status:itemstatusName})
  }
  public deleteProuctReqItem(prID,ITemId): Observable<any>{
    return this._http.delete<any>(`${this.baseURl}/${prID}/items/${ITemId}`);
  }
  public submitReqToSap(productrequestid:number):Observable<any>{
    return this._http.put<any>(`${this.baseURl}/submittosap/${productrequestid}`,'');
  }
  public btUpdateSourceLocationPr(prid,newsourcelocationid):Observable<any>{
    return this._http.put<any>(`${this.baseURl}/updatesourcelocation/${prid}/${newsourcelocationid}`,'');
  }
  public btProductTransferItems(productrequestid):Observable<Array<GetPrTransferItemsResponse>>{
    return this._http.get<Array<GetPrTransferItemsResponse>>(`${this.baseURl}/${productrequestid}/items/fortransfer`);
  }
public getTransferItems(productRequestId):Observable<Page<BtProductTransferItemList>>{
  return this._http.get<Page<BtProductTransferItemList>>(`${this.baseURl}/${productRequestId}/transfer`)
}
public getSearchPT(searchparam,id): Observable<Array<BtProductTransferItemList>> {
  return this._http.get<Array<BtProductTransferItemList>>(`${this.baseURl}/${id}/transfer/advancesearch`, {params:searchparam});
}

public updatePartcode(pritemid,partcode){
  return this._http.put(`${this.baseURl}/cahngepartcodeofpr/${pritemid}`,{partCode:partcode});
}
public getItems(productrequestId: number, filters?: string,pageInfo?: string): Observable<Page<any>> {
  let url = `${this.baseURl}/${productrequestId}/items`;
  if (filters) {
      url = `${url}?${filters}`;
  }
  if (pageInfo) {
      url = `${url}&${pageInfo}`;
  }
  // if(page)
  return this._http.get<Page<any>>(url);
}
}

    