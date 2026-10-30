import { Subject, Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalserviceService {
  private msg: Subject<any> = new Subject<any>();
  private usrmessage:Subject<any>=new Subject<any>();
  private customerinventoryuploaddata:Subject<Array<any>>=new Subject<Array<any>>();
  constructor() { }
  public alert(message: any) {
    this.msg.next(message);
  }
  public onMessage(): Observable<any> {
    return this.msg.asObservable();
  }
  public sendMessageTouser(message){
    this.usrmessage.next(message);
  }
  public onMessageForUser():Observable<any>{
    return this.usrmessage.asObservable();
  }
  public setCustomerInvnetoryData(data){
    this.customerinventoryuploaddata.next(data);
  }
  public getCustomerInvnentorydata():Observable<Array<any>>{
    return this.customerinventoryuploaddata.asObservable();
  }
}
