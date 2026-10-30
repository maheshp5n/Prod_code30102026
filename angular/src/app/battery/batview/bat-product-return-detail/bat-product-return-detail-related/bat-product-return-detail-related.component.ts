import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { ActionHistoryObjectAndId, LpReturnOrderItemResponse, Page, ROlineItems } from '../../../../services/model';
import { BtProductReturnService } from '../../../bat_services/bt-product-return.service';
import { BtProductreturnorderDetailsDataTransfer } from '../bat-product-return-detail.datatrasfer';

@Component({
  selector: 'app-bat-product-return-detail-related',
  templateUrl: './bat-product-return-detail-related.component.html',
  styleUrls: ['./bat-product-return-detail-related.component.css']
})
export class BatProductReturnDetailRelatedComponent implements OnInit {
  proRoCol:any=[];
  proSortArr=[];
  page:any=0;
  dReceived: boolean = false;
  btProductReturnItems =  [];
  productROid:any;
  loggedInuser:any;
  _cFlag:any;
  _user
  sortArr:any;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(
    public data: BtProductreturnorderDetailsDataTransfer,private spinner:NgxSpinnerService,
    private _global: GlobalserviceService,
    private _router: Router, private _acRoutes: ActivatedRoute,
    private _btProductReturnSerive : BtProductReturnService

  ) {}

  ngOnInit() {
    this.getProductReturnItems();
  }
  getProductReturnItems() {
      this.btProductReturnItems=[]; 
      this.spinner.show();
      this._btProductReturnSerive.getProductReturnItems(this.data.btProductReturnRes.id).subscribe(data => {
       this.btProductReturnItems = data;      
       this.spinner.hide();
      });
    
      this.actionHistoryObjectAndId.objectId = '' + this.data.btProductReturnRes.id;
      this.actionHistoryObjectAndId.objectName = 'BtProductReturnOrder';
  }
  advancesearch = {
    returnOrderItemManualId: '',
    ProductManualId: '',
    returnOrderManualId: '',
    productDescription:''
  };
  
  searchROItems() {   
    if(this.advancesearch.returnOrderItemManualId=='' 
      && this.advancesearch.ProductManualId=='' &&this.advancesearch.returnOrderManualId==''&&this.advancesearch.productDescription==''){
     this._global.alert({type:'alert',message:"Please Enter Valid Key For Search"});
    }
    else{
      this.spinner.show();
      this._btProductReturnSerive.advanceSearchForBtReturnItems(this.data.btProductReturnRes.id,this.advancesearch).subscribe(data => {
        this.spinner.hide();
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
  
  }
  resetROItems(){
    this.advancesearch.returnOrderItemManualId='';
    this.advancesearch.ProductManualId= '';
    this.advancesearch.returnOrderManualId= '';  
    this.getProductReturnItems();
  
  }
  getDeleteReturnItems(itemId){
    this.spinner.show();
    this._btProductReturnSerive.deteteReturnItems(this.data.btProductReturnRes.id,itemId).subscribe(data=>{
      this.spinner.hide();
      this._global.alert({type:'',message:'Item Deleted'});     
      this.getProductReturnItems();
      // this._router.navigate(['/bat_product_returnOrder', this.data.btProductReturnRes.id]);
    },error=>{
      this.spinner.hide();      
      this._global.alert({type:'alert',message: error.error.message});
    }); 
  }
  

}
