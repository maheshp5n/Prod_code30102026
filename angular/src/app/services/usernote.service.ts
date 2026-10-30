import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
@Injectable({ providedIn: 'root' })
export class UsernotesServices {
    private baseuurl = 'api/usernotes';
    constructor(private _http: HttpClient) { }

    public getNotes(): Observable<Array<any>> {
        return this._http.get<Array<any>>(this.baseuurl);
    }
    public delteNote(id) {
        return this._http.delete(`${this.baseuurl}/${id}`);
    }
    public createNotes(data): Observable<Array<any>> {
        return this._http.post<Array<any>>(this.baseuurl, data);
    }
}
