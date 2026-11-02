import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LocationService } from '../../../services/location.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, LocationPartCodeSerialNumber, LocationProductItemResponse, LocationProductRequestResponse, LocationResponse, LocationType, Page, ProductType } from '../../../services/model';
import { ProductsService } from '../../../services/products.services';
import { BtproductService } from '../../bat_services/btproduct.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BtLocationProductItemResponse, BtLocationProductRequestResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-warehouse-details',
  templateUrl: './bat-warehouse-details.component.html',
  styleUrls: ['./bat-warehouse-details.component.css']
})
export class BatWarehouseDetailsComponent implements OnInit {

 //@ViewChild('creatLocation', { static: false }) creatLocation: ModalDirective;
//  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
 @ViewChild('mslRequest') mslRequest: ModalDirective;
//  @ViewChild('productRequestModal', { static: false }) productRequestModal: ModalDirective;
 @ViewChild('btProductReturn') btProductReturn: ModalDirective;
 @ViewChild('editLocation') editLocation: ModalDirective;

 response: LocationResponse = new LocationResponse()
 ngClassDetails: string = 'active';
 ngClassReleated: string = '';
 vDetails: boolean = true;
 locationId: number;
 isEditMode: boolean = false;
 heading: string;
 edit: boolean = false;
 CREATE_ACCOUNT_HEADING: string = 'Create MSL Entity';
 EDIT_ACCOUNT_HEADING: string = 'Edit MSL Request Entity'
 localres: LocationResponse = new LocationResponse();
 message: string = "";
 page: number = 0;
 productPage: number = 0;
 sortColumnName: string;
 sortOrder: string;
 totalPages: any;
 first: boolean;
 last: boolean;
 serialNumber: number = 0;
 sentRequisitionSerialNumber: number = 0;
 receivedRequisitionSerialNumber: number = 0;
 size: number;
 firstChild: boolean;
 lastChild: boolean;
 serialNumberChild: number = 0;
 serialNumbrLoc: string;
 loginData;
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
 serialNumberDetaiDisplay = {
   angleIcon: 'fa fa-angle-down',
   open: false
 }
 proType:Array<ProductType>=[]
 today = new Date();
 dateCustomClasses: DatepickerDateCustomClasses[];
 rootLocationDetails: LocationResponse = new LocationResponse();
 childLoactionDetails: Page<LocationResponse> = new Page<LocationResponse>();
 locationProductItem: Page<BtLocationProductItemResponse> = new Page<BtLocationProductItemResponse>();
 productRequestSent: Page<BtLocationProductRequestResponse> = new Page<BtLocationProductRequestResponse>();

 productRequestReceive: Page<BtLocationProductRequestResponse> = new Page<BtLocationProductRequestResponse>();
 openModels = { mslrequestModel: false, productrequest: false, productReturn: false, location: false };
 openProductReturnModel = {btProductReturn:false}
 alerts = {
   type: '',
   message: ''
 }
 LocaChildadvancesearch = {
   locationName: '',
   locationTypeName: '',
   legalEntityName: '',
   owner: '',
   plantcode: '',
   mobileWarehoue:'',
   inventoryWarehouse:''
 
 };
 ProductItemAdvancesearch = {
   locationName:'',
   locationPlant:'',
   productPartcode:'',
   productDescription:'',
   productType:''
 
 };
SendReqAdvancesearch = {
   productRequestManualId:'',
   productRequestStatus:'',
   productRequestFromDate:'',
   productRequestSoruceLocationName:'',
   productRequestDestinationLocationName:'',


  //  productRequestNumber: '',
  //  sourceWarehouse: '',
  //  destinationWarehouse: '',
  //  expectedDate: ''
 
 };
 RecievReqAdvancesearch = {
  productRequestManualId:'',
  productRequestStatus:'',
  productRequestFromDate:'',
  productRequestSoruceLocationName:'',
  productRequestDestinationLocationName:''

  //  productRequestNumber: '',
  //  sourceWarehouse: '',
  //  destinationWarehouse: '',
  //  expectedDate: ''
 
 };

 locationtypeList:Array<LocationType>=[];

 actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
 constructor(private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService, private loginservice: Loginservices, 
   private localservice: BtwarehouseService,private productService:BtproductService,private datePipe:DatePipe,private _global: GlobalserviceService) {
   this._activatedRoute.params.subscribe(pram => {
     this.locationId = pram['locationId'];
     this.getLocationById(this.locationId);
     this.getRootLocation(this.locationId);

     this.actionHistoryObjectAndId.objectId = '' + this.locationId;
     this.actionHistoryObjectAndId.objectName = 'Location';
   });
 }

 ngOnInit() {
   //this.getLocationById();
   this.getLoggedInUser();
 }
 rights(object: string, right: string): boolean {
   return this.loginservice.isUserValidFor(object, right);
 }
 getLoggedInUser() {
   this.loginData = this.loginservice.getLoggedInUser();
 }
 getLocationById(locationId) {
   this.spinner.show();
   this.localservice.getLocationById(locationId).subscribe(data => {
     this.spinner.hide();
     this.response = data;
     this.localres = data;
   }, error => {
     this.spinner.hide()
     this.message = error.error.message;
     this._global.alert({ type: 'alert', message: this.message });

   })
 }
 createLocatioModelOpen(loc: LocationResponse) {
   this.isEditMode = true;
   this.editLocation.show();
   this.spinner.show();
   this.localservice.getLocationById(loc.locationId).subscribe(data => {
     this.spinner.hide();
     this.localres = data;
   }, error => {
     this.spinner.hide()
     this.message = error.error.message;
     this._global.alert({ type: 'alert', message: this.message });

   })


 }
 cancelLocationData() {
   this.isEditMode = false;
   //this.creatLocation.hide()
   this.editLocation.hide();
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
 subTabChange(tabName: string) {
   if (tabName == 'details') {
     this.ngClassDetails = 'active';
     this.ngClassReleated = '';
     this.vDetails = true;
     this.getLocationById(this.locationId);
     this.getRootLocation(this.locationId);
   }
   else {
     this.ngClassDetails = '';
     this.ngClassReleated = 'active';
     this.vDetails = false;
     this.getLocationTypeForList();
     this.getProductType();
     this.getChildLocation(this.locationId, 0);
     this.getLocationProduct(this.locationId, 0);
     this.getProductRequestReceived(this.locationId, 0);
     this.getProeductRequestSend(this.locationId, this.sntReqRecivPage);
   }
   return false;
 }
 // pageing in related
 setPagePrevious(event: any) {
   event.preventDefault();
   this.loationPage = this.loationPage - 1;
   this.getLocationProduct(this.locationId, this.loationPage);
 }

 setPage(event: any) {
   event.preventDefault();
   this.getLocationProduct(this.locationId);
 }

 setPageNext(event: any) {
   event.preventDefault();
   this.loationPage = this.loationPage + 1;
   this.getLocationProduct(this.locationId, this.loationPage);
 }
 getRootLocation(locationId) {
   this.spinner.show();
   this.localservice.getRootLocation(locationId).subscribe(data => {
     this.rootLocationDetails = data;
     this.spinner.hide();
   }, error => {
     this.spinner.hide()
   });
 }
 chileLocPage: number = 0;
 childLocationSize:number=5;
 getChildLocation(locationid, page?) {
   this.childLoactionDetails.content = [];
   this.chileLocPage = page;
   this.spinner.show();
   this.localservice.getChildLocation(locationid, this.chileLocPage,this.childLocationSize).subscribe(data => {
     this.childLoactionDetails = data;
     this.serialNumberChild = (this.chileLocPage * data.size);
     this.spinner.hide();
   }, error => {
     this.spinner.hide();
     this.message = error.error.message;
     this._global.alert({ type: 'alert', message: this.message });
    });
 }
 setPagePreviousChild(event: any) {
   event.preventDefault();
   this.chileLocPage = this.chileLocPage - 1;
   this.getChildLocation(this.locationId, this.chileLocPage);
 }

 setPageChild(event: any) {
   event.preventDefault();
   this.getChildLocation(this.locationId, this.chileLocPage);
 }

 setPageNextChild(event: any) {
   event.preventDefault();
   this.chileLocPage = this.chileLocPage + 1;
   this.getChildLocation(this.locationId, this.chileLocPage);
 }
 loationPage: number = 0;
 locationPageSize:any=5;
 getLocationProduct(locationid, page?) {
   this.loationPage = page;
   this.locationProductItem.content = [];
   this.spinner.show();
   this.localservice.getLocationProductItems(locationid, this.loationPage,this.locationPageSize).subscribe(data => {
     this.locationProductItem = data;
     this.serialNumber = (this.loationPage * data.size);
     this.spinner.hide();
   }, error => {
     this.spinner.hide();
     this.message = error.error.message;
     this._global.alert({ type: 'alert', message: this.message });
    });
 }
 onMslRequestCancel($event) {
   this.mslRequest.hide();
 }
 onMslCreated($event) {
   this.alerts.type = 'success';
   this.message = `MSL Request Saved`;
   this.mslRequest.hide();
   this._global.alert({ type: '', message: this.message });
   this.getLocationById(this.locationId)
   this.getChildLocation(this.locationId);
   this.getLocationProduct(this.locationId);
   this.getProductRequestReceived(this.locationId, 0);
   this.getProeductRequestSend(this.locationId);
 }
//  onProductRequestCancled($event) {

//    this.productRequestModal.hide();
//  }
//  onProductRequestSaved($event) {
//    this.alerts.type = 'success';
//    this.message = `Product Request <a href='/#/productrequest/${$event.id}'>${$event.manualId}</a> Created Successfully`;
//    this.productRequestModal.hide();
//    this._global.alert({ type: '', message: this.message });
//    this.getChildLocation(this.locationId);
//    this.getLocationProduct(this.locationId);
//    this.getProductRequestReceived(this.locationId, 0);
//    this.getProeductRequestSend(this.locationId,0);
//  }
 sntReqRecivPage: any = 0;
 sntReqRecivSize:any=5;
 getProeductRequestSend(locationid, page?) {
   // this.productRequestSent.size

   this.sntReqRecivPage = page;
   this.spinner.show();
   // if (page) {
     this.productRequestSent.content=[];
     this.localservice.getSendproductRequest(locationid,this.sntReqRecivPage,this.sntReqRecivSize).subscribe(data => {
       this.productRequestSent = data;
       this.productRequestSent.totalElements;
       // console.log(this.productRequestSent.totalElements);
       this.sentRequisitionSerialNumber = (this.sntReqRecivPage * data.size)
       this.spinner.hide();
     }, error => {
       this.spinner.hide();
       this.message = error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
      });
   
   //  else {
   //   console.log("no page");
   //   this.localservice.getSendproductRequest(locationid).subscribe(data => {
   //     this.productRequestSent = data;
   //     this.spinner.hide();
   //   }, error => {
   //     this.spinner.hide();
   //     this.message = error.error.message;
   //     this.popupSuccessModel.show();
   //   });
   // }

 }
 ReqRecivpage: number = 0;
 ReqRecivsize:any=5;
 getProductRequestReceived(locationid, page?) {
   this.ReqRecivpage = page;
   this.spinner.show();
   this.productRequestReceive.content=[];
   this.localservice.getReceivedRequest(locationid, this.ReqRecivpage,this.ReqRecivsize).subscribe(data => {
     this.productRequestReceive = data;
     this.productRequestReceive.totalElements
     this.receivedRequisitionSerialNumber = (this.ReqRecivpage * data.size)

     this.spinner.hide();
   }, error => {
     this.spinner.hide();
     this.message = error.error.message;
     this._global.alert({ type: 'alert', message: this.message });
    });
 }

 onLocationCreated($event) {
   this.response = $event;
   this.getChildLocation(this.locationId);
   this.getLocationProduct(this.locationId);
   this.getProductRequestReceived(this.locationId, 0);
   this.getProeductRequestSend(this.locationId);

 }
 saveBtProductReturn($event) {
   this.alerts.type = 'success';
   this.message = `Return order <a href="#/productreturnorder/${$event.lprOrderid}">${$event.lprOrderidManual} Created Successfully </a> `;
   this.btProductReturn.hide();
   this._global.alert({ type: '', message: this.message });
   this.getChildLocation(this.locationId);
   this.getLocationProduct(this.locationId);
   this.getProductRequestReceived(this.locationId, 0);
   this.getProeductRequestSend(this.locationId);
 }
 cancelProductReturn($evvent) {
   this.btProductReturn.hide();
 }


 setSentRequisitionPageNext(event: any) {
   event.preventDefault();
   this.sntReqRecivPage = this.sntReqRecivPage + 1;

   this.getProeductRequestSend(this.locationId, this.sntReqRecivPage);
 }

 setSentRequisitionPagePrevious(event: any) {
   event.preventDefault();
   this.sntReqRecivPage = this.sntReqRecivPage - 1;

   this.getProeductRequestSend(this.locationId, this.sntReqRecivPage);
 }


 setReceivedRequisitionPageNext(event: any) {
   event.preventDefault();
   this.ReqRecivpage = this.ReqRecivpage + 1
   // this.receivedRequisitionSerialNumber = this.receivedRequisitionSerialNumber+1;
   this.getProductRequestReceived(this.locationId, this.ReqRecivpage);
 }

 setReceivedRequisitionPagePrevious(event: any) {
   event.preventDefault();
   this.ReqRecivpage = this.ReqRecivpage - 1
   // this.receivedRequisitionSerialNumber = this.receivedRequisitionSerialNumber-1;
   this.getProductRequestReceived(this.locationId, this.ReqRecivpage);
 }
 locationSerialNumberList: Array<LocationPartCodeSerialNumber> = [];
 serialNumberDetaiDisp: boolean = false;
 expendPartcodeItem(partCode) {
   this.spinner.show();
   // this.serialNumberDetaiDisplay.open=!this.serialNumberDetaiDisplay.open;
   this.localservice.getserialNumberByLocationPartCode(this.locationId, partCode).subscribe(data => {
     this.spinner.hide();
     this.locationSerialNumberList = data;
     if (this.locationSerialNumberList != null) {
       this.serialNumberDetaiDisp = true;
     }
     else {
       this.serialNumberDetaiDisp = false;
     }

   })
 }

 searchChildLoc() {
  
   if(this.LocaChildadvancesearch.legalEntityName=='' && this.LocaChildadvancesearch.locationName==''
    && this.LocaChildadvancesearch.locationTypeName=='' && this.LocaChildadvancesearch.owner=='' &&
    this.LocaChildadvancesearch.plantcode==''  && this.LocaChildadvancesearch.inventoryWarehouse=='' &&
    this.LocaChildadvancesearch.mobileWarehoue==''){
    this.message="Please Enter Valid Key For Search";
    this._global.alert({ type: 'alert', message: this.message });
  }
   else{
     this.spinner.show();
     this.localservice.getChildLocSearchResult(this.LocaChildadvancesearch,this.locationId).subscribe(data => {
       this.spinner.hide();
       this.childLoactionDetails.content = data;
       this.page = 0;
       this.size = data.length;
       this.first = true;
       this.last = true;
     },error=>{
       this.spinner.hide();
       this.message=error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
      });
   }
 
 }
 locChildresetsearch(){
   this.LocaChildadvancesearch.locationName= ''
   this.LocaChildadvancesearch.locationTypeName= ''
  this.LocaChildadvancesearch.legalEntityName=''
  this.LocaChildadvancesearch.owner= ''
  this.LocaChildadvancesearch.plantcode= ''
  this.LocaChildadvancesearch.inventoryWarehouse=''
  this.LocaChildadvancesearch.mobileWarehoue=''
  this.getChildLocation(this.locationId, 0);

 //  this.LocaChildadvancesearch.
 
 }
 getLocationTypeForList(){
   this.spinner.show();
   this.localservice.locationTypeForlist().subscribe(data=>{
     this.spinner.hide();
     this.locationtypeList=data;
   })
 }
 searchPrItemLoc(){
   if(this.ProductItemAdvancesearch.locationName== ''&&
   this.ProductItemAdvancesearch.locationPlant== '' &&
   this.ProductItemAdvancesearch.productDescription=='' &&
   this.ProductItemAdvancesearch.productPartcode=='' &&
   this.ProductItemAdvancesearch.productType==''){
     this.message="Please Enter Valid Key For Search";
     this._global.alert({ type: 'alert', message: this.message });
    }
   else
   {
     this.spinner.show()
   this.localservice.getProductItemLocSearchResult(this.ProductItemAdvancesearch,this.locationId).subscribe(data=>{
  this.spinner.hide();
  this.locationProductItem.content=data;
   },error=>{
     this.spinner.hide();
       this.message=error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
      })
 }
 }
 resetProductItemADVSearch(){
   this.ProductItemAdvancesearch.locationName=''
   this.ProductItemAdvancesearch.locationPlant=''
   this.ProductItemAdvancesearch.productDescription=''
   this.ProductItemAdvancesearch.productType=''
   this.ProductItemAdvancesearch.productPartcode=''
 }
 getProductType(){
   this.productService.getTypeForAdv().subscribe(data=>{
    this.spinner.hide();
 this.proType=data;
   })

 }
 SearchSendReq(){
   if(
     this.SendReqAdvancesearch.productRequestManualId=='' &&
     this.SendReqAdvancesearch.productRequestDestinationLocationName == '' &&
     this.SendReqAdvancesearch.productRequestFromDate==''&&
     this.SendReqAdvancesearch.productRequestSoruceLocationName==''&&
     this.SendReqAdvancesearch.productRequestStatus==''
   ){
     this.message="Please Enter valid Key For Search";
     this._global.alert({ type: 'alert', message: this.message });
    }
   else{ 
    this.spinner.show();
    if(this.SendReqAdvancesearch.productRequestFromDate!=''){
     this.SendReqAdvancesearch.productRequestFromDate= this.datePipe.transform(this.SendReqAdvancesearch.productRequestFromDate, 'yyyy-MM-dd');
   }
   this.localservice.getSendReqSearchResult(this.SendReqAdvancesearch,this.locationId).subscribe(data=>{
     this.spinner.hide();
     this.productRequestSent.content = data;
   },error=>{
     this.spinner.hide();
       this.message=error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
      })
 }
 }
 locSearchSenReqreset(){
   this.SendReqAdvancesearch.productRequestDestinationLocationName= ''
   this.SendReqAdvancesearch.productRequestFromDate = ''
   this.SendReqAdvancesearch.productRequestManualId= ''
   this.SendReqAdvancesearch.productRequestSoruceLocationName=''
   this.SendReqAdvancesearch.productRequestStatus=''


 }
 SearchReceiev(){
   if(
     this.RecievReqAdvancesearch.productRequestDestinationLocationName=='' &&
     this.RecievReqAdvancesearch.productRequestFromDate == '' &&
     this.RecievReqAdvancesearch.productRequestManualId==''&&
     this.RecievReqAdvancesearch.productRequestSoruceLocationName==''&&
     this.RecievReqAdvancesearch.productRequestStatus==''
   ){
     this.message="Please Enter valid Key For Search";
     this._global.alert({ type: 'alert', message: this.message });
    }
   else{ 
    this.spinner.show();
    if(this.RecievReqAdvancesearch.productRequestFromDate!=''){
     this.RecievReqAdvancesearch.productRequestFromDate= this.datePipe.transform(this.RecievReqAdvancesearch.productRequestFromDate, 'yyyy-MM-dd');
   }
   this.localservice.getReciveReqSearchResult(this.RecievReqAdvancesearch,this.locationId).subscribe(data=>{
     this.spinner.hide();
     this.productRequestReceive.content = data;
   },error=>{
     this.spinner.hide();
       this.message=error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
      })
 }
 }
 locSearchRecievereset(){
   this.RecievReqAdvancesearch.productRequestDestinationLocationName= ''
   this.RecievReqAdvancesearch.productRequestFromDate = ''
   this.RecievReqAdvancesearch.productRequestManualId= ''
   this.RecievReqAdvancesearch.productRequestSoruceLocationName=''
   this.RecievReqAdvancesearch.productRequestStatus=''


 }

}

