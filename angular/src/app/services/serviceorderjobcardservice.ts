import { Page, Repaireng } from './model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ServiceOrderJobCardService {
    private baseurl = 'api/serviceorderjobcard';
    private getRepairURl='api/serviceorderjobcard/repairman'
    private excelTemplateURL='api/repair/bulk/template'
    private uploadTemplateURL='api/repair/bulk'
    constructor(private http: HttpClient) { }

    public createJobCard(serviceresourceid: any, data: Array<any>): Observable<Array<any>> {
        return this.http.post<Array<any>>(`${this.baseurl}/${serviceresourceid}`, data);
    }
    public getJobCards(filter?): Observable<Page<any>> {
        let url = this.baseurl;
        if (filter) {
            url = `${url}?${filter}`;
        }
        return this.http.get<Page<any>>(url);
    }
    public getJobCard(id): Observable<any> {
        return this.http.get(`${this.baseurl}/${id}`);
    }
    public getRpairEngList():Observable<Array<Repaireng>>{
      return this.http.get<Array<Repaireng>>(this.getRepairURl)
    }

    public getAdvanceSearchResult(searchkey):Observable<Array<any>>{
        return this.http.get<Array<any>>(`${this.baseurl}/advancesearch`,{params:searchkey});
    }
    // get template for downoad template

    public getExcelTemplate(): Observable<Blob> {
        return this.http.get(`${this.excelTemplateURL}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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

      public uploadFiles(file: File): Observable<any> {
        const fd: FormData = new FormData();
        fd.append('file', file, file.name);
        console.log(fd);
        return this.http.post(this.uploadTemplateURL, fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
      }
}
