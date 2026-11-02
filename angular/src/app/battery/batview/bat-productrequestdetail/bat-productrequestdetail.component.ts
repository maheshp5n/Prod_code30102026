import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LocationProductRequestService } from '../../../services/locationproductrequest.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, LocationProductRequestItemResponse, LocationProductRequestResponse, LocationProductTransferItemResponse, LPItemRequest, LPRequest, LPTransferRequest, Page, ProductsForList } from '../../../services/model';
import { ProductsService } from '../../../services/products.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { AddItemtoPrductRequest, BtItemListByPrId, BtProductList, BtProductRequestResponse, BtProductTransferItemList} from '../../bt_model';

@Component({
  selector: 'app-bat-productrequestdetail',
  templateUrl: './bat-productrequestdetail.component.html',
  styleUrls: ['./bat-productrequestdetail.component.css']
})
export class BatProductrequestdetailComponent implements OnInit {
  productRequestId;
  locationProductRequestResponse: LocationProductRequestResponse = new LocationProductRequestResponse();
  btProducRequestResponse: BtProductRequestResponse = new BtProductRequestResponse();
  btProductReqItemResponse: Page<BtItemListByPrId> = new Page<BtItemListByPrId>();
  btItemResByItemid: BtItemListByPrId = new BtItemListByPrId();
  pageProductRequestItems: Page<LocationProductRequestItemResponse> = new Page<LocationProductRequestItemResponse>();
  pageProductTranferDetails: Page<BtProductTransferItemList> = new Page<BtProductTransferItemList>();
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  page: number = 0;
  prId: number;
  editProductReq : boolean = false;
  destinationLocation : string;
  displayButtons = { info: { visibal: true, icon: 'fa fa-angle-down' }, sysInfo: { visibal: true, icon: 'fa fa-angle-down' } };
  _cFlag: string;
  prliSize: number = 5;
  alertInfo = {
    type: '',
    message: ''
  }
  avilableStockAtSource: any = {
    exicomTotalOkQuantity: 0,
    quantity_in_on_hand: 0
  };
  avilabStockAtDestination: any = {
    exicomTotalOkQuantity: 0,
    quantity_in_on_hand: 0
  };
  @ViewChild('transferItem') transferItem: ModalDirective;
  @ViewChild('receiveItem') receiveItem: ModalDirective;
  @ViewChild('addItemModal') addItemModal: ModalDirective;
  @ViewChild('editProductRequestModel') editProductRequestModel: ModalDirective;


  products: Array<ProductsForList> = [];
  _lpitemRequest: LPItemRequest = new LPItemRequest();
  btPrItemReq: AddItemtoPrductRequest = new AddItemtoPrductRequest();
  openModels = { transferItem: false, receiveItem: false };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  logedInUser;
  cmponent: string;
  ProductLineitemCoulms = ["S.No", "Name", "Product", "Product Description", "Quantity"];
  protransfer = ["S.No.", "Product Transfer Item", "Product Transfer", "Product", "Product Description", "Quantity Sent", "Quantity Recieved", "Full Recieved"]
  lineItemColumns: any = [];
  protransferCol: any = [];
  locationParentAndSiblings = [];
  prSubmitDate = [];
  constructor(
    private _spinnerservice: NgxSpinnerService,
    private _acRoutes: ActivatedRoute,
    private _productService: ProductsService,
    private _global: GlobalserviceService,
    private _user: Loginservices,
    private _btWareHouse: BtwarehouseService,
    private _btProductReqService: BtproductrequestService
  ) {
    this.logedInUser = _user.getLoggedInUser();
    this._acRoutes.params.subscribe(pram => {
      this.productRequestId = pram['id'];
      this.cmponent = "Product Request Line Item";
      this.lineItemColumns = this.ProductLineitemCoulms;
      this.protransferCol = this.protransfer;
      this.getProductRequestDetailById(this.productRequestId);
      this.actionHistoryObjectAndId.objectId = this.productRequestId;
      this.actionHistoryObjectAndId.objectName = 'BtProductRequest';
    });
    this._acRoutes.queryParams.subscribe(pram => {
      this.lineItemColumns = this.ProductLineitemCoulms;
      this._cFlag = pram['_c'];
      console.log(this._cFlag);
    });
  }

