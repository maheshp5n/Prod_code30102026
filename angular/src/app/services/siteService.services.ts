import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { SiteDetailsReq, AccountsForList, CirclesForList, Zonedetails, ServiceResourseForList, ServiceCenterForList, SiteDetailsResponse, Page, CaseResponse, ServiceAppointmentResponse, AssetsResponse, SiteDetailsForList, CircleZoneForList } from "./model";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { BT_SiteDetailForList } from "../battery/bt_model";

@Injectable({providedIn:'root'})
export class siteService{
    baseUrl='api/sites';
    baseUrl2='api/accounts';
    baseUrl3='api/sites/siteid';
    recentUrl='api/sites/recentvisit';
    RelatedUrl='api/sites/releted';
    forListUrl='api/sites/forlist'
    siteCaseBaseUrl='api/assets';

    constructor(private http:HttpClient){}

    public saveSite(req:SiteDetailsReq):Observable<SiteDetailsReq>{
      
        return this.http.post<SiteDetailsReq>(this.baseUrl,req);

    }
    public saveSiteByCase(req:SiteDetailsReq,assetSerialNo:string):Observable<SiteDetailsReq>{
      
        return this.http.post<SiteDetailsReq>(`${this.siteCaseBaseUrl}/${assetSerialNo}/sites`,req);

    }
    public getSites(filter?:string,pageInfo?:string):Observable<Page<SiteDetailsResponse>>{
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
            return this.http.get<Page<SiteDetailsResponse>>(url);
    
    }
    public getRecentVisit(filter?:string,pageInfo?:string):Observable<Page<SiteDetailsResponse>>{
        let url = `${this.recentUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
             return this.http.get<Page<SiteDetailsResponse>>(url);
        
     }
    public getAccountsForList():Observable<Array<AccountsForList>>{
        return this.http.get<Array<AccountsForList>>('api/accounts/forlist');
    }
    public getCircleList():Observable<Array<CircleZoneForList>>{
        return this.http.get<Array<CircleZoneForList>>('api/circle/circle/forlist');
        
    }
    public getZoneforList():Observable<Array<Zonedetails>>{
         return this.http.get<Array<Zonedetails>>('api/zone/forlist')
    //   return this.http.get<Array<Zonedetails>>('api/zone')
    }
    public getServiceResourcesList(legalEntityId: number):Observable<Array<ServiceResourseForList>>{
      return this.http.get<Array<ServiceResourseForList>>('api/serviceresource/forlist/' + legalEntityId);
    }
    public getserviceCenterForList():Observable<Array<ServiceCenterForList>>{
        return this.http.get<Array<ServiceCenterForList>>('api/legalentity/forlist')

    }
    public getDetailBysiteId(siteid:number):Observable<SiteDetailsResponse>{
        return this.http.get<SiteDetailsResponse>(`${this.baseUrl}/${siteid}`);
    }
    public getEditBysiteId(siteid:number):Observable<SiteDetailsReq>{
        return this.http.get<SiteDetailsReq>(`${this.baseUrl}/${siteid}`);
    }
    public updateSite(siteReq:SiteDetailsReq,siteid:number):Observable<SiteDetailsReq>{
      
        return this.http.put<SiteDetailsReq>(`${this.baseUrl}/${siteid}`,siteReq);

    }
    public getCaseBySiteId(siteid:number,page:number,column:string,size,order:string):Observable<Page<CaseResponse>>{
        return this.http.get<Page<CaseResponse>>(this.RelatedUrl +'/'+ siteid + '/case' +'?page=' + page + '&size='+size+'&sort='+column +','+order);//&'+"sort="+column+","+order);
    }
    
    public getServiceAppointmentBySiteId(siteid:number,page:number,column:string,order:string):Observable<Page<ServiceAppointmentResponse>>{
        return this.http.get<Page<ServiceAppointmentResponse>>(this.RelatedUrl+'/'+siteid+'/serviceappointment' + '?page=' + page + '&size=20');//&'+"sort="+column+","+order);
    }
    public getAssetBySiteId(siteid:number,filter?:string,pageInfo?:string):Observable<Page<AssetsResponse>>{
        let url = `${this.RelatedUrl+'/'+siteid+'/assets'}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this.http.get<Page<AssetsResponse>>(url);
        // (this.RelatedUrl+'/'+siteid+'/assets' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
    }
    public siteForlist():Observable<Array<SiteDetailsForList>>{
        return this.http.get<Array<SiteDetailsForList>>(this.forListUrl);
    }
  
    public getAdvanceSearchResult(searchkeys):Observable<Array<any>>{
        return this.http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:searchkeys});
    }
    
    public uploadBulkData(fd:FormData):Observable<any>{
        return this.http.post(`${this.baseUrl}/bulkCreate`,fd, {responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
    }
   

    parseErrorBlob(err: HttpErrorResponse): Observable<any> {
        const reader: FileReader = new FileReader();
        const obs = Observable.create((observer: any) => {
            reader.onloadend = (e) => {
                observer.error(JSON.parse(reader.result.toString()));
                observer.complete();
            };
        });
        reader.readAsText(err.error);
        return obs;
    }
  
}