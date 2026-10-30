import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AccountsType } from "./model";

@Injectable()
export class AccountsTypeService{
    constructor(private _http:HttpClient){}
    public getAccountsType():Observable<Array<AccountsType>>{
        return this._http.get<Array<AccountsType>>('api/accountstype');
    }
}