import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class Sysconfigparamservice{
    private _baseurl='api/sysconfig';
    constructor(private _http:HttpClient){}
    public getTemplates():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}`);
    }
    public updateTemplate(templateid,data){
       return this._http.put(`${this._baseurl}/${templateid}`,{'body':data});
    }
    public saveTemplate(templatedata){
        return this._http.post(`${this._baseurl}`,templatedata);
    }
    public getSmsSetting():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}/smsserver`)
    }
    public postSmsServer(smsServerDetails:any){
        return this._http.post(`${this._baseurl}/smsserver`,smsServerDetails);
    }
    public updateSmsEnableDisable(id,flag){
        return this._http.put(`${this._baseurl}/smsserver/${id}/enabledisable?flag=${flag}`,{})
    }
    public updateSmsServersetting(id,data){
        return this._http.put(`${this._baseurl}/smsserver/${id}`,data);
    }
    public getSmsSettingHeaders(id):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}/smsserver/${id}/headers`);
    }
    public getSmsSettingParams(id):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}/smsserver/${id}/params`);
    }
    public getMailServers():Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}/mailserver`);
    }
    public saveMailServer(sData):Observable<any>{
        return this._http.post<any>(`${this._baseurl}/mailserver`,sData);
    }
    public updateMailerActive(id,flg):Observable<any>{
        return this._http.put(`${this._baseurl}/mailserver/${id}/enabledisable?flg=${flg}`,{});
    }
    public updateMailServer(id,data):Observable<any>{
        return this._http.put<any>(`${this._baseurl}/mailserver/${id}`,data);
    }
    public getMailerProperty(id):Observable<Array<any>>{
        return this._http.get<Array<any>>(`${this._baseurl}/mailserver/${id}/otherproperty`);
    }
}