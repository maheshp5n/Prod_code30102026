import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Page,
  ExpenseManagementResponse,
  ExpenseManagementRequest,
  ExpenseManagementStatus,
  ExpenseManagementExpenseType,
  ExpenseManagementExpensePurpose,
  ExpenseManagementBusinessArea,
  ExpenseManagementBusinessPlace,
  ExpenseManagementCostCenter,
  FileUploadResponse,
  GLCodeResponse,
  ExpenseAccountsOnlyRequest
} from './model';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpenseManagementService {
  private baseUrl = 'api/expensemanagement';
  private baseUrlRecent = 'api/expensemanagement/recentvisit';
  private baseUrlPurpose = 'api/expensemanagement/expensepurpose';
  private baseUrlType = 'api/expensemanagement/expensetype';
  private baseUrlStatus = 'api/expensemanagement/status';
  private baseUrlBusinessArea = 'api/expensemanagement/businessarea';
  private baseUrlBusinessPlace = 'api/expensemanagement/businessplace';
  private baseUrlCostCenter = 'api/expensemanagement/costcenter';
  private baseUrllistbystatus = 'api/expensemanagement/listbystatus';
  private baseUrl3 = 'api/expensemanagement/submitforapproval?expenseManagementId=';
  private baseUrltour = 'api/tourexpense';
  private baseUrllodging = 'api/tourexpense';
  private baseUrllocalCon = 'api/tourexpense';
  private baseUrlOther = 'api/tourexpense';


  constructor(private _http: HttpClient) { }

  public getExpenseByStatus( filter?: string, pageInfo?: string): Observable<Page<ExpenseManagementResponse>> {
    let url = `${this.baseUrllistbystatus}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ExpenseManagementResponse>>(url);
  }

  public getexpensesmanagement(
    filter?: string,
    pageInfo?: string,
  ): Observable<Page<ExpenseManagementResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ExpenseManagementResponse>>(url);
  }

  public getexpensesmanagementById(
    id: number
  ): Observable<ExpenseManagementResponse> {
    return this._http.get<ExpenseManagementResponse>(
      `${this.baseUrl}/${id}`
    );
  }

  public postexpensesmanagement(
    expensesmanagement: ExpenseManagementRequest
  ): Observable<ExpenseManagementResponse> {
    return this._http.post<ExpenseManagementResponse>(
      this.baseUrl,
      expensesmanagement
    );
  }

  public putexpensesmanagement(
    expensesmanagement: ExpenseManagementRequest
  ): Observable<ExpenseManagementRequest> {
    return this._http.put<ExpenseManagementRequest>(
      this.baseUrl + '/',
      expensesmanagement
    );
  }

  public getRecentexpensesmanagement(
    filter?: string,
    pageInfo?: string,
  ): Observable<Page<ExpenseManagementResponse>> {
    let url = `${this.baseUrlRecent}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<ExpenseManagementResponse>>(url)
  }

  //
  public getPurposeForList(
    division: number
  ): Observable<Array<ExpenseManagementExpensePurpose>> {
    return this._http.get<Array<ExpenseManagementExpensePurpose>>(
      this.baseUrlPurpose + '/' + division
    );
  }

  public getTypeList(
    division: number
  ): Observable<Array<ExpenseManagementExpenseType>> {
    return this._http.get<Array<ExpenseManagementExpenseType>>(
      this.baseUrlType + '/' + division
    );
  }

  public getStatusForList(
    division: number
  ): Observable<Array<ExpenseManagementStatus>> {
    return this._http.get<Array<ExpenseManagementStatus>>(
      this.baseUrlStatus + '/' + division
    );
  }

  public getBusinessAreaList(
    division: number
  ): Observable<Array<ExpenseManagementBusinessArea>> {
    return this._http.get<Array<ExpenseManagementBusinessArea>>(
      this.baseUrlBusinessArea + '/' + division
    );
  }

  public getBusinessPlaceList(
    division: number
  ): Observable<Array<ExpenseManagementBusinessPlace>> {
    return this._http.get<Array<ExpenseManagementBusinessPlace>>(
      this.baseUrlBusinessPlace + '/' + division
    );
  }

  public getCostCenterList(
    division: number
  ): Observable<Array<ExpenseManagementCostCenter>> {
    return this._http.get<Array<ExpenseManagementCostCenter>>(
      this.baseUrlCostCenter + '/' + division
    );
  }

  public postsubmitforapproval(eid, bid,remrks): Observable<ExpenseManagementResponse> {

    return this._http.post<ExpenseManagementResponse>(`${this.baseUrl3}${eid}${'&businessDoaminId='}${bid}${'&remark='}${remrks}`, ' ');
  }

  public uploadFile(file, objectName, objectId): Observable<ExpenseManagementResponse> {

    return this._http.post<ExpenseManagementResponse>(`${'api/uploadFile?'}${'objectName='}${objectName}${'&objectId='}${objectId}`,
      'file');
  }

  public getFileList(objectName: string, objectId: number): Observable<Array<FileUploadResponse>> {
    return this._http.get<Array<FileUploadResponse>>(`api/uploadedFiles` + '?objectName=' + objectName + '&objectId=' + objectId);
  }

  public downloadFile(fileName): Observable<any> {
    return this._http.get(`${'api/'}${'downloadFile/'}${fileName}`, { responseType: 'blob' });
  }
  public getGLSummaryList(
    expensemanagementId: number
  ): Observable<Array<GLCodeResponse>> {
    return this._http.get<Array<GLCodeResponse>>(
      this.baseUrl + '/glcodesummary'+'/' + expensemanagementId
    );
  }
  // shagufta
  public postexpenseAccountsOnly(
    accountsonlyReq: ExpenseAccountsOnlyRequest
  ): Observable<ExpenseAccountsOnlyRequest> {
    return this._http.post<ExpenseManagementResponse>(
      this.baseUrl+'/accountsonly',
      accountsonlyReq
    );
  }
  public deleteExpense(objectid,url): Observable<ExpenseManagementResponse> {
    return this._http.delete<ExpenseManagementResponse>(url+'/'+objectid);
}

// public deleteExpensetour(objectid): Observable<ExpenseManagementResponse> {
//   return this._http.delete<ExpenseManagementResponse>(this.baseUrl+'/'+objectid);
// }
// Search
public getSearchResult(searchparam): Observable<Array<ExpenseManagementResponse>> {
  return this._http.get<Array<ExpenseManagementResponse>>(`${this.baseUrl}/advancesearch`,{params:searchparam});
}
public statusForList():Observable<Array<string>>{
  return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
}
}
