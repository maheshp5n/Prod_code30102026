import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { PlantDetails, Page } from "./model";
import { Observable } from "rxjs";

@Injectable({providedIn:'root'})    
export class PlantService{
    constructor(private _http:HttpClient){}
    
    public getPlants():Observable<Array<PlantDetails>>{        
        return this._http.get<Array<PlantDetails>>('api/plant');
    }
}