import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Page } from '../../services/model';
import { BtCustomerInventoryInRequest, BtCustomerInventoryOutRequest, EditGrnRequest } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtCustomerInventoryService {
  baseUrl = 'bt/api/customerinventoryin';

  constructor(private _http: HttpClient) { }
  public getCustomerInventory(filter?: string, pageInfo?: string): Observable<Page<any>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<any>>(url);
}
  public getBtCustomerInventoryById(btCustomerInventoryId):Observable<any>{
    return this._http.get<any>(`${this.baseUrl}/${btCustomerInventoryId}`);
  }
  public postCustomerInventoryIn(file: FormData) {
    return this._http.post(`${this.baseUrl}/bulkupload`, file, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
  }
  public postOutInventory(file: FormData) {
    return this._http.post(`${this.baseUrl}/bulkupload/out`, file, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
  }

  parseErrorBlob(err: HttpErrorResponse): Observable<any> {
    const reader: FileReader = new FileReader();
    const obs = Observable.create((observer: any) => {
      reader.onloadend = (e) => {
        observer.error(JSON.parse(reader.result.toString()));
        observer.complete();
      };
    });
    reader.readAsText(err.error);
    return obs;
  }

  getTempalte(type) {
    return this._http.get(`${this.baseUrl}/template?type=${type}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
  }
  public getAdvanceSearch(keysdata): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`, { params: keysdata });
  }
  public btpostCustomerInventoryIn(req: BtCustomerInventoryInRequest):Observable<any>{
    return this._http.post<any>(`${this.baseUrl}`,req);
  }
  public btpostCustomerInventoryOut(req: BtCustomerInventoryOutRequest):Observable<any>{
    return this._http.post<any>(`${this.baseUrl}/outinventory`,req);
  }

  public updateCustomerInventoryIn(request:any):Observable<any>{
    return this._http.put<any>(this.baseUrl,request);
  }
  public createPDFForBrc(id: number): Observable<any> {
    return this._http.get(`${this.baseUrl}/downloadpdf/${id}`,{ responseType: 'blob' }).pipe(catchError(this.parseErrorBlob))
  }
  public editGrn(outId,req:EditGrnRequest):Observable<any>{
    return this._http.put<any>(`${this.baseUrl}/updateoutgrn/${outId}`,req);
  }
}
