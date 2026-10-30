import { ActivatedRoute, Router } from '@angular/router';
import { GlobalserviceService } from './../../../globalservice.service';
import { Component, OnInit } from '@angular/core';
import { ProductreturnorderDetailsDataTransfer } from '../productreturnorderdetails.datatransfer';
import { ProductReturnService } from '../../../services/productreturnservice'
import { ActionHistoryObjectAndId, LpReturnOrderItemResponse, Page, ROlineItems } from '../../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'app-productreturnorderdetailsreleted',
  templateUrl: './productreturnorderdetailsreleted.component.html',
  styleUrls: ['./productreturnorderdetailsreleted.component.scss']
})
export class ProductreturnorderdetailsreletedComponent implements OnInit {
  proRoCol:any=[];
  proSortArr=[];
  proRO=["S.No.","Return Order Line Item","Product","Product Description","Product Serial Number","Received","Action"]
   page:any=0;
  dReceived: boolean = false;
  pageItemResponse: Page<LpReturnOrderItemResponse> = new Page<LpReturnOrderItemResponse>();
  d: Page<ROlineItems> = new Page<ROlineItems>();
  productROid:any;
  loggedInuser:any;
  _cFlag:any;
  _user
  sortArr:any;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(
    private data: ProductreturnorderDetailsDataTransfer,private spinner:NgxSpinnerService,
    private _productreturnservice: ProductReturnService,
    private _global: GlobalserviceService,
    private _router: Router, private _acRoutes: ActivatedRoute

  ) { 
    this._acRoutes.queryParams.subscribe(pram => {
      this.proRoCol=this.proRO;
      this.proSortArr=["","lpro_lineitem_id","prd.product_part_code","prd.pdoduct_description","product_line_item_serial_number","",""]

    });
    this._acRoutes.params.subscribe(pram=>{
      pram['id']
      console.log(pram['returnorderid'])
      console.log(pram)
      //  this.productROid=this.data.productReturnOrderResponse.returnOrderId;
      //  console.log(this.productROid);
    })
  }

  ngOnInit() {
    this.getProductReturnItems();
  }
  totalElementofRO:number=0;
  getProductReturnItems() {
    this.productROid=this.data.productReturnOrderResponse.returnOrderId;
    if (!this.dReceived) {
      if (this.data == undefined || this.data.productReturnOrderResponse == undefined || this.data.productReturnOrderResponse.returnOrderId == undefined) {
        setTimeout(this.getProductReturnItems, 100);
        return;
      }
      else {
        this.dReceived = true;
      }
    }
    if (this.dReceived) {
      this.pageItemResponse.content=[];
      this.d.content=[];
      this._productreturnservice.getROItems(this.data.productReturnOrderResponse.returnOrderId,'?page='+this.page+'&size=5').subscribe(data => {
        this.d = data;
        this.totalElementofRO=data.totalElements;
        // data.content.forEach(item => {
        //  this.pageItemResponse.content.forEach(item => {
        //     this.d.push({
        //       partCode: item.partCode,
        //       partDescription: item.partDescription,
        //       returnItemId: item.returnOrderItemManualId,
        //       lineItemSerialNumber: item.s,
        //       lineItemId: lineitem.returnLineItemIdManual,
        //       received: lineitem.received,
        //       returnOrderItemId: item.returnOrderItemId,
        //       returnLineItemId: lineitem.returnLineItemId
        //     });
        //   });
        // });
      });
    }
      this.actionHistoryObjectAndId.objectId = '' + this.data.productReturnOrderResponse.returnOrderId;
      this.actionHistoryObjectAndId.objectName = 'LProductReturnOrder';
  }
  removeItem(id) {

    this._productreturnservice.deleteReturnOrderLineItem(this.data.productReturnOrderResponse.returnOrderId, id).subscribe(data => {
      this.getProductReturnItems();
      this._global.alert({ alert: '', message: 'Item deleted' });
      this._router.navigate(['/productreturnorder', this.data.productReturnOrderResponse.returnOrderId]);
    }, error => this._global.alert({ alert: 'alert', message: error.error.message }));
  }
  advancesearch = {
    returnOrderLineItemManualId: '',
    productPartcode: '',
    productDescription: '',
    serialNumber: ''
  
  };
  
  searchROItems() {
     
    if(this.advancesearch.returnOrderLineItemManualId=='' && this.advancesearch.serialNumber==''
      && this.advancesearch.productPartcode=='' &&this.advancesearch.productDescription==''){
    //  this.message="Please Enter valid Key for Search";
     this._global.alert({type:'alert',message:"Please Enter valid Key for Search"});

    }
    else{
      this.spinner.show();
      this.d.content=[];
      this._productreturnservice.getSearchResultROItems(this.advancesearch,this.productROid).subscribe(data => {
        this.spinner.hide();
        this.d.content = data;
      },error=>{
        this.spinner.hide();
        // this.message=error.error.message;
        // this.popupSuccessModel.show();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
  
  }
  resetROItems(){
    this.advancesearch.returnOrderLineItemManualId='';
    this.advancesearch.productPartcode= '';
    this.advancesearch.productDescription= '';
    this.advancesearch.serialNumber= '';
  
  }
  
}
