import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';
import { Page, UpdateJobCardQcRequest } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BatJObCardQcService {

  baseUrl = 'bt/api/jobcardqc';
  dropdownbaseUrl = 'bt/api/jobcard/qc/dropdown';
  constructor(private _http: HttpClient) { }

  public getBtJobCardQCList(filter?: any, pageInfo?: string): Observable<Page<any>> {
    let url = this.baseUrl;
    if (filter) {
      url = `${url}?${filter}`;
    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<any>>(url);
  }
  // for advance search
  public getBtSearchResult(searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }
  public getBtJobCardQcByQcId(qcId): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/${qcId}`)
  }
  public updateJobCardQc(qcid, req: UpdateJobCardQcRequest): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/${qcid}`, req)
  }

  // api's for drop down
  getQcPhysicalCheck(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcphysicalcheck`);
  }
  getQCElectricalCheck(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcelectricalcheck`);
  }
  getqcphysicalremarks(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcphysicalremarks`);
  }
  getqcelectricalremarks(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcelectricalremarks`);
  }
  getqccorrectiveactiontaken(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qccorrectiveactiontaken`);
  }
  getqcsoftwareupdation(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcsoftwareupdation`);
  }
  getqcparametercheck(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcparametercheck`);
  }
  getqcsohupation(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcsohupation`);
  }
  getqcvoltagap(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcvoltagap`);
  }
  getqcfinalapproval(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcfinalapproval`);
  }

  postUploadFile(file: FormData, id): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}/uploadfile/${id}`, file);
  }
  getUploadFile(id): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/files/${id}`);
  }
  // new 23/2/23
  getqcfaultobservedduringpditaken(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.dropdownbaseUrl}/qcfaultobserved`);
  }

}
