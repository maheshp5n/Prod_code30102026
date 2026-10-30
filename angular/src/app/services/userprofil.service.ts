import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { ProfileRequest, UINavigationModuleResponse } from './model';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class UserprofileService{
    private baseurl='api/userprofile';
    constructor(private _http:HttpClient){}
    public postProfile(profileRequest:ProfileRequest):Observable<ProfileRequest>{
        return this._http.post<ProfileRequest>(`${this.baseurl}`,profileRequest);
    }
    public updateProfile(profileId:number,profileRequest:ProfileRequest):Observable<ProfileRequest>{
        return this._http.put<ProfileRequest>(`${this.baseurl}/${profileId}`,profileRequest);
    }
    public getUINavigationByProfileid(profileId:number):Observable<Array<UINavigationModuleResponse>>{
        return this._http.get<Array<UINavigationModuleResponse>>(`${this.baseurl}/${profileId}/navigations`);
    }
    public getAllNavigation():Observable<Array<UINavigationModuleResponse>>{
        return this._http.get<Array<UINavigationModuleResponse>>(`${this.baseurl}/navigations`);
    }
}