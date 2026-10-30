import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceAppointmentStatus, PendingResponsibility, ClaimStatus, CaseResponse, Page, ServiceAppointmentRequest, ServiceAppointmentResponse, ServiceAppointmentProductConsumedRequest, ServiceAppointmentProductConsumedResponse, LocationProductItemForList, ServiceResourseResponse, ProductConsumptionType, BatteryFSRResponse, ServiceAppointmentReAssignRequest, SecondServiceAppointmentRequest, ToBeApproved, ToBeApprovedHistoryResponse, FileUploadResponse, ServiceResourseForList, ServiceResourseVendor, ServiceAppointmentAssignToRequest, ServiceAppointmentUnAssignToRequest, LptransferOkSRN_details } from './model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ServiceAppointmentService {

    constructor(private _http: HttpClient) { }
   baseUrl:string='api/serviceappointment';
   byclaimstatus:string='api/serviceappointment/byclaimstatus';
   recentUrl:string='api/serviceappointment/recentvisit';
   bystatusurl:string='api/serviceappointment/bystatus';
   vendorbaseurl:string='api/vendor';
    public getAllServiceAppointMent(filter?:string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        } 
        //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        return this._http.get<Page<ServiceAppointmentResponse>>(url);
    }
    public getRecentServiceAppointMent(filter?:string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
        let url = `${this.recentUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        } 
        //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        return this._http.get<Page<ServiceAppointmentResponse>>(url);
    }
    public getServiceAppointmentListByClaimStatus( filter?: string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
        let url = `${this.byclaimstatus}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        } 
        //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        return this._http.get<Page<ServiceAppointmentResponse>>(url);
    }
    public getServiceAppointmentListByStatus(filter?: string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
        let url = `${this.bystatusurl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        } 
        //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
        // return this._http.get<Page<ServiceAppointmentResponse>>('api/serviceappointment/bystatus' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
        return this._http.get<Page<ServiceAppointmentResponse>>(url);
    }
    public getServiceAppointmentById(id: number): Observable<ServiceAppointmentResponse> {
        return this._http.get<ServiceAppointmentResponse>(`api/serviceappointment/${id}`);
    }
    public postServiceAppoinment(request: ServiceAppointmentRequest): Observable<ServiceAppointmentRequest> {
        return this._http.post<ServiceAppointmentRequest>(`api/serviceappointment`, request);
    }
    public postSecondServiceAppoinment(request: SecondServiceAppointmentRequest): Observable<SecondServiceAppointmentRequest> {
        return this._http.post<SecondServiceAppointmentRequest>(`api/serviceappointment`, request);
    }
    public updateServiceAppointment(request: ServiceAppointmentRequest): Observable<ServiceAppointmentRequest> {
        return this._http.put<ServiceAppointmentRequest>(`api/serviceappointment`, request);
    }

    public getServiceAppointmentStatus(): Observable<Array<ServiceAppointmentStatus>> {
        return this._http.get<Array<ServiceAppointmentStatus>>('api/serviceappointment/status');
    }
    public getPendingResponsibility(): Observable<Array<PendingResponsibility>> {
        return this._http.get<Array<PendingResponsibility>>('api/serviceappointment/pendingresponsibility');
    }
    public getClaimStatus(): Observable<Array<ClaimStatus>> {
        return this._http.get<Array<ClaimStatus>>('api/serviceappointment/claimstatus');
    }
    public getCaseById(caseNo: number): Observable<CaseResponse> {
        return this._http.get<CaseResponse>(`api/case/${caseNo}`);
    }
    public getCaseLists(): Observable<Array<CaseResponse>> {
        return this._http.get<Array<CaseResponse>>(`api/case/caseforlist`);
    }
    public getProductConsumedType(): Observable<Array<ProductConsumptionType>> {
        return this._http.get<Array<ProductConsumptionType>>('api/serviceappointment/productconsumedtype');
    }
    public postconsumeProductDetails(id: number, request: ServiceAppointmentProductConsumedRequest): Observable<ServiceAppointmentProductConsumedRequest> {
        return this._http.post<ServiceAppointmentProductConsumedRequest>(`api/serviceappointment/${id}/consumed`, request);
    }
    public getconsumeProductDetails(id: number, page: number): Observable<Page<ServiceAppointmentProductConsumedResponse>> {
        return this._http.get<Page<ServiceAppointmentProductConsumedResponse>>(`api/serviceappointment/${id}/consumed` + '?page=' + page + '&size=5&');
    }
    public getOkProduct(id: number): Observable<Array<LocationProductItemForList>> {
        return this._http.get<Array<LocationProductItemForList>>(`api/serviceappointment/${id}/okproduct`);
    }
    public getOkproductSerialNumber(id: number): Observable<Array<string>> {
        return this._http.get<Array<string>>(`api/serviceappointment/okproductserialnumber/${id}`);
    }

    public getTimeList(): Observable<Array<string>> {
        return this._http.get<Array<string>>(`api/serviceappointment/time`);
    }
    public getServiceResourceByAppointmentId(id: number, page: number): Observable<Page<ServiceResourseResponse>> {
        return this._http.get<Page<ServiceResourseResponse>>(`api/serviceappointment/related/${id}` + '?page=' + page + '&size=5&');
    }
    public getServiceResourceListForAssignTo():Observable<Array<ServiceResourseVendor>>{
        return this._http.get<Array<ServiceResourseVendor>>('api/vendor/forlist');
      }  
    public getBatteryFSRByserviceId(id: number, page: number): Observable<Page<BatteryFSRResponse>> {
        return this._http.get<Page<BatteryFSRResponse>>(`api/serviceappointment/related/${id}` + '/BatteryFSR' + '?page=' + page + '&size=5&');
    }
    public reAssignServiceAppoinment(request: ServiceAppointmentReAssignRequest): Observable<ServiceAppointmentReAssignRequest> {
        return this._http.post<ServiceAppointmentReAssignRequest>(`api/serviceappointment/reassign`, request);
    }
    public getApprovalHistory(objectName: string, objectId: number): Observable<Array<ToBeApprovedHistoryResponse>> {
        return this._http.get<Array<ToBeApprovedHistoryResponse>>(`api/approvalhistory` + '?objectName=' + objectName + '&objectId=' + objectId);
    }

    public postUploadMultipleFiles(objectName: string, objectId, formData: FormData): Observable<any> {
        return this._http.post<any>('api/uploadFile?objectName=' + objectName + '&objectId=' + objectId, formData);
    }
    public getFileList(objectName: string, objectId: number): Observable<Array<FileUploadResponse>> {
        return this._http.get<Array<FileUploadResponse>>(`api/uploadedFiles` + '?objectName=' + objectName + '&objectId=' + objectId);
    }
    public getDownloadFile(fileName: any): Observable<any> {
        return this._http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
    }

    // public postRequestForSA(caseId): Observable<any> {
    //     return this._http.post<any>('api/serviceappointment?caseId=' + caseId);
    // }

    public postRequestForSA(caseId,remarks): Observable<any> {

        return this._http.post<any>('api/serviceappointment/anotherserviceappointment?caseId='+caseId+'&remark='+remarks, {});
    }
    
  public deleteProductConsumed(id: number): Observable<Object> {
    return this._http.delete<Object>(this.baseUrl + '/deleteconsumed/' + id);
  }
  public getAdvanceSearchResult(data):Observable<Array<ServiceAppointmentResponse>>{
      return this._http.get<Array<ServiceAppointmentResponse>>(`${this.baseUrl}/advancesearch?sanumber=${data.sanumber}&casenumber=${data.casenumber}&site=${data.site}&ctype=${data.ctype}&creason=${data.creason}&circle=${data.circle}&assets=${data.assets}&status=${data.status}&cstatus=${data.cstatus}&srs=${data.srs}&crdf=${data.crdf}&crdt=${data.crdt}`);
  }
  public getStatusForList():Observable<Array<string>>{
   return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
  }
  public createPDF(idName: string, id: number): Observable<any> {

    return this._http.get(`${this.baseUrl}${'/pdf?'}${idName}${'='}${id}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  parseErrorblob(error: HttpErrorResponse): Observable<any> {
    const reader: FileReader = new FileReader();
    const obs = Observable.create((observer: any) => {
      reader.onloadend = (e) => {
        observer.error(JSON.parse(reader.result.toString()));
        observer.complete();
      }
    });
    reader.readAsText(error.error);
    return obs;
  }
  public unAssignSa(serviceAppointmentUnAssignToRequest:ServiceAppointmentUnAssignToRequest):Observable<any>
  {
      console.log(serviceAppointmentUnAssignToRequest);
      
      return this._http.put<ServiceAppointmentAssignToRequest>('api/vendor/unassign/',serviceAppointmentUnAssignToRequest);
  }





  //////////////////////For Vendor Service Appointment///////////////////////////

  public getAllServiceAppointMentvendor(filter?:string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
    let url = `${this.vendorbaseurl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    } 
    //return this._http.get<Page<AccountCreate>>(`api/accounts?page=${page}`);
    return this._http.get<Page<ServiceAppointmentResponse>>(url);
}

public assignToSelf(serviceAppointmentId):Observable<any>
{
    console.log(serviceAppointmentId);
    
    return this._http.put<any>('api/vendor/assign/'+serviceAppointmentId,'{}');
}
public assignTo(serviceAppointmentAssignToRequest):Observable<any>
{
    console.log(serviceAppointmentAssignToRequest);
    
    return this._http.put<ServiceAppointmentAssignToRequest>('api/vendor/assignto/',serviceAppointmentAssignToRequest);
}
public getOKSRNDetails(serial: string,): Observable<Array<LptransferOkSRN_details>> {
    return this._http.get<Array<LptransferOkSRN_details>>( this.baseUrl+`/getOkSRNDetails/${serial}`);
}
}
