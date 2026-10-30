import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { cityDetailsReq, cityDetailsResponse, CityCatagory, Login, Page } from "./model";
import { Observable } from "rxjs";

@Injectable({providedIn:'root'})
export class cityService{
    baseUrl='api/city';
    recentVisitUrl='api/city/recentvisit';
    getTableUrl:'api/city';

    constructor(private http:HttpClient){}


    public saveCity(request:cityDetailsReq):Observable<cityDetailsReq>{
      
        return this.http.post<cityDetailsReq>('api/city',request);

    }
  

    // public getCity(cityname:string):Observable<Array<cityDetailsResponse>>{
    //     return this.http.get<Array<cityDetailsResponse>>(`${this.baseUrl}/${cityname}`);
    // }

    
    public getCity(filter?:string,pageInfo?:string):Observable<Page<cityDetailsResponse>>{
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
            return this.http.get<Page<cityDetailsResponse>>(url);
    
    }
    public getCityResponse(page?:number):Observable<Page<cityDetailsResponse>>{
        if(page){
            return this.http.get<Page<cityDetailsResponse>>(`api/city?page=${page}`);
        }
        return this.http.get<Page<cityDetailsResponse>>('api/city');

    }
    public getRecentVisit(page:number,column:string,order:string):Observable<Page<cityDetailsResponse>>{
       
            return this.http.get<Page<cityDetailsResponse>>(this.recentVisitUrl+ '?page=' + page + '&size=20&' +"sort="+column+","+order);
        
    }
    public getcityCatg():Observable<Array<CityCatagory>>{
        return this.http.get<Array<CityCatagory>>(`${this.baseUrl}/catagory`);
    }
    public updateCity(cityReq:cityDetailsReq,cityname:string):Observable<cityDetailsReq>{
      
        return this.http.put<cityDetailsReq>(`${this.baseUrl}/${cityname}`,cityReq);

    }

    
    public getCityByCityname(cityname:string):Observable<cityDetailsResponse>{
        return this.http.get<cityDetailsResponse>(`${this.baseUrl}/${cityname}`);
    }

    public getCityName(cityname:string):Observable<cityDetailsResponse>{
        return this.http.get<cityDetailsResponse>(`${this.baseUrl}/${cityname}`);
    }
    // search

    public getSearchResult(searchparam): Observable<Array<cityDetailsResponse>> {
        return this.http.get<Array<cityDetailsResponse>>(`${this.baseUrl}/advancesearch`,{params:searchparam});
      }

}