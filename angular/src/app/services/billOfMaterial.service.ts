import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BillOfMaterialRequest, BillOfMaterialResponse, DCTRepairRequest, CaseRequest } from './model';
import { Observable } from 'rxjs';

@Injectable()
export class BillOfmaterialService{
    private baseUrl='api/bom';
    constructor(private _http: HttpClient) { } 
    public saveBOM(id:string,request:Array<BillOfMaterialRequest>):Observable<Array<BillOfMaterialRequest>>{
      return this._http.post<Array<BillOfMaterialRequest>>(this.baseUrl +'/'+id,request);
     }
     public getBOM(id:string):Observable<Array<BillOfMaterialResponse>>{
       return this._http.get<Array<BillOfMaterialResponse>>(this.baseUrl+'/'+id)
     }
     public addBOM(id:string,request:BillOfMaterialRequest):Observable<Array<BillOfMaterialRequest>>{
      return this._http.post<Array<BillOfMaterialRequest>>(this.baseUrl +'/'+'additems'+'/' +id,request);
     }
 
}