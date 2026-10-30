import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BtRmaList, Page, RMAResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtRmaService {

  private baseUrl = 'bt/api/rma';



  constructor(private _http: HttpClient) { }

  public getRma(filter?: string, pageInfo?: string): Observable<Page<RMAResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

      url = `${url}&${filter}`;

    }
    if (pageInfo) {
      url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<RMAResponse>>(url);
  }
  // for advance search
  public getSearchResult(searchparam): Observable<Array<any>> {
    return this._http.get<Array<any>>(`${this.baseUrl}/advancesearch`, { params: searchparam });
  }

  public getRmaById(id: number): Observable<any> {
    return this._http.get<any>(this.baseUrl + '/' + id);
  }

  public putRmaRejectById(fd): Observable<any> {
    return this._http.put<any>(this.baseUrl + '/reject', fd)
  }
  public changeRmaStatusById(fd): Observable<any> {
    return this._http.put<any>(this.baseUrl + '/updateStatus', fd)
  }
  // for download status template
  public getRmaStatusTemplate(): Observable<Blob> {
    return this._http.get(this.baseUrl + '/changeStatusTemplate', { responseType: 'blob' }).pipe(catchError(this.parseErrorblob))
  }

  ///for upload status template 
  public rmaStatusBulkUpload(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name)
    return this._http.put(this.baseUrl + '/changeStatusUpload', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob))
  }
  public getRejectRmaTemplate(): Observable<Blob> {
    return this._http.get(this.baseUrl + '/reject_template', { responseType: 'blob' }).pipe(catchError(this.parseErrorblob))
  }

  public rejectBulkUploadRMA(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name)
    return this._http.put(this.baseUrl + '/rejectUpload', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob))
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
  public rmaForList(search): Observable<Array<BtRmaList>> {
    return this._http.get<Array<BtRmaList>>(`${this.baseUrl}/forlist?search=${search}`);
  }


}
