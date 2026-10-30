import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { CaseReasonForList, CaseResponse, CaseStageDetails, CaseStatus, CaseTypeForList, CustomerForList, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import * as fileSaver from 'file-saver';
import { BtCommercialService } from '../../bat_services/bt-commercial.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCaseResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-commercial',
  templateUrl: './bat-commercial.component.html',
  styleUrls: ['./bat-commercial.component.scss']
})
export class BatCommercialComponent implements OnInit {

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('choosenFiles', { static: false }) choosenFiles: ModalDirective;
  AllCase: Page<BTCaseResponse> = new Page<BTCaseResponse>()
  searchitem: string = '';
  datainput: boolean = false
  ngClassTable: string = 'fa fa-check';
  values: string;
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create city ';
  EDIT_ACCOUNT_HEADING: string = 'Edit city '
  order: string = 'cityName'; // first column variable
  reverse: boolean = false;
  page: number = 0;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  showFilter: boolean = false;
  i: any
  message: string = "";
  ngClassQueteSent: string;
  ngClassToBeInvoice: string = 'dropdown-item dropdown-item-checked';
  ngClassWorkOrder: string;
  ngClassInvoiced: string;
  ngClassDocAwaited: string;
  toBeInvoiced: string = 'To Be Invoiced';
  workOrderRecieved = 'Work Order Received';
  queteSent = 'Quote Sent';
  invoiced: string = 'Invoiced';
  ngClassdocInTransit: string;
  docAwaitView: boolean = false;
  docAwaitStatus: string = 'Document Awaited - Customer';
  docInTransitStatus: string = 'Document in-Transit';
  invoicedView: boolean = false;
  queteSentView: boolean = false;
  toBeInvoiceView: boolean = true;
  workOrderView: boolean = false;
  docInTransitView: boolean = false;
  open: boolean = false;
  close: boolean = false;
  popupNgClass: string = 'alert alert-danger center center-block';
  caseId: any;
  pindtselection = false;
  uploadForm: FormGroup;
  fileList: any[] = [];
  getFile
  filyType: string;
  fileToUpload: File;
  formData = new FormData();
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  caseReasonForList: Array<CaseReasonForList> = [];
  caseTypeList: Array<CaseTypeForList> = [];
  caseStatusForList: Array<CaseStatus> = [];
  caseStageForList: Array<CaseStageDetails> = [];
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
    sqId: 2,
    name: 'Quote Sent',
    filtreparam: 'QuoteSent',
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
      sortColumnName: 'caseId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Site Id',
      sortColumnName: 'ast.site.siteId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Asset Serial Number',
      sortColumnName: 'ast.serialNumber',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Type',
      sortColumnName: 'caseType.caseTypesName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Case Reason',
      sortColumnName: 'caseReason.caseReasonDetailsName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Status',
      sortColumnName: 'status.caseStatusName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Stage',
      sortColumnName: 'stage.caseStageName',
      icon: 'fa fa-angle-down pull-right'
    },

    {
      id: 8,
      name: 'Date/Time Opened',
      sortColumnName: 'createDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Date/Time Closed',
      sortColumnName: 'closeDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Service Resource',
      sortColumnName: 'engineer.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Vendor Name',
      sortColumnName: 'engact.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      name: 'Customer',
      sortColumnName: 'site.customer',
      icon: 'fa fa-angle-down pull-right'
    }


  ];
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
  //done
  _cFlag: string;
  constructor(private pagefilterservice: PagefilterService, private formBuilder: FormBuilder,
    private _global: GlobalserviceService, private datePipe: DatePipe,
    private orderPipe: OrderPipe, private _btcaseService: BtCaseserviceService, private loginservice: Loginservices, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute, private _btComercialService: BtCommercialService
  ) {
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
    // this.AllCase = orderPipe.transform(this.AllCase, this.order);
  }

  ngOnInit() {
    this.getCaseTypeList();
    this.getCaseReson();
    this.getStatusForList();
    this.getStageForList();
    // this.CaseByStage(this.sortColumnName, this.sortOrder,this.toBeInvoiced);
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_commercial').subscribe(data => {
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


      this.CaseByStage(this.dropdownSelected.filtreparam);
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
        if (item.name == 'Closed Cases') {
          this.CaseByStatus(item.clickEventParam);
        }
        else if (item.name == 'Open Cases') {
          this.CaseByStatus(item.clickEventParam);
        }

        else if (item.name == 'Document Awaited') {
          this.CaseByStage(item.clickEventParam);
        }
        else {
          this.CaseByStage(item.clickEventParam);
        }
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
        this.CaseByStage(this.dropdownSelected.filtreparam);

      } else {
        tbl.icon = '';
      }
    });
  }
  CaseByStatus(status: string) {
    this.spinner.show();
    this.AllCase.content = [];
    this._btcaseService.getCaseByStatus(status, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  CaseByStage(stage: string) {
    this.spinner.show();
    this.AllCase.content = [];
    this._btcaseService.getCaseByStage(stage, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllCase = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      if (this.dropdownsList[0].name == 'Closed Cases') {
        this.CaseByStatus(this.dropdownSelected.filtreparam);
      }
      else if (this.dropdownsList[0].name == 'Open Cases') {
        this.CaseByStatus(this.dropdownSelected.filtreparam);
      }

      else if (this.dropdownsList[0].name == 'Document Awaited') {
        this.CaseByStage(this.dropdownSelected.filtreparam);
      }
      else {
        this.CaseByStage(this.dropdownSelected.filtreparam);
      }
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


  // Bulk upload commercial

  onFileSelect(event) {
    this.getFile = event.target.files.id;
    this.filyType = event.target.id;
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
      }
    }
  }
  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
    }
    if (this.filyType === 'getFile') {
      this._btComercialService.uploadFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];

        (<HTMLInputElement>document.getElementById('getFile')).value = null
        this.message = 'Updated Successfully';
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
      this.formData = new FormData();
      this.getFile = '';
    }
    if (this.filyType === 'docrecievedFile') {
      // get Doc Recieved bul upload
      this._btComercialService.uploadDocRecievdFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];

        (<HTMLInputElement>document.getElementById('docrecievedFile')).value = null
        this.message = 'Updated Successfully';
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
      this.formData = new FormData();
      this.getFile = '';

    }
    else {
      this._btcaseService.uploadchangeCaseTypeFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];

        (<HTMLInputElement>document.getElementById('getFile')).value = null
        this.message = 'Updated Successfully';
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
      this.formData = new FormData();
      this.getFile = '';
    }
  }

  downloadFile(data) {
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "Report" + ".xlsx");
  }
  cancelChoosen() {

    (<HTMLInputElement>document.getElementById('getFile')).value = null
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = '';
  }
  getTemplateExcel() {
    this.spinner.show();
    this._btComercialService.getExcelTemplate().subscribe(data => {
      this.spinner.hide();
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "InvoiceTemplate" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this.popupSuccessModel.show();
    })
  }

  getTemplateExcelforcaseTypechang() {
    this.spinner.show();
    this._btcaseService.getExcelTemplateforhangeCaseType().subscribe(data => {
      this.spinner.hide();
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "ChangeCaseTypeTemplate" + '.xlsx');
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this.popupSuccessModel.show();
    });
  }
  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == "") {
      this._global.alert({ type: 'alert', message: "Enter Valid Input" });
    }
    if (this.AllCase.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Enter Valid Input" });
    }
    else {

      this.dropdownSelected.page = pagenumber - 1;
      this.spinner.show();
      if (this.dropdownsList[0].name == 'Closed Cases') {
        this.CaseByStatus(this.dropdownSelected.filtreparam);
      }
      else if (this.dropdownsList[0].name == 'Open Cases') {
        this.CaseByStatus(this.dropdownSelected.filtreparam);
      }

      else if (this.dropdownsList[0].name == 'Document Awaited') {
        this.CaseByStage(this.dropdownSelected.filtreparam);
      }
      else {
        this.CaseByStage(this.dropdownSelected.filtreparam);
      }

    }
  }
  searchReset() {
    this.advancesearch.assetsSerialNumber = ''
    this.advancesearch.caseTypeName = ''
    this.advancesearch.reasonName = ''
    this.advancesearch.caseStatusName = ''
    this.advancesearch.stage = ''
    this.advancesearch.engName = ''
    // this.advancesearch.account= ''
    this.advancesearch.dateTimeOpenFrom = ''
    this.advancesearch.dateTimeOpenTo = ''
    this.advancesearch.dateTimeCloseTo = ''
    this.advancesearch.dateTimeCloseFrom = ''
    this.advancesearch.manualID = ''
    this.advancesearch.sapCustomerNumber = ''
    this.CaseByStage(this.dropdownSelected.filtreparam);

  }

  searchCase() {
    if (this.advancesearch.manualID == '' &&
      this.advancesearch.siteManualID == '' &&
      this.advancesearch.assetsSerialNumber == '' &&
      this.advancesearch.caseTypeName == '' &&
      this.advancesearch.reasonName == '' &&
      this.advancesearch.caseStatusName == '' &&
      this.advancesearch.stage == '' &&
      this.advancesearch.engName == '' &&
      // this.advancesearch.account== ''&&
      this.advancesearch.dateTimeOpenFrom == '' &&
      this.advancesearch.dateTimeOpenTo == '' &&
      this.advancesearch.dateTimeCloseTo == '' &&
      this.advancesearch.dateTimeCloseFrom == '' && this.advancesearch.sapCustomerNumber == '') {
      this.message = "Please Enter valid Key for Search";
      this._global.alert({ type: 'alert', message: this.message });
    }
    else {
      this.spinner.show();
      if (this.advancesearch.dateTimeOpenFrom != '') {
        this.advancesearch.dateTimeOpenFrom = this.datePipe.transform(this.advancesearch.dateTimeOpenFrom, 'yyyy-MM-dd');
      }
      if (this.advancesearch.dateTimeOpenTo != '') {
        this.advancesearch.dateTimeOpenTo = this.datePipe.transform(this.advancesearch.dateTimeOpenTo, 'yyyy-MM-dd');
      }
      if (this.advancesearch.dateTimeCloseTo != '') {
        this.advancesearch.dateTimeCloseTo = this.datePipe.transform(this.advancesearch.dateTimeCloseTo, 'yyyy-MM-dd');
      }
      if (this.advancesearch.dateTimeCloseFrom != '') {
        this.advancesearch.dateTimeCloseFrom = this.datePipe.transform(this.advancesearch.dateTimeCloseFrom, 'yyyy-MM-dd');
      }
      console.log(this.advancesearch);
      
      this._btcaseService.getSearchResult(this.advancesearch,0).subscribe(data => {
        this.spinner.hide();
        this.AllCase.content = data;
        this.page = 0;
        this.size = data.length;
        this.first = true;
        this.last = true;
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
  }

  getTemplateExcelforducRecieve() {
    this._btcaseService.getExcelTemplateforDocRecieved().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, 'Document_recieved' + '.xlsx');
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this.popupSuccessModel.show();
    });
  }
  getCaseTypeList() {
    this.caseTypeList = [];
    this._btcaseService.getCaseTypeForlist().subscribe(data => {
      const temp = [];
      for (let i = 0; i < data.length; i++) {
        let lis = new CaseTypeForList;
        lis.caseTypeName = data[i]
        temp.push(lis);
        this.caseTypeList = temp;
      }
    });
  }
  getCaseReson() {
    this.caseReasonForList = [];
    this._btcaseService.getCaseReasonAdvFORList().subscribe(data => {
      const temp = [];
      for (let j = 0; j < data.length; j++) {
        let lis = new CaseReasonForList;
        lis.caseReasonName = data[j]
        temp.push(lis);
        this.caseReasonForList = temp;
      }



    })
  }
  getStatusForList() {
    this._btcaseService.getCaseStatusAdvFORList().subscribe(data => {
      const temp = [];
      for (let j = 0; j < data.length; j++) {
        let lis = new CaseStatus;
        lis.caseStatusName = data[j]
        temp.push(lis);
        this.caseStatusForList = temp;
      }
    })
  }
  getStageForList() {
    this._btcaseService.getCaseStageAdvFORList().subscribe(data => {
      const temp = [];
      for (let j = 0; j < data.length; j++) {
        let lis = new CaseStageDetails;
        lis.caseStageName = data[j]
        temp.push(lis);
        this.caseStageForList = temp;
      }
    })
  }
  customerforList: Array<CustomerForList> = [];
  getCustomerForList() {
    this._btcaseService.getCaseCustomerAdvFORList().subscribe(data => {
      const temp = [];
      for (let i = 0; i < data.length; i++) {
        let lis = new CustomerForList;
        lis.customerName = data[i];
        temp.push(lis);

      }
      this.customerforList = temp;
    })

  }
  LimteExceed:boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(this.LimteExceed==true){
      return false;
    }
    return true;

  }
  



}
