import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page, LocationFaultyLineItemResponse, DCTRepairRequest, RepairDCTResponse, DctRepairConsumedItemsRequest, DctRepairConsumedItemsResponse } from './model';

@Injectable({ providedIn: 'root' })
export class DCTRepairservice {
  private baseUrl = 'api/dctrepair';
  private baseUrl2 = 'api/dctrepair/itemforrepair';
  private prodStatusURL = 'api/dctrepair/productstatus'
  constructor(private _http: HttpClient) { }
  public getfaultyLineItems(page: number, column: string, order: string): Observable<Page<LocationFaultyLineItemResponse>> {
    return this._http.get<Page<LocationFaultyLineItemResponse>>(this.baseUrl2 + '?page=' + page + '&size=20' + '&' + 'sort=' + column + ',' + order);
  }
  public postDCTRpair(dctreq: DCTRepairRequest): Observable<DCTRepairRequest> {
    return this._http.post<DCTRepairRequest>(this.baseUrl, dctreq);
  }
  public getProductStatus(): Observable<Array<string>> {
    return this._http.get<Array<string>>(this.prodStatusURL)
  }
  public getDCTRepairByid(id: number): Observable<LocationFaultyLineItemResponse> {
    return this._http.get<LocationFaultyLineItemResponse>(this.baseUrl + '/' + id);
  }
  public getRepairBySerialNumbrAndPartCode(page: number, serialNumbr: string, partCOde: string): Observable<Page<RepairDCTResponse>> {
    return this._http.get<Page<RepairDCTResponse>>(this.baseUrl + '?serialNo=' + serialNumbr + '&partcode=' + partCOde + '&page=' + page + '&size=20');
  }
  public getRepairItmonsumdResponse(id: number, page: number): Observable<Page<DctRepairConsumedItemsResponse>> {
    return this._http.get<Page<DctRepairConsumedItemsResponse>>(this.baseUrl + '/related' + '/' + id + '?page=' + page + '&size=20');
  }
  public getOkproductByPartCode(partCode: string): Observable<Array<string>> {
    return this._http.get<Array<string>>(this.baseUrl + '/' + partCode + '/okitemsserialnumber');
  }
  public getProblemMaster(partCode): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/problemmaster/${partCode}`);
  }
}