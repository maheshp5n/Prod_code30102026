import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  OtherChargesExpenseResponse,
  OtherChargesExpenseRequest,
  Page,
  BusinessDomain
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtherChargesService {
  private baseUrl = 'api/otherchargesexpenses';
  private baseUrlListById = 'api/otherchargesexpenses/list';
  private baseUrlTypeList = 'api/otherchargesexpenses/otherchargestype';

  constructor(private _http: HttpClient) { }

  getOtherChargesDetails(id: number): Observable<OtherChargesExpenseResponse> {
    return this._http.get<OtherChargesExpenseResponse>(this.baseUrl + '/' + id);
  }

  getOtherChargesTypeList(id: number): Observable<Array<BusinessDomain>> {
    return this._http.get<Array<BusinessDomain>>(this.baseUrlTypeList + '/' + id);
  }

  public getOtherChargesListByExpenseId(expenseManagementId: number,
    page: number
  ): Observable<Page<OtherChargesExpenseResponse>> {
    return this._http.get<Page<OtherChargesExpenseResponse>>(
      `${this.baseUrlListById}/${expenseManagementId}` +
      '?page=' +
      page +
      '&size=5');
  }

  public postOtherCharges(otherChargesRequest: OtherChargesExpenseRequest): Observable<OtherChargesExpenseRequest> {
    return this._http.post<OtherChargesExpenseRequest>(this.baseUrl, otherChargesRequest);
  }

  public putOtherCharges(otherChargesRequest: OtherChargesExpenseRequest): Observable<OtherChargesExpenseRequest> {
    return this._http.put<OtherChargesExpenseRequest>(this.baseUrl + '/', otherChargesRequest);
  }

}
