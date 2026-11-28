import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CirclesService } from '../../../services/circles.services';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, AccountsForList, CirclesForList, CirclesRequest, CirclesResponse, CustomerForList, LegalEntityResponse, ServiceCenterForList, ServiceResourseForList, SiteDetailsReq, SiteDetailsResponse, SSAForlist, SSAResponse, Zonedetails } from '../../../services/model';
import { siteService } from '../../../services/siteService.services';
import { ssaService } from '../../../services/ssa.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTsiteCreateReq, GetAllBTsiteResp } from '../../bt_model';

@Component({
  selector: 'app-bt-createsite',
  templateUrl: './bt-createsite.component.html',
  styleUrls: ['./bt-createsite.component.css']
})
export class BtCreatesiteComponent implements OnInit {
  // btSiteres: BTsiteCreateReq = new BTsiteCreateReq();


  ////////////////////////////////////////////////
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createAcountModel') createAcountModel: ModalDirective;
  @ViewChild('createServiceCenterModel') createServiceCenterModel: ModalDirective;
  @ViewChild('createCircleModel') createCircleModel: ModalDirective;
  @ViewChild('createSSAModel') createSSAModel: ModalDirective;
  @ViewChild('createCustomerModel') createCustomerModel: ModalDirective;

  isVisible: boolean = false;
  _isEditMode: boolean;
  isAssetSerialNo: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Account';
  EDIT_ACCOUNT_HEADING: string = 'Edit Account';
  siteres: GetAllBTsiteResp = new GetAllBTsiteResp();
  createAccount: AccountCreate = new AccountCreate();
  circlesList: Array<CirclesForList> = [];
  serviceResourcesList: Array<ServiceResourseForList> = [];
  req: BTsiteCreateReq = new BTsiteCreateReq()
  istrue: boolean;
  legalEntityName;
  AccountList: Array<AccountsForList> = [];
  customerList: Array<CustomerForList> = [];
  parentcustomer: CustomerForList = new CustomerForList();

  zoneList: Array<Zonedetails> = [];
  isAccountSelected: string
  isZoneSelected: string
  isCircleSelected: string
  isLegalEntitySelected: string
  accountList: Array<AccountCreate>
  noMatchCircle: string;
  noMatchZone: string;
  faPlus: string = "btn btn-primary fa fa-plus";
  circleList: Array<CirclesResponse> = [];
  // noMatchAsset: string;
  // noMatchserviceCenter: string
  assetSerailNoCase: string;
  siteManualIdTamp:string;
  siteIdTamp:number;
  serviceCenterList: Array<ServiceCenterForList> = []
  ssares: SSAResponse=new SSAResponse();
  isCustomerModelShow: boolean;
  isClonebt:string='';
  

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set isClone(isClone: string){
    this.isClonebt=isClone;


   
  }

  get isClone(): string{
    return this.isClonebt;
  }
  @Input()
  set isAssetSerialnmbr(isAssetSerialno: boolean) {

    this.isAssetSerialNo = isAssetSerialno;
  }
  get isAssetSerialnmbr(): boolean {
    return this.isAssetSerialNo
  }
  @Input()
  set assetSerialnoCase(assetIdCase: string) {
    this.assetSerailNoCase = assetIdCase;

  }
  get assetSerialnoCase(): string {
    return this.assetSerailNoCase;
  }

  @Input()
  set editEntityData(siteres: GetAllBTsiteResp) {
    this.siteres = siteres;
    this.noMatchCircle = '';
    if(this.isClonebt=="clone")
    {
      this.siteres.siteId==null;
      this.siteres.siteName="";
      this.siteres.siteManualId="";
    }
    if(siteres.serviceCenterId!=null){
      this.btSiteServise.getServiceResourcesList(siteres.serviceCenterId).subscribe(data => {
        this.spinner.hide();
        this.siteres.serviceCenterId =siteres.serviceCenterId;
        
        this.serviceResourcesList = data;
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message})

      });
      this.spinner.show()
      this.ssaservice.getSSAForlist(siteres.circleName).subscribe(data=>{
        this.spinner.hide();
        this.SSAList=data;
  
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message})
     
      })
    }
    if(this.isEditMode){
      this.isZoneSelected = "fa fa-times";
      this.isAccountSelected = "fa fa-times"
      this.isCircleSelected = "fa fa-times";
      this.isLegalEntitySelected = "fa fa-times";
    }
  }

  get editEntityData(): GetAllBTsiteResp {
    return this.siteres;
  }

  @Output()
  onSiteCreated: EventEmitter<BTsiteCreateReq> = new EventEmitter<BTsiteCreateReq>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onAccountCreatedAndNew: EventEmitter<BTsiteCreateReq> = new EventEmitter<BTsiteCreateReq>();

  modelmeta = { accountModelOpen: false, legalEntityModelOpen: false, circleModelOpen: false }
  constructor(private btSiteServise: BtsiteserviceService ,private _global:GlobalserviceService, private spinner: NgxSpinnerService, private loginservice: Loginservices,
    private ssaservice:ssaService,private circleservice:BtcircleService, private _customerService: CustomerService) { }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  // getTypeaheadConfig(): TypeaheadConfig {
  //   return Object.assign(new TypeaheadConfig(), { hideResultsOnBlur: false });
  // }

  cancelData() {
    if (this.isEditMode != true) {
      this.siteres = new GetAllBTsiteResp();
    }
    this.onCancle.emit('cancled');
  }

  cancelAccontData() {
    this.createAcountModel.hide();

  }
  cancelLegalentityData() {
    this.createServiceCenterModel.hide()
  }
  cancelCircleData() {
    this.createCircleModel.hide();
    this.isEditMode = false;
    // this.heading = this.CREATE_CIRCLE_HEADING;
    this.newcircles = new CirclesRequest();
  }

  onCircleCreated($event) {
    this.circlesList.push($event);
    this.siteres.circleName = $event.circleName;
    this.siteres.zoneName=$event.zone;

  }




  getAccountList() {
    this.spinner.show();
    this.btSiteServise.getAccountsForList().subscribe(data => {
      this.spinner.hide();
      this.AccountList = data
    })

  }
  getCircleList() {
    this.spinner.show();
    this.btSiteServise.getCircleList().subscribe(data => {
      this.spinner.hide();
      this.circlesList = data;
  

    })
  }
  
  getZoneList() {
    this.spinner.show();
    this.btSiteServise.getZoneforList().subscribe(data => {
      this.spinner.hide();
      this.zoneList = data;


    })
  }

    // parent customer
    createCustomerModelshow() {
      this.parentcustomer = new CustomerForList();
      this.createCustomerModel.show();
    }

    createCustomerModelhide() {
      this.parentcustomer = new CustomerForList();
      this.createCustomerModel.hide();
    }

  getAllCustomer() {
    this._customerService.getAllCustomer().subscribe(data => {
      this.customerList = data;
    }, err => {

    })
  }

  onParentCustomerCreated($event: CustomerForList) {
    this.getAllCustomer();
    // this.createAccount.parentCustomerId = $event.customerId;
    this.siteres.customer = $event.customerName;
  }

