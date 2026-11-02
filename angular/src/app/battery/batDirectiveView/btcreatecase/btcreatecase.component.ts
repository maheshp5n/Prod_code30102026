import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AccountService } from '../../../services/accounts.services';
import { ContactsService } from '../../../services/contacts.services';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { Loginservices } from '../../../services/login.services';
import { CasePriority, ContactForList, Contacts, PendingResponsibility, ProblemReported, selected } from '../../../services/model';
import { PreRegCaseService } from '../../../services/preregcaseservice.services';
import { siteService } from '../../../services/siteService.services';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { AssetForList, AssetTypeChoice, BTassetResp, BTAssetsForList, BTCaseReasonForList, BTCaseRequest, BTCaseResponse, BTCaseStageDetails, BTCaseTypeForList, BTCaseTypeRecord, BtContactForlist, BtPreRegCaseRequest, BtProblemMaster, btServiceAppointmentResponseWithSCH, BT_CaseStatus, BT_SiteDetailForList, GetAllBTsiteResp } from '../../bt_model';

@Component({
  selector: 'app-btcreatecase',
  templateUrl: './btcreatecase.component.html',
  styleUrls: ['./btcreatecase.component.css']
})
export class BTCreatecaseComponent implements OnInit {

  @ViewChild('createSiteModel') createSiteModel: ModalDirective;
  @ViewChild('createLegalEntityModel') createLegalEntityModel: ModalDirective;
 @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('createContactModel') createContactModel: ModalDirective;
  @ViewChild('chooscaseFiles') chooscaseFiles : ModalDirective;
  @ViewChild('choosenFiless') choosenFiless: ModalDirective
  @Output()
  onCaseCreated: EventEmitter<BTCaseResponse> = new EventEmitter<BTCaseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  domainId: number;
  NewBtn: boolean = false;
  assetResponse: BTassetResp = new BTassetResp()
  res: BTCaseResponse = new BTCaseResponse();
  req: BTCaseRequest = new BTCaseRequest();
  caseTypeList: Array<BTCaseTypeForList> = [];
  casetypRecord: Array<BTCaseTypeRecord> = [];
  caseReason: Array<BTCaseReasonForList> = [];
  // leaglEntityList: Array<ServiceCenterForList> = [];
  caseReasonList: BTCaseReasonForList;
  problem:ProblemReported = new ProblemReported();
  heading: string;
  edit: boolean = false;
  newcontact: any;
  CREATE_ACCOUNT_HEADING: string = 'Create Site';
  EDIT_ACCOUNT_HEADING: string = 'Edit Site ';
  caseRecordTypeName: string;
  i: any = 0;
  loginData: any;
  verticalname: number;
  selected: string;
  AssetsSerialNo: string;
  ContactOfAccount: Array<BtContactForlist> = [];
  contactByAccount: any;
  assests: BTassetResp = new BTassetResp();
  // siteres: SiteDetailsResponse = new SiteDetailsResponse();
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
  todayDate3:Date = new Date();
  message: string;
  problem1: ProblemReported = new ProblemReported();
  pendingres: Array<PendingResponsibility>=[];
  // origin: Array<CaseOriginDetails> = [];
  caseStatusName: any;
  status: Array<BT_CaseStatus> = [];
  contactId: number;
  priority: Array<CasePriority>;
  stage: Array<BTCaseStageDetails>;
  siteList: Array<BT_SiteDetailForList>=[];
  AccountList: Array<any>;
  assets: any;
  astserialNumbr: any
  legalEntityList: any;
  contact: any
  contactID: any;
  mobileNo: any;
  isAssetSerialNo: boolean;
  uploadForm: FormGroup;
  // noMatchAsset: string;
  // noMatchSite:string
  id: number;
  indate: boolean = false;
  outdate: boolean = false;
  assetSerialnoCase: string;
  assetNot: boolean = true;
  serviceResourcesList: Array<any> = [];
  notBatryDCT: boolean = false;
  popupNgClass: string = 'alert alert-danger center center-block';
  tampCaseId: number;
  tempCaseName: string;
  fullName: string;
  isSiteEditMode:boolean;
  ngClassMissingAsset = 'form-group';
  ngClassAssetSerial = 'form-group'
  colAsset = 'col-md-10 col-sm-10'
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  installationTrue:boolean=false;
  isContactEditMode:boolean;
  isAssetEditMode:boolean;
  siteres: GetAllBTsiteResp =new GetAllBTsiteResp();
  leaglEntityList: any;
  PrblmmasterTypeList:Array<BtProblemMaster>=[];
  AssetTypeList:Array<AssetTypeChoice>=[];
  caseStatusidEdit:number;
  caseStatusnameEdit:string;
  notforsch:boolean=false;
  casebutton:string='Create';
  prcrew:BtPreRegCaseRequest=new BtPreRegCaseRequest();
  serviceCentr: number;
  contactfullname:string;
  pendingresEdit:number;
  assetAcountSAPNUMBER:string;
  fromsite:boolean=false;
  datepipe:DatePipe;

