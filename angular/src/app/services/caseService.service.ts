import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { CaseRequest, CaseResponse, Page, CaseTypeForList, CaseTypeRecord, CaseStatus, CaseStageDetails, CaseOriginDetails, CasePriority, CaseReasonForList, SiteDetailsForList, AssetsResponse, AssetsForList, ProblemReported, PendingResponsibility, CaseForList, ServiceAppointmentResponse, ServiceAppointmentProductConsumedResponse, opportunitycaseResponse, OppertunityResponse, BatteryFSRResponse, CaseProofOfDeliveryRequest, RejectCaseRequest, caseLogReject, ChangecaseTypeRequest } from './model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class caseService {
  baseUrl = 'api/case';
  recentUrl = 'api/case/recentvisit'
  reletadeUrl = 'api/case/releted';
  caseStatusUrl = 'api/case/bystatus';
  caseStageUrl = 'api/case/bystage';
  BttryFSRURL = 'api/batteryfsr/related';
  FileUploadUrl = 'api/case/import';
  podbulkURL='api/case/podBulkUpload';
  caserejectBulkUplUrl='api/case/caseRejectBulkUpload';
  downloadFileURL = 'api/downloadFile';
  excelTemplateURL = 'api/case/templateExcel';
  podExcelTempURL='api/case/podTemplate';
  caseRejectTempURL='api/case/caseRejectTemplate';
  changecaseTypeURL = 'api/case/templateChangeCaseType';
  docrecievedbulURL='api/case/documentreceived/template'
  changecasetypeListURL='changeCaseTypeSingle/caseType';
  casereasonforchangecasetypeListURL='changeCaseTypeSingle/caseReason'
  constructor(private http: HttpClient) { }
  public saveCase(request: CaseRequest): Observable<CaseRequest> {

    return this.http.post<CaseRequest>(this.baseUrl, request);
  }
  public getCase(filter?:string,pageInfo?:string): Observable<Page<CaseResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<CaseResponse>>(url);

  }
  public getRecentVisit(page: number, column: string, order: string): Observable<Page<CaseResponse>> {

    return this.http.get<Page<CaseResponse>>(this.recentUrl + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);

  }
  public getCaseTypes(verticalname: number): Observable<Array<CaseTypeForList>> {
    return this.http.get<Array<CaseTypeForList>>(`api/case/types/${verticalname}`)

  }
  public getCaseRecord(): Observable<Array<CaseTypeRecord>> {
    return this.http.get<Array<CaseTypeRecord>>('api/businessdomain')

  }
  public getCaseReason(caseType: number): Observable<Array<CaseReasonForList>> {
    return this.http.get<Array<CaseReasonForList>>(`api/case/casereason/${caseType}`)

  }
  public getCaseStatus(): Observable<Array<CaseStatus>> {
    return this.http.get<Array<CaseStatus>>('api/case/casestatus')

  }
  public getCaseStage(status: number, division: number): Observable<Array<CaseStageDetails>> {
    return this.http.get<Array<CaseStageDetails>>('api/case/casestage?status=' + status + '&division=' + division)

  }
  public getCaseOrigin(): Observable<Array<CaseOriginDetails>> {
    return this.http.get<Array<CaseOriginDetails>>('api/case/caseorigin')

  }
  public getCasePriority(): Observable<Array<CasePriority>> {
    return this.http.get<Array<CasePriority>>('api/case/casepriority')

  }
  public getSitesForList(): Observable<Array<SiteDetailsForList>> {
    return this.http.get<Array<SiteDetailsForList>>('api/sites/forlist');
  }
  public getCaseTypeForlist():Observable<Array<string>>{
    return this.http.get<Array<string>>(this.baseUrl+'/casetype/forlist')
  }
  public getAssestForList(): Observable<Array<AssetsForList>> {
    return this.http.get<Array<AssetsForList>>('api/assets/forlist');
  }

  public getDetailByCaseId(caseid: number): Observable<CaseResponse> {
    return this.http.get<CaseResponse>(`${this.baseUrl}/${caseid}`);
  }
  public getProblemReported(caseRecordType: number): Observable<Array<ProblemReported>> {
    return this.http.get<Array<ProblemReported>>('api/case/caseexpectedproblem' + '/' + caseRecordType)

  }
  public getCaseAssets(assetsserialnumber: string): Observable<AssetsResponse> {
    return this.http.get<AssetsResponse>(`api/assets/${assetsserialnumber}`)

  }
  public updateCase(req: CaseRequest): Observable<CaseRequest> {
    return this.http.put<CaseRequest>(`api/case/`, req)
  }
  public pendingrisponsibility(): Observable<Array<PendingResponsibility>> {
    return this.http.get<Array<PendingResponsibility>>('pendingresponsibility')
  }

  // Ankur
  public getCaseForList(): Observable<Array<CaseForList>> {
    return this.http.get<Array<CaseForList>>('api/case/caseforlist');
  }
  public getServiceAppointmentByCaseId(caseid: number, page: number, column: string, order: string): Observable<Page<ServiceAppointmentResponse>> {
    return this.http.get<Page<ServiceAppointmentResponse>>(this.reletadeUrl + '/' + caseid + '/serviceappointment' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
  }
  public getProductConsuedBycaseId(caseid: number, page: number, column: string, order: string): Observable<Page<ServiceAppointmentProductConsumedResponse>> {
    return this.http.get<Page<ServiceAppointmentProductConsumedResponse>>(this.reletadeUrl + '/' + caseid + '/productconsumed' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
  }
  public getProductOpportunityBycaseId(caseid: number, page: number, column: string, order: string): Observable<Page<OppertunityResponse>> {
    return this.http.get<Page<OppertunityResponse>>(this.reletadeUrl + '/' + caseid + '/opertunity' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
  }

  public getCaseByStatus( status: string,filter?: string, pageInfo?: string): Observable<Page<CaseResponse>> {

    let url = `${this.caseStatusUrl + '?status=' + encodeURI(status)}`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<CaseResponse>>(url);

  }
  public getCaseByStage(stage: string,filter?:string,pageInfo?:string): Observable<Page<CaseResponse>> {

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
    return this.http.get<Page<CaseResponse>>(url);

  }
  public getBattryFSRByCaseId(caseId: number, page: number, column: string, order: string): Observable<Page<BatteryFSRResponse>> {
    return this.http.get<Page<BatteryFSRResponse>>(this.BttryFSRURL + '/' + caseId + '?page=' + page + '&size=20&');//&'+"sort="+column+","+order);
  }
  public updatePD(req: CaseProofOfDeliveryRequest): Observable<CaseResponse> {
    return this.http.put<CaseResponse>(`api/case/proofofdelivery`, req)
  }
  public uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    return this.http.post(this.FileUploadUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  // POD bulk upload

  public pod_uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    console.log(fd);
    return this.http.post(this.podbulkURL, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }

  public uploadchangeCaseTypeFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    return this.http.post(this.baseUrl + '/changeCaseType', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
  public uploadImageFile(id: any, formdata: FormData): Observable<any> {
    return this.http.post('api/uploadMultipleFiles?objectName=CaseDetails&objectId=' + id, formdata);
  }
  public filedownload(fileName: string): Observable<Blob> {
    return this.http.get(`${this.downloadFileURL}/${fileName}`, { responseType: 'blob' });
  }
  public filedownloadnew(id: number,caseid): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${caseid}/downloadfile/${id}`, { responseType: 'blob' });
  }
  public getFileList(id: number): Observable<Array<any>> {
    return this.http.get<Array<any>>('api/uploadedFiles?objectName=CaseDetails&objectId=' + id)
  }
  public getDownloadFile(fileName: any): Observable<any> {
    return this.http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
  }
  public getDownloadFileCaseNew(id,fileid):Observable<any>{
    return this.http.get(`${this.baseUrl}/${id}/downloadFile/${fileid}`, { responseType: 'blob' });
  }
  public getExcelTemplate(): Observable<Blob> {
    return this.http.get(`${this.excelTemplateURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getPODExcelTemplate(): Observable<Blob> {
    return this.http.get(`${this.podExcelTempURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getCaseRejectExcelTemplate(): Observable<Blob> {
    return this.http.get(`${this.caseRejectTempURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getExcelTemplateforhangeCaseType(): Observable<Blob> {
    return this.http.get(`${this.changecaseTypeURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getExcelTemplateforDocRecieved(): Observable<Blob> {
    return this.http.get(`${this.docrecievedbulURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }

  public getSearchResult(searchparam): Observable<Array<CaseResponse>> {
    return this.http.post<Array<CaseResponse>>(`${this.baseUrl}/searchincase`, searchparam);
  }

  public getSitesForListTini(key): Observable<Page<SiteDetailsForList>> {
    return this.http.get<Page<SiteDetailsForList>>(`api/sites/forlist/small?searchkey=${key}&page=0&size=20`);
  }
  // case reject
  public rejectcase(req: RejectCaseRequest): Observable<any> {
    return this.http.put<any>(this.baseUrl+`/reject`, req)
  }
  // rejectlog_list
  public getRejectLogListByCaseId(caseManualId: string,filter?:any): Observable<Array<caseLogReject>> {
    return this.http.get<Array<caseLogReject>>(this.baseUrl + '/reject' + '?caseManualId=' + caseManualId);//&'+"sort="+column+","+order);
  }
  // change case type list
  public getCaseTypeschangeCaseType(precasetype: string,precaseReason:string): Observable<Array<String>> {
    return this.http.get<Array<String>>( this.baseUrl+'/'+this.changecasetypeListURL+'?existing_case_type=' + encodeURI(precasetype)+'&existing_case_reason=' + encodeURI(precaseReason))

  }
  // case reason list for chaneCasetype
  public getCaseTypeschangeCaseReason(precasetype: string,precaseReason:string,selectedcasetype): Observable<Array<String>> {
    return this.http.get<Array<String>>(this.baseUrl+'/'+ this.casereasonforchangecasetypeListURL+'?existing_case_type='+ encodeURI(precasetype)+'&existing_case_reason='+encodeURI(precaseReason) +'&new_case_type='+ encodeURI(selectedcasetype))

  }
  // update casetype change by put
  public updateChnageCaseType(req: ChangecaseTypeRequest): Observable<any> {
    return this.http.put<any>(this.baseUrl+'/changeCaseTypeSingle', req)
  }
  public rejectcase_uploadFiles(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    console.log(fd);
    return this.http.post(this.caserejectBulkUplUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  // for advance search
  public getCaseReasonAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(`api/case/casereason/forlist`)

  }
  public getCaseStatusAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(`api/case/casestatus/forlist`)

  }
  public getCaseStageAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(`api/case/casestage/forlist`)

  }


  public getCaseCustomerAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(`api/case/customer/forlist`)

  }
  public uploadFilecase(id: any,files:File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('files', files, files.name);
    console.log(files);
    return this.http.post(this.baseUrl+'/uploadfile/' + id, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getFileListcase(id: number): Observable<Array<any>> {
    return this.http.get<Array<any>>('api/uploadedFiles?objectName=CaseDetails&objectId='+ id)
  }
  public getFileListcasenew(id: number): Observable<Array<any>> {
    return this.http.get<Array<any>>(this.baseUrl+'/casefile/'+id);
  }
public getExistingSite(siteID):Observable<Array<any>>{
return this.http.get<Array<any>>('bt/api/pmsiteassets/site/'+siteID);
}

// uploadtemplate
public getExcelTemplatesitedata(): Observable<Blob> {
  return this.http.get(`${'bt/api/pmsiteassets/uploadtemplate'}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
}

public uploadFilesiteData(file:File): Observable<any> {
  const fd: FormData = new FormData();
  fd.append('file', file, file.name);
  console.log(file);
  return this.http.post('bt/api/pmsiteassets', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
}
// sitewithasset existing data

public getExistingassetSite(siteID,assetno):Observable<Array<any>>{
  return this.http.get<Array<any>>('bt/api/pmsiteassets/siteiwthasses/'+siteID+'?assetssapnumber='+assetno+'&condation=condation');
  }

}
