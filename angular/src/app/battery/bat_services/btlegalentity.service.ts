import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contacts, LegalEntityResponse, LegaLEntityType, LocationResponse, Login, Page, ServiceCenterForList } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtlegalentityService {
  baseUrl='bt/api/legalentity';
  recentVisitUrl='bt/api/legalentity/recentvisit';
  getTableUrl:'bt/api/legalentity';
  forListUrl='bt/api/legalentity/forlist';
  RelatedUrl='bt/api/legalentity/releted'
  legalEntityTypeUrl='bt/api/legalentity/type'

  constructor(private _http: HttpClient) { }
  
  public saveLigalEntity(req:LegalEntityResponse):Observable<LegalEntityResponse>{
      
    return this._http.post<LegalEntityResponse>('bt/api/legalentity',req);

}

public getLegalEntity(filter?:string,pageInfo?:string):Observable<Page<LegalEntityResponse>>{
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
        return this._http.get<Page<LegalEntityResponse>>(url);
  
   
}

public getRecentVisit(filter?:string,pageInfo?:string):Observable<Page<LegalEntityResponse>>{
    let url = `${this.recentVisitUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
        return this._http.get<Page<LegalEntityResponse>>(url);
   
 
}

public loginIdMethod():Observable<Login>{
    return this._http.get<Login>('bt/api/legalentity/recentvisit');
}


public getLigalENtityById(id:number):Observable<LegalEntityResponse>{
    return this._http.get<LegalEntityResponse>(`${this.baseUrl}/${id}`);
}
public getContactById(id:number):Observable<Contacts>{
    return this._http.get<Contacts>(`${this.baseUrl}/${id}`);
}
public getLegalEntityId():Observable<Page<LegalEntityResponse>>{
    return this._http.get<Page<LegalEntityResponse>>(this.baseUrl);
}
public updateEntity(request:LegalEntityResponse,legalEntityId:number):Observable<LegalEntityResponse>{
  
    return this._http.put<LegalEntityResponse>(`${this.baseUrl}/${legalEntityId}`,request);

}

public getLegalEntityForList():Observable<Array<ServiceCenterForList>>{
        return this._http.get<Array<ServiceCenterForList>>(this.forListUrl);
}
public getLocationById(legalEntityId:number,page:number,column:string,order:string):Observable<Page<LocationResponse>>{
    return this._http.get<Page<LocationResponse>>(this.RelatedUrl+'/'+legalEntityId+'/location' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
}
public getEngById(legalEntityId:number,page:number,column:string,order:string):Observable<Page<Contacts>>{
    return this._http.get<Page<Contacts>>(this.RelatedUrl+'/'+legalEntityId+'/eng' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
}
public getLegalEntityType():Observable<Array<LegaLEntityType>>{
    return this._http.get<Array<LegaLEntityType>>(this.legalEntityTypeUrl);
}
public getSearchResult(searchparam): Observable<Array<LegalEntityResponse>> {
return this._http.get<Array<LegalEntityResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
}
}
