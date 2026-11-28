import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { LastActionForList, PreRegCaseRequest, PreRegCaseResponse, ProblemReported } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BTassetResp, BTCaseReasonForList, BTCaseStageDetails, BTCaseTypeForList, BtPreRegCaseRequest, BtPreRegCaseResponse, BT_CaseStatus, BT_CaseTypeRecord, Page } from '../../bt_model';
import * as fileSaver from 'file-saver';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bat-pre-registration-case',
  templateUrl: './bat-pre-registration-case.component.html',
  styleUrls: ['./bat-pre-registration-case.component.css']
})
export class BatPreRegistrationCaseComponent implements OnInit {

  @ViewChild('createPreRegisCaseModel') createPreRegisCaseModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  res: BtPreRegCaseResponse = new BtPreRegCaseResponse();
  req: BtPreRegCaseRequest = new BtPreRegCaseRequest();
  AllCase: Page<BtPreRegCaseResponse>  = new Page<BtPreRegCaseResponse>()
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Pre-Registration Case ';
  EDIT_ACCOUNT_HEADING: string = 'Edit Pre-Registration Case '
  order: string = 'caseId'; // first column variable
  reverse: boolean = false;
  page: number = 0;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  showFilter: boolean = false;
  showHide: boolean = false;
  searchitem: string = '';
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassGenerated: string;
   ignoreStatus: string = 'ignore';
  generatedStatus: string = 'generated';
  recently: boolean = true;
  ngClassIgnore: string;
  related: boolean = true;
  open: boolean = false;
  close: boolean = false;
  datainput: boolean = false;
  ngClassTable: string = 'fa fa-check';
  values: string;
  caseId: any;
  problemOccured: Array<ProblemReported> = [];
  problem: ProblemReported = new ProblemReported();
  recentUser: boolean = true;
  AllProblemList: Array<ProblemReported> = [];
  caseReason: Array<BTCaseReasonForList> = [];
  caseTypeList: Array<BTCaseTypeForList> = [];
  assests: BTassetResp = new BTassetResp()
  i: any;
  // popupNgClass: string = 'alert alert-danger center center-block';
  caseReasonvar: string;
  caseReasonId: any;
  caseTypeId: string;
  caseTypeName: string;
  selectedReason: Array<ProblemReported>;
  message: string = '';
  isCaseModelOpen: boolean = false;
  casetypRecord: Array<BT_CaseTypeRecord> = [];
  _cFlag: string;
  uploadForm: UntypedFormGroup;
  caseReasonForList: Array<BTCaseReasonForList> = [];
  // caseTypeList: Array<CaseTypeForList> = [];
  caseStatusForList:Array<BT_CaseStatus>=[];
  caseStageForList:Array<BTCaseStageDetails>=[];

