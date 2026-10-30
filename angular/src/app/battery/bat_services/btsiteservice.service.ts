import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { BTassetResp, BTCaseResponse, BTsiteCreateReq, GetAllBTsiteResp, Page } from '../bt_model';
import { AccountsForList, AssetsResponse, CaseResponse, CircleZoneForList, ServiceAppointmentResponse, ServiceCenterForList, ServiceResourseForList, SiteDetailsForList, SiteDetailsReq, Zonedetails } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtsiteserviceService {

  baseUrl='bt/api/sitedetails';
  siteCaseBaseUrl='bt/api/assets';
  RelatedUrl= this.baseUrl+'/releted';


  constructor(private http:HttpClient) { }

  // public saveSite(req:SiteDetailsReq):Observable<SiteDetailsReq>{
      
  //   return this.http.post<SiteDetailsReq>(this.baseUrl,req);

// }

  // public getAllBtSiteList(page:number, size:number):Observable<Page<GetAllBTsiteResp>>{
  //   return this.http.get<Page<GetAllBTsiteResp>>(this.baseUrl+'?page='+page+'&size='+size)
  // }

  public getAllBtSiteList(filter?:string,pageInfo?:string):Observable<Page<GetAllBTsiteResp>>{
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
        return this.http.get<Page<GetAllBTsiteResp>>(url);

}

  public getCreateBtSite(req:BTsiteCreateReq):Observable<BTsiteCreateReq>{
    return this.http.post<BTsiteCreateReq>(this.baseUrl,req)
  }

  public updateBtSite(btSiteReq:BTsiteCreateReq):Observable<BTsiteCreateReq>{
    return this.http.put<BTsiteCreateReq>(`${this.baseUrl}`,btSiteReq)
  }

  public getBtSitedataById(siteID:number):Observable<GetAllBTsiteResp>{
    return this.http.get<GetAllBTsiteResp>(`${this.baseUrl}/${siteID}`);
  }


  public getBtSiteAdvanceSearch(searchkeys):Observable<Array<any>>{
    return this.http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:searchkeys});
  }

  public uploadBulkDataBt(fd:FormData):Observable<any>{
    return this.http.post(`${this.baseUrl}/siteBulkupload`,fd);
  }

  public getAccountsForList():Observable<Array<AccountsForList>>{
    return this.http.get<Array<AccountsForList>>('bt/api/accounts/forlist');
  }
  public getCircleList():Observable<Array<CircleZoneForList>>{
    return this.http.get<Array<CircleZoneForList>>('bt/api/circle/circle/forlist');
    
  }
  public getZoneforList():Observable<Array<Zonedetails>>{
     return this.http.get<Array<Zonedetails>>('api/zone/forlist')
//   return this.http.get<Array<Zonedetails>>('api/zone')
  }
  public getServiceResourcesList(legalEntityId: number):Observable<Array<ServiceResourseForList>>{
  return this.http.get<Array<ServiceResourseForList>>('bt/api/serviceresource/forlist/' + legalEntityId);
  }
  public getserviceCenterForList():Observable<Array<ServiceCenterForList>>{
    return this.http.get<Array<ServiceCenterForList>>('bt/api/legalentity/forlist')

  }



public saveSiteByCase(req:BTsiteCreateReq,assetSerialNo:string):Observable<BTsiteCreateReq>{
  
    return this.http.post<BTsiteCreateReq>(`${this.siteCaseBaseUrl}/${assetSerialNo}/sites`,req);

}

public getCaseBySiteId(siteid:number,page:number,column:string,order:string,size?):Observable<Page<BTCaseResponse>>{
    return this.http.get<Page<BTCaseResponse>>(this.RelatedUrl +'/'+ siteid + '/case' +'?page=' + page + '&size='+size+'&sort='+column +','+order);//&'+"sort="+column+","+order);
}

public getServiceAppointmentBySiteId(siteid:number,page:number,column:string,order:string):Observable<Page<ServiceAppointmentResponse>>{
    return this.http.get<Page<ServiceAppointmentResponse>>(this.RelatedUrl+'/'+siteid+'/serviceappointment' + '?page=' + page + '&size=20');//&'+"sort="+column+","+order);
}
public getAssetBySiteId(siteid:number,filter?:string,pageInfo?:string):Observable<Page<BTassetResp>>{
    let url = `${this.RelatedUrl+'/'+siteid+'/assets'}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<BTassetResp>>(url);
    // (this.RelatedUrl+'/'+siteid+'/assets' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
}
public siteForlist():Observable<Array<SiteDetailsForList>>{
    return this.http.get<Array<SiteDetailsForList>>(this.baseUrl+'/forlist');
}

public getAdvanceSearchResult(searchkeys):Observable<Array<any>>{
    return this.http.get<Array<any>>(`${this.baseUrl}/advancesearch`,{params:searchkeys});
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
//  site searching for list --- Shagufta
public getSmallForlist(searchkey):Observable<Array<any>>{
  return this.http.get<Array<any>>(`${this.baseUrl}/forlist?search=${searchkey}`);
}

}
