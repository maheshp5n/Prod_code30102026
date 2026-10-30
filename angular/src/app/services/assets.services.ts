import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Page,AssetsResponse, AssetsRequest, SiteDetailsForList, CaseResponse, AssetsForList, ProductsForList, AssetsStatus } from './model'
import { URLSearchParams } from "@angular/http";
import { catchError } from 'rxjs/operators';

@Injectable()
export class AssetsService {
    baseUrl:string='api/assets'

    constructor(private _http:HttpClient){}
    
    /* public getAllAssets(page?:number):Observable<Page<AssetsResponse>>{
        if(page){
            return this._http.get<Page<AssetsResponse>>(`api/assets/recent?page=${page}`);
        }
        return this._http.get<Page<AssetsResponse>>('api/assets');
    } */
    public getAllAssets(filter?: string, pageInfo?: string):Observable<Page<AssetsResponse>>{
        let url = `${this.baseUrl}?`;
        if (filter) {
            url = `${url}&${filter}`;
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this._http.get<Page<AssetsResponse>>(url);
    }

    /* public getAssetRecent(page?:number):Observable<Page<AssetsResponse>>{
        if(page){
            return this._http.get<Page<AssetsResponse>>(`api/assets/recent?page=${page}`);
        }
        return this._http.get<Page<AssetsResponse>>('api/assets/recent');
    } */

    public getAssetRecent(page:number,column:string,order:string):Observable<Page<AssetsResponse>>{        
        return this._http.get<Page<AssetsResponse>>('api/assets/recent' + '?page=' + page + '&size=20&'+"sort="+column+","+order);
    }
    public getAssetStatus():Observable<Array<AssetsStatus>>{        
        return this._http.get<Array<AssetsStatus>>('api/assets/status');
    }
    
    public getAssetsBySerialNumber(sapNumber:string):Observable<AssetsResponse>{
        return this._http.get<AssetsResponse>(`api/assets/astdetails?assetsserialnumber=${encodeURIComponent(sapNumber)}`);
    }

    public postAssets(assetsDetails:AssetsRequest):Observable<AssetsRequest>{
        return this._http.post<AssetsRequest>('api/assets',assetsDetails);
    }
    
    public editAssets(assetsRequest:AssetsRequest,sapNumber:string):Observable<AssetsRequest>{
      
        return this._http.put<AssetsRequest>(`api/assets?assetsserialnumber=${encodeURIComponent(sapNumber)}`,assetsRequest);
    }

    public getSitesForList():Observable<Array<SiteDetailsForList>>{        
        return this._http.get<Array<SiteDetailsForList>>('api/sites/forlist');
    } 

    public getProductForList(type?:string):Observable<Array<ProductsForList>>{
        if(type){
            return this._http.get<Array<ProductsForList>>(`api/products/forlist?type=${type}`);
        }
        return this._http.get<Array<ProductsForList>>('api/products/forlist')
    }
      
    public getCaseList(serialNumber: string, column:string,page: number,order): Observable<Page<CaseResponse>>{
        return this._http.get<Page<CaseResponse>>('api/assets/case?assetsserialnumber=' + encodeURIComponent(serialNumber) + '&page=' + page + '&size=5&'+'sort='+column +','+order);
    }
    
    public getAssetsForList():Observable<Array<AssetsForList>>{
        return this._http.get<Array<AssetsForList>>(`api/assets/forlist`);
    }
    public getAssetsForListForInstallation():Observable<Array<AssetsForList>>{
        return this._http.get<Array<AssetsForList>>(`api/assets/forlist/forinstallation`);
    }
    public getSmallForList(searchkey):Observable<Page<AssetsForList>>{
        return this._http.get<Page<AssetsForList>>(`${this.baseUrl}/forlist/small?size=20&page=0&search=${searchkey}`);
    }
    public getListForInstallationSmall(searchkey):Observable<Page<AssetsForList>>{
        return this._http.get<Page<AssetsForList>>(`${this.baseUrl}/forlist/forinstallation/small?size=20&page=0&searchkey=${searchkey}`);
    }

    public getAdvanceSearchResult(searchContent):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:searchContent});
    }
    
    public getProductForListSmall(searchkey):Observable<Array<ProductsForList>>{
        return this._http.get<Array<ProductsForList>>(`api/products/forlist/small?searchkey=${searchkey}`)
    }
    // bulk upload
    public uploadFiles(file: File): Observable<any> {
        const fd: FormData = new FormData();
        fd.append('file', file, file.name);
        return this._http.put(this.baseUrl+'/'+'amcUpdate', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
        return this._http.get(this.baseUrl+'/templateAMCUpdate', { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
      }
}