  todayDate=new Date();
    _visitDate=new Date();
  _todayDate3:Date = new Date();
    _datetoday3:Date = new Date();
    createDateMin:Date;
  // getTypeaheadConfig(): TypeaheadConfig {
  //   return Object.assign(new TypeaheadConfig(), { hideResultsOnBlur: false });
  // }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
 
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
  set isInstallcase(installationtrue:boolean){
    this.installationTrue=installationtrue;
    
  }
  get isInstallcase():boolean{
    return this.installationTrue;
  }
  @Input()
  set siteId(siteId: number) {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
   
    if(siteId!=null){
      this.res.siteId = siteId;
    this.installationTrue=true;
     this.fromsite=true;
      // this.getSiteById(this.res.siteId);
      this.missingast=true;
    this.MissingAssetGet();
        this.spinner.show()
        this.siteservice.getBtSitedataById(this.res.siteId).subscribe(data => {
          this.spinner.hide();
       
          if (data != null) {
            this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
            this.updateSiteList();
            this.siteres = data;
            this.res.serviceCenterId = data.serviceCenterId;
            this.res.serviceResourceId=data.serviceResourceId;
            this.res.accountSapNumber = data.accountSapNumber;
            this.siteaccountsapnmbr=data.accountSapNumber;
            this.res.accountName = data.accountName;
            this.getServiceResForList(this.res.serviceCenterId);
            //  this.res.serviceResourceId = data.serviceResourceId;
            // this.getContactByAccount(data.accountSapNumber);
          }
          });
          // 
          if(this.installationTrue==true){
          if (this.domainId === 1) {
            this.res.caseTypeId = 2;
            this.ngClassMissingAsset = 'form-group';
            this.getCaseReason(this.res.caseTypeId);
          
            this.res.caseReasonId = 8;
          
          }
          else {
            this.res.caseTypeId = 6;
            this.getCaseReason(this.res.caseTypeId);
            this.res.caseReasonId = 18;
            this.ngClassMissingAsset = 'form-group';
          }
      }
    
      
    }

  }
  get siteId(): number {
    return this.res.siteId;
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
  set AssetSerial(caseRes:BTCaseResponse){
    
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    // console.log("Asset from");
    this.res.siteId=caseRes.siteId;
    this.res.siteManualId=caseRes.siteManualId;
    this.res.assetsId=caseRes.assetsId;
    this.assetAcountSAPNUMBER=caseRes.accountSapNumber;
    if(caseRes.assetsSerialNumber!=null&&caseRes.assetsSerialNumber!=undefined&&caseRes.assetsSerialNumber!=''){
      this.assetList.push({id:caseRes.assetsId,serialNumber:caseRes.assetsSerialNumber,productPartCode:caseRes.assetsPartCode,productDescription:caseRes.assetDescription,asstPartCode:caseRes.assetsSerialNumber+caseRes.assetsPartCode});
   
    }
    this.spinner.show()
    this.siteList.push({batterySiteId:caseRes.siteId,manuaSitelId:caseRes.siteManualId,siteName:caseRes.siteName});
    this.updateSiteList();
    this.siteservice.getBtSitedataById(caseRes.siteId).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        // this.siteList.push({siteId:data.siteId,siteManualId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;
        this.res.accountSapNumber = data.accountSapNumber;
        // this.assetAcountSAPNUMBER=data.accountSapNumber
        this.siteaccountsapnmbr=data.accountSapNumber  //new add here for site account from asset from
        this.res.accountName = data.accountName;
        this.getServiceResForList(this.res.serviceCenterId);
        //  this.res.serviceResourceId = data.serviceResourceId;
        // this.getContactByAccount(data.accountSapNumber);
        this.siteaccountsapnmbr=data.accountSapNumber;
      }
      });
    this.res=caseRes;
    this.spinner.show();
    this.caseservice.getCaseTypes(1).subscribe((data) => {
      this.spinner.hide()
      this.caseTypeList = data;

    });

    //shagg

    if(this.assetWarenty==null){
      if(this.domainId==1){
        this.res.caseTypeId=4;
        this.getCaseReason(this.res.caseTypeId);
        // this.res.caseReasonId=5;
        }
        else{
          this.res.caseTypeId=8;
          this.getCaseReason(this.res.caseTypeId);
          // this.res.caseReasonId=17;
          
        }

    }
   else if(this.assetWarenty=="Without Warranty/AMC"){
     if(this.domainId==1){
     this.res.caseTypeId=4;
     this.getCaseReason(this.res.caseTypeId);
    //  this.res.caseReasonId=5;
     }
     else{
       this.res.caseTypeId=8;
       this.getCaseReason(this.res.caseTypeId);
      //  this.res.caseReasonId=17;
       
     }
   }
   else if(this.assetWarenty=="Under Warranty"){

     this.res.caseTypeId=3;
     this.getCaseReason(this.res.caseTypeId);
    //  this.res.caseReasonId = 27;
   }
   else if(this.assetWarenty=="Under AMC"){
    if(this.domainId==1){
      this.res.caseTypeId = 10;
        this.getCaseReason(this.res.caseTypeId);
        // this.res.caseReasonId = 8;
        this.ngClassMissingAsset = 'form-group required';
     }
     else{
      this.res.caseTypeId = 9;
      this.getCaseReason(this.res.caseTypeId);
      // this.res.caseReasonId = 11;
      this.ngClassMissingAsset = 'form-group required';
  
     }
     
   }
   else{
    if(this.domainId==1){
      this.res.caseTypeId = 2;
        this.getCaseReason(this.res.caseTypeId);
        // this.res.caseReasonId = 3;
        this.ngClassMissingAsset = 'form-group required';
     }
     else{
      this.res.caseTypeId = 7;
      this.getCaseReason(this.res.caseTypeId);
      // this.res.caseReasonId = 11;
      this.ngClassMissingAsset = 'form-group required';
  
     }
   }

  }
  get AssetSerial():BTCaseResponse{
     return  this.res;
  }
 
