import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BtJobCardResponse, BtServiceOrderAssignReq, Page, ReAssignJobCardReq, RepairEngineerList } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtJobCardServiceService {
  baseUrl = 'bt/api/jobcard';

  constructor(private _http: HttpClient) { }
  public btServiceOrderRepairEngineerList(): Observable<Array<RepairEngineerList>> {
    return this._http.get<Array<RepairEngineerList>>(`${this.baseUrl}/repariengineer`);
  }
  public postBtAssignServiceOrder(req: Array<BtServiceOrderAssignReq>): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}`, req);
  }
  public getBtJobCardList(filter?: any, pageInfo?: string): Observable<Page<any>> {
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
  public getBtJobCardByJobCardId(jobcardid): Observable<BtJobCardResponse> {
    return this._http.get<BtJobCardResponse>(`${this.baseUrl}/${jobcardid}`);
  }
  public btPostSingleJobCard(req): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}/single`, req);

  }
  public btUpdateJobCard(jobCardId: number, req): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/${jobCardId}`, req);
  }
  public btJobCardProductConsume(jobCardId, type: any): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${jobCardId}/productconsumed?list=${type}`);

  }
  public btItemsForQC(jobCardId, type: any): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/${jobCardId}/qc?list=${type}`);

  }

  public getResolution(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/dropdown/resolution`);
  }
  // new BRC 
  public getBRC(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/dropdown/correctiveaction`);
  }

  public getengRemarksMethods(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/dropdown/engremarks`);
  }

  public getBatteryBalanceAt(): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/dropdown/batterybalanceat`);
  }
  public getBerRemrks() {
    return this._http.get<Array<any>>(`${this.baseUrl}/dropdown/berremarks`);
  }

  postUploadFile(file: FormData, id): Observable<any> {
    return this._http.post<any>(`${this.baseUrl}/uploadfile/${id}`, file);
  }
  getUploadFile(id): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/files/${id}`);
  }
  postReAssign(id, req:ReAssignJobCardReq): Observable<any> {
    return this._http.put<any>(`${this.baseUrl}/reassign/${id}`, req)
  }
}
