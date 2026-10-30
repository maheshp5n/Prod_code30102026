import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({providedIn:'root'})
export class SapDocumentService{
    private baserurl='bt/api/sapdoc';
    constructor(private _http:HttpClient){}

    public getSkHdr(manulaId):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baserurl}/stkhdrdoc/${manulaId}`);
    }

    public getStoHdr(manulaId):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baserurl}/stohdrdoc/${manulaId}`);
    }

}