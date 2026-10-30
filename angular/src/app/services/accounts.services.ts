import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Page, AccountCreate, AccountsForList, Contacts, BoardingLoading, SiteDetailsResponse, CaseResponse, ContactForList, AssetsForList } from './model'
@Injectable()
export class AccountService {

    constructor(private _http: HttpClient) { }

    url: string = 'api/accounts';
    /* public getAccounts(page?:number):Observable<Page<AccountCreate>>{
        if(page){
            return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts');
    }
 */
    public getAccounts(filter?: string, pageInfo?: string): Observable<Page<AccountCreate>> {
        //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        let url = `${this.url}?`;
        if (filter) {
            url = `${url}&${filter}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this._http.get<Page<AccountCreate>>(url);
    }
    /* public getRecentVisit(page?:number):Observable<Page<AccountCreate>>{
        if(page)
        {
            return this._http.get<Page<AccountCreate>>(`api/accounts/recentvisit?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts/recentvisit');
    }
     */
    public getRecentVisit(page: number, column: string, order: string): Observable<Page<AccountCreate>> {
        //return this._http.get<Page<AccountCreate>>(`api/accounts/recentvisit?page=${page}' + '$size=5`);
        return this._http.get<Page<AccountCreate>>('api/accounts/recentvisit' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);

    }

    public getMyAccount(page?: number): Observable<Page<AccountCreate>> {
        if (page) {
            return this._http.get<Page<AccountCreate>>(`api/accounts/myaccount?page=${page}`);
        }
        return this._http.get<Page<AccountCreate>>('api/accounts/myaccount');
    }
    public getAccount(sapNumber: string): Observable<AccountCreate> {
        return this._http.get<AccountCreate>(`api/accounts/${sapNumber}`);
    }
    public postAccounts(accountDetails: AccountCreate): Observable<AccountCreate> {
        return this._http.post<AccountCreate>('api/accounts', accountDetails);
    }
    public getAccountsForList(): Observable<Array<AccountsForList>> {
        return this._http.get<Array<AccountsForList>>('api/accounts/forlist');
    }

    public updateAccountDetails(accountDetails: AccountCreate, sapNumber: string,): Observable<AccountCreate> {
        return this._http.put<AccountCreate>(`api/accounts/${sapNumber}`, accountDetails);

    }
    public getBoardingList(): Observable<Array<AccountsForList>> {
        return this._http.get<Array<AccountsForList>>('api/boardingloading');
    }

    public getContactList(sapNumber: string): Observable<Array<Contacts>> {
        return this._http.get<Array<Contacts>>(`api/accounts/${sapNumber}/contacts`);
    }

    public postBoardingList(boardingLoading: BoardingLoading): Observable<BoardingLoading> {
        return this._http.post<BoardingLoading>('api/boardingloading', boardingLoading);
    }

    public getSitesList(sapNumber: string): Observable<Array<SiteDetailsResponse>> {
        /* return this._http.get<Array<SiteDetailsResponse>>(`api/accounts/${sapNumber}/sites`); */
        return this._http.get<Array<SiteDetailsResponse>>(`api/accounts/${sapNumber}/sites`);
    }
    public getCaseList(sapNumber: String, page: number): Observable<Page<CaseResponse>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}` + '?page=' + page + '&size=5&');
    }
    public getContactsByAccountId(sapNumber: String, page: number): Observable<Page<Contacts>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<Contacts>>(`api/accounts/${sapNumber}/contacts/related` + '?page=' + page + '&size=5&');
    }
    public getSitesByAccount(sapNumber: String, page: number): Observable<Page<SiteDetailsResponse>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<SiteDetailsResponse>>(`api/accounts/${sapNumber}/sites/related` + '?page=' + page + '&size=5&');
    }
    public getContactByAccount(sapNumber: string): Observable<Array<ContactForList>> {
        return this._http.get<Array<ContactForList>>(`api/accounts/${sapNumber}/contacts/forlist`);
    }
    public getAssetsListByAsscountSapNumber(sapnumber: string): Observable<Array<AssetsForList>> {
        return this._http.get<Array<AssetsForList>>(`api/accounts/${sapnumber}/assets/forlist`);
    }
    public getAddressAccountId(sapNumber: string): Observable<AccountCreate> {
        return this._http.get<AccountCreate>(this.url + `/address/${sapNumber}`);
    }
    //EDIT BY RAUSHAN FOR BETTER FILTER

    public getCaseListS(sapNumber, filter): Observable<Page<CaseResponse>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}?${filter}`);
    }
    public getContactsByAccountIdS(sapNumber, filter): Observable<Page<Contacts>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<Contacts>>(`api/accounts/${sapNumber}/contacts/related?${filter}`);
    }
    public getSitesByAccountS(sapNumber, filter): Observable<Page<SiteDetailsResponse>> {
        //return this._http.get<Page<CaseResponse>>(`api/accounts/case/${sapNumber}`)
        return this._http.get<Page<SiteDetailsResponse>>(`api/accounts/${sapNumber}/sites/related${filter}`);
    }
    public getSearchResult(searchparam): Observable<Array<AccountCreate>> {
        return this._http.get<Array<AccountCreate>>(`${this.url}/advanceSearch`,{params:searchparam});
      }

      public getContactDetailsNotUser(sapnumber):Observable<Array<ContactForList>> {
        return this._http.get<Array<ContactForList>>(`api/accounts/${sapnumber}/contacts/notuser/forlist`)
    }
}