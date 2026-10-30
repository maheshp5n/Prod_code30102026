import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ToBeApproved, Page, ApprovalRequest } from './model';

@Injectable({ providedIn: 'root' })
export class ItemsToBeApprovedService {
    private baseUrl = 'api/itemtobeapproved';
    constructor(private _http: HttpClient) { }
    public getItemsToBeApproved(page?: number, column?: string, order?: string, filter?): Observable<Page<ToBeApproved>> {
        let url = this.baseUrl;

        url = `${this.baseUrl}` + '?page=' + page + '&size=20&' + "sort=" + column + "," + order;

        return this._http.get<Page<ToBeApproved>>(url);
    }
    public approveRejectItems(approvalId, appRequest: ApprovalRequest) {
        return this._http.put(`${this.baseUrl}/${approvalId}/approvereject`, appRequest);
    }
    public getItemToBeApprovdButton(objectName: string, objectid: any): Observable<ToBeApproved> {
        return this._http.get<ToBeApproved>(`${this.baseUrl}/hasapproval?objectname=${objectName}&objectid=${objectid}`);
    }
    public multipleApproval(lst: Array<any>): Observable<any> {
        return this._http.put(`${this.baseUrl}/multipleapproval`, lst);
    }

    public getAdvanceSearchResult(search):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:search});
    }
}