import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChangeOwnerRequest, LocationFaultyItemResponse, LocationForList, LocationPartCodeSerialNumber, LocationProductItemForList, LocationProductItemResponse, LocationProductRequestResponse, LocationRequest, LocationResponse, LocationType, LPRequest, LPReturnOrderRequest, Page, ServiceCenterForList, WarehousedetailsRequest, WarehousedetailsResponse } from '../../services/model';
import { BtLocationProductItemResponse, BtLocationProductRequestResponse } from '../bt_model';

@Injectable({
  providedIn: 'root'
})
export class BtwarehouseService {

  baseUrl = 'bt/api/location';
    recentVisitUrl = this.baseUrl+'/recentvisit';
    // forListUrl = 'api/location/forlist';
    // typeForlist = 'api/location/type';

    constructor(private http: HttpClient) { }
    public saveLocation(location: LocationRequest): Observable<LocationRequest> {
        return this.http.post<LocationRequest>(this.baseUrl, location);

    }
    public getLocation(filter?: string, pageInfo?: string): Observable<Page<LocationResponse>> {
        let url = `${this.baseUrl}?`;
        if (filter) {

            url = `${url}&${filter}`;

        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this.http.get<Page<LocationResponse>>(url);


    }
    public getLocationrecentvisit(filter?: string, pageInfo?: string): Observable<Page<LocationResponse>> {
        let url = `${this.recentVisitUrl}?`;
        if (filter) {

            url = `${url}&${filter}`;

        }
        if (pageInfo) {
            url = `${url}&${pageInfo}`;
        }
        return this.http.get<Page<LocationResponse>>(url);


    }
    public getLocationById(id: number): Observable<LocationResponse> {
        return this.http.get<LocationResponse>(`${this.baseUrl}/${id}`);
    }
    public updateLocation(request: LocationRequest, locationid: number): Observable<LocationRequest> {

        return this.http.put<LocationRequest>(`${this.baseUrl}/${locationid}`, request);

    }
    public getProductItemByLocation(locationId: number): Observable<Array<LocationProductItemForList>> {
        return this.http.get<Array<LocationProductItemForList>>(`${this.baseUrl}/${locationId}/product/forlist`);
    }
    public getRootLocation(locationid: string): Observable<LocationResponse> {
        return this.http.get<LocationResponse>(this.baseUrl + '/' + locationid + '/rootlocation');
    }
    public locationForlist(type?: string): Observable<Array<LocationForList>> {
        let url = `${this.baseUrl}/forlist`;
        if (type) {
            url = `${url}?type=${encodeURIComponent(type)}`
        }
        return this.http.get<Array<LocationForList>>(url);
    }
    public locationTypeForlist(): Observable<Array<LocationType>> {
        return this.http.get<Array<LocationType>>(this.baseUrl+'/type');
    }
    public changeOwner(details: ChangeOwnerRequest) {
        return this.http.put(`${this.baseUrl}/changeowner`, details);
    }
    public getChildLocation(locationid, pageInfo?: any, size?: any): Observable<Page<LocationResponse>> {
        // if (pageInfo) {
        ///
        // hghggh
        let url = `${this.baseUrl}/${locationid}/childlocation`;


        if (pageInfo !== null) {
            url = `${url + `?page=${pageInfo}&size=${size}`}`;
            // console.log("pageInfo==", url);
        }
        //     return this.http.get<Page<LocationResponse>>(`${this.baseUrl}/${locationid}/childlocation` + '?page=' + page);
        // }
        console.log("pageInfo==", url);
        return this.http.get<Page<LocationResponse>>(url);
    }
    public getLocationProductItems(locationid, page: number, size: any, producttype?: any): Observable<Page<BtLocationProductItemResponse>> {
        let url = `${this.baseUrl}/${locationid}/productitem?`
        if (page !== null) {
            url = `${url + `?page=${page}&size=${size}`}`;
        }
        if (producttype) {
            url = `${url}&typefilter=${producttype}`;
        }

        return this.http.get<Page<BtLocationProductItemResponse>>(`${url}`);
    }
    public getForlistdLocationProductItems(locationid): Observable<Array<LocationProductItemForList>> {
        return this.http.get<Array<LocationProductItemForList>>(`${this.baseUrl}/${locationid}/product/forlist`);
    }
    public saveProductRequest(locationId: number, lpRequest: LPRequest): Observable<LPRequest> {
        return this.http.post<LPRequest>(`${this.baseUrl}/${locationId}/productrequest`, lpRequest);
    }
    public getSendproductRequest(locationId: number, pageInfo?: any, size?: any): Observable<Page<BtLocationProductRequestResponse>> {
        console.log(pageInfo);
        // if (page) {
        //     fghj
        //     return this.http.get<Page<LocationProductRequestResponse>>(`${this.baseUrl}/${locationId}/sentproductrequest?page=${page}`);
        // }
        let url = `${this.baseUrl}/${locationId}/sentproductrequest`;

        if (pageInfo !== null) {
            url = `${url + `?page=${pageInfo}&size=${size}`}`;
            console.log("pageInfo==", url);
        }

        return this.http.get<Page<BtLocationProductRequestResponse>>(url);
    }
    public getReceivedRequest(locationId: number, page?: number, size?: string): Observable<Page<BtLocationProductRequestResponse>> {
        // if (page != undefined) {
        //     bbbbb
        //     return this.http.get<Page<LocationProductRequestResponse>>(this.baseUrl + "/" + locationId + "/" + "receivedrequest" + '?page=' + page + '&size=10&');
        // }
        let url = `${this.baseUrl}/${locationId}/receivedrequest`;

        if (page !== undefined) {
            url = `${url + `?page=${page}&size=${size}`}`;
        }
        return this.http.get<Page<BtLocationProductRequestResponse>>(url);
    }
    public saveReturnOrder(locationId: number, returnOrder: LPReturnOrderRequest): Observable<LPReturnOrderRequest> {
        return this.http.post<LPReturnOrderRequest>(`${this.baseUrl}/${locationId}/returnorder`, returnOrder);
    }
    public getFaultyItems(locationId: number): Observable<Array<LocationFaultyItemResponse>> {
        return this.http.get<Array<LocationFaultyItemResponse>>(`${this.baseUrl}/${locationId}/itemforreturn`)
    }
    public getAllLocationProductItems(locationType: string, page: number, itemtype): Observable<Page<LocationProductItemResponse>> {
        let url = `${this.baseUrl}/productitem?locationtype=${locationType}`
        if (page) {
            url = `${url}&page=${page}`;
        }
        if (itemtype) {
            url = `${url}&producttype=${itemtype}`;
        }
        return this.http.get<Page<LocationProductItemResponse>>(url);
    }
    public submitreporder(data): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/customereorder`, data);
    }
    public getLocationThreshold(filter?: any): Observable<Page<any>> {
        let url = `${this.baseUrl}/threshold?locationType=warehouse`;
        if (filter) {
            url = `${url}&${filter}`;
        }
        return this.http.get<Page<any>>(url);
    }
    public getLocationThresholdByLocationId(locationId: number, filter?: any): Observable<Page<any>> {
        let url = `${this.baseUrl}/threshold/${locationId}`;
        if (filter) {
            url = `${url}?${filter}`;
        }
        return this.http.get<Page<any>>(url);
    }
    public resetMSL(data): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/threshold`, data);
    }
    public getserialNumberByLocationPartCode(locationId: number, partCode: string): Observable<Array<LocationPartCodeSerialNumber>> {
        return this.http.get<Array<LocationPartCodeSerialNumber>>(`${this.baseUrl}/serialnumber/${locationId}/${partCode}`);
    }
 

    public getProductItemsByLocationId(locationid, filter?): Observable<Page<LocationProductItemResponse>> {
        let url = `${this.baseUrl}/${locationid}/productitem?`
        if (filter) {
            url = `${url}${filter}`;
        }

        return this.http.get<Page<LocationProductItemResponse>>(`${url}`);
    }
    public getChildLocations(locationid, filter?): Observable<Page<LocationResponse>> {
        let url = `${this.baseUrl}/${locationid}/childlocation`;
        if (filter) {
            url = `${url}?${filter}`;
        }
        return this.http.get<Page<LocationResponse>>(url);
    }

    public getSendproductRequests(locationId: number, filter): Observable<Page<LocationProductRequestResponse>> {
        let url = `${this.baseUrl}/${locationId}/sentproductrequest`;
        if (filter) {
            url = `${url}?${filter}`;
        }
        return this.http.get<Page<LocationProductRequestResponse>>(url);
    }
    public getReceivedRequests(locationId: number, filter): Observable<Page<LocationProductRequestResponse>> {
        let url = `${this.baseUrl}/${locationId}/receivedrequest`;
        if (filter) {
            url = `${url}?${filter}`;
        }

        return this.http.get<Page<LocationProductRequestResponse>>(url);
    }

    public searchMyInventory(search): Observable<Array<any>> {
        return this.http.post<Array<any>>(`${this.baseUrl}/searchmyinventory`, search);
    }

    public getMyInventory(filter?): Observable<Page<any>> {
        let url = this.baseUrl + '/myinventory';
        if (filter) {
            url = url + filter;
        }
        return this.http.get<Page<any>>(url);
    }
    public changeStatus(lineitemid, stateid,remarks) {
        return this.http.put(`${this.baseUrl}/updatestate/${lineitemid}`, { state: stateid,remarks:remarks });
    }

    public getLocationParentSiblingAndChilds():Observable<Array<any>>{
        return this.http.get<Array<any>>(`${this.baseUrl}/parentandsiblings`);
    }
    public getSearchResult(searchparam): Observable<Array<LocationResponse>> {
        return this.http.get<Array<LocationResponse>>(`${this.baseUrl}/advancesearch`, {params:searchparam});
      }

      //   cild location adv search
    public getChildLocSearchResult(searchparam,localid): Observable<Array<LocationResponse>> {
        return this.http.get<Array<LocationResponse>>(`${this.baseUrl}/${localid}/childlocation/advancesearch`, {params:searchparam});
      }
      public getProductItemLocSearchResult(searchparam,localid): Observable<Array<BtLocationProductItemResponse>> {
        return this.http.get<Array<BtLocationProductItemResponse>>(`${this.baseUrl}/${localid}/productitem/advancesearch`, {params:searchparam});
      }
      public getSendReqSearchResult(searchparam,localid): Observable<Array<BtLocationProductRequestResponse>> {
        return this.http.get<Array<BtLocationProductRequestResponse>>(`${this.baseUrl}/${localid}/sentproductrequest/advancesearch`, {params:searchparam});
      }
      public getReciveReqSearchResult(searchparam,localid): Observable<Array<BtLocationProductRequestResponse>> {
        return this.http.get<Array<BtLocationProductRequestResponse>>(`${this.baseUrl}/${localid}/receivedrequest/advancesearch`, {params:searchparam});
      }

      public getAvailableStock(locationId,productId):Observable<any>{
          console.log(`data ${locationId} and ${productId}`)
          return this.http.get<any>(`${this.baseUrl}/stock/${locationId}/${productId}`)
      }
}

