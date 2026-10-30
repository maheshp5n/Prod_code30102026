import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { caseService } from '../../../services/caseService.service';
import { ContactsService } from '../../../services/contacts.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, ActionHistoryObjectAndId, CaseResponse, Contacts, SiteDetailsResponse } from '../../../services/model';
import { siteService } from '../../../services/siteService.services';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';

@Component({
  selector: 'app-bat-accountsdetail',
  templateUrl: './bat-accountsdetail.component.html',
  styleUrls: ['./bat-accountsdetail.component.css']
})
export class BatAccountsdetailComponent implements OnInit {
  @ViewChild('createContactModel', { static: false }) public createContactModel: ModalDirective;
  @ViewChild('createSitesModel', { static: false }) public createSitesModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createAcountModel', { static: false }) createAcountModel: ModalDirective;
  @ViewChild('createCaseModel', { static: false }) createCaseModel: ModalDirective;

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;

  account: AccountCreate = new AccountCreate();
  accountList: Array<AccountCreate> = [];
  createAccount: AccountCreate = new AccountCreate();

  contactsList: Array<Contacts> = [];
  sitesList: Array<SiteDetailsResponse> = [];
  casesList: Array<CaseResponse> = [];

  caseRes: CaseResponse = new CaseResponse();
  res: SiteDetailsResponse = new SiteDetailsResponse();
  newcontact: Contacts = new Contacts();

  columns =  ["S.No", "Name","Department","Email","Phone Number","Contact Numbe"] ;
  contactColumns:any=[];
  siteCoulms=["S.No","Site ID","Cluster/District","Exchange/Site Name","Infra ID"];
  sitecolumn:any=[];
  caseColumn= ["S.No", "Name","Department","Email","Phone Number","Contact Numbe"] ;
  caseColumns:any=[];
  caseColumnAcc=["S.No","Case Number","Type","Case Reason","Status"]


  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;

  casePage: number = 0;
  caseFirst: boolean;
  caseLast: boolean;
  caseSize: number;
  totalCases: number
  caseSerial: number;

  sitePage: number = 0;
  siteFirst: boolean;
  siteLast: boolean;
  siteSize: number;
  totalSites: number;
  siteSerial: number

  contactPage: number = 0;
  contactFirst: boolean;
  contactLast: boolean;
  contactSize: number;
  totalContacts: number;
  contactSerial: number;

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';


  CREATE_ACCOUNT_HEADING: string = 'New Account';
  EDIT_ACCOUNT_HEADING: string = 'Edit Account';

  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';

  CREATE_SITE_HEADING: string = 'Create Site ';
  EDIT_SITE_HEADING: string = 'Edit Site ';

  CREATE_CASE_HEADING: string = 'Create Case ';
  EDIT_CASE_HEADING: string = 'Edit Case ';

  sapId: string = '';
  isBillingStreet: boolean = true;
  popupNgClass: string = 'alert alert-danger center center-block';

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  addressDisplay1 = {
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
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }

  isEditModelShow: boolean = false;
  openModules = { createAccountOpen: false, contactCreate: false, createSite: false, createCase: false }
  _cFlag: string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private loginService: Loginservices,private btcontactService:BtcontactsService, private _contactService: ContactsService, private siteservice: siteService,
    private case_service: caseService,private _btAccountService :BtaccountService,private _global:GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getAccounts(this.sapId);
      this.actionHistoryObjectAndId.objectName = 'Accounts';
      this.actionHistoryObjectAndId.objectId = this.sapId;
  
