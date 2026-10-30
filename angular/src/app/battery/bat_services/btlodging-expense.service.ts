import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BoardingLoadingDetailsResponse, CityForListReponse, LodgingExpenseRequest, LodgingExpenseResponse, ModeOfTravel, OwnConveyance, Page } from '../../services/model';
import { BtLodgingExpenseRequest, BtLodgingExpenseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtlodgingExpenseService {

  private baseUrl = 'bt/api/lodgingexpense';
  // private baseUrlListownconveyance = 'api/lodgingexpense/ownconveyance';
  private baseUrlListById = this.baseUrl+'/list';
  private urlCircleList = 'api/city/forlist';

  constructor(private _http: HttpClient) { }

  getLodgingExpenseDetails(id: number): Observable<BtLodgingExpenseResponse> {
    return this._http.get<BtLodgingExpenseResponse>(this.baseUrl + '/getbyid/' + id);
  }

  public getLodgingExpenseListByExpenseId(expenseManagementId: number, page: number): Observable<Page<BtLodgingExpenseResponse>> {
    return this._http.get<Page<BtLodgingExpenseResponse>>(`${this.baseUrlListById}/${expenseManagementId}` + '?page=' + page + '&size=5');
  }

  public postLodgingExpense(lodgingExpenseRequest: BtLodgingExpenseRequest): Observable<BtLodgingExpenseResponse> {
    return this._http.post<BtLodgingExpenseResponse>(this.baseUrl, lodgingExpenseRequest);
  }

  public putLodgingExpense(lodgingExpenseRequest: BtLodgingExpenseRequest): Observable<BtLodgingExpenseResponse> {
    return this._http.put<BtLodgingExpenseResponse>(this.baseUrl + '/', lodgingExpenseRequest);
  }

  public getDetailByboadingName(boardingName:string):Observable<BoardingLoadingDetailsResponse>{
    return this._http.get<BoardingLoadingDetailsResponse>(`${this.baseUrl}/${boardingName}`);
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