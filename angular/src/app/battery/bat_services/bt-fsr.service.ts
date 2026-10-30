import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { batteryCommisionFsr, BtAssetMasterUpdate, BtEquipmentSrNO, BtFsrCommissionDataGetById, BtFsrData, BT_ProductForListDetail } from '../bt_model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BtFsrService {

  baseUrl: string = 'bt/api/fsr/ser';
  baseurlCommision:string = '/bt/api/fsr/com';
  

  constructor(private http: HttpClient) { }

  getSitesEnvironmentaList(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/siteEnCon');
  }

  getBtCoolingMethod(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/coolMeth');

  }
  getBtPowerPlantMake(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/pwrplmk');

  }
  getBtResolutionMethod(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/resMeth');

  }
  getBtPendingReasonCustomerBucket(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/penrsn');

  }
  getBtSolutionImparted(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/forlist/solImp');

  }
  getBtSiteType() {
    return this.http.get<any>(this.baseUrl + '/forlist/siteType');
  }

  getActiveAlarm(){
    return this.http.get<any>(this.baseUrl + '/forlist/actAlarm');
  }
  searchForPartcode(partcode: number,equipmentSrNo:string):Observable<Array<any>>{
    return this.http.get<any>(this.baseUrl + `/forlist/asset?partcode=${partcode}&equipmentSrNo=${equipmentSrNo}`);
  }
  saveFsr(fsrData:BtFsrData ):Observable<any> {
    return this.http.post<any>(this.baseUrl, fsrData);
  }
  getBtPendingReasonCustomerBucketforcomm(): Observable<any> {
    return this.http.get<any>(this.baseurlCommision + '/forlist/penrsn');

  }
  savecommisionFsr(fsrComm: batteryCommisionFsr):Observable<any> {
    return this.http.post<any>(this.baseurlCommision, fsrComm);
  }
  EditFsr(url:string, id:number):Observable<any> {
    return this.http.get<any>(url+'/'+id);
  }
  UpdateCommFsr(fsrData:batteryCommisionFsr,id):Observable<any>{
    return this.http.put<any>(this.baseurlCommision+`/${id}`,fsrData);
  }
  UpdateServiceFsr(fsrData:BtFsrData,id):Observable<any>{
    return this.http.put<any>(this.baseUrl+`/${id}`,fsrData);
  }
  public createPDFFsr(url:string, idName: string, id: number): Observable<any> {


    return this.http.get(`${url}${'/pdf?'}${idName}${'='}${id}`, { responseType: 'blob' }).pipe(catchError(this.parseErrorblob));
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
  public getSmallForlist(partCode):Observable<Array<BT_ProductForListDetail>>{
    return this.http.get<Array<BT_ProductForListDetail>>(`${this.baseUrl}/forlist/partcode?searchkey=${partCode}`);
}

}
