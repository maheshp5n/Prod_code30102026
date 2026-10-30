import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  LodgingExpenseResponse,
  LodgingExpenseRequest,
  ModeOfTravel,
  Page, OwnConveyance,
  CityForListReponse
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LodgingExpenseService {
  private baseUrl = 'api/lodgingexpense';
  private baseUrlListownconveyance = 'api/lodgingexpense/ownconveyance';
  private baseUrlListById = 'api/lodgingexpense/list';
  private urlCircleList = 'api/city/forlist';

  constructor(private _http: HttpClient) { }

  getLodgingExpenseDetails(id: number): Observable<LodgingExpenseResponse> {
    return this._http.get<LodgingExpenseResponse>(this.baseUrl + '/' + id);
  }

  public getLodgingExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<LodgingExpenseResponse>> {
    return this._http.get<Page<LodgingExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postLodgingExpense(lodgingExpenseRequest: LodgingExpenseRequest): Observable<LodgingExpenseRequest> {
    return this._http.post<LodgingExpenseRequest>(this.baseUrl, lodgingExpenseRequest);
  }

  public putLodgingExpense(lodgingExpenseRequest: LodgingExpenseRequest): Observable<LodgingExpenseRequest> {
    return this._http.put<LodgingExpenseRequest>(this.baseUrl + '/', lodgingExpenseRequest);
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

  public getCityList(): Observable<Array<CityForListReponse>> {
    return this._http.get<Array<CityForListReponse>>(this.urlCircleList);
  }
}
