import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActionHistoryObjectAndId, ActionHistoryResponse, ObjectDefinition, Page } from './model';

@Injectable({ providedIn: 'root' })
export class ObjectServices {
    private baseurl = 'api/object';
    constructor(private _http: HttpClient) { }
    public getObjects(): Observable<Array<ObjectDefinition>> {
        return this._http.get<Array<ObjectDefinition>>(`${this.baseurl}`);
    }
    public getObject(objectName: string): Observable<ObjectDefinition> {
        return this._http.get<ObjectDefinition>(`${this.baseurl}/${objectName}`);
    }
    public getObjectFieldName(objectName: string): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/${objectName}/methods`);
    }
    public getObjectForList(): Observable<Array<any>> {
        return this._http.get<Array<any>>(`${this.baseurl}/forlist`);
    }

    public getActionHistoryFull(actionHistoryObjectAndId: ActionHistoryObjectAndId): Observable<Array<ActionHistoryResponse>> {
        return this._http.get<Array<ActionHistoryResponse>>(`${this.baseurl}/objecthistory?objectname=${actionHistoryObjectAndId.objectName}&objectid=${actionHistoryObjectAndId.objectId}`);
    }
    public getActionHistory(actionHisotryObjectAndId:ActionHistoryObjectAndId,filter:any):Observable<Page<ActionHistoryResponse>>{
        return this._http.get<Page<ActionHistoryResponse>>(`${this.baseurl}/objecthistorypage?objectname=${actionHisotryObjectAndId.objectName}&objectid=${actionHisotryObjectAndId.objectId}&${filter}`);
    }
}
