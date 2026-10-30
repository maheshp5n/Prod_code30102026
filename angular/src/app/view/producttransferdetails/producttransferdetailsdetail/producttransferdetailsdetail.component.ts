import { Component, OnInit } from '@angular/core';
import { LocationProductTransferItemResponse } from '../../../services/model';
import { PrudcttransferDataService } from '../producttransferdata.service';

@Component({
  selector: 'app-producttransferdetailsdetail',
  templateUrl: './producttransferdetailsdetail.component.html',
  styleUrls: ['./producttransferdetailsdetail.component.scss']
})
export class ProducttransferdetailsdetailComponent implements OnInit {
  productTransferDetails:LocationProductTransferItemResponse=new LocationProductTransferItemResponse();
  constructor(public _data:PrudcttransferDataService) { }
  displayButtons={info:{visibal:true,icon:'fa fa-angle-down'},sysInfo:{visibal:true,icon:'fa fa-angle-down'},shippingInfo:{visibal:true,icon:'fa fa-angle-down'}};

  ngOnInit() {
    this.productTransferDetails=this._data.dataServiceLocationTResponse;
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
