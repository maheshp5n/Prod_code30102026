import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateLoginRequest, ObjectAccessResponse, Page, ResetpasswordRequest, UserForList, UserProfile, UserResponse, userRole } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BatuserregistrationService {
  private baseurl='bt/api/users';
    constructor(private _http:HttpClient){}
    public getUserForList(contactId:number):Observable<Array<UserForList>>{
        return this._http.get<Array<UserForList>>(`${this.baseurl}/forlist/${contactId}`);
    }
    public getUsers(pageInfo?:string):Observable<Page<UserResponse>>{
        let url=this.baseurl;
        if(pageInfo){
            url=`${url}?${pageInfo}`
        }
        return this._http.get<Page<UserResponse>>(`${url}`);
    }
    public enableDisableUsers(username:string,flag:boolean){
        return this._http.put(`${this.baseurl}/enabledisable?useremailid=${username}&fflag=${flag}`,{});
    }
    public getUserProfile():Observable<Array<UserProfile>>{
        return this._http.get<Array<UserProfile>>(`${this.baseurl}/profile`);
    }
    public getObjectAccessByProfileId(profileId:number):Observable<Array<ObjectAccessResponse>>{
        return this._http.get<Array<ObjectAccessResponse>>(`${this.baseurl}/profile/${profileId}`);
    }
    public getObjectAccessByprofileByUserId(userid:string):Observable<Array<ObjectAccessResponse>>{
        return this._http.get<Array<ObjectAccessResponse>>(`${this.baseurl}/profile/user?userid=${userid}`);
    }
    public resetPassword(resetRequest:ResetpasswordRequest){
        return this._http.put(`${this.baseurl}/resetpassword`,resetRequest);
    }
    public getUserByuserId(userId:string):Observable<UserResponse>{
        return this._http.get<UserResponse>(`${this.baseurl}/${userId}`);
    }
    public getSearchResult(searchparam): Observable<Array<UserResponse>> {
        return this._http.get<Array<UserResponse>>(`${this.baseurl}/advancesearch`, {params:searchparam});
      }
      public getUserForList2(): Observable<Array<UserForList>> {
        return this._http.get<Array<UserForList>>(this.baseurl+'/forlist');
      }
      public getAuthorities(): Observable<Array<userRole>> {
        return this._http.get<Array<userRole>>(`${this.baseurl}/authorities`);
    }
}
