import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { Loginservices } from '../../services/login.services';
import { siteService } from '../../services/siteService.services';
import { ActivatedRoute } from '@angular/router';
import { caseService } from '../../services/caseService.service';
import { ligalEntityService } from '../../services/ligalentity.services';
import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatePipe } from '@angular/common';
import { ContactsService } from '../../services/contacts.services';
import { CaseResponse, ContactForList, ServiceResourseForList, Contacts, ServiceCenterForList, AssetsForList, AccountsForList, SiteDetailsForList, CaseStageDetails, CasePriority, CaseStatus, PendingResponsibility, CaseOriginDetails, ProblemReported, selected, SiteDetailsResponse, AssetsResponse, CaseReasonForList, CaseTypeRecord, CaseTypeForList, CaseRequest, PreRegCaseResponse, PreRegCaseRequest } from '../../services/model';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { AssetsService } from '../../services/assets.services';

@Component({
  selector: 'app-case-create-pre-regis',
  templateUrl: './case-create-pre-regis.component.html',
  styleUrls: ['./case-create-pre-regis.component.scss']
})
export class CaseCreatePreRegisComponent implements OnInit {
  @ViewChild('createSiteModel') createSiteModel: ModalDirective;
  @ViewChild('createLegalEntityModel') createLegalEntityModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('createContactModel') createContactModel: ModalDirective;

