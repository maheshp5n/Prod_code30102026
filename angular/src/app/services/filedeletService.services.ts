import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


@Injectable({providedIn:'root'})
export class FileDeleteService{
    baseUrl:string="api/deleteFiles";
    constructor(private http:HttpClient){
      
        
    }
    public deleteAttachment(imageid,objectname, objectid): Observable<Object> {
        return this.http.delete(this.baseUrl+'?imageId=' + imageid+'&objectName='+objectname+'&objectId='+objectid);
    }

}
