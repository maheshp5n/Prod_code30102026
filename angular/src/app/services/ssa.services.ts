import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SSARequest, Page, SSAResponse, SSAForlist, SiteDetailsResponse } from './model';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class ssaService{
    baseUrl='api/ssa';
    RelatedUrlsites='api/sites/related/ssa'
  

    constructor(private http:HttpClient){}
    public saveSSA(request:SSARequest):Observable<SSAResponse>{
       return this.http.post<SSAResponse>(this.baseUrl,request);

    }
  
    public getSSA(filter?:string,pageInfo?:string):Observable<Page<SSAResponse>>{
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
            return this.http.get<Page<SSAResponse>>(url);
    
    }
  
  
    public getSSAForlist(circleName:string):Observable<Array<SSAForlist>>{
        return this.http.get<Array<SSAForlist>>(`${this.baseUrl}/forlist?circleName=${encodeURI(circleName)}`);
    }
    public updateSSA(ssaReq:SSARequest):Observable<SSAResponse>{
      
        return this.http.put<SSAResponse>(`${this.baseUrl}`,ssaReq);

    }
    public getSSAById(id:number):Observable<SSAResponse>{
        return this.http.get<SSAResponse>(`${this.baseUrl}/${id}`);
    }
    public getSiteBySSAID(ssaId:number,page:number):Observable<Page<SiteDetailsResponse>>{
        return this.http.get<Page<SiteDetailsResponse>>(this.RelatedUrlsites+'/'+ssaId + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
    }
    public getSearchResult(searchparam): Observable<Array<SSAResponse>> {
        return this.http.get<Array<SSAResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
      }
    

}