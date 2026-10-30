import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page, ServiceOrderReturnItemResponse, ServiceOrderReturnResponse } from './model';

@Injectable({
  providedIn: 'root'
})
export class ServiceorderreturnService {
  private baseurl: string = 'api/serviceorderreturn';
  constructor(private http: HttpClient) {

  }
  
  public getSOR(filter?: any,pageInfo?:string): Observable<Page<any>> {
    let url = this.baseurl+`/list`;
    if (filter) {
        url = `${url}?${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<any>>(url);
}
public getSORbyid(id): Observable<ServiceOrderReturnResponse> {
    return this.http.get<ServiceOrderReturnResponse>(`${this.baseurl}/${id}`);
}
public getSearchResult(searchparam): Observable<Array<any>> {
  return this.http.get<Array<any>>(`${this.baseurl}/advancesearch`, {params:searchparam});
}
// advc search items
public getSORItemsSearchResult(sorid:number,searchparam): Observable<Array<any>> {
  return this.http.get<Array<any>>(`${this.baseurl}/${sorid}/items/advancesearch`, {params:searchparam});
}

// service order return Items
public getSORItemsbysorId(sorid:number,filter?: any,pageInfo?:string): Observable<Page<ServiceOrderReturnItemResponse>> {
  let url = this.baseurl+`/${sorid}`+`/item`;
  if (filter) {
      url = `${url}?${filter}`;
  }
  if (pageInfo) {
      url = `${url}&${pageInfo}`;
  }
  return this.http.get<Page<ServiceOrderReturnItemResponse>>(url);
}
}
