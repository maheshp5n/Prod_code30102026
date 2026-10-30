import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Page, PrcActionRequest, PreRegCaseRequest, PreRegCaseResponse } from '../../services/model';
import { BTPrcActionRequest, BtPreRegCaseRequest, BtPreRegCaseResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtPreRegistrationCaseService {

  baseUrl = 'bt/api/preRegistrationCases';
   
  // reletadeUrl = 'api/preRegistrationCases/related';
  // byactionUrl = 'api/preRegistrationCases/byaction';
  // actionUrl = 'api/preRegistrationCases/action';
  BttryFSRURL = 'api/batteryfsr/related';
  // FileUploadUrl = 'api/case/import';
  downloadFileURL = 'api/downloadFile';
  // excelTemplateURL = 'api/case/templateExcel';
  // changecaseTypeURL = 'api/case/templateChangeCaseType';
  constructor(private http: HttpClient) { }
  public saveCase(request: BtPreRegCaseRequest): Observable<BtPreRegCaseRequest> {

    return this.http.post<BtPreRegCaseRequest>(this.baseUrl, request);
  }
  public getCase(filter?:string,pageInfo?:string): Observable<Page<BtPreRegCaseResponse>> {

    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<BtPreRegCaseResponse>>(url);

  }
  

  public getDetailByCaseId(caseid : number): Observable<BtPreRegCaseResponse> {
    return this.http.get<BtPreRegCaseResponse>(`${this.baseUrl}/${caseid}`);
  }

  public getCaseByPrcId(caseid: number): Observable<BtPreRegCaseResponse> {
    return this.http.get<BtPreRegCaseResponse>(`${this.baseUrl}/related/${caseid}/${"getcase"}`);
  }
  public updateCase(req: BtPreRegCaseRequest,id): Observable<BtPreRegCaseRequest> {
    return this.http.put<BtPreRegCaseRequest>(this.baseUrl+'/'+id, req)
  }

  public getRRCaseByAction(page: number, column: string, order: string, cmnt: string): Observable<Page<BtPreRegCaseResponse>> {

    return this.http.get<Page<BtPreRegCaseResponse>>(this.baseUrl + 'byaction?action=' + encodeURI(cmnt) + '&page=' + page + '&size=20&' + "sort=" + column + "," + order);

  }
  public RRCaseActionByIdIgnor( id:number,action: string,req:PreRegCaseRequest): Observable<BtPreRegCaseResponse> {

    return this.http.put<BtPreRegCaseResponse>(this.baseUrl+'/action/'+id + '?Ignore=' + encodeURI(action) ,req);

  }
  public RRCaseActionByIdCmnt(actionreq:BTPrcActionRequest): Observable<BtPreRegCaseResponse> {
  //  console.log(decodeURI(Generatecase));
    return this.http.put<BtPreRegCaseResponse>(this.baseUrl+'/action',actionreq);

  }
  // public uploadFiles(file: File): Observable<any> {
  //   const fd: FormData = new FormData();
  //   fd.append('file', file, file.name);
  //   return this.http.post(this.FileUploadUrl, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  // }

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
  // public getExcelTemplate(): Observable<Blob> {
  //   return this.http.get(`${this.excelTemplateURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  // }
  // public getExcelTemplateforhangeCaseType(): Observable<Blob> {
  //   return this.http.get(`${this.changecaseTypeURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  // }

  public getSearchResult(searchparam): Observable<Array<BtPreRegCaseResponse>> {
    return this.http.get<Array<BtPreRegCaseResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }
  public getPRCByCaseId(caseid: number, page: number): Observable<Page<BtPreRegCaseResponse>> {
    return this.http.get<Page<BtPreRegCaseResponse>>(this.baseUrl + '/related/case/' + caseid + '/getprc' + '?page=' + page + '&size=5');//&'+"sort="+column+","+order);
  }

  //prc by asset serial number
  public getPRCByAsset(assetser: string, page: number,size?:number): Observable<Page<BtPreRegCaseResponse>> {
    return this.http.get<Page<BtPreRegCaseResponse>>(this.baseUrl + '/related/assets' + '/getprc' +'?assetsserialnumber='+assetser+'&page=' + page + '&size='+size);//&'+"sort="+column+","+order);
  }
  // Prc by site id
  public getPRCBySite(siteId: number,filter?:string,pageInfo?:string): Observable<Page<BtPreRegCaseResponse>> {
    let url = `${this.baseUrl+'/related/site/' +siteId+ '/getprc'}?`;
    if (filter) {

        url = `${url}${filter}`;
       
    }
    if(pageInfo){
      url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<BtPreRegCaseResponse>>(url)
    // (this.reletadeUrl + '/site/' +siteId+ '/getprc' +'?page=' + page + '&size='+size);//&'+"sort="+column+","+order);
  }
  
public getLastActAdvFORList(): Observable<Array<string>> {
  return this.http.get<Array<string>>(this.baseUrl+'/lastaction/forlist')

}

}