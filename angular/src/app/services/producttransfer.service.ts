import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocationProductTransferItemResponse, Page, LocationProductTransferLineItemResponse, PTResponse } from './model';

@Injectable({providedIn:'root'})
export class ProductTransferServices{
    private baseurl='api/producttransfer';
    private transferItemUrl='api/producttransfer/transferLineitem'
    constructor(private _http:HttpClient){}
    public getTransferItemDetails(transferId:number):Observable<LocationProductTransferItemResponse>{
      return this._http.get<LocationProductTransferItemResponse>(`${this.baseurl}/items/${transferId}`);
    }
    public getPTListPageable(filtername?:string,pageInfo?:string):Observable<Page<string>>{
      let url=`${this.baseurl}?`;
      if(filtername){
        url=`${url}&${filtername}`;
      }
      if(pageInfo){
        url=`${url}&${pageInfo}`
      }
      return this._http.get<Page<string>>(url);
  }
  public getTransferDetailsById(transferId:number):Observable<PTResponse>{
    return this._http.get<PTResponse>(`${this.baseurl}/${transferId}`);
  }
    public getItems(filtername?:string,pageInfo?:string):Observable<Page<LocationProductTransferItemResponse>>{
        let url=`${this.baseurl}/items?`;
        if(filtername){
          url=`${url}${filtername}`;
        }
        if(pageInfo){
          url=`${url}&${pageInfo}`
        }
        return this._http.get<Page<LocationProductTransferItemResponse>>(url);
    }

    public getLineItems(transferItemId:number):Observable<Array<LocationProductTransferLineItemResponse>>{
      return this._http.get<Array<LocationProductTransferLineItemResponse>>(`${this.baseurl}/items/${transferItemId}/lineitems`)
    }
       // shagufta
       public getLProductTransferLineItemById(productTransferLineItemId:number):Observable<LocationProductTransferLineItemResponse>{
        return this._http.get<LocationProductTransferLineItemResponse>(`${this.transferItemUrl}/${productTransferLineItemId}`);
    }
    public getSearchResult(searchparam): Observable<Array<LocationProductTransferItemResponse>> {
      return this._http.get<Array<LocationProductTransferItemResponse>>(`${this.baseurl}/advancesearch`, {params:searchparam});
    }
    public getPTItemsSearchResult(searchparam,id): Observable<Array<LocationProductTransferLineItemResponse>> {
      return this._http.get<Array<LocationProductTransferLineItemResponse>>(`${this.baseurl}/items/${id}/lineitems/advancesearch`, {params:searchparam});
    }
    // Pt Items
    public getPTItems(transferItemId:number,filter?,pageInfo?):Observable<Page<LocationProductTransferItemResponse>>{
      let url=`${this.baseurl}/transferItems/${transferItemId}?`;
      if(filter){
        url=`${url}&${filter}`;
      }
      if(pageInfo){
        url=`${url}&${pageInfo}`
      }
      return this._http.get<Page<LocationProductTransferItemResponse>>(url);
      // (`${this.baseurl}/transferItems/${transferItemId}`)
    }
    public getPTSearchResult(searchparam): Observable<Array<any>> {
      return this._http.get<Array<any>>(`${this.baseurl}/productTransfer/advancesearch`, {params:searchparam});
    }
}