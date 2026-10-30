import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Contacts,
  Page,
  BoardingLoading,
  BoardingLoadingForListResponse,
  ContactType,
  ContactDesignation,
  ContactForList,
  ExpenseManagementResponse
} from './model';
@Injectable({ providedIn: 'root' })
export class ContactsService {
  private baseUrl = 'api/contacts';
  private baseUrl2 = 'api/boardingloading';
  private baseUrlRecent = 'api/contacts/recentvisit';
  private baseUrlType = 'api/contacts/type';
  private baseUrlDesignation = 'api/contacts/designation';
  private baseUrlDropdown = 'api/contacts/list';
  private boardingForList = 'api/boardingloading/boardingforlist';
  private contactForlistUrl = 'api/contacts/forlist';
  constructor(private _http: HttpClient) { }

  public getContacts(filter?: string, pageInfo?: string): Observable<Page<Contacts>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<Contacts>>(url);
  }

  public getContact(id: number): Observable<Contacts> {
    return this._http.get<Contacts>(`${this.baseUrl}/${id}`);
  }

  public postContacts(contact: Contacts): Observable<Contacts> {
    return this._http.post<Contacts>(this.baseUrl, contact);
  }

  public getBoardingName(id: string): Observable<BoardingLoading> {
    return this._http.get<BoardingLoading>(this.baseUrl2 + '/' + id);
  }

  public editContacts(id: number, contact: Contacts): Observable<Contacts> {
    return this._http.put<Contacts>(this.baseUrl + '/' + id, contact);
  }

  public getRecentContacts(filter?:string,pageInfo?:string): Observable<Page<Contacts>> {
    let url = `${this.baseUrlRecent}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<Contacts>>(url);
  }

  public getTypeForList(): Observable<Array<ContactType>> {
    return this._http.get<Array<ContactType>>(this.baseUrlType);
  }

  public getDesignationForlist(): Observable<Array<ContactDesignation>> {
    return this._http.get<Array<ContactDesignation>>(this.baseUrlDesignation);
  }

  public getContactsByType( filter?: string, pageInfo?: string): Observable<Page<Contacts>> {
    let url = `${this.baseUrlDropdown}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<Contacts>>(url);
    // return this._http.get<Page<Contacts>>(this.baseUrlDropdown + i + '&page=' + page + '&size=20' + '&' + 'sort=' +
    //   column + ',' + order);
  }

  public getBoardingForList(): Observable<Array<BoardingLoadingForListResponse>> {
    return this._http.get<Array<BoardingLoadingForListResponse>>(this.boardingForList);
  }

  public getcontactsForList(): Observable<Array<ContactForList>> {
    return this._http.get<Array<ContactForList>>(this.contactForlistUrl);
  }

  // public getExpenseManagementListByExpenseId(id: number, page: number): Observable<Page<ExpenseManagementResponse>> {
  //   return this._http.get<Page<ExpenseManagementResponse>>(
  //     this.baseUrl + '/related/' + id + '/expenseManagement' + '?page=' + page + '&size=5');
  // }

  public getContactsByEmployeeEmailId(id): Observable<Contacts> {
    return this._http.get<Contacts>(this.baseUrl + '/employee?emailId=' + id);
  }
  public getDepartments():Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseUrl}/department`);
  }
  public getAdvanceSearch(keysdata):Observable<Array<any>>{
    return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:keysdata});
  }
}
