import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  LocalConveyanceExpenseResponse,
  LocalConveyanceExpenseRequest,
  Page
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocalConveyanceExpenseService {
  private baseUrl = 'api/localconveyance';
  private baseUrlListById = 'api/localconveyance/list';

  constructor(private _http: HttpClient) { }

  getLocalConveyanceExpenseDetails(id: number): Observable<LocalConveyanceExpenseResponse> {
    return this._http.get<LocalConveyanceExpenseResponse>(this.baseUrl + '/' + id);
  }


  public getLocalConveyanceExpenseListByExpenseId(expenseManagementId: number,
    page: number
  ): Observable<Page<LocalConveyanceExpenseResponse>> {
    return this._http.get<Page<LocalConveyanceExpenseResponse>>(
      `${this.baseUrlListById}/${expenseManagementId}` +
      '?page=' +
      page +
      '&size=5');
  }

  public postLocalConveyanceExpense(localConveyanceExpenseRequest: LocalConveyanceExpenseRequest): Observable<LocalConveyanceExpenseRequest> {
    return this._http.post<LocalConveyanceExpenseRequest>(this.baseUrl, localConveyanceExpenseRequest);
  }

  public putLocalConveyanceExpense(localConveyanceExpenseRequest: LocalConveyanceExpenseRequest): Observable<LocalConveyanceExpenseRequest> {
    return this._http.put<LocalConveyanceExpenseRequest>(this.baseUrl + '/', localConveyanceExpenseRequest);
  }

}
