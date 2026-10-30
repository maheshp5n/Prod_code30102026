import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";


@Injectable({ providedIn: 'root' })
export class Pm_asset_site_service{
    constructor(private http: HttpClient) { }
    baseUrl='bt/api/pmsiteassets';
    public getExcelTemplate(): Observable<Blob> {
        return this.http.get(`${this.baseUrl}/uploadtemplate`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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

}