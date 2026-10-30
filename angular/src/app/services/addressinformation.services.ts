import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { CountryDetails, StateProvinceDetails, AddressInformation } from "./model";

@Injectable()
export class AddressInformationServices{
    constructor(private _http:HttpClient){}
    
}