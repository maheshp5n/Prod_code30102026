import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "../bt_model";

@Injectable({providedIn:'root'})
export class BatPartcodeChangeService{
    private baseurl='bt/api/partcodechange';
    constructor(private _http:HttpClient){}

    public getDetails(filter?):Observable<Page<any>>{
        return this._http.get<Page<any>>(`${this.baseurl}?${filter}`);
    }
    public getSingleDetails(id):Observable<any>{
        return this._http.get(`${this.baseurl}/${id}`);
    }

    public postDetails(request):Observable<any>{
        return this._http.post(`${this.baseurl}`,request);
    }
}