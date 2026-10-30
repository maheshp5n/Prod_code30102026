import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ReportwizardService {
    private baseurl = 'api/report';
    constructor(private _http: HttpClient) {

    }
    public getAllReport(type?): Observable<Array<any>> {
        if (type) {
            return this._http.get<Array<any>>(`${this.baseurl}?type=${type}`);
        }
        return this._http.get<Array<any>>(this.baseurl);
    }
    public getParams(reportId): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/${reportId}/param`);
    }
    public getExecuter(reportId): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/${reportId}/executer`);
    }
    public postReportWizard(creport) {
        return this._http.post(`${this.baseurl}/wizard`, creport);
    }
    public editReportWizard(id, creport) {
        return this._http.put(`${this.baseurl}/wizard/${id}`, creport);
    }
    public executeReport(reportId, param): Observable<Array<any>> {
        return this._http.post<Array<any>>(`${this.baseurl}/execute/${reportId}`, param);
    }
    public getWizard(): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/wizard`);
    }
    public getUserRoleAndUsers(type): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/executertype?type=${type}`);
    }

    public deletereport(id){
        return this._http.delete(`${this.baseurl}/wizard/${id}`);
    }
    public getTypes(): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/wizard/forlist`);
    }
    public getSearchResult(searchparam): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/advancesearch`, {params:searchparam});
      }
}
