import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ChangePassword } from './model';

@Injectable()
export class ChangePasswordService {

  constructor(private _http: HttpClient) { }
  public changePassword(changePassword: ChangePassword) {
    return this._http.post('api/users/changepassword', changePassword);
  }

}
