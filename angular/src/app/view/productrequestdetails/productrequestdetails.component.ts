import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { LocationProductRequestResponse, LocationProductRequestItemResponse, Page, ProductsForList, LPItemRequest, LPTransferRequest, LocationProductTransferItemResponse, ActionHistoryObjectAndId, LPRequest } from '../../services/model';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ProductsService } from '../../services/products.services';
import { FieldValidation } from '../../services/validation.services';
import { GlobalserviceService } from '../../globalservice.service';
import { Loginservices } from '../../services/login.services';
import { LocationService } from '../../services/location.service';

@Component({
  selector: 'app-productrequestdetails',
  templateUrl: './productrequestdetails.component.html',
  styleUrls: ['./productrequestdetails.component.scss']
})
export class ProductrequestdetailsComponent implements OnInit {

  productRequestId;
  locationProductRequestResponse: LocationProductRequestResponse = new LocationProductRequestResponse();
  pageProductRequestItems: Page<LocationProductRequestItemResponse> = new Page<LocationProductRequestItemResponse>();
  pageProductTranferDetails: Page<LocationProductTransferItemResponse> = new Page<LocationProductTransferItemResponse>();
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  page:number=0;
  displayButtons = { info: { visibal: true, icon: 'fa fa-angle-down' }, sysInfo: { visibal: true, icon: 'fa fa-angle-down' } };
  _cFlag: string;
   prliSize:number=5;
  alertInfo = {
    type: '',
    message: ''
  }
  avilableStockAtSource: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  avilabStockAtU: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  @ViewChild('alertPopupModal') alertPopupModal: ModalDirective;
  @ViewChild('transferItem') transferItem: ModalDirective;
  @ViewChild('receiveItem') receiveItem: ModalDirective;
  @ViewChild('addItemModal') addItemModal :ModalDirective;
  @ViewChild('sureModel')sureModel:ModalDirective
  products: Array<ProductsForList> = [];
  _lpitemRequest: LPItemRequest = new LPItemRequest();
  openModels = { transferItem: false, receiveItem: false };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  logedInUser;
  cmponent:string;
  ProductLineitemCoulms=["S.No","Name","Product","Product Description","Quantity"];
  protransfer=["S.No.","Product Transfer Item","Product Transfer","Product","Product Description","Quantity Sent","Quantity Recieved","Full Recieved"]
  pRItemSortNam:any=[];
  lineItemColumns:any=[];
  protransferCol:any=[];
  sortArr:any;
  locationParentAndSiblings=[];
  constructor(
    private _spinnerservice: NgxSpinnerService,
    private _acRoutes: ActivatedRoute,
    private _productRequestService: LocationProductRequestService,
    private _productService: ProductsService,
    private _global: GlobalserviceService,
    private _user: Loginservices,
    private _location:LocationService
  ) {
    this.logedInUser = _user.getLoggedInUser();
    this._acRoutes.params.subscribe(pram => {
      this.productRequestId = pram['id'];
      this.cmponent="Product Request Line Item";
      this.lineItemColumns=this.ProductLineitemCoulms;
      this.protransferCol=this.protransfer;
      this.getOwners();
      this.getProductRequestDetails(this.productRequestId);
      this.actionHistoryObjectAndId.objectId = this.productRequestId;
      this.actionHistoryObjectAndId.objectName = 'LProductRequest';
    });
    this._acRoutes.queryParams.subscribe(pram => {
      this.lineItemColumns=this.ProductLineitemCoulms;
      this.pRItemSortNam=["","locationProductRequestLineItemId","product.productPartCode","product.pdoductDescription",""];
      this._cFlag = pram['_c'];
    });
  }
  rolGroup:string;
loginData:any
  ngOnInit() {
    // this.getProducts();
    this.getOwners();
    this.getParentSiblingsAndChildLocation();
    this.getLoggedInUser();
  }
  getLoggedInUser() {
    this.loginData = this._user.getLoggedInUser();
    this.rolGroup = this.loginData.roleGroupAndAccount['roleGroup']
   
  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getProductRequestDetails(this.productRequestId);
      console.log(this.pageProductTranferDetails.content.length);
      this.getOwners();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getOwners();
      this.getItems(this.productRequestId,0,this.prliSize);
      this.getProductTransferResponse(this.productRequestId);
    }
    return false;
  }
  changeButtonDisplay(bname) {
    switch (bname) {
      case 'info': {
        this.displayButtons.info.visibal = !this.displayButtons.info.visibal;
        if (this.displayButtons.info.visibal) {
          this.displayButtons.info.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.info.icon = 'fa fa-angle-up';
        }
        break;
      }
      case 'sysinfo': {
        this.displayButtons.sysInfo.visibal = !this.displayButtons.sysInfo.visibal;
        if (this.displayButtons.sysInfo.visibal) {
          this.displayButtons.sysInfo.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.sysInfo.icon = 'fa fa-angle-up';
        }
        break;
      }
    }
  }
  issSortclose
  getProductRequestDetails(requestId: number) {
  
    this._spinnerservice.show();
    this.getOwners();
    this._productRequestService.getLocationProductRequest(requestId).subscribe(data => {
      this.locationProductRequestResponse = data;
      console.log(data);
      
      if(this.locationProductRequestResponse.status=='Short Closed'){
        this.issSortclose=false;
      }
      else if(this.PROwnerRole=='DCT_MAIN_STORE' && this.rolGroup=='DCT_MAIN_STORE'){
     
          this.issSortclose=true
      
      }
     else if(this.PROwnerRole=='SCH' && this.rolGroup=='DCT_MAIN_STORE'){
   
        this.issSortclose=true
    
    }
      else if(this.PROwnerRole=='FIELD_SERVICE_ENGINEER' && this.rolGroup=='SCH'){
        this.issSortclose=true;

      }


      else{
        this.issSortclose=false
      }
      console.log(this.issSortclose);
      this._spinnerservice.hide();
    }, error => {
      this._spinnerservice.hide();
    });
  }
  getItems(requestid,page, size) {
    this._spinnerservice.show();
    // const size=5;
    this.page=page;
    this.pageProductRequestItems.content=[];
    this._productRequestService.getItems(requestid,'page='+page+'&size=5').subscribe(data => {
      this.pageProductRequestItems = data;
     
      //   d.edit=false
       
      this.pageProductRequestItems.content.forEach(e=>{
        e.edit=false
        if(e.quantityIssued<e.quantityRequired && e.lineItemStatus=='Short Closed'){
          e.shortClosedQuantity=e.quantityRequired-e.quantityIssued;
        }
        else if(e.quantityIssued==0 && e.lineItemStatus=='Short Closed'){
          e.shortClosedQuantity=e.quantityRequired;
        }
        else if((e.quantityIssued==e.quantityRequired) && e.lineItemStatus=='Short Closed'){
          e.shortClosedQuantity=e.quantityRequired;
        }
        else{
          e.shortClosedQuantity=0
        }
        if(this.locationProductRequestResponse.status=='Short Closed'){
          e.lineItemStatus="Short Closed";
        }
      })
      this._spinnerservice.hide();
    }, err => {
      this._spinnerservice.hide();
    });
  }
  addItem() {
    console.log(this._lpitemRequest);
    this._lpitemRequest.lprId = this.productRequestId;

    if (this._lpitemRequest.productPartCode == null || this._lpitemRequest.productPartCode == undefined) {
      this.showAlert('Please select product part code');
      return false;
    }
    if (this._lpitemRequest.requestQuantity == null || this._lpitemRequest.requestQuantity == undefined || !FieldValidation.isValidNumber(this._lpitemRequest.requestQuantity)) {
      this.showAlert('Please fill Quantity');
      return false;
    }

    this._spinnerservice.show();
    this._productRequestService.addItems(this.productRequestId, this._lpitemRequest).subscribe(data => {
      this._spinnerservice.hide();

      this.showMessage(`Updated Successfully`);
      this.getItems(this.productRequestId,this.page,this.prliSize);
     this.getOwners();
      this.getProductTransferResponse(this.productRequestId);
      this.getProductRequestDetails(this.productRequestId);
      

    }, error => {
      this._spinnerservice.hide();
      this.showAlert(error.error.message);
    });
  }
  submitProductRequest() {
    this._spinnerservice.show();
    this._productRequestService.submitRequest(this.productRequestId).subscribe(data => {
      this._spinnerservice.hide();
      this.getOwners();
      this.getProductRequestDetails(this.productRequestId);
      this.showMessage(`Product Request ${this.locationProductRequestResponse.prManualId} has been submitted`);

    }, error => {
      this._spinnerservice.hide();
      this.showAlert(error.error.message);
    });
  }
  showAlert(message) {
    let al = { type: 'danger', message: message };
    this.alertInfo = al;
    this.alertPopupModal.show();
  }
  showMessage(message) {
    let al = { type: 'success', message: message };
    this.alertInfo = al;
    this.alertPopupModal.show();
  }
  getProducts() {
    this._productService.getProductForList().subscribe(data=>{
      this.products = data
      data.forEach(e=>{
        let pro=new ProductsForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this.products.push(pro);
      })
    }
    
      )
  }
  productItemList: Array<ProductsForList> = [];
  searchProducts($event){
    this._productService.getSmallForlist($event.term).subscribe(data=>{
      const temp=[];
      data.forEach(e=>{
        let pro=new ProductsForList();
      
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        temp.push(pro);
      })
      this.productItemList=temp;
    });
  }
  onProductTransferDone($event: LPTransferRequest) {
    this.transferItem.hide();
    this.showMessage(`Updated successfully`);
    this.getOwners();
    this.getProductRequestDetails(this.productRequestId);


  }
  onProductTransferCancled($event) {
    this.transferItem.hide();
  }
  onItemReceived($event) {
    this.receiveItem.hide();
    this.getOwners();
    this.getProductRequestDetails(this.productRequestId);
    this.showMessage(`Updated successfully`);
  }
  onItemReceivedCancled($event) {
    this.receiveItem.hide();
  }
  getProductTransferResponse(productRequestid, filter?) {

    this.getOwners();
    this._spinnerservice.show();
    this.pageProductTranferDetails.content=[];
    this._productRequestService.getTransferItems(productRequestid, filter).subscribe(data => {
      this._spinnerservice.hide();
      this.pageProductTranferDetails = data;
    }, error => {
      this._spinnerservice.hide();
    });
  }

  deleteItem(id) {
    this._spinnerservice.show();
    this._productRequestService.deleteProductLineItemsById(id).subscribe(data => {
      this._spinnerservice.hide();
      this._global.alert({ type: '', message: 'Item deleted' });
      this.getItems(this.productRequestId,this.page,this.prliSize);
    }, error => {
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  getParentSiblingsAndChildLocation(){
    this._location.getLocationParentSiblingAndChilds().subscribe(data=>this.locationParentAndSiblings=data);
  }

  updateLocation(){
    this._spinnerservice.show();
    this._productRequestService.updatesoruceLocation(this.productRequestId,this.locationProductRequestResponse.sourceLocationId).subscribe(data=>{
      this._spinnerservice.hide();
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

  changePartCode(prdouctRequestItemId,partCode){
   if(prdouctRequestItemId!==null && partCode!==null){
    this._spinnerservice.show();
    this._productRequestService.updatePartcode(prdouctRequestItemId,partCode).subscribe(data=>{
      this._spinnerservice.hide();
      this.getItems(this.productRequestId,this.page,this.prliSize);
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
   }else{
    this._global.alert({type:'alert',message:'Please Pick one PartCode'});
     
   }
  }
  cancel(){
this._spinnerservice.show();
this._productRequestService.getItems(this.productRequestId,'page=0'+'&size=5').subscribe(data => {
  this.pageProductRequestItems = data;
  this.pageProductRequestItems.content.forEach(d=>d.edit=false
    );
  this._spinnerservice.hide();
}, err => {
  this._spinnerservice.hide();
});
  }
  Discription:string=null;
  onPicAsset($event){
  //  console.log($event.productDescription);
   this.Discription=$event.productDescription;
  }
  getAvilableStock($event) {
    if ($event != null) {
      this.Discription = $event.productDescription;
    } else {
      this.Discription = null;
    }
    this.getAvilableStockAtSource();
    this.getAvilableStockAtU();
  }
  getAvilableStockAtSource() {
    
    this.avilableStockAtSource = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._location.getStockByWarehouseAndProductPartCode(
      this.locationProductRequestResponse.sourceLocationId,
      this._lpitemRequest.productPartCode)
      .subscribe(data =>{ 
        this.avilableStockAtSource = data;
        if(JSON.stringify(this.avilableStockAtSource)=="{}"){
          this.avilableStockAtSource = {
            ok_quantity: 0,
            quantity_in_on_hand: 0
          };
          this.avilableStockAtSource.ok_quantity=0;
          this.avilableStockAtSource.quantity_in_on_hand=0;
          
        }
        
       } );
  }
  _lprequest: LPRequest = new LPRequest();
  _lpitems: LPItemRequest = new LPItemRequest();
  getAvilableStockAtU() {
    this.avilabStockAtU = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._location.getStockByWarehouseAndProductPartCode(
      this.locationProductRequestResponse.destinationLocationId,
      this._lpitemRequest.productPartCode)
      .subscribe(data => {this.avilabStockAtU = data
        if(JSON.stringify(this.avilabStockAtU)=="{}"){
          this.avilabStockAtU = {
            ok_quantity: 0,
            quantity_in_on_hand: 0
          };
          this.avilabStockAtU.ok_quantity=0;
          this.avilabStockAtU.quantity_in_on_hand=0;
          
        }
      });
  }

  submitToSap() {
    this._spinnerservice.show();
    this._productRequestService.submitRequesttoSap(this.productRequestId).subscribe(data => {
      this._spinnerservice.hide();
      this.getOwners();
      this.getProductRequestDetails(this.productRequestId);
      this.showMessage(`Product Request ${this.locationProductRequestResponse.prManualId} has been submitted`);

    }, error => {
      this._spinnerservice.hide();
      this.showAlert(error.error.message);
    });
  }

  disableLineItem(lineItemId){
    this._spinnerservice.show();
    this._productRequestService.disableProductRequestLineItem(lineItemId).subscribe(data=>{
      this._spinnerservice.hide();
      this.getProductRequestDetails(this.productRequestId);
      this.getItems(this.productRequestId,0,this.prliSize);
      this.getOwners();
      this._global.alert({type:'',message:'Product request line item has been disabled'});
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({type:'alert',message:error.error.message});
      
    });
  }
  additemModel(){
    this.addItemModal.show();
    this._lpitemRequest.productPartCode=null;
    this._lpitemRequest.requestQuantity=null;
    this.avilabStockAtU.ok_quantity=0;
    this.avilabStockAtU.quantity_in_on_hand=0;
    this.avilableStockAtSource.ok_quantity=0;
    this.avilableStockAtSource.quantity_in_on_hand=0
    }
    advancesearchPRitems = {
      name: '',
      product: '',
      productDescription: ''
      
    };
    advancesearchPT = {
      name: '',
      product: '',
      productDescription: ''
      
    };
    resetpreqitem(){
  this.advancesearchPRitems.name=''
  this.advancesearchPRitems.product=''
  this.advancesearchPRitems.productDescription=''
    }
    searchPReqitems(){
      if(this.advancesearchPRitems.name=='' &&
      this.advancesearchPRitems.product=='' &&
      this.advancesearchPRitems.productDescription==''){
        this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
      }
      else{
        this._spinnerservice.show();
        this._productRequestService.getSearchResultPRLineitems(this.advancesearchPRitems,this.productRequestId).subscribe(data=>{
          this._spinnerservice.hide();
          this.pageProductRequestItems.content = data;
        },error=>{
          this._spinnerservice.hide();
          this._global.alert({ type: 'alert', message:error.error.message });
        })

      }
     

    }

    resetPT(){
   this.advancesearchPT.name=''
   this.advancesearchPT.product=''
   this.advancesearchPT.productDescription=''
    }
    productTransferlen:boolean=false;
    searchPT(){
      if(this.advancesearchPT.name=='' &&
      this.advancesearchPT.product=='' &&
      this.advancesearchPT.productDescription==''){
        this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
      }
      else{
        this._spinnerservice.show();
        this.pageProductTranferDetails.content=[];
        this._productRequestService.getSearchPT(this.advancesearchPT,this.productRequestId).subscribe(data=>{
          this._spinnerservice.hide();
          this.pageProductTranferDetails.content = data;
          console.log(this.pageProductTranferDetails.content.length)
          if(this.pageProductTranferDetails.content.length === 0){
            this.productTransferlen=true;
          }
          else{
            this.productTransferlen=false;
          }
        },error=>{
          this._spinnerservice.hide();
          this._global.alert({ type: 'alert', message:error.error.message });
        })


    }
  }

  // short close
  selecteditemid:number;
  forPR:string;
  shortcloseHeading:string='';
  messageShort:string='';
  onshortClose( id?:any){
    this.shortcloseHeading=this.locationProductRequestResponse.prManualId;
    this.selecteditemid=id
    this.forPR='allPR'
    this.messageShort='Do you want to Short Close this PR'
    this.sureModel.show();
    
  }
  onshortCloseItem( id?:any){
    console.log(id);
    this.shortcloseHeading=id.lpRequestItemManualId;
    this.forPR='onlyItem';
    this.messageShort='Do you want to Short Close this item'
    this.selecteditemid=id.lpRequestItemId;
    console.log("item id="+ this.selecteditemid);
      
    this.sureModel.show();
  }
  yesRequest(){
    console.log(this.forPR);
    this._spinnerservice.show();
    this._productRequestService.shortClose(this.productRequestId,this.selecteditemid,this.forPR).subscribe(data=>{
      this._spinnerservice.hide();
      this.sureModel.hide();
      if(this.forPR=="allPR")
      {
        this._global.alert({ type: '', message:" Product Request has been Short Closed" });
      }
      else
      {
        this._global.alert({ type: '', message:" Product Request Line Item has been Short Closed" });
      }
      
      this.getProductRequestDetails(this.productRequestId);
      this.getItems(this.productRequestId,0,this.prliSize);
   
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
    })
  }
  sureModelhide(){
    this.sureModel.hide();
    // this.prId=null;
    // this.prManualId=null;
  }
PROwnerRole:string='';
  getOwners(){
    this._spinnerservice.show();
    this._productRequestService.getOwnerdetails(this.productRequestId).subscribe(data=>{
    if(data!=null)
      {this.PROwnerRole=data.role;}
      else{
        this._global.alert({ type: 'alert', message:'This owner role group is missing'});
      }
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
  
    })
  }
  


}
