import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AccountService } from '../../../services/accounts.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, AccountsForList, PlantDetails } from '../../../services/model';
import { PlantService } from '../../../services/plants.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtproductService } from '../../bat_services/btproduct.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTasseReq, BTassetResp, BtAssetsStatus, BTProductForListResponse, BtProductList, BT_ProductForListDetail, BT_SiteDetailForList, GetAllBTsiteResp, Product } from '../../bt_model';

@Component({
  selector: 'app-bt-createasset',
  templateUrl: './bt-createasset.component.html',
  styleUrls: ['./bt-createasset.component.css']
})
export class BtCreateassetComponent implements OnInit {
 @ViewChild('createProductModel') createProductModel: ModalDirective;
  @ViewChild('accountModel') accountModel: ModalDirective;
  @ViewChild('createSiteModel') createSiteModel: ModalDirective;

  _isEditMode: boolean
  assetRequest: BTasseReq = new BTasseReq();
  assetResponse: BTassetResp = new BTassetResp();
  createAccount: AccountCreate = new AccountCreate();
  res: GetAllBTsiteResp = new GetAllBTsiteResp();
  createProduct: Product = new Product();
  _productList: Array<BT_ProductForListDetail> = [];
  productsForList: BT_ProductForListDetail = new BT_ProductForListDetail();
  plant: Array<PlantDetails> = [];
  accountForList: Array<AccountsForList> = []
  accountList: AccountsForList = new AccountsForList();
  status: Array<BtAssetsStatus> = [];
  siteList: Array<BT_SiteDetailForList> = [];
  siteForList: BT_SiteDetailForList = new BT_SiteDetailForList();
  message: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  isProductModelShow: boolean;
  isAccountModelShow: boolean;
  isSiteModelShow: boolean;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];

  idEditRights:boolean = true;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
    console.log(isEditMode)
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAssetData(assetResponse: BTassetResp) {
    if (assetResponse) {
      console.log(assetResponse);
      this.assetResponse = assetResponse;
      this.assetResponse.purchaseOrderDate = this.datePipe.transform(assetResponse.purchaseOrderDate, 'yyyy-MM-dd');
      this.assetResponse.purchaseDate = this.datePipe.transform(this.assetResponse.purchaseDate, 'yyyy-MM-dd');
      this.assetResponse.installDate = this.datePipe.transform(this.assetResponse.installDate, 'yyyy-MM-dd');
      this.assetResponse.warrantyExpiryDate = this.datePipe.transform(this.assetResponse.warrantyExpiryDate, 'yyyy-MM-dd');
        this.assetResponse.accountSapNumber=assetResponse.accountSapNumber;
        this.assetResponse.primaryAccountSapNumber=assetResponse.primaryAccountSapNumber;
        this.assetResponse.primaryAccountName=assetResponse.primaryAccountName;
        this.assetResponse.accountName=assetResponse.accountName;
        this.assetResponse.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd')
        this.assetResponse.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd')
        this.assetResponse.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd')
        this._productList.push({id:this.assetResponse.productID,
        partCode:this.assetResponse.productPartCode,
        productDescription:this.assetResponse.productDescription,
        productType:"Product"});
        this.updateProductList();
        this.siteList.push({batterySiteId:this.assetResponse.siteId,siteName:this.assetResponse.siteName,manuaSitelId:this.assetResponse.siteManualId});
        this.updateSiteList();
       
    }
    else {
      this.assetResponse = new BTassetResp();
    }
  }

  get editAssetData(): BTassetResp {
    return this.assetResponse;
  }

  @Output()
  onAssetCreated: EventEmitter<BTassetResp> = new EventEmitter<BTassetResp>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor(private spinner: NgxSpinnerService, 
    private _assetService: BTassetserviceService,
    private _accountService: BtaccountService,
    private _global:GlobalserviceService,
    private Btsiteservice:BtsiteserviceService,
   private _plantService: PlantService, 
   private datePipe: DatePipe,private loginService: Loginservices,
   private btproductservice:BtproductService,private caseservice:BtCaseserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }
  loginData:any
  getLoggedInUser() {
    this.loginData = this.loginService.getLoggedInUser();
    // this.domainId = this.loginData.emptype['businessDomainId']
    // this.loginData.empType['businessDomainId'];
  }
  ngOnInit() {
    // *ngIf="loginData.roleGroupAndAccount.roleGroup=='COMMERCIAL'"
    //this.getProductDetails();
    this.getPlantDetails();
    // this.getSitesForList();
    this.getAssetStatus();
    this.getAccountForList();
    this.getLoggedInUser();
    this.faPlus;
    // if(this.rights('BtAssetsWarrantyDetails','create') || this.rights('BtAssetsWarrantyDetails','edit')){
    //   this.isEditMode=false;
    // }else{
    //   this.isEditMode = true;
    // }
  }
  getAccountForList() {
    this._accountService.getBtAccountsForList().subscribe(data => {
      this.accountForList = data;
    }, err => {

    });
  }
  productsvalues:string='';
  // getProductDetails() {
  //   this._assetService.getProductForList('Product').subscribe(data => {
  //     this._productList = data;
  //     data.forEach(e=>{
  //       let pro=new ProductsForList();
  //       pro.productPartCode=e.productPartCode;
  //       pro.productDescription=e.productDescription;
  //       pro.bothvalues=e.productPartCode+' '+e.productDescription;
  //       this._productList.push(pro);
  //     })

  //   })
  // }
  getPlantDetails() {
    this._plantService.getPlants().subscribe(data => {
      this.plant = data;
    }, err => {
      this._global.alert({type:'alert',message:err.error.message})

    });
  }
  getAssetStatus() {
    this._assetService.getAssetStatus().subscribe(data => {
      this.status = data;
    }, err => {

    });
  }
  getSiteForList(key) {
      this.caseservice.getSitesForListTini(key.term).subscribe(data => {
        this.siteList = data;
       
      });
    
  }

  postCreateAsset() {
    console.log(this._isEditMode);
    // console.log((this.assetResponse.warrantyFromDateOfDeliveryInMonth).toString().length);
    if (this.assetResponse.serialNumber == null || this.assetResponse.serialNumber =='' || this.assetResponse.serialNumber == undefined) {
      this.message = 'Please Fill The Serial Number';
      this._global.alert({type:'alert',message:this.message})
    }
    else if (this.assetResponse.serialNumber != undefined && (this.assetResponse.serialNumber).length >= 200) {
      this.message = 'Please Fill The Serial Number Under 200 Characters'
      this._global.alert({type:'alert',message:this.message})
    }
    else if (this.assetResponse.productID == null || this.assetResponse.productID ==0) {
      this.message = 'Please Enter Partcode'
      this._global.alert({type:'alert',message:this.message})
    }

    else if (this.assetResponse.accountSapNumber == null || this.assetResponse.accountSapNumber == '') {
      this.message = 'Please Fill The Account Name'
      this._global.alert({type:'alert',message:this.message})
    }

    else if (this.assetResponse.assetsStatusId == null || this.assetResponse.assetsStatusId === 0) {
      this.message = 'Please Fill The Status'
      this._global.alert({type:'alert',message:this.message})
    }
    // else if (this.assetResponse.plantCode == null || this.assetResponse.plantCode === '0') {
    //   this.message = 'Please fill the plant '
    //   this.popupSuccessModel.show();
    // }
    else if (this.assetResponse.remarks != undefined && (this.assetResponse.remarks).length >= 500) {
      this.message = 'Please Fill The Remarks Under 200 Characters'
      this._global.alert({type:'alert',message:this.message})
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.warrantyFromDateOfDeliveryInMonth) ||
      (this.assetResponse.warrantyFromDateOfDeliveryInMonth != undefined && (this.assetResponse.warrantyFromDateOfDeliveryInMonth).toString().length) > 2){
      
      this.message = 'Please Fill The Valid 2 Digit Warranty From DOD(months)';
      this._global.alert({type:'alert',message:this.message})
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.warrantyFromDateOfInstallationInMonth) ||
      (this.assetResponse.warrantyFromDateOfInstallationInMonth != undefined && (this.assetResponse.warrantyFromDateOfInstallationInMonth).toString().length>2)) {
      this.message = 'Please Fill The Valid 2 Digit Warranty From Installation(Months)';
      this._global.alert({type:'alert',message:this.message})
    }
    else if (this.assetResponse.amcPurchaseOrderNumber != undefined && (this.assetResponse.amcPurchaseOrderNumber) > 200) {
      this.message = 'Please Fill The AMC PO Number Under 200 Characters';
      this._global.alert({type:'alert',message:this.message})
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.amcPeriod) ||
      (this.assetResponse.amcPeriod != undefined && (this.assetResponse.amcPeriod).length > 3)) {
      this.message = 'Please Fill The Valid 3 Digit AMC Period(Months)';
      this._global.alert({type:'alert',message:this.message})
    }

    else {
      if (!this._isEditMode) {
        this.assetRequest.serialNumber = this.assetResponse.serialNumber;
        this.assetRequest.productId = this.assetResponse.productID;
        this.assetRequest.accountSapNumber = this.assetResponse.accountSapNumber;
        this.assetRequest.plantCode = this.assetResponse.plantCode;
        this.assetRequest.siteId = this.assetResponse.siteId;
        this.assetRequest.assetsStatusId = this.assetResponse.assetsStatusId;
        this.assetRequest.primaryAccountSapNumber = this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.remarks = this.assetResponse.remarks;
      this.assetRequest.primaryAccountSapNumber=this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.purchaseOrderNumber = this.assetResponse.purchaseOrderNumber;
        this.assetRequest.purchaseOrderDate = this.datePipe.transform(this.assetResponse.purchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.sapDeliveryDocumentNumber = this.assetResponse.sapDeliveryDocumentNumber;
        this.assetRequest.sapMaterialDocumentNumber = this.assetResponse.sapMaterialDocumentNumber;

        this.assetRequest.purchaseDate = this.datePipe.transform(this.assetResponse.purchaseDate, 'yyyy-MM-dd');
        this.assetRequest.installDate = this.datePipe.transform(this.assetResponse.installDate, 'yyyy-MM-dd');
        this.assetRequest.warrantyFromDateOfDeliveryInMonth = Number(this.assetResponse.warrantyFromDateOfDeliveryInMonth);
        this.assetRequest.warrantyFromDateOfInstallationInMonth = Number(this.assetResponse.warrantyFromDateOfInstallationInMonth);

        this.assetRequest.amcPurchaseOrderNumber = this.assetResponse.amcPurchaseOrderNumber;
        this.assetRequest.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd');
        this.assetRequest.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd');
        this.assetRequest.amcPeriod = this.assetResponse.amcPeriod;

        this.spinner.show();
        this._assetService.postAssets(this.assetRequest).subscribe(data => {
          this.message = data.serialNumber+' Created Successfully';
          this.onAssetCreated.emit(data);
      
            this.spinner.hide();
          this._global.alert({type:'',message:this.message})
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({type:'alert',message:this.message})
        });
      }
      else {
    
        this.assetRequest.assetsId=this.assetResponse.id;
        this.assetRequest.serialNumber = this.assetResponse.serialNumber;
        this.assetRequest.productId = this.assetResponse.productID;
        this.assetRequest.accountSapNumber = this.assetResponse.accountSapNumber;
        this.assetRequest.primaryAccountSapNumber=this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.plantCode = this.assetResponse.plantCode;
        if(this.assetResponse.siteId!=null)
       { 
         this.assetRequest.siteId = this.assetResponse.siteId;
        }else{
        this.assetRequest.siteId =null;
       }
        this.assetRequest.assetsStatusId = this.assetResponse.assetsStatusId;
        this.assetRequest.primaryAccountSapNumber = this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.remarks = this.assetResponse.remarks;

        this.assetRequest.purchaseOrderNumber = this.assetResponse.purchaseOrderNumber;
        this.assetRequest.purchaseOrderDate = this.datePipe.transform(this.assetResponse.purchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.sapDeliveryDocumentNumber = this.assetResponse.sapDeliveryDocumentNumber;
        this.assetRequest.sapMaterialDocumentNumber = this.assetResponse.sapMaterialDocumentNumber;

        this.assetRequest.purchaseDate = this.datePipe.transform(this.assetResponse.purchaseDate, 'yyyy-MM-dd');
        this.assetRequest.installDate = this.datePipe.transform(this.assetResponse.installDate, 'yyyy-MM-dd');
        this.assetRequest.warrantyFromDateOfDeliveryInMonth = Number(this.assetResponse.warrantyFromDateOfDeliveryInMonth);
        this.assetRequest.warrantyFromDateOfInstallationInMonth = Number(this.assetResponse.warrantyFromDateOfInstallationInMonth);

        this.assetRequest.amcPurchaseOrderNumber = this.assetResponse.amcPurchaseOrderNumber;
        this.assetRequest.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd');
        this.assetRequest.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd');
        this.assetRequest.amcPeriod = this.assetResponse.amcPeriod;
    

        this.spinner.show();
        this._assetService.editAssets(this.assetRequest, this.assetResponse.id).subscribe(data => {
          this.message = data.serialNumber+' Updated Successfully';
          this.onCancle.emit('cancled');
          this.onAssetCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this._global.alert({type:'',message:this.message})
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({type:'alert',message:this.message})
        }
        );
      }
    }
  }
  cancelData() {
    this.assetRequest = new BTasseReq();
    this.onCancle.emit('cancled');
    
  }


  getPrimaryAccountByAccount(sapAccountNumber: string){
    if(sapAccountNumber !== null && sapAccountNumber !== undefined){
      this.accountForList.forEach(data =>{
        if(sapAccountNumber === data.sapAccountNumber){
        this.assetResponse.primaryAccountSapNumber = data.sapAccountNumber;
        this.assetResponse.primaryAccountName = data.accountName;
        }        
      });
    }
  }
  accountName(Acountres: BTassetResp) {
    let primaryAccountName: string;
    this.accountForList.forEach(element => {
      if (Acountres.accountName == element.accountName) {
        primaryAccountName = element.accountName;
      }
    });

    this.accountForList.forEach(element => {
      if (primaryAccountName == element.accountName) {
        this.assetResponse.primaryAccountSapNumber = element.sapAccountNumber;
        this.assetResponse.primaryAccountName = element.accountName;
      }
    });
  }
  accountModelhide() {
    this.createAccount = new AccountCreate();
    this.accountModel.hide();
  }
  addNewAccount() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.accountModel.show();
  }
  cancelDataAccount() {
    this.isEditMode = false;
    this.createAccount = new AccountCreate();
    this.accountModel.hide();
  }

  createProductModelhide() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  addNewProduct() {
    this.createProduct = new Product();
    this.isEditMode = false;
    this.createProductModel.show();
  }
  cancelDataProd() {
    this.isEditMode = false;
    this.createProduct = new Product();
    this.createProductModel.hide();
  }

  createSiteModelhide() {
    this.res = new GetAllBTsiteResp();
    this.createSiteModel.hide();
  }
  addNewSite() {
    this.res = new GetAllBTsiteResp();
    this.isEditMode = false;
    this.createSiteModel.show();
  }
  cancelDataSite() {
    this.isEditMode = false;
    this.res = new GetAllBTsiteResp();
    this.createSiteModel.hide();
  }
  onProductCreated($event) {
    this.assetResponse.productID=$event.id;
    this.assetResponse.productPartCode=$event.partCode;
    this._productList.push({id:$event.id,
      partCode:$event.partCode,
      productDescription:$event.productDescription,
      productType:"Product"});
      this.updateProductList();
  }
  onAccountCreated($event) {
    this.assetResponse.accountSapNumber = $event.sampAccountNumber;
    this.getAccountForList();
  }
  onSiteCreated($event: GetAllBTsiteResp) {
    this.siteList.push({batterySiteId:$event.siteId,siteName:$event.siteName,manuaSitelId:$event.siteManualId});
    this.updateSiteList();
  }
 
  productSelected($event:Product){
    if($event!=null || $event!=undefined)
    {
      this.assetResponse.productDescription=$event.productDescription;
    }
    else{
      this.assetResponse.productDescription=null;
    }
    
  }

  searchProducts($event){
    console.log($event);
    this.btproductservice.getSmallForlist($event.term).subscribe(data=>{
      this._productList = data;
      data.forEach(e=>{
        let pro=new BT_ProductForListDetail();
        pro.partCode=e.partCode;
        pro.id=e.id;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.partCode+' '+e.productDescription;
        this._productList.push(pro);
        // pro.productPartCode=e.partCode;
        // pro.productDescription=e.productDescription;
        // pro.bothvalues=e.partCode+' '+e.productDescription;
        // this._productList.push(pro);
    })
  });
  }
  updateSiteList(){
    let temp=[];
    const sfound=[];
    for(let i=0;i<this.siteList.length;i++){
      let found=false;
      for(let j=0;j<sfound.length;j++){
        if(sfound[j]==this.siteList[i].batterySiteId){
          found=true;
          break;
        }
      }
      if(!found){
        if(this.siteList[i].manuaSitelId!=null&&this.siteList[i].manuaSitelId!=undefined&&this.siteList[i].manuaSitelId!=''){
          temp.push({batterySiteId:this.siteList[i].batterySiteId,manuaSitelId:this.siteList[i].manuaSitelId,siteName:this.siteList[i].siteName});
          sfound.push(this.siteList[i].batterySiteId);
        }
        
      }
    }
    this.siteList=temp;
  }
  updateProductList(){
    let temp=[];
    const sfound=[];
    for(let i=0;i<this._productList.length;i++){
      let found=false;
      for(let j=0;j<sfound.length;j++){
        if(sfound[j]==this._productList[i].id){
          found=true;
          break;
        }
      }
      if(!found){
        if(this._productList[i].partCode!=null&&this._productList[i].partCode!=undefined&&this._productList[i].partCode!=''){
          temp.push({id:this._productList[i].id,partCode:this._productList[i].partCode,productDescription:this._productList[i].productDescription,productType:this._productList[i].productType});
          sfound.push(this._productList[i].id);
        }
        
      }
    }
    this._productList=temp;
  }

  
  //Validation for numeric type value

  keyPressNumbersWithDecimal(event) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode != 46 && charCode > 31
      && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
      return false;
    }
    return true;
  }


  }
