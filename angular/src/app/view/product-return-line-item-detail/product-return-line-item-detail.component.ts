import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { ProductReturnService } from '../../services/productreturnservice';
import { LpReturnLineItemResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-product-return-line-item-detail',
  templateUrl: './product-return-line-item-detail.component.html',
  styleUrls: ['./product-return-line-item-detail.component.scss']
})
export class ProductReturnLineItemDetailComponent implements OnInit {
  lineItemId:any
  lpReturnLineItemResponse:LpReturnLineItemResponse=new LpReturnLineItemResponse();
 
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
 
 ngClassDetails:string='active';
  ngClassReleated:string='';
  vDetails:boolean=true;
   isEditMode:boolean=false;
   heading : string;
   edit:boolean=false;
   message:string="";
  loginData:any;
  popupNgClass: string = 'alert alert-danger center center-block';
  // @Output()
  // onProductSelected: EventEmitter<PriceBookEntriesResponse> = new EventEmitter<PriceBookEntriesResponse>();
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
  constructor(  private _spinnerservice:NgxSpinnerService,
    private _acRoutes:ActivatedRoute,private _productreturnservice:ProductReturnService) { 
    this._acRoutes.params.subscribe(pram=>{
      this.lineItemId=pram['lineItemId'];
      this.getLineItems();
    });
  }

  ngOnInit() {
    
  }
  getLineItems(){
    this._spinnerservice.show();
  this._productreturnservice.getLReturnLineItemById(this.lineItemId).subscribe(data=>{
    this._spinnerservice.hide();
    this.lpReturnLineItemResponse=data;
  },error=>{
    this._spinnerservice.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
  })
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
      // this.getEntries();
    }
    return false;
  }

}
