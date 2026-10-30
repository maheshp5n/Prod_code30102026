import { Component, OnInit } from '@angular/core';
import { BtProductreturnorderDetailsDataTransfer } from '../bat-product-return-detail.datatrasfer';

@Component({
  selector: 'app-bat-product-return-detail-details',
  templateUrl: './bat-product-return-detail-details.component.html',
  styleUrls: ['./bat-product-return-detail-details.component.css']
})
export class BatProductReturnDetailDetailsComponent implements OnInit {
  displayButtons={info:{visibal:true,icon:'fa fa-angle-down'},sysInfo:{visibal:true,icon:'fa fa-angle-down'},shippingInfo:{visibal:true,icon:'fa fa-angle-down'}};
  constructor(public data:BtProductreturnorderDetailsDataTransfer) { }

  ngOnInit() {
  }
  changeButtonDisplay(bname){
    switch(bname){
      case 'info':{
        this.displayButtons.info.visibal=!this.displayButtons.info.visibal;
        this.displayButtons.info.icon=this.displayButtons.info.visibal?'fa fa-angle-down':'fa fa-angle-up';
        
        break;
      }
      case 'sysinfo':{
        this.displayButtons.sysInfo.visibal=!this.displayButtons.sysInfo.visibal;
        this.displayButtons.sysInfo.icon=this.displayButtons.sysInfo.visibal?'fa fa-angle-down':'fa fa-angle-up';
        
        break;
      }
      case 'shippingInfo':{
        this.displayButtons.shippingInfo.visibal=!this.displayButtons.shippingInfo.visibal;
        this.displayButtons.shippingInfo.icon=this.displayButtons.shippingInfo.visibal?'fa fa-angle-down':'fa fa-angle-up';
        break;
      }
    }
  }

}
