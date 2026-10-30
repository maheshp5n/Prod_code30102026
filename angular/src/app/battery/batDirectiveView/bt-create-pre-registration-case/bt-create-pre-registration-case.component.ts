import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { caseService } from '../../../services/caseService.service';
import { Loginservices } from '../../../services/login.services';
import { AccountsForList, AssetsForList, ContactForList, Contacts, PendingResponsibility, ProblemReported, selected, ServiceCenterForList, ServiceResourseForList, SiteDetailsForList } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { AssetForList, AssetTypeChoice, BTassetResp, BTAssetsForList, BTCaseReasonForList, BTCaseStageDetails, BTCaseTypeForList, BTCaseTypeRecord, BtContactForlist, BtPreRegCaseRequest, BtPreRegCaseResponse, BtProblemMaster, BtServiceCenterForList, BT_CaseOriginDetails, BT_CasePriority, BT_CaseStatus, BT_SiteDetailForList, GetAllBTsiteResp } from '../../bt_model';

@Component({
  selector: 'app-bt-create-pre-registration-case',
  templateUrl: './bt-create-pre-registration-case.component.html',
  styleUrls: ['./bt-create-pre-registration-case.component.css']
})
export class BtCreatePreRegistrationCaseComponent implements OnInit {
  @ViewChild('createSiteModel', { static: false }) createSiteModel: ModalDirective;
  @ViewChild('createLegalEntityModel', { static: false }) createLegalEntityModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createAssetModel', { static: false }) createAssetModel: ModalDirective;
  @ViewChild('createContactModel', { static: false }) createContactModel: ModalDirective;

  @Output()
  onCaseCreated: EventEmitter<BtPreRegCaseRequest> = new EventEmitter<BtPreRegCaseRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  domainId: number;
  NewBtn: boolean = false;
  assetResponse: BTassetResp = new BTassetResp()
  res: BtPreRegCaseResponse = new BtPreRegCaseResponse();
  req: BtPreRegCaseRequest = new BtPreRegCaseRequest();
  caseTypeList: Array<BTCaseTypeForList> = [];
  casetypRecord: Array<BTCaseTypeRecord> = [];
  caseReason: Array<BTCaseReasonForList> = [];
  leaglEntityList: Array<ServiceCenterForList> = [];
  caseReasonList: BTCaseReasonForList;
  problem: ProblemReported = new ProblemReported();
  heading: string;
  edit: boolean = false;
  newcontact: Contacts = new Contacts();
  CREATE_ACCOUNT_HEADING: string = 'Create Site';
  EDIT_ACCOUNT_HEADING: string = 'Edit Site ';
  caseRecordTypeName: string;
  i: any = 0;
  loginData: any;
  verticalname: number;
  selected: string;
  AssetsSerialNo: string;
  ContactOfAccount: Array<any> = [];
  contactByAccount: Contacts = new Contacts();
  assests: BTassetResp = new BTassetResp();
  siteres: GetAllBTsiteResp = new GetAllBTsiteResp();
  assetList: Array<BTAssetsForList>=[];
  AllProblemList: Array<ProblemReported> = [];
  problemOccured: Array<ProblemReported> = [];
  isAccountSelected: string;
  isSiteSelected: string;
  selectvalues: Array<selected> = [];
  selectCheckValue: selected;
  arayelement: string[];
  arrayValue = [];
  selectedKey: any;
  valuofarry: string;
  outputVar: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  value;
  isAssetSerialnmbr: boolean = false;
  date: any = new Date();
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  problem1: ProblemReported = new ProblemReported();
  pendingres: Array<PendingResponsibility>;
  origin: Array<BT_CaseOriginDetails> = [];
  caseStatusName: any;
  status: Array<BT_CaseStatus> = [];
  contactId: number;
  priority: Array<BT_CasePriority>;
  stage: Array<BTCaseStageDetails>;
  siteList: Array<BT_SiteDetailForList>=[];
  AccountList: Array<AccountsForList>;
  assets: any;
  astserialNumbr: AssetsForList = new AssetsForList();
  legalEntityList: ServiceCenterForList = new ServiceCenterForList();
  contact: Contacts = new Contacts();
  contactID: any;
  mobileNo: any;
  isAssetSerialNo: boolean;
  // noMatchAsset: string;
  // noMatchSite:string
  id: number;
  foSCHonly:boolean=false;
  indate: boolean = false;
  outdate: boolean = false;
  assetSerialnoCase: string;
  assetNot: boolean = true;
  serviceResourcesList: Array<ServiceResourseForList> = [];
  notBatryDCT: boolean = false;
  // popupNgClass: string = 'alert alert-danger center center-block';
  tampCaseId: number;
  tempCaseName: string;
  fullName: string;
  ngClassMissingAsset = 'form-group';
  ngClassAssetSerial = 'form-group'
  colAsset = 'col-md-10 col-sm-10'
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  installationTrue:boolean=false;
  assetres:BTassetResp=new BTassetResp();
  siteaccountsapnmbr:string;
  contactfullname:string;
  
  todayDate=new Date();
  _visitDate=new Date();
  todayDate3:Date = new Date();
  _datetoday3:Date = new Date();
  createDateMin:Date;

 @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  serviceCentr: number;
  @Input()
  set serviceCenterId(serviceCenterId: number) {
    this.serviceCentr = serviceCenterId;
    this.spinner.show();
    this.siteservice.getServiceResourcesList(this.serviceCentr).subscribe(data => {
      this.spinner.hide();
      this.serviceResourcesList = data;
    });

  }

