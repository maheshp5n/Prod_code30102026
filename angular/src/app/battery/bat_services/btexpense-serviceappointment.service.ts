import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { btExpenseCostCenter, btExpServiceAppointmentlist, btExpServiceAppointmentReq, btExpServiceAppointmentResp, Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtexpenseServiceappointmentService {

  private baseUrl = 'bt/api/btServiceAppointmentExp';
  // private baseUrlListownconveyance = 'api/boardingexpense/ownconveyance';
  // private baseUrlListById = this.baseUrl+'/list';
  // private urlCircleList = 'api/city/forlist';

  constructor(private _http: HttpClient) { }

  public getServiceAppointmentExpenseDetails(id: number, page: number): Observable<Page<btExpServiceAppointmentResp>> {
    return this._http.get<Page<btExpServiceAppointmentResp>>(this.baseUrl + '/list/' + id + '?page=' + page + '&size=5');
  }
  public getServiceAppointmentExpenseDetailsById(id: number): Observable<btExpServiceAppointmentResp> {
    return this._http.get<btExpServiceAppointmentResp>(this.baseUrl + '/getbyid/' + id);
  }

  public getcostcenter(): Observable<Array<btExpenseCostCenter>> {
    return this._http.get<Array<btExpenseCostCenter>>(this.baseUrl + '/getcostcenterandglcode');
  }
  public getServiceAppointmentExpClaim():Observable<Array<btExpServiceAppointmentlist>>{
    return this._http.get<Array<btExpServiceAppointmentlist>>(this.baseUrl+'/getbtserviceappointment');
  }
  public postExpenseSA(req:btExpServiceAppointmentReq):Observable<btExpServiceAppointmentResp>{
    return this._http.post<btExpServiceAppointmentResp>(this.baseUrl, req)
  }
  public updateExpenseSA(req:btExpServiceAppointmentReq):Observable<btExpServiceAppointmentResp>{
    return this._http.put<btExpServiceAppointmentResp>(this.baseUrl,req)
  }
}
