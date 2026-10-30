import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRoleResponse } from '../../services/model';

@Injectable({
  providedIn: 'root'
})
export class BtuserroleService {

  private baseurl = 'bt/api/userroles';
  constructor(private _http: HttpClient) {

  }
  public getUserRoleResponse(): Observable<Array<UserRoleResponse>> {
      return this._http.get<Array<UserRoleResponse>>(`${this.baseurl}`)
  }
  public createUserroles(userrole: UserRoleResponse): Observable<UserRoleResponse> {
      return this._http.post<UserRoleResponse>(`${this.baseurl}`, userrole);
  }
  public deleteRoles(role: string) {
      return this._http.delete(`${this.baseurl}?role=${encodeURI(role)}`);
  }
  public getRoleGroup(): Observable<Array<any>> {
      return this._http.get<Array<any>>(`${this.baseurl}/rolegroup`);
  }
  public getRoles(): Observable<Array<any>> {
      return this._http.get<Array<any>>(`${this.baseurl}/role`);
  }
  public updateRoleGroup(role: any) {
      return this._http.put(`${this.baseurl}/updaterolegroup`, role);
  }
}