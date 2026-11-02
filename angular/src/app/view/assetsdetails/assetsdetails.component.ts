import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ActionHistoryObjectAndId, AssetsResponse, CaseResponse, Page, PreRegCaseResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { AssetsService } from '../../services/assets.services';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { caseService } from '../../services/caseService.service';
import { Loginservices } from '../../services/login.services';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-assetsdetails',
  templateUrl: './assetsdetails.component.html',
  styleUrls: ['./assetsdetails.component.css']
})
export class AssetsdetailsComponent implements OnInit {
  @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createCaseModel') createCaseModel: ModalDirective;
  @ViewChild('createPRCModel') createPRCModel :ModalDirective;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;

  sapId: string = '';

  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;

  casePage: number = 0;
  caseFirst: boolean;
  caseLast: boolean;
  caseSize: number;
  totalCases: number;
  caseSerial: number;
  loginData: any;
  domainId: number;
  siteId:number;
  CREATE_ACCOUNT_HEADING: string = 'New Asset';
  EDIT_ACCOUNT_HEADING: string = 'Edit Asset';

  CREATE_CASE_HEADING: string = 'New Case';
  EDIT_CASE_HEADING: string = 'Edit Case';

  assetResponse: AssetsResponse = new AssetsResponse();
  assetList: Array<AssetsResponse> = [];
  asset: AssetsResponse = new AssetsResponse();

  casesList: Page<CaseResponse> =new Page<CaseResponse>();
  caseRes: CaseResponse = new CaseResponse();
  popupNgClass: string = 'alert alert-danger center center-block';
  isEditModelShow: boolean = false;
  isDCTCaseModelShow: boolean = false;
  isBatteryCaseModelShow: boolean = false;
  cmponentprc:string="assetprc";
  cmponentassetcase:string='assetcase'
  assetColumnsprc:any=[];
  assetColumnsCase:any=[];
   sitePrcHeader=["S.No","Prc No","Type","Case Reason","Action","Case Number"];
   assetCaseHeader=["S.No","Case Number","Type","Case Reason","Status","PRC Number"];
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  purchaseDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  warrantyDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  amcDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  warranty: string;
  _cFlag: string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _assetsService: AssetsService,private prcservice:PreRegCaseService,
     private case_service: caseService, private loginService: Loginservices,  private _global:GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = decodeURIComponent(pram['id']);
      this.getAssets(this.sapId);
      this.actionHistoryObjectAndId.objectId = this.sapId;
      this.actionHistoryObjectAndId.objectName = 'Assets';

    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
      this.assetColumnsprc=this.sitePrcHeader;
      this.assetColumnsCase=this.assetCaseHeader;
    });
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
  }

  getAssets(sapId: string) {
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(sapId).subscribe(data => {
      this.asset = data;
      
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  sortOrder: string = 'desc';
  sortColumnName: string = 'createDate';
  getCaseList(sapId: string, casePage: number) {
    this.spinner.show();
    this.casesList.content=[];
    this._assetsService.getCaseList(sapId,this.sortColumnName, casePage,this.sortOrder).subscribe(data => {
      this.casesList = data;
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
      this.popupSuccessModel.show();
    });
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changePurchaseDisplay() {
    this.purchaseDisplay.open = !this.purchaseDisplay.open;
    if (this.purchaseDisplay.open) {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeWarrantyDisplay() {
    this.warrantyDisplay.open = !this.warrantyDisplay.open;
    if (this.warrantyDisplay.open) {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeAmcDisplay() {
    this.amcDisplay.open = !this.amcDisplay.open;
    if (this.amcDisplay.open) {
      this.amcDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.amcDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open;
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getAssets(this.sapId);
    } else {
      this.getCaseList(this.sapId, this.casePage);
      this.getPrcByAsset();
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
  }

  createAcountModelhide() {
    this.assetResponse = new AssetsResponse();
    this.createAssetModel.hide();
  }

  createEditModelshow(asset: AssetsResponse) {
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(asset.serialNumber).subscribe(data => {
      this.assetResponse = data;
      this.assetResponse.productDescription=data.productDescription;
      if (data.siteId == null) {
        this.assetResponse.siteId = 0;
      }
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createAssetModel.show();
  }

  cancelData() {
    this.assetResponse = new AssetsResponse();
    this.createAssetModel.hide();
  }

  onAssetCreated($event: AssetsResponse) {
    let assetEvent = new AssetsResponse();
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber($event.serialNumber).subscribe(data => {
      assetEvent = data;
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.assetList.push(assetEvent);
    this.asset = $event;
    this.getAssets(this.sapId);
  }
 assetWarrety:string='';
  createCaseModelshow(serialNumber) {
    this.loginData = this.loginService.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId'];
    this.caseRes = new CaseResponse();
    this.caseRes.assetsSerialNumber = serialNumber;
    this.caseRes.caseReasonVerticalId = this.domainId;
    this.caseRes.caseStatusId = 3;
    this.caseRes.siteId=this.asset.siteId;
    this.siteId=this.asset.siteId;
    this.caseRes.accountSapNumber=this.asset.accountSapNumber
      this.caseRes.assetDescription    = this.asset.productDescription;
      this.assetWarrety=this.asset.warranty
    if (this.domainId === 1) {
      this.caseRes.caseStageId = 5;
    } else if (this.domainId === 2) {
      this.caseRes.caseStageId = 11;
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
      this.caseRes = data;
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CASE_HEADING;
    this.createCaseModel.show();

  }
  createCaseModelhide() {
    this.caseRes = new CaseResponse();
    this.createCaseModel.hide();
  }
  prcModelhide(){
    this.createPRCModel.hide();
  }

  cancelDatacase() {
    this.caseRes = new CaseResponse();
    this.createCaseModel.hide();
  }
  onCaseCreated($event: CaseResponse) {
    this.message = 'Created Successfully';
    this._global.alert({ type: '', message: this.message });

    this.casesList.content.unshift($event);
    this.getCaseList(this.sapId, this.casePage)
    this.getAssets(this.sapId);
    this.getPrcByAsset();
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.casePage = this.casePage - 1;
    this.getCaseList(this.sapId, this.casePage);
  }

  setPage(event: any) {
    event.preventDefault();
    this.getCaseList(this.sapId, this.casePage);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.casePage = this.casePage + 1;
    this.getCaseList(this.sapId, this.casePage);
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }
  changeURL(urld):string{
    return encodeURIComponent(urld);
  }
  res: PreRegCaseResponse = new PreRegCaseResponse();
  createPRCModelshow(asst:AssetsResponse){
  // this.res=asst;
  this.res.assetsSerialNumber=asst.serialNumber;
  this.res.siteId=asst.siteId;
  this.res.siteManualId=asst.siteManualId;
  this.res.accountName=asst.accountName;
  this.res.accountSapNumber=asst.accountSapNumber;
  this.res.assetsPartCode=asst.productPartCode;
  this.heading="Create Pre-Registration";
    this.createPRCModel.show();

  }
  page:number=0;
  prcdata:Page<PreRegCaseResponse>=new Page<PreRegCaseResponse>()
  getPrcByAsset(){
    this.spinner.show();
    const size=5;
    this.prcdata.content=[];
    this.prcservice.getPRCByAsset(this.sapId,this.page,size).subscribe(data=>{
    this.spinner.hide();
    this.prcdata=data;
    })
  }
}
