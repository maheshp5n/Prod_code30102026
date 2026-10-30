import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import {
    BRCRepairBERRemark, PackingCondition, FaultObserved, BRCRepiarStatus, BRCRepairType, BRCRepiarBalancedAt,
    BRCRepiarResolutionMethod, BRCRepairInventoryReceiveRequest, BRCRepairInventoryReceiveItemRequest,
    BRCRepairInventoryDispatchRequest, BRCRepariInventoryProductConsumedRequest, Page, BRCInventoryReceiveResponse,
    ServiceResourseForList, BRCRepairInventoryForList, BRCRepariInventoryProductConsumedResponse
} from './model';
import { Observable } from 'rxjs';

@Injectable()
export class ManageInventoryService {
    FileUploadUrl='api/brcrepair/bulkuploadMI';
    excelTemplateURL='api/brcrepair/templateExcelMI'
    constructor(private _http: HttpClient) { }

    public getMIDetails(page: number, column: string, order: string): Observable<Page<BRCInventoryReceiveResponse>> {
        return this._http.get<Page<BRCInventoryReceiveResponse>>('api/brcrepair' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getRecentVisit(page: number, column: string, order: string): Observable<Page<BRCInventoryReceiveResponse>> {
        return this._http.get<Page<BRCInventoryReceiveResponse>>('api/brcrepair/recentVisit' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getMIByMiId(receivingid: number): Observable<BRCInventoryReceiveResponse> {
        return this._http.get<BRCInventoryReceiveResponse>(`api/brcrepair/${receivingid}`);
    }
    public getRemarks(): Observable<Array<BRCRepairBERRemark>> {
        return this._http.get<Array<BRCRepairBERRemark>>('api/brcrepair/dropdown/berremarks');
    }
    public getPackingCondation(): Observable<Array<PackingCondition>> {
        return this._http.get<Array<PackingCondition>>('api/brcrepair/dropdown/packingcondition');
    }
    public getPhysicalDamage(): Observable<Array<any>> {
        return this._http.get<Array<any>>('api/brcrepair/dropdown/physicaldamage');
    }
    public getFaultObserved(): Observable<Array<FaultObserved>> {
        return this._http.get<Array<FaultObserved>>('api/brcrepair/dropdown/faultobserved');
    }
    public getStatus(): Observable<Array<BRCRepiarStatus>> {
        return this._http.get<Array<BRCRepiarStatus>>('api/brcrepair/dropdown/status');
    }
    public getTypes(): Observable<Array<BRCRepairType>> {
        return this._http.get<Array<BRCRepairType>>('api/brcrepair/dropdown/types');
    }
    public getBalanceAt(): Observable<Array<BRCRepiarBalancedAt>> {
        return this._http.get<Array<BRCRepiarBalancedAt>>('api/brcrepair/dropdown/balanceat');
    }
    public getResulutionMethods(): Observable<Array<BRCRepiarResolutionMethod>> {
        return this._http.get<Array<BRCRepiarResolutionMethod>>('api/brcrepair/dropdown/resolutionmethods');
    }

    public saveRequest(request: BRCRepairInventoryReceiveRequest): Observable<BRCRepairInventoryReceiveRequest> {
        return this._http.post<BRCRepairInventoryReceiveRequest>(`api/brcrepair`, request);
    }
    public updateRequest(request: BRCRepairInventoryReceiveRequest): Observable<BRCRepairInventoryReceiveRequest> {
        return this._http.put<BRCRepairInventoryReceiveRequest>(`api/brcrepair`, request);
    }
    // public assignToEng(request: BRCRepairInventoryReceiveItemRequest, receivingid: number): Observable<BRCRepairInventoryReceiveItemRequest> {
    //     return this._http.post<BRCRepairInventoryReceiveItemRequest>(`api/brcrepair/jobcard/${receivingid}/assigntoeng`, request);
    // }
    public assignToEng(receivingId: number, serviceResourceId: number): Observable<BRCRepairInventoryReceiveItemRequest> {
        return this._http.post<BRCRepairInventoryReceiveItemRequest>(`api/brcrepair/jobcard/assigntoeng` + '?receivingId=' + receivingId + '&serviceResourceId=' + serviceResourceId, null);
    }
    public receiveFromEng(request: BRCRepairInventoryReceiveItemRequest, repairid: number): Observable<BRCRepairInventoryReceiveItemRequest> {
        return this._http.post<BRCRepairInventoryReceiveItemRequest>(`api/brcrepair/jobcard/${repairid}/receivefromeng`, request);
    }
    public saveDispatch(request: BRCRepairInventoryDispatchRequest, repairid: number): Observable<BRCRepairInventoryDispatchRequest> {
        return this._http.post<BRCRepairInventoryDispatchRequest>(`api/brcrepair/${repairid}`, request);
    }
    public consumeProduct(request: BRCRepariInventoryProductConsumedRequest, repairid: number): Observable<BRCRepariInventoryProductConsumedRequest> {
        return this._http.post<BRCRepariInventoryProductConsumedRequest>(`api/brcrepair/${repairid}/productconsumed`, request);
    }

    public serviceResourceList(): Observable<Array<ServiceResourseForList>> {
        return this._http.get<Array<ServiceResourseForList>>(`api/serviceresource/forList`);
    }
    public repairInventoryForList(): Observable<Array<BRCRepairInventoryForList>> {
        return this._http.get<Array<BRCRepairInventoryForList>>(`api/brcrepair/forList`);
    }
    public getConsumeRelatedList(receivingId: number, page: number): Observable<Page<BRCRepariInventoryProductConsumedResponse>> {
        return this._http.get<Page<BRCRepariInventoryProductConsumedResponse>>(`api/brcrepair/productconsumed/related/${receivingId}` + '?page=' + page + '&size=5&');
    }

    public getServiceResourceListByLegalEntity(legalEntityId: number): Observable<Array<ServiceResourseForList>> {
        return this._http.get<Array<ServiceResourseForList>>(`api/serviceresource/forlist/${legalEntityId}`);
    }

    public getByUser(page: number, column: string, order: string): Observable<Page<BRCInventoryReceiveResponse>> {
        return this._http.get<Page<BRCInventoryReceiveResponse>>('api/brcrepair/getbyuser' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getByTyper(page: number, column: string, order: string, type: string): Observable<Page<BRCInventoryReceiveResponse>> {
        return this._http.get<Page<BRCInventoryReceiveResponse>>('api/brcrepair/getbytype' + '?type=' + type + '&page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getByUserAndType(page: number, column: string, order: string, type: string): Observable<Page<BRCInventoryReceiveResponse>> {
        return this._http.get<Page<BRCInventoryReceiveResponse>>('api/brcrepair/getbyuserandtype' + '?type=' + type + '&page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    public getProductConsumed(page: number, column: string, order: string): Observable<Page<BRCRepariInventoryProductConsumedResponse>> {
        return this._http.get<Page<BRCRepariInventoryProductConsumedResponse>>('api/brcrepair/productconsumed' + '?page=' + page + '&size=20&' + "sort=" + column + "," + order);
    }
    // bulk upload by shagufta
    public uploadFiles(file:File) : Observable<any>  { 
    const fd: FormData = new FormData();
    fd.append('file', file,file.name);
     return this._http.post('api/brcrepair/bulkuploadMI',fd ,{responseType: 'blob'});
}
public getExcelTemplate():Observable<Blob>{
    return this._http.get(`${this.excelTemplateURL}`,{ responseType: 'blob' });
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

}