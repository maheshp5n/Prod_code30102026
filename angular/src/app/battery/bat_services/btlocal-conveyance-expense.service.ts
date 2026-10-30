import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalConveyanceExpenseRequest, LocalConveyanceExpenseResponse, Page } from '../../services/model';
import { BtLocalConveyanceExpenseRequest, BtLocalConveyanceExpenseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtlocalConveyanceExpenseService {

  private baseUrl = 'bt/api/btlocalconveyance';
  private baseUrlListById = this.baseUrl+'/list';

  constructor(private _http: HttpClient) { }

  getLocalConveyanceExpenseDetails(id: number): Observable<BtLocalConveyanceExpenseResponse> {
    return this._http.get<BtLocalConveyanceExpenseResponse>(this.baseUrl + '/getbyid/' + id);
  }


  public getLocalConveyanceExpenseListByExpenseId(expenseManagementId: number,
    page: number
  ): Observable<Page<BtLocalConveyanceExpenseResponse>> {
    return this._http.get<Page<BtLocalConveyanceExpenseResponse>>(
      `${this.baseUrlListById}/${expenseManagementId}` +
      '?page=' +
      page +
      '&size=5');
  }

  public postLocalConveyanceExpense(localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest): Observable<BtLocalConveyanceExpenseResponse> {
    return this._http.post<BtLocalConveyanceExpenseResponse>(this.baseUrl, localConveyanceExpenseRequest);
  }

  public putLocalConveyanceExpense(localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest): Observable<BtLocalConveyanceExpenseResponse> {
    return this._http.put<BtLocalConveyanceExpenseResponse>(this.baseUrl + '/', localConveyanceExpenseRequest);
  }

}