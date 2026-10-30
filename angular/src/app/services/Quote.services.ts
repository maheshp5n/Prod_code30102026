import { Observable } from 'rxjs';
import { QuoteRequest, QuoteResponse, QuoteStatus, Page, QuoteLineItemResponse, QuoteLineItemRequest, BusinessDomain, ExpenseManagementResponse, MailResponse } from './model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UrlSegment } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class QuoteService {
  private baseUrl = 'api/quote';
  private baseURlRec='api/quote/recentvisit';
  private byStatusUrl = 'api/quote/bystatus';
approveurl='api/quote/submitforapproval';
  constructor(private _http: HttpClient) { }

  public getQuote(filter?:string,pageInfo?:string): Observable<Page<QuoteResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<QuoteResponse>>(url);
  }

  public getRecentQuote(filter?:string,pageInfo?:string): Observable<Page<QuoteResponse>> {
    let url = `${this.baseURlRec}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<QuoteResponse>>(url);
  }

  public getQuotesByStatus(filter?:string,pageInfo?:string): Observable<Page<QuoteResponse>> {
    let url = `${this.byStatusUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<QuoteResponse>>(url);
  }

  public getQuoteDetails(id: number): Observable<QuoteResponse> {
    return this._http.get<QuoteResponse>(`${this.baseUrl}/${id}`);
  }

  public postQuote(quote: QuoteRequest): Observable<QuoteResponse> {
    return this._http.post<QuoteResponse>(this.baseUrl, quote);
  }

  public putQuote(quotes: QuoteRequest): Observable<QuoteResponse> {
    return this._http.put<QuoteResponse>(this.baseUrl, quotes);
  }

  public getQuoteStatus(): Observable<Array<QuoteStatus>> {
    return this._http.get<Array<QuoteStatus>>(this.baseUrl + '/status');
  }

  public postQuoteLineItem(quoteLineItemRequest: Array<QuoteLineItemRequest>, priceBookId: number,
    quoteId: number, businessDomainId: number): Observable<Array<QuoteLineItemResponse>> {
    return this._http.post<Array<QuoteLineItemResponse>>(this.baseUrl + '/lineitem?priceBookId='
      + priceBookId + '&quoteId=' + quoteId + '&businessDomainId=' + businessDomainId,
      quoteLineItemRequest);
  }

  public putQuoteLineItemsEditAll(priceBookId: number,
    quoteId: number, businessDomainId: number, quoteLineItemRequest: Array<QuoteLineItemRequest>):
    Observable<Array<QuoteLineItemResponse>> {
    return this._http.put<Array<QuoteLineItemResponse>>(this.baseUrl + '/lineitem/updateall?priceBookId='
      + priceBookId + '&quoteId=' + quoteId + '&businessDomainId=' + businessDomainId,
      quoteLineItemRequest);
  }

  public getQuoteLineItemsList(id: number): Observable<Array<QuoteLineItemResponse>> {
    return this._http.get<Array<QuoteLineItemResponse>>(this.baseUrl + '/lineitemList?quoteId=' + id);
  }

  public getQuoteLineItem(id: number): Observable<QuoteLineItemResponse> {
    return this._http.get<QuoteLineItemResponse>(this.baseUrl + '/lineitem/' + id);
  }

  public getQuoteLineItems(id: number, page: number, column: string, order: string): Observable<Page<QuoteLineItemResponse>> {
    return this._http.get<Page<QuoteLineItemResponse>>(
      this.baseUrl + '/lineitem?quoteId=' + id + '&page=' + page + '&size=5' + '&' + 'sort=' + column + ',' + order);
  }

  public putQuoteLineItemEdit(quoteLineItemRequest: QuoteLineItemRequest, priceBookId: number,
    quoteId: number, businessDomainId: number): Observable<QuoteLineItemResponse> {
    return this._http.put<QuoteLineItemResponse>(this.baseUrl + '/lineitem?priceBookId='
      + priceBookId + '&quoteId=' + quoteId + '&businessDomainId=' + businessDomainId,
      quoteLineItemRequest);
  }

  public syncQuoteWithOppertunity(quoteId: number, businessDomainId: number): Observable<boolean> {
    return this._http.get<boolean>(this.baseUrl + '/syncquote?quoteId=' + quoteId + '&businessDomainId=' + businessDomainId);
  }

  public stopSyncQuoteWithOppertunity(quoteId: number, businessDomainId: number): Observable<boolean> {
    return this._http.get<boolean>(this.baseUrl + '/stopsyncquote?quoteId=' + quoteId + '&businessDomainId=' + businessDomainId);
  }

  // public deleteQuote(id: number): Observable<QuoteResponse> {
  //   return this._http.delete<QuoteResponse>(this.baseUrl + '/' + id);
  // }

  public deleteQuoteLineItem(id: number): Observable<QuoteResponse> {
    return this._http.delete<QuoteResponse>(this.baseUrl + '/lineitem/' + id);
  }
  public postsubmitforapproval(quoteId): Observable<QuoteResponse> {

    return this._http.post<QuoteResponse>(this.approveurl+'?quoteId=' + quoteId,'');
  } 
  public getMailList(quoteid: number): Observable<Array<MailResponse>> {
    return this._http.get<Array<MailResponse>>(this.baseUrl + '/mail'+'/'+ quoteid);
  }
  public getSearchResult(searchparam): Observable<Array<QuoteResponse>> {
    return this._http.get<Array<QuoteResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }
  public getQuoteStatusForList():Observable<Array<string>>{
    return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
  }
}
