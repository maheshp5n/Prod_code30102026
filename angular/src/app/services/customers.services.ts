import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import {  CustomerForList } from './model'

@Injectable({providedIn:'root'})
export class CustomerService {

    constructor(private _http:HttpClient){}
    
    public getAllCustomer():Observable<Array<CustomerForList>>{        
        return this._http.get<Array<CustomerForList>>('api/customer');
    }

    public postCustomer(request:CustomerForList):Observable<CustomerForList>{
        return this._http.post<CustomerForList>('api/customer',request);
    }   
    
}