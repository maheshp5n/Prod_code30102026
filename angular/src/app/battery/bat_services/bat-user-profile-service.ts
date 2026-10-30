import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProfileRequest, UINavigationModuleResponse } from "../../services/model";

@Injectable({providedIn:'root'})
export class BatUserProfileService{
    private baseurl='bt/api/userprofile';
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