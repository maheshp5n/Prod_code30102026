import { WorkOrderRequest } from './model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn:'root'})
export class workOrderForQuetService{
    private baseUrl = 'api/quote/updateworkorder';
    constructor(private http: HttpClient) { }

    public updateCaseInvoice(req:WorkOrderRequest):Observable<WorkOrderRequest>{
        return this.http.put<WorkOrderRequest>(this.baseUrl+'?workorder=',req)
    }
}