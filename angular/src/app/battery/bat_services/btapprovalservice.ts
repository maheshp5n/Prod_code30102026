import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "../../services/model";

@Injectable({ providedIn: 'root' })
export class BtApprovalService {

    private baseurl: string = '/bt/api/approval';

    constructor(private http: HttpClient) { }

    public getApprovalPage(pageefilter?): Observable<Page<any>> {
        return this.http.get<Page<any>>(`${this.baseurl}?${pageefilter}`);
    }
    public getSingleByApprovalId(id): Observable<any> {
        return this.http.get<any>(`${this.baseurl}/${id}`);
    }
    public createApproval(data) {
        return this.http.post(this.baseurl, data);
    }
    public updateApproval(id, data) {
        return this.http.put(`${this.baseurl}/${id}`, data);
    }
    public createApprovalStep(approvalId, stapedata) {
        return this.http.post(`${this.baseurl}/${approvalId}/stape`, stapedata);
    }
    public updateApprovalStep(approvalId, stapeId, data) {
        return this.http.put(`${this.baseurl}/${approvalId}/stape/${stapeId}`, data);
    }
    public getApprovalStepsByApprovalStepId(approvalId, stapeId): Observable<any> {
        return this.http.get<any>(`${this.baseurl}/${approvalId}/stape/${stapeId}`);
    }
    public getStepsByApprovalId(approvalId): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/${approvalId}/stapes`)
    }
    public enableDisableApprovalFlow(id, flag) {
        return this.http.put(`${this.baseurl}/enabledisable/${id}`, { flag: flag });
    }
    public getSearchResult(searchparam): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/advancesearch`, { params: searchparam });
    }


}