  get serviceCenterId(): number {
    return this.serviceCentr;
  }
  @Input()
  set siteId(siteId: number) {
    if(siteId!=null){
      this.res.siteId = siteId;
      this.installationTrue=true;
      // this.getSiteById(this.res.siteId);
      this.spinner.show()
    this.siteservice.getBtSitedataById(this.res.siteId).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;
        this.res.primaryAccountSapNumber = data.accountSapNumber;
        this.res.primaryAccountName = data.accountName;
        this.getServiceResForList(this.res.serviceCenterId);
         this.res.serviceResourceId = data.serviceResourceId;
         this.siteaccountsapnmbr=data.accountSapNumber;
        // this.getContactByAccount(data.accountSapNumber);
        // contact by site`s account
        this.accountservice.getBtContactList(data.accountSapNumber).subscribe(data => {
          this.spinner.hide();
          this.ContactOfAccount = [];
          data.forEach((ct) => {
            let _ct = new ContactForList();
            _ct.firstName = ct.contactFirstName;
            _ct.lastName = ct.contactLastName;
            _ct.middleName = ct.contactMiddleName;
            _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+' '+ct.contactMobileNumber;
            _ct.phoneNumber = ct.contactMobileNumber;
            _ct.contactId = ct.contactid;
            this.ContactOfAccount.push(_ct);
         }, (error => {
            this.spinner.hide();
            this.message = error.error.essage;
            this._global.alert({ type: 'alert', message: this.message });
          })
          );
        }); 
      }
      })
      // 
      if (this.domainId === 1) {
        this.res.caseTypeID = 2;
        this.getCaseReason(this.res.caseTypeID);
        this.res.caseReasonId = 8;
        this.ngClassMissingAsset = 'form-group required';
      }
      else {
        this.res.caseTypeID = 6;
        this.getCaseReason(this.res.caseTypeID);
        this.res.caseReasonId = 18;
        this.ngClassMissingAsset = 'form-group required';
      }
      
    }
  }
  get siteId(): number {
    return this.res.siteId;
  }
@Input()
set SiteSeviceResourceID(siteseviceResourceID:number){
 this.res.serviceResourceId=siteseviceResourceID;
  // this.res.serviceResourceName=siteInfo.serviceResourceName;
  // this.res=siteInfo;
}
get SiteSeviceResourceID():number{
  return this.res.serviceResourceId;
}
  @Input()
  set editEntityData(res: BtPreRegCaseResponse) {
    
    this.res=res;
    
    if (res!=null) {
      this.res.caseOriginId=res.caseOriginId;
      this.res.caseReasonName=res.caseReasonName;
      // this.res.typeChoice=res.assetsTypeChoiceId;
      this.res.caseTypeID=res.caseTypeID;
      this.res.caseStageId=res.caseStageId;
     this.res.prcCaseId= res.prcCaseId;
     this.res.assetsId = res.assetId;
     this.res.caseReasonId=res.caseReasonId;
    this.res.RJIL_TT01_No=res.rjil_TT01_No;

  this.res.openDate=res.openDate;
  this.res.ticketNo=res.ticketNo;

    if(res.contactPersonId!=null){
      if(this.res.contactPersonMiddleName!=null){
        this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonMiddleName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
       }else{
         this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
       }
       this.ContactOfAccount.push({contactId:this.res.contactPersonId,emailId:this.res.contactPersonEmailId,firstName:this.res.contactPersonFirstName,middleName:this.res.contactPersonMiddleName,lastName:this.res.contactPersonLastName,phoneNumber:this.res.contactPersonContactNo,fullName:this.contactfullname})
      this.res.contactPersonId=res.contactPersonId;   
    }
    
      if(res.missingAssets!=null && res.missingAssets!=''){
        this.missingast=true;
        this.req.assetsId = null;
      }else{
        this.missingast=false;
        this.req.assetsId=this.res.assetsId;
      }
      if(this.res.missingAssets!=null &&  this.res.missingAssets!=undefined){
        this.req.assetsId = null;
      }else{
        this.req.assetsId=this.res.assetsId;
      }
    // this.siteList=[]
    if(res.assetsSerialNumber!=null&&res.assetsSerialNumber!=undefined&&res.assetsSerialNumber!=''){
       this.assetList.push({serialNumber:res.assetsSerialNumber,id:res.assetId,productDescription:res.assetDescription,productPartCode:res.assetsPartCode,asstPartCode:res.assetsSerialNumber+res.assetsPartCode});

       
    }
    if(res.siteId!=null&&res.siteId!=undefined){  
      this.siteList.push({batterySiteId:this.res.siteId,manuaSitelId:this.res.siteManualId,siteName:this.res.siteName});
      // this.updateSiteList();
    }
    this.loginData = this.loginservice.getLoggedInUser();
    if(this.res.caseTypeID==2 || this.res.caseTypeID==6){
      this.installationTrue=true;
    }else{
      this.installationTrue=false;
    }
    if (this.domainId === 1) {
      this.notBatryDCT = true;
      this.res.caseTypeID = 3;
      console.log(this.res.caseTypeID);
      // this.getCaseReason(this.res.caseTypeID);
      // this.res.caseReasonId = 1;
    }
    this.caseservice.getCaseAssets(this.res.assetsSerialNumber).subscribe(data=>{
      if(data!==null){
        if (data.isAMCWarrantyExist || data.isWarrantyExist) {
          this.indate = true;
          this.outdate = false;
  
          if (this.domainId === 1) {
            this.res.caseTypeID = 1;

            // this.getCaseReason(this.res.caseTypeID);
            
          } else if (this.domainId === 2) {
            this.res.caseTypeID = 7;
            // this.getCaseReason(this.res.caseTypeID);
          }
        } 
        else {
          this.outdate = true;
          this.indate = false;
  
          if (this.domainId === 1) {
            this.res.caseTypeID = 3;
            console.log(this.res.caseTypeID)
            // this.getCaseReason(this.res.caseTypeID);
            // this.res.caseReasonId=1;
          } else if (this.domainId === 2) {
            // this.res.caseTypeID = 8;
            // this.getCaseReason(this.res.caseTypeID);
            // this.res.caseReasonId=17;
          }
        }
      }
     
    });
    this.domainId = this.loginData.emptype['businessDomainId']
  
      this.spinner.show();
      this.assetNot = true;
      if (this._isEditMode == true && (this.res.assetsSerialNumber != null || this.res.assetsSerialNumber != '')) {
        this.assetNot = false
      }
      if (this.isEditMode) {
         //contacts
         this.spinner.show();
       
         this.siteservice.getBtSitedataById(res.siteId).subscribe(data => {
           this.spinner.hide();
           if (data != null) {
             this.siteres = data;
            //  this.res.serviceCenterId = data.serviceCenterId;
             this.res.primaryAccountSapNumber = data.accountSapNumber;
             this.res.primaryAccountName = data.accountName;
            //  this.res.serviceResourceId = data.serviceResourceId;
            //  this.spinner.show();
            //  this.accountservice.getBtContactList(data.accountSapNumber).subscribe(data => {
            //    this.spinner.hide();
            //    this.ContactOfAccount = [];
            //    data.forEach((ct) => {
            //      let _ct = new ContactForList();
            //      _ct.firstName = ct.contactFirstName;
            //      _ct.lastName = ct.contactLastName;
            //      _ct.middleName = ct.contactMiddleName;
            //      _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+' '+ct.contactMobileNumber;
            //      _ct.phoneNumber = ct.contactMobileNumber;
            //      _ct.contactId = ct.contactid;
            //      this.ContactOfAccount.push(_ct);
            //   }, (error => {
            //      this.spinner.hide();
            //      this.message = error.error.essage;
            //      this._global.alert({ type: 'alert', message: this.message });
            //     })
            //    );
            //  }); 
            }
         });
        // this.res.isAssignByAssignmentRule = true;
        this.problemOccured = this.res.problemReported;
        this.spinner.show();
        this.caseservice.getProblemReported(this.res.problemMasterId).subscribe(data => {
          this.spinner.hide()
          this.AllProblemList = data;
        });
        this.spinner.show()
        this.legalEntityService.getLegalEntityForList().subscribe((data) => {
          this.leaglEntityList = data;
        });

        this.siteservice.getServiceResourcesList(this.res.serviceCenterId).subscribe(data => {
          this.spinner.hide()
          this.serviceResourcesList = data;
        });
        this.spinner.show();
        this.caseservice.getCaseTypes(this.domainId).subscribe((data) => {
          this.spinner.hide()
          this.caseTypeList = data;

        });

        console.log(this.res.caseTypeID);
        this.spinner.show();
        this.caseservice.getCaseReason(this.res.caseTypeID).subscribe((data) => {
          this.spinner.hide();
          this.caseReason = data;
        });


        this.caseservice.getCaseStatus().subscribe(data => {
            // this.status = data; 
            const tempst=[];
            data.forEach(e=>{
      
              let sts=new BT_CaseStatus();
              if(e.id==2)
            {  sts.caseStatusName=e.caseStatusName;
              sts.id=e.id;
              sts.isDesable=true;
            }
              else{
                sts.caseStatusName=e.caseStatusName;
              sts.id=e.id;
              sts.isDesable=false;
              }
              tempst.push(sts);
      
            })
            this.status=tempst;
          
          });
        if (this.res.caseStatusId !== null && this.domainId !== null) {
          this.caseservice.getCaseStage(this.res.caseStatusId).subscribe(data => {
            this.stage = data;
          });
        }
        this.spinner.show();
        this.caseservice.pendingrisponsibility().subscribe(data => {
          this.spinner.hide();
          this.pendingres = data;
        });
      } else {
        this.problemOccured = [];
        // if (this.res.assetsSerialNumber != null && this.res.assetsSerialNumber.length > 0) {
        //   this.getAssetsBySerialNmber(this.res.assetsSerialNumber);
        // }
     
        if (this.res.primaryAccountSapNumber != null && this.res.primaryAccountSapNumber.length > 0) {
          this.getAssetsListByAccountSapNumber(this.res.primaryAccountSapNumber);
          // this.getSitesListByAccountSapNumber(this.res.accountSapNumber);
        }
      }
      this.spinner.hide();
    }
    else {
           this.res = new BtPreRegCaseResponse();
       }
  }

  get editEntityData(): BtPreRegCaseResponse {
    return this.res;
  }
  assetWarenty:string;
  @Input()
  set assetWarretyCheck(assetWarrety:string){
    this.assetWarenty=assetWarrety;

  }
  get assetWarretyCheck():string{
     return this.assetWarenty;
  }
  @Input()
  set AssetSerial(ress:BtPreRegCaseResponse){
    this.res=ress;
    // this.res.siteManualId=ress.siteManualId;
    // this.res.siteName=ress.siteName;
    // this.res.siteId=ress.siteId;
    this.siteList.push({batterySiteId:ress.siteId,manuaSitelId:ress.siteManualId,siteName:ress.siteName});
    this.updateSiteList();
    // this.updateSiteList();
    // this.getContactByAccount(ress.accountSapNumber);  this.spinner.show();
         this.siteservice.getBtSitedataById(ress.siteId).subscribe(data => {
          this.spinner.hide();
          if (data != null) {
            this.updateSiteList();
            this.siteres = data;
            this.res.serviceCenterId = data.serviceCenterId;
            this.res.primaryAccountSapNumber = data.accountSapNumber;
            this.res.primaryAccountName = data.accountName;
            this.getServiceResForList(this.res.serviceCenterId);
            this.res.serviceResourceId = data.serviceResourceId;
            this.spinner.show();
            this.accountservice.getBtContactList(data.accountSapNumber).subscribe(data => {
              this.spinner.hide();
              this.ContactOfAccount = [];
              data.forEach((ct) => {
                let _ct = new ContactForList();
                _ct.firstName = ct.contactFirstName;
                _ct.lastName = ct.contactLastName;
                _ct.middleName = ct.contactMiddleName;
                _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+' '+ct.contactMobileNumber;
                _ct.phoneNumber = ct.contactMobileNumber;
                _ct.contactId = ct.contactid;
                this.ContactOfAccount.push(_ct);
             }, (error => {
                this.spinner.hide();
                this.message = error.error.essage;
                this._global.alert({ type: 'alert', message: this.message });
              })
              );
            }); 
           }
        });


  }
  get AssetSerial():BtPreRegCaseResponse{
   return this.res;
  }


  openModlues = {siteModuleOpen: false, legalEntityModuleOpen: false, assetsModuleOpen: false, contactModuleOpen: false }
  constructor(private _assetsService:BTassetserviceService,private preregcaseservice:BtPreRegistrationCaseService,private siteservice: BtsiteserviceService, private loginservice: Loginservices, private caseservice: BtCaseserviceService,
     private legalEntityService: BtlegalentityService, private accountservice: BtaccountService, private spinner: NgxSpinnerService, private datePipe: DatePipe, private contatactService: BtcontactsService, private _global: GlobalserviceService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];

  }
  rights(object, right: string): boolean {

    return this.loginservice.isUserValidFor(object, right);

  }
  //   getCOL(object,right:string){
  //     if(this.loginservice.isUserValidFor(object,right)){
  // this.colAsset='col-md-12 col-sm-12';
  //       console.log(this.loginservice.isUserValidFor(object,right))
  //     }
  //     else{
  //       this.colAsset='col-md-10 col-sm-10';
  //     }
  //   }


  ngOnInit() {
    // this.getAssestList();
    this.getLoggedInUser();
    // this.caseRecord();
    this.caseOrigin();
    this.casePeriority();
    this.getAccountList();
 this.getAssetTypeChoice();
    // this.getSite();
    this.getCaseStatus();
    // this.getcaseType();
    this.LegalENtityList();
    // this.ProblemReport();
    this.getProblmMaster();
    this.Risponsibility();
 
    if(!this.isEditMode){
      this.res.caseStatusId = 1;
      this.res.problemMasterId = 0;
    }

    //  this.getContactsData();
    if (!this.isEditMode && this.res.siteId==null) {
      if (this.domainId === 1) {
        this.notBatryDCT = true;
        this.getcaseType();
        this.res.caseTypeID = 3;
        console.log(this.res.caseTypeID);
        this.getCaseReason(this.res.caseTypeID);
        this.res.caseReasonId = 1;
      }
      else {
        this.notBatryDCT = false;
        this.res.caseTypeID = 7;
        this.getCaseReason(this.res.caseTypeID);
        this.res.caseReasonId = 11;
      }
    }else{
      console.log(this.res.caseReasonId)
      // if(this.res.caseTypeID==3){
      //   this.getCaseReason(this.res.caseTypeID);
      //   this.res.caseReasonId=1
      // }
    }
    this.todayDate3.setDate(this.todayDate.getDate() -5);

  }


  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    // this.loginData.empType['businessDomainId'];
  }

  caseType() {
    this.caseservice.getCaseTypes(this.domainId).subscribe(data => {
      this.caseTypeList = data;
    });
  }


  Risponsibility() {
    this.caseservice.pendingrisponsibility().subscribe(data => {
      this.pendingres = data;

    });
  }

  LegalENtityList() {
    this.legalEntityService.getLegalEntityForList().subscribe((data) => {
      this.leaglEntityList = data;
    });
  }
  getCasereasonchange(caseTypeId) {
    console.log(caseTypeId);
    if(caseTypeId==2 || caseTypeId==6){
      this.installationTrue=true;
    }else{
      this.installationTrue=false;
    }
    this.getCaseReason(caseTypeId);





  }



  getcaseType() {
    this.caseservice.getCaseTypes(this.domainId).subscribe((data) => {
      this.caseTypeList = data;
    });
    console.log(this.res.caseTypeID);
    this.getCaseReason(this.res.caseTypeID);


  }
    getCaseReason(caseTypeId) {
      console.log(caseTypeId)
    this.spinner.show();
    this.caseservice.getCaseReason(caseTypeId).subscribe((data) => {
      if (data != null) {
        this.spinner.hide();
      }
      this.caseReason = data;

      if (this.domainId === 2) {
        if (this.res.caseTypeID === 5) {
          this.ngClassMissingAsset = 'form-group required';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 12;
          return;
        }
        if (this.res.caseTypeID === 6) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 18;
          return;
        }
        if (this.res.caseTypeID === 7) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group required';
          this.res.caseReasonId = 11;
          return;
        }
        if (this.res.caseTypeID === 8) {
          this.ngClassAssetSerial = 'form-group required';
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 17;

        }
        if(this.res.caseTypeID===9){
          this.ngClassAssetSerial = 'form-group required';
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 30;
        }

      } 
      else if (this.domainId === 1) {
        console.log(caseTypeId)
        if (caseTypeId=== 1) {
          console.log(caseTypeId)
          // this.ngClassMissingAsset = 'form-group required';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            console.log(caseTypeId)
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          // this.ngClassAssetSerial = 'form-group';
        
          this.res.caseReasonId = 7;
          this.res.caseReasonName='Audit';
          // console.log(this.res.caseTypeId+'reason='+this.res.caseReasonId)
          return;

        }
        if (caseTypeId === 2) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          this.res.caseReasonId = 8;
          return;
        }
        if (caseTypeId === 3) {
          this.ngClassMissingAsset = 'form-group';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          this.res.caseReasonId = 1;
          return;
        }
        if (caseTypeId === 4) {
          this.ngClassMissingAsset = 'form-group';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          this.res.caseReasonId = 21;
          console.log(this.res.caseReasonId+"name="+this.res.caseReasonName);
          return;
        }
        if(caseTypeId==11){
              this.res.caseReasonId=48;
              if(this.missingast==false){
                this.ngClassAssetSerial = 'form-group required';
              }
              else{
                this.ngClassAssetSerial = 'form-group';
                this.ngClassMissingAsset = 'form-group required';
              }
              
          // this.ngClassMissingAsset = 'form-group';
          // this.ngClassAssetSerial = 'form-group';
          return;
        }
      }
    }, error => {
      this.spinner.hide();
    });
  }
  ProblemReport(id) {
    this.spinner.show();
  
    this.caseservice.getProblemReported(id).subscribe(data => {
      this.AllProblemList = data;
      this.spinner.hide()
    });

    if (this.domainId === 1) {
      this.notBatryDCT = true;

    }
    else {
      this.notBatryDCT = false;
    }
    // if(!this.isEditMode){
    //   this.res.caseStatusId = 1;
    // }
  }
  getCaseStatus() {
    this.caseservice.getCaseStatus().subscribe(data => {
      // this.status = data;
      const tempst=[];
      data.forEach(e=>{

        let sts=new BT_CaseStatus();
        if(e.id==2)
      {  sts.caseStatusName=e.caseStatusName;
        sts.id=e.id;
        sts.isDesable=true;
      }
        else{
          sts.caseStatusName=e.caseStatusName;
        sts.id=e.id;
        sts.isDesable=false;
        }
        tempst.push(sts);

      })
      this.status=tempst;
      this.getstage(this.res.caseStatusId)
    });


    if (this.domainId === 1) {
      this.res.caseStageId = 5;
    } else if (this.domainId === 2) {
      this.res.caseStageId = 11;
    }

    this.res.caseOriginId = 3;
    this.res.casePriorityId = 2;
  }


  caseOrigin() {
    this.caseservice.getCaseOrigin().subscribe((data) => {
      this.origin = data;
    });
  }
  getstage(id) {
    if (id != null && this.domainId != null) {
      this.spinner.show();
      this.caseservice.getCaseStage(id).subscribe(data => {
        this.spinner.hide();
        this.stage = data;
        if (this.stage.length > 0) {
          this.res.caseStageId = this.stage[0].id;
        }
      });

    }
  }
  casePeriority() {
    this.caseservice.getCasePriority().subscribe(data => {
      this.priority = data;
    });
  }
  getSite() {
    this.caseservice.getSitesForList().subscribe(data => {
      // this.siteList = data;

    });
  }
  getSiteById(siteid) {
    this.spinner.show()
    this.siteservice.getBtSitedataById(siteid).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;

        this.res.primaryAccountSapNumber = data.accountSapNumber;
        this.res.primaryAccountName = data.accountName;
        this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.getServiceResForList(this.res.serviceCenterId);
       this.res.serviceResourceId = data.serviceResourceId;
        if (this.res.assetsId == null || this.res.assetsId === undefined || this.res.assetsId === 0) {
          if (this.domainId === 1) {
            this.res.caseTypeID = 1;
            this.res.caseTypeName='Exceptional Case';
            console.log(this.res.caseTypeId)
            this.getCaseReason(this.res.caseTypeID);
            // this.res.caseReasonId = 2;
            this.ngClassMissingAsset = 'form-group required';
          }
          else {
            this.res.caseTypeID = 5;
            this.getCaseReason(this.res.caseTypeID);
            this.res.caseReasonId = 12;
            this.ngClassMissingAsset = 'form-group required';
          }
        }
        this.siteaccountsapnmbr=data.accountSapNumber;
        // this.getContactByAccount(data.accountSapNumber);
      }
      
    });
  }
  getAccountList() {
    this.siteservice.getAccountsForList().subscribe(data => {
      this.AccountList = data;
    });

  }
  getAssestList() {
    this.caseservice.getAssestForList().subscribe(data => {
      this.assets = data;
      this.assetList = this.assets;
    });
  }
  getAssetsListTini(key){
    this._assetsService.getSmallForList(key.term).subscribe(data=>{
      this.assets=data;
      this.assetList=[];
      
      let temp=[]
      data.forEach(e=>{
        let assetsList=new BTAssetsForList();
     assetsList.id=e.id;
     assetsList.serialNumber=e.serialNumber;
     assetsList.productPartCode=e.productPartCode;
     assetsList.productDescription=e.productDescription;
     assetsList.asstPartCode=e.serialNumber+'-'+e.productPartCode;
     this.assetList.push(assetsList);
     console.log( this.assetList);
     
      })
  
    });
  }
  getContactsData() {
    this.spinner.show();
    this.contatactService.getcontactsForList().subscribe(data => {
      this.spinner.hide();
      //  this.res.contactPersonId=null;
      this.ContactOfAccount = [];
      data.forEach((ct) => {
        let _ct = new ContactForList();
        _ct.contactId = ct.contactId;
        _ct.accountSapNumber = ct.accountSapNumber;
        _ct.contactId = ct.contactId;
        _ct.emailId = ct.emailId;
        _ct.firstName = ct.firstName;
        _ct.lastName = ct.lastName;
        _ct.middleName = ct.middleName;
        _ct.phoneNumber = ct.contactMobileNumber;
        _ct.fullName = ct.firstName + ' ' + ct.middleName + ' ' + ct.lastName+' '+ct.contactMobileNumber;
        this.ContactOfAccount.push(_ct);

      },
        (error => {
          this.spinner.hide();
          this.message = error.error.essage;
          this._global.alert({ type: 'alert', message: this.message });
          setTimeout(() => {
            this._global.alert({ type: 'alert', message: this.message });
          }, 2000
          );
        })

      );

    })
  }

  reg = new RegExp('^[0-9]$')
  postCase() {
    // if (this.res.caseReasonVerticalId === 0 || this.res.caseReasonVerticalId === null) {
    //   this.message = 'Select Case Record Type';

    // }

    if (this.res.caseTypeID === 0 || this.res.caseTypeID === null) {
      this.message = 'Select Case Type';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    // else if ( this.missingast==false &&(this.res.assetsId === undefined || this.res.assetsId === null) ) {
    //   this.message = 'Select Assets Serial Number';
    //   // this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({ type: 'alert', message: this.message });

    // }
    else if ((this.res.assetsId === undefined || this.res.assetsId === null) && (this.res.missingAssets === undefined || this.res.missingAssets === null || this.res.missingAssets === '') && (this.res.caseTypeId === 1)) {
      this.message = 'Fill The Missing Asset Serial Number';
      this._global.alert({type:'alert',message:this.message})
    }
    else if((this.res.assetsTypeChoiceId==null || this.res.assetsTypeChoiceId==undefined) && this.missingast==true){
      this.message='Fill Work/Asset type Choice';
      this._global.alert({type:'alert',message:this.message})
    }
    else if ((this.res.assetsId === undefined || this.res.assetsId === null || this.res.assetsId === '') && (this.res.missingAssets === undefined || this.res.missingAssets === null || this.res.missingAssets === '') && (this.res.caseTypeID === 1 || this.res.caseTypeID === 5)) {
      this.message = 'Fill The Missing Asset Serial Number';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });
    }
    // else if(this.installationTrue==true && (this.res.ponumber==undefined || this.res.ponumber==null) && (this.res.caseTypeID==2 || this.res.caseTypeID==6)){
    //   this.message = 'Fill PO Number';
    //   // this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({ type: 'alert', message: this.message });
    // }

    // else if(this.installationTrue==true && (this.res.podate==undefined || this.res.podate==null) && (this.res.caseTypeID==2 || this.res.caseTypeID==6)){
    //   this.message = 'Fill PO Date';
    //   // this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({ type: 'alert', message: this.message });
    // }
    else if (this.res.caseReasonId === 0 || this.res.caseReasonId === null ) {
      this.message = 'Select Any Case Reason';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    } else if (this.res.contactPersonId === null || this.res.contactPersonId === 0) {
      this.message = 'Select Any Contact Name';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    } else if (this.res.caseStatusId === 0) {
      this.message = 'Select Any Status';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    } else if (this.res.caseStageId === 0) {
      this.message = 'Select Any Stage';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    } else if (this.res.caseOriginId === 0 || this.res.caseOriginId === null) {
      this.message = 'Select Any Case Origin Name';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    } else if (this.res.casePriorityId === 0 || this.res.casePriorityId === null) {
      this.message = 'Select Any Priority';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if (this.res.internalRemark != undefined && this.res.internalRemark !== undefined && (this.res.internalRemark).length >= 1000) {
      this.message = 'Internal Comments Exceeds 1000 Character';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if (this.res.otherProblemReported !== null && this.res.otherProblemReported !== undefined && (this.res.otherProblemReported).length > 1000) {
      this.message = ' Other Problems Exceeds 1000 Character';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if (this.res.inputFromEng !== null && this.res.inputFromEng !== undefined && (this.res.inputFromEng).length > 1000) {
      this.message = ' Input From Engineer Exceeds 1000 Character';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if (this.res.remarks !== null && this.res.remarks !== undefined && (this.res.remarks).length > 1000) {
      this.message = ' Remark Exceeds 1000 Character';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }

    else if (this.res.noOfSmr!==null && this.res.noOfSmr!==undefined && (this.res.noOfSmr).length > 4) {
      this.message = 'Number Of SMR Exceeds Only Numbers With 2 Character';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if(this.problemOccured.length==0){
      this.message = 'Select Any One Problem';
      this._global.alert({type:'alert',message:this.message})
    }
  
    // else if(this.res.noOfSmr!=null && ){

    // }

    else {
      this.istrue = true;
      if(this.res.missingAssets!=null &&  this.res.missingAssets!=undefined){
        this.req.assetsId = null;
      }else{
        this.req.assetsId=this.res.assetsId;
      }
      if (!this._isEditMode) {
        this.req.caseRecordTypeId = this.domainId;
        this.req.caseId = this.res.preCaseId;
        // this.req.assetsId = this.res.assetsId;
        this.req.assetsSerialNumber = this.res.assetsSerialNumber;
        this.req.caseOriginId = this.res.caseOriginId;
        this.req.casePriorityId = this.res.casePriorityId;
        this.req.caseReasonId = this.res.caseReasonId;
        this.req.caseStageId = this.res.caseStageId;
        this.req.caseStatusId = this.res.caseStatusId;
        this.req.caseTypeId = this.res.caseTypeID;
        this.req.contactPersonId = this.res.contactPersonId;
        this.req.noOfSmr = this.res.noOfSmr;
        this.req.remarks = this.res.remarks;
        this.req.caseProblemReported = this.problemOccured;
        this.req.serviceCenterId = this.res.serviceCenterId;
        this.req.serviceResourceId = this.res.serviceResourceId;
        this.req.missingAssets = this.res.missingAssets;
        this.req.RJILTT01No = this.res.RJIL_TT01_No;
        this.req.otherProblemReported = this.res.otherProblemReported;
        this.req.siteId = this.res.siteId;
        this.req.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
        this.req.inputFromEng = this.res.inputFromEng;
        this.req.internalRemark = this.res.internalRemark;
        this.req.pendingResponsibility = this.res.pendingResponsibilityId;
        this.req.poDate=this.datePipe.transform(this.res.PODate, 'yyyy-MM-dd HH:mm:ss');
        this.req.poNumber=this.res.ponumber;
        this.req.poamount=this.res.poamount;
        this.req.limeitem=this.res.lineitem;
        this.req.typeChoice=this.res.assetsTypeChoiceId;
        this.req.btProblemMaster=this.res.problemMasterId;
        // this.req.openDate=this.datePipe.transform(this.res.openDate, 'yyyy-MM-dd HH:mm:ss');
        this.req.openDate=this.res.openDate;
        this.req.ticketNo=this.res.ticketNo;
        this.spinner.show();
        this.preregcaseservice.saveCase(this.req).subscribe(data => {
          this.tempCaseName = data.caseManualId;
          this.tampCaseId = data.caseId;
          this.message = 'Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onCaseCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
            this.res = new BtPreRegCaseResponse();
            this.problemOccured = [];
          }
          this._global.alert({ type: '', message: this.message });
          this.onCancle.emit('cancled');
        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this._global.alert({ type: 'alert', message: this.message });


          }
        );
      } else {

        this.req.openDate=this.datePipe.transform(this.res.openDate,'yyyy-MM-dd HH:mm:ss');
        // this.req.ticketNo=this.res.ticketNo;
        this.req.id=this.res.prcCaseId
        this.req.caseRecordTypeId = this.domainId;
        this.req.caseId = this.res.caseId;
        this.req.assetsSerialNumber = this.res.assetsSerialNumber;
        // this.req.assetsId = this.res.assetsId;
        this.req.caseOriginId = this.res.caseOriginId;
        this.req.casePriorityId = this.res.casePriorityId;
        this.req.caseReasonId = this.res.caseReasonId;
        this.req.caseStageId = this.res.caseStageId;
        this.req.caseStatusId = this.res.caseStatusId;
        this.req.caseTypeId = this.res.caseTypeID;
        this.req.contactPersonId = this.res.contactPersonId;
        this.req.noOfSmr = this.res.noOfSmr;
        this.req.remarks = this.res.remarks;
        this.req.caseProblemReported = this.problemOccured;
        this.req.serviceCenterId = this.res.serviceCenterId;
        this.req.serviceResourceId = this.res.serviceResourceId;
        this.req.missingAssets = this.res.missingAssets;
        this.req.RJILTT01No = this.res.RJIL_TT01_No;

        this.req.ticketNo=this.res.ticketNo;
        // this.req.openDate=this.res.openDate;
        this.req.siteId = this.res.siteId;
        this.req.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
        this.req.inputFromEng = this.res.inputFromEng;
        this.req.internalRemark = this.res.internalRemark;
        // this.req.caseProblemReported = this.problemOccured;
        this.req.pendingResponsibility = this.res.pendingResponsibilityId;
        this.req.poDate=this.res.podate;
        this.req.poNumber=this.res.ponumber;
        this.req.poamount=this.res.poamount;
        this.req.limeitem=this.res.lineitem;
        this.req.typeChoice=this.res.assetsTypeChoiceId;
        this.req.btProblemMaster=this.res.problemMasterId;
        this.req.otherProblemReported = this.res.otherProblemReported;
        this.spinner.show();
        this.preregcaseservice.updateCase(this.req,this.res.prcCaseId).subscribe(data => {
          this.onCaseCreated.emit(data);
          this.tempCaseName = data.caseManualId;
          this.tampCaseId = data.caseId;
          // this.res.caseId = data.caseId;
          this.message = 'Updated Successfully'
          // this.popupNgClass = 'alert alert-success center center-block';
          if (data != null) {
            this.spinner.hide();
            this.problemOccured = [];
          }
          this._global.alert({ type: '', message: this.message });
          this.onCancle.emit('cancled');
        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this._global.alert({ type: 'alert', message: this.message });

          }
        );

      }

    }
    // if (!this.istrue) {
    //   this.popupSuccessModel.show();

    // }


  }
  // cancelSuccessPOpup() {
  //   this.popupSuccessModel.hide();
  //   this.onCancle.emit('cancled');

  // }
  cancelDatacase() {
    this.problemOccured = [];
    if (!this.isEditMode) {
      this.res = new BtPreRegCaseResponse();
    }
    this.onCancle.emit('cancled');
    this.problemOccured = [];

    // this.createAssetModel.hide()
  }

  cancelData() {
    this.createLegalEntityModel.hide();
    this.createAssetModel.hide();
    this.createContactModel.hide();
  }

  cancelDatasite() {
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createSiteModel.hide();
  }

  addNewSite() {


    this.heading = "create site";
    this.siteres = new GetAllBTsiteResp()
    this.createSiteModel.show()
  }


  onKeydownSiteId() {
    this.isSiteSelected = 'fa fa-times';

  }

  onKeydownAccount() {
    this.isAccountSelected = 'fa fa-times';
    this.assetNot = false;
  }

  leagalEntityTimes() {
    this.isAccountSelected = 'fa fa-search';
    this.res.serviceCenterName = null;
  }

  onAssetCreated($event: BTassetResp) {
    // this.getAssestList();
    this.res.assetsSerialNumber = $event.serialNumber;
    this.astserialNumbr.partCode = $event.productPartCode;
    this.astserialNumbr.productDescription = $event.productDescription;

    this.getAssetsBySerialNmber($event.serialNumber);
  }
  siteIdSelected(id) {
    this.res.siteId=id;
    if (event != null) {
      this.res.contactPersonId = null;
      this.ContactOfAccount = [];
      this.getSiteById(id);
    }
    // this.getSiteById(id);

  }
  getServiceResForList(serviceCenterId: number) {
    if (serviceCenterId != null) {
      this.spinner.show();
      this.siteservice.getServiceResourcesList(serviceCenterId).subscribe(data => {
        this.spinner.hide();
        this.serviceResourcesList = data;
        data.forEach(e=>{
          if(this.res.serviceResourceId==null || this.res.serviceResourceId==undefined){
            this.res.serviceResourceId=e.serviceResourceId;
            this.res.serviceResourceName=e.name;
          }
         
        })
      });
    }
  }
  expirydate: any;
  // assetDiscription: string
  assetsSerialNumberSelected($event) {
    if ($event != null) {
      // this.res.assetsSerialNumber = $event.serialNumber;
      this.res.assetDescription = $event.productDescription;
      if (this.res.assetsId.length > 0) {
        this.isAssetSerialnmbr = true;
      }
      // this.spinner.show();
      console.log($event.serialNumber);
      this.getAssetsBySerialNmber($event.id);

      if (this.res.siteId != null || this.res.siteManualId != null) {
        this.isSiteSelected = 'fa fa-times';
      }
      else {
        this.isSiteSelected = 'fa fa-search';
      }
    }
    else {
      this.indate = false;
      this.outdate = false;
      this.res.siteId = null;
      this.res.siteManualId = null;
      this.res.contactPersonId = null;
      this.res.assetDescription = null;

    }
  }

  onContactCreated($event: Contacts) {
    // let _contact = new ContactForList();
    // _contact.accountSapNumber = $event.accountSAPNumber;
    // _contact.contactId = $event.contactid;
    // _contact.emailId = $event.contactEmailId;
    // _contact.firstName = $event.contactFirstName;
    // _contact.lastName = $event.contactLastName;
    // this.ContactOfAccount.unshift(_contact);
    this.getContactByAccount($event.accountSAPNumber);
    this.res.contactPersonId = $event.contactid;
  }
  onEntityCreated($event) {
    this.legalEntityList.legalEntityName = $event.legalEntityName;
    this.leaglEntityList.unshift(this.legalEntityList);
  }

  // leagaltiEntitySelected($event: TypeaheadMatch) {
  //   this.res.serviceCenterId = $event.item.legalEntityId;

  // }

  addNewServiceCenter() {
    this._isEditMode = false;
    this.heading = 'Create LegalEntity';
    this.createLegalEntityModel.show();
  }

  addNewAsset() {
    this.assests = new BTassetResp();
    this.isEditMode = false;
    this.heading = 'Create Asset';
    this.createAssetModel.show();
  }

 
  // multiple array
   moveRight(selectedReason) {
    for(let i=0;i<selectedReason.length;i++){
    if (selectedReason!== undefined) {  
      console.log(selectedReason)
      const index = this.AllProblemList.indexOf(selectedReason[i]);
      this.problemOccured.push(selectedReason[i]);
      this.AllProblemList.splice(index,selectedReason.length);
      
      this.problem = new ProblemReported();
    } else {
      this.message = "Please select any 1 problem";
      this._global.alert({ type: 'alert', message: this.message });
    }
  }
  }
  moveLeft(unselectReason) {
    console.log(unselectReason);
    for(let i=0;i<unselectReason.length;i++){
    if (unselectReason!== undefined) {
      const index = this.problemOccured.indexOf(unselectReason[i]);
      this.AllProblemList.push(unselectReason[i]);
      this.problemOccured.splice(index,1);
      this.problem = new ProblemReported();
    } else {
      this.message = "Please Select Any 1 Problem";
      this._global.alert({ type: 'alert', message: this.message });
     

    }
  }
  }
  // 

  

  // 
  addNewContact() {
    // this.newcontact=this.res
    this.newcontact = new Contacts();
    this.newcontact.accountName = this.res.primaryAccountName;
    this.newcontact.accountSAPNumber = this.res.primaryAccountSapNumber;

    this.heading = 'Create Contact';
    this.createContactModel.show();
  }
  onSiteCreated($event) {
    this.res.siteId = $event.siteId;
    this.res.siteManualId = $event.siteManualId;
    this.res.siteName=$event.siteName;
    this.res.primaryAccountName = $event.accountName;
    this.res.primaryAccountSapNumber = $event.accountSAPNumber;
    this.res.serviceCenterName = $event.leagalEntityName;
    this.res.serviceResourceId = $event.serviceResourceId;
    this.siteList.push($event);
    this.getSite();
    this.getSiteById(this.res.siteId);

  }


  getAssetsBySerialNmber(assetsSerialNumber) {
    this.spinner.show();
    this.caseservice.getCaseAssets(assetsSerialNumber).subscribe(data => 
      { this.spinner.hide();
      if (data != null && data.serialNumber != null) {
        this.assests = data;
        this.isAssetSerialnmbr = true;
        if (data.siteId == 0) {
          this.res.siteId = null;
        }
        else {
          this.res.siteId = data.siteId;
          this.assetSerialnoCase = data.serialNumber;
          this.res.siteManualId = data.siteManualId;
          console.log(data.siteId)
        }
        this.assetSerialnoCase = data.serialNumber;
        this.res.siteManualId = data.siteManualId;
        this.res.siteId=data.siteId;
        this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList(); //creation

       console.log(this.siteList);
        this.expirydate = data.warrantyExpiryDate;
        if (data.siteId !== 0) {
          console.log(data.siteId);
          this.getSiteById(data.siteId);
        }
        this.expirydate = this.datePipe.transform(this.expirydate, 'yyyy-MM-dd');
        if (data.isWarrantyExist==1 || data.isAMCWarrantyExist ==1) {
          this.indate = true;
          this.outdate = false;

          if (this.domainId === 1) {
            this.res.caseTypeID = 3;
          } else if (this.domainId === 2) {
            this.res.caseTypeID = 7;
          }
        } else {
          this.outdate = true;
          this.indate = false;

          if (this.domainId === 1) {
            this.res.caseTypeID = 4;
          } else if (this.domainId === 2) {
            this.res.caseTypeID = 8;
          }
        }
        console.log(this.res.caseTypeID);
        this.getCaseReason(this.res.caseTypeID);
      } else {
        this.assetNot = true;
      }

    },error => {
      this.spinner.hide();
      // this.res.assetsSerialNumber = null;
      this.message = error.error.message;
      this._global.alert({type:'alert',message:this.message})


    });
  }
  getContactByAccount(sapNumber: string) {
    this.spinner.show();
    this.accountservice.getBtContactList(sapNumber).subscribe(data => {
      this.spinner.hide();
      this.ContactOfAccount = [];
      data.forEach((ct) => {
        let _ct = new ContactForList();
        // this.res.contactPersonId=ct.contactid;
        _ct.firstName = ct.contactFirstName;
        _ct.lastName = ct.contactLastName;
        _ct.middleName = ct.contactMiddleName;
        _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+' '+ct.contactMobileNumber;
        _ct.phoneNumber = ct.contactMobileNumber;
        _ct.contactId = ct.contactid;
        this.ContactOfAccount.push(_ct);


      }, (error => {
        this.spinner.hide();
        this.message = error.error.essage;
        this._global.alert({ type: 'alert', message: this.message });

      })

      );
    });
  }
  public getAssetsListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getBtAssetsListByAsscountSapNumber(sapNumber).subscribe(asst => {
      // this.assetList = asst;
    });
  }
  updateAssetList(){
    let temp=[];
    const sfound=[];
    for(let i=0;i<this.assetList.length;i++){
      let found=false;
      for(let j=0;j<sfound.length;j++){
        if(sfound[j]==this.assetList[i].id){
          found=true;
          break;
        }
      }
      if(!found){
        if(this.assetList[i].id!=null&&this.assetList[i].serialNumber!=undefined&&this.assetList[i].serialNumber!=''){
         
          // ({id:caseRes.assetId,serialNumber:caseRes.assetsSerialNumber,productPartCode:caseRes.assetsPartCode,productDescription:caseRes.assetDescription})
          temp.push({id:this.assetList[i].id,seialNumber:this.assetList[i].serialNumber,productPartCode:this.assetList[i].productPartCode,productDescription:this.assetList[i].productDescription,asstPartCode:this.assetList[i].serialNumber+this.assetList[i].productPartCode});

          sfound.push(this.assetList[i].id);
        }
        
      }
    }
    this.assetList=temp;
  }
  //
  public getSitesListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getBtSitesList(sapNumber).subscribe(st => {
      this.siteList = [];
      st.forEach((s) => {
        let _st = new BT_SiteDetailForList();
        _st.batterySiteId = s.siteId;
        _st.manuaSitelId = s.siteManualId;
        _st.siteName = s.siteName;
        this.siteList.push(_st);
      });
      this.updateSiteList();

    });
  }


  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');
  //   }
  // }
    // shagufta
    onlyNumbers(key): boolean {
      const charCode = (key.which) ? key.which : key.keyCode;
     
      if (charCode > 31 && (charCode < 48 || charCode > 57 || charCode.length>4)) {
        return false;
      }
      else if(this.LimteExceed==true){
        return false;
      }
      return true;
  
    }
    LimteExceed:boolean;
    onKey(event):any {
       if(event.target.value.length>1){
         this.LimteExceed=true;
        
       }
       else{
         this.LimteExceed=false;
       }
       
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
      missingast:boolean=false;
      MissingAssetGet(){
        if(this.missingast==true){
          this.res.assetsId=null;
          // this.res.siteId=null;
          
          this.ngClassAssetSerial='form-group'
          this.ngClassMissingAsset = 'form-group required';
          this.res.assetDescription='';
          this.res.assetDescription=null
          this.indate = false;
          this.outdate = false;

        }
        else{
          this.res.missingAssets=null;
          this.res.assetsTypeChoiceId=null;
        
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial='form-group required'
        }
        

      }
      AssetTypeList:Array<AssetTypeChoice>=[]
      getAssetTypeChoice() {
        this.caseservice.getAssetTypeChoice()
        .subscribe((data) => {
        this.AssetTypeList=data;
        });}

        getSiteTini(key) {
          this.caseservice.getSitesForListTini(key.term).subscribe(data => {
            this.siteList = data;
            this.siteList.forEach(e=>{
            })
          });
        }
        PrblmmasterTypeList:Array<BtProblemMaster>=[];
        getProblmMaster(){
          this.caseservice.getCaseProblemMasterList().subscribe(data=>{
         this.PrblmmasterTypeList=data;
          })
        }
        getProblms(id){
          this.problemOccured=[]
          this.ProblemReport(id);
      }


      // get pageable contact number

      getsmallcontactonKey(key){
        if(key.term!='' || key.term!=' '){
        console.log(key.term);
        this.ContactOfAccount=new Array<BtContactForlist>();
        this.caseservice.getContactSmall(this.siteaccountsapnmbr,key.term).subscribe(data=>{
          this.ContactOfAccount = new Array<BtContactForlist>();
          let temp=[]
          console.log(data)
          data.forEach((ct) => {
            let _ct = new BtContactForlist;
           // this.res.contactPersonId=ct.contactid;
           ct.firstName = ct.contactFirstName;
            _ct.lastName = ct.contactLastName;
            _ct.middleName = ct.contactMiddleName;
            _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+''+ct.contactMobileNumber;
            _ct.phoneNumber = ct.contactPhoneNumber;
            _ct.contactId = ct.contactid;
            _ct.phoneNumber=ct.contactMobileNumber;
            temp.push(_ct);
            this.ContactOfAccount=temp;
          });
          console.log(this.ContactOfAccount);
        },(error => {
          this.spinner.hide();
          this.message = error.error.essage;
          this._global.alert({type:'alert',message:this.message})
    
        }))
      }
      }
}
