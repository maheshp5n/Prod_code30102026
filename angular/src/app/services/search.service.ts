import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({providedIn:'root'})
export class SearchService{
    private url='api/search';
    constructor(private _http:HttpClient){

    }
    searchdata(searchKeyString:any):Observable<Array<GlobalSearchIndex>>{
        return this._http.get<Array<GlobalSearchIndex>>(`${this.url}?searchkey=${searchKeyString}`);
    }
    searchAdvanceData(key:any):Observable<any>{
        return this._http.get<any>(`${this.url}/advance?searchkey=${key}`);
    }
    public getAdvanceSearchResult(searchContent,baseUrl):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${baseUrl}/advancesearch`,{params:searchContent});
    }

}
export class GlobalSearchIndex{
    searchId:number;
    searchString:string;
    keyName:string;
    objectName:string;
    objectDetailsUrl:string;
    objectIdName:string;
    objectIdValue:string;
}