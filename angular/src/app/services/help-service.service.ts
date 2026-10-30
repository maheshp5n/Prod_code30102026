import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HelpTutorial, Page } from './model';

@Injectable({
  providedIn: 'root'
})
export class HelpServiceService {
 baseURL="api/help"
  constructor(private http: HttpClient) {
    
   }
   public getHelpList(): Observable<Page<HelpTutorial>> {
    // let url = `${this.baseURL}?`;
    // if (filter) {

    //     url = `${url}&${filter}`;

    // }
    // if (pageInfo) {
    //     url = `${url}&${pageInfo}`;
    // }
    return this.http.get<Page<HelpTutorial>>(this.baseURL);


}
}
