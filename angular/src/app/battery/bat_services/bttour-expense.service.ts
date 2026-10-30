import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ModeOfTravel, OwnConveyance, Page, TourExpenseRequest, TourExpenseResponse } from '../../services/model';
import { BtTourExpenseRequest, BtTourExpenseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BttourExpenseService {

  private baseUrl = 'bt/api/tourexpense';
  private baseUrlListownconveyance = this.baseUrl+'/ownconveyance';
  private baseUrlListById = this.baseUrl+'/list';

  constructor(private _http: HttpClient) { }

  getTourExpenseDetails(id: number): Observable<BtTourExpenseResponse> {
    return this._http.get<BtTourExpenseResponse>(this.baseUrl + '/getbyid/' + id);
  }

  public getTourExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<BtTourExpenseResponse>> {
    return this._http.get<Page<BtTourExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postTourExpense(tourExpenseRequest: BtTourExpenseRequest): Observable<BtTourExpenseResponse> {
    return this._http.post<BtTourExpenseResponse>(this.baseUrl, tourExpenseRequest);
  }

  public putTourExpense(tourExpenseRequest: BtTourExpenseRequest): Observable<BtTourExpenseResponse> {
    return this._http.put<BtTourExpenseResponse>(this.baseUrl + '/', tourExpenseRequest);
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
