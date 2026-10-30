import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Page,
  ServiceResourseRequest,
  ServiceResourseResponse,
  ResourceType,
  UserForList,
  LocationForList,
  ContactForList,
  ServiceAppointmentResponse,
  LocationResponse,
  ServiceResourseForList
} from './model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ServiceResourceService {
  private baseUrl = 'api/serviceresource';
  private recentURL='api/serviceresource/recentvisit';
  private urlUserForList = 'api/users/forlist';
  private baseUrlResourceType = 'api/serviceresource/resourcetype';
  private urlLocationForList = 'api/location/forlist';
  private urlContactsForList = 'api/contacts/forlist/serviceresource';

  constructor(private _http: HttpClient) { }

  public getServiceResource(filter?:string,pageInfo?:string): Observable<Page<ServiceResourseResponse>> {
    let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
    return this._http.get<Page<ServiceResourseResponse>>(url);
  }

  public getServiceResourceById(serviceid: number): Observable<ServiceResourseResponse> {
    return this._http.get<ServiceResourseResponse>(this.baseUrl + '/' + serviceid);
  }

  public getUserForList(): Observable<Array<UserForList>> {
    return this._http.get<Array<UserForList>>(this.urlUserForList);
  }

  public getResourceTypeForList(): Observable<Array<ResourceType>> {
    return this._http.get<Array<ResourceType>>(this.baseUrlResourceType);
  }

  public getLocationForList(): Observable<Array<LocationForList>> {
    return this._http.get<Array<LocationForList>>(this.urlLocationForList);
  }


  public postServiceResource(serviceResource: ServiceResourseRequest): Observable<ServiceResourseResponse> {
    return this._http.post<ServiceResourseResponse>(this.baseUrl, serviceResource);
  }

  public getRecentServiceResource(filter?:string,pageInfo?:string): Observable<Page<ServiceResourseResponse>> {
    let url = `${this.recentURL}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ServiceResourseResponse>>(url);
  }

  public putServiceResource(id: number, serviceResource: ServiceResourseRequest): Observable<ServiceResourseResponse> {
    return this._http.put<ServiceResourseResponse>(this.baseUrl + '/' + id, serviceResource);
  }

  public getContactsForList(): Observable<Array<ContactForList>> {
    return this._http.get<Array<ContactForList>>(this.urlContactsForList);
  }

  public getServiceAppointmentListByServiceResourceId(id: number, page: number): Observable<Page<ServiceAppointmentResponse>> {
    return this._http.get<Page<ServiceAppointmentResponse>>(this.baseUrl + '/related/' + id + '/ServiceAppointment' + '?page=' + page + '&size=5');
  }

  public getLocationListByServiceResourceId(id: number, page: number): Observable<Page<LocationResponse>> {
    return this._http.get<Page<LocationResponse>>(this.baseUrl + '/related/' + id + '/location' + '?page=' + page + '&size=5');
  }

  public getServiceResourceForList():Observable<Array<ServiceResourseForList>>{
    return this._http.get<Array<ServiceResourseForList>>('api/serviceresource/forList');
  }  

 
  public getSearchResult(searchparam): Observable<Array<ServiceResourseResponse>> {
    return this._http.get<Array<ServiceResourseResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }
  public getRatingSearchResult(searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/rating/search`, {params:searchparam});
  }
  public getServiceResourceCustomerRating(filter?:string,pageInfo?:string): Observable<Page<any>> {
    let url = `${this.baseUrl}/rating?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
    return this._http.get<Page<any>>(url);
  }

}
