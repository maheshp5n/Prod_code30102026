import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from './model';

@Injectable({
  providedIn: 'root'
})
export class LazyreportdownloadService {
  private baseurl='api/asyreport'
  constructor(private http:HttpClient) { }

  public getPendingReport(pgeoption):Observable<Page<any>>{
    return this.http.get<Page<any>>(this.baseurl,{params:pgeoption});
  }
  public searchReport(searchkeys):Observable<Page<any>>{
    return this.http.get<Page<any>>(this.baseurl,{params:searchkeys});
  }
}
