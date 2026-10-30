import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Page, ApprovalRequest, ApprovalResponse, ApprovalWorkflowRequest, UserForList, UserRoleResponse, ObjectDefinition, ApprovalStepRequest, ApprovalStepsResponse } from './model'; import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApprovalService {
  private baseUrl = 'api/approval';
  private userListURL='api/users/forlist';
  private roleListURL='api/userroles'
  private ObjectDefListURL='api/object';
  private addLineItemURL='api/approval/step'
  private linItmListURL='api/approval/stepslist';
  private lineItemURL='api/approval/steps'
  

  constructor(private _http: HttpClient) { }

  public getApproval(filter?: string, pageInfo?: string): Observable<Page<ApprovalResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {
        url = `${url}&${filter}`;
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ApprovalResponse>>(url);
  }

  public getApprovalById(id: number): Observable<ApprovalResponse> {
    return this._http.get<ApprovalResponse>(`${this.baseUrl}/${id}`);
  }

  public postApproval(approval: ApprovalWorkflowRequest): Observable<ApprovalResponse> {
    return this._http.post<ApprovalResponse>(this.baseUrl, approval);
  }

  public putApproval(approval: ApprovalWorkflowRequest): Observable<ApprovalResponse> {
    return this._http.put<ApprovalResponse>(this.baseUrl, approval);
  }

  public getObjectLableNames(): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/${'objectNames'}`);
  }

  public getObjectNames(id: string): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/${'objectNames'}/${id}`);
  }

  public getObjectIdFiledName(id: string): Observable<any> {
    return this._http.get<any>(`${this.baseUrl}/${'objectIds'}/${id}`);
  }
  public getUsersList(): Observable<Array<UserForList>> {
    return this._http.get<Array<UserForList>>(`${this.userListURL}`);
  }
  public getRoleList(): Observable<Array<UserRoleResponse>> {
    return this._http.get<Array<UserRoleResponse>>(`${this.roleListURL}`);
  }
  getObjectDef():Observable<Array<ObjectDefinition>>{
    return this._http.get<Array<ObjectDefinition>>(`${this.ObjectDefListURL}`);

  }
  public postApprovalLineItem(req: ApprovalStepRequest): Observable<ApprovalStepRequest> {
    return this._http.post<ApprovalStepRequest>(this.addLineItemURL, req);
  }
  public getLineItemListById(id: number): Observable<Array<ApprovalStepsResponse>> {
    return this._http.get<Array<ApprovalStepsResponse>>(`${this.linItmListURL}/${id}`);
  }
  public getLineItemById(id: string): Observable<ApprovalStepsResponse> {
    return this._http.get<ApprovalStepsResponse>(`${this.lineItemURL}/${id}`);
  }
  public putlinItmApproval(approval: ApprovalStepRequest): Observable<ApprovalStepRequest> {
    return this._http.put<ApprovalStepRequest>(this.addLineItemURL, approval);
  }
  public deletApprovalItem(id:number):Observable<any>
  {
    return this._http.delete<any>(this.lineItemURL+'/'+id);
  }

}
