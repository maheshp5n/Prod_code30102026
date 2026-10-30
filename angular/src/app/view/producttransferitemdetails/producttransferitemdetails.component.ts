import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { LocationProductTransferItemResponse, LocationProductTransferLineItemResponse, ProductType } from '../../services/model';
import { ProductsService } from '../../services/products.services';
import { ProductTransferServices } from '../../services/producttransfer.service';
import { PrudcttransferDataService } from '../producttransferdetails/producttransferdata.service';

@Component({
  selector: 'app-producttransferitemdetails',
  templateUrl: './producttransferitemdetails.component.html',
  styleUrls: ['./producttransferitemdetails.component.css']
})
export class ProducttransferitemdetailsComponent implements OnInit {
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  transferIItemId:number;
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  proRoCol=[];
objectId:any;
page:number=0;
prTranferItem=["S.No","Product Serial No.","Product Part Code","Product Description","Product Type","Recieved"]

  proType:Array<ProductType>=[]
  lineItemResp:Array<LocationProductTransferLineItemResponse>=[]
  resp:LocationProductTransferItemResponse=new LocationProductTransferItemResponse();
  constructor(private _producttransferService:ProductTransferServices
    ,private _ngxspinnnerService:NgxSpinnerService,private global:GlobalserviceService,
     private _activatedRoute:ActivatedRoute,private productService:ProductsService) {
      this._activatedRoute.queryParams.subscribe(pram => {
        this.proRoCol=this.prTranferItem;
  
      });
      this._activatedRoute.params.subscribe(param=>{
        this.transferIItemId=param['ptItemid'];
        this.objectId=this.transferIItemId;
      });
      
      this.getDetailsofitemById(this.transferIItemId);
     }

  ngOnInit() {
    this.getProductType();
  }
  getDetailsofitemById(id){
    this._ngxspinnnerService.show();
    this._producttransferService.getTransferItemDetails(id).subscribe(data=>{
      this.resp=data;
      this._ngxspinnnerService.hide();
    },error=>{
      this._ngxspinnnerService.hide();
      this.global.alert({ type: 'alert', message:error.error.message });
    })
  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getDetailsofitemById(this.transferIItemId);
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getLineItemsofitem();
    }
    return false;
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  getLineItemsofitem(){
    this._ngxspinnnerService.show();
    this.lineItemResp=[];
    this._producttransferService.getLineItems(this.transferIItemId).subscribe(data=>{
    this.lineItemResp=data;
    this._ngxspinnnerService.hide();
    })
  }
  advancesearchPT = {
    serialNumber: '',
    partcode: '',
    productDescription: '',
    productType:''
    
  };
  resetPTItems(){
this.advancesearchPT.serialNumber='';
this.advancesearchPT.partcode='';
this.advancesearchPT.productDescription='';
this.advancesearchPT.productType='';
  }
  searchPTitems(){
    if(this.advancesearchPT.serialNumber=='' &&
    this.advancesearchPT.partcode=='' &&
    this.advancesearchPT.productDescription=='' &&
    this.advancesearchPT.productType==''){
      this.global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    }
    else{
      this._ngxspinnnerService.show();
      this._producttransferService.getPTItemsSearchResult(this.advancesearchPT,this.transferIItemId).subscribe(data=>{
        this._ngxspinnnerService.hide();
        this.lineItemResp = data;
      },error=>{
        this._ngxspinnnerService.hide();
        this.global.alert({ type: 'alert', message:error.error.message });
      })

    }
   

  }
  getProductType(){
    this.productService.getTypeForAdv().subscribe(data=>{
     this._ngxspinnnerService.hide();
  this.proType=data;
    })
 
  }

}
