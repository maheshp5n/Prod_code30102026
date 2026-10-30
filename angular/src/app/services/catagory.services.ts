import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {  BusinessDomain } from "./model";

@Injectable()
export class AccountsCatagoryServices{
    constructor(private _http:HttpClient){}
    public getCatagory():Observable<Array<BusinessDomain>>{
        return this._http.get<Array<BusinessDomain>>('api/businessdomain');
    }
}