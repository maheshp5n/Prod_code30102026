import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { FileUploadResponse } from "../../services/model";

@Injectable({providedIn:'root'})
export class GlobalFilesService
{
    constructor(private _http:HttpClient){

    }

    public getFileList(objectName: string, objectId: number): Observable<Array<FileUploadResponse>> {
        return this._http.get<Array<FileUploadResponse>>(`api/uploadedFiles` + '?objectName=' + objectName + '&objectId=' + objectId);
      }
}
 