import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { caseLogReject, CaseProofOfDeliveryRequest, CaseResponse, ChangecaseTypeRequest, Page, PendingResponsibility, ProblemReported, RejectCaseRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentResponse } from '../../services/model';
import { AssetTypeChoice, BTassetResp, BTCaseRequest, BTCaseResponse, BtProblemMaster, BTServiceAppointmentResponse, BT_CaseOriginDetails, BT_CasePriority, BT_CaseReasonForList, BT_CaseStageDetails, BT_CaseStatus, BT_CaseTypeForList, BT_CaseTypeRecord, BT_SiteDetailForList } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtCaseserviceService {
  baseUrl:string='bt/api/case';
   FileUploadUrl = 'bt/api/case/import';
  excelTemplateURL = 'bt/api/case/templateExcel';
  downloadFileURL = 'api/downloadFile';
  // for commercial added by pawan
  caseStatusUrl = 'bt/api/case/bystatus'; 
  docrecievedbulURL='bt/api/case/documentreceived/template'; 
  changecaseTypeURL = 'bt/api/case/templateChangeCaseType';
  caseStageUrl = 'bt/api/case/bystage';
  changecasetypeListURL='changeCaseTypeSingle/caseType'; 
  casereasonforchangecasetypeListURL='changeCaseTypeSingle/caseReason';
  caserejectBulkUplUrl='bt/api/case/caseRejectBulkUpload';
  podExcelTempURL='bt/api/case/podTemplate';
  constructor(private http: HttpClient) { }
  public pendingrisponsibility(): Observable<Array<PendingResponsibility>> {
    return this.http.get<Array<PendingResponsibility>>(this.baseUrl+'/pendingresponsibility/forlist')
  }
  
  public getCaseAssets(assetsserialnumber: any): Observable<BTassetResp> {
    return this.http.get<BTassetResp>(`bt/api/assets/${assetsserialnumber}`)

  }


 public updateCase(req: BTCaseRequest,id): Observable<BTCaseResponse> {
  return this.http.put<BTCaseResponse>(this.baseUrl+'/'+id, req);
}
 public getCaseTypes(verticalname: number): Observable<Array<BT_CaseTypeForList>> {
  return this.http.get<Array<BT_CaseTypeForList>>(this.baseUrl+`/types/${verticalname}`)

}
public getCaseProblemMasterList(): Observable<Array<BtProblemMaster>> {
  return this.http.get<Array<BtProblemMaster>>(this.baseUrl+`/problemMaster/forlist`);

}
public getCaseRecord(): Observable<Array<BT_CaseTypeRecord>> {
  return this.http.get<Array<BT_CaseTypeRecord>>('api/businessdomain')

}
public getCaseReason(caseType: number): Observable<Array<BT_CaseReasonForList>> {
  return this.http.get<Array<BT_CaseReasonForList>>( this.baseUrl+`/casereason/${caseType}`)

}
public getCaseStatus(): Observable<Array<BT_CaseStatus>> {
  return this.http.get<Array<BT_CaseStatus>>(this.baseUrl+'/casestatus')

}
public getCaseStage(status: number): Observable<Array<BT_CaseStageDetails>> {
  return this.http.get<Array<BT_CaseStageDetails>>(this.baseUrl+'/casestage/' + status)

}
public getCaseOrigin(): Observable<Array<BT_CaseOriginDetails>> {
  return this.http.get<Array<BT_CaseOriginDetails>>(this.baseUrl+'/caseorigin')

}
public getCasesubReason(): Observable<Array<any>>{
  return this.http.get<Array<any>>(this.baseUrl+'/casesubreason')
}
public getCasePriority(): Observable<Array<BT_CasePriority>> {
  return this.http.get<Array<BT_CasePriority>>(this.baseUrl+'/casepriority')

}
public getSitesForList(): Observable<Array<any>> {
  return this.http.get<Array<any>>('bt/api/sites/forlist');
}
public getCaseTypeForlist():Observable<Array<string>>{
  return this.http.get<Array<string>>(this.baseUrl+'/casetype/forlist')
}
public getAssestForList(): Observable<Array<any>> {
  return this.http.get<Array<any>>('bt/api/assets/forlist');
}
public getProblemReported(caseRecordType: number): Observable<Array<ProblemReported>> {
  return this.http.get<Array<ProblemReported>>('bt/api/case/caseexpectedproblem' + '/' + caseRecordType)

}
public getSitesForListTini(key): Observable<Array<BT_SiteDetailForList>> {
  return this.http.get<Array<BT_SiteDetailForList>>(`bt/api/sitedetails/forlist?search=${key}`);
}
 public saveCase(request: BTCaseRequest): Observable<BTCaseResponse> {

   return this.http.post<BTCaseResponse>(this.baseUrl, request);
 }
 public getCase(filter?:string,pageInfo?:string): Observable<Page<BTCaseResponse>> {
   let url = `${this.baseUrl}?`;
   if (filter) {

       url = `${url}&${filter}`;
      
   }
   if (pageInfo) {
       url = `${url}&${pageInfo}`;
   }
   return this.http.get<Page<BTCaseResponse>>(url);

 }
 public getProductConsuedBycaseId(caseid: number, page: number, column: string, order: string): Observable<Page<ServiceAppointmentProductConsumedResponse>> {
  return this.http.get<Page<ServiceAppointmentProductConsumedResponse>>(this.baseUrl+'/releted' + '/' + caseid + '/productconsumed' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
}
public getAllProductConsuedBycaseId(caseid: number): Observable<Page<ServiceAppointmentProductConsumedResponse>> {
  return this.http.get<Page<ServiceAppointmentProductConsumedResponse>>(this.baseUrl+'/releted' + '/' + caseid + '/productconsumed');//&'+"sort="+column+","+order);
}
//  advance search
public getSearchResult(searchparam,page?): Observable<Array<BTCaseResponse>> {
  return this.http.get<Array<BTCaseResponse>>(`${this.baseUrl}/advancesearch?size=20&page=${page}`,{params:searchparam});
}
public getDetailByCaseId(caseid: number): Observable<BTCaseResponse> {
  return this.http.get<BTCaseResponse>(`${this.baseUrl}/${caseid}`);
}
public getServiceAppointmentByCaseId(caseid: number, page: number, column: string, order: string): Observable<Page<BTServiceAppointmentResponse>> {
  return this.http.get<Page<BTServiceAppointmentResponse>>(this.baseUrl + '/releted'+'/' + caseid + '/serviceappointment' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
}
public getDownloadFile(fileName: any): Observable<any> {
  return this.http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
}
public uploadImageFile(id: any, formdata: FormData): Observable<any> {
  return this.http.post('api/uploadMultipleFiles?objectName=BtCase&objectId=' + id, formdata);
}
public filedownload(fileName: string): Observable<Blob> {
  return this.http.get(`${this.downloadFileURL}/${fileName}`, { responseType: 'blob' });
}
public getFileList(id: number): Observable<Array<any>> {
  return this.http.get<Array<any>>(this.baseUrl+'/casefileupload/' + id+'/list')
}
public getContactList(sapNumber: string): Observable<Array<any>> {
  return this.http.get<Array<any>>(`bt/api/accounts/${sapNumber}/contacts`);
}
//for get small contact
public getContactSmall(sapnumber:string,contact:string):Observable<Array<any>>{
  return this.http.get<Array<any>>(`bt/api/accounts/${sapnumber}/contacts/forlist/sml/${contact}`);
}

public getCaseCustomerAdvFORList(): Observable<Array<string>> {
  return this.http.get<Array<string>>(`bt/api/case/customer/forlist`)

}
public getCaseLists(): Observable<Array<CaseResponse>> {
  return this.http.get<Array<CaseResponse>>(`api/case/caseforlist`);
}

/////For battery PRC
public getExcelTemplate(): Observable<Blob> {
  return this.http.get(`${this.excelTemplateURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
}
  // for advance search
  public getCaseReasonAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.baseUrl+`/casereason/forlist`)

  }
  public getCaseStatusAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.baseUrl+`/casestatus/forlist`)

  }
  public getCaseStageAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.baseUrl+`/casestage/forlist`)

  }

public uploadFiles(file: File): Observable<any> {
  const fd: FormData = new FormData();
  fd.append('file', file, file.name);
  return this.http.post(this.FileUploadUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
// asset type choice nre requirment

public getAssetTypeChoice(): Observable<Array<AssetTypeChoice>> {
  return this.http.get<Array<AssetTypeChoice>>(this.baseUrl+`/forlist/asstesType/`)

}
  // method add by pawan for commercial 
  public getRejectLogListByCaseId(caseManualId: string,filter?:any): Observable<Array<caseLogReject>> {
    return this.http.get<Array<caseLogReject>>(this.baseUrl + '/reject' + '?caseManualId=' + caseManualId);
  }
  public getCaseByStatus( status: string,filter?: string, pageInfo?: string): Observable<Page<BTCaseResponse>> {

    let url = `${this.caseStatusUrl + '?status=' + encodeURI(status)}`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<BTCaseResponse>>(url);

  }
  public getExcelTemplateforDocRecieved(): Observable<Blob> {
    return this.http.get(`${this.docrecievedbulURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  
  public getExcelTemplateforhangeCaseType(): Observable<Blob> {
    return this.http.get(`${this.changecaseTypeURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getCaseByStage(stage: string,filter?:string,pageInfo?:string): Observable<Page<BTCaseResponse>> {

    let url = `${this.caseStageUrl+ '?stage=' + encodeURI(stage)}`;
    // if (filter) {
    //     url = `${url}&${filter}`;
    // }
    if (filter) {

      url = `${url}&${filter}`;
     
  }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<BTCaseResponse>>(url);

  }
  public uploadchangeCaseTypeFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    return this.http.post(this.baseUrl + '/changeCaseType', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
    // update casetype change by put
    public updateChnageCaseType(req: ChangecaseTypeRequest): Observable<any> {
      return this.http.put<any>(this.baseUrl+'/changeCaseTypeSingle', req)
    }
     // change case type list
    public getCaseTypeschangeCaseType(precasetype: string,precaseReason:string): Observable<Array<String>> {
    return this.http.get<Array<String>>( this.baseUrl+'/'+this.changecasetypeListURL+'?existing_case_type=' + encodeURI(precasetype)+'&existing_case_reason=' + encodeURI(precaseReason))

  }
   // case reason list for chaneCasetype
   public getCaseTypeschangeCaseReason(precasetype: string,precaseReason:string,selectedcasetype): Observable<Array<String>> {
    return this.http.get<Array<String>>(this.baseUrl+'/'+ this.casereasonforchangecasetypeListURL+'?existing_case_type='+ encodeURI(precasetype)+'&existing_case_reason='+encodeURI(precaseReason) +'&new_case_type='+ encodeURI(selectedcasetype))

  }
  
  // case reject
  public rejectcase(req: RejectCaseRequest): Observable<any> {
    return this.http.put<any>(this.baseUrl+`/reject`, req)
  }
  public uploadFilecase(id: any,files:File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('files', files, files.name);
    console.log(files);
    return this.http.post(this.baseUrl+'/casefileupload/' + id, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public rejectcase_uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    console.log(fd);
    return this.http.post(this.caserejectBulkUplUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public updatePD(req: CaseProofOfDeliveryRequest): Observable<BTCaseResponse> {
    return this.http.put<BTCaseResponse>(`bt/api/case/proofofdelivery`, req)
  }
  public getPODExcelTemplate(): Observable<Blob> {
    return this.http.get(`${this.podExcelTempURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  // missing asset file bulkupload
  public missingassetcase_uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    console.log(fd);
    return this.http.post(this.baseUrl+'/missingAssets', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }

  public getCaseMissingAssetTemplate(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/missingAssets`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  // pdf

  public createPDF(id: number): Observable<any> {


    return this.http.get(`${this.baseUrl}${'/pdf?'}${'caseid='}${id}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }

  // get cases according to case type and case reason
  public getCasebyTypereason(caseTypeName: string,reason:string): Observable<Array<any>> {
    return this.http.get<Array<any>>( this.baseUrl+`/casetypecasereason?type=${caseTypeName}&reason=${reason}`);
  }
  // public updatePODwithFile(fd): Observable<BTCaseResponse> {
  //   return this.http.put<BTCaseResponse>(`bt/api/case/podupdate`, fd)
  // }
}
