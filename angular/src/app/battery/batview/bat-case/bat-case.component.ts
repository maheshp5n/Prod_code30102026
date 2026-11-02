import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { caseService } from '../../../services/caseService.service';
import { Loginservices } from '../../../services/login.services';
import { CaseResponse, CaseTypeRecord, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import * as fileSaver from 'file-saver';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCASEPODLIST, BTCaseResponse, BT_CaseReasonForList, BT_CaseStageDetails, BT_CaseStatus, BT_CaseTypeForList } from '../../bt_model';
import { FieldValidation } from '../../../services/validation.services';

@Component({
  selector: 'app-bat-case',
  templateUrl: './bat-case.component.html',
  styleUrls: ['./bat-case.component.css']
})
export class BatCaseComponent implements OnInit {

  @ViewChild('createCaseModel') createCaseModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  @ViewChild('chooscaseFiles') chooscaseFiles :ModalDirective;
  @ViewChild('caseforpodupdate') caseforpodupdate:ModalDirective;
  @ViewChild('createPD') createPD:ModalDirective;
  // res: CaseResponse = new CaseResponse();
  // req: CaseRequest = new CaseRequest();
  AllCase: Page<BTCaseResponse> = new Page<BTCaseResponse>();
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
  i: any;
  popupNgClass: string = 'alert alert-danger center center-block';
  caseReasonvar: string;
  caseReasonId: any;
  caseTypeId: string;
  caseTypeName: string;
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
  caseReasonForList:Array<BT_CaseReasonForList>=[];
  newcaseReasonForList:Array<BT_CaseReasonForList>=[];
  caseStatusForList:Array<BT_CaseStatus>=[];
  caseStageForList:Array<BT_CaseStageDetails>=[];
  caseTypeList:Array<BT_CaseTypeForList>=[];
  newcaseTypeList:Array<BT_CaseTypeForList>=[];
  pagenumber:any=null;
  _cFlag: string;
  uploadForm: FormGroup;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  casetypeid:number
  caseReason:string="";
  caseListPOD:Array<BTCASEPODLIST>=[]
  caseselectlist:Array<any>=[];
  selectedId:number;
  caseidselect:boolean;
  allselect:boolean=false;
  casetypeNameofpod:string;
  advancesearch = {
    manualID: '',
    siteManualID: '',
    assetsSerialNumber: '',
    caseTypeName: '',
    reasonName: '',
    caseStatusName: '',
    stage: '',
    engName: '',
    sapCustomerNumber: '',
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
      sortname: 'created_date',
      sortorder: 'desc',
      page: 0,
      size: 20,
      querypram: false
    };
    requestTableHeaders = [
      {
        id: 1,
        name: 'Case Number',
        sortColumnName: 'id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id:2,
        name:'Pre-Registration Case',
        sortColumnName:'prc.manual_id',
        icon:'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Site Id',
        sortColumnName: 'btsite.manual_id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 4,
        name: 'Asset Serial Number',
        sortColumnName: 'asstes.manual_id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 5,
        name: 'Type',
        sortColumnName: 'caseType.case_types_name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 6,
        name: 'Case Reason',
        sortColumnName: 'reason.case_reason_details_name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 7,
        name: 'Status',
        sortColumnName: 'st.case_status_name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 8,
        name: 'Stage',
        sortColumnName: 'stage.case_stage_name',
        icon: 'fa fa-angle-down pull-right'
      },
     
      {
        id: 9,
        name: 'Date/Time Opened',
        sortColumnName: 'created_date',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 10,
        name: 'Date/Time Closed',
        sortColumnName: 'close_date',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 11,
        name: 'Service Resource',
        sortColumnName: 'serRes.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id:12,
        name:'Vendor Name',
        sortColumnName: 'acc.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id:13,
        name:'RMA Number',
        sortColumnName: 'rma.manual_id',
        icon: 'fa fa-angle-down pull-right'
      }
        ];
  
    //done

