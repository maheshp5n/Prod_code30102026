import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page,ApprovalRequest } from "../../services/model";
import { BTToBeApproved } from "../bt_model";

@Injectable({
    providedIn:'root'
})
export class BtTobeApprovedService{
    private baseurl='bt/api/tobeapproved'
    constructor(private http:HttpClient){}

    public getPendingPage(filter?):Observable<Page<any>>{
        return this.http.get<Page<any>>(`${this.baseurl}?${filter}`);
    }
    public updateAction(id,data){
        return this.http.put(`${this.baseurl}/${id}`,data);
    }
    // t/api/tobeapproved/single/approval?objectId=18&objectName=BtServiceAppointment
    
    public getItemToBeApprovdButton(objectName: string, objectid: any): Observable<BTToBeApproved> {
        return this.http.get<any>(`${this.baseurl}/single/approval?objectId=${objectid}&objectName=${objectName}`);
    }

    public getAdvanceSearchResult(search):Observable<Array<any>>{
        return this.http.get<Array<any>>(`${this.baseurl}/advanceSearch`,{params:search});
    }
    public updateMultipleAction(data: any): Observable<any> {
        return this.http.put(`${this.baseurl}/multiple`, data);
      }
      
}