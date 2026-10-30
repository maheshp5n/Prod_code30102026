import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductType } from '../../services/model';
import { Product, ProductSubFamily, ProductFamily, ProductDetailResponse, Page, UpdateProductStatus, BT_ProductForListDetail, BTProductForListResponse, BtProductList } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtproductService {

  private baseUrl1: string = 'bt/api/products';
  private baseUrl3 = 'api/productfamily';

  constructor(private _http: HttpClient) { }


  public createProductReq(req: Product): Observable<any> {
    return this._http.post<any>(this.baseUrl1, req);
  }
  public updateProductReq(req: Product): Observable<any> {
    return this._http.put<any>(this.baseUrl1, req);
  }
  public updateProductStatus(req: UpdateProductStatus): Observable<any> {
    return this._http.put<any>(`${this.baseUrl1}/status`, req);
  }
  public getProductFamilyService(): Observable<Array<ProductFamily>> {
    return this._http.get<Array<ProductFamily>>(this.baseUrl3);
  }
  public getProductSubFamily(id: number): Observable<Array<ProductSubFamily>> {
    return this._http.get<Array<ProductSubFamily>>(this.baseUrl3 + '/' + id + '/subproduct');
  }
  // public getBtProductByFilter(filterParam : string,page:number): Observable<Page<ProductDetailResponse>> {
  //   return this._http.get<Page<ProductDetailResponse>>(this.baseUrl1 + `?filterParam=${filterParam}&page=${page}&size=20`);
  // }
  public getBtProductByFilter(filter?: string, pageInfo?: string): Observable<Page<ProductDetailResponse>> {
    let url = `${this.baseUrl1}?`;
    if (filter) {

      url = `${url}&${filter}`;

    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ProductDetailResponse>>(url);
  }
  public getDataByProductId(id: number): Observable<ProductDetailResponse> {
    return this._http.get<ProductDetailResponse>(`${this.baseUrl1}/${id}`);
  }
  public getSearchResult(searchparam): Observable<Array<ProductDetailResponse>> {
    return this._http.get<Array<ProductDetailResponse>>(`${this.baseUrl1}/advancesearch`, { params: searchparam });
  }
  public getSmallForlist(searchkey): Observable<Array<BT_ProductForListDetail>> {
    return this._http.get<Array<BT_ProductForListDetail>>(`${this.baseUrl1}/forlist?searchkey=${searchkey}`);
  }
  public getTypeForAdv(): Observable<Array<ProductType>> {
    return this._http.get<Array<ProductType>>(this.baseUrl1 + '/type')
  }
  public getAllProducts(searchkey: string): Observable<Array<BTProductForListResponse>> {
    return this._http.get<Array<BTProductForListResponse>>(`${this.baseUrl1}/forlist?searchkey=${searchkey}`);
  }

  public fetchProductList(searchKey): Observable<Array<BtProductList>> {
    return this._http.get<Array<BtProductList>>(`${this.baseUrl1}/forlist?searchkey=${searchKey}`);
  }
}
