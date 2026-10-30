import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Page, ToBeApproved, ToBeApprovedHistoryResponse } from './model';
import { Observable } from 'rxjs';

@Injectable()
export class ApprovalHistoryService {
  private baseUrl = 'api/approvalhistory';
  private batteryBaseUrl = 'bt/api/approvalhistory';


  constructor(private _http: HttpClient) { }

  public getApprovalStatus(objectName: string, objectId: number): Observable<Array<ToBeApprovedHistoryResponse>> {
    return this._http.get<Array<ToBeApprovedHistoryResponse>>(`${this.baseUrl}?objectName=${objectName}&objectId=${objectId}`);


  }

  public getApprovalStatusbt(objectName: string, objectId: number): Observable<Array<ToBeApprovedHistoryResponse>> {
    return this._http.get<Array<ToBeApprovedHistoryResponse>>(`${this.batteryBaseUrl}?objectName=${objectName}&objectId=${objectId}`);


  }

}
