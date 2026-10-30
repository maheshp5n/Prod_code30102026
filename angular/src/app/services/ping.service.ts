import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PingPoingService{
    constructor(private _http:HttpClient){}
    public ping(){
        return this._http.get('api/ping');
    }
    public logout(){
        return this._http.get('api/ping/logout');
    }
}