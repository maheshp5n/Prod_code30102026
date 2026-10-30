import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Page, PrcActionRequest, PreRegCaseRequest, PreRegCaseResponse } from './model';

@Injectable({providedIn:'root'})
export class PreRegCaseService{
    baseUrl = 'api/preRegistrationCases';
   
    reletadeUrl = 'api/preRegistrationCases/related';
    byactionUrl = 'api/preRegistrationCases/byaction';
    actionUrl = 'api/preRegistrationCases/action';
    BttryFSRURL = 'api/batteryfsr/related';
    FileUploadUrl = 'api/case/import';
    downloadFileURL = 'api/downloadFile';
    excelTemplateURL = 'api/case/templateExcel';
    changecaseTypeURL = 'api/case/templateChangeCaseType';
    constructor(private http: HttpClient) { }
    public saveCase(request: PreRegCaseRequest): Observable<PreRegCaseRequest> {
  
      return this.http.post<PreRegCaseRequest>(this.baseUrl, request);
    }
    public getCase(filter?:string,pageInfo?:string): Observable<Page<PreRegCaseResponse>> {
  
      let url = `${this.baseUrl}?`;
      if (filter) {
  
          url = `${url}&${filter}`;
         
      }
      if (pageInfo) {
          url = `${url}&${pageInfo}`;
      }
      return this.http.get<Page<PreRegCaseResponse>>(url);
  
    }
    
  
    public getDetailByCaseId(caseid: number): Observable<PreRegCaseResponse> {
      return this.http.get<PreRegCaseResponse>(`${this.baseUrl}/${caseid}`);
    }
  
    public getCaseByPrcId(caseid: number): Observable<PreRegCaseResponse> {
      return this.http.get<PreRegCaseResponse>(`${this.reletadeUrl}/${caseid}/${"getcase"}`);
    }
    public updateCase(req: PreRegCaseRequest): Observable<PreRegCaseRequest> {
      return this.http.put<PreRegCaseRequest>(this.baseUrl, req)
    }
  
    public getRRCaseByAction(page: number, column: string, order: string, cmnt: string): Observable<Page<PreRegCaseResponse>> {

      return this.http.get<Page<PreRegCaseResponse>>(this.byactionUrl + '?action=' + encodeURI(cmnt) + '&page=' + page + '&size=20&' + "sort=" + column + "," + order);
  
    }
    public RRCaseActionByIdIgnor( id:number,action: string,req:PreRegCaseRequest): Observable<PreRegCaseResponse> {

      return this.http.put<PreRegCaseResponse>(this.actionUrl+'/'+id + '?Ignore=' + encodeURI(action) ,req);
  
    }
    public RRCaseActionByIdCmnt(actionreq:PrcActionRequest): Observable<PreRegCaseResponse> {
    //  console.log(decodeURI(Generatecase));
      return this.http.put<PreRegCaseResponse>(this.actionUrl,actionreq);
  
    }
    public uploadFiles(file: File): Observable<any> {
      const fd: FormData = new FormData();
      fd.append('file', file, file.name);
      return this.http.post(this.FileUploadUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
    public getFileList(id: number): Observable<Array<any>> {
      return this.http.get<Array<any>>('api/uploadedFiles?objectName=CaseDetails&objectId=' + id)
    }
    public getDownloadFile(fileName: any): Observable<any> {
      return this.http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
    }
    public getExcelTemplate(): Observable<Blob> {
      return this.http.get(`${this.excelTemplateURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
    }
    public getExcelTemplateforhangeCaseType(): Observable<Blob> {
      return this.http.get(`${this.changecaseTypeURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
    }
  
    public getSearchResult(searchparam): Observable<Array<PreRegCaseResponse>> {
      return this.http.get<Array<PreRegCaseResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
    }
    public getPRCByCaseId(caseid: number, page: number): Observable<Page<PreRegCaseResponse>> {
      return this.http.get<Page<PreRegCaseResponse>>(this.reletadeUrl + '/case/' + caseid + '/getprc' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
    }
  
    //prc by asset serial number
    public getPRCByAsset(assetser: string, page: number,size?:number): Observable<Page<PreRegCaseResponse>> {
      return this.http.get<Page<PreRegCaseResponse>>(this.reletadeUrl + '/assets' + '/getprc' +'?assetsserialnumber='+assetser+'&page=' + page + '&size='+size);//&'+"sort="+column+","+order);
    }
    // Prc by site id
    public getPRCBySite(siteId: number,filter?:string,pageInfo?:string): Observable<Page<PreRegCaseResponse>> {
      let url = `${this.reletadeUrl+'/site/' +siteId+ '/getprc'}?`;
      if (filter) {
  
          url = `${url}&${filter}`;
         
      }
      if(pageInfo){
        url = `${url}&${pageInfo}`;
      }
      return this.http.get<Page<PreRegCaseResponse>>(url)
      // (this.reletadeUrl + '/site/' +siteId+ '/getprc' +'?page=' + page + '&size='+size);//&'+"sort="+column+","+order);
    }
    
  public getLastActAdvFORList(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.baseUrl+'/lastaction/forlist')

  }
  
}