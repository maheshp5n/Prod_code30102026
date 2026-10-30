import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
    WarehousedetailsRequest,
    Page,
    WarehousedetailsResponse,
    ServiceCenterForList
} from './model';

@Injectable()

export class WarehouseService {
  private baseUrl = 'api/warehouse';
  private baseUrlRecent = 'api/warehouse/recentvisit';
  forListUrl = 'api/legalentity/forlist';

  constructor(private _http: HttpClient) { }

  public getWarehouse(page: number, column: string, order: string): Observable<Page<WarehousedetailsResponse>> {
    return this._http.get<Page<WarehousedetailsResponse>>(this.baseUrl + '?page=' + page + '&size=20' + "&" + "sort=" + column + "," + order);
  }


  public getWarehouseById(id: number): Observable<WarehousedetailsResponse> {
    return this._http.get<WarehousedetailsResponse>(`${this.baseUrl}/${id}`);
  }

  public postWarehouse(warehouse: WarehousedetailsRequest): Observable<WarehousedetailsRequest> {
    return this._http.post<WarehousedetailsRequest>(this.baseUrl, warehouse);
  }

  public putWarehouse(id: number, warehouse: WarehousedetailsRequest): Observable<WarehousedetailsRequest> {
    return this._http.put<WarehousedetailsRequest>(this.baseUrl + "/" + id, warehouse);
  }

  public getRecentWarehouse(page: number, column: string, order: string): Observable<Page<WarehousedetailsResponse>> {
    return this._http.get<Page<WarehousedetailsResponse>>(this.baseUrlRecent + '?page=' + page + '&size=20' + "&" + "sort=" + column + "," + order);
  }

  public getLegalEntityForList(): Observable<Array<ServiceCenterForList>> {
    return this._http.get<Array<ServiceCenterForList>>(this.forListUrl);
  }
}
