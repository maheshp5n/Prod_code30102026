import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "./model";

@Injectable({providedIn:'root'})
export class PartcodechangeService{
    private baseurl=`/api/partcodechange`;
    private PageUrl=`/api/partcodechange/approved/page`
    
    constructor(private _http:HttpClient){}

    public getApprovedList():Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/approved`);
    }
    public getApprovedListPageable(page?:any,size?:any):Observable<Page<any>> {
        // let url = `${this.baseurl}?`;
        // if (filter) {
    
        //     url = `${url}&${filter}`;
           
        // }
        // if (pageInfo) {
        //     url = `${url}&${pageInfo}`;
        // }
    return this._http.get<Page<any>>(this.PageUrl+'?page='+page+'&size='+size);
        // return this._http.get<Page<any>>(`${this.baseurl}/approved/page`);
    }
    public getpendingList():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/pending`);
    }
    public approveById(id,data){
        return this._http.post(`${this.baseurl}/${id}`,data);
    }
    public approveSelected(ids:Array<any>){
        return this._http.post(this.baseurl,ids);
    }
    public searchPendingToApprove(searchkey):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/pending/search`,{params:searchkey});
    }
    public searchApproved(searchkey):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/approved/search`,{params:searchkey});
    }


    public getEligableForchange():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/noworkflorequest`);
    }

    public createNewWorkFlow(data){
        return this._http.post(`${this.baseurl}/noworkflorequest`,data);
    }
}