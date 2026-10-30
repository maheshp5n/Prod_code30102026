import { Component, OnInit, Output, EventEmitter, ViewChild, Input } from '@angular/core';
import { AssetsRequest, AccountCreate, PlantDetails, AssetsResponse, AccountsForList, SiteDetailsForList, ProductsForList, AssetsStatus, Product, SiteDetailsResponse, SitesForList } from '../../services/model';
import { AssetsService } from '../../services/assets.services';
import { AccountService } from '../../services/accounts.services';
import { PlantService } from '../../services/plants.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { DatePipe } from '@angular/common';
import { FieldValidation } from '../../services/validation.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-createasset',
  templateUrl: './createasset.component.html',
  styleUrls: ['./createasset.component.css']
})
export class CreateassetComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel', { static: false }) createProductModel: ModalDirective;
  @ViewChild('accountModel', { static: false }) accountModel: ModalDirective;
  @ViewChild('createSiteModel', { static: false }) createSiteModel: ModalDirective;

  _isEditMode: boolean
  assetRequest: AssetsRequest = new AssetsRequest();
  assetResponse: AssetsResponse = new AssetsResponse();
  createAccount: AccountCreate = new AccountCreate();
  res: SiteDetailsResponse = new SiteDetailsResponse();
  createProduct: Product = new Product();
  _productList: Array<ProductsForList> = [];
  productsForList: ProductsForList = new ProductsForList();
  plant: Array<PlantDetails> = [];
  accountForList: Array<AccountsForList> = []
  accountList: AccountsForList = new AccountsForList();
  status: Array<AssetsStatus> = [];
  siteList: Array<SiteDetailsForList> = [];
  siteForList: SiteDetailsForList = new SiteDetailsForList();
  message: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  isProductModelShow: boolean;
  isAccountModelShow: boolean;
  isSiteModelShow: boolean;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(assetResponse: AssetsResponse) {
    if (assetResponse) {
      this.assetResponse = assetResponse;
      this.assetResponse.purchaseOrderDate = this.datePipe.transform(assetResponse.purchaseOrderDate, 'yyyy-MM-dd')
      this.assetResponse.purchaseDate = this.datePipe.transform(this.assetResponse.purchaseDate, 'yyyy-MM-dd')
      this.assetResponse.installDate = this.datePipe.transform(this.assetResponse.installDate, 'yyyy-MM-dd')
      this.assetResponse.warrantyExpiryDate = this.datePipe.transform(this.assetResponse.warrantyExpiryDate, 'yyyy-MM-dd')

      this.assetResponse.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd')
      this.assetResponse.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd')
      this.assetResponse.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd')
      this._productList.push({productPartCode:this.assetResponse.productPartCode,productDescription:this.assetResponse.productDescription});
      
      //shagufta
      this.spinner.show();
      this._assetService.getSitesForList().subscribe(data => {
        this.siteList = data;
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message=err.error.message;
        this.popupSuccessModel.show();
      });
    }
    else {
      this.assetResponse = new AssetsResponse();
    }
  }

  get editAccountData(): AssetsResponse {
    return this.assetResponse;
  }

  @Output()
  onAssetCreated: EventEmitter<AssetsRequest> = new EventEmitter<AssetsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private spinner: NgxSpinnerService, private _assetService: AssetsService, private _accountService: AccountService,
    private _plantService: PlantService, private datePipe: DatePipe,private loginService: Loginservices) {
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
    this.getSitesForList();
    this.getAssetStatus();
    this.getAccountForList();
    this.getLoggedInUser();
    this.faPlus;
  }
  getAccountForList() {
    this._accountService.getAccountsForList().subscribe(data => {
      this.accountForList = data;
    }, err => {

    });
  }
  productsvalues:string='';
  getProductDetails() {
    this._assetService.getProductForList('Product').subscribe(data => {
      // const pro=new ProductsForList();
      this._productList = data;
      data.forEach(e=>{
        let pro=new ProductsForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this._productList.push(pro);
      })

    })
  }
  getPlantDetails() {
    this._plantService.getPlants().subscribe(data => {
      this.plant = data;
    }, err => {

    });
  }
  getAssetStatus() {
    this._assetService.getAssetStatus().subscribe(data => {
      this.status = data;
    }, err => {

    });
  }
  getSitesForList() {
    this.spinner.show();
    this._assetService.getSitesForList().subscribe(data => {
      this.siteList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message=err.error.message;
      this.popupSuccessModel.show();
    });
  }

  postCreateAccount(assetResponse: AssetsResponse) {
    if (this.assetResponse.serialNumber == null || this.assetResponse.serialNumber =='' || this.assetResponse.serialNumber == undefined) {
      this.message = 'Please fill the serial number';
      this.popupSuccessModel.show();
    }
    else if (this.assetResponse.serialNumber != undefined && (this.assetResponse.serialNumber).length >= 200) {
      this.message = 'Please fill the serial number under 200 characters'
      this.popupSuccessModel.show();
    }
    else if (this.assetResponse.productPartCode == null || this.assetResponse.productPartCode === '') {
      this.message = 'Please fill the pard code'
      this.popupSuccessModel.show();
    }

    else if (this.assetResponse.accountSapNumber == null || this.assetResponse.accountSapNumber == '') {
      this.message = 'Please fill the account name'
      this.popupSuccessModel.show();
    }

    else if (this.assetResponse.assetsStatusId == null || this.assetResponse.assetsStatusId === 0) {
      this.message = 'Please fill the status'
      this.popupSuccessModel.show();
    }
    // else if (this.assetResponse.plantCode == null || this.assetResponse.plantCode === '0') {
    //   this.message = 'Please fill the plant '
    //   this.popupSuccessModel.show();
    // }
    else if (this.assetResponse.remarks != undefined && (this.assetResponse.remarks).length >= 500) {
      this.message = 'Please fill the remarks under 200 characters'
      this.popupSuccessModel.show();
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.warrantyFromDateOfDeliveryInMonth) ||
      (this.assetResponse.warrantyFromDateOfDeliveryInMonth != undefined && (this.assetResponse.warrantyFromDateOfDeliveryInMonth).length > 2)) {
      this.message = 'Please fill the valid 2 digit warranty from DOD(months) ';
      this.popupSuccessModel.show();
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.warrantyFromDateOfInstallationInMonth) ||
      (this.assetResponse.warrantyFromDateOfInstallationInMonth != undefined && (this.assetResponse.warrantyFromDateOfInstallationInMonth).length > 2)) {
      this.message = 'Please fill the valid 2 digit warranty from installation(months)';
      this.popupSuccessModel.show();
    }
    else if (this.assetResponse.amcPurchaseOrderNumber != undefined && (this.assetResponse.amcPurchaseOrderNumber).length > 200) {
      this.message = 'Please fill the amc po number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (!FieldValidation.isValidNumber(this.assetResponse.amcPeriod) ||
      (this.assetResponse.amcPeriod != undefined && (this.assetResponse.amcPeriod).length > 3)) {
      this.message = 'Please fill the valid 3 digit amc period(months)';
      this.popupSuccessModel.show();
    }

    else {
      if (!this._isEditMode) {
        this.assetRequest.serialNumber = this.assetResponse.serialNumber;
        this.assetRequest.productPardCode = this.assetResponse.productPartCode;
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
        this.assetRequest.warrantyFromDateOfDeliveryInMonth = this.assetResponse.warrantyFromDateOfDeliveryInMonth;
        this.assetRequest.warrantyFromDateOfInstallationInMonth = this.assetResponse.warrantyFromDateOfInstallationInMonth;

        this.assetRequest.amcPurchaseOrderNumber = this.assetResponse.amcPurchaseOrderNumber;
        this.assetRequest.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd');
        this.assetRequest.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd');
        this.assetRequest.amcPeriod = this.assetResponse.amcPeriod;

        this.spinner.show();
        this._assetService.postAssets(this.assetRequest).subscribe(data => {
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAssetCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      else {
        this.assetRequest.serialNumber = this.assetResponse.serialNumber;
        this.assetRequest.productPardCode = this.assetResponse.productPartCode;
        this.assetRequest.accountSapNumber = this.assetResponse.accountSapNumber;
        this.assetRequest.primaryAccountSapNumber=this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.plantCode = this.assetResponse.plantCode;
        this.assetRequest.siteId = this.assetResponse.siteId;
        this.assetRequest.assetsStatusId = this.assetResponse.assetsStatusId;
        this.assetRequest.primaryAccountSapNumber = this.assetResponse.primaryAccountSapNumber;
        this.assetRequest.remarks = this.assetResponse.remarks;

        this.assetRequest.purchaseOrderNumber = this.assetResponse.purchaseOrderNumber;
        this.assetRequest.purchaseOrderDate = this.datePipe.transform(this.assetResponse.purchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.sapDeliveryDocumentNumber = this.assetResponse.sapDeliveryDocumentNumber;
        this.assetRequest.sapMaterialDocumentNumber = this.assetResponse.sapMaterialDocumentNumber;

        this.assetRequest.purchaseDate = this.datePipe.transform(this.assetResponse.purchaseDate, 'yyyy-MM-dd');
        this.assetRequest.installDate = this.datePipe.transform(this.assetResponse.installDate, 'yyyy-MM-dd');
        this.assetRequest.warrantyFromDateOfDeliveryInMonth = this.assetResponse.warrantyFromDateOfDeliveryInMonth;
        this.assetRequest.warrantyFromDateOfInstallationInMonth = this.assetResponse.warrantyFromDateOfInstallationInMonth;

        this.assetRequest.amcPurchaseOrderNumber = this.assetResponse.amcPurchaseOrderNumber;
        this.assetRequest.amcPurchaseOrderDate = this.datePipe.transform(this.assetResponse.amcPurchaseOrderDate, 'yyyy-MM-dd');
        this.assetRequest.amcStartDate = this.datePipe.transform(this.assetResponse.amcStartDate, 'yyyy-MM-dd');
        this.assetRequest.amcExpiryDate = this.datePipe.transform(this.assetResponse.amcExpiryDate, 'yyyy-MM-dd');
        this.assetRequest.amcPeriod = this.assetResponse.amcPeriod;

        this.spinner.show();
        this._assetService.editAssets(this.assetRequest, this.assetRequest.serialNumber).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAssetCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      }
    }
  }
  cancelData() {
    this.assetRequest = new AssetsRequest();
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
  accountName(Acountres: AssetsResponse) {
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
    this.res = new SiteDetailsResponse();
    this.createSiteModel.hide();
  }
  addNewSite() {
    this.res = new SiteDetailsResponse();
    this.isEditMode = false;
    this.createSiteModel.show();
  }
  cancelDataSite() {
    this.isEditMode = false;
    this.res = new SiteDetailsResponse();
    this.createSiteModel.hide();
  }
  onProductCreated($event: Product) {
    this.getProductDetails();
    this.assetResponse.productPartCode = $event.partCode;
  }
  onAccountCreated($event: AccountCreate) {
    this.getAccountForList();
    this.assetResponse.accountSapNumber = $event.sampAccountNumber;
  }
  onSiteCreated($event: SiteDetailsResponse) {
    this.getSitesForList();
    this.assetResponse.siteId = $event.siteId;
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  productSelected($event:Product){
    this.assetResponse.productDescription=$event.productDescription;
    
  }

  searchProducts($event){
    console.log($event);
    this._assetService.getProductForListSmall($event.term).subscribe(data=>{
      this._productList = data;
      data.forEach(e=>{
        let pro=new ProductsForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this._productList.push(pro);
    })
  });
  }

}
