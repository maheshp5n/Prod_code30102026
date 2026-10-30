import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
    Page,
    ProductLineItemsResponse,
    ProductLineItemsRequest,
    ProductLineItemProductState,
    ProductLineItemStatus
} from './model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductLineItemsService {
  private baseUrl = 'api/productlineitem';
    private baseUrlRecent = 'api/productlineitem/recent';
    private baseUrl2 = 'api/productlineitem/productstate';
    private baseUrl3 = 'api/productlineitem/status';

    constructor(private _http: HttpClient) { }

    public getProductLineItems(page: number, column: string, order: string): Observable<Page<ProductLineItemsResponse>> {
        return this._http.get<Page<ProductLineItemsResponse>>(this.baseUrl + '?page=' + page + '&size=20' + "&" + "sort=" + column + "," + order);
    }


    public getproductLineItemsById(serialNumber): Observable<ProductLineItemsResponse> {
        return this._http.get<ProductLineItemsResponse>(this.baseUrl + "/" + serialNumber);
    }

    public postProductLineItems(productlineitems: ProductLineItemsRequest): Observable<ProductLineItemsRequest> {
        return this._http.post<ProductLineItemsRequest>(this.baseUrl, productlineitems);
    }

    public getRecentProductLineItems(page: number, column: string, order: string): Observable<Page<ProductLineItemsResponse>> {
        return this._http.get<Page<ProductLineItemsResponse>>(this.baseUrlRecent + '?page=' + page + '&size=20' + "&" + "sort=" + column + "," + order);
    }

    public putProductLineItems(id: string, productlineitemsRequest: ProductLineItemsRequest): Observable<ProductLineItemsRequest> {
        return this._http.put<ProductLineItemsRequest>(this.baseUrl + "/" + id, productlineitemsRequest);
    }
 
 public getProductLineItemStateForList(): Observable<Array<ProductLineItemProductState>> {
        return this._http.get<Array<ProductLineItemProductState>>(this.baseUrl2);
    }

 public getProductLineItemStatusForList(): Observable<Array<ProductLineItemStatus>> {
        return this._http.get<Array<ProductLineItemStatus>>(this.baseUrl3);
    }

}
