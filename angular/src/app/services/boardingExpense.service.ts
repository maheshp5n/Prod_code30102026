import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  BoardingExpenseResponse,
  BoardingExpenseRequest,
  ModeOfTravel, CityForListReponse,
  Page, OwnConveyance
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardingExpenseService {
  private baseUrl = 'api/boardingexpense';
  private baseUrlListownconveyance = 'api/boardingexpense/ownconveyance';
  private baseUrlListById = 'api/boardingexpense/list';
  private urlCircleList = 'api/city/forlist';

  constructor(private _http: HttpClient) { }

  getBoardingExpenseDetails(id: number): Observable<BoardingExpenseResponse> {
    return this._http.get<BoardingExpenseResponse>(this.baseUrl + '/' + id);
  }

  public getBoardingExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<BoardingExpenseResponse>> {
    return this._http.get<Page<BoardingExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postBoardingExpense(boardingExpenseRequest: BoardingExpenseRequest): Observable<BoardingExpenseRequest> {
    return this._http.post<BoardingExpenseRequest>(this.baseUrl, boardingExpenseRequest);
  }

  public putBoardingExpense(boardingExpenseRequest: BoardingExpenseRequest): Observable<BoardingExpenseRequest> {
    return this._http.put<BoardingExpenseRequest>(this.baseUrl + '/', boardingExpenseRequest);
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
