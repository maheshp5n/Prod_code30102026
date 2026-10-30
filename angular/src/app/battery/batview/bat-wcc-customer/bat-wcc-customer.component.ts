import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
// import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { CaseReasonForList, CaseStageDetails, CaseStatus, CaseTypeForList, CustomerForList } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtCommercialService } from '../../bat_services/bt-commercial.service';
import { BTCaseResponse, BtCustomerWccResp, Page } from '../../bt_model';
import * as fileSaver from 'file-saver';
import { BtWccCustomerService } from '../../bat_services/bt-wcc-customer.service';

@Component({
  selector: 'app-bat-wcc-customer',
  templateUrl: './bat-wcc-customer.component.html',
  styleUrls: ['./bat-wcc-customer.component.css']
})
export class BatWccCustomerComponent implements OnInit {

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('choosenFiles', { static: false }) choosenFiles: ModalDirective;
  AllCase: Page<BTCaseResponse> = new Page<BTCaseResponse>()
  AllBtWccCustomer :Page<BtCustomerWccResp>=new Page<BtCustomerWccResp>();
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
  // uploadForm: FormGroup;
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
      name: 'Invoice Request Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Invoice Request Status',
      sortColumnName: 'st.status',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'JMS Number',
      sortColumnName: 'wcc.jms_no',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Circle Name',
      sortColumnName: 'wcc.circle_circle_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Case Reason',
      sortColumnName: 'wcc.case_reason_details_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Case Type',
      sortColumnName: 'wcc.case_types_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name:'Customer',
      sortColumnName:'customer',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Total Basic Amount',
       sortColumnName: 'closeDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Approval Date',
      sortColumnName: 'approval_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Download Excel'
    }


  ];
  advancesearch = {
    manualID: '',
    jmsNumber: '',
    circle: '',
    serviceCenter: '',
    reasonName: '',
    caseType: '',
    customer: '',
    status: '',
  };
  //done
  _cFlag: string;
  constructor(private pagefilterservice: PagefilterService, 
    // private formBuilder: FormBuilder,
    private _global: GlobalserviceService, private datePipe: DatePipe,
    private _btcaseService: BtCaseserviceService,private btwccCustomerService:BtWccCustomerService, private loginservice: Loginservices, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute
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
    this.AllBtWccCustomer.content=[];
    // this.getStatusForList();
    // this.getStageForList();
    // this.CaseByStage(this.sortColumnName, this.sortOrder,this.toBeInvoiced);
    // this.uploadForm = this.formBuilder.group({
    //   profile: ['']
    // });
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_wcc_customer').subscribe(data => {
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


      this.AllWccCust(this.dropdownSelected.filtreparam);
    });
  }
  // dropdownChange(sqId) {
  //   this.dropdownsList.forEach(item => {
  //     if (item.sqId == sqId) {
  //       item.class = 'dropdown-item dropdown-item-checked';
  //       this.dropdownSelected.sqId = sqId;
  //       this.dropdownSelected.name = item.name;
  //       this.dropdownSelected.filtreparam = item.clickEventParam;
  //       this.dropdownSelected.page = 0;
  //       if (item.name == 'Closed Cases') {
  //         this.CaseByStatus(item.clickEventParam);
  //       }
  //       else if (item.name == 'Open Cases') {
  //         this.CaseByStatus(item.clickEventParam);
  //       }

  //       else if (item.name == 'Document Awaited') {
  //         this.CaseByStage(item.clickEventParam);
  //       }
  //       else {
  //         this.CaseByStage(item.clickEventParam);
  //       }
  //       if (item.pined) {
  //         this.pindtselection = true;
  //       } else {
  //         this.pindtselection = false;
  //       }
  //     } else {
  //       item.class = 'dropdown-item';
  //     }
  //   });
  // }
  getSortOrder(tblId) {
    if(tblId!=0){
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
        this.dropdownSelected.page = 0;
        this.AllWccCust(this.dropdownSelected.filtreparam);

      } else {
        tbl.icon = '';
      }
    });
  }
  }
  // CaseByStatus(status: string) {
  //   this.spinner.show();
  //   this.AllBtWccCustomer.content = [];
  //   this.btwccCustomerService.getAllWccCustomer(status, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
  //     this.AllBtWccCustomer = data;
  //     this.spinner.hide();
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.error.message;
  //     this.popupSuccessModel.show();
  //   })
  // }
  AllWccCust(stage: string) {
    this.spinner.show();
    this.AllBtWccCustomer.content = [];
    this.btwccCustomerService.getAllWccCustomer(`page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.AllBtWccCustomer = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass == 'alert alert-success center center-block') {
  //     if (this.dropdownsList[0].name == 'Closed Cases') {
  //       this.CaseByStatus(this.dropdownSelected.filtreparam);
  //     }
  //     else if (this.dropdownsList[0].name == 'Open Cases') {
  //       this.CaseByStatus(this.dropdownSelected.filtreparam);
  //     }

  //     else if (this.dropdownsList[0].name == 'Document Awaited') {
  //       this.CaseByStage(this.dropdownSelected.filtreparam);
  //     }
  //     else {
  //       this.CaseByStage(this.dropdownSelected.filtreparam);
  //     }
  //   }
  // }
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

  // onFileSelect(event) {
  //   this.getFile = event.target.files.id;
  //   this.filyType = event.target.id;
  //   if (event.target.files.length > 0) {
  //     this.choosenFiles.show();
  //     for (let i = 0; i < event.target.files.length; i++) {
  //       this.fileList.push(event.target.files[i]);
  //       this.fileToUpload = event.target.files[i];
  //     }
  //   }
  // }
  // onSubmit() {
  //   this.spinner.show();
  //   for (let i = 0; i < this.fileList.length; i++) {
  //     this.formData.append('files', this.fileList[i]);
  //   }
  //   if (this.filyType === 'getFile') {
  //     this._btComercialService.uploadFiles(this.fileToUpload).subscribe(data => {
  //       this.spinner.hide();
  //       this.choosenFiles.hide();
  //       this.downloadFile(data);
  //       this.popupNgClass = 'alert alert-success center center-block';
  //       this.fileList = [];

  //       (<HTMLInputElement>document.getElementById('getFile')).value = null
  //       this.message = 'Updated Successfully';
  //       this.popupSuccessModel.show();
  //     }, error => {
  //       this.spinner.hide();
  //       this.message = error.message;
  //       this._global.alert({ type: 'alert', message: this.message });
  //     });
  //     this.formData = new FormData();
  //     this.getFile = '';
  //   }
  //   if (this.filyType === 'docrecievedFile') {
  //     // get Doc Recieved bul upload
  //     this._btComercialService.uploadDocRecievdFiles(this.fileToUpload).subscribe(data => {
  //       this.spinner.hide();
  //       this.choosenFiles.hide();
  //       this.downloadFile(data);
  //       this.popupNgClass = 'alert alert-success center center-block';
  //       this.fileList = [];

  //       (<HTMLInputElement>document.getElementById('docrecievedFile')).value = null
  //       this.message = 'Updated Successfully';
  //       this.popupSuccessModel.show();
  //     }, error => {
  //       this.spinner.hide();
  //       this.message = error.message;
  //       this._global.alert({ type: 'alert', message: this.message });
  //     });
  //     this.formData = new FormData();
  //     this.getFile = '';

  //   }
  //   else {
  //     this._btcaseService.uploadchangeCaseTypeFiles(this.fileToUpload).subscribe(data => {
  //       this.spinner.hide();
  //       this.choosenFiles.hide();
  //       this.downloadFile(data);
  //       this.popupNgClass = 'alert alert-success center center-block';
  //       this.fileList = [];

  //       (<HTMLInputElement>document.getElementById('getFile')).value = null
  //       this.message = 'Updated Successfully';
  //       this.popupSuccessModel.show();
  //     }, error => {
  //       this.spinner.hide();
  //       this.message = error.message;
  //       this._global.alert({ type: 'alert', message: this.message });
  //     });
  //     this.formData = new FormData();
  //     this.getFile = '';
  //   }
  // }

  // downloadFile(data) {
  //   var file = new Blob([data], {
  //     type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  //   });
  //   fileSaver.saveAs(file, "Report" + ".xlsx");
  // }
  // cancelChoosen() {

  //   (<HTMLInputElement>document.getElementById('getFile')).value = null
  //   this.choosenFiles.hide();
  //   this.formData = new FormData();
  //   this.fileList = [];
  //   this.getFile = '';
  // }
  // getTemplateExcel() {
  //   this.spinner.show();
  //   this._btComercialService.getExcelTemplate().subscribe(data => {
  //     this.spinner.hide();
  //     var file = new Blob([data], {
  //       type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  //     });
  //     fileSaver.saveAs(file, "InvoiceTemplate" + ".xlsx");
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.message;
  //     this.popupSuccessModel.show();
  //   })
  // }

  // getTemplateExcelforcaseTypechang() {
  //   this.spinner.show();
  //   this._btcaseService.getExcelTemplateforhangeCaseType().subscribe(data => {
  //     this.spinner.hide();
  //     var file = new Blob([data], {
  //       type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  //     });
  //     fileSaver.saveAs(file, "ChangeCaseTypeTemplate" + '.xlsx');
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.message;
  //     this.popupSuccessModel.show();
  //   });
  // }
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
      // if (this.dropdownsList[0].name == 'Closed Cases') {
      //   this.CaseByStatus(this.dropdownSelected.filtreparam);
      // }
      // else if (this.dropdownsList[0].name == 'Open Cases') {
      //   this.CaseByStatus(this.dropdownSelected.filtreparam);
      // }

      // else if (this.dropdownsList[0].name == 'Document Awaited') {
        this.AllWccCust(this.dropdownSelected.filtreparam);
      // }
      // else {
      //   this.CaseByStage(this.dropdownSelected.filtreparam);
      // }

    }
  }
  searchReset() {
    this.advancesearch.caseType = ''
    this.advancesearch.circle = ''
    this.advancesearch.reasonName = ''
    this.advancesearch.customer = ''
    this.advancesearch.jmsNumber = ''
    this.advancesearch.manualID = ''
    // this.advancesearch.account= ''
    this.advancesearch.serviceCenter = ''
    this.advancesearch.status = ''

    this.AllWccCust(this.dropdownSelected.filtreparam);

  }

  searchCase() {
    if (this.advancesearch.manualID == '' &&
      this.advancesearch.caseType == '' &&
      this.advancesearch.circle == '' &&
      this.advancesearch.customer == '' &&
      this.advancesearch.reasonName == '' &&
      this.advancesearch.jmsNumber == '' &&
      this.advancesearch.serviceCenter == '' &&
      this.advancesearch.status == '') {
      this.message = "Please Enter valid Key for Search";
      this._global.alert({ type: 'alert', message: this.message });
    }
    else {
      this.spinner.show();
      // if (this.advancesearch.dateTimeOpenFrom != '') {
      //   this.advancesearch.dateTimeOpenFrom = this.datePipe.transform(this.advancesearch.dateTimeOpenFrom, 'yyyy-MM-dd');
      // }
      // if (this.advancesearch.dateTimeOpenTo != '') {
      //   this.advancesearch.dateTimeOpenTo = this.datePipe.transform(this.advancesearch.dateTimeOpenTo, 'yyyy-MM-dd');
      // }
      // if (this.advancesearch.dateTimeCloseTo != '') {
      //   this.advancesearch.dateTimeCloseTo = this.datePipe.transform(this.advancesearch.dateTimeCloseTo, 'yyyy-MM-dd');
      // }
      // if (this.advancesearch.dateTimeCloseFrom != '') {
      //   this.advancesearch.dateTimeCloseFrom = this.datePipe.transform(this.advancesearch.dateTimeCloseFrom, 'yyyy-MM-dd');
      // }
      this.btwccCustomerService.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.AllBtWccCustomer.content = data;
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

  // getTemplateExcelforducRecieve() {
  //   this._btcaseService.getExcelTemplateforDocRecieved().subscribe(data => {
  //     // this.downloadFile(data);
  //     var file = new Blob([data], {
  //       type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  //     });
  //     fileSaver.saveAs(file, 'Document_recieved' + '.xlsx');
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.message;
  //     this.popupSuccessModel.show();
  //   });
  // }
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
  // getStatusForList() {
  //   this._btcaseService.getCaseStatusAdvFORList().subscribe(data => {
  //     const temp = [];
  //     for (let j = 0; j < data.length; j++) {
  //       let lis = new CaseStatus;
  //       lis.caseStatusName = data[j]
  //       temp.push(lis);
  //       this.caseStatusForList = temp;
  //     }
  //   })
  // }
  // getStageForList() {
  //   this._btcaseService.getCaseStageAdvFORList().subscribe(data => {
  //     const temp = [];
  //     for (let j = 0; j < data.length; j++) {
  //       let lis = new CaseStageDetails;
  //       lis.caseStageName = data[j]
  //       temp.push(lis);
  //       this.caseStageForList = temp;
  //     }
  //   })
  // }
  // customerforList: Array<CustomerForList> = [];
  // getCustomerForList() {
  //   this._btcaseService.getCaseCustomerAdvFORList().subscribe(data => {
  //     const temp = [];
  //     for (let i = 0; i < data.length; i++) {
  //       let lis = new CustomerForList;
  //       lis.customerName = data[i];
  //       temp.push(lis);

  //     }
  //     this.customerforList = temp;
  //   })

  // }
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
  

  getTemplateExcel(id:number) {
    this.btwccCustomerService.getExcelTemplate(id).subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Customer_WCC" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:error.message});
    })
  }

}

