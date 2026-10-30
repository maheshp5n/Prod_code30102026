import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  TourExpenseResponse,
  TourExpenseRequest,
  ModeOfTravel,
  Page, OwnConveyance
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TourExpenseService {
  private baseUrl = 'api/tourexpense';
  private baseUrlListownconveyance = 'api/tourexpense/ownconveyance';
  private baseUrlListById = 'api/tourexpense/list';

  constructor(private _http: HttpClient) { }

  getTourExpenseDetails(id: number): Observable<TourExpenseResponse> {
    return this._http.get<TourExpenseResponse>(this.baseUrl + '/' + id);
  }

  public getTourExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<TourExpenseResponse>> {
    return this._http.get<Page<TourExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postTourExpense(tourExpenseRequest: TourExpenseRequest): Observable<TourExpenseRequest> {
    return this._http.post<TourExpenseRequest>(this.baseUrl, tourExpenseRequest);
  }

  public putTourExpense(tourExpenseRequest: TourExpenseRequest): Observable<TourExpenseRequest> {
    return this._http.put<TourExpenseRequest>(this.baseUrl + '/', tourExpenseRequest);
  }

  public getModeOfTravel(): Observable<Array<ModeOfTravel>> {
    return this._http.get<Array<ModeOfTravel>>(
      this.baseUrl + '/' + 'modeoftravel');
  }

  public getOwnConveyance(modeoftravelid: number): Observable<Array<OwnConveyance>> {
    return this._http.get<Array<OwnConveyance>>(
      this.baseUrlListownconveyance + '/' + modeoftravelid
    );
  }
}
