import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "./model";

@Injectable({providedIn:'root'})
export class BulkutilService{
    private baseurl='api/bulkutil';
    constructor(private http:HttpClient){}
    public getBulkUtils(filters?):Observable<Page<any>>{
        return this.http.get<Page<any>>(`${this.baseurl}`,{params:filters});
    }
    public getBulkUtilById(id){
        return this.http.get(`${this.baseurl}/${id}`);
    }
    public getBulkAction(id,filters?):Observable<Page<any>>{
        return this.http.get<Page<any>>(`${this.baseurl}/${id}/actions`,{params:filters});
    }

}