import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { CaseResponse, AssetsResponse, PreRegCaseResponse, PreRegCaseRequest, ProblemReported, CaseReasonForList, CaseTypeForList, CaseTypeRecord, Page, CaseStatus, CaseStageDetails, LastActionForList } from '../../services/model';
import { OrderPipe } from 'ngx-order-pipe';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { caseService } from '../../services/caseService.service';
import * as fileSaver from 'file-saver';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-case-pre-regis',
  templateUrl: './case-pre-regis.component.html',
  styleUrls: ['./case-pre-regis.component.scss']
})
export class CasePreRegisComponent implements OnInit {
  @ViewChild('createPreRegisCaseModel') createPreRegisCaseModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  res: PreRegCaseResponse = new PreRegCaseResponse();
  req: PreRegCaseRequest = new PreRegCaseRequest();
  AllCase: Page<PreRegCaseResponse>  = new Page<PreRegCaseResponse>()
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
  caseReason: Array<CaseReasonForList> = [];
  caseTypeList: Array<CaseTypeForList> = [];
  assests: AssetsResponse = new AssetsResponse()
  i: any;
  popupNgClass: string = 'alert alert-danger center center-block';
  caseReasonvar: string;
  caseReasonId: any;
  caseTypeId: string;
  caseTypeName: string;
  selectedReason: Array<ProblemReported>;
  message: string = '';
  isCaseModelOpen: boolean = false;
  casetypRecord: Array<CaseTypeRecord> = [];
  _cFlag: string;
  uploadForm: FormGroup;
  caseReasonForList: Array<CaseReasonForList> = [];
  // caseTypeList: Array<CaseTypeForList> = [];
  caseStatusForList:Array<CaseStatus>=[];
  caseStageForList:Array<CaseStageDetails>=[];

  advancesearch = {
    manualId: '',
    caseManualId:'',
    manualSiteId: '',
    assetSerialNumber: '',
    type: '',
    reason: '',
    status: '',
    stage: '',
    engineer: '',
    account: '',
    lastAction: ''
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
    sortname: 'createDate',
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
      sortColumnName: 'cd.caseId',
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
      sortColumnName: 'ast.site.siteId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Asset Serial Number',
      sortColumnName: 'ast.serialNumber',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Case Type',
      sortColumnName: 'caseType.caseTypesName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Case Reason',
      sortColumnName: 'caseReason.caseReasonDetailsName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Status',
      sortColumnName: 'status.caseStatusName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Stage',
      sortColumnName: 'stage.caseStageName',
      icon: 'fa fa-angle-down pull-right'
    },
   
    {
      id: 9,
      name: 'Created Date',
      sortColumnName: 'createDate',
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
      sortColumnName: 'engineer.name',
      icon: 'fa fa-angle-down pull-right'
    }
    
   
  ];

  //done
  constructor(private pagefilterservice:PagefilterService,private case_service: caseService,private preregcaseservice:PreRegCaseService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private orderPipe: OrderPipe, private loginService: Loginservices, private formBuilder: FormBuilder) {
    this._activatedRoute.params.subscribe(pram => {
      this.caseId = pram['caseId'];
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
    // this.requestTableHeaders=[];
    this.AllCase.content=[];
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
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });

  }
  // shagufta
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('prc').subscribe(data =>{
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
      this.popupSuccessModel.show();
    });
  }

  caseRecord() {
    this.case_service.getCaseRecord().subscribe((data) => {
      this.casetypRecord = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });
  }


  editCaseModelshow(res: PreRegCaseResponse) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.res = res;
    this.preregcaseservice.getDetailByCaseId(res.prcCaseId).subscribe(data => {
      this.res = data;
      this.res.contactPersonId = data.contactPersonId;
      this.res.assetDescription = data.assetDescription;
      this.spinner.hide();
      this.createPreRegisCaseModel.show();
    }, error => {
      this.spinner.hide();
      // this.createPreRegisCaseModel.show();
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });
  }



  createPreRegisCase() {
    this.assests = new AssetsResponse();
    this.res = new PreRegCaseResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createPreRegisCaseModel.show();
  }
  onCancel() {
    this.res = new PreRegCaseResponse()
    this.createPreRegisCaseModel.hide()
  }



  onCaseCreated($event: PreRegCaseResponse) {
    if (!this.isEditMode) {
      this.AllCase.content.unshift($event);
    } else {
      let index = 0;
      this.AllCase.content.forEach(element => {

        if (element.prcCaseId === $event.prcCaseId) {
          this.AllCase[index] = $event;
        } else {
          index++;
        }
      });
    }
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.showCase();
    }
  }
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
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];
        this.message = "Submitted Successfully";
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this.popupSuccessModel.show();
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
      this.popupSuccessModel.show();
    })
  }
  

  searchCase() {
   
    if( this.advancesearch.manualId=='' && this.advancesearch.caseManualId=='' && this.advancesearch.account=='' &&
    this.advancesearch.assetSerialNumber=='' && this.advancesearch.engineer=='' &&
    this.advancesearch.manualSiteId==''&& this.advancesearch.reason==''&&
    this.advancesearch.stage=='' && this.advancesearch.status=='' && this.advancesearch.type=='' &&
    this.advancesearch.lastAction==''){
     this.message="Please Enter valid Key for Search";
      this.popupSuccessModel.show()
    }
   else{
    this.spinner.show();
    this.preregcaseservice.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.AllCase.content = data;
      this.AllCase.first = true;
      this.AllCase.last = true;
      this.page = 0;
      this.size = data.length;
      this.first = true;
      this.last = true;
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
    this.AllCase.number=this.pagenumber-1;
  }
else{
// this.assetList.number=this.pagenumber-1;

}
    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.AllCase.content=[];
    this.preregcaseservice.getCase(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });

  }
  searchreset(){
   this.advancesearch.manualId= ''
   this.advancesearch.caseManualId=''
   this.advancesearch.manualSiteId=''
   this.advancesearch. assetSerialNumber= ''
   this.advancesearch.type=''
   this.advancesearch.reason=''
   this.advancesearch.status=''
   this.advancesearch.stage= ''
   this.advancesearch.engineer=''
   this.advancesearch.account=''
   this.advancesearch.lastAction=''

  }
  getCaseTypeList(){
    this.caseTypeList=[];
    this.case_service.getCaseTypeForlist().subscribe(data=>{
      const temp=[];
     for(let i=0;i<data.length;i++)
     {
     let lis=new CaseTypeForList;
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
        let lis=new CaseReasonForList;
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
        let lis=new CaseStatus;
        lis.caseStatusName=data[j]
        temp.push(lis);
        this.caseStatusForList=temp;
      }
    })
  }
  getStageForList(){
    this.case_service.getCaseStageAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new CaseStageDetails;
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
  
}
