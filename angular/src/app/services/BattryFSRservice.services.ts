
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { BatteryFSRRequest, BatteryFSRResponse, BatterySiteType, BatteryComplaintStatus, ProblemReported, Page } from './model';
import { Injectable } from '@angular/core';
@Injectable()
export class BatrryFSRService{
    baseUrl:string='api/batteryfsr';
    recentUrl:string='api/batteryfsr/recentvisit'
    siteTypeUrl:string='api/batteryfsr/sitetype';
    complainStatusURL='api/batteryfsr/complaintstatus';
    problemByCaseURL='api/batteryfsr/problemreported';
    fileUploadURL='/uploadMultipleFiles';
    singleFileUploadURL='api/uploadFile';
    downloadFileURL='api/downloadFile';
    responseType:ResponseType;
  constructor(private _http: HttpClient) { } 
  public saveBattryFSR(request:BatteryFSRRequest):Observable<BatteryFSRRequest>{
    return this._http.post<BatteryFSRRequest>(this.baseUrl,request);
   }
   public updateBattryFSR(req:BatteryFSRRequest):Observable<BatteryFSRRequest>{
    return this._http.put<BatteryFSRRequest>(this.baseUrl,req);
  }
   public getBatrryFSRAllList(filter?:string,pageInfo?:string): Observable<Page<BatteryFSRResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    } 
    return this._http.get<Page<BatteryFSRResponse>>(url);
  }
  public getRecentVisit(page:number,column:string,order:string):Observable<Page<BatteryFSRResponse>>{
       
    return this._http.get<Page<BatteryFSRResponse>>(this.recentUrl+ '?page=' + page + '&size=20&' +"sort="+column+","+order);

}
  public getBattrySiteType():Observable<Array<BatterySiteType>>{
    return this._http.get<Array<BatterySiteType>>(this.siteTypeUrl);
  }
  public getComplainStatus():Observable<Array<BatteryComplaintStatus>>{
    return this._http.get<Array<BatteryComplaintStatus>>(this.complainStatusURL);
  }
  public getProblemReportByCaseId(caseId:number):Observable<Array<ProblemReported>>{
      return this._http.get<Array<ProblemReported>>(this.problemByCaseURL+'/'+caseId);
  }
  public getDetailById(id:number):Observable<BatteryFSRResponse>{
    return this._http.get<BatteryFSRResponse>(`${this.baseUrl}/${id}`);
}
 public uploadFiles(id:any,formdata: FormData) : Observable<any>  
  { 
    return this._http.post('api/uploadMultipleFiles?objectName=BatteryFSR&objectId='+id,formdata) ;
  } 
  public filedownload(fileName:string):Observable<Blob>{
    return this._http.get(`${this.downloadFileURL}/${fileName}`,{ responseType: 'blob' });
  }
  public getFileList(id:number):Observable<Array<any>>{
    return this._http.get<Array<any>>('api/uploadedFiles?objectName=BatteryFSR&objectId='+id)
  }
  public getDownloadFile(fileName: any): Observable<any> {        
    return this._http.get(`api/downloadFile/${fileName}`, {responseType: 'blob'});
}

}