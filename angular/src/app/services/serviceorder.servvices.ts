import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from './model';

@Injectable({ providedIn: 'root' })
export class ServiceOrderService {
    private baseurl: string = 'api/serviceorder';
    constructor(private http: HttpClient) {

    }

    public getServiceOrders(filter?: any,pageInfo?:string): Observable<Page<any>> {
        let url = this.baseurl;
        if (filter) {
            url = `${url}?${filter}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this.http.get<Page<any>>(url);
    }
    public getServiceOrder(id): Observable<any> {
        return this.http.get(`${this.baseurl}/${id}`);
    }
    public saveWorkOrder(servicesorder): Observable<any> {
        return this.http.post(this.baseurl, servicesorder);
    }
    public getServiceOrderItems(id): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/${id}/items`);
    }
    public getFaultyDevice(): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/faultydevice`);
    }
    public getRepariCenter(): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/reparicenter`);
    }
    public getPendingToReceiveAll(): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/pendingtoreceive`);
    }
    public getPendintToReceiveByOrderId(id): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/${id}/pendingtoreceive`);
    }
    public receiveItems(items: Array<any>): Observable<any> {
        return this.http.post<any>(`${this.baseurl}/receive`, items);
    }

    public getRepariEng(): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/reparieng`);
    }
    public getSearchResult(searchparam): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/advancesearch`, {params:searchparam});
      }
      public getSearchResultforFaultyProd(searchparam): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/faultydevice/advancesearch`, {params:searchparam});
      }
      public getSearchResultforItems(searchparam,id): Observable<Array<any>> {
        return this.http.get<Array<any>>(`${this.baseurl}/${id}/items/advancesearch`, {params:searchparam});
      }
}
