import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import { Subject } from 'rxjs';
import { Notifications } from './model';
import * as SockJs from 'sockjs-client';
import { GlobalserviceService } from '../globalservice.service';
import { ExcelService } from './ExcelService ';

@Injectable({ providedIn: 'root' })
export class WabsocketService {
  private webSocketUrl: string;
  private stompClient;
  private newNotification$: Subject<Notifications> = new Subject<Notifications>();
  public newNotification = this.newNotification$.asObservable();
  private $compiledata: Subject<string> = new Subject<string>();
  public compileMessage = this.$compiledata.asObservable();
  private $customerinventoryupload:Subject<any>=new Subject<any>();
  private customerinventoryupload=this.$customerinventoryupload.asObservable();
  private userId: string;
  private conncte: boolean = false;
  constructor(private _global:GlobalserviceService,private _excel:ExcelService) {
    //this.webSocketUrl = `ws://${location.host}/notification`;
    this.webSocketUrl = `notification`;
    if (localStorage) {
      var details = JSON.parse(localStorage.getItem('login'));
      this.userId = details.emailId;
    }
  }
  private initWebSocket() {
    this.conncte = true;
    let websocket = new SockJs(this.webSocketUrl);
    this.stompClient = Stomp.over(websocket);
    this.stompClient.debug = () => { }
    let that = this;
    this.stompClient.connect({}, (frame) => {
      that.stompClient.subscribe(`/topic/${that.userId}`, (message) => {
        let body = JSON.parse(message.body);
        that.newNotification$.next(body);
      });
      that.stompClient.subscribe(`/user/topic/compile`, (message) => {
        that.$compiledata.next(message.body);
      });
      that.stompClient.subscribe(`/user/topic/customerinventory`,(message)=>{
        //that.$customerinventoryupload.next(JSON.parse(message.body));
        that._global.sendMessageTouser(JSON.parse(message.body));
      });
      that.stompClient.subscribe(`/user/topic/customerinventorydata`,(message)=>{
        that._excel.exportAsExcelFile(JSON.parse(message.body),"bulkuploadresult");
      });

      that.stompClient.subscribe(`/user/topic/bulkuploadnotification`,(message)=>{
        that._global.sendMessageTouser(JSON.parse(message.body));
      });

      that.stompClient.subscribe(`/user/topic/bulkuploadresultfile`,(message)=>{
        //that._global.setCustomerInvnetoryData(JSON.parse(message.body));
        that._excel.exportAsExcelFile(JSON.parse(message.body),"bulkuploadresult");
      });

    }, () => {
      if (this.conncte) {
        this.initWebSocket();
      }
    });

  }
  public disconnect() {
    try {
      this.conncte = false;
      this.stompClient.disconnect(() => { });
    } catch (error) {

    }

  }
  public connect() {
    this.initWebSocket();
  }
  public sendforcompilation(objectName, data) {
    this.stompClient.send(`/app/compile/${objectName}`, {}, data);
  }
}
