import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Login, AccountCreate, LegalEntityResponse, Contacts, Page, LocationResponse, MSLItemRequestRequest, LocationRequest, LocationProductItemForList, LocationForList, LocationType, ChangeOwnerRequest, LocationProductItemResponse, LPRequest, LocationProductRequestResponse, LPReturnOrderRequest, LocationFaultyItemResponse, LocationPartCodeSerialNumber } from "./model";
import { url } from 'inspector';

@Injectable({ providedIn: 'root' })
export class LocationService {
    baseUrl = 'api/location';
    recentVisitUrl = 'api/location/recentvisit';
    forListUrl = 'api/location/forlist';
    typeForlist = 'api/location/type';

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
        return this.http.get<Array<LocationType>>(this.typeForlist);
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
    public getLocationProductItems(locationid, page: number, size: any, producttype?: any): Observable<Page<LocationProductItemResponse>> {
        let url = `${this.baseUrl}/${locationid}/productitem?`
        if (page !== null) {
            url = `${url + `?page=${page}&size=${size}`}`;
        }
        if (producttype) {
            url = `${url}&typefilter=${producttype}`;
        }

        return this.http.get<Page<LocationProductItemResponse>>(`${url}`);
    }
    public getForlistdLocationProductItems(locationid): Observable<Array<LocationProductItemForList>> {
        return this.http.get<Array<LocationProductItemForList>>(`${this.baseUrl}/${locationid}/product/forlist`);
    }
    public saveProductRequest(locationId: number, lpRequest: LPRequest): Observable<LPRequest> {
        return this.http.post<LPRequest>(`${this.baseUrl}/${locationId}/productrequest`, lpRequest);
    }
    public getSendproductRequest(locationId: number, pageInfo?: any, size?: any): Observable<Page<LocationProductRequestResponse>> {
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

        return this.http.get<Page<LocationProductRequestResponse>>(url);
    }
    public getReceivedRequest(locationId: number, page?: number, size?: string): Observable<Page<LocationProductRequestResponse>> {
        // if (page != undefined) {
        //     bbbbb
        //     return this.http.get<Page<LocationProductRequestResponse>>(this.baseUrl + "/" + locationId + "/" + "receivedrequest" + '?page=' + page + '&size=10&');
        // }
        let url = `${this.baseUrl}/${locationId}/receivedrequest`;

        if (page !== undefined) {
            url = `${url + `?page=${page}&size=${size}`}`;
        }
        return this.http.get<Page<LocationProductRequestResponse>>(url);
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
    public getStockByWarehouseAndProductPartCode(warehouse, partcode): Observable<any> {
        return this.http.get<any>(`${this.baseUrl}/stock/${warehouse}/${partcode}`);
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
      public getProductItemLocSearchResult(searchparam,localid): Observable<Array<LocationProductItemResponse>> {
        return this.http.get<Array<LocationProductItemResponse>>(`${this.baseUrl}/${localid}/productitem/advancesearch`, {params:searchparam});
      }
      public getSendReqSearchResult(searchparam,localid): Observable<Array<LocationProductRequestResponse>> {
        return this.http.get<Array<LocationProductRequestResponse>>(`${this.baseUrl}/${localid}/sentproductrequest/advancesearch`, {params:searchparam});
      }
      public getReciveReqSearchResult(searchparam,localid): Observable<Array<LocationProductRequestResponse>> {
        return this.http.get<Array<LocationProductRequestResponse>>(`${this.baseUrl}/${localid}/receivedrequest/advancesearch`, {params:searchparam});
      }

      public changePartcodeAndState(id,data){
          return this.http.put(`${this.baseUrl}/partcodechangemyinventory/${id}`,data);
      }

}
