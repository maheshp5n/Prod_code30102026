import { Observable } from 'rxjs';
import { Page } from './model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class CustomerInventoryServices {
    private baseurl = 'api/customerinventory';
    constructor(private http: HttpClient) {

    }
    public getCustomerInventory(filter?): Observable<Page<any>> {
        let url = this.baseurl;
        if (filter) {
            url = `${url}?${filter}`;
        }
        return this.http.get<Page<any>>(url);
    }
    public postCustomerInventoryIn(file: FormData) {
        return this.http.post(this.baseurl, file, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
    }
    public postOutInventory(file: FormData) {
        return this.http.post(`${this.baseurl}/out`, file, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
    }

    parseErrorBlob(err: HttpErrorResponse): Observable<any> {
        const reader: FileReader = new FileReader();
        const obs = Observable.create((observer: any) => {
            reader.onloadend = (e) => {
                observer.error(JSON.parse(reader.result.toString()));
                observer.complete();
            };
        });
        reader.readAsText(err.error);
        return obs;
    }

    getTempalte(type) {
        return this.http.get(`${this.baseurl}/template?type=${type}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorBlob));
    }
    public getAdvanceSearch(keysdata):Observable<Array<any>>{
        return this.http.get<Array<any>>(`${this.baseurl}/advancesearch`,{params:keysdata});
      }
}