@Input()
set SiteSeviceResourceID(siteseviceResourceID:number){
//  this.res.serviceResourceId=siteseviceResourceID;
 ;
}
get SiteSeviceResourceID():number{
  return this.res.serviceResourceId;
}

  @Input()
  set editEntityData(res: BTCaseResponse) {
    console.log("edit");
    console.log(res)
    console.log(res.missingAssets)
    if (res!=null&&res!=undefined) {
      // this.res.manualId=r;
      if(res.missingAssets!=null && res.missingAssets!=''){
        this.missingast=true;
        this.req.assetsId = null;
      }else{
        this.missingast=false;
        this.req.assetsId=this.res.assetsId;
      }
      this.assetAcountSAPNUMBER=res.accountSapNumber;
      this.res.id=res.id;
   this.res.problemMasterId=res.problemMasterId
      this.tampCaseId=res.caseId
    this.res=res;
    this.res.caseOriginId=res.caseOriginId;
    this.res.casesubReason=res.casesubReason;
    this.res.caseReasonName=res.caseReasonName;
    this.res.typeChoice=res.assetsTypeChoiceId;
    this.res.caseTypeId=res.caseTypeId;
    this.res.caseStageId=res.caseStageId;
    this.res.pendingResponsibility=res.pendingResponsibilityId;
    this.caseStatusidEdit=res.caseStatusId;
    this.caseStatusnameEdit=res.caseStatusName;
    this.pendingresEdit=this.res.pendingResponsibility;
    console.log(this.res.pendingResponsibility);
    console.log(this.pendingresEdit);
    this.res.isAssignByAssignmentRule=res.isAssignByAssignmentRule;
    this.res.RJILTT01No=res.rjil_TT01_No
   this.res.poamount= this.res.poamount;
    this.res.PONumber=this.res.ponumber;
    this.res.PODate=   this.res.podate;
    this.res.lineitem=this.res.lineitem;
    this.res.isRolCreated=res.isRolCreated;
    //new
    this.res.openDate=res.openDate;
    // let dateNew = new Date();
    // this.res.openDate=new Date(res.openDate);
    // console.log("this.req.openDate.getFullYear()",this.res.openDate.setFullYear(this.res.openDate.getFullYear()));
    // console.log("this.req.openDate.getFullYear()",this.res.openDate.setMonth(this.res.openDate.getMonth()));
    // console.log("this.req.openDate.getFullYear()",this.res.openDate.setDate(this.res.openDate.getDate()));
    // console.log("this.req.openDate.getFullYear()",this.res.openDate.setHours(this.res.openDate.getHours() -5));
    // console.log("this.req.openDate.getFullYear()",this.res.openDate.setMinutes(this.res.openDate.getMinutes() -30));
    
    console.log("Final Date",this.res.openDate);
    
    
    this.res.ticketNo=res.ticketNo;
    
    console.log(this.res.isRolCreated)
    this.res.rolRemark=res.rolRemark;
    this.res.rmaCreated=res.rmaCreated;
    if(res.contactPersonId!=null){
      if(this.res.contactPersonMiddleName!=null){
        this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonMiddleName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
       }else{
         this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
       }
       this.ContactOfAccount.push({contactId:this.res.contactPersonId,emailId:this.res.contactPersonEmailId,firstName:this.res.contactPersonFirstName,middleName:this.res.contactPersonMiddleName,lastName:this.res.contactPersonLastName,phoneNumber:this.res.contactPersonContactNo,fullName:this.contactfullname})
      this.res.contactPersonId=res.contactPersonId;   
    }
       // this.updateCOntactList();
    // this.res.caseReasonId=res.caseReasonId;
    // this.res.caseReasonName=res.caseReasonName;
    // this.getCaseReason(this.res.caseTypeId);
    if(res.assetsSerialNumber!=null&&res.assetsSerialNumber!=undefined&&res.assetsSerialNumber!=''){
      this.assetList.push({id:res.assetsId,serialNumber:res.assetsSerialNumber,productPartCode:res.assetsPartCode,productDescription:res.assetDescription,asstPartCode:res.assetsSerialNumber+res.assetsPartCode});
    }
    if(res.siteId!=null&&res.siteId!=undefined){  
      this.siteList.push({batterySiteId:this.res.siteId,manuaSitelId:this.res.siteManualId,siteName:this.res.siteName});
      // this.updateSiteList();
    }
    
     this.res.assetsId=res.assetsId;
    this.loginData = this.loginservice.getLoggedInUser();
  //  if(this.isEditMode==false){
    // new add

  
    if(this.res.caseTypeId==2 || this.res.caseTypeId==6){
      this.installationTrue=true;
    }else{
      this.installationTrue=false;
    }
    if(this.res.assetsSerialNumber!=undefined&&this.res.assetsSerialNumber!=null){
      this.caseservice.getCaseAssets(this.res.assetsSerialNumber)
      .subscribe(data=>{
        if(data==null){
          return;
        }
        if( data==null||data.isAMCWarrantyExist==null){
          data.isAMCWarrantyExist=0;
        }
        if(data==null||data.isWarrantyExist==null){
          data.isWarrantyExist=0;
        }
        if (data.isAMCWarrantyExist || data.isWarrantyExist) {
          this.indate = true;
          this.outdate = false;
  
          // if (this.domainId === 1 &&this.res.caseTypeId ==3) {
            
          //   this.getCaseReason(this.res.caseTypeId);
            
          // } else if (this.domainId === 2 && this.res.caseTypeId ==7) {
            
          //   this.getCaseReason(this.res.caseTypeId);
          // }
        } 
        else {
          this.outdate = true;
          this.indate = false;
  
          // if (this.domainId === 1&&this.res.caseTypeId == 4) {
            
          //   this.getCaseReason(this.res.caseTypeId);
          //  this.res.caseReasonId=5;
          // }
          //  else if (this.domainId === 2 &&this.res.caseTypeId ==8) {
            
          //   this.getCaseReason(this.res.caseTypeId);
          //  this.res.caseReasonId=17;
          // }
        }
      });
    } 
  
  //  }
  //  else{
  //    this.res=new CaseResponse();
  //  }
    
    
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
             this.siteaccountsapnmbr=data.accountSapNumber
             
            //  this.res.serviceCenterId = data.serviceCenterId;
             this.res.accountSapNumber = data.accountSapNumber;
             this.res.accountName = data.accountName;
            //  this.res.serviceResourceId = data.serviceResourceId;
             this.spinner.show();
            //  this.accountservice.getContactList(data.accountSapNumber).subscribe(data => { 
            //    this.spinner.hide();
            //    this.ContactOfAccount = [];
            //    data.forEach((ct) => {
            //      let _ct = new ContactForList();
            //      _ct.firstName = ct.contactFirstName;
            //      _ct.lastName = ct.contactLastName;
            //      _ct.middleName = ct.contactMiddleName;
            //      _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+' '+ct.contactMobileNumber;
      
            //      _ct.phoneNumber=ct.contactMobileNumber;
            //      _ct.contactId = ct.contactid;
            //      this.ContactOfAccount.push(_ct);
            //      console.log(this.ContactOfAccount);
            //   }, (error => {
            //      this.spinner.hide();
            //      this.message = error.error.essage;
            //      this._global.alert({type:'alert',message:this.message})
            //     }
            //      )
            //    );
            //  }); 
            
            }
         });
        // this.res.isAssignByAssignmentRule = true;
        this.problemOccured = this.res.problemReported;
        this.spinner.show();


        
        this.caseservice.getProblemReported(this.res.problemMasterId)
        .subscribe(data => {
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
        this.caseservice.getCaseTypes(this.domainId)
        .subscribe((data) => {
          this.spinner.hide()
          this.caseTypeList = data;

        });
        this.spinner.show();
        this.caseservice.getCaseReason(this.res.caseTypeId)
        .subscribe((data) => {
          this.spinner.hide();
          this.caseReason = data;
        });


        this.caseservice.getCaseStatus()
        .subscribe(data => {
            // this.status = data;
            const tempst=[];
            data.forEach(e=>{

              let sts=new BT_CaseStatus();
              if(this.notforsch==true)
              { 
               //  for sch 
                if( e.id==3){
                 sts.caseStatusName=e.caseStatusName;
                 sts.id=e.id;
                 sts.isDesable=false;
               }else if(e.id==2){
                 sts.caseStatusName=e.caseStatusName;
                 sts.id=e.id;
                 sts.isDesable=true;
               }
              else if(e.id==1){
                 sts.caseStatusName=e.caseStatusName;
               sts.id=e.id;
               sts.isDesable=true;
               }
               tempst.push(sts);
             }else{
               // for call center
               if( e.id==3){
                 sts.caseStatusName=e.caseStatusName;
                 sts.id=e.id;
                 sts.isDesable=true;
               }else if(e.id==2){
                 sts.caseStatusName=e.caseStatusName;
                 sts.id=e.id;
                 sts.isDesable=false;
               }
              else if(e.id==1){
                 sts.caseStatusName=e.caseStatusName;
               sts.id=e.id;
               sts.isDesable=false;
               }
               tempst.push(sts);
             }
       
             })
             this.status=tempst;
            // console.log(this.status);
            // this.getstage(this.res.caseStatusId);
            // this.status=tempst;
          });
        if (this.res.caseStatusId !== null && this.domainId !== null) {
          this.caseservice.getCaseStage(this.res.caseStatusId)
          .subscribe(data => {
            this.stage = data;
            this.stage.forEach(e=>{
              if(e.id==res.caseStageId){
                this.res.caseStageId=e.id;
              }
            })
          });
        
          
        }
        this.spinner.show();
        this.caseservice.pendingrisponsibility()
        .subscribe(data => {
          this.spinner.hide();
          //  this.pendingres = data;
          data.forEach(e=>{
            if(e.pendingResponsibilityName!='None'){
              this.pendingres.push(e);
            }
          })
        });
      } else {
        this.problemOccured = [];
        // if (this.res.assetsSerialNumber != null && this.res.assetsSerialNumber.length > 0) {
        //   this.getAssetsBySerialNmber(this.res.assetsSerialNumber);
        // }
     
        if (this.res.accountSapNumber != null && this.res.accountSapNumber.length > 0) {
          this.getAssetsListByAccountSapNumber(this.res.accountSapNumber);
          this.getSitesListByAccountSapNumber(this.res.accountSapNumber);
        }
      }
      this.spinner.hide();
    }
    else {
           this.res = new BTCaseResponse();
       }
  }

  get editEntityData(): BTCaseResponse {
    return this.res;
  }

  openModlues = { siteModuleOpen: false, legalEntityModuleOpen: false, assetsModuleOpen: false, contactModuleOpen: false }
  constructor(private siteservice: BtsiteserviceService, private loginservice: Loginservices,
    private preregcaseservice:BtPreRegistrationCaseService, private _activatedRoute: ActivatedRoute, private caseservice: BtCaseserviceService, private legalEntityService: BtlegalentityService, 
    private accountservice: AccountService, private spinner: NgxSpinnerService, private datePipe: DatePipe, 
    private contatactService: BtcontactsService,private _assetsService:BTassetserviceService,private formBuilder:FormBuilder
    , private _global:GlobalserviceService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];

    this.loginData = this.loginservice.getLoggedInUser();
    //  if(this.isEditMode==false){
      // new add
      // console.log("dataaa")

      if(this.loginData.roleGroupAndAccount.roleGroup=="SCH")
      {
        this.notforsch=true;
        // console.log("sch")
      }
      else{
        this.notforsch=false;

      }
      // console.log(this.notforsch)

    
  }
  selectDate(myDate){
    let cusDate = new Date(myDate);
    console.log("cys Data",cusDate);
    

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


    //this.getAssestList();
    this.getLoggedInUser();
    // this.caseRecord();
    this.caseOrigin();
    this.caseSReason();
    this.casePeriority();
    this.getAccountList();
    this.getAssetTypeChoice();
    //this.getSite();
    // this.getCaseStatus();
    this.getProblmMaster();
 if(this.isEditMode!=true){  
  this.getCaseStatus();
    this.getcaseType();
    this.Risponsibility();
  }
    this.LegalENtityList();
    // this.ProblemReport();

    if(!this.isEditMode){
      this.casebutton='Create';
      this.res.caseStatusId = 1;
      this.getstage(this.res.caseStatusId);
    //   if(this.res.caseStatusId==1)
    //  { 
    //    this.getstage(this.res.caseStatusId);
    //   this.res.caseStageId=1;
    // }else{
    //   this.res.caseStageId=0
    //
  
    
    }
    else{
      this.casebutton='Update';
    }
 
    
   
    this.spinner.show();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    //  this.getContactsData();
    if (!this.isEditMode && this.res.siteId==null) {
      if (this.domainId === 1) {
        this.notBatryDCT = true;
        this.res.caseTypeId = 3;
        this.getCaseReason(this.res.caseTypeId);
        this.res.caseReasonId = 21;
      }
      else {
        this.notBatryDCT = false;
        this.res.caseTypeId = 7;
        this.getCaseReason(this.res.caseTypeId);
        this.res.caseReasonId = 11;
      }
    }
    this.todayDate3.setDate(this.todayDate.getDate() -5);
    
  }

   roleGroup:string;
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    this.roleGroup=this.loginData.roleGroupAndAccount['roleGroup'];
  

    // this.loginData.empType['businessDomainId'];
  }

  caseType() {
    this.caseservice.getCaseTypes(this.domainId)
    .subscribe(data => {
      this.caseTypeList = data;
    });
  }


  Risponsibility() {
    this.caseservice.pendingrisponsibility()
    .subscribe(data => {
      // this.pendingres = data;
      data.forEach(e=>{
        if(e.pendingResponsibilityName!='None'){
          this.pendingres.push(e);
        }
      })

    });
  }

  LegalENtityList() {
    this.legalEntityService.getLegalEntityForList().subscribe((data) => {
      this.leaglEntityList = data;
    });
  }
  getCasereasonchange(caseTypeId) {
    if(caseTypeId==2 || caseTypeId==6){
      this.installationTrue=true;
    }else{
      this.installationTrue=false;
    }
    this.getCaseReason(caseTypeId);





  }



  getcaseType() {
    this.caseservice.getCaseTypes(this.domainId)
    .subscribe((data) => {
      this.caseTypeList = data;
    });
    this.getCaseReason(this.res.caseTypeId);


  }

  getProblmMaster(){
    this.caseservice.getCaseProblemMasterList().subscribe(data=>{
   this.PrblmmasterTypeList=data;
    })
  }
  getProblms(id){
    
    this.problemOccured = [];
    this.ProblemReport(id);
}
  getAssetTypeChoice() {
    this.caseservice.getAssetTypeChoice()
    .subscribe((data) => {
    this.AssetTypeList=data;
    });}
    getCaseReason(caseTypeId) {
    this.spinner.show();
    this.caseservice.getCaseReason(caseTypeId)
    .subscribe((data) => {
      if (data != null) {
        this.spinner.hide();
      }
      this.caseReason = data;

      if (this.domainId === 2) {
        if (this.res.caseTypeId === 5) {
          this.ngClassMissingAsset = 'form-group required';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 12;
          return;
        }
        if (this.res.caseTypeId === 6) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 18;
          return;
        }
        if (this.res.caseTypeId === 7) {
          this.ngClassMissingAsset = 'form-group';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
          }
          this.res.caseReasonId = 11;
          return;
        }
        if (this.res.caseTypeId === 8) {
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
          }
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 17;

        }
        if(this.res.caseTypeId===9){
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
          }
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 30;
        }

      } 
      else if (this.domainId === 1) {
        if (this.res.caseTypeId === 1) {
          // this.ngClassMissingAsset = 'form-group required';
          // this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 7;
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          return;

        }
        if (this.res.caseTypeId === 2) {
          // this.ngClassMissingAsset = 'form-group';
          // this.ngClassAssetSerial = 'form-group'; 
          this.res.caseReasonId = 8;
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          return;
        }
        if (this.res.caseTypeId === 3) {
          // this.ngClassMissingAsset = 'form-group';
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
        if (this.res.caseTypeId === 4) {
          // this.ngClassMissingAsset = 'form-group';
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          this.res.caseReasonId = 21;
          return;
        }

        if(this.res.caseTypeId===11){
          if(this.missingast==false){
            this.ngClassAssetSerial = 'form-group required';
          }
          else{
            this.ngClassAssetSerial = 'form-group';
            this.ngClassMissingAsset = 'form-group required';
          }
          this.res.caseReasonId=48;
          return;
        }
      }
    }, error => {
      this.spinner.hide();
    });
  }
  ProblemReport(id) {

    this.spinner.show();
    this.caseservice.getProblemReported(id)
    .subscribe(data => {
      this.AllProblemList = data;
      this.spinner.hide()
    });

    if (this.domainId === 1) {
      this.notBatryDCT = true;

    }
    else {
      this.notBatryDCT = false;
    }
    if(!this.isEditMode){
      // this.res.caseStatusId = 1;
      // this.getstage(this.res.caseStatusId);
      // this.res.caseStageId=1;
    }
  }
  getCaseStatus() {
    this.caseservice.getCaseStatus()
    .subscribe(data => {
      // this.status = data;
      const tempst=[];
      data.forEach(e=>{

        let sts=new BT_CaseStatus();
      //   if(e.id==2)
        
      // {  sts.caseStatusName=e.caseStatusName;
      //   sts.id=e.id;
      //   sts.isDesable=false;
      //   console.log("yess status 2")
      // }
        // else{
        //   sts.caseStatusName=e.caseStatusName;
        // sts.id=e.id;
        // sts.isDesable=false;
        // }
        if(this.notforsch==true)
       { 
        //  for sch 
         if( e.id==3){
          sts.caseStatusName=e.caseStatusName;
          sts.id=e.id;
          sts.isDesable=false;
        }else if(e.id==2){
          sts.caseStatusName=e.caseStatusName;
          sts.id=e.id;
          sts.isDesable=true;
        }
       else if(e.id==1){
          sts.caseStatusName=e.caseStatusName;
        sts.id=e.id;
        sts.isDesable=true;
        }
        tempst.push(sts);
      }else{
        // for call center
        if( e.id==3){
          sts.caseStatusName=e.caseStatusName;
          sts.id=e.id;
          sts.isDesable=true;
        }else if(e.id==2){
          sts.caseStatusName=e.caseStatusName;
          sts.id=e.id;
          sts.isDesable=false;
        }
       else if(e.id==1){
          sts.caseStatusName=e.caseStatusName;
        sts.id=e.id;
        sts.isDesable=false;
        }
        tempst.push(sts);
      }

      })
      this.status=tempst;
  if(!this.isEditMode) 
  {  
     this.getstage(this.res.caseStatusId);
    }
    });


    if (this.domainId === 1) {
      this.res.caseStageId = 5;
    } else if (this.domainId === 2) {
      this.res.caseStageId = 11;
    }
    if(this.isEditMode!=true){
      this.res.caseOriginId = 3;
      this.res.casePriorityId = 2;
     }
  }

  origin:any;
  caseOrigin() {
    this.caseservice.getCaseOrigin().subscribe((data) => {
      this.origin = data;
    });
  }
  caseSubReason:any;
  caseSReason(){
    this.caseservice.getCasesubReason().subscribe((data) =>{
      this.caseSubReason =  data;
    });
  }
  getstage(id) {
    if (id != null && this.domainId != null) {
      this.spinner.show();
      this.caseservice.getCaseStage(id)
      .subscribe(data => {
        this.spinner.hide();
        this.stage = data;
        if (this.stage.length > 0) {
          // this.res.caseStageId = this.stage[0].id;
        }
      });
      if(id==1){
        this.res.caseStageId=1
      }
      else if(id==3){
        this.res.caseStageId=5;
      }
      else if(id==2){
        this.res.caseStageId=2
      }
      else{
        this.res.caseStageId=0
      }

    }
  }
  casePeriority() {
    this.caseservice.getCasePriority().subscribe(data => {
      this.priority = data;
    });
  }
  getSite() {
    this.caseservice.getSitesForList()
    // .subscribe(data => {
    //   this.siteList = data;
    // });
  }

  getSiteTini(key) {
    this.caseservice.getSitesForListTini(key.term).subscribe(data => {
      this.siteList = data;
      this.siteList.forEach(e=>{
      })
    });
  }