serviceResourceTem:Array<ServiceResourseForList>=[];
  getServiceResForList($event) {
   if($event!=null){
    this.spinner.show();
    this.serviceResourcesList=[];
    this.btSiteServise.getServiceResourcesList($event.legalEntityId).subscribe(data => {
      this.spinner.hide();
    const temp=[];
     data.forEach(e=>{
        let newel=new ServiceResourseForList();
       newel.accountName=e.accountName;
       newel.name=e.name;
       newel.serviceResourceId=e.serviceResourceId;
       newel.emailId=e.emailId;
       newel.bothvalue=newel.name+' '+newel.emailId;
        temp.push(newel);

      })
      this.serviceResourcesList=temp;
    
    });
    this.getCIrcleByLg($event.legalEntityId);
   }
  }
 
  getServiceCenter() {
    this.spinner.show();
    this.btSiteServise.getserviceCenterForList().subscribe(data => {
      this.spinner.hide();
      this.serviceCenterList = data;
    })
  }
 
  siteSave() {

    if (this.siteres.accountSapNumber == null || this.siteres.accountSapNumber == '') {
      this.message = 'Please Fill Account Name'
      this._global.alert({type:'alert',message:this.message})

    }
    else if(this.siteres.customer==null || this.siteres.customer==undefined){
      this.message='Please Fill Customer';
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.siteres.siteManualId == null || this.siteres.siteManualId == undefined || this.siteres.siteManualId=="") {
      this.message = 'Please Fill The Site ID';
      this._global.alert({type:'alert',message:this.message})

    }
    else if((this.siteres.siteManualId).length>200){
      this.message='Site Manual ID Exceed 200 Characters';
      this._global.alert({type:'alert',message:this.message})

    }
    else if (!FieldValidation.isValidName(this.siteres.siteName)) {
      this.message = 'Please Fill Valid Site Name '
      this._global.alert({type:'alert',message:this.message})

    }
    else if((this.siteres.siteName).length>200){
    this.message='Site Name Exceeds 200 Characters'
    this._global.alert({type:'alert',message:this.message})

    }
    else if (this.siteres.serviceCenterId == null || this.siteres.serviceCenterId == 0) {
    this.message = 'Please  Fill Valid Service Center Name'
      this._global.alert({type:'alert',message:this.message})

    }
   
    else if (this.siteres.serviceResourceId == null || this.siteres.serviceResourceId == 0) {
      this.message = 'Please  Select Service Resources Name'
      this._global.alert({type:'alert',message:this.message})


    }
    else if (this.siteres.circleName == null || this.siteres.circleName == ' ' || this.noMatchCircle == " ** Record not found") {
      this.message = 'Please Fill Circle Name'
      this._global.alert({type:'alert',message:this.message})


    }
    else if (FieldValidation.isValidName(this.siteres.clusterDistrict) == false) {
      this.message = 'Please  Fill District Name'
      this._global.alert({type:'alert',message:this.message})

    }
    else if((this.siteres.clusterDistrict).length>200){
      this.message='Cluster/District Exceed 200 Characters'
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.siteres.ssaId == null || this.siteres.ssaId == undefined) {
      this.message = 'Please  Fill SSA Name'
      this._global.alert({type:'alert',message:this.message})


    }

    else if (this.siteres.siteDescription == null || this.siteres.siteDescription == '') {
      this.message = 'Please  Fill Site Description'
      this._global.alert({type:'alert',message:this.message})
    }
 
    else if( this.siteres.siteAddress!=undefined && (this.siteres.siteAddress).length>200){
      this.message='Site Address Exceed 200 Characters'
      this._global.alert({type:'alert',message:this.message})

    }
  
    else if( this.siteres.siteDescription!=undefined && this.siteres.siteDescription!=null && (this.siteres.siteDescription).length>=500){
      this.message='Please Fill  Site Description Under 500 Characters';
      this._global.alert({type:'alert',message:this.message})

    }


    else {
      // this.istrue = true;
      if (this._isEditMode != true) {

        this.req.siteManualId = this.siteres.siteManualId
        this.req.accountSapNumber = this.siteres.accountSapNumber
        this.req.customer = this.siteres.customer;
        this.req.serviceCenterid = this.siteres.serviceCenterId
        this.req.serviceResourceId = this.siteres.serviceResourceId
        this.req.circleName = this.siteres.circleName
        // this.req.zoneName = this.siteres.zoneName
        this.req.clusterDistrict = this.siteres.clusterDistrict
        this.req.siteAddress = this.siteres.siteAddress
        this.req.siteDescription = this.siteres.siteDescription
        this.req.siteName = this.siteres.siteName
        this.req.infraId = this.siteres.infraId
        // this.req.ownerEmailId = this.siteres.ownerEmailId
        this.req.siteId = this.siteres.siteId;
        this.req.infraId = this.siteres.infraId;
        this.req.ssaId=this.siteres.ssaId;
        this.req.cmp = this.siteres.siteCmp;
        this.req.rjilJcName=this.siteres.RjilJcName;

        console.log(this.req)
        this.spinner.show()
        // if (this.isAssetSerialNo) {
        //   this.siteservice.saveSiteByCase(this.req, this.assetSerailNoCase).subscribe(data => {
        //     this.siteIdTamp=data.siteId;
        //     this.siteManualIdTamp=data.siteManualId;
        //     this.spinner.hide();
        //     this.onSiteCreated.emit(data);
        //     this.message = 'Created Successfully';
        //     this.popupNgClass = 'alert alert-success center center-block';
        //     this.spinner.hide()
        //     this.popupSuccessModel.show();

        //   }, error => {
        //     this.spinner.hide()
        //     this.message = error.error.message;
        //     this.popupSuccessModel.show()
        //     // setTimeout(() => {
        //     //   this.popupSuccessModel.hide()
        //     // }, 2000
        //     // );

        //   })
        // }
        // else {

          this.btSiteServise.getCreateBtSite(this.req).subscribe(data => {
            // this.message = 'Created Successfully';
            // this.popupNgClass = 'alert alert-success center center-block';
            this.spinner.hide();
            this.siteIdTamp=data.siteId;
            this.siteManualIdTamp=data.siteManualId;
            this._global.alert({type:'',message:this.siteManualIdTamp+" Created Successfully"})
            this.onSiteCreated.emit(data);
            this.onCancle.emit('cancled');
            // setTimeout(() => {
            //   this.popupSuccessModel.hide();
            //   this.onCancle.emit('cancled');
            // }, 2000
            // );
          }, error => {
            this.spinner.hide()
            this._global.alert({type:'alert',message:error.error.message})

         

          }

          );
        // }

      }

      else {

        this.req.siteManualId = this.siteres.siteManualId
        this.req.accountSapNumber = this.siteres.accountSapNumber;
        this.req.customer = this.siteres.customer;
        this.req.primaryAccountSapNumber = this.siteres.primaryAccountSapNumber;
        this.req.serviceCenterid = this.siteres.serviceCenterId
        this.req.serviceResourceId = this.siteres.serviceResourceId
        this.req.circleName = this.siteres.circleName
        // this.req.zoneName = this.siteres.zoneName
        this.req.clusterDistrict = this.siteres.clusterDistrict
        this.req.siteAddress = this.siteres.siteAddress
        this.req.siteDescription = this.siteres.siteDescription
        this.req.siteName = this.siteres.siteName
        this.req.infraId = this.siteres.infraId
        // this.req.ownerEmailId = this.siteres.ownerEmailId
        this.req.siteId = this.siteres.siteId
        this.req.cmp = this.siteres.siteCmp
        // this.req.infraId = this.siteres.infraId
        this.req.ssaId=this.siteres.ssaId;
        this.req.rjilJcName=this.siteres.RjilJcName;

        if(this.isClonebt=="edit")
        {
        this.spinner.show()
        this.btSiteServise.updateBtSite(this.req).subscribe(data => {
          this.spinner.hide()
          // this.message = 'Updated Successfully'
          this._global.alert({type:'',message:data.siteManualId+" Updated Successfully"})
          this.onSiteCreated.emit(data);
          this.onCancle.emit('cancled');

          // this.popupNgClass = 'alert alert-success center center-block';
          this.siteIdTamp=data.siteId;
          this.siteManualIdTamp=data.siteManualId;
          // this.popupSuccessModel.show();
          // setTimeout(() => {
          //   this.popupSuccessModel.hide();
          //   this.onCancle.emit('cancled');
          // }, 2000
          // );

        }, err => {
          this.spinner.hide()
          this._global.alert({type:'alert',message:err.error.message})

        
        }
        );
      }
      else{
        this.spinner.show();
        this.btSiteServise.getCreateBtSite(this.req).subscribe(data => {
          // this.message = 'Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.siteIdTamp=data.siteId;
          this.siteManualIdTamp=data.siteManualId;
          this._global.alert({type:'',message:this.siteManualIdTamp+" Created Successfully"})
          this.onSiteCreated.emit(data);
          this.onCancle.emit('cancled');
          this.isClonebt="edit";
          // setTimeout(() => {
          //   this.popupSuccessModel.hide();
          //   this.onCancle.emit('cancled');
          // }, 2000
          // );
        }, error => {
          this.spinner.hide()
          this._global.alert({type:'alert',message:error.error.message})

       

        }

        );

      }
      }

    }
    // if (!this.istrue) {
    //   this.popupSuccessModel.show();
   
    // }



  }
  loginData: any
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  ngOnInit() {
    this.getLoggedInUser()
    this.getAccountList()
    // this.getCircleList();
    this.getServiceCenter();
    this.getAllCustomer();
    this.isZoneSelected = "fa fa-search";
    this.isAccountSelected = "fa fa-search"
    this.isCircleSelected = "fa fa-search";
    this.isLegalEntitySelected = "fa fa-search";

    if (!this.isEditMode) {
      this.siteres = new GetAllBTsiteResp();

    }
  

  }

  getPrimaryaccountName($event: BTsiteCreateReq) {
    let primaryAccountNumber: string;
    this.AccountList.forEach(element => {
      if ($event.accountSapNumber == element.sapAccountNumber) {

        primaryAccountNumber = element.primaryAccountSampNumber;
      }
    }
    );

    if (primaryAccountNumber != null) {
      this.AccountList.forEach(element => {
        if (primaryAccountNumber == element.sapAccountNumber) {
          this.siteres.primaryAccountSapNumber = element.sapAccountNumber;
          this.siteres.primaryAccountName = element.accountName;

        }
      });
    } else {
      this.siteres.primaryAccountSapNumber = null;

      this.siteres.primaryAccountName = null;
    }




  }
 

