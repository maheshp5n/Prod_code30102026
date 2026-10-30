import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CaseInvoiceRequest, CaseResponse } from './model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({ providedIn: 'root' })
export class CommercialService{
    baseUrl='api/case';
    FileUploadUrl='api/case/bulkuploadInvoice';
    docRecievedBulURL='api/case/documentreceived/bulkupload'
    excelTemplateURL='api/case/templateExcelInvoice'
    constructor(private http: HttpClient) { }
    public updateCaseInvoice(req:CaseInvoiceRequest):Observable<CaseResponse>{
        return this.http.put<CaseResponse>(this.baseUrl+'/'+'invoice',req)
    }
 // bulk upload by shagufta
 public uploadFiles(file:File) : Observable<any>  { 
    const fd: FormData = new FormData();
    fd.append('file', file,file.name);
     return this.http.put(this.FileUploadUrl,fd ,{responseType: 'blob'}).pipe(catchError(this.parseErrorblob));
}
public uploadDocRecievdFiles(file:File) : Observable<any>  { 
  const fd: FormData = new FormData();
  fd.append('file', file, file.name);
  console.log(fd);
  return this.http.post(this.docRecievedBulURL, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
}
public getExcelTemplate():Observable<Blob>{
    return this.http.get(`${this.excelTemplateURL}`,{ responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
  public updateDocRecieved(caseId:number):Observable<CaseResponse>{
    return this.http.put<CaseResponse>(this.baseUrl+'/'+'documentreceived' +'/'+caseId,'');
}

}