import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Page, PODetails, POType, VendorCommRequest, VendorInvoiceRequest, vendorInvoiceResponse, VendorInvSAPreq, VendorPOcategory, WccPendingInvoice } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtInvoiceService {
  constructor(private http: HttpClient) { }
  baseUrl: string = 'bt/api/invoice';
  downloadFileURL = 'api/downloadFile';
  dctbaseUrl: string = 'api/vendorinvoice';
  

  public getPOTypes(): Observable<Array<POType>> {
    return this.http.get<Array<POType>>(this.baseUrl + '/potypes');
  }
  public getPOCategory(): Observable<Array<VendorPOcategory>> {
    return this.http.get<Array<VendorPOcategory>>(this.baseUrl + '/pocategory');
  }
  public getPOByTypeId(typeid: number): Observable<Array<PODetails>> {
    return this.http.get<Array<PODetails>>(this.baseUrl + '/po' + '/' + typeid);
  }
  public getPendingInvoice(): Observable<Array<WccPendingInvoice>> {
    return this.http.get<Array<WccPendingInvoice>>(this.baseUrl + '/wccpendingtoinvoice');
  }
  public saveInvoice(request: VendorInvoiceRequest): Observable<vendorInvoiceResponse> {
    return this.http.post<vendorInvoiceResponse>(this.baseUrl, request);

  }
  public getVenInvoice(filter?: string, pageInfo?:string): Observable<Page<vendorInvoiceResponse>> {
    let url = `${this.baseUrl}?`;
    if (filter) {

        url = `${url}&${filter}`;
       
    }
    if (pageInfo) {
        url = `${url}&${pageInfo}`;
    }
    return this.http.get<Page<vendorInvoiceResponse>>(url);
  }
  public getVendInvoiceById(id: number): Observable<vendorInvoiceResponse> {
    return this.http.get<vendorInvoiceResponse>(this.baseUrl + '/' + id);
  }
  public postVendorInvGRN(id: number, vencomreq: VendorCommRequest): Observable<vendorInvoiceResponse> {
    return this.http.put<vendorInvoiceResponse>(this.baseUrl + '/' + id+'/grn', vencomreq);
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
  public vcuploadFiless(fd): Observable<any> { 
     return this.http.post(`${this.baseUrl}/invoicecopy`,fd);
     
   }
  public updateInvoiceSAP(id: number, sapreq: VendorInvSAPreq): Observable<vendorInvoiceResponse> {
    return this.http.put<vendorInvoiceResponse>(this.baseUrl + '/' + id + '/sap', sapreq);
  }
  public postInvoiceSAP(id: number, sapreq: VendorInvSAPreq): Observable<vendorInvoiceResponse> {
    return this.http.post<vendorInvoiceResponse>(this.baseUrl + '/sap', sapreq);
  }
  public getFileList(id: number): Observable<Array<any>> {
    return this.http.get<Array<any>>('api/uploadedFiles?objectName=BtVendorInvoiceDetails&objectId=' + id)
  }
  public filedownload(fileName: string): Observable<Blob> {
    return this.http.get(`${this.downloadFileURL}/${fileName}`, { responseType: 'blob' });
  }
  public getDownloadFile(fileName: any): Observable<any> {
    return this.http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
  }
  public updateVendorInvoice(id:number,f1 :FormData): Observable<any> {
    return this.http.put(`${this.baseUrl}/invoicecopy/${id}`, f1);
  }

  public submitForApproval(id): Observable<any> {
    return this.http.post(`${this.baseUrl}/submitforapproval/${id}`, {});
  }

  public getInternalServiceCode(): Observable<Array<any>> {
    return this.http.get<Array<any>>(`${this.baseUrl}/internalserviceocode`);
  }
  public getSearchResult(searchparam): Observable<Array<vendorInvoiceResponse>> {
    return this.http.get<Array<vendorInvoiceResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
  }
  public getStatusForList():Observable<Array<string>>{
    return this.http.get<Array<string>>(this.baseUrl+'/status/forlist');
  }

  // public uploadFiles(fs:FormData,s:Function,e:Function){
  //   const xhr=new XMLHttpRequest();
  //   xhr.addEventListener("readystatechange", function() {
  //     if(this.readyState === 4) {
  //       s(xhr.response);
  //     }
  //     else{
  //       e(xhr.response);
  //     }
  //   });
  //   xhr.open('POST','/bt/api/invoice/invoicecopy');
  //   xhr.send(fs);
  // }
}
