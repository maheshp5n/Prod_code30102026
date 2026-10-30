import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { QuotePdfHeaderAndFooterResponse, Page, QuotePdfHeaderAndFooterRequest, QuoteTemplateEnableAndDisableRequest, SimpleResponseMessage, BusinessDomain } from './model';

@Injectable({ providedIn: 'root' })

export class QuotePDFTemplateServices {
    constructor(private _http: HttpClient) { }
    baseUrl = 'api/quote/submitforapproval';
    public getAllTemplate(page: number, column: string, order: string): Observable<Page<QuotePdfHeaderAndFooterResponse>> {
        return this._http.get<Page<QuotePdfHeaderAndFooterResponse>>('api/quotetemplate' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getTemplateById(id: number): Observable<QuotePdfHeaderAndFooterResponse> {
        return this._http.get<QuotePdfHeaderAndFooterResponse>(`api/quotetemplate/${id}`);
    }
    public saveTemplate(request: QuotePdfHeaderAndFooterRequest): Observable<QuotePdfHeaderAndFooterResponse> {
        return this._http.post<QuotePdfHeaderAndFooterResponse>(`api/quotetemplate`, request);
    }
    public updateTemplate(request: QuotePdfHeaderAndFooterRequest): Observable<QuotePdfHeaderAndFooterResponse> {
        return this._http.put<QuotePdfHeaderAndFooterResponse>(`api/quotetemplate`, request);
    }
    public enableAndDisableTemplate(request: QuoteTemplateEnableAndDisableRequest): Observable<QuotePdfHeaderAndFooterResponse> {
        return this._http.put<QuotePdfHeaderAndFooterResponse>(`api/quotetemplate/enable`, request);
    }

    // shagufta
    public getBuisnessDomains(): Observable<Array<BusinessDomain>> {
        return this._http.get<Array<BusinessDomain>>('api/businessdomain');

    }
}
