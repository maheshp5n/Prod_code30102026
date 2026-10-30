import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {
  PriceBookRequest,
  Page,
  PriceBookResponse,
  PriceBookEntriesResponse,
  PriceBookEntriesRequest,
  PriceBookEntriesRequestInProducts,
  PriceBookForList
} from './model';

@Injectable({ providedIn: 'root' })
export class PriceBookService {
  baseUrl = 'api/pricebook';
  entryBaseUrl = 'api/pricebookentry';
  entrybyPriceookIdURL = 'api/pricebookentry/pricebook';

  constructor(private http: HttpClient) { }

  public savePriceBook(req: PriceBookRequest): Observable<PriceBookRequest> {
    return this.http.post<PriceBookRequest>(this.baseUrl, req);
  }
  public getpriceBook(filter?:string,pageInfo?:string): Observable<Page<PriceBookResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<PriceBookResponse>>(url);

  }
  public getAllPriceBookList(): Observable<Array<PriceBookForList>> {
    return this.http.get<Array<PriceBookForList>>(this.baseUrl + '/forlist');
  }

  public getpriceBookEntries(id: number, page: number, column: string, order: string): Observable<Page<PriceBookEntriesResponse>> {
    return this.http.get<Page<PriceBookEntriesResponse>>('api/pricebookentry/pricebook/' + id +
      '?page=' + page + '&size=20&' + 'sort=' + column + ',' + order);
  }

  public getpriceBookEntriesList(id: number): Observable<Array<PriceBookEntriesResponse>> {
    return this.http.get<Array<PriceBookEntriesResponse>>(`api/pricebookentry/pricebook/list/${id}`);
      
  }

  public updatePicebook(siteReq: PriceBookRequest): Observable<PriceBookRequest> {
    return this.http.put<PriceBookRequest>(this.baseUrl, siteReq);
  }
  public getDetailById(id: number): Observable<PriceBookResponse> {
    return this.http.get<PriceBookResponse>(`${this.baseUrl}/${id}`);
  }
  public getAllEntries(page: number, priceBookId: number,size?:number): Observable<Page<PriceBookEntriesResponse>> {
    return this.http.get<Page<PriceBookEntriesResponse>>(this.entryBaseUrl + '/' + 'pricebook' + '/' + priceBookId + '?page=' + page + '&size='+size);
  }
  public saveProductsById(id: number, request: Array<PriceBookEntriesRequest>): Observable<Array<PriceBookEntriesRequest>> {
    return this.http.post<Array<PriceBookEntriesRequest>>(this.entryBaseUrl + '/' + id, request);
  }
  public getBookEntriesById(page: number, id: number): Observable<Page<PriceBookEntriesResponse>> {
    return this.http.get<Page<PriceBookEntriesResponse>>(this.entrybyPriceookIdURL + '/' + id + '?page=' + page + '&size=6&');
  }
  public updatePicebookentries(entries: PriceBookEntriesRequest): Observable<PriceBookEntriesRequest> {
    return this.http.put<PriceBookEntriesRequest>(this.entryBaseUrl, entries);
  }
  public getDetailByentriesId(entriesId: number): Observable<PriceBookEntriesResponse> {
    return this.http.get<PriceBookEntriesResponse>(`${this.entryBaseUrl}/${entriesId}`);
  }
  public savePriceBookEntry(id: number, req: PriceBookEntriesRequestInProducts): Observable<Array<PriceBookEntriesResponse>> {
    return this.http.post<Array<PriceBookEntriesResponse>>(this.entryBaseUrl + '/entry/' + id, req);
  }
}
