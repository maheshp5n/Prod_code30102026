import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { LocationProductTransferLineItemResponse } from '../../services/model';
import { ProductTransferServices } from '../../services/producttransfer.service';

@Component({
  selector: 'app-product-transfer-line-item-details',
  templateUrl: './product-transfer-line-item-details.component.html',
  styleUrls: ['./product-transfer-line-item-details.component.scss']
})
export class ProductTransferLineItemDetailsComponent implements OnInit {

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
 
  productTransferItemId;
  locationProductRequestResponse:LocationProductTransferLineItemResponse=new LocationProductTransferLineItemResponse();

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
  constructor( private _spinnerservice:NgxSpinnerService,
    private _acRoutes:ActivatedRoute,
    private _producttransferService:ProductTransferServices) { 
      this._acRoutes.params.subscribe(pram=>{
        this.productTransferItemId=pram['productTransferItemId'];
        this.getProductTransferDetails();
      });
    }

  ngOnInit() {
  }
  getProductTransferDetails(){
    this._spinnerservice.show();
    this._producttransferService.getLProductTransferLineItemById(this.productTransferItemId).subscribe(data=>{
      this.locationProductRequestResponse=data;
      this._spinnerservice.hide();
    },error=>{
      this._spinnerservice.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
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