  @Output()
  onCaseCreated: EventEmitter<PreRegCaseRequest> = new EventEmitter<PreRegCaseRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  domainId: number;
  NewBtn: boolean = false;
  assetResponse: AssetsResponse = new AssetsResponse()
  res: PreRegCaseResponse = new PreRegCaseResponse();
  req: PreRegCaseRequest = new PreRegCaseRequest();
  caseTypeList: Array<CaseTypeForList> = [];
  casetypRecord: Array<CaseTypeRecord> = [];
  caseReason: Array<CaseReasonForList> = [];
  leaglEntityList: Array<ServiceCenterForList> = [];
  caseReasonList: CaseReasonForList;
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
  ContactOfAccount: Array<ContactForList> = [];
  contactByAccount: Contacts = new Contacts();
  assests: AssetsResponse = new AssetsResponse();
  siteres: SiteDetailsResponse = new SiteDetailsResponse();
  assetList: Array<AssetsForList>=[];
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
  origin: Array<CaseOriginDetails> = [];
  caseStatusName: any;
  status: Array<CaseStatus> = [];
  contactId: number;
  priority: Array<CasePriority>;
  stage: Array<CaseStageDetails>;
  siteList: Array<SiteDetailsForList>=[];
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
  indate: boolean = false;
  outdate: boolean = false;
  assetSerialnoCase: string;
  assetNot: boolean = true;
  serviceResourcesList: Array<ServiceResourseForList> = [];
  notBatryDCT: boolean = false;
  popupNgClass: string = 'alert alert-danger center center-block';
  tampCaseId: number;
  tempCaseName: string;
  fullName: string;
  ngClassMissingAsset = 'form-group';
  ngClassAssetSerial = 'form-group'
  colAsset = 'col-md-10 col-sm-10'
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  installationTrue:boolean=false;
  assetres:AssetsResponse=new AssetsResponse();
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
    this.siteservice.getDetailBysiteId(this.res.siteId).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.siteList.push({siteId:data.siteId,siteManualId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;
        this.res.accountSapNumber = data.accountSapNumber;
        this.res.accountName = data.accountName;
        this.getServiceResForList(this.res.serviceCenterId);
         this.res.serviceResourceId = data.serviceResourceId;
        // this.getContactByAccount(data.accountSapNumber);
        // contact by site`s account
        this.accountservice.getContactList(data.accountSapNumber).subscribe(data => {
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
            this.popupSuccessModel.show();})
          );
        }); 
      }
      })
      // 
      if (this.domainId === 1) {
        this.res.caseTypeId = 2;
        this.getCaseReason(this.res.caseTypeId);
        this.res.caseReasonId = 8;
        this.ngClassMissingAsset = 'form-group required';
      }
      else {
        this.res.caseTypeId = 6;
        this.getCaseReason(this.res.caseTypeId);
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
  set editEntityData(res: PreRegCaseResponse) {
    if (res!=null) {
    this.res=res;
    this.siteList=[]
    if(res.assetsSerialNumber!=null&&res.assetsSerialNumber!=undefined&&res.assetsSerialNumber!=''){
       this.assetList.push({assetsSerialNumber:res.assetsSerialNumber,partCode:res.assetsPartCode,productDescription:res.assetDescription});
    }
    if(res.siteId!=null&&res.siteId!=undefined){
      
      this.siteList.push({siteId:res.siteId,siteManualId:res.siteManualId,siteName:res.siteName});
      this.updateSiteList();
    }
    this.loginData = this.loginservice.getLoggedInUser();
    if(this.res.caseTypeId==2 || this.res.caseTypeId==6){
      this.installationTrue=true;
    }else{
      this.installationTrue=false;
    }
    this.caseservice.getCaseAssets(this.res.assetsSerialNumber).subscribe(data=>{
      if(data!==null){
        if (data.amcwarrantyExist || data.warrantyExist) {
          this.indate = true;
          this.outdate = false;
  
          if (this.domainId === 1) {
            this.res.caseTypeId = 3;
            this.getCaseReason(this.res.caseTypeId);
            
          } else if (this.domainId === 2) {
            this.res.caseTypeId = 7;
            this.getCaseReason(this.res.caseTypeId);
          }
        } 
        else {
          this.outdate = true;
          this.indate = false;
  
          if (this.domainId === 1) {
            this.res.caseTypeId = 4;
            this.getCaseReason(this.res.caseTypeId);
            this.res.caseReasonId=5;
          } else if (this.domainId === 2) {
            this.res.caseTypeId = 8;
            this.getCaseReason(this.res.caseTypeId);
            this.res.caseReasonId=17;
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
       
         this.siteservice.getDetailBysiteId(res.siteId).subscribe(data => {
           this.spinner.hide();
           if (data != null) {
             this.siteres = data;
            //  this.res.serviceCenterId = data.serviceCenterId;
             this.res.accountSapNumber = data.accountSapNumber;
             this.res.accountName = data.accountName;
            //  this.res.serviceResourceId = data.serviceResourceId;
             this.spinner.show();
             this.accountservice.getContactList(data.accountSapNumber).subscribe(data => {
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
                 this.popupSuccessModel.show();})
               );
             }); 
            }
         });
        this.res.isAssignByAssignmentRule = true;
        this.problemOccured = this.res.problemReported;
        this.spinner.show();
        this.caseservice.getProblemReported(this.domainId).subscribe(data => {
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
        this.spinner.show();
        this.caseservice.getCaseReason(this.res.caseTypeId).subscribe((data) => {
          this.spinner.hide();
          this.caseReason = data;
        });


        this.caseservice.getCaseStatus().subscribe(data => {
            this.status = data;  });
        if (this.res.caseStatusId !== null && this.domainId !== null) {
          this.caseservice.getCaseStage(this.res.caseStatusId, this.domainId).subscribe(data => {
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
     
        if (this.res.accountSapNumber != null && this.res.accountSapNumber.length > 0) {
          this.getAssetsListByAccountSapNumber(this.res.accountSapNumber);
          // this.getSitesListByAccountSapNumber(this.res.accountSapNumber);
        }
      }
      this.spinner.hide();
    }
    else {
           this.res = new PreRegCaseResponse();
       }
  }

  get editEntityData(): PreRegCaseResponse {
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
  set AssetSerial(ress:PreRegCaseResponse){
    this.res=ress;
    // this.res.siteManualId=ress.siteManualId;
    // this.res.siteName=ress.siteName;
    // this.res.siteId=ress.siteId;
    // this.siteList.push({siteId:ress.siteId,siteManualId:ress.siteManualId,siteName:ress.siteName});
    // this.updateSiteList();
    // this.getContactByAccount(ress.accountSapNumber);  this.spinner.show();
         this.siteservice.getDetailBysiteId(ress.siteId).subscribe(data => {
          this.spinner.hide();
          if (data != null) {
            this.siteres = data;
            this.res.serviceCenterId = data.serviceCenterId;
            this.res.accountSapNumber = data.accountSapNumber;
            this.res.accountName = data.accountName;
            this.getServiceResForList(this.res.serviceCenterId);
            this.res.serviceResourceId = data.serviceResourceId;
            this.spinner.show();
            this.accountservice.getContactList(data.accountSapNumber).subscribe(data => {
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
                this.popupSuccessModel.show();})
              );
            }); 
           }
        });


  }
  get AssetSerial():PreRegCaseResponse{
   return this.res;
  }


  openModlues = {siteModuleOpen: false, legalEntityModuleOpen: false, assetsModuleOpen: false, contactModuleOpen: false }
  constructor(private _assetsService:AssetsService,private preregcaseservice:PreRegCaseService,private siteservice: siteService, private loginservice: Loginservices, private _activatedRoute: ActivatedRoute, private caseservice: caseService,
     private legalEntityService: ligalEntityService, private accountservice: AccountService, private spinner: NgxSpinnerService, private datePipe: DatePipe, private contatactService: ContactsService) {
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

    // this.getSite();
    this.getCaseStatus();
    this.getcaseType();
    this.LegalENtityList();
    this.ProblemReport();
    this.Risponsibility();
 
   

    //  this.getContactsData();
    if (!this.isEditMode && this.res.siteId==null) {
      if (this.domainId === 1) {
        this.notBatryDCT = true;
        this.res.caseTypeId = 3;
        this.getCaseReason(this.res.caseTypeId);
        this.res.caseReasonId = 1;
      }
      else {
        this.notBatryDCT = false;
        this.res.caseTypeId = 7;
        this.getCaseReason(this.res.caseTypeId);
        this.res.caseReasonId = 11;
      }
    }

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
    // this.getCaseReason(this.res.caseTypeId);


  }
    getCaseReason(caseTypeId) {
    this.spinner.show();
    this.caseservice.getCaseReason(caseTypeId).subscribe((data) => {
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
          this.ngClassAssetSerial = 'form-group required';
          this.res.caseReasonId = 11;
          return;
        }
        if (this.res.caseTypeId === 8) {
          this.ngClassAssetSerial = 'form-group required';
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 17;

        }
        if(this.res.caseTypeId===9){
          this.ngClassAssetSerial = 'form-group required';
          this.ngClassMissingAsset = 'form-group';
          this.res.caseReasonId = 30;
        }

      } else if (this.domainId === 1) {
        if (this.res.caseTypeId === 1) {
          this.ngClassMissingAsset = 'form-group required';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 2;
          return;

        }
        if (this.res.caseTypeId === 2) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group';
          this.res.caseReasonId = 8;
          return;
        }
        if (this.res.caseTypeId === 3) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group required';
          this.res.caseReasonId = 1;
          return;
        }
        if (this.res.caseTypeId === 4) {
          this.ngClassMissingAsset = 'form-group';
          this.ngClassAssetSerial = 'form-group required';
          this.res.caseReasonId = 5;
          return;
        }
      }
    }, error => {
      this.spinner.hide();
    });
  }
  ProblemReport() {
    this.spinner.show();
    this.caseservice.getProblemReported(this.domainId).subscribe(data => {
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
      this.res.caseStatusId = 3;
    }
  }
  getCaseStatus() {
    this.caseservice.getCaseStatus().subscribe(data => {
      this.status = data;
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
      this.caseservice.getCaseStage(id, this.domainId).subscribe(data => {
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
      this.siteList = data;

    });
  }
  getSiteById(siteid) {
    this.spinner.show()
    this.siteservice.getDetailBysiteId(siteid).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.siteres = data;
        this.res.serviceCenterId = data.serviceCenterId;
        this.res.accountSapNumber = data.accountSapNumber;
        this.res.accountName = data.accountName;
        this.siteList.push({siteId:data.siteId,siteManualId:data.siteManualId,siteName:data.siteName});
        this.updateSiteList();
        this.getServiceResForList(this.res.serviceCenterId);
       this.res.serviceResourceId = data.serviceResourceId;
        if (this.res.assetsSerialNumber == null || this.res.assetsSerialNumber === undefined || this.res.assetsSerialNumber.length === 0) {
          if (this.domainId === 1) {
            this.res.caseTypeId = 1;
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
        this.getContactByAccount(data.accountSapNumber);
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
      this.assets=data.content;
      this.assetList=data.content;
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
          this.popupSuccessModel.show();
          setTimeout(() => {
            this.popupSuccessModel.hide();
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

    if (this.res.caseTypeId === 0 || this.res.caseTypeId === null) {
      this.message = 'Select Case Type';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if ((this.res.assetsSerialNumber === undefined || this.res.assetsSerialNumber === null) && (this.res.caseTypeId !== 1 && this.res.caseTypeId !== 5) && (this.res.caseTypeId !== 2 && this.res.caseTypeId !== 6)) {
      this.message = 'Select Assets Serial Number';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if ((this.res.assetsSerialNumber === undefined || this.res.assetsSerialNumber === null || this.res.assetsSerialNumber === '') && (this.res.missingAssets === undefined || this.res.missingAssets === null || this.res.missingAssets === '') && (this.res.caseTypeId === 1 || this.res.caseTypeId === 5)) {
      this.message = 'Fill The Missing Asset Serial Number';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if(this.installationTrue==true && (this.res.ponumber==undefined || this.res.ponumber==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
      this.message = 'Fill PO Number';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }

    else if(this.installationTrue==true && (this.res.podate==undefined || this.res.podate==null) && (this.res.caseTypeId==2 || this.res.caseTypeId==6)){
      this.message = 'Fill PO Date';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.res.caseReasonId === 0 || this.res.caseReasonId === null) {
      this.message = 'Select Any Case Reason';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    } else if (this.res.contactPersonId === null || this.res.contactPersonId === 0) {
      this.message = 'Select Any Contact Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    } else if (this.res.caseStatusId === 0) {
      this.message = 'Select Any Status';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    } else if (this.res.caseStageId === 0) {
      this.message = 'Select Any Stage';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    } else if (this.res.caseOriginId === 0 || this.res.caseOriginId === null) {
      this.message = 'select any case Origin Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    } else if (this.res.casePriorityId === 0 || this.res.casePriorityId === null) {
      this.message = 'Select Any Priority';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if (this.res.internalRemark != undefined && this.res.internalRemark !== undefined && (this.res.internalRemark).length >= 1000) {
      this.message = 'Internal Comments Exceeds 1000 Character';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if (this.res.otherProblemReported !== null && this.res.otherProblemReported !== undefined && (this.res.otherProblemReported).length > 1000) {
      this.message = ' Other Problems Exceeds 1000 Character';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if (this.res.inputFromEng !== null && this.res.inputFromEng !== undefined && (this.res.inputFromEng).length > 1000) {
      this.message = ' Input From Engineer Exceeds 1000 Character';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
    else if (this.res.remarks !== null && this.res.remarks !== undefined && (this.res.remarks).length > 1000) {
      this.message = ' Remark Exceeds 1000 Character';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }

    else if (this.res.noOfSmr!==null && this.res.noOfSmr!==undefined && (this.res.noOfSmr).length > 4) {
      this.message = 'Number of SMR Exceeds Only Numbers With 2 Character';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();

    }
  
    // else if(this.res.noOfSmr!=null && ){

    // }

    else {
      this.istrue = true;
      if (!this._isEditMode) {
        this.req.caseRecordTypeId = this.domainId;
        this.req.caseId = this.res.prcCaseId;
        this.req.assetsSerialNumber = this.res.assetsSerialNumber;
        this.req.caseOriginId = this.res.caseOriginId;
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
        this.req.rjiltt01No = this.res.rjil_TT01_No;
        this.req.otherProblemReported = this.res.otherProblemReported;
        this.req.siteId = this.res.siteId;
        this.req.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
        this.req.inputFromEng = this.res.inputFromEng;
        this.req.internalRemark = this.res.internalRemark;
        this.req.pendingResponsibility = this.res.pendingResponsibilityId;
        this.req.poDate=this.res.podate;
        this.req.poNumber=this.res.ponumber;
        this.req.iSmsSend=this.res.iSmsSend;
        this.spinner.show();
        this.preregcaseservice.saveCase(this.req).subscribe(data => {
          this.tempCaseName = data.caseManualId;
          this.tampCaseId = data.caseId;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onCaseCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
            this.res = new PreRegCaseResponse();
            this.problemOccured = [];
          }
          this.popupSuccessModel.show();

        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this.popupSuccessModel.show();


          }
        );
      } else {
        this.req.caseRecordTypeId = this.domainId;
        this.req.caseId = this.res.prcCaseId;
        this.req.assetsSerialNumber = this.res.assetsSerialNumber;
        this.req.caseOriginId = this.res.caseOriginId;
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
        this.req.rjiltt01No = this.res.rjil_TT01_No;
        this.req.siteId = this.res.siteId;
        this.req.isAssignByAssignmentRule = this.res.isAssignByAssignmentRule;
        this.req.inputFromEng = this.res.inputFromEng;
        this.req.internalRemark = this.res.internalRemark;
        this.req.caseProblemReported = this.problemOccured;
        this.req.pendingResponsibility = this.res.pendingResponsibilityId;
        this.req.poDate=this.res.podate;
        this.req.poNumber=this.res.ponumber;
        this.req.iSmsSend=this.res.iSmsSend;
        this.spinner.show();
        this.preregcaseservice.updateCase(this.req).subscribe(data => {
          this.onCaseCreated.emit(data);
          this.tempCaseName = data.caseManualId;
          this.tampCaseId = data.caseId;
          // this.res.caseId = data.caseId;
          this.message = 'Updated Successfully'
          this.popupNgClass = 'alert alert-success center center-block';
          if (data != null) {
            this.spinner.hide();
            this.problemOccured = [];
          }
          this.popupSuccessModel.show();

        },
          (error) => {
            this.spinner.hide();
            this.message = error.error.message;
            this.popupSuccessModel.show();

          }
        );

      }

    }
    // if (!this.istrue) {
    //   this.popupSuccessModel.show();

    // }


  }
  cancelSuccessPOpup() {
    this.popupSuccessModel.hide();
    this.onCancle.emit('cancled');

  }
  cancelDatacase() {
    this.problemOccured = [];
    if (!this.isEditMode) {
      this.res = new PreRegCaseResponse();
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
    this.siteres = new SiteDetailsResponse()
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

  onAssetCreated($event: AssetsResponse) {
    // this.getAssestList();
    this.res.assetsSerialNumber = $event.serialNumber;
    this.astserialNumbr.partCode = $event.productPartCode;
    this.astserialNumbr.productDescription = $event.productDescription;

    this.getAssetsBySerialNmber($event.serialNumber);
  }
  siteIdSelected($event) {
    if ($event != null) {
      this.res.contactPersonId = null;
      this.ContactOfAccount = [];
      this.getSiteById($event.siteId);
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
      this.res.assetsSerialNumber = $event.assetsSerialNumber;
      this.res.assetDescription = $event.productDescription;
      if (this.res.assetsSerialNumber.length > 0) {
        this.isAssetSerialnmbr = true;
      }
      this.spinner.show();
      this.getAssetsBySerialNmber(this.res.assetsSerialNumber);

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
    this.assests = new AssetsResponse();
    this.isEditMode = false;
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
      this.popupSuccessModel.show()
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
      this.message = "Please select any 1 problem";
      this.popupSuccessModel.show()
     

    }
  }
  }
  // 

  

  // 
  addNewContact() {
    // this.newcontact=this.res
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
    this.caseservice.getCaseAssets(assetsSerialNumber).subscribe(data => {
      this.spinner.hide();
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
        this.res.siteManualId = data.siteManualId
        this.siteList.push({siteId:data.siteId,siteManualId:data.siteManualId,siteName:data.siteName});
       
        this.expirydate = data.warrantyExpiryDate;
        if (data.siteId !== 0) {
          this.getSiteById(data.siteId);
        }

        this.expirydate = this.datePipe.transform(this.expirydate, 'yyyy-MM-dd');
        if (data.amcwarrantyExist || data.warrantyExist) {
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

    }, (error) => {
      this.spinner.hide();
      this.res.assetsSerialNumber = null;
      this.message = error.error.message;
      this.popupSuccessModel.show();


    });
  }
  getContactByAccount(sapNumber: string) {
    this.spinner.show();
    this.accountservice.getContactList(sapNumber).subscribe(data => {
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
        this.popupSuccessModel.show();

      })

      );
    });
  }
  public getAssetsListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getAssetsListByAsscountSapNumber(sapNumber).subscribe(asst => {
      this.assetList = asst;
    });
  }
  public getSitesListByAccountSapNumber(sapNumber: string) {
    this.accountservice.getSitesList(sapNumber).subscribe(st => {
      this.siteList = [];
      st.forEach((s) => {
        let _st = new SiteDetailsForList();
        _st.siteId = s.siteId;
        _st.siteManualId = s.siteManualId;
        _st.siteName = s.siteName;
        this.siteList.push(_st);
      });
      this.updateSiteList();

    });
  }


  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
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
            if(sfound[j]==this.siteList[i].siteId){
              found=true;
              break;
            }
          }
          if(!found){
            if(this.siteList[i].siteManualId!=null&&this.siteList[i].siteManualId!=undefined&&this.siteList[i].siteManualId!=''){
              temp.push({siteId:this.siteList[i].siteId,siteManualId:this.siteList[i].siteManualId,siteName:this.siteList[i].siteName});
              sfound.push(this.siteList[i].siteId);
            }
            
          }
        }
        this.siteList=temp;
      }
}
