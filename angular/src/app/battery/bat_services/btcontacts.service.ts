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
} from '../../services/model';
@Injectable({
  providedIn: 'root'
})
export class BtcontactsService {

    private baseUrl = 'bt/api/contacts';//
    // private baseUrl2 = 'api/boardingloading';
    private baseUrlRecent = 'bt/api/contacts/recentvisit';//
    private baseUrlType = 'bt/api/contacts/type';//
    private baseUrlDesignation = 'bt/api/contacts/designation';//
    private baseUrlDropdown = 'bt/api/contacts/list';//   
    private baseUrlContactsForList = 'bt/api/contacts/forlist/serviceresource/cirlce';
    private baseUrlAccount = 'bt/api/contacts/accounts';
    // private boardingForList = 'bt/api/boardingloading/boardingforlist';
    // private contactForlistUrl = 'api/contacts/forlist';
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

  
    public getcontactsForList(): Observable<Array<ContactForList>> {
      return this._http.get<Array<ContactForList>>(this.baseUrl+'/forlist');
    }
  
    public getExpenseManagementListByExpenseId(id: number, page: number): Observable<Page<ExpenseManagementResponse>> {
      return this._http.get<Page<ExpenseManagementResponse>>(
        this.baseUrl + '/related/' + id + '/expenseManagement' + '?page=' + page + '&size=5');
    }
    public getAllExpenseManagementListByExpenseId(id: number): Observable<Page<ExpenseManagementResponse>> {
      return this._http.get<Page<ExpenseManagementResponse>>(
        this.baseUrl + '/related/' + id + '/expenseManagement');
    }
  
    public getContactsByEmployeeEmailId(id): Observable<Contacts> {
      return this._http.get<Contacts>(this.baseUrl + '/employee?emailId=' + id);
    }
    public getDepartments():Observable<Array<any>>{
      return this._http.get<Array<any>>(`${this.baseUrl}/department`);
    }
    public getAdvanceSearch(keysdata):Observable<Array<any>>{
      return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:keysdata});
    }
    public getContactListForCircle(): Observable<Array<ContactForList>> {
      return this._http.get<Array<ContactForList>>(this.baseUrlContactsForList);
    }
    // Ayush
    public getBtContactsByAccountId(sapNumber: String, page: number): Observable<Page<Contacts>> {
      return this._http.get<Page<Contacts>>(`${this.baseUrl}/accounts/${sapNumber}` + '?page=' + page + '&size=5&');
  }
  }
  

