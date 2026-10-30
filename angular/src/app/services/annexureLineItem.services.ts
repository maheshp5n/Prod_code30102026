import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Page,
  AnnexureLineItemRequest, AnnexureLineItemResponse, SiteDetailsForList, AnnexureGLCode, ServiceAppointmentResponse
} from './model'; import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AnnexureLineItemService {
  private baseUrl = 'api/annexure/lineitem';

  constructor(private _http: HttpClient) { }

  public getAnnexureLineItemByAnnexId(id: number, page: number): Observable<Page<AnnexureLineItemResponse>> {
    return this._http.get<Page<AnnexureLineItemResponse>>('api/annexure/lineitem?annexureId=' + id + '&page='
      + page + '&size=5');
  }

  public getAnnexureLineItemById(id: number): Observable<AnnexureLineItemResponse> {
    return this._http.get<AnnexureLineItemResponse>(this.baseUrl + '/' + id);
  }

  public postAnnexureLineItem(annexureLineItem: AnnexureLineItemRequest): Observable<AnnexureLineItemResponse> {
    return this._http.post<AnnexureLineItemResponse>(this.baseUrl, annexureLineItem);
  }

  public putAnnexureLineItem(annexureLineItem: AnnexureLineItemRequest): Observable<AnnexureLineItemResponse> {
    return this._http.put<AnnexureLineItemResponse>(this.baseUrl, annexureLineItem);
  }

  public getGLCode(): Observable<Array<AnnexureGLCode>> {
    return this._http.get<Array<AnnexureGLCode>>('api/annexure/glcode');
  }

  public getClosedServiceAppointmentForAnnexure(): Observable<Array<ServiceAppointmentResponse>> {
    return this._http.get<Array<ServiceAppointmentResponse>>('api/serviceappointment/forlist/byowner');
  }

  public getAnnexureLineItemByLineItemId(id: number): Observable<AnnexureLineItemResponse> {
    return this._http.get<AnnexureLineItemResponse>(this.baseUrl + '/lineitem/' + id);
  }
}