  constructor(private pagefilterservice:PagefilterService, private case_service:caseService,
     private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _global:GlobalserviceService,private datePipe:DatePipe,
    private orderPipe: OrderPipe, private loginService: Loginservices, 
    private formBuilder: FormBuilder,private btcaseservice:BtCaseserviceService) {
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
  CaseId:any=1;

  ngOnInit() {
    // this.caseRecord();
    this.AllCase.content=[];

    this.spinner.show();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    this.caseRecord();
    this.getCaseTypeList();
    this.caseType();
    this.getCaseReson();
    this.getStatusForList();
    this.getStageForList();

  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_case').subscribe(data =>{
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
    this.btcaseservice.getCase(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
  }

  byStatus(status){
    this.spinner.show();
    this.AllCase.content=[];
//  this.case_service.getCaseByStatus(status,`page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
//   this.AllCase = data;
//       this.spinner.hide();
//     }, error => {
//       this.spinner.hide();
//       this.message = error.error.message;
//       this.popupSuccessModel.show();
//     })
  }
  byStage(stag){
    // this.spinner.show();
    this.AllCase.content=[];
//  this.case_service.getCaseByStage(stag, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
//   this.AllCase = data;
//       this.spinner.hide();
//     }, error => {
//       this.spinner.hide();
//       this.message = error.error.message;
//       this.popupSuccessModel.show();
//     })
  }

  caseRecord() {
    this.btcaseservice.getCaseRecord().subscribe((data) => {
      this.casetypRecord = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({type:'alert',message:this.message});

    });
  }


  editCaseModelshow(res: BTCaseResponse) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    // this.res = res;
    this.btcaseservice.getDetailByCaseId(res.caseId).subscribe(data => {
      // this.res = data;
      // this.res.contactPersonId = data.contactPersonId;
      // this.res.assetDescription = data.assetDescription;
      this.spinner.hide();
      this.createCaseModel.show();
    }, error => {
      this.spinner.hide();
      this.createCaseModel.show();
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({type:'alert',message:this.message});

    });
  }

  res: BTCaseResponse=new BTCaseResponse();
  createShowModal() {
    // this.assests = new AssetsResponse();
    this.res = new BTCaseResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createCaseModel.show();
  }
  onCancel() {
    // this.res = new CaseResponse()
    this.createCaseModel.hide()
  }

  cancelDatacase() {
    this.isEditMode = false;
    this.createCaseModel.hide();
  }

  onCaseCreated($event: BTCaseResponse) {
    this.caseId=$event.caseId;
    if(this.isEditMode==true){
      this.cancelDatacase();
      this.message = 'Updated Successfully'
      this.cancelDatacase();
      this._global.alert({ type: '', message: this.message });
    }
   else{
    this.message = 'Created Successfully';
    this._global.alert({ type: '', message: "Case"+' '+$event.caseManualId+' '+this.message });

          // this.chooscaseFiles.show();
  
      this.AllCase.content.unshift($event);
  
      let index = 0;
      this.AllCase.content.forEach(element => {

        if (element.id === $event.id) {
          this.AllCase[index] = $event;
        } else {
          index++;
        }
      });
      this.cancelDatacase();
     
    
   }
   this.showCase();
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
      // this.chooscaseFiles.hide();
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
  missingassetid:any=null;
  onSubmit() {
    
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('file', this.fileList[i]);
      // this.fileToUpload = event.target.fileList[i];

    }
    if (this.filyType == "getFile") {
      this.btcaseservice.uploadFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
        this.showCase();
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
        this.showCase();
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
      this.btcaseservice.rejectcase_uploadFiles(this.fileToUpload).subscribe(data=>{
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('caseRejectgetFile')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
        this.showCase();
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
    
    if(this.filyType=="missingassetid"){
    this.btcaseservice.missingassetcase_uploadFiles(this.fileToUpload).subscribe(data=>{
      this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('missingassetid')).value=null
        this.message = "Updated Successfully";
        this._global.alert({type:'',message:this.message});
        this.showCase();
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
    // var file = new Blob([data], {
    //   type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    // });
    fileSaver.saveAs(data, "Report" + ".xlsx");



  }
  cancelChoosen() {
    if(this.filyType=='getFile'){
      (<HTMLInputElement>document.getElementById('getFile')).value=null
    }


  if(this.filyType=='select'){
    (<HTMLInputElement>document.getElementById('select')).value=null
  }
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = "";
  }
  getTemplateExcel() {
    this.btcaseservice.getExcelTemplate().subscribe(data => {
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
    this.btcaseservice.getPODExcelTemplate().subscribe(data => {
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
  getMissingAssetTemp(){
    this.btcaseservice.getCaseMissingAssetTemplate().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Missing_asset_case" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:error.message});
    })
  }
  

  searchCase() {
   
    if(this.advancesearch.manualID=='' && this.advancesearch.sapCustomerNumber=='' &&
    this.advancesearch.assetsSerialNumber=='' && this.advancesearch.dateTimeCloseFrom==''&&
    this.advancesearch.dateTimeCloseTo=='' && this.advancesearch.dateTimeOpenFrom=='' && 
    this.advancesearch.dateTimeOpenTo=='' && this.advancesearch.engName=='' &&
    this.advancesearch.siteManualID==''&& this.advancesearch.reasonName==''&&
    this.advancesearch.stage=='' && this.advancesearch.caseStatusName=='' &&
     this.advancesearch.caseTypeName==''){
     this.message="Please Enter Valid Key For Search";
     this._global.alert({type:'alert',message:this.message});
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
      this.btcaseservice.getSearchResult(this.advancesearch,this.page).subscribe(data => {
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
        this._global.alert({type:'alert',message:this.message});
      });
    }
  
  }
  searchReset(){
    this.advancesearch.manualID=''
    this.advancesearch.sapCustomerNumber='' 
    this.advancesearch.assetsSerialNumber='' 
     this.advancesearch.dateTimeCloseFrom=''
    this.advancesearch.dateTimeCloseTo=''
    this.advancesearch.dateTimeOpenFrom=''
    this.advancesearch.dateTimeOpenTo=''
    this.advancesearch.engName=''
    this.advancesearch.siteManualID=''
     this.advancesearch.reasonName=''
    this.advancesearch.stage=''
     this.advancesearch.caseStatusName=''
     this.advancesearch.caseTypeName=''
    this.showCase();

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
    this.AllCase.number=this.pagenumber-1;
    if(pagenumber==null || pagenumber=='' || pagenumber==0){
      this.message="Please Enter A Valid Number";
      this._global.alert({type:'alert',message:this.message});
    
    }
    else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }

   
   else if(this.AllCase.totalPages< pagenumber){
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Then Total Page"});
    }
    else{
      this.dropdownSelected.page=pagenumber-1;
      this.spinner.show();
      this.AllCase.content=[];
      this.btcaseservice.getCase(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
        this.AllCase = data;
        this.spinner.hide();
      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({type:'alert',message:this.message});
      });
    }
  }
// else{
//   this.message="Please Enter A Valid Number";
//   this._global.alert({type:'alert',message:this.message});

// }
   
  getCaseTypeList(){
    this.caseTypeList=[];
    this.btcaseservice.getCaseTypeForlist().subscribe(data=>{
      const temp=[];
     for(let i=0;i<data.length;i++)
     {
     let lis=new BT_CaseTypeForList;
     lis.caseTypeName=data[i]
     temp.push(lis);
     this.caseTypeList=temp;
     }
    });
  }
  getCaseReson(){
    this.caseReasonForList=[];
    this.btcaseservice.getCaseReasonAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new BT_CaseReasonForList;
        lis.caseReasonName=data[j]
        // let first_word = data.split(",")[0];
        temp.push(lis);
        this.caseReasonForList=temp;
        this.newcaseReasonForList=temp;
      }
      
        

    })
  }
  caseType() {
    this.btcaseservice.getCaseTypes(1)
    .subscribe(data => {
      this.caseTypeList = data;
    });
  }
  getStatusForList(){
    this.btcaseservice.getCaseStatusAdvFORList().subscribe(data=>{
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
    this.btcaseservice.getCaseStageAdvFORList().subscribe(data=>{
      const temp=[];
      for(let j=0; j<data.length;j++){
        let lis=new BT_CaseStageDetails;
        lis.caseStageName=data[j]
        temp.push(lis);
        this.caseStageForList=temp;
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

  invoiceforpod(){

  
    this.caseListPOD=[];
    this.newcaseReasonForList = this.caseReasonForList;
    this.newcaseReasonForList  = [];
    this.caseTypeId="";
    this.caseforpodupdate.show();
  }
  cancelinvPOD(){
    this.allselect=false;
    this.casetypeid=0
    this.caseTypeId="";
    this.caseReason="";
    this.caseListPOD=[];
    this.caseforpodupdate.hide();
    
  }
  onCasePDCreated(){
    this.showCase();
    this.createPD.hide();
    this.caseforpodupdate.hide();

  }
  cancelPDData(){
    this.createPD.hide();
    // this.caseselectlist=[];
    this.caseListPOD.forEach(e=>{
      e.caseidselected=false;
    })
    
  }
  findCase(reason){
    console.log(reason);
    console.log(this.casetypeNameofpod);
 if (reason!=undefined && this.casetypeNameofpod!=undefined){
      this.spinner.show();
      this.btcaseservice.getCasebyTypereason(this.casetypeNameofpod,reason).subscribe(data=>{
        this.spinner.hide();
        this.caseListPOD=data;
        this.caseselectlist=[];
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      })
 }
 else{
  this._global.alert({type:'alert',message:'Please select case type OR Case Reason'});
 }

  }
  submittopod(){
    if(this.caseselectlist.length==0){
      this._global.alert({type:'alert',message:"Please choose atleast one case"});
    }else
      {
         this.createPD.show();
         }
 
   
   }

   recalculateall(event){
    this.caseselectlist=[];
    this.allselect=true;
    // const index = this.caseListPOD.indexOf(event);
     if(event.target.checked==true)
     { this.caseListPOD.forEach(el=>{
        
        el.caseidselected= true;
        this.caseselectlist.push(el.id);
      
      })}
      else{
        this.allselect=false;
        this.caseselectlist=[];
        this.caseListPOD.forEach(el=>{
        
          el.caseidselected= false;
          // this.caseselectlist.splice(el.id);
        
        })
      }
   }
   recalculate($event,sa:BTCASEPODLIST){
    this.allselect=false;
    const index = this.caseListPOD.indexOf(sa);
    if(sa.caseidselected==true){
      this.caseselectlist.push(sa.id);
    }
    else{
      this.caseselectlist.splice(index,sa.id);
     }
  
  }
  backclik(ids){
    this.caseselectlist=[]
     ids.forEach(e=>{
     const templist=[]
      this.caseListPOD.forEach(el=>{
        
        if(e==el.id)
        el.caseidselected= true;
        // el.id=e
      
      })
      // this.caseselectlist=templist;
      this.caseselectlist.push(e);
    })
    
   
    this.createPD.hide();
  }
 
  getCasereasonchange(caseTypeId) {
    console.log(caseTypeId);
  
    // this.res.caseTypeName=
    if(caseTypeId==1){
      this.casetypeNameofpod="Exceptional Case";
      this.getCaseReason(caseTypeId);
      return;
     
    }
    else if(caseTypeId==2){
      this.casetypeNameofpod="Installation";
      this.getCaseReason(caseTypeId);
      return;
    }
    else if(caseTypeId==3){
    this.casetypeNameofpod="Under Warranty";
    this.getCaseReason(caseTypeId);
    return;
    }
    else if(caseTypeId==4){
      this.casetypeNameofpod="Without Warranty";
      this.getCaseReason(caseTypeId);
    }
    else if(caseTypeId==11){
      this.casetypeNameofpod="Manpower";
      this.getCaseReason(caseTypeId);
      return;
    }
    console.log( this.casetypeNameofpod);
    
    
 }

  getCaseReason(caseTypeId) {

    this.spinner.show();
    this.btcaseservice.getCaseReason(caseTypeId)
    .subscribe((data) => {
      if (data != null) {
        this.spinner.hide();
      }
      this.caseReasonForList = data;
      this.newcaseReasonForList = data;
        if (caseTypeId === 1) {
          this.caseReason = "Audit";
          return;
        }
        if (caseTypeId === 2) { 
          this.caseReason = "Installation";
         
          return;
        }
        if (caseTypeId === 3) {
        
          this.caseReason = "Corrective Maintenance"
          return;
        }
        if (caseTypeId=== 4) {
          this.caseReason = "Corrective Maintenance";
          return;
        }
        if(caseTypeId==11){
        this.caseReason=  "Manpower"
        }
      
    }, error => {
      this.spinner.hide();
    });
  }

}



