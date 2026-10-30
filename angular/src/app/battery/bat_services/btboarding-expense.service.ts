import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BoardingExpenseRequest, BoardingExpenseResponse, CityForListReponse, ModeOfTravel, OwnConveyance, Page } from '../../services/model';
import { BtBoardingExpenseRequest, BtBoardingExpenseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtboardingExpenseService {

  private baseUrl = 'bt/api/boardingexpense';
  // private baseUrlListownconveyance = 'api/boardingexpense/ownconveyance';
  private baseUrlListById = this.baseUrl+'/list';
  private urlCircleList = 'api/city/forlist';

  constructor(private _http: HttpClient) { }

  getBoardingExpenseDetails(id: number): Observable<BtBoardingExpenseResponse> {
    return this._http.get<BtBoardingExpenseResponse>(this.baseUrl + '/getbyid/' + id);
  }

  public getBoardingExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<BtBoardingExpenseResponse>> {
    return this._http.get<Page<BtBoardingExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postBoardingExpense(boardingExpenseRequest: BtBoardingExpenseRequest): Observable<BtBoardingExpenseResponse> {
    return this._http.post<BtBoardingExpenseResponse>(this.baseUrl, boardingExpenseRequest);
  }

  public putBoardingExpense(boardingExpenseRequest: BtBoardingExpenseRequest): Observable<BtBoardingExpenseResponse> {
    return this._http.put<BtBoardingExpenseResponse>(this.baseUrl + '/', boardingExpenseRequest);
  }

  // public getModeOfTravel(): Observable<Array<ModeOfTravel>> {
  //   return this._http.get<Array<ModeOfTravel>>(
  //     this.baseUrl + '/' + 'modeoftravel');
  // }

  // public getOwnConveyance(modeoftravelid: number): Observable<Array<OwnConveyance>> {
  //   return this._http.get<Array<OwnConveyance>>(
  //     this.baseUrlListownconveyance + '/' + modeoftravelid
  //   );
  // }

  public getCityList(): Observable<Array<CityForListReponse>> {
    return this._http.get<Array<CityForListReponse>>(this.urlCircleList);
  }

}
