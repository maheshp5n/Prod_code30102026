import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BusinessDomain, OtherChargesExpenseRequest, OtherChargesExpenseResponse, Page } from '../../services/model';
import { BtOtherChargesExpenseRequest, BtOtherChargesExpenseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtotherChargesExpenseService {

  private baseUrl = 'bt/api/otherchargesexpenses';
  private baseUrlListById = this.baseUrl+'/list';
  private baseUrlTypeList = this.baseUrl+'/otherchargestype';

  constructor(private _http: HttpClient) { }

  getOtherChargesDetails(id: number): Observable<BtOtherChargesExpenseResponse> {
    return this._http.get<BtOtherChargesExpenseResponse>(this.baseUrl + '/getbyid/' + id);
  }

  getOtherChargesTypeList(id:number): Observable<Array<BusinessDomain>> {
    return this._http.get<Array<BusinessDomain>>(this.baseUrlTypeList+ '/' +id);
  }

  public getOtherChargesListByExpenseId(expenseManagementId: number,
    page: number
  ): Observable<Page<BtOtherChargesExpenseResponse>> {
    return this._http.get<Page<BtOtherChargesExpenseResponse>>(
      `${this.baseUrlListById}/${expenseManagementId}` +
      '?page=' +
      page +
      '&size=5');
  }

  public postOtherCharges(otherChargesRequest: BtOtherChargesExpenseRequest): Observable<BtOtherChargesExpenseResponse> {
    return this._http.post<BtOtherChargesExpenseResponse>(this.baseUrl, otherChargesRequest);
  }

  public putOtherCharges(otherChargesRequest: BtOtherChargesExpenseRequest): Observable<BtOtherChargesExpenseResponse> {
    return this._http.put<BtOtherChargesExpenseResponse>(this.baseUrl + '/', otherChargesRequest);
  }

}
