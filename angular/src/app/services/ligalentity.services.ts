import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {Login, AccountCreate, LegalEntityResponse, Contacts, Page, LocationResponse, ServiceCenterForList, LegaLEntityType } from "./model";

@Injectable({providedIn:'root'})
export class ligalEntityService{
    baseUrl='api/legalentity';
    recentVisitUrl='api/legalentity/recentvisit';
    getTableUrl:'api/legalentity';
    forListUrl='api/legalentity/forlist';
    RelatedUrl='api/legalentity/releted'
    legalEntityTypeUrl='api/legalentity/type'

    constructor(private http:HttpClient){}


    public saveLigalEntity(req:LegalEntityResponse):Observable<LegalEntityResponse>{
      
        return this.http.post<LegalEntityResponse>('api/legalentity',req);

    }

    public getLegalEntity(filter?:string,pageInfo?:string):Observable<Page<LegalEntityResponse>>{
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
            return this.http.get<Page<LegalEntityResponse>>(url);
      
       
    }

    public getRecentVisit(filter?:string,pageInfo?:string):Observable<Page<LegalEntityResponse>>{
        let url = `${this.recentVisitUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
            return this.http.get<Page<LegalEntityResponse>>(url);
       
     
    }

    public loginIdMethod():Observable<Login>{
        return this.http.get<Login>('api/legalentity/recentvisit');
    }
   

    public getLigalENtityById(id:number):Observable<LegalEntityResponse>{
        return this.http.get<LegalEntityResponse>(`${this.baseUrl}/${id}`);
    }
    public getContactById(id:number):Observable<Contacts>{
        return this.http.get<Contacts>(`${this.baseUrl}/${id}`);
    }
    public getLegalEntityId():Observable<Page<LegalEntityResponse>>{
        return this.http.get<Page<LegalEntityResponse>>(this.baseUrl);
    }
    public updateEntity(request:LegalEntityResponse,legalEntityId:number):Observable<LegalEntityResponse>{
      
        return this.http.put<LegalEntityResponse>(`${this.baseUrl}/${legalEntityId}`,request);

    }
 
    public getLegalEntityForList():Observable<Array<ServiceCenterForList>>{
            return this.http.get<Array<ServiceCenterForList>>(this.forListUrl);
    }
    public getLocationById(legalEntityId:number,page:number,column:string,order:string):Observable<Page<LocationResponse>>{
        return this.http.get<Page<LocationResponse>>(this.RelatedUrl+'/'+legalEntityId+'/location' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
    }
    public getEngById(legalEntityId:number,page:number,column:string,order:string):Observable<Page<Contacts>>{
        return this.http.get<Page<Contacts>>(this.RelatedUrl+'/'+legalEntityId+'/eng' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
    }
    public getLegalEntityType():Observable<Array<LegaLEntityType>>{
        return this.http.get<Array<LegaLEntityType>>(this.legalEntityTypeUrl);
}
public getSearchResult(searchparam): Observable<Array<LegalEntityResponse>> {
    return this.http.get<Array<LegalEntityResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }

}