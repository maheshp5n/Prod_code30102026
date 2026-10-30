import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Login, userRole, AccountsForList, ContactForList, CreateLoginRequest, ObjectAccessResponse } from "./model";
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class Loginservices {
    constructor(private _http: HttpClient) { }

    public doLogin(userdetails: Login): Observable<any> {
        return this._http.post<any>('api/login', userdetails);
    }
    public doforceLogin(userdetails: Login): Observable<any> {
        return this._http.post<any>('api/login/force', userdetails);
    }

    public getLoggedInUser(): Login {
        return JSON.parse(localStorage.getItem('login'));
    }

    public getAuthorities(): Observable<Array<userRole>> {
        return this._http.get<Array<userRole>>('api/login/authorities');
    }
    public getAccountId(): Observable<Array<AccountsForList>> {
        return this._http.get<Array<AccountsForList>>('api/accounts/forlist');
    }
    public getContactDetails(sapnumber): Observable<Array<ContactForList>> {
        return this._http.get<Array<ContactForList>>(`api/accounts/${sapnumber}/contacts/forlist`)
    }
    public createLoginUser(payload: CreateLoginRequest): Observable<any> {
        return this._http.post<any>('api/login/create', payload);
    }
    public updateLoginInfo(payload: CreateLoginRequest): Observable<any> {
        return this._http.put<any>(`api/login/update`, payload);
    }
    public isUserValidFor(object: string, right: string): boolean {
        let l = JSON.parse(localStorage.getItem('login'));
        if (l == undefined || l == null) {
            return false;
        }
        let v: Array<ObjectAccessResponse> = l.objectAccess;
        if (v.length > 0) {
            for (let i = 0; i < v.length; i++) {
                if (v[i].objectName == object) {
                    if (right == 'view') {
                        return v[i].viewAllRights || v[i].viewRights;
                    }
                    if (right == 'create') {
                        return v[i].createRights;
                    }
                    if (right == 'edit') {
                        return v[i].editRights;
                    }
                    if (right == 'delete') {
                        return v[i].deleteRights;
                    }
                }
            }
        }
        return false;
    }

    public getOtp(data) {
        return this._http.post(`api/login/otp`, data);
    }
    public getRecover(data) {
        return this._http.post(`api/login/recover`, data);
    }
    public getRoleAuthorities(): Observable<Array<userRole>> {
        return this._http.get<Array<userRole>>('api/users/authorities');
    }

    public getNavigation(id){
        return this._http.get(`api/login/navigation/${id}`);
    }
}
