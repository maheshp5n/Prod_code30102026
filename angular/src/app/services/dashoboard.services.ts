import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class DashboardService{
    private baseurl:string='api/dashboard'
    constructor(private _http:HttpClient){}
    public getCasefirst():Observable<Array<any>>{
        return this._http.get<Array<any>>(this.baseurl);
    }
    public getQRCActiveCustomer():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/qrcforactivecustomer`);
    }
    public getTop5Complaints():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/topfivecomplaints`);
    }
    public getAgingOfCloserComplaint():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/agingofcloser`);
    }
    public getPaidRepairDetails():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this.baseurl}/paidrepair`);
    }
} 