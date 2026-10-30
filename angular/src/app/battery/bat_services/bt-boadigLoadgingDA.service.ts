import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { BoardingLoading } from "../../services/model";
import { Bat_BoardingLoadingDetailsRequest, Bat_BoardingLoadingDetailsResponse, Page } from "../bt_model";
@Injectable({
    providedIn: 'root'
  })
export class BAT_BoardingLoadingService{
    baseUrl:string='bt/api/boardingloading';
  constructor(private _http: HttpClient) { } 
  public getBoardingList(): Observable<Array<BoardingLoading>> {
    return this._http.get<Array<BoardingLoading>>('api/boardingloading');
  }

  public postBoardingList(boardingLoading: BoardingLoading): Observable<BoardingLoading> {
    return this._http.post<BoardingLoading>(this.baseUrl, boardingLoading);
  }
  public saveBoading(request:Bat_BoardingLoadingDetailsRequest):Observable<Bat_BoardingLoadingDetailsRequest>{
      
    return this._http.post<Bat_BoardingLoadingDetailsRequest>(this.baseUrl,request);

}
public getDetailByboadingName(boardingName:string):Observable<Bat_BoardingLoadingDetailsResponse>{
  return this._http.get<Bat_BoardingLoadingDetailsResponse>(`${this.baseUrl}/${boardingName}`);
}
public updateBoading(req:Bat_BoardingLoadingDetailsRequest):Observable<Bat_BoardingLoadingDetailsRequest>{
  return this._http.put<Bat_BoardingLoadingDetailsRequest>(this.baseUrl,req)
}
public geBoadingTable(filter?: string, pageInfo?: string):Observable<Page<Bat_BoardingLoadingDetailsResponse>>{
  let url = `${this.baseUrl}?`;
  if (filter) {
      url = `${url}&${filter}`;
  }
  if (pageInfo) {
      url = `${url}&${pageInfo}`;
  }  
  return this._http.get<Page<Bat_BoardingLoadingDetailsResponse>>(url);

}


  public getBoardingForList(): Observable<Array<BoardingLoading>> {
    return this._http.get<Array<BoardingLoading>>('bt/api/boardingloading/boardingforlist');
  }




}

