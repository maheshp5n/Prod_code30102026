import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
    Page,
    ProductFamily,
    ProductSubFamily,
    ProductsResponse,
    Product,
    PriceBookResponse,
    ProductsForList,
    PanIndiaProductSummary,
    ProductType
} from './model';
@Injectable({ providedIn: 'root' })
export class ProductsService {
    private baseUrl = 'api/products';
    private baseUrl3 = 'api/productfamily';
    private baseUrlRecent = 'api/products/recent';
    private baseUrlpricebook = 'api/pricebookentry/pricebookByPartCode?partCode=';

    constructor(private _http: HttpClient) { }

    public getProducts(filter?:string,pageInfo?:string): Observable<Page<ProductsResponse>> {
        let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
        return this._http.get<Page<ProductsResponse>>(url);
    }

    public getRecentProducts(filter?:string,pageInfo?:string): Observable<Page<ProductsResponse>> {
        let url = `${this.baseUrlRecent}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this._http.get<Page<ProductsResponse>>(url);
    }

    public getProductDetails(id: string): Observable<ProductsResponse> {
        return this._http.get<ProductsResponse>(`${this.baseUrl}/${id}`);
    }
    public getSubproduct(id: number): Observable<ProductSubFamily> {
        return this._http.get<ProductSubFamily>(`${this.baseUrl}/${id}`);
    }

    public postProducts(products: Product): Observable<Product> {
        return this._http.post<Product>(this.baseUrl, products);
    }

    public putProducts(id: string, products: Product): Observable<Product> {
        return this._http.put<Product>(this.baseUrl + '/' + id, products);
    }

    public getProductFamilyService(): Observable<Array<ProductFamily>> {
        return this._http.get<Array<ProductFamily>>(this.baseUrl3);
    }

    public getProductSubFamily(id: number): Observable<Array<ProductSubFamily>> {
        return this._http.get<Array<ProductSubFamily>>(this.baseUrl3 + '/' + id + '/subproduct');
    }
    public getProductForList(): Observable<Array<ProductsForList>> {
        return this._http.get<Array<ProductsForList>>('api/products/forlist');
    }
    public getPriceBookByProductPartCode(id: string, page: number): Observable<Page<PriceBookResponse>> {
        return this._http.get<Page<PriceBookResponse>>(this.baseUrlpricebook + id +
            '&page=' + page + '&size=5');
    }
    public getPanIndiaProductList(filter?):Observable<Page<PanIndiaProductSummary>>{
        let url=`${this.baseUrl}/panindiastock?`;
        if(filter){
            url=`${url}${filter}`;
        }
        return this._http.get<Page<PanIndiaProductSummary>>(url);
    }
    public getSmallForlist(searchkey):Observable<Array<ProductsForList>>{
        return this._http.get<Array<ProductsForList>>(`${this.baseUrl}/forlist/small?searchkey=${searchkey}`);
    }
    public getSearchResult(searchparam): Observable<Array<ProductsResponse>> {
        return this._http.get<Array<ProductsResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
      }
      public getAdvSearchProductFamily():Observable<Array<string>>{
          return this._http.get<Array<string>>(this.baseUrl+'/family/forlist')
      }
      public getAdvSearchSubProductFamily():Observable<Array<string>>{
        return this._http.get<Array<string>>(this.baseUrl+'/subfamily/forlist')
    }
    public getTypeForAdv():Observable<Array<ProductType>>{
        return this._http.get<Array<ProductType>>(this.baseUrl+'/type')
    }
}

