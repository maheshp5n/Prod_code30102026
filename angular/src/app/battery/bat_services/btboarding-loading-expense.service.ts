import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BoardingLoading, BoardingLoadingDetailsRequest, BoardingLoadingDetailsResponse, Page } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtboardingLoadingExpenseService {
  baseUrl:string='api/boardingloading';
  constructor(private _http: HttpClient) { } 
  public getBoardingList(): Observable<Array<BoardingLoading>> {
    return this._http.get<Array<BoardingLoading>>(this.baseUrl);
  }

  public postBoardingList(boardingLoading: BoardingLoading): Observable<BoardingLoading> {
    return this._http.post<BoardingLoading>(this.baseUrl, boardingLoading);
  }
  public saveBoading(request:BoardingLoadingDetailsRequest):Observable<BoardingLoadingDetailsRequest>{
      
    return this._http.post<BoardingLoadingDetailsRequest>(this.baseUrl, request);

}
public getDetailByboadingName(boardingName:string):Observable<BoardingLoadingDetailsResponse>{
  return this._http.get<BoardingLoadingDetailsResponse>(`${this.baseUrl}/${boardingName}`);
}
public updateBoading(req:BoardingLoadingDetailsRequest):Observable<BoardingLoadingDetailsRequest>{
  return this._http.put<BoardingLoadingDetailsRequest>(this.baseUrl,req)
}
public geBoadingTable(filter?: string, pageInfo?: string):Observable<Page<BoardingLoadingDetailsResponse>>{
  let url = `${this.baseUrl}?`;
  if (filter) {
      url = `${url}&${filter}`;
  }
  if (pageInfo) {
      url = `${url}&${pageInfo}`;
  }  
  return this._http.get<Page<BoardingLoadingDetailsResponse>>(url);

}


  public getBoardingForList(): Observable<Array<BoardingLoading>> {
    return this._http.get<Array<BoardingLoading>>(this.baseUrl+'/boardingforlist');
  }

  
  
  public getBoardingName(id: string): Observable<BoardingLoading> {
    return this._http.get<BoardingLoading>(this.baseUrl + '/' + id);
  }

}