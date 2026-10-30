import { Component, OnInit } from '@angular/core';
import { ProductreturnorderDetailsDataTransfer } from '../productreturnorderdetails.datatransfer';

@Component({
  selector: 'app-productreturnorderdetailsdetails',
  templateUrl: './productreturnorderdetailsdetails.component.html',
  styleUrls: ['./productreturnorderdetailsdetails.component.scss']
})
export class ProductreturnorderdetailsdetailsComponent implements OnInit {
  displayButtons={info:{visibal:true,icon:'fa fa-angle-down'},sysInfo:{visibal:true,icon:'fa fa-angle-down'},shippingInfo:{visibal:true,icon:'fa fa-angle-down'}};
  constructor(public data:ProductreturnorderDetailsDataTransfer) { }

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
