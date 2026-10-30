import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BatteryFSRResponse, CaseResponse, ClaimStatus, FileUploadResponse, LocationProductItemForList, Page, PendingResponsibility, ProductConsumptionType, SecondServiceAppointmentRequest, ServiceAppointmentProductConsumedRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentReAssignRequest, ServiceAppointmentRequest, ServiceAppointmentResponse, ServiceAppointmentStatus, ServiceResourseResponse, ToBeApprovedHistoryResponse } from '../../services/model';
import { anotherSA, BtSAServicePayout, BtServiceAppointmentProductConsumedResponse, btServiceAppointmentRequest, BTServiceAppointmentResponse, BtServiceAppointmentResponseSchOnly, btServiceAppointmentResponseWithSCH } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtServiceappointmentService {
  constructor(private _http: HttpClient) { }
   baseUrl:string='bt/api/serviceappointment';
   byclaimstatus:string='bt/api/serviceappointment/byclaimstatus';
   recentUrl:string='bt/api/serviceappointment/recentvisit';
   bystatusurl:string='bt/api/serviceappointment/bystatus';
    public getAllServiceAppointMent(filter?:string,pageInfo?:string): Observable<Page<ServiceAppointmentResponse>> {
        let url = `${this.baseUrl}?`;
        if (filter) {
    
            url = `${url}&${filter}`;
           
        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        } 
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
        return this._http.get<Page<ServiceAppointmentResponse>>(url);
    }
    public getServiceAppointmentById(id: number): Observable<btServiceAppointmentResponseWithSCH> {
        return this._http.get<btServiceAppointmentResponseWithSCH>(`${this.baseUrl}/res/${id}`);
    }
    public postServiceAppoinment(request: btServiceAppointmentRequest): Observable<BTServiceAppointmentResponse> {
        return this._http.post<BTServiceAppointmentResponse>(`${this.baseUrl}`, request);
    }
    public postSecondServiceAppoinment(request: SecondServiceAppointmentRequest): Observable<SecondServiceAppointmentRequest> {
        return this._http.post<SecondServiceAppointmentRequest>(`${this.baseUrl}`, request);
    }
    public updateServiceAppointment(id : number,request: btServiceAppointmentRequest): Observable<BTServiceAppointmentResponse> {
        return this._http.put<BTServiceAppointmentResponse>(`${this.baseUrl}/${id}`, request);
    }

    public getServiceAppointmentStatus(): Observable<Array<ServiceAppointmentStatus>> {
        return this._http.get<Array<ServiceAppointmentStatus>>(`${this.baseUrl}/status`);
    }
    public getPendingResponsibility(): Observable<Array<PendingResponsibility>> {
        return this._http.get<Array<PendingResponsibility>>(`${this.baseUrl}/pendingresponsibility`);
    }
    public getClaimStatus(): Observable<Array<ClaimStatus>> {
        return this._http.get<Array<ClaimStatus>>(`${this.baseUrl}/claimstatus`);
    }
    public getCaseById(caseNo: number): Observable<CaseResponse> {
        return this._http.get<CaseResponse>(`bt/api/case/${caseNo}`);
    }
    public getCaseLists(): Observable<Array<CaseResponse>> {
        return this._http.get<Array<CaseResponse>>(`bt/api/case/caseforlist`);
    }
    public getProductConsumedType(): Observable<Array<ProductConsumptionType>> {
        return this._http.get<Array<ProductConsumptionType>>(`${this.baseUrl}/productconsumedtype`);
    }
    public postconsumeProductDetails(id: number, request: ServiceAppointmentProductConsumedRequest): Observable<ServiceAppointmentProductConsumedRequest> {
        return this._http.post<ServiceAppointmentProductConsumedRequest>(`${this.baseUrl}/${id}/consumed`, request);
    }
    public getconsumeProductDetails(id: number, page: number): Observable<Page<BtServiceAppointmentProductConsumedResponse>> {
        return this._http.get<Page<BtServiceAppointmentProductConsumedResponse>>(`${this.baseUrl}/${id}/consumed` + '?page=' + page + '&size=5&');
    }
    public getOkProduct(id: number): Observable<Array<LocationProductItemForList>> {
        return this._http.get<Array<LocationProductItemForList>>(`${this.baseUrl}/${id}/okproduct`);
    }
    public getOkproductSerialNumber(id: number): Observable<Array<string>> {
        return this._http.get<Array<string>>(`${this.baseUrl}/okproductserialnumber/${id}`);
    }

    public getTimeList(): Observable<Array<string>> {
        return this._http.get<Array<string>>(`${this.baseUrl}/time`);
    }
    public getServiceResourceByAppointmentId(id: number, page: number): Observable<Page<ServiceResourseResponse>> {
        return this._http.get<Page<ServiceResourseResponse>>(`${this.baseUrl}/related/${id}` + '?page=' + page + '&size=5&');
    }
   
    public  getSchDetailsById(serviceappointmentId,pageInfo): Observable<Page<BtServiceAppointmentResponseSchOnly>> {
        // console.log(filter);
        console.log(pageInfo);

        // let url = `${this.baseUrl}?`;
        // if (filter) {
    
        //     url = `${url}&${filter}`;
           
        // }
        // if (pageInfo) {
        //     url = `${url}&${pageInfo}`;
        // } 
        return this._http.get<Page<BtServiceAppointmentResponseSchOnly>>(`${this.baseUrl}/sch/${serviceappointmentId}?${pageInfo}`);
    }
    public getBatteryFSRByserviceId(id: number, page: number): Observable<Page<BatteryFSRResponse>> {
        return this._http.get<Page<BatteryFSRResponse>>(`${this.baseUrl}/related/${id}` + '/BatteryFSR' + '?page=' + page + '&size=5&');
    }
    public reAssignServiceAppoinment(request: ServiceAppointmentReAssignRequest): Observable<ServiceAppointmentReAssignRequest> {
        return this._http.post<ServiceAppointmentReAssignRequest>(`${this.baseUrl}/reassign`, request);
    }
    public getApprovalHistory(objectName: string, objectId: number): Observable<Array<ToBeApprovedHistoryResponse>> {
        return this._http.get<Array<ToBeApprovedHistoryResponse>>(`bt/api/approvalhistory` + '?objectName=' + objectName + '&objectId=' + objectId);
    }

    public postUploadMultipleFiles(objectName: string, objectId, formData: FormData): Observable<any> {
        return this._http.post<any>('bt/api/uploadFile?objectName=' + objectName + '&objectId=' + objectId, formData);
    }
    public getFileList(objectName: string, objectId: number): Observable<Array<FileUploadResponse>> {
        return this._http.get<Array<FileUploadResponse>>(`bt/api/uploadedFiles` + '?objectName=' + objectName + '&objectId=' + objectId);
    }
    public getDownloadFile(fileName: any): Observable<any> {
        return this._http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
    }
    public postRequestForSA(caseId,remarks): Observable<any> {

        return this._http.post<any>(`${this.baseUrl}/anotherserviceappointment?caseId=`+caseId+'&remark='+remarks, {});
    }
    
  public deleteProductConsumed(id: number): Observable<Object> {
    return this._http.delete<Object>(this.baseUrl + '/deleteconsumed/' + id);
  }
public getAdvanceSearchResult(searchparam):Observable<Array<ServiceAppointmentResponse>>{
    return this._http.get<Array<ServiceAppointmentResponse>>(`${this.baseUrl}/advancesearch?sanumber`,{params:searchparam});
}
  public getStatusForList():Observable<Array<string>>{
   return this._http.get<Array<string>>(this.baseUrl+'/status/forlist')
  }
  public createPDF(idName: string, id: number): Observable<any> {

    return this._http.get(`${this.baseUrl}${'/pdf?'}${idName}${'='}${id}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  public getListOfservicePayout(): Observable<Array<BtSAServicePayout>>{
      return this._http.get<Array<BtSAServicePayout>>(`${this.baseUrl}/forlist/servicePayout`);
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
  public getIdanotherServiceAppointmentBy(id: number): Observable<anotherSA> {
    return this._http.get<anotherSA>(`${this.baseUrl}/anotherserviceappointment/${id}`);
}
public uploadFsr(id: any,file:File): Observable<any> {
    // const file: FormData = new FormData();
    // file.append('files', files, files.name);
    console.log(file);
    return this._http.post(this.baseUrl+'/fsr/fileupload/' + id, file, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
}
