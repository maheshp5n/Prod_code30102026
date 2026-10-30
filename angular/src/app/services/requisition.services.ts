import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { RequisitionStatus, RequisitionProductType, RequisitionApprovalStatus, RequisitionRequest, Page, RequisitionResponse, WarehouseForList } from './model';

@Injectable({providedIn:'root'})
export class RequisitionService {
    constructor(private _http: HttpClient) { }
    url: string = 'api/requisition';

    public getAllRequisition(page: number, column: string, order: string): Observable<Page<RequisitionResponse>> {
        return this._http.get<Page<RequisitionResponse>>(this.url + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getRecentRequisition(page: number, column: string, order: string): Observable<Page<RequisitionResponse>> {
        return this._http.get<Page<RequisitionResponse>>(`api/requisition/recentvisit` + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getRequisitionById(id:number):Observable<RequisitionResponse>{
        return this._http.get<RequisitionResponse>(`api/requisition/${id}`);        
    }
    public postRequisition(request: RequisitionRequest): Observable<RequisitionRequest> {
        return this._http.post<RequisitionRequest>(`api/requisition`, request);
    }
    
    public updateRequisition(request: RequisitionRequest): Observable<RequisitionRequest> {
        return this._http.put<RequisitionRequest>(`api/requisition`, request);
    }

    public getStatus(): Observable<Array<RequisitionStatus>> {
        return this._http.get<Array<RequisitionStatus>>(`api/requisition/status`);
    }
    public getProductType():Observable<Array<RequisitionProductType>>{
        return this._http.get<Array<RequisitionProductType>>('api/requisition/producttype');
    }
    public getApprovalStatus():Observable<Array<RequisitionApprovalStatus>>{
        return this._http.get<Array<RequisitionApprovalStatus>>(`api/requisition/approvalstatus`)
    }
    public getWarehouseForList():Observable<Array<WarehouseForList>>{
        return this._http.get<Array<WarehouseForList>>(`api/warehouse/forlist`)
    }
   
}
