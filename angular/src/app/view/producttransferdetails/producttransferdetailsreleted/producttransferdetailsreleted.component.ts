import { Component, OnInit } from '@angular/core';
import { ProductTransferServices } from '../../../services/producttransfer.service';
import { ActionHistoryObjectAndId, LocationProductTransferItemResponse, LocationProductTransferLineItemResponse, Page } from '../../../services/model';
import { ActivatedRoute } from '@angular/router';
import { PrudcttransferDataService } from '../producttransferdata.service';
import { GlobalserviceService } from '../../../globalservice.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-producttransferdetailsreleted',
  templateUrl: './producttransferdetailsreleted.component.html',
  styleUrls: ['./producttransferdetailsreleted.component.scss']
})
export class ProducttransferdetailsreletedComponent implements OnInit {
prTCol=[];
cmponent='proTranferItem';
objectId:any;
page:number=0;
proTranferItem:any;
prTranferItem=["S.No","Product Transfer Item","Product Request Line Items","Part Code","Source Warehouse","Destination Warehouse","Tracking","Owner"]
sortCaseColumns=["","lpTransferItemId","lpRequestLineItem.locationProductRequestLineItemId","product.productPartCode","product.pdoductDescription","","","","owner.firstName"];  
transferId:number;
sortArr:any;
sortCaseColumnsBlnk=[];
ptid:any;
isptItem:boolean=false;
  locationProductTransferLineItems:Array<LocationProductTransferLineItemResponse>=[];
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private _dataservice:PrudcttransferDataService,private _producttransferService:ProductTransferServices,
    private _activatedroute:ActivatedRoute,private _global:GlobalserviceService,
    private _spinnerservice: NgxSpinnerService,) { 
    this._activatedroute.queryParams.subscribe(pram => {
      this.prTCol=this.prTranferItem;
      this.sortCaseColumnsBlnk=this.sortCaseColumns;
    

    });
    this._activatedroute.params.subscribe(pram=>{
      console.log(pram);
      this.ptid= decodeURIComponent(pram['transferitemid']);
       this.objectId=this._dataservice.dataServiceLocationTResponse.locationProductTransferItemId;
       console.log(this.objectId);
      })
  }

  ngOnInit() {
    console.log(this._dataservice.dataServiceLocationTResponse.id);
    this.getLineItemDetails(this._dataservice.dataServiceLocationTResponse.id);
    this.actionHistoryObjectAndId.objectId = '' + this._dataservice.dataServiceLocationTResponse.locationProductTransferItemId;
      this.actionHistoryObjectAndId.objectName = 'LPTransferItem';
  }
  LPTItems:Page<LocationProductTransferItemResponse>=new Page<LocationProductTransferItemResponse>()
  getLineItemDetails(transferId){
    this._spinnerservice.show();
    this.LPTItems.content=[];
    this._producttransferService.getPTItems(transferId,`?page=0&size=5`).subscribe(data=>{
      this._spinnerservice.hide();
      this.LPTItems=data

    },error=>{
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
    })
  }
advancesearch = {
  manualId: '',
  partcode:'',
  sourceLocation: '',
  destinationLocation: '',
  productDescription:''
 
  
};
  searchPT() {
    if( this.advancesearch.manualId== '' &&
    this.advancesearch. partcode=='' &&
    this.advancesearch.sourceLocation== '' &&
    this.advancesearch.destinationLocation== '' &&
    this.advancesearch.productDescription==''){
      this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
  
    }
    else{
      this._spinnerservice.show();
    this._producttransferService.getSearchResult(this.advancesearch).subscribe(data => {
      this._spinnerservice.hide();
      this.LPTItems.content = data;
    
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({type:'alert',message:error.error.message});
  
    });
  
    }
    
  }
  resetPT(){
    this.advancesearch.manualId=''
    this.advancesearch. partcode=''
    this.advancesearch.sourceLocation=''
    this.advancesearch.destinationLocation=''
    this.advancesearch.productDescription=''
  }
}