siteaccountsapnmbr:string;
  getSiteById(siteid) {
    this.spinner.show()
    this.siteservice.getBtSitedataById(siteid).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;
        this.res.accountSapNumber = data.accountSapNumber;
        console.log(this.res.accountSapNumber);
        this.res.accountName = data.accountName;
        this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.getServiceResForList(this.res.serviceCenterId);
       this.res.serviceResourceId = data.serviceResourceId;
        if (this.res.assetsId === null || this.res.assetsId === undefined || this.res.assetsId === 0) {
          if (this.domainId === 1) {
            this.res.caseTypeId = 1;//exceptional
            this.getCaseReason(this.res.caseTypeId);
            this.res.caseReasonId = 2;
            this.ngClassMissingAsset = 'form-group required';
          }
          else {
            this.res.caseTypeId = 5;
            this.getCaseReason(this.res.caseTypeId);
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
    // this.caseservice.getAssestForList().subscribe(data => {
    //   this.assets = data;
    //   this.assetList = this.assets;
    // });
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
        _ct.phoneNumber = ct.phoneNumber;
        _ct.fullName = ct.firstName + ' ' + ct.middleName + ' ' + ct.lastName+''+ct.contactMobileNumber;
        _ct.phoneNumber=ct.contactMobileNumber;
        this.ContactOfAccount.push(_ct);

      },
        (error => {
          this.spinner.hide();
          this.message = error.error.essage;
          this._global.alert({type:'alert',message:this.message})
          // setTimeout(() => {
          //   this.popupSuccessModel.hide();
          // }, 2000
          // );
        })

      );

    })
  }

  reg = new RegExp('^[0-9]$')
  isPRC:boolean=false;
  postCase(isprc) {
    console.log(this.assetAcountSAPNUMBER);
    console.log(this.siteaccountsapnmbr)
    console.log(this.installationTrue);
    if ( this.missingast==false &&(this.res.assetsId === undefined || this.res.assetsId === null) ) {
      this.message = 'Select Assets Serial Number';
      // this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({ type: 'alert', message: this.message });

    }
    else if (this.res.caseTypeId === 0 || this.res.caseTypeId === null) {
      this.message = 'Select Case Type';
      this._global.alert({type:'alert',message:this.message})

    }
 
    // else if ( this.missingast==false &&(this.res.assetsId === undefined || this.res.assetsId === null) ) {
    //   this.message = 'Select Assets Serial Number';
    //   // this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({ type: 'alert', message: this.message });

    // }
    else if ((this.missingast==true) && (this.res.missingAssets==null || this.res.missingAssets=='')) {
      this.message = 'Fill The Missing Asset Serial Number';
      this._global.alert({type:'alert',message:this.message})
    }
    else if((this.res.assetsTypeChoiceId==null || this.res.assetsTypeChoiceId==undefined) && this.missingast==true){
      this.message='Fill Work/Asset type Choice';
      this._global.alert({type:'alert',message:this.message})
    }
    // else if(this.installationTrue==true && (this.res.PONumber==undefined || this.res.PONumber==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
    //   this.message = 'Fill PO Number';
    //   this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({type:'alert',message:this.message})
    // }
    // else if(this.installationTrue==true && (this.res.poamount==undefined || this.res.poamount==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
    //   this.message = 'Fill PO Amount';
    //   this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({type:'alert',message:this.message})
    // }
    // else if(this.installationTrue==true && (this.res.lineitem==undefined || this.res.lineitem==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
    //   this.message = 'Fill Line Item';
    //   this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({type:'alert',message:this.message})
    // }

    // else if(this.installationTrue==true && (this.res.PODate==undefined || this.res.PODate==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
    //   this.message = 'Fill PO Date';
    //   this.popupNgClass = 'alert alert-danger center center-block';
    //   this._global.alert({type:'alert',message:this.message})
    // }
    else if (this.res.caseReasonId === 0 || this.res.caseReasonId === null) {
      this.message = 'Select Any Case Reason';
      this._global.alert({type:'alert',message:this.message})

    }  
    else if (this.res.contactPersonId === null || this.res.contactPersonId === 0 || this.res.contactPersonId==undefined) {
      this.message = 'Select Any Contact Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({type:'alert',message:this.message})

    } 
    else if (this.res.caseStatusId === 0) {
      this.message = 'Select Any Status';
      this._global.alert({type:'alert',message:this.message})

    } else if (this.res.caseStageId === 0) {
      this.message = 'Select Any Stage';
      this._global.alert({type:'alert',message:this.message})

    } 
    else if (this.res.caseOriginId === 0 || this.res.caseOriginId === null) {
      this.message = 'Select Any Case Orig  in Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({type:'alert',message:this.message})

    } 
    else if (this.res.casePriorityId === 0 || this.res.casePriorityId === null) {
      this.message = 'Select Any Priority';
      this.popupNgClass = 'alert alert-danger center center-block';
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.res.internalRemark != undefined && this.res.internalRemark !== undefined && (this.res.internalRemark).length >= 1000) {
      this.message = 'Internal Comments Exceeds 1000 Character';
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.res.otherProblemReported !== null && this.res.otherProblemReported !== undefined && (this.res.otherProblemReported).length > 1000) {
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.res.inputFromEng !== null && this.res.inputFromEng !== undefined && (this.res.inputFromEng).length > 1000) {
      this.message = ' Input From Engineer Exceeds 1000 Character';
      this._global.alert({type:'alert',message:this.message})

    }
    else if (this.res.remarks !== null && this.res.remarks !== undefined && (this.res.remarks).length > 1000) {
      this.message = ' Remark Exceeds 1000 Character';
      this._global.alert({type:'alert',message:this.message})

    }

    else if (this.res.noOfSmr!==null && this.res.noOfSmr!==undefined && (this.res.noOfSmr).length > 4) {
      this.message = 'Number of SMR Exceeds Only Numbers With 2 Character';
      this._global.alert({type:'alert',message:this.message})

    }
    else if(this.problemOccured.length==0){
      this.message = 'Select Any One Problem';
      this._global.alert({type:'alert',message:this.message})
    }
    // else if(this.installationTrue!=true &&() )
    else if( (this.fromsite!=true) &&(this.siteaccountsapnmbr!=this.assetAcountSAPNUMBER)){
      this.message = 'Site`s account is not matched with Asset Account,site accountsap='+this.siteaccountsapnmbr+',and asset sap is='+this.assetAcountSAPNUMBER;
      this._global.alert({type:'alert',message:this.message})
    }
    else {
      if(isprc=='isCase'){
 
      this.istrue = true;
      if(this.res.missingAssets!=null &&  this.res.missingAssets!=undefined && this.res.missingAssets!==""){
        this.req.assetsId = null;
      }else{
        this.req.assetsId=this.res.assetsId;
      }

      console.log("missing asset"+this.res.missingAssets);
      console.log("assets==="+this.res.assetsId);
      this.req.caseRecordTypeId = this.domainId;
      // this.req.id = this.res.caseId;

      this.req.caseOriginId = this.res.caseOriginId;
      this.req.casesubReason = this.res.casesubReason;
      this.req.casePriorityId = this.res.casePriorityId;
      this.req.caseReasonId = this.res.caseReasonId;
      this.req.caseStageId = this.res.caseStageId;
      this.req.caseStatusId = this.res.caseStatusId;
      this.req.caseTypeId = this.res.caseTypeId;
      this.req.contactPersonId = this.res.contactPersonId;
      this.req.noOfSmr = this.res.noOfSmr;
      this.req.remarks = this.res.remarks;
      this.req.caseProblemReported = this.problemOccured;
      this.req.serviceCenterId = this.res.serviceCenterId;
      this.req.serviceResourceId = this.res.serviceResourceId;
      this.req.missingAssets = this.res.missingAssets;
      this.req.RJILTT01No = this.res.RJILTT01No;
      this.req.otherProblemReported = this.res.otherProblemReported;
      this.req.siteId = this.res.siteId;
      this.req.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
      this.req.inputFromEng = this.res.inputFromEng;
      this.req.internalRemark = this.res.internalRemark;
      this.req.pendingResponsibility = this.res.pendingResponsibility;
      this.req.poDate=this.datePipe.transform(this.res.PODate, 'yyyy-MM-dd HH:mm:ss');
      this.req.poNumber=this.res.PONumber;
      this.req.typeChoice=this.res.assetsTypeChoiceId;
      this.req.poamount=this.res.poamount;
      this.req.lineitem=this.res.lineitem;
      this.req.btProblemMaster=this.res.problemMasterId;
      this.req.rma=this.res.rmaCreated;
      this.req.rol=this.res.isRolCreated;
      this.req.rolRemark=this.res.rolRemark;
      this.req.openDate=this.datePipe.transform(this.res.openDate, 'yyyy-MM-dd HH:mm:ss');
      // this.req.openDate=this.res.openDate;
      // let dateNew = new Date();
      // this.req.openDate=new Date(this.res.openDate);
      // console.log("this.req.openDate.getFullYear()",this.req.openDate.getFullYear());
      // console.log("this.req.openDate.getFullYear()",this.req.openDate.getMonth());
      // console.log("this.req.openDate.getFullYear()",this.req.openDate.getDate());
      

  //     let DateReq = new Date();
        
  //     DateReq = this.res.openDate;
  // let nowTime = new Date();
  // let myDateVis  = new Date(DateReq);
 
  
  // myDateVis.setHours(nowTime.getHours() + 5);
  // myDateVis.setMinutes(nowTime.getMinutes() + 30);
 

  // this.req.openDate =  this.datePipe.transform(myDateVis,'yyyy-MM-dd HH:mm:ss');
  
  
      console.log(this.res.ticketNo)
      this.req.ticketNo= this.res.ticketNo;

      if (!this._isEditMode) {
        this.spinner.show();
        console.log(this.req)
        this.caseservice.saveCase(this.req).subscribe(data => {
          this.onCaseCreated.emit(data);
          this.tampCaseId=data.caseId;
            this.spinner.hide();
            this.res = new BTCaseResponse();
            this.problemOccured = [];
       },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this._global.alert({type:'alert',message:this.message})


          }
        );
      } else {
        this.spinner.show();
        this.req.id=this.tampCaseId;

        // 
        

      //   this.req.openDate=this.res.openDate;
      // this.req.ticketNo= this.res.ticketNo;
        console.log("while editing")
        console.log(this.req)
        this.caseservice.updateCase(this.req,this.tampCaseId)
        .subscribe(data => {
          this.onCaseCreated.emit(data);;
          if (data != null) {
            this.spinner.hide();
            this.problemOccured = [];
          }
          // this._global.alert({type:'',message:this.message})

        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this._global.alert({type:'alert',message:this.message})

          }
        );

      }

    }
    else if(isprc=='isPRC'){
      this.CreatePRC();
    }
  }

  }
 
  cancelDatacase() {
    this.problemOccured = [];
    if (!this.isEditMode) {
      this.res = new BTCaseResponse();
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
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createSiteModel.hide();
  }

  addNewSite() {

    this.isSiteEditMode=false;
    this.heading = "create site";
    this.siteres = new GetAllBTsiteResp();
    this.createSiteModel.show();
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
    if($event!=null)
   { this.res.assetsSerialNumber = $event.serialNumber;
    this.astserialNumbr.partCode = $event.productPartCode;
    this.astserialNumbr.productDescription = $event.productDescription;}

    this.getAssetsBySerialNmber(this.res.assetsSerialNumber);
  }
  siteIdSelected(id) {
    console.log(id);
    if (id!= null) {
      this.res.contactPersonId = null;
      this.ContactOfAccount = [];
      this.getSiteById(id);
    }
    else{
      this.res.siteId=null;
      // this.res.
    }

  }
  getServiceResForList(serviceCenterId: number) {
    if (serviceCenterId != null) {
      this.spinner.show();
      this.siteservice.getServiceResourcesList(serviceCenterId).subscribe(data => {
        this.spinner.hide();
        this.serviceResourcesList = data;
        data.forEach(e=>{
          // if(this.res.serviceResourceId==null || this.res.serviceResourceId==undefined){
            // this.res.serviceResourceId=e.serviceResourceId;
            // this.res.serviceResourceName=e.name;
          
         
        })
      });
    }
  }
  expirydate: any;
  // assetDiscription: string
  assetsSerialNumberSelected($event) {
    if ($event != null) {
      this.res.assetsId=$event.id;
      // this.res.assetsSerialNumber = $event.serialNumber;
      this.res.assetDescription = $event.productDescription;
      if (this.res.assetsId!=undefined) {
        this.isAssetSerialnmbr = true;
      }
      this.getAssetsBySerialNmber(this.res.assetsId);

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
    // this.isEditMode=true;
  }
  onEntityCreated($event) {
    this.legalEntityList.legalEntityName = $event.legalEntityName;
    this.leaglEntityList.unshift(this.legalEntityList);
  }


  addNewServiceCenter() {
    this._isEditMode = false;
    this.heading = 'Create LegalEntity';
    this.createLegalEntityModel.show();
  }

  addNewAsset() {
    this.assests = new BTassetResp();
    this.isAssetEditMode = false;
    this.heading = 'Create Asset';
    this.createAssetModel.show();
  }

 
  // multiple array
   moveRight(selectedReason) {
    for(let i=0;i<selectedReason.length;i++){
      
    if (selectedReason!== undefined) {  
      const index = this.AllProblemList.indexOf(selectedReason[i]);
      this.problemOccured.push(selectedReason[i]);
      this.AllProblemList.splice(index,selectedReason.length);
      
      this.problem = new ProblemReported();
    } else {
      this.message = "Please select any 1 problem";
      this._global.alert({type:'alert',message:this.message})
    }
  }
  }
  moveLeft(unselectReason) {
    for(let i=0;i<unselectReason.length;i++){
    if (unselectReason!== undefined) {
      const index = this.problemOccured.indexOf(unselectReason[i]);
      this.AllProblemList.push(unselectReason[i]);
      this.problemOccured.splice(index,1);
      this.problem = new ProblemReported();
    } else {
      this.message = "Please select any 1 problem";
      this._global.alert({type:'alert',message:this.message})
     

    }
  }
  }
  // 

  

  // 
  addNewContact() {
    // this.newcontact=this.res
    this.isContactEditMode=false;
    this.newcontact = new Contacts();
    this.newcontact.accountName = this.res.accountName;
    this.newcontact.accountSAPNumber = this.res.accountSapNumber;

    this.heading = 'Create Contact';
    this.createContactModel.show();
  }
  onSiteCreated($event) {
    this.res.siteId = $event.siteId;
    this.res.siteManualId = $event.siteManualId;
    this.res.siteName=$event.siteName;
    this.res.accountName = $event.accountName;
    this.res.accountSapNumber = $event.accountSAPNumber;
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
        }
        this.assetSerialnoCase = data.serialNumber;
        this.res.siteManualId = data.siteManualId;
        
        // this.siteList.push({siteId:data.siteId,siteManualId:data.siteManualId,siteName:data.siteName});
        this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
        // this.siteList.push({batterySiteId:data.siteId,manuaSitelId:data.siteManualId,siteName:data.siteName});
      //  this.updateSiteList();
        this.expirydate = data.warrantyExpiryDate;
        console.log(this.assests.accountSapNumber);
       this.assetAcountSAPNUMBER=this.assests.accountSapNumber;
        if (data.siteId !== 0) {
          this.getSiteById(data.siteId);
        }

        this.expirydate = this.datePipe.transform(this.expirydate, 'yyyy-MM-dd');
        if (data.isWarrantyExist==1 || data.isAMCWarrantyExist ==1) {
          this.indate = true;
          this.outdate = false;

          if (this.domainId === 1) {
            this.res.caseTypeId = 3;
          } else if (this.domainId === 2) {
            this.res.caseTypeId = 7;
          }
        } else {
          this.outdate = true;
          this.indate = false;

          if (this.domainId === 1) {
            this.res.caseTypeId = 4;
          } else if (this.domainId === 2) {
            this.res.caseTypeId = 8;
          }
        }
        this.getCaseReason(this.res.caseTypeId);
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
    // this.caseservice.getContactList(sapNumber).subscribe(data => {
      this.caseservice.getContactList(sapNumber).subscribe(data =>{
      this.spinner.hide();
      this.ContactOfAccount = [];
      data.forEach((ct) => {
        let _ct = new ContactForList();
        // this.res.contactPersonId=ct.contactid;
        _ct.firstName = ct.contactFirstName;
        _ct.lastName = ct.contactLastName;
        _ct.middleName = ct.contactMiddleName;
        _ct.fullName = ct.contactFirstName + ' ' + ct.contactMiddleName + ' ' + ct.contactLastName+''+ct.contactMobileNumber;
        _ct.phoneNumber = ct.contactPhoneNumber;
        _ct.contactId = ct.contactid;
        _ct.phoneNumber=ct.contactMobileNumber;

        this.ContactOfAccount.push(_ct);
                // working hrere24_jan
        // this.ContactOfAccount.push({contactId:this.res.contactPersonId,firstName:this.res.contactPersonFirstName,lastName:this.res.contactPersonLastName,phoneNumber:""});
     

      }, (error => {
        this.spinner.hide();
        this.message = error.error.essage;
        this._global.alert({type:'alert',message:this.message})

      })

      );
    });
  }
  getsmallcontactonKey(key){
    if(key.target.value!=="" && key.target.value!==" "){
    this.ContactOfAccount=new Array<BtContactForlist>();
    this.caseservice.getContactSmall(this.siteaccountsapnmbr,key.target.value).subscribe(data=>{
      this.ContactOfAccount = new Array<BtContactForlist>();
      let temp=[]
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
    },(error => {
      this.spinner.hide();
      this.message = error.error.essage;
      this._global.alert({type:'alert',message:this.message})

    }))
  }
  }
  public getAssetsListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getAssetsListByAsscountSapNumber(sapNumber).subscribe(asst => {
      // this.assetList = asst;
    });
  }
  public getSitesListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getSitesList(sapNumber).subscribe(st => {
      this.siteList = [];
      // st.forEach((s) => {
      //   let _st = new SiteDetailsForList();
      //   _st.siteId = s.siteId;
      //   _st.siteManualId = s.siteManualId;
      //   _st.siteName = s.siteName;
      //   this.siteList.push(_st);
      // });
      this.updateSiteList();
    });
  }



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
              // temp.push({batterySiteId:this.siteList[i].batterySiteId,siteManualId:this.siteList[i].manuaSitelId,siteName:this.siteList[i].siteName});
             
              temp.push({batterySiteId:this.siteList[i].batterySiteId,manuaSitelId:this.siteList[i].manuaSitelId,siteName:this.siteList[i].siteName});
  
              sfound.push(this.siteList[i].batterySiteId);
            }
            
          }
        }
        this.siteList=temp;
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
      // update contact

      
      updateCOntactList(){
        let temp=[];
        const sfound=[];
        for(let i=0;i<this.ContactOfAccount.length;i++){
          let found=false;
          for(let j=0;j<sfound.length;j++){
            if(sfound[j]==this.ContactOfAccount[i].contactId){
              found=true;
              break;
            }
          }
          if(!found){
            if(this.ContactOfAccount[i].contactId!=null&&this.ContactOfAccount[i].contactId!=undefined&& this.ContactOfAccount[i].contactId!=0){

             if(this.ContactOfAccount[i].middleName!=null){
              this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonMiddleName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
             }else{
               this.contactfullname=this.res.contactPersonFirstName+' '+this.res.contactPersonLastName+' '+this.res.contactPersonContactNo;
             }
            temp.push({contactId:this.res.contactPersonId,emailId:this.res.contactPersonEmailId,firstName:this.res.contactPersonFirstName,middleName:this.res.contactPersonMiddleName,lastName:this.res.contactPersonLastName,phoneNumber:this.res.contactPersonContactNo,fullName:this.contactfullname})
              sfound.push(this.ContactOfAccount[i].contactId);
            }
            
          }
        }
        this.ContactOfAccount=temp;
      }
      
      // Create PRC
  
      CreatePRC(){
        this.prcrew.caseRecordTypeId = this.domainId;
        // this.req.caseId = this.res.caseId;
        this.prcrew.assetsId = this.res.assetsId;
        // this.prcrew.assetsSerialNumber = this.res.assetsSerialNumber;
        this.prcrew.caseOriginId = this.res.caseOriginId;
        this.prcrew.casePriorityId = this.res.casePriorityId;
        this.prcrew.caseReasonId = this.res.caseReasonId;
        this.prcrew.caseStageId = this.res.caseStageId;
        this.prcrew.caseStatusId = this.res.caseStatusId;
        this.prcrew.caseTypeId = this.res.caseTypeId;
        this.prcrew.contactPersonId = this.res.contactPersonId;
        this.prcrew.noOfSmr = this.res.noOfSmr;
        this.prcrew.remarks = this.res.remarks;
        this.prcrew.caseProblemReported = this.problemOccured;
        this.prcrew.serviceCenterId = this.res.serviceCenterId;
        this.prcrew.serviceResourceId = this.res.serviceResourceId;
        this.prcrew.missingAssets = this.res.missingAssets;
        this.prcrew.RJILTT01No = this.res.RJILTT01No;
        this.prcrew.otherProblemReported = this.res.otherProblemReported;
        this.prcrew.siteId = this.res.siteId;
        this.prcrew.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
        this.prcrew.inputFromEng = this.res.inputFromEng;
        this.prcrew.internalRemark = this.res.internalRemark;
        this.prcrew.pendingResponsibility = this.res.pendingResponsibility;
        // this.prcrew.poDate=this.res.PODate;
        if(this.res.assetsTypeChoiceId!==0 || this.res.assetsTypeChoiceId==undefined)
 {       this.prcrew.typeChoice=this.res.assetsTypeChoiceId;}
        // this.req.poNumber=this.res.ponumber;
        this.prcrew.openDate=this.res.openDate
        this.prcrew.ticketNo=this.res.ticketNo;
        this.prcrew.poDate=this.res.PODate;
        this.prcrew.poNumber=this.res.PONumber;
        this.prcrew.poamount=this.res.poamount;
        this.prcrew.limeitem=this.res.lineitem;
        this.prcrew.btProblemMaster=this.res.problemMasterId;
        this.spinner.show();
        this.preregcaseservice.saveCase(this.prcrew).subscribe(data => {
       
          this.message = 'Created Successfully';
          
          if (data != null) {
            this.spinner.hide();
            this.problemOccured = [];
          }
          this._global.alert({type:'',message:this.message});

        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this._global.alert({type:'alert',message:this.message})


          }
        );
      }
