import { Component, OnInit } from '@angular/core';
import { BtProductTransferResponse } from '../../../bt_model';
import { BtProductTransferDataService } from '../bat-product-transfer.data.serice';

@Component({
  selector: 'app-bat-product-transfer-details',
  templateUrl: './bat-product-transfer-details.component.html',
  styleUrls: ['./bat-product-transfer-details.component.css']
})
export class BatProductTransferDetailsComponent implements OnInit {
  btProductTransferData:BtProductTransferResponse=new BtProductTransferResponse();
  constructor(public _btData : BtProductTransferDataService) { }
  displayButtons={info:{visibal:true,icon:'fa fa-angle-down'},sysInfo:{visibal:true,icon:'fa fa-angle-down'},shippingInfo:{visibal:true,icon:'fa fa-angle-down'}};

  ngOnInit() {
    this.btProductTransferData=this._btData.btProductTransferData;
    console.log(this.btProductTransferData)
  }
changeButtonDisplay(bname){
    switch(bname){
      case 'info':{
        this.displayButtons.info.visibal=!this.displayButtons.info.visibal;
        if(this.displayButtons.info.visibal){
          this.displayButtons.info.icon='fa fa-angle-down';
        }
        else{
          this.displayButtons.info.icon='fa fa-angle-up';
        }
        break;
      }
      case 'sysinfo':{
        this.displayButtons.sysInfo.visibal=!this.displayButtons.sysInfo.visibal;
        if(this.displayButtons.sysInfo.visibal){
          this.displayButtons.sysInfo.icon='fa fa-angle-down';
        }
        else{
          this.displayButtons.sysInfo.icon='fa fa-angle-up';
        }
        break;
      }
      case 'shippinginfo':{
        this.displayButtons.shippingInfo.visibal=!this.displayButtons.shippingInfo.visibal;
        if(this.displayButtons.shippingInfo.visibal){
          this.displayButtons.shippingInfo.icon='fa fa-angle-down';
        }
        else{
          this.displayButtons.shippingInfo.icon='fa fa-angle-up';
        }
        break;
      }
    }
  }


}
