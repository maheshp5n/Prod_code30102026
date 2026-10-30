import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class WorkflowServices{
    private baseurl='api/workflow';
    constructor(private _http:HttpClient){}

    public getWorkflow():Observable<Array<WorkflowResponse>>{
        return this._http.get<Array<WorkflowResponse>>(`${this.baseurl}`);
    }

    public setWorkflow(data:WorkflowRequest):Observable<any>{
        return this._http.post<any>(this.baseurl,data);
    }
    public enableDisable(id:number,flag:boolean):Observable<WorkflowResponse>{
        return this._http.put<WorkflowResponse>(`${this.baseurl}/${id}/disableenable?flag=${flag}`,{});
    }
    public editWorkflow(id:number,workflow:WorkflowRequest):Observable<any>{
        return this._http.put<any>(`${this.baseurl}/${id}`,workflow);
    }
}

export class WorkflowResponse{
    id:number;
    description:string;
    objectName:string;
    object:string;
    dmlCommmand:string;
    onevent:string;
    createdBy:string;
    modifiedBy:string;
    createdDate:Date;
    modifiedDate:Date;
    className:string;
    active:string;
    createdByMidName:string;
    createdByLastName:string;
    createdByEmailId:string;
    modifiedByMidName:string;
    modifiedByLastName:string;
    modifiedByEmailId:string;
    codeSnip:string;
}
export class WorkflowRequest{
    id:number;
    object:string;
    onEntityManager:string;
    description:string;
    dmlCommand:string;
    on:string;
}
