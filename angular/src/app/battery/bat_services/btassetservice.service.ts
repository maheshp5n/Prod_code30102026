import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AssetsForList, ProductsForList } from '../../services/model';
import { AssetForList, BrcListByAssetId, BTasseReq, BtAssetForLIst, BTAssetForLIst, BTassetResp, BtAssetsStatus, BTCaseResponse, BtPreRegCaseResponse, Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BTassetserviceService {
  baseUrl: string = 'bt/api/assets'

  constructor(private _http: HttpClient) { }
  public getAllAssets(filter?: string, pageInfo?: string): Observable<Page<BTassetResp>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
      url = `${url}&${filter}`;
    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BTassetResp>>(url);
  }
  //  advance search
  public getSearchResult(searchparam): Observable<Array<BTassetResp>> {
    return this._http.get<Array<BTassetResp>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }

  public getAssetsBySerialNumber(sapNumber: any): Observable<BTassetResp> {
    return this._http.get<BTassetResp>(this.baseUrl + '/' + sapNumber);
    // (`api/assets/astdetails?assetsserialnumber=${encodeURIComponent(sapNumber)}`);
  }

  public postAssets(assetsDetails: BTasseReq): Observable<BTassetResp> {
    return this._http.post<BTassetResp>(this.baseUrl, assetsDetails);
  }

  public editAssets(assetsRequest: BTasseReq, assetid: number): Observable<BTassetResp> {

    return this._http.put<BTassetResp>(`bt/api/assets/` + assetid, assetsRequest);
  }
  public getSmallForList(searchkey): Observable<Array<AssetForList>> {
    return this._http.get<Array<AssetForList>>(`${this.baseUrl}/forlist?search=${searchkey}`);
  }
  // public getSmallForListPage(searchkey): Observable<Page<AssetForList>> {
  //   return this._http.get<Page<AssetForList>>(`${this.baseUrl}/forlist?search=${searchkey}&size=${30}&page=${0}`);
  // }
  public getAssetStatus(): Observable<Array<BtAssetsStatus>> {
    return this._http.get<Array<BtAssetsStatus>>(this.baseUrl + '/status/forlist');
  }
  public getSiteForList(): Observable<Array<any>> {
    return this._http.get<Array<any>>('bt/api/sitedetails/forlist');
  }
  public getSiteSmallForlist(searchkey): Observable<Array<any>> {
    return this._http.get<Array<any>>(`bt/api/sitedetails/forlist?search=${searchkey}`);
  }

  public getProductForList(type?: string): Observable<Array<ProductsForList>> {
    if (type) {
      return this._http.get<Array<ProductsForList>>(`bt/api/products/forlist?type=${type}&size=${30}&page=${0}`);
    }
    return this._http.get<Array<ProductsForList>>('bt/api/products/forlist')
  }
  public getAssetsForListForInstallation(): Observable<Array<BTAssetForLIst>> {
    return this._http.get<Array<BTAssetForLIst>>(`bt/api/assets/forlist/forinstallation`);
  }
  public getListForInstallationSmall(searchkey): Observable<Page<BTAssetForLIst>> {
    return this._http.get<Page<BTAssetForLIst>>(`bt/api/assets/forlist/forinstallation/small?size=20&page=0&searchkey=${searchkey}`);
  }

  // case list by asset serial no
  public getCaseList(serialNumber: string, page: number): Observable<Page<BTCaseResponse>> {
    return this._http.get<Page<BTCaseResponse>>(this.baseUrl + '/case/' + serialNumber + '?page=' + page + '&size=5');
  }
  public getAllCaseList(serialNumber: string): Observable<Page<BTCaseResponse>> {
    return this._http.get<Page<BTCaseResponse>>(this.baseUrl + '/case/' + serialNumber);
  }
  // bulk upload
  public uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    return this._http.put(this.baseUrl + '/' + 'amcUpdate', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  parseErrorblob(error: HttpErrorResponse): Observable<any> {
    const reader: FileReader = new FileReader();
    const obs = Observable.create((observer: any) => {
      reader.onloadend = (e) => {
        observer.error(JSON.parse(reader.result.toString()));
        observer.complete();
      }
    });
    reader.readAsText(error.error);
    return obs;
  }
  public getExcelTemplate(): Observable<Blob> {
    return this._http.get(this.baseUrl + '/templateAMCUpdate', { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  // prc list of asset id
  public getPrcListbyAsset(serialNumber: string, page: number, size): Observable<Page<BtPreRegCaseResponse>> {
    return this._http.get<Page<BtPreRegCaseResponse>>(this.baseUrl + '/prc/' + serialNumber + '?page=' + page + '&size=' + size);
  }

  public getAllPrcListbyAsset(serialNumber: string): Observable<Page<BtPreRegCaseResponse>> {
    return this._http.get<Page<BtPreRegCaseResponse>>(this.baseUrl + '/prc/' + serialNumber);
  }
  // get Asset for list (method add by pawan)
  public btAssetForLIst(search): Observable<Array<BtAssetForLIst>> {
    return this._http.get<Array<BtAssetForLIst>>(`${this.baseUrl}/forlist?search=${search}`);
  }
  // brc list by asset serial no
  public btBrcList(serialNumber: string, page: number): Observable<Page<BrcListByAssetId>> {
    return this._http.get<Page<BrcListByAssetId>>(this.baseUrl + '/brccase/' + serialNumber + '?page=' + page + '&size=5');
  }

}
