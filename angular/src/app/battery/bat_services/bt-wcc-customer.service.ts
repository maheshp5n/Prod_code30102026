import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BtCustomerWccRejectionList, BtCustomerWccRejectReq, BtCustomerWccResp, BtWccCaseResp, Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtWccCustomerService {

  constructor(private _http:HttpClient) { }

  baseUrl:string='bt/api/wcccustomer'

  public getAllWccCustomer(filter?:string, pageinfo?:string):Observable<Page<BtCustomerWccResp>>{
  let url=this.baseUrl+'?';
  if(filter)
  {
    url=url+'&'+filter;
  }
  if(pageinfo)
  {
    url=url+'&'+pageinfo;
  }
    return this._http.get<Page<BtCustomerWccResp>>(url)
  }

  public getAllWccCase(id:number):Observable<Array<BtWccCaseResp>>{
    return this._http.get<Array<BtWccCaseResp>>(this.baseUrl+'/wcccase/'+id)
  }

  public getWccCustomerByID(id:number):Observable<BtCustomerWccResp>{
    return this._http.get<BtCustomerWccResp>(this.baseUrl+'/'+id)
  }

  public approveWccCustomer(fd):Observable<any>{
    return this._http.put<any>(this.baseUrl+'/approval',fd)
  }

  public getReasonList():Observable<Array<BtCustomerWccRejectionList>>{
    return this._http.get<Array<BtCustomerWccRejectionList>>(this.baseUrl+'/rejectReasonForlist')
  }

  public getSearchResult(searchparam):Observable<Array<BtCustomerWccResp>>{
    return this._http.get<Array<BtCustomerWccResp>>(this.baseUrl+'/advancesearch',{params:searchparam})
  }
  public rejectWccCustomer(req:BtCustomerWccRejectReq):Observable<any>{
    return this._http.put<any>(this.baseUrl+'/reject',req)
  }

  public updatePODwithFile(fd): Observable<BtCustomerWccResp> {
    return this._http.post<BtCustomerWccResp>(this.baseUrl, fd)
  }

  /////For battery PRC
  public getExcelTemplate(id:number): Observable<Blob> {
  return this._http.get(this.baseUrl+'/craeteWccTemplate/'+id, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
}


parseErrorblob(error: HttpErrorResponse): Observable<any> {
  const reader: FileReader = new FileReader();
  const obs = Observable.create((observer: any) => {
    reader.onloadend = (e) => {
      observer.error(JSON.parse(reader.result.toString()));
      observer.complete();
    }
  });
 reader.readAsText(error.error);
return obs;
}

}
