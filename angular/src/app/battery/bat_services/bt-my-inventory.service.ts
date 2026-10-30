import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../services/model';
import { BtMyInventoryResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtMyInventoryService {

  baseUrl = 'bt/api/invnetory';

  constructor(private _http : HttpClient) { }

  public btAllInventory(filter): Observable<Page<BtMyInventoryResponse>> {
    return this._http.get<Page<BtMyInventoryResponse>>(`${this.baseUrl}?${filter}`); 
  }
  public getSearchResult(searchparam): Observable<Array<BtMyInventoryResponse>> {
    return this._http.get<Array<BtMyInventoryResponse>>(`${this.baseUrl}/search`, { params: searchparam });
  }
}