  advancesearch = {
    manualID: '',
    caseManualId:'',
    siteManualId: '',
    assetsSerialNumber: '',
    type: '',
    reason: '',
    caseStatusName: '',
    stage: '',
    engineer: '',
    // account: '',
    lastAction: '',
    caseTypeName:''
  };
   //filter
   dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Recently Viewed',
    clickEventParam: 'related',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Closed Cases',
    clickEventParam: 'closed',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 4,
    name: 'Open Cases',
    clickEventParam: 'open',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 5,
    name: 'To Be Invoice',
    clickEventParam: 'toBeInvoice',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 6,
    name: 'Document Awaited',
    clickEventParam: 'documentAwaited',
    class: 'dropdown-item',
    pined: false
  },
  
 
 ];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Pre-Registration Case',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Case Number',
      sortColumnName: 'btcase.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id:11,
      name: 'Last Action',
      sortColumnName:'',
      icon: ''
    },
    {
      id: 3,
      name: 'Site Id',
      sortColumnName: 'site.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Asset Serial Number',
      sortColumnName: 'ast.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Case Type',
      sortColumnName: 'ct.case_types_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Case Reason',
      sortColumnName: 'cr.case_reason_details_name',
      icon: 'fa fa-angle-down pull-right'
    },
    // asf
    {
      id: 7,
      name: 'Status',
      sortColumnName: 'csd.case_status_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Stage',
      sortColumnName: 'cstage.case_stage_name',
      icon: 'fa fa-angle-down pull-right'
    },
   
    {
      id: 9,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },
    // {
    //   id: 9,
    //   name: 'Date/Time Closed',
    //   sortColumnName: 'closedDate',
    //   icon: 'fa fa-angle-down pull-right'
    // },
    {
      id: 10,
      name: 'Service Resource',
      sortColumnName: 'se.name',
      icon: 'fa fa-angle-down pull-right'
    }
    
   
  ];

  //done
  constructor(private pagefilterservice:PagefilterService,private case_service: BtCaseserviceService,private preregcaseservice:BtPreRegistrationCaseService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
     private loginService: Loginservices, private formBuilder: UntypedFormBuilder,private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.caseId = pram['id'];
    });
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.size = Number(param['size']);
      }
      this.getFilterList();
    });
    // this.AllCase = this.orderPipe.transform(this.AllCase, this.order);
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.caseRecord();
    this.getCaseTypeList();
    this.getCaseReson();
    this.getStatusForList();
    this.getStageForList();
    this.getLastActionForList();
    this.spinner.show();
    // this.requestTableHeaders=[];
    this.AllCase.content=[];
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });

  }
  // shagufta
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_prc').subscribe(data =>{
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.dropdownSelected.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.dropdownSelected.name = d.filterName;
            this.dropdownSelected.filtreparam = d.filterParam;
            this.dropdownSelected.sqId = d.userSummaryPageFilterId;
            this.pindtselection = true;
            this.dropdownSelected.page = 0;
          }

          else {
            if (d.sqId == this.dropdownSelected.sqId) {
              this.pindtselection = true;
            }
          }

        }
        this.dropdownsList.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }

      this.showCase();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.showCase();
        if (item.pined) {
          this.pindtselection = true;
        } else {
          this.pindtselection = false;
        }
      } else {
        item.class = 'dropdown-item';
      }
    });
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
        this.dropdownSelected.page = 0;
        this.showCase();
      } else {
        tbl.icon = '';
      }
    });
  }
  // 
 
 

  showCase() {
    this.spinner.show();
    this.AllCase.content=[];
    this.preregcaseservice.getCase(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  caseRecord() {
    this.case_service.getCaseRecord().subscribe((data) => {
      this.casetypRecord = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });

    });
  }


  editCaseModelshow(res: BtPreRegCaseResponse) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.res = res;
    this.preregcaseservice.getDetailByCaseId(res.preCaseId).subscribe(data => {
      this.res = data;
      this.res.prcCaseManualId=data.prcCaseManualId;
      this.res.contactPersonId = data.contactPersonId;
      this.res.assetDescription = data.assetDescription;
      this.spinner.hide();
      this.createPreRegisCaseModel.show();
    }, error => {
      this.spinner.hide();
      // this.createPreRegisCaseModel.show();
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });

    });
  }



  createPreRegisCase() {
    this.assests = new BTassetResp();
    this.res = new BtPreRegCaseResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createPreRegisCaseModel.show();
  }
  onCancel() {
    this.res = new BtPreRegCaseResponse()
    this.createPreRegisCaseModel.hide()
  }



  onCaseCreated($event: BtPreRegCaseResponse) {
    
    if (!this.isEditMode) {
      this.AllCase.content.unshift($event);
    } else {
      let index = 0;
      this.AllCase.content.forEach(element => {

        if (element.preCaseId === $event.preCaseId) {
          this.AllCase[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.showCase();
  }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass == 'alert alert-success center center-block') {
  //     this.showCase();
  //   }
  // }
  // bulk upload function
  fileToUpload: File;
  getFile
  fileList: string[] = [];
  filyType: string;
  onFileSelect(event) {
    this.getFile = event.target.files.id;
    this.filyType = event.target.id;
    console.log(event.target.id);
    console.log(event.target.files.length)
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
        console.log(this.fileToUpload);

      }
    }
  }
  formData = new FormData();
  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
      // this.fileToUpload = event.target.fileList[i];

    }
    if (this.filyType == "getFile") {
      this.case_service.uploadFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        // this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];
        this.message = "Submitted Successfully";
        this._global.alert({ type: '', message: this.message });
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
      this.formData = new FormData();
      this.getFile = "";
    }
  }

  downloadFile(data) {
    //  var blob = new Blob([<any>data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;'});
    //  var url= window.URL.createObjectURL(blob);
    //  fileSaver.saveAs(blob,data.fileName);
    //  window.open(url);
    // 
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "Report" + ".xlsx");



  }
  cancelChoosen() {
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = "";
  }
  getTemplateExcel() {
    this.case_service.getExcelTemplate().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Case" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({ type: 'alert', message: this.message });
    })
  }
  

  searchCase() {
   
    if( this.advancesearch.manualID=='' && this.advancesearch.caseManualId=='' &&
    this.advancesearch.assetsSerialNumber=='' && this.advancesearch.engineer=='' &&
    this.advancesearch.siteManualId==''&& this.advancesearch.reason==''&&
    this.advancesearch.stage=='' && this.advancesearch.caseStatusName=='' && this.advancesearch.type=='' &&
    this.advancesearch.lastAction==''){
     this.message="Please Enter Valid Key For Search";
     this._global.alert({ type: 'alert', message: this.message });
    }
   else{
    this.spinner.show();
    this.preregcaseservice.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.AllCase.content = data;
      this.page = 0;
      this.size = data.length;
      this.first = true;
      this.last = true;
      this.AllCase.first = true;
      this.AllCase.last = true;
    });
   }
  }
  pinFilter() {
    this.spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this.spinner.hide();
    }, error => this.spinner.hide());
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    // console.log(this.assetList.pa)
  
    if(pagenumber!=null)
    {
  if(this.AllCase.totalPages< pagenumber || pagenumber==0){
    this.message="Page Count Should Be Less Then Total Page";
    this._global.alert({ type: 'alert', message: this.message });
  
    // this.AllCase.number=this.pagenumber-1;
  }
else{
// this.assetList.number=this.pagenumber-1;


    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.AllCase.content=[];
    this.preregcaseservice.getCase(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
}
  else{
    this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
   }

  }
  searchreset(){
   this.advancesearch.manualID= ''
   this.advancesearch.caseManualId=''
   this.advancesearch.siteManualId=''
   this.advancesearch. assetsSerialNumber= ''
   this.advancesearch.type=''
   this.advancesearch.reason=''
   this.advancesearch.caseStatusName=''
   this.advancesearch.stage= ''
   this.advancesearch.engineer=''
  //  this.advancesearch.account=''
   this.advancesearch.lastAction=''
   this.showCase();

  }
  getCaseTypeList(){
    this.caseTypeList=[];
    this.case_service.getCaseTypeForlist().subscribe(data=>{
      const temp=[];
     for(let i=0;i<data.length;i++)
     {
     let lis=new BTCaseTypeForList;
     lis.caseTypeName=data[i]
     temp.push(lis);
     this.caseTypeList=temp;
     }
    });
  }
  getCaseReson(){
    this.caseReasonForList=[];
    this.case_service.getCaseReasonAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new BTCaseReasonForList;
        lis.caseReasonName=data[j]
        temp.push(lis);
        this.caseReasonForList=temp;
      }
      
        

    })
  }
  getStatusForList(){
    this.case_service.getCaseStatusAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new BT_CaseStatus;
        lis.name=data[j]
        temp.push(lis);
        this.caseStatusForList=temp;
      }
    })
  }
  getStageForList(){
    this.case_service.getCaseStageAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new BTCaseStageDetails;
        lis.caseStageName=data[j]
        temp.push(lis);
        this.caseStageForList=temp;
      }
    })
  }
  actionList:Array<LastActionForList>=[];
  getLastActionForList(){
this.preregcaseservice.getLastActAdvFORList().subscribe(data=>{
  const temp=[];
  for(let i=0;i<data.length;i++){
    let lis = new  LastActionForList();
    lis.action=data[i];
    temp.push(lis);
    this.actionList=temp;
  }
})
  }
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
  
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  
  }
  
}