  ngOnInit() {
    this.getProducts();
    this.getParentSiblingsAndChildLocation();
  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getProductRequestDetailById(this.productRequestId);
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getItemByPrId(this.productRequestId);
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
  getProductRequestDetailById(prId: number) {
    this._spinnerservice.show();
    this._btProductReqService.fetchBtProductReqById(prId).subscribe(data => {
      this._spinnerservice.hide();
      this.btProducRequestResponse = data;
    }, error => {
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  //for product request submit date
  getProductRequestSubmittedDate(){
    this._spinnerservice.show();
    this._btProductReqService.getDateOfPrSubmitted(this.productRequestId).subscribe(data=>{
      this._spinnerservice.hide();
      this.prSubmitDate = data;  
      this.getProductRequestDetailById(this.productRequestId);
    },err=>{
      this._spinnerservice.hide();
    })
  }
  getItemByPrId(PrId: number) {
    this._spinnerservice.show();
    this._btProductReqService.itemByPrId(PrId).subscribe(data => {
      this.btProductReqItemResponse.content = data.content;
      this.btProductReqItemResponse.totalElements = data.totalElements;
      this._spinnerservice.hide();
    }, error => {
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }
  addItem() {
    this.btPrItemReq.productRequestId = this.productRequestId;
    if (this.btPrItemReq.btProductId == null || this.btPrItemReq.productRequestId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Product Part Code'});
      return false;
    }
    if (this.btPrItemReq.requiredQty == null || this.btPrItemReq.requiredQty == undefined || !FieldValidation.isValidNumber(this.btPrItemReq.requiredQty)) {
      this._global.alert({ type: 'alert', message: 'Please Fill Quantity'});
      return false;
    }else {
      if (this.btPrItemReq.requiredQty == 0  ) {
        this._global.alert({ type: 'alert', message: "Quantity Can Not Be Zero"});
        return false;
      }
    }

    this._spinnerservice.show();
    this._btProductReqService.addItemReq(this.btPrItemReq, this.productRequestId).subscribe(data => {
      this._global.alert({ type: '', message: 'Added Successfully'});
       this.getItemByPrId(this.productRequestId);
       this.getProductRequestDetailById(this.productRequestId);
      // this.getProductTransferResponse(this.productRequestId);
      this.addItemModal.hide();
    }, error => {
      this._spinnerservice.hide();    
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  submitProductRequest(productStatus) {
    this._spinnerservice.show();
    this._btProductReqService.changeStatusOfProductRequest(this.productRequestId,productStatus).subscribe(data => {
      this._spinnerservice.hide();   
      this._global.alert({ type: '', message: `Product Request ${this.btProducRequestResponse.productRequestManualId} Has Been Submitted`});
      this.getProductRequestSubmittedDate();
      this.getItemByPrId(this.productRequestId);    
     
    }, error => {
      this._spinnerservice.hide(); 
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  getProducts() {
    this._productService.getProductForList().subscribe(data => {
      this.products = data
      data.forEach(e => {
        let pro = new ProductsForList();
        pro.productPartCode = e.productPartCode;
        pro.productDescription = e.productDescription;
        pro.bothvalues = e.productPartCode + ' ' + e.productDescription;
        this.products.push(pro);
      })
    }

    )
  }
  productItemList: Array<BtProductList> = [];
  searchProducts($event) {
    this._btProductReqService.fetchProductList($event.term).subscribe(data => {
      const temp = [];
      data.forEach(e => {
        let pro = new BtProductList();
        pro.id = e.id;
        pro.partCode = e.partCode;
        pro.productType = e.productType;
        pro.bothvalues = e.partCode + ' ' + e.productType;
        temp.push(pro);
      })
      this.productItemList = temp;
    });
  }
  getShortCloseProductRequest(changeStatus){
    console.log(changeStatus)
    this._spinnerservice.show(); 
    this._btProductReqService.changeStatusOfProductRequest(this.productRequestId,changeStatus).subscribe(data=>{
    this._global.alert({ type: '', message: `Product Request ${this.btProducRequestResponse.productRequestManualId} Has Been Short Closed` });
      this.getProductRequestDetailById(this.productRequestId);
      this.getItemByPrId(this.productRequestId);
    },error=>{
      this._spinnerservice.hide();      
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  } 
  
  getShortCloseProductItem(prItemId,manulId,itemStatus){
    this._spinnerservice.show();
    this._btProductReqService.changeStatusOfProductReqItem(this.productRequestId,prItemId,itemStatus).subscribe(data=>{
      this._spinnerservice.hide();   
      this._global.alert({ type: '', message: `Product Item ${manulId} Has Been Short Closed` });
      this.getItemByPrId(this.productRequestId);
    },error=>{
      this._spinnerservice.hide();   
      this._global.alert({ type: 'alert', message: error.error.message });

    })
  }
  getDeleteProuctRequestITems(itemId,manaulId){
    this._spinnerservice.show();
    this._btProductReqService.deleteProuctReqItem(this.productRequestId,itemId).subscribe(data=>{    
    this._global.alert({ type: '', message: `Product Item ${manaulId} Has Been Deleted` });
    this._spinnerservice.hide();   
    this.getItemByPrId(this.productRequestId);
    },error=>{
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }
  
  submitToSap() {
    this._spinnerservice.show();
    this._btProductReqService.submitReqToSap(this.productRequestId).subscribe(data => {
      this._spinnerservice.hide();
      this.getProductRequestDetailById(this.productRequestId);
      this._global.alert({ type: '', message: `Product Request ${this.locationProductRequestResponse.prManualId} Has Been Submitted`});
    }, error => {
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  onProductTransferDone($event: LPTransferRequest) {
    this.transferItem.hide();
   this._global.alert({ type: 'alert', message: `Updated successfully` });
    this.getProductRequestDetailById(this.productRequestId);

  }
  onProductTransferCancled($event) {
    this.transferItem.hide();
  }
  onItemReceived($event) {
    this.receiveItem.hide();
    this.getProductRequestDetailById(this.productRequestId);  
    this._global.alert({ type: '', message: `Updated successfully`});
  }
  onItemReceivedCancled($event) {
    this.receiveItem.hide();
  }
  getProductTransferResponse(productRequestid, filter?) {
    this._spinnerservice.show();
    this._btProductReqService.getTransferItems(productRequestid).subscribe(data => {
      this._spinnerservice.hide();
      this.pageProductTranferDetails.content = data.content;  
      this.pageProductTranferDetails.totalElements = data.totalElements;  
      console.log(this.pageProductTranferDetails)
    }, error => {
      this._spinnerservice.hide();
    });
  }
  getParentSiblingsAndChildLocation(){
    this._btWareHouse.getLocationParentSiblingAndChilds().subscribe(data=>{
      this.locationParentAndSiblings = data;
    })
  }
  
  updateLocation() {
    this._spinnerservice.show();
    this._btProductReqService.btUpdateSourceLocationPr(this.productRequestId, this.btProducRequestResponse.productRequestSourceLocationId).subscribe(data => {
      this._spinnerservice.hide();
    }, error => {
      this._spinnerservice.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  additemModel() {
    this.addItemModal.show();
    this.btPrItemReq.btProductId = null;
    this.btPrItemReq.requiredQty = null;
    this.avilabStockAtDestination.exicomTotalOkQuantity = 0;
    // this.avilabStockAtDestination.quantity_in_on_hand = 0;
    this.avilableStockAtSource.exicomTotalOkQuantity = 0;
    // this.avilableStockAtSource.quantity_in_on_hand = 0
  }
  EditProductReq(){
    this.editProductRequestModel.show();
    this.editProductReq = true;  
    
  }
  advancesearchPRitems = {
    productRequestItemManualId: '',
    productRequestItemProductPartcode: '',
    productRequestItemStatus: ''

  };
  searchPReqitems() {
    if (this.advancesearchPRitems.productRequestItemManualId == '' &&
      this.advancesearchPRitems.productRequestItemProductPartcode == '' &&
      this.advancesearchPRitems.productRequestItemStatus == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinnerservice.show();
      this._btProductReqService.advanceSearchForPrItems(this.advancesearchPRitems, this.productRequestId).subscribe(data => {
        this._spinnerservice.hide();      
       this.btProductReqItemResponse.content = data;
      }, error => {
        this._spinnerservice.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      })
    }
  } 
  resetpreqitem() {
    this.advancesearchPRitems.productRequestItemManualId = ''
    this.advancesearchPRitems.productRequestItemProductPartcode = ''
    this.advancesearchPRitems.productRequestItemStatus = ''
    this.getItemByPrId(this.productRequestId);

  }
  searchPT() {
    if (this.advancesearchPT.productTransferManualId == '' &&
      this.advancesearchPT.productPartcode == '' &&
      this.advancesearchPT.productTranferTrackingNumber == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinnerservice.show();
      this._btProductReqService.getSearchPT(this.advancesearchPT, this.productRequestId).subscribe(data => {
        this._spinnerservice.hide();
        this.pageProductTranferDetails.content = data;
      }, error => {
        this._spinnerservice.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      })


    }
  }
  changePartCode(prdouctRequestItemId, partCode) {
    if (prdouctRequestItemId !== null && partCode !== null) {
      this._spinnerservice.show();
      this._btProductReqService.updatePartcode(prdouctRequestItemId, partCode).subscribe(data => {
        this._spinnerservice.hide();
        // this.getItems(this.productRequestId,this.page,this.prliSize);
      }, error => {
        this._spinnerservice.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    } else {
      this._global.alert({ type: 'alert', message: 'Please Pick One PartCode' });

    }
  }
  cancel() {
    this._spinnerservice.show();
    this._btProductReqService.getItems(this.productRequestId, 'page=0' + '&size=5').subscribe(data => {
      this.pageProductRequestItems = data;
      this.pageProductRequestItems.content.forEach(d => d.edit = false);
      this._spinnerservice.hide();
    }, err => {
      this._spinnerservice.hide();
    });
  }
  Discription: string = null;
  onPicAsset($event) {
    //  console.log($event.productDescription);
    this.Discription = $event.productDescription;
  }
  btProductID : any;
  getAvilableStock(value) {
    console.log(value)
    if (value != null) {
      this.btProductID = value;
    }
     this.getAvilableStockAtSource();
     this.getAvilableStockAtDestination();
  }  
  getAvilableStockAtSource() {
    this.avilableStockAtSource = {
      exicomTotalOkQuantity: 0,
      quantity_in_on_hand: 0
    };   
    console.log(this.btProductID)
    this._btWareHouse.getAvailableStock(this.btProducRequestResponse.productRequestSourceLocationId,this.btProductID).subscribe(data => {
        this.avilableStockAtSource = data;
        if (JSON.stringify(this.avilableStockAtSource) == "{}") {
          this.avilableStockAtSource = {
            ok_qexicomTotalOkQuantityuantity: 0,
            quantity_in_on_hand: 0
          };
          this.avilableStockAtSource.exicomTotalOkQuantity = 0;
          this.avilableStockAtSource.quantity_in_on_hand = 0;
        }
      });
  }
  getAvilableStockAtDestination() {
    this.avilabStockAtDestination = {
      exicomTotalOkQuantity: 0,
      quantity_in_on_hand: 0
    };
    this._btWareHouse.getAvailableStock(this.btProducRequestResponse.productRequestDestinationLocationId,this.btProductID).subscribe(data => {
        this.avilabStockAtDestination = data
        if (JSON.stringify(this.avilabStockAtDestination) == "{}") {
          this.avilabStockAtDestination = {
            exicomTotalOkQuantity: 0,
            quantity_in_on_hand: 0
          };
          this.avilabStockAtDestination.exicomTotalOkQuantity = 0;
          this.avilabStockAtDestination.quantity_in_on_hand = 0;

        }   
      });
  }
 
  advancesearchPT = {
    productTransferManualId : '',
    productTransferShippingMode : '',
    productTransferShippingToName : '',
    productTranferTrackingNumber : '',
    productPartcode : '',
    productDescription : ''
  }
  resetPT() {
    this.advancesearchPT.productTransferManualId = ''
    this.advancesearchPT.productTransferShippingMode = ''
    this.advancesearchPT.productTransferShippingToName = ''  
    this.advancesearchPT.productTranferTrackingNumber = ''
    this.advancesearchPT.productPartcode = '' 
    this.advancesearchPT.productDescription = ''

   this.getProductTransferResponse(this.productRequestId);
  }
  onUpdateProductRequest($event : BtProductRequestResponse){
    this.getProductRequestDetailById(this.productRequestId);
  }
  cancelData(){
    this.editProductRequestModel.hide();
  }
    //Validation for numric tye value
    numberOnly(event): boolean {
      const charCode = (event.which) ? event.which : event.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
      }
      return true;
  
    }

}
