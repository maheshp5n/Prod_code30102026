import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ExpenseAccountsOnlyRequest, ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseManagementCostCenter, ExpenseManagementExpensePurpose, ExpenseManagementExpenseType, ExpenseManagementResponse, ExpenseManagementStatus, FileUploadResponse, GLCodeResponse } from '../../services/model';
import { BatteryExpenseManagementRequest, BatteryExpenseManagementResponse, BtExpenseAccountsOnlyRequest, Page } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtexpensemanagementService {
  private baseUrl = 'bt/api/expense';
  private baseUrlRecent = this.baseUrl+'/recentvisit';
  private baseUrlBusinessPlace = this.baseUrl+'/businessplace';
  // private baseUrlPurpose = 'api/expensemanagement/expensepurpose';
  // private baseUrlType = 'api/expensemanagement/expensetype';
  // private baseUrlStatus = 'api/expensemanagement/status';
  // private baseUrlBusinessArea = 'api/expensemanagement/businessarea';
  // private baseUrlBusinessPlace = 'api/expensemanagement/businessplace';
  // private baseUrlCostCenter = 'api/expensemanagement/costcenter';
  // private baseUrllistbystatus = this.baseUrl+'/listbystatus';
  private baseUrl3 = this.baseUrl+'/submitforapproval?btExpenseManagementId=';
  // private baseUrltour = 'api/tourexpense';
  // private baseUrllodging = 'api/tourexpense';
  // private baseUrllocalCon = 'api/tourexpense';
  // private baseUrlOther = 'api/tourexpense';


  constructor(private _http: HttpClient) { }

  // public getExpenseByStatus( filter?: string, pageInfo?: string): Observable<Page<BatteryExpenseManagementResponse>> {
  //   let url = `${this.baseUrllistbystatus}?`;
  //   if (filter) {

  //       url = `${url}&${filter}`;
       
  //   }
  //   if (pageInfo) {
  //       url = `${url}&${pageInfo}`;
  //   }
  //   return this._http.get<Page<BatteryExpenseManagementResponse>>(url);
  // }

  public getexpensesmanagement(
    filter?: string,
    pageInfo?: string,
  ): Observable<Page<BatteryExpenseManagementResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BatteryExpenseManagementResponse>>(url);
  }

  public getexpensesmanagementById(
    id: number
  ): Observable<BatteryExpenseManagementResponse> {
    return this._http.get<BatteryExpenseManagementResponse>(
      this.baseUrl+'/getById/'+id
    );
  }

  public postexpensesmanagement(
    expensesmanagement: BatteryExpenseManagementRequest
  ): Observable<BatteryExpenseManagementResponse> {
    return this._http.post<BatteryExpenseManagementResponse>(
      this.baseUrl,
      expensesmanagement
    );
  }

  public putexpensesmanagement(
    expensesmanagement: BatteryExpenseManagementRequest
  ): Observable<BatteryExpenseManagementResponse> {
    return this._http.put<BatteryExpenseManagementResponse>(
      this.baseUrl,
      expensesmanagement
    );
  }

  public getRecentexpensesmanagement(
    filter?: string,
    pageInfo?: string,
  ): Observable<Page<BatteryExpenseManagementResponse>> {
    let url = `${this.baseUrlRecent}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this._http.get<Page<BatteryExpenseManagementResponse>>(url)
  }

  public getBusinessAreaList(  division: number
   
    ): Observable<Array<ExpenseManagementBusinessArea>> {
      return this._http.get<Array<ExpenseManagementBusinessArea>>(
        this.baseUrl + '/businessarea/'+ division
      );
    }
  
    public getBusinessPlaceList(  division: number
    
    ): Observable<Array<ExpenseManagementBusinessPlace>> {
      return this._http.get<Array<ExpenseManagementBusinessPlace>>(
        this.baseUrlBusinessPlace +'/'+ division 
      );
    }
  
  
    public getCostCenterList(  division: number
      
    ): Observable<Array<ExpenseManagementCostCenter>> {
      return this._http.get<Array<ExpenseManagementCostCenter>>(
        this.baseUrl + '/costcenter/' +division
      );
    }
    public getPurposeForList(division: number

      ): Observable<Array<ExpenseManagementExpensePurpose>> {
        return this._http.get<Array<ExpenseManagementExpensePurpose>>(
          this.baseUrl+'/expensepurpose/'+division
        );
      }
    
      public getTypeList(division: number
       
      ): Observable<Array<ExpenseManagementExpenseType>> {
        return this._http.get<Array<ExpenseManagementExpenseType>>(
          this.baseUrl + '/expensetype/'+division
        );
      }
      public getStatusForList(division: number
  
        ): Observable<Array<ExpenseManagementStatus>> {
          return this._http.get<Array<ExpenseManagementStatus>>(
            this.baseUrl + '/status/'+division
          );
        }

        public getSearchResult(searchparam): Observable<Array<BatteryExpenseManagementResponse>> {
          return this._http.get<Array<BatteryExpenseManagementResponse>>(`${this.baseUrl}/advancesearch`,{params:searchparam});
        }

  //////////////////////old/////////////////////////////





  public postsubmitforapproval(eid, bid,remrks): Observable<ExpenseManagementResponse> {

    return this._http.get<ExpenseManagementResponse>(`${this.baseUrl3}${eid}${'&businessDoaminId='}${bid}${'&remark='}${remrks}`);
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
    btExpenseManagementId: number
  ): Observable<Array<GLCodeResponse>> {
    return this._http.get<Array<GLCodeResponse>>(
      this.baseUrl + '/glcodesummary'+'/' + btExpenseManagementId
    );
  }
  // shagufta
  public postexpenseAccountsOnly(
    accountsonlyReq: BtExpenseAccountsOnlyRequest
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

// public statusForList():Observable<Array<string>>{
//   return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
// }
public statusForList():Observable<Array<string>>{
  return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
}
}
