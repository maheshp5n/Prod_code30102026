import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactForList, LocationForList, LocationResponse, Page, ResourceType, ServiceAppointmentResponse, ServiceResourseForList, ServiceResourseRequest, ServiceResourseResponse, UserForList } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtserviceresourseService {

  private baseUrl = 'bt/api/serviceresource';
  private recentURL='bt/api/serviceresource/recentvisit';
  // private urlUserForList = 'api/users/forlist';
  // private baseUrlResourceType = 'api/serviceresource/resourcetype';
  private urlLocationForList = 'bt/api/location/forlist';
  private urlContactsForList = 'bt/api/contacts/forlist/serviceresource';

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



  public getResourceTypeForList(): Observable<Array<ResourceType>> {
    return this._http.get<Array<ResourceType>>(this.baseUrl+'/resourcetype');
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
    return this._http.get<Array<ServiceResourseForList>>(this.baseUrl+'/forList');
  }  
  public getSearchResult(searchparam): Observable<Array<ServiceResourseResponse>> {
    return this._http.get<Array<ServiceResourseResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }

}
