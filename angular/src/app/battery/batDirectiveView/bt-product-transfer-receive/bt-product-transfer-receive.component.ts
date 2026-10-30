import { Component, OnInit,Output,EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtProductTransferRecieveRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-product-transfer-receive',
  templateUrl: './bt-product-transfer-receive.component.html',
  styleUrls: ['./bt-product-transfer-receive.component.css']
})
export class BtProductTransferReceiveComponent implements OnInit {
  btProductTransferItems : Array<any>=[];
  btRecieveReqLIst : Array<BtProductTransferRecieveRequest> = [];

  @Output()
  onPtrCreated: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private btTransferService:BatproducttransferService,private _spinner: NgxSpinnerService,private _router:Router,private _global: GlobalserviceService) { 
  }

  ngOnInit(): void {
    this.getPendingToreceiveItem();
  }

  getPendingToreceiveItem(){
    this._spinner.show();
    this.btTransferService.getPendingToRecieve().subscribe(data=>{
      this.btProductTransferItems = data;
      this.btProductTransferItems.forEach(d=>{
        d.receivingQty = d.transferItemTransferedQuantity-d.transferItemReceivedQuantity;

      })
      this._spinner.hide();
    },error=>{
      this._spinner.hide();
    })
  }
  saveProductRecieve(){ 
    this.btRecieveReqLIst = [];
    for(let i=0;i<this.btProductTransferItems.length;i++){
      let recieveQty = new BtProductTransferRecieveRequest();
      recieveQty.transferItemId = this.btProductTransferItems[i].transferItemId;
      recieveQty.quantity = this.btProductTransferItems[i].receivingQty;
      this.btRecieveReqLIst.push(recieveQty);
    }
    this._spinner.show();
    this.btTransferService.postPendingToRecieve(this.btRecieveReqLIst).subscribe(data=>{
      this._spinner.hide();
      this.onPtrCreated.emit(data);
      
    },error=>{
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })


  }
  canelProductRecieve(){
    this.onCancle.emit('canceled');

  }

}
