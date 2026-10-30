import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountsForList, AssetsForList } from './model';

@Injectable()
export class WorkOrderService {
    constructor(private _http:HttpClient){}

    public getAccountsForList():Observable<Array<AccountsForList>>{
        return this._http.get<Array<AccountsForList>>('api/accounts/forlist');
    }
    
    public getAssetsForList():Observable<Array<AssetsForList>>{
        return this._http.get<Array<AssetsForList>>(`api/assets/forlist`);
    }
     

}