import { Component, OnInit, ViewChild } from '@angular/core';
import { CaseResponse, CaseRequest, ProblemReported, AssetsResponse, CaseReasonForList, CaseTypeRecord, CaseTypeForList, Page, CaseStatus, CaseStageDetails } from '../../services/model';
import { caseService } from '../../services/caseService.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { OrderPipe } from 'ngx-order-pipe';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Loginservices } from '../../services/login.services';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as fileSaver from 'file-saver';
import { Event } from 'ngx-bootstrap/utils/facade/browser';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import { Pm_asset_site_service } from '../../services/pm_asset_site.service';

@Component({
  selector: 'app-case',
  templateUrl: './case.component.html',
  styleUrls: ['./case.component.scss']
})
export class CaseComponent implements OnInit {
  @ViewChild('createCaseModel') createCaseModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  @ViewChild('chooscaseFiles') chooscaseFiles:ModalDirective;
  res: CaseResponse = new CaseResponse();
  req: CaseRequest = new CaseRequest();
  AllCase: Page<CaseResponse> = new Page<CaseResponse>();
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Case ';
  EDIT_ACCOUNT_HEADING: string = 'Edit Case '
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
  datainput: boolean = false;
  ngClassTable: string = 'fa fa-check';
  values: string;
  caseId: any;
  problemOccured: Array<ProblemReported> = [];
  problem: ProblemReported = new ProblemReported();
  recentUser: boolean = true;
  AllProblemList: Array<ProblemReported> = [];
  caseReasonForList: Array<CaseReasonForList> = [];
  caseTypeList: Array<CaseTypeForList> = [];
  caseStatusForList:Array<CaseStatus>=[];
  caseStageForList:Array<CaseStageDetails>=[];
  assests: AssetsResponse = new AssetsResponse()
  i: any;
  popupNgClass: string = 'alert alert-danger center center-block';
  caseReasonvar: string;
  caseReasonId: any;
  caseTypeId: string;
  caseTypeName: string;
  selectedReason: Array<ProblemReported>;
  message: string = '';
  fileToUpload: File;
  getFile:any;
  podgetFile:any;
  caseRejectgetFile:any;
  fileList: any[] = [];
  filyType: string;
  formData = new FormData();
  isCaseModelOpen: boolean = false;
  casetypRecord: Array<CaseTypeRecord> = [];
  pagenumber:any=null;
  _cFlag: string;
  uploadForm: FormGroup;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  pmassetsite:any=null;
  advancesearch = {
    caseNumber: '',
    siteName: '',
    assetsSerialNumber: '',
    typeName: '',
    reasonName: '',
    status: '',
    stage: '',
    engName: '',
    account: '',
    dateTimeOpenFrom: '',
    dateTimeOpenTo: '',
    dateTimeCloseTo: '',
    dateTimeCloseFrom: ''
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
        name: 'Case Number',
        sortColumnName: 'caseId',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id:2,
        name:'Pre-Registration Case',
        sortColumnName:'prc.manualId',
        icon:'fa fa-angle-down pull-right'
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
        name: 'Type',
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
        name: 'Date/Time Opened',
        sortColumnName: 'createDate',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 10,
        name: 'Date/Time Closed',
        sortColumnName: 'closeDate',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 11,
        name: 'Service Resource',
        sortColumnName: 'engineer.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id:12,
        name:'Vendor Name',
        sortColumnName: 'engact.name',
        icon: 'fa fa-angle-down pull-right'
      }
     
      
     
    ];
  
    //done

  constructor(private pagefilterservice:PagefilterService,private case_service: caseService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _global:GlobalserviceService,private datePipe:DatePipe,private pmsiteassetservice:Pm_asset_site_service,
    private orderPipe: OrderPipe, private loginService: Loginservices, private formBuilder: FormBuilder) {
    this._activatedRoute.params.subscribe(param => {
      this.caseId = param['caseId'];

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

    this.spinner.show();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });

  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('cases').subscribe(data =>{
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
        console.log(item.name,"stats=="+item.clickEventParam);
        // if(item.name=='Closed Cases'){
        //   this.byStatus(item.clickEventParam);
        // }
        // else if(item.name=='Open Cases'){
        //   this.byStatus(item.clickEventParam);
        // }
        // else if(item.name=='To Be Invoiced'){
        // this.byStage(item.clickEventParam);
        // }
        // else if(item.name=='Document Awaited'){
        //   this.byStage(item.clickEventParam);
        // }
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


  showCase() {
    this.spinner.show();
    this.AllCase.content=[];
    this.case_service.getCase(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
  }

  byStatus(status){
    this.spinner.show();
    this.AllCase.content=[];
 this.case_service.getCaseByStatus(status,`page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
  this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  byStage(stag){
    this.spinner.show();
    this.AllCase.content=[];
 this.case_service.getCaseByStage(stag, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
  this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
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


  editCaseModelshow(res: CaseResponse) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.res = res;
    this.case_service.getDetailByCaseId(res.caseId).subscribe(data => {
      this.res = data;
      this.res.contactPersonId = data.contactPersonId;
      this.res.assetDescription = data.assetDescription;
      this.spinner.hide();
      this.createCaseModel.show();
    }, error => {
      this.spinner.hide();
      this.createCaseModel.show();
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });
  }


  createShowModal() {
    this.assests = new AssetsResponse();
    this.res = new CaseResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createCaseModel.show();
  }
  onCancel() {
    this.res = new CaseResponse()
    this.createCaseModel.hide()
  }



  


  cancelDatacase() {
    this.isEditMode = false;
    this.createCaseModel.hide();
  }

  onCaseCreated($event: CaseResponse) {
    this.message = 'Created Successfully';
    this._global.alert({ type: '', message: this.message });
          this.chooscaseFiles.show();
          this.caseId=$event.caseId;
    if (!this.isEditMode) {
      this.AllCase.content.unshift($event);

    } else {
      let index = 0;
      this.AllCase.content.forEach(element => {
       this.caseId=$event.caseId;
        if (element.caseId === $event.caseId) {
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
      // this.createCaseModel.hide();
      this.cancelDatacase();
      this.showCase();

    }
  }
  // bulk upload function
 
  onFileSelect(event) {
    // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
    if(event!=null){
      
    this.getFile = event.target.files.id;
    this.filyType = event.target.id;
    // this.podgetFile=event.target.files.id;
   
    console.log(this.filyType);
    if (event.target.files.length > 0) {
      this.chooscaseFiles.hide()
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
        // console.log(this.fileToUpload);

      }
    }
    }
    else{
      console.log(event)
    }
   
  }
 
  onSubmit() {
    console.log(this.filyType);
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('file', this.fileList[i]);
      // this.fileToUpload = event.target.fileList[i];

    }
    if (this.filyType == "getFile") {
      this.case_service.uploadFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
      }, error => {
        this.spinner.hide();
        this.message = error.message;
     
        this._global.alert({type:'alert',message:error.message});
      });
      this.formData = new FormData();
      this.getFile = "";
    }
    if(this.filyType=="podgetFile"){
      this.case_service.pod_uploadFiles(this.fileToUpload).subscribe(data=>{
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('podgetFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
      },
      error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({type:'alert',message:error.message});
      }
      );
      this.formData = new FormData();
      this.getFile = "";
    }
    if(this.filyType=="caseRejectgetFile"){
      this.case_service.rejectcase_uploadFiles(this.fileToUpload).subscribe(data=>{
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('caseRejectgetFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
      },
      error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({type:'alert',message:error.message});
      }
      );
      this.formData = new FormData();
      this.getFile = "";

    }
    if(this.filyType=="select"){
      console.log(this.formData);
      
     
      this.case_service.uploadFilecase(this.caseId,this.fileToUpload).subscribe(data=>{
       this.fileList = [];
       this.spinner.hide();
        (<HTMLInputElement>document.getElementById('select')).value=null
            // this.spinner.hide();
            this.message = 'File Uploaded Successfully';
            this.choosenFiles.hide();
            this._global.alert({ type: '', message: this.message });
          }, error => {
            this.fileList = [];
            this.spinner.hide();
            // this.popupNgClass = 'alert alert-danger center center-block';
            this.message = error.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          });
        this.formData = new FormData();
    }
    if(this.filyType=="pmassetsite"){
      this.case_service.uploadFilesiteData(this.fileToUpload).subscribe(data=>{
        this.spinner.hide();
        this.choosenFiles.hide();
        // this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('caseRejectgetFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
      },
      error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({type:'alert',message:error.message});
      }
      );
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
    (<HTMLInputElement>document.getElementById('getFile')).value=null
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
      this._global.alert({type:'alert',message:error.message});
    })
  }
  getPODTemplateExcel(){
    this.case_service.getPODExcelTemplate().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "POD_Case" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:error.message});
    })

  }
  getCaseRejectTemplateExcel(){
    this.case_service.getCaseRejectExcelTemplate().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Case_Reject" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:error.message});
    })
  }
  getsiteDataTemplateExcel(){
    this.case_service.getExcelTemplatesitedata().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Site_data_upload" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:error.message});
    })
  }
  

  searchCase() {
   
    if(this.advancesearch.caseNumber=='' && this.advancesearch.account=='' &&
    this.advancesearch.assetsSerialNumber=='' && this.advancesearch.dateTimeCloseFrom==''&&
    this.advancesearch.dateTimeCloseTo=='' && this.advancesearch.dateTimeOpenFrom=='' && 
    this.advancesearch.dateTimeOpenTo=='' && this.advancesearch.engName=='' &&
    this.advancesearch.siteName==''&& this.advancesearch.reasonName==''&&
    this.advancesearch.stage=='' && this.advancesearch.status=='' &&
     this.advancesearch.typeName==''){
     this.message="Please Enter valid Key for Search";
      this.popupSuccessModel.show()
    }
    else{
      this.spinner.show();
      if(this.advancesearch.dateTimeOpenFrom!='')
      {
        this.advancesearch.dateTimeOpenFrom = this.datePipe.transform(this.advancesearch.dateTimeOpenFrom, 'yyyy-MM-dd');
      } 
      if(this.advancesearch.dateTimeOpenTo!=''){
      this.advancesearch.dateTimeOpenTo= this.datePipe.transform(this.advancesearch.dateTimeOpenTo, 'yyyy-MM-dd');
    }      
     if(this.advancesearch.dateTimeCloseTo!=''){          
          this.advancesearch.dateTimeCloseTo= this.datePipe.transform(this.advancesearch.dateTimeCloseTo, 'yyyy-MM-dd');
          }
      if(this.advancesearch.dateTimeCloseFrom!='')
      {
        this.advancesearch.dateTimeCloseFrom= this.datePipe.transform(this.advancesearch.dateTimeCloseFrom, 'yyyy-MM-dd');
      }
      this.case_service.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.AllCase.content = data;
        this.AllCase.first = true;
        this.AllCase.last = true;
        this.page = 0;
        this.size = data.length;
        this.first = true;
        this.last = true;
      },error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this.popupSuccessModel.show()

      });
    }
  
  }
  searchReset(){
    this.advancesearch.caseNumber=''
    this.advancesearch.account='' 
    this.advancesearch.assetsSerialNumber='' 
     this.advancesearch.dateTimeCloseFrom=''
    this.advancesearch.dateTimeCloseTo=''
    this.advancesearch.dateTimeOpenFrom=''
    this.advancesearch.dateTimeOpenTo=''
    this.advancesearch.engName=''
    this.advancesearch.siteName=''
     this.advancesearch.reasonName=''
    this.advancesearch.stage=''
     this.advancesearch.status=''
     this.advancesearch.typeName=''

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
  encodeURLCode(urld):string{
    return encodeURIComponent(urld);
  }

  jumpPage(pagenumber){
    if(pagenumber!=null)

   {
    this.AllCase.number=this.pagenumber-1;
  }
else{

}
    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.AllCase.content=[];
    this.case_service.getCase(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });

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
   // file upload
  //  fileList:string[];

  //  formData: FormData = new FormData()
   fileName: string;
   imageUrl: string;

   removeFile(fileName) {
     if (this.fileList.length > 0) {
       for (const value of this.fileList) {
         if (value['name'] === fileName) {
           this.fileList.splice(this.fileList.indexOf(value), 1);
         }
       }
       this.choosenFiles.show();
     }
   }
   closeUploadFiles() {
     this.formData = new FormData();
     this.choosenFiles.hide();
     
     (<HTMLInputElement>document.getElementById('getFile')).value=null
   }
}
