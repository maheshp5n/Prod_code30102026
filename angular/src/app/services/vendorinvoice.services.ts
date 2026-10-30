import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { type } from 'os';
import { POType, VendorPOcategory, VendorInvoiceRequest, WccPendingInvoice, PODetails, vendorInvoiceResponse, Page, VendorCommRequest, VendorInvSAPreq } from './model';
import { catchError } from 'rxjs/operators';
@Injectable({ providedIn: 'root' })
export class VendorInvoiceService {
  constructor(private http: HttpClient) { }
  baseUrl: string = 'api/vendorinvoice';
  downloadFileURL = 'api/downloadFile';

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
    return this.http.put<vendorInvoiceResponse>(this.baseUrl + '/' + id, vencomreq);
  }

  public uploadFiles(file: File, data): Observable<Blob> {

    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    // fd.append("import-context", new Blob([`{"multiple":false,"createUpdate":null,"createMissingBundles":false,"createMissingMessages":false}`], { type: "multipart/form-data"}),"import-context");
    fd.append('data', data);
    const headers = new HttpHeaders({
      'Content-Type': 'multipart/form-data'
    });
    return this.http.post(this.baseUrl + '/invoicecopy', fd, { headers: headers, responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
  public vcuploadFiless(id: any, invoiceNumber: string, vnedorinvDate: any, remarks: string, fl: FormData): Observable<vendorInvoiceResponse> {
    return this.http.post<vendorInvoiceResponse>('api/vendorinvoice/invoicecopy/v1?wccid=' + id + '&vendorInvoiceNumber=' + invoiceNumber + '&vendorInvoiceDate=' + vnedorinvDate + '&remarks=' + remarks, fl);
  }
  public updateInvoiceSAP(id: number, sapreq: VendorInvSAPreq): Observable<vendorInvoiceResponse> {
    return this.http.put<vendorInvoiceResponse>(this.baseUrl + '/' + id + '/sap', sapreq);
  }
  public getFileList(id: number): Observable<Array<any>> {
    return this.http.get<Array<any>>('api/uploadedFiles?objectName=VendorInvoiceDetails&objectId=' + id)
  }
  public filedownload(fileName: string): Observable<Blob> {
    return this.http.get(`${this.downloadFileURL}/${fileName}`, { responseType: 'blob' });
  }
  public getDownloadFile(fileName: any): Observable<any> {
    return this.http.get(`api/downloadFile/${fileName}`, { responseType: 'blob' });
  }

  public updateVendorInvoice(invoice: any, file: FormData): Observable<any> {
    return this.http.put(`${this.baseUrl}/${invoice.id}/update?wccid=${invoice.wccId}&vendorInvoiceNumber=${invoice.vendorInvoiceNumber}&vendorInvoiceDate=${encodeURIComponent(invoice.vendorInvoiceDate)}&remarks=${encodeURIComponent(invoice.remarks)}`, file);
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

  public getExcelTemplate(): Observable<Blob> {
    return this.http.get(this.baseUrl+'/templateGRNUpdate', { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }

  public uploadFiles1(file: File): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', file, file.name);
    return this.http.put(this.baseUrl+'/'+'bulkupdatecommercials', fd, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
  }
  parseErrorblob1(error: HttpErrorResponse): Observable<any> {
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
