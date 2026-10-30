import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountCreate, AccountsForList, AssetsForList, BoardingLoading, CaseResponse, ContactForList, Contacts, Page, SiteDetailsResponse } from '../../services/model';
import { AssetForList } from '../bt_model';
@Injectable({
  providedIn: 'root'
})
export class BtaccountService {

  constructor(private _http: HttpClient) { }
  baseUrl: string = 'bt/api/accounts';
  public getBtAccounts(filter?: string, pageInfo?: string): Observable<Page<AccountCreate>> {
      let url = `${this.baseUrl}?`;
      if (filter) {
          url = `${url}&${filter}`;
      }
      if (pageInfo) {
          url = `${url}&${pageInfo}`;
      }
      return this._http.get<Page<AccountCreate>>(url);
  }
  public getBtRecentVisit(page: number, column: string, order: string): Observable<Page<AccountCreate>> {
    return this._http.get<Page<AccountCreate>>(this.baseUrl +'/recentvisit' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
  }

  public getMyBtAccount(page?: number): Observable<Page<AccountCreate>> {
      if (page) {
          return this._http.get<Page<AccountCreate>>(`${this.baseUrl}/myaccount?page=${page}`);
      }
      return this._http.get<Page<AccountCreate>>(`${this.baseUrl}/myaccount`);
  }
  public getBtAccount(sapNumber: string): Observable<AccountCreate> {
      return this._http.get<AccountCreate>(`${this.baseUrl}/${sapNumber}`);
  }
  public postBtAccounts(accountDetails: AccountCreate): Observable<AccountCreate> {
      return this._http.post<AccountCreate>(this.baseUrl, accountDetails);
  }
  public getBtAccountsForList(): Observable<Array<AccountsForList>> {
      return this._http.get<Array<AccountsForList>>(`${this.baseUrl}/forlist`);
  }

  public updateBtAccountDetails(accountDetails: AccountCreate, sapNumber: string,): Observable<AccountCreate> {
      return this._http.put<AccountCreate>(`${this.baseUrl}/${sapNumber}`, accountDetails);

  }
  public getBtBoardingList(): Observable<Array<AccountsForList>> {
      return this._http.get<Array<AccountsForList>>('api/boardingloading');
  }

  public getBtContactList(sapNumber: string): Observable<Array<Contacts>> {
      return this._http.get<Array<Contacts>>(`${this.baseUrl}/${sapNumber}/contacts`);
  }

  public postBtBoardingList(boardingLoading: BoardingLoading): Observable<BoardingLoading> {
      return this._http.post<BoardingLoading>('api/boardingloading', boardingLoading);
  }

  public getBtSitesList(sapNumber: string): Observable<Array<SiteDetailsResponse>> {
      return this._http.get<Array<SiteDetailsResponse>>(`${this.baseUrl}/${sapNumber}/sites`);
  }
  public getBtCaseList(sapNumber: String, page: number): Observable<Page<CaseResponse>> {
      return this._http.get<Page<CaseResponse>>(`${this.baseUrl}/case/${sapNumber}` + '?page=' + page + '&size=5&');
  }
  public getAllBtCaseList(sapNumber: String): Observable<Page<CaseResponse>> {
    return this._http.get<Page<CaseResponse>>(`${this.baseUrl}/case/${sapNumber}`);
}
  public getBtContactsByAccountId(sapNumber: String, page: number): Observable<Page<Contacts>> {
      return this._http.get<Page<Contacts>>(`${this.baseUrl}/${sapNumber}/contacts/related` + '?page=' + page + '&size=5&');
  }
  public getBtSitesByAccount(sapNumber: String, page: number,size:number): Observable<Page<SiteDetailsResponse>> {
      return this._http.get<Page<SiteDetailsResponse>>(`${this.baseUrl}/${sapNumber}/sites/related` + '?page=' + page + '&size='+size+'&');
  }
  public getAllBtSitesByAccount(sapNumber: String): Observable<Page<SiteDetailsResponse>> {
      return this._http.get<Page<SiteDetailsResponse>>(`${this.baseUrl}/${sapNumber}/sites/related`);
  }
  public getBtContactByAccount(sapNumber: string): Observable<Array<ContactForList>> {
      return this._http.get<Array<ContactForList>>(`${this.baseUrl}/${sapNumber}/contacts/forlist`);
  }
  public getBtAssetsListByAsscountSapNumber(sapnumber: string): Observable<Array<AssetForList>> {
      return this._http.get<Array<AssetForList>>(`${this.baseUrl}/${sapnumber}/assets/forlist`);
  }
  public getBtAddressAccountId(sapNumber: string): Observable<AccountCreate> {
      return this._http.get<AccountCreate>(this.baseUrl + `/address/${sapNumber}`);
  }
  //EDIT BY RAUSHAN FOR BETTER FILTER

  public getBtCaseListS(sapNumber, filter): Observable<Page<CaseResponse>> {
      return this._http.get<Page<CaseResponse>>(`${this.baseUrl}/case/${sapNumber}?${filter}`);
  }
  public getBtContactsByAccountIdS(sapNumber, filter): Observable<Page<Contacts>> {
      return this._http.get<Page<Contacts>>(`${this.baseUrl}/${sapNumber}/contacts/related?${filter}`);
  }
  public getBtSitesByAccountS(sapNumber, filter): Observable<Page<SiteDetailsResponse>> {
      return this._http.get<Page<SiteDetailsResponse>>(`${this.baseUrl}/${sapNumber}/sites/related${filter}`);
  }
  public getBtSearchResult(searchparam): Observable<Array<AccountCreate>> {
      return this._http.get<Array<AccountCreate>>(`${this.baseUrl}/advanceSearch`,{params:searchparam});
    }

    // public getAccountId(): Observable<Array<AccountsForList>> {
    //     return this._http.get<Array<AccountsForList>>('api/accounts/forlist');
    // }
    public getAccountId(): Observable<Array<AccountsForList>> {
        return this._http.get<Array<AccountsForList>>(`${this.baseUrl}/forlist`);
    }
    public getContactDetails(sapnumber): Observable<Array<ContactForList>> {
        return this._http.get<Array<ContactForList>>(`${this.baseUrl}/${sapnumber}/contacts/forlist`)
    }

    public getContactDetailsNotUser(sapnumber):Observable<Array<ContactForList>> {
        return this._http.get<Array<ContactForList>>(`${this.baseUrl}/${sapnumber}/contacts/notuser/forlist`)
    }
}
