import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  OppertunityStage,
  OppertunityType,
  OppertunityForecastCategory,
  OppertunityRequest,
  Page, QuoteResponse,
  OppertunityResponse,
  OppertunityLineItemRequest,
  OppertunityLineItemResponse
} from './model';

@Injectable({ providedIn: 'root' })
export class OpportunityService {
  private baseUrl = 'api/oppertunity';
  private baseUrl1 = 'api/oppertunity/stage';
  private baseUrl2 = 'api/oppertunity/type';
  private baseUrl3 = 'api/oppertunity/forecastcategory';
  private baseUrlRecent = 'api/oppertunity/recentvisit';
  private baseUrlList = 'api/oppertunity/list';
  private urlQuoteByOppoId = 'api/oppertunity/related/quote/';
  private baseUrlQuote = 'api/quote/';

  constructor(private _http: HttpClient) { }

  public getOppertunityStage(): Observable<Array<OppertunityStage>> {
    return this._http.get<Array<OppertunityStage>>(this.baseUrl1);
  }

  public getOppertunityType(): Observable<Array<OppertunityType>> {
    return this._http.get<Array<OppertunityType>>(this.baseUrl2);
  }

  public getOppertunityForecastCategory(): Observable<
    Array<OppertunityForecastCategory>
  > {
    return this._http.get<Array<OppertunityForecastCategory>>(this.baseUrl3);
  }

  //   public getOpportunity(page: number, column: string, order: string): Observable<Page<OpportunityResponse>> {
  // tslint:disable-next-line: max-line-length
  //       return this._http.get<Page<OpportunityResponse>>(this.baseUrl + '?page=' + page + '&size=20' + "&" + "sort=" + column + "," + order);
  //   }


  public getRecentOpportunities(filter?:string,pageInfo?:string): Observable<Page<OppertunityResponse>> {
    // tslint:disable-next-line: max-line-length
    let url = `${this.baseUrlRecent}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
    return this._http.get<Page<OppertunityResponse>>(url);
  }


  public getRecords(filter?:string,pageInfo?:string): Observable<Page<OppertunityResponse>> {
    // tslint:disable-next-line: max-line-length
    let url = `${this.baseUrlList}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
    return this._http.get<Page<OppertunityResponse>>(url);
  }

  public getRecordsForForecast(filter?:string,pageInfo?:string): Observable<Page<OppertunityResponse>> {
    // tslint:disable-next-line: max-line-length
    let url = `${this.baseUrlList}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<OppertunityResponse>>(url);
  }

  public getOpportunityDetails(id: number): Observable<OppertunityResponse> {
    return this._http.get<OppertunityResponse>(this.baseUrl + '/' + id);
  }

  public postOpportunity(oppertunityRequest: OppertunityRequest): Observable<OppertunityResponse> {
    return this._http.post<OppertunityResponse>(this.baseUrl, oppertunityRequest);
  }

  public putOpportunity(oppertunityRequest: OppertunityRequest): Observable<OppertunityResponse> {
    return this._http.put<OppertunityResponse>(this.baseUrl, oppertunityRequest);
  }

  public postOpportunityLineItem(oppertunityLineItemRequest: Array<OppertunityLineItemRequest>, priceBookId: number,
    oppertunityId: number, businessDomainId: number): Observable<Array<OppertunityLineItemResponse>> {
    return this._http.post<Array<OppertunityLineItemResponse>>(this.baseUrl + '/lineitem?priceBookId='
      + priceBookId + '&oppertunityId=' + oppertunityId + '&businessDomainId=' + businessDomainId,
      oppertunityLineItemRequest);
  }

  public putOpportunityLineItemsEditAll(oppertunityLineItemRequest: Array<OppertunityLineItemRequest>, priceBookId: number,
    oppertunityId: number, businessDomainId: number): Observable<Array<OppertunityLineItemResponse>> {
    return this._http.put<Array<OppertunityLineItemResponse>>(this.baseUrl + '/lineitem/updateall?priceBookId='
      + priceBookId + '&oppertunityId=' + oppertunityId + '&businessDomainId=' + businessDomainId,
      oppertunityLineItemRequest);
  }


  public getOppoLineItems(id: number, page: number): Observable<Page<OppertunityLineItemResponse>> {
    return this._http.get<Page<OppertunityLineItemResponse>>(
      this.baseUrl + '/lineitem?oppertunityId=' + id + '&page=' + page + '&size=5');
  }

  public getOppoLineItemsList(id: number): Observable<Array<OppertunityLineItemResponse>> {
    return this._http.get<Array<OppertunityLineItemResponse>>(
      this.baseUrl + '/lineitemList?oppertunityId=' + id);
  }

  public getOppoLineItem(id: number): Observable<OppertunityLineItemResponse> {
    return this._http.get<OppertunityLineItemResponse>(this.baseUrl + '/lineitem/' + id);
  }

  public putOpportunityLineItemEdit(oppertunityLineItemRequest: OppertunityLineItemRequest, priceBookId: number,
    oppertunityId: number, businessDomainId: number): Observable<OppertunityLineItemResponse> {
    return this._http.put<OppertunityLineItemResponse>(this.baseUrl + '/lineitem?priceBookId='
      + priceBookId + '&oppertunityId=' + oppertunityId + '&businessDomainId=' + businessDomainId,
      oppertunityLineItemRequest);
  }

  public deleteOpportunity(id: number): Observable<OppertunityResponse> {
    return this._http.delete<OppertunityResponse>(this.baseUrl + '/' + id);
  }

  public deleteOpportunityLineItem(id: number): Observable<OppertunityResponse> {
    return this._http.delete<OppertunityResponse>(this.baseUrl + '/lineitem/' + id);
  }

  // , column: string, order: string
  public getQuoteByOppoId(oppoId: number, page: number): Observable<Page<QuoteResponse>> {
    return this._http.get<Page<QuoteResponse>>(this.urlQuoteByOppoId + oppoId +
      '?page=' + page + '&size=5');
    // + '&' + 'sort=' + column + ',' + order
  }

  public deleteQuote(id: number): Observable<QuoteResponse> {
    return this._http.delete<QuoteResponse>(this.baseUrlQuote + id);
  }
  public getSearchResult(searchparam): Observable<Array<OppertunityResponse>> {
    return this._http.get<Array<OppertunityResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }
  // public getStageForList(): Observable<Array<OppertunityStage>> {
  //   return this._http.get<Array<OppertunityStage>>(`${this.baseUrl}/stage`);
  // }
}


