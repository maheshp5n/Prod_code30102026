import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PdfServiceService {

  constructor(private _http: HttpClient) { }

  public createPDF(baseUrl: string, idName: string, id: number): Observable<any> {

    return this._http.get(`${baseUrl}${'/pdf?'}${idName}${'='}${id}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
