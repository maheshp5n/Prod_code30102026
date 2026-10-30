import { Component, OnInit, ViewChild,EventEmitter, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { LocationProductRequestService } from '../../../services/locationproductrequest.service';
import { LocationProductRequestItemResponse } from '../../../services/model';
import { BtItemListByPrId } from '../../bt_model';

@Component({
  selector: 'app-bat-product-request-items-details',
  templateUrl: './bat-product-request-items-details.component.html',
  styleUrls: ['./bat-product-request-items-details.component.scss']
})
export class BatProductRequestItemsDetailsComponent implements OnInit {
  productReqItemsRes;
  ngClassDetails:string='active';
  ngClassReleated:string='';
  vDetails:boolean=true;
   isEditMode:boolean=false;
   heading : string;
   edit:boolean=false;
   message:string="";
  loginData:any;
  popupNgClass: string = 'alert alert-danger center center-block';
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  addressDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  };
  infoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  sysinfoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  constructor( private _spinnerservice:NgxSpinnerService,
    private _acRoutes:ActivatedRoute,
    private _productRequestService:LocationProductRequestService,) { 
      this._acRoutes.queryParams.subscribe(param=>{
        this.productReqItemsRes = param;
      });
    }

  ngOnInit() {
  }
  changeAddressDisplay(){
    this.addressDisplay.open=!this.addressDisplay.open
    if(this.addressDisplay.open){
      this.addressDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.addressDisplay.angleIcon='fa fa-angle-right';
    }
  }
  changeInfoDisplay(){
    this.infoDisplay.open=!this.infoDisplay.open
    if(this.infoDisplay.open){
      this.infoDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.infoDisplay.angleIcon='fa fa-angle-right';
    }

  }
  sysInfoDisplay(){
    this.sysinfoDisplay.open=!this.sysinfoDisplay.open
    if(this.sysinfoDisplay.open){
      this.sysinfoDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.sysinfoDisplay.angleIcon='fa fa-angle-right';
    }
    
  }
  subTabChange(tabName:string){
    if(tabName=='details'){
      this.ngClassDetails='active';
      this.ngClassReleated='';
      this.vDetails=true;
    }
    else{
      this.ngClassDetails='';
      this.ngClassReleated='active';
      this.vDetails=false;
    }
    return false;
  }

}