SSAList:Array<SSAForlist>
  circleNameSelect($event) {
  if($event!=null){
    this.SSAList=[]
    this.siteres.ssaId=null;
     this.siteres.circleName=$event.circleName;
     this.spinner.show()
    this.ssaservice.getSSAForlist(this.siteres.circleName).subscribe(data=>{
      this.spinner.hide();
      this.SSAList=data;

    })
  }
  else{
    this.SSAList=[];
    this.siteres.ssaId=null;
    this.siteres.ssaName=null;
    this.siteres.zoneName=null;
  }
    

  }
  // ==========end=======================

  // ============Service center  search field====
  LegalEntityTimes() {
    this.isLegalEntitySelected = "fa fa-search";
    this.siteres.serviceCenterId = null;
    this.siteres.serviceCenterId = null;
  }

  onKeydownLegalEntity($event) {
    this.isLegalEntitySelected = "fa fa-times";
  }

  


 

  accountSelected($event) {
    // this.siteres.accountSapNumber = $event.item.sapAccountNumber
   if($event!=null){
    this.siteres.primaryAccountSapNumber = $event.primaryAccountSampNumber;
   }
   else{
    this.siteres.primaryAccountSapNumber=null;
   }
  }
  addNewAccount() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();

  }
  createAcountModelhide() {
    this.createAcountModel.hide();
  }
  // ==========create service center model=======
  addNewServiceCenter() {
    this.isEditMode = false;
    this.heading = "Create Legal Entity";
    this.createServiceCenterModel.show();

  }
  createServiceCenterModelhide() {
    this.createServiceCenterModel.hide()
  }
  newcircles: CirclesRequest = new CirclesRequest()
  addNewCircle() {
    this.isEditMode = false;
    this.heading = "Create Circle";
    this.newcircles = new CirclesRequest();
    this.createCircleModel.show();
  }
  recent: any
  onEntityCreated($event:LegalEntityResponse) {
 this.getServiceCenter();
    this.siteres.serviceCenterId = $event.legalentityId;
    this.btSiteServise.getServiceResourcesList(this.siteres.serviceCenterId).subscribe(data => {
      this.spinner.hide();
      this.serviceResourcesList = data;
    
    });
  }


  onAccountCreated($event) {
     this.getAccountList();
    this.siteres.primaryAccountSapNumber = $event.parentAccountSapNumber
    this.siteres.accountName = $event.accountName;
    this.siteres.accountSapNumber = $event.sampAccountNumber;
  }
 circleName:string
  addNewSSACenter(){
    this.heading="Create SSA"
    this.createSSAModel.show();
   this.circleName= this.siteres.circleName;
    this.isEditMode=false;
    // this.siteres.circleName=this.ssares.circleName;
    
  }
  onSSACreated($event){
    this.getCircleList();
    this.siteres.circleName=$event.circleName;
    this.spinner.show();
    this.ssaservice.getSSAForlist(this.siteres.circleName).subscribe(data=>{
      this.spinner.hide();
      this.SSAList=data;

    },error=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message})

    })
      this.siteres.ssaId=$event.id;
    this.siteres.ssaName=$event.name;
  }
  cancelSSAData(){
    this.createSSAModel.hide();
  }
  // popupClose() {
  //   // this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');
  //   }
  // }
  getCIrcleByLg(id){
    this.circleservice.getCircleListByLegalEnt(id).subscribe(data=>{
      this.circlesList = data;
    })
  }

}