getFile:any;
filyType:string;
fileToUpload:File;
fileList: any[] = [];
formData = new FormData();
      onFileSelect(event) {
        // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
        if(event!=null){
          
        this.getFile = event.target.files.id;
        this.filyType = event.target.id;
        // this.podgetFile=event.target.files.id;
      
        if (event.target.files.length > 0) {
          // this.chooscaseFiles.hide();
          this.choosenFiless.show();
          for (let i = 0; i < event.target.files.length; i++) {
            this.fileList.push(event.target.files[i]);
            this.fileToUpload = event.target.files[i];
            // console.log(this.fileToUpload);
    
          }
        }
        }
        // else{
        //   console.log(event)
        // }
       
      }
     
      onSubmit() {
        
        this.spinner.show();
        for (let i = 0; i < this.fileList.length; i++) {
          this.formData.append('file', this.fileList[i]);
          // this.fileToUpload = event.target.fileList[i];
    
        }
    
        if(this.filyType=="select"){
          this.caseservice.uploadFilecase(this.tampCaseId,this.fileToUpload).subscribe(data=>{
           this.fileList = [];
           this.spinner.hide();
            (<HTMLInputElement>document.getElementById('select')).value=null
                // this.spinner.hide();
                this.message = 'File Uploaded Successfully';
                this.choosenFiless.hide();
                this._global.alert({ type: '', message: this.message });
              }, error => {
                this.fileList = [];
                this.spinner.hide();
                // this.popupNgClass = 'alert alert-danger center center-block';
                this.message = error.message;
                this._global.alert({ type: 'alert', message: this.message });
              });
            this.formData = new FormData();
        }
      }
      cancelChoosen() {
     
    
      if(this.filyType=='select'){
        (<HTMLInputElement>document.getElementById('select')).value=null
      }
        this.choosenFiless.hide();
        this.formData = new FormData();
        this.fileList = [];
        this.getFile = "";
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
         this.res.typeChoice='';
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial='form-group required'
        }
        

      }
      rmaChange(required){
        if(required)
       { 
         this.isrolORisrma=true;
         this.res.rolCreated=false;
         this.res.isRolCreated=false;
         this.res.caseStatusId=2;

         this.res.rolRemark="";
        //  this.isrolORisrma=false;

        this.res.caseStatusName="Close";
        this.res.pendingResponsibility=2;
        this.getstage(this.res.caseStatusId);
      }
        else{
          this.isrolORisrma=false;
          this.res.caseStatusId=this.caseStatusidEdit;
          this.res.caseStatusName=this.caseStatusnameEdit;
          this.res.pendingResponsibility=this.pendingresEdit;
          this.getstage(this.res.caseStatusId);
        }
      }
      isrolORisrma:boolean=false;
      rolChange(required){
        if(required)
       {  
        //  this.notforsch=false;
         this.isrolORisrma=true;
         this.res.caseStatusId=2;
        this.res.caseStatusName="Close";
        this.res.pendingResponsibility=2;
        this.getstage(this.res.caseStatusId);
      }
        else{
          this.res.rolRemark="";
          // this.notforsch=true;
          this.isrolORisrma=false;
          this.res.rolCreated=false;
          this.res.caseStatusId=this.caseStatusidEdit;
          this.res.caseStatusName=this.caseStatusnameEdit;
          this.res.pendingResponsibility=this.pendingresEdit;
          this.getstage(this.res.caseStatusId);
        }
      }
  
      onStagechange(stageid){
        this.res.caseStageId=stageid;
      }
}
