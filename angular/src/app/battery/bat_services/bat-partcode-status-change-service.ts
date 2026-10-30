import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "../bt_model";

@Injectable({providedIn:'root'})
export class BtPartcodeStatusChangeService{
    private baseurl='bt/api/productstatuschange';
    constructor(private _http:HttpClient){}

    public getList(filter):Observable<Page<any>>{
        return this._http.get<Page<any>>(`${this.baseurl}?${filter}`);
    }

    public getSingle(id):Observable<any>{
        return this._http.get<any>(`${this.baseurl}/${id}`);
    }

    public postRequest(request):Observable<any>{
        return this._http.post(`${this.baseurl}`,request);
    }


}