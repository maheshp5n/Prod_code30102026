import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Page, AccountCreate, AccountsForList, Contacts } from "./model";

@Injectable({providedIn:'root'})
export class sitedetailService{

    constructor(private _http:HttpClient){}
    
    public getAccounts(page?:number):Observable<Page<AccountCreate>>{
        if(page){
            return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts');
    }
    public getRecentVisit(page?:number):Observable<Page<AccountCreate>>{
        if(page){
            return this._http.get<Page<AccountCreate>>(`api/accounts/recentvisit?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts/recentvisit');
    }
    public getMyAccount(page?:number):Observable<Page<AccountCreate>>{
        if(page){
            return this._http.get<Page<AccountCreate>>(`api/accounts/myaccount?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts/myaccount');
    }
    public getAccount(sapNumber:string):Observable<AccountCreate>{
        return this._http.get<AccountCreate>(`api/accounts/${sapNumber}`);
    }
    public postAccounts(accountDetails:AccountCreate):Observable<AccountCreate>{
        return this._http.post<AccountCreate>('api/accounts',accountDetails);
    }
    public getAccountsForList():Observable<Array<AccountsForList>>{
        return this._http.get<Array<AccountsForList>>('api/accounts/forlist');
    }
    public getContactList(sapNumber):Observable<Array<Contacts>>{
        return this._http.get<Array<Contacts>>(`api/accounts/${sapNumber}/contacts`);
    }
}