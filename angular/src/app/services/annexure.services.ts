import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Page,
  AnnexureRequest,
  AnnexureResponse,
  AnnexureApprovalStatus
} from './model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AnnexureService {
  private baseUrl = 'api/annexure';

  constructor(private _http: HttpClient) { }

  public getAnnexure(filter?: string, pageInfo?: string): Observable<Page<AnnexureResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<AnnexureResponse>>(url);
  }

  public getRecentAnnexure(page: number, column: string, order: string): Observable<Page<AnnexureResponse>> {
    return this._http.get<Page<AnnexureResponse>>(this.baseUrl + '/recentvisit' + '?page=' + page + '&size=20' +
      '&' + 'sort=' + column + ',' + order);
  }

  public getAnnexureById(id: number): Observable<AnnexureResponse> {
    return this._http.get<AnnexureResponse>(this.baseUrl + '/' + id);
  }

  public postAnnexure(annexure: AnnexureRequest): Observable<AnnexureResponse> {
    return this._http.post<AnnexureResponse>(this.baseUrl, annexure);
  }

  public putAnnexure(annexure: AnnexureRequest): Observable<AnnexureResponse> {
    return this._http.put<AnnexureResponse>(this.baseUrl, annexure);
  }

  public getApprovalStatus(): Observable<Array<AnnexureApprovalStatus>> {
    return this._http.get<Array<AnnexureApprovalStatus>>(this.baseUrl + '/approvalstatus');
  }

  public submitForApproval(annexureId: number, businessDomainId: number): Observable<boolean> {
    return this._http.post<boolean>(this.baseUrl + '/submitforapproval?annexureId=' + annexureId + '&businessDoaminId=' + businessDomainId, '');
  }

}