      this.contactColumns = this.columns;
       this.sitecolumn= this.siteCoulms;
        this.caseColumn= this.caseColumn;
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }


  ngOnInit() {
  }
  rights(object:string,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }
  getAccounts(sapId: string) {
    this.spinner.show();
    this._btAccountService.getBtAccount(sapId).subscribe(data => {
      if (data !== null) {
        this.spinner.hide();
      }
      this.account = data;
      if (this.account.billingStreet == null || this.account.billingStreet == '') {
        this.isBillingStreet = false;
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  getContactList(sapId: string, contactPage: number) {
    this.spinner.show();
    this.btcontactService.getBtContactsByAccountId(sapId, contactPage).subscribe(data => {
      this.contactsList = data.content;
      this.totalContacts = data.totalElements;
      this.contactFirst = data.first;
      this.contactLast = data.last;
      this.contactSize = data.size;
      this.contactSerial = (((contactPage) * this.contactSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;     
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  getSiteList(sapId: string, sitePage: number) {
    this.spinner.show();
    let size=10;
    this._btAccountService.getBtSitesByAccount(sapId, sitePage,size).subscribe(data => {
      this.sitesList = data.content;
      this.totalSites = data.totalElements;
      this.siteFirst = data.first;
      this.siteLast = data.last;
      this.siteSize = data.size;
      this.siteSerial = (((sitePage) * this.siteSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;     
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  getCaseList(sapId: string, casePage: number) {
    this.spinner.show();
    this._btAccountService.getBtCaseList(sapId, casePage).subscribe(data => {
      this.casesList = data.content;
      this.totalCases = data.totalElements;
      this.caseFirst = data.first;
      this.caseLast = data.last;
      this.caseSize = data.size;
      this.caseSerial = (((casePage) * this.caseSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;    
      this._global.alert({ type: 'alert', message: this.message });
    });
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
  changeAddressDisplay1() {
    this.addressDisplay1.open = !this.addressDisplay1.open
    if (this.addressDisplay1.open) {
      this.addressDisplay1.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay1.angleIcon = 'fa fa-angle-right';
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
  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName == 'details') {
       this.getAccounts(this.sapId);
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    }
    else {
      this.getContactList(this.sapId, this.contactPage);
      this.getSiteList(this.sapId, this.sitePage);
      this.getCaseList(this.sapId, this.casePage);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.getContacts();
    }
    return false;
  }


  showCreateAccountView() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  createEditModelshow(createAccount: AccountCreate) {
    //this.createAccount=createAccount;   
    this.spinner.show();
    this._btAccountService.getBtAccount(createAccount.sampAccountNumber).subscribe(data => {
      this.createAccount = data;
      if (data.shippingCountryId == null) {
        this.createAccount.shippingCountryId = '0';
      }
      if (data != null) {
        this.spinner.hide();
      }

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;   
      this._global.alert({ type: 'alert', message: this.message });
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.hide();
  }
  createAcountModelhide() {
    this.createAccount = new AccountCreate();
    this.createAcountModel.hide();

  }
  onAccountCreated($event: AccountCreate) {
    let eventAccount = new AccountCreate();
    this._btAccountService.getBtAccount($event.sampAccountNumber).subscribe(data => {
      eventAccount = data;
    });
    this.accountList.push(eventAccount);
    this.account = $event;
    if (this.account.billingStreet == null || this.account.billingStreet == '') {
      this.isBillingStreet = false;
    }
  }

  showCreateContactView(account: AccountCreate) {
    this.newcontact = new Contacts();
    this.newcontact.accountName = account.accountName;
    this.newcontact.accountSAPNumber = account.sampAccountNumber;
    this.isEditMode = false;
    this.heading = this.CREATE_CONTACT_HEADING;
    this.createContactModel.show();
  }
  contactEditModelshow(id) {
    this.spinner.show();
    this._contactService.getContact(id).subscribe((data) => {
      this.newcontact = data;
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CONTACT_HEADING;
    this.createContactModel.show();
  }
  cancelData1() {
    this.newcontact = new Contacts();
    this.createContactModel.hide();
  }


  onContactCreated($event: Contacts) {
    this.contactsList.unshift($event);
    this.getContactList(this.sapId, this.contactPage);
  }

  createShowModal(account: AccountCreate) {
    this.res = new SiteDetailsResponse();
    this.res.accountName = account.accountName;
    this.res.accountSapNumber = account.sampAccountNumber;
    this.isEditMode = false;
    this.heading = this.CREATE_SITE_HEADING;
    this.createSitesModel.show();
  }
  siteEditModelshow(id) {
    this.spinner.show()
    this.siteservice.getDetailBysiteId(id).subscribe(data => {
      this.spinner.hide()
      this.res = data;
    });
    this.isEditMode = true;
    this.heading = this.EDIT_SITE_HEADING;
    this.createSitesModel.show();
  }
  cancelData2() {
    this.res = new SiteDetailsResponse();
    this.createSitesModel.hide();
  }

  onSiteCreated($event: SiteDetailsResponse) {
    this.sitesList.unshift($event);
    this.getSiteList(this.sapId, this.sitePage);
  }

  createCaseModelshow(account: AccountCreate, verticalId) {
    this.caseRes = new CaseResponse();
    this.caseRes.accountName = account.accountName;
    this.caseRes.accountSapNumber = account.sampAccountNumber;
    this.caseRes.caseReasonVerticalId = verticalId;
    this.caseRes.caseStatusId = 1;
    if (verticalId === 1) {
      this.caseRes.caseStageId = 1;
    } else if (verticalId === 2) {
      this.caseRes.caseStageId = 7;
    }
    this.caseRes.caseOriginId = 3;
    this.caseRes.casePriorityId = 2;
    this.isEditMode = false;
    this.heading = this.CREATE_CASE_HEADING;
    this.createCaseModel.show();
  }
  caseEditModelshow(id) {
    this.spinner.show();
    this.case_service.getDetailByCaseId(id).subscribe(data => {
      this.caseRes = data
      this.spinner.hide();
    })
    this.isEditMode = true;
    this.heading = this.EDIT_CASE_HEADING;
    this.createCaseModel.show();
  }

  createCaseModelhide() {
    this.caseRes = new CaseResponse();
    this.createCaseModel.hide();
  }

  cancelDatacase() {
    this.caseRes = new CaseResponse();
    this.createCaseModel.hide();
  }

  onCaseCreated($event: CaseResponse) {
    this.casesList.unshift($event);
    this.getCaseList(this.sapId, this.casePage);
  }

  // Contacts Pagination
  setPagePreviousContact(event: any) {
    event.preventDefault();
    this.contactPage = this.contactPage - 1;
    this.getContactList(this.sapId, this.contactPage);
  }

  setPageContact(event: any) {
    event.preventDefault();
    this.getContactList(this.sapId, this.contactPage);
  }

  setPageNextContact(event: any) {
    event.preventDefault();
    this.contactPage = this.contactPage + 1;
    this.getContactList(this.sapId, this.contactPage);
  }
  // Sites Pagination
  setPagePreviousSites(event: any) {
    event.preventDefault();
    this.sitePage = this.sitePage - 1;
    this.getSiteList(this.sapId, this.sitePage);
  }

  setPageSites(event: any) {
    event.preventDefault();
    this.getSiteList(this.sapId, this.sitePage);
  }

  setPageNextSites(event: any) {
    event.preventDefault();
    this.sitePage = this.sitePage + 1;
    this.getSiteList(this.sapId, this.sitePage);
  }
  // caseList Pagination
  setPagePreviousCase(event: any) {
    event.preventDefault();
    this.casePage = this.casePage - 1;
    this.getCaseList(this.sapId, this.casePage);
  }

  setPageCase(event: any) {
    event.preventDefault();
    this.getCaseList(this.sapId, this.casePage);
  }

  setPageNextCase(event: any) {
    event.preventDefault();
    this.casePage = this.casePage + 1;
    this.getCaseList(this.sapId, this.casePage);
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }

}
