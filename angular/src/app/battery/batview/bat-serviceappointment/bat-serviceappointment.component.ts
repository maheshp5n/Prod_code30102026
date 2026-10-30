import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { caseService } from '../../../services/caseService.service';
import { Loginservices } from '../../../services/login.services';
import { CaseReasonForList, CaseTypeForList, ClaimStatus, Page, SAAdvanceSearchData, SAStatus, ServiceAppointmentResponse } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { btServiceAppointmentResponseWithSCH } from '../../bt_model';

@Component({
  selector: 'app-bat-serviceappointment',
  templateUrl: './bat-serviceappointment.component.html',
  styleUrls: ['./bat-serviceappointment.component.scss']
})
export class BatServiceappointmentComponent implements OnInit {

  @ViewChild('createServiceAppointmentModel', { static: false }) createServiceAppointmentModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  //  advanceSearchResult: SAAdvanceSearchData = new SAAdvanceSearchData();
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  loginData;
  searchitem: string = '';
  recentlyView: boolean = false;
  related: boolean = false;

  allView: boolean = true;
  all: boolean = true;

  claimed: boolean = false;
  claimView: boolean = false;
  claimStatus: string = 'Claimed';

  postedInSapView: boolean = false;
  postedinsap: boolean = false;
  postedStatus: string = 'Posted in SAP';
  approvedView: boolean = false;
  approvedStatus: string = 'Approved';
  rejectView: boolean = false;
  rejectStatus: string = 'Rejected';

  tobeClaimView: boolean = false;
  tobeClaimed: boolean = false;
  tobeClaimedStatus: string = 'To be Claimed';

  open: boolean = false;
  openView: boolean = false;
  opneStatus: string = 'Open Service Appointments';

  closeView: boolean = false;
  close: boolean = false;
  closeStatus: string = 'Close Service Appointments';

  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  ngClassClaimed: string;
  ngClassClosed: string;
  ngClassOpen: string;
  ngClassPostinSap: string;
  ngClassTobeClaimed: string;
  ngClassApproved: string;
  ngClassRejected: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  caseReasonForList: Array<CaseReasonForList> = [];
  sastatusList: Array<SAStatus> = []
  claimstatuslist: Array<ClaimStatus> = [];
  caseTypeList: Array<CaseTypeForList> = [];
  CREATE_ACCOUNT_HEADING: string = 'New Service Appointment';
  EDIT_ACCOUNT_HEADING: string = 'Edit Service Appointment';

  page: number = 0;
  i: number;

  message: string = '';
  appointmentList: Page<ServiceAppointmentResponse> = new Page<ServiceAppointmentResponse>();
  appointmentListResponse: Page<ServiceAppointmentResponse> = new Page<ServiceAppointmentResponse>();
  serviceAppointmentResponse: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  popupNgClass: string = 'alert alert-danger center center-block';
  isModelShow: boolean = false;
  _cFlag: string;
  advanceSearchResult ={
    manualId : "",
    caseIdManual  : "",
    siteIdManual  : "",
    circleName  : "",
    assetsSerialNumber  : "",
    serviceAppointmentStatusName  : "",
    claimStatusName  : "",
    serviceResourceName  : "",
    caseType  :"",
    caseReason  :"",
    createdFrom  :"",
    createdTo : ""
  // show: boolean;
  }

  dropdownsList = [{
    sqId: 1,
    name: 'All ',
    clickEventParam: 'All',
    class: 'dropdown-item dropdown-item-checked',
    pined: false  
  },

  {
    sqId: 2,
    name: 'Recently viewed',
    clickEventParam: 'Recently viewed',
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
      name: 'Appointment Number',
      sortColumnName: 'service_appointment_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Site ID',
      sortColumnName: 'site.manual_id',
      icon: ''
    },
    {
      id: 6,
      name: 'Site Name',
      sortColumnName: 'site.site_name',
      icon: ''
    },
    {
      id: 9,
      name: 'Asset Serial Number',
      sortColumnName: 'assets.manual_id',
      icon: ''
    },
    {
      id: 3,
      name: 'Case Number',
      sortColumnName: 'ca.manual_id',
      icon: ''
    },
    {
      id: 4,
      name: 'Case Type',
      sortColumnName: 'ct.case_types_name',
      icon: ''
    },
    {
      id: 5,
      name: 'Case Reason',
      sortColumnName: 'crd.case_reason_details_name',
      icon: ''
    },

    {
      id: 7,
      name: 'Circle',
      sortColumnName: 'cd.circle_name',
      icon: ''
    },
    {
      id: 8,
      name: 'Contact',
      sortColumnName: 'contact.first_name',
      icon: ''
    },
    {
      id: 10,
      name: 'Status',
      sortColumnName: 'ser.status_name',
      icon: ''
    },
    {
      id: 11,
      name: 'Date Of Visit',
      sortColumnName: 'visit_date',
      icon: ''
    },
    {
      id: 12,
      name: 'Claim Status',
      sortColumnName: 'cstatus.claim_status_name',
      icon: ''
    },
    {
      id: 13,
      name: 'Service Resource',
      sortColumnName: 'serRes.name',
      icon: ''
    },

    {
      id: 14,
      name: 'Created Date',
      sortColumnName: 'create_date',
      icon: ''
    },
    {
      id: 15,
      name: 'Wcc Created',
      sortColumnName: 'wcc',
      icon: ''
    }
  ];
  pindtselection = false;
  constructor(private pagefilterservice: PagefilterService, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute, private _btserviceAppointment: BtServiceappointmentService, private loginService: Loginservices,
    private _global: GlobalserviceService, private datePipe: DatePipe,
    private case_service: caseService) {
    // this.advanceSearchResult.show = false;
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
    this.appointmentList.content = [];
    this.getCaseTypeList();
    this.getCaseReson();
    this.getStatusForList();
    this.getClaimStatusForList();
    // this.getAllServiceAppointMent(this.sortColumnName, this.sortOrder);
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_serviceappointment').subscribe(data => {
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

      this.getAllServiceAppointMent();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        console.log(this.dropdownSelected.filtreparam);
        this.dropdownSelected.page = 0;
        if (this.dropdownSelected.filtreparam == 'all') {
          this.getAllServiceAppointMent();
        }
        else if (this.dropdownSelected.filtreparam == 'related') {
          this.getRecentlyViewed();
        }
        else if (this.dropdownSelected.filtreparam == 'open') {
          this.getServiceAppointmentListByStatus();
        }
        else if (this.dropdownSelected.filtreparam == 'close') {
          this.getServiceAppointmentListByStatus();
        }
        else {
          this.getServiceAppointmentListByClaimStatus();
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
        if (this.dropdownSelected.filtreparam == 'all') {
          this.getAllServiceAppointMent();
        }
        else if (this.dropdownSelected.filtreparam == 'related') {
          this.getRecentlyViewed();
        }
        else if (this.dropdownSelected.filtreparam == 'open') {
          this.getServiceAppointmentListByStatus();
        }
        else if (this.dropdownSelected.filtreparam == 'close') {
          this.getServiceAppointmentListByStatus();
        }
        else {
          this.getServiceAppointmentListByClaimStatus();
        }
      } else {
        tbl.icon = '';
      }
    });
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

  getAllServiceAppointMent() {
    this.spinner.show();
    this.appointmentList.content = [];
    this._btserviceAppointment.getAllServiceAppointMent(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;
      this.appointmentListResponse = data;
  
      for(let i=0;i<this.appointmentList.content.length;i++)
      {

        if( this.appointmentList.content[i].visitDate==null ||  this.appointmentList.content[i].visitDate==undefined)
        {
          this.appointmentList.content[i].dateOfVisit='';
        }else{

          this.appointmentList.content[i].dateOfVisit = this.appointmentList.content[i].visitDate;
          let dateof = new String(this.appointmentList.content[i].dateOfVisit);
          this.appointmentList.content[i].dateOfVisit =  dateof.slice(0,10);
        }
       
       

      }

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }


  getRecentlyViewed() {
    this.spinner.show();
    this.appointmentList.content = [];
    this._btserviceAppointment.getRecentServiceAppointMent(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getServiceAppointmentListByClaimStatus() {
    this.spinner.show();
    this.appointmentList.content = [];
    this._btserviceAppointment.getServiceAppointmentListByClaimStatus(`claimstatus=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  getServiceAppointmentListByStatus() {
    this.spinner.show();
    this.appointmentList.content = [];
    this._btserviceAppointment.getServiceAppointmentListByStatus(`status=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      if (data != null) {
        this.appointmentList = data;
      }
      else {
        this.appointmentList.content = [];
      }


    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }



  showCreateServiceAppointmentView() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createServiceAppointmentModel.show();
  }

  createServiceAppointmentModelhide() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.createServiceAppointmentModel.hide();
  }

  createEditModelshow(servicAppointmentResponse: ServiceAppointmentResponse) {
    this.spinner.show();
    this._btserviceAppointment.getServiceAppointmentById(servicAppointmentResponse.serviceAppointmentId).subscribe(data => {
      this.serviceAppointmentResponse = data;
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
    this.createServiceAppointmentModel.show();
  }

  cancelData() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.createServiceAppointmentModel.hide();
  }




  onServiceAppointmentCreated($event: ServiceAppointmentResponse) {
    if (!this.isEditMode) {
      this.appointmentList.content.unshift($event);
    } else {
      let index = 0;
      this.appointmentList.content.forEach(element => {
        if (element.serviceAppointmentId === $event.serviceAppointmentId) {
          this.appointmentList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }
  resetsearch() {
    this.advanceSearchResult.assetsSerialNumber = '';
    this.advanceSearchResult.caseIdManual = '';
    this.advanceSearchResult.circleName = '';
    this.advanceSearchResult.createdFrom = '';
    this.advanceSearchResult.createdTo = '';
    this.advanceSearchResult.caseReason = '';  
    this.advanceSearchResult.caseType = '';
    this.advanceSearchResult.claimStatusName = '';
    this.advanceSearchResult.manualId ='';
    this.advanceSearchResult.siteIdManual = '';
    this.advanceSearchResult.serviceResourceName = '';
    this.advanceSearchResult.serviceAppointmentStatusName = '';
    this.getAllServiceAppointMent();
  }
  getAdvanceSearchResult() {
    if (this.advanceSearchResult.assetsSerialNumber == undefined) {
      this.advanceSearchResult.assetsSerialNumber = '';
    }
    if (this.advanceSearchResult.caseIdManual == undefined) {
      this.advanceSearchResult.caseIdManual = '';
    }
    if (this.advanceSearchResult.circleName == undefined) {
      this.advanceSearchResult.circleName = '';
    }
    if (this.advanceSearchResult.createdFrom == undefined) {
      this.advanceSearchResult.createdFrom = '';
    }
    if (this.advanceSearchResult.createdTo == undefined) {
      this.advanceSearchResult.createdTo = '';
    }

    if (this.advanceSearchResult.claimStatusName == undefined) {
      this.advanceSearchResult.claimStatusName = '';
    }
    if (this.advanceSearchResult.caseType == undefined) {
      this.advanceSearchResult.caseType = '';
    }
    if (this.advanceSearchResult.manualId == undefined) {
      this.advanceSearchResult.manualId = '';
    }
    if (this.advanceSearchResult.siteIdManual== undefined) {
      this.advanceSearchResult.siteIdManual = '';
    }
    if (this.advanceSearchResult.serviceResourceName == undefined) {
      this.advanceSearchResult.serviceResourceName = '';
    }
    if (this.advanceSearchResult.serviceAppointmentStatusName == undefined) {
      this.advanceSearchResult.serviceAppointmentStatusName = '';
    }

    if (this.advanceSearchResult.assetsSerialNumber == '' && this.advanceSearchResult.caseIdManual == '' &&
      this.advanceSearchResult.circleName == '' && this.advanceSearchResult.createdFrom == '' &&
      this.advanceSearchResult.createdTo == '' && this.advanceSearchResult.caseReason == '' &&
      this.advanceSearchResult.claimStatusName == '' && this.advanceSearchResult.caseType == '' &&
      this.advanceSearchResult.manualId == '' && this.advanceSearchResult.siteIdManual == '' &&
      this.advanceSearchResult.serviceResourceName == '' && this.advanceSearchResult.serviceAppointmentStatusName == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }


    else {
      if (this.advanceSearchResult.createdFrom != '') {
        this.advanceSearchResult.createdFrom = this.datePipe.transform(this.advanceSearchResult.createdFrom, 'yyyy-MM-dd');
      }

      if (this.advanceSearchResult.createdTo != '') {
        this.advanceSearchResult.createdTo = this.datePipe.transform(this.advanceSearchResult.createdTo, 'yyyy-MM-dd');

      }
      this.spinner.show();
      this._btserviceAppointment.getAdvanceSearchResult(this.advanceSearchResult).subscribe(data => {
        this.spinner.hide();
        this.appointmentList.content = data;
        this.appointmentList.first = true;
        this.appointmentList.last = true;

        for(let i=0;i<this.appointmentList.content.length;i++)
        {
  
          if( this.appointmentList.content[i].visitDate==null ||  this.appointmentList.content[i].visitDate==undefined)
          {
            this.appointmentList.content[i].dateOfVisit='';
          }else{
  
            this.appointmentList.content[i].dateOfVisit = this.appointmentList.content[i].visitDate;
            let dateof = new String(this.appointmentList.content[i].dateOfVisit);
            this.appointmentList.content[i].dateOfVisit =  dateof.slice(0,10);
          }
        }
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }

  }

  encodeurlc(urlc): string {
    return encodeURIComponent(urlc);
  }
  pagenumber: any = null;
  jumpPage(pagenumber) {

    let pageno = pagenumber;
    if(pageno.match(/[a-z]/i))
    {
      pagenumber=null;
      this.message="Enter Valid Input";
      this._global.alert({ type: 'alert', message: this.message });
    }
    
    if(pagenumber == null || pagenumber ==0){
      this._global.alert({type:'alert',message:"Please Enter Valid Input"});
     
    }
    else if (this.appointmentList.totalPages < pagenumber) {
      this._global.alert({type:'alert',message:"Page Count Should Be Less Then Total Page"});
    }else if(FieldValidation.isLetter(pagenumber))
    {
      this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
      pagenumber=null;
    }
    
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getAllServiceAppointMent()
      // this.serialNumber = (((this.dropdownSelected.page) * this.size));
    }
  }

  getCaseTypeList() {
    this.caseTypeList = [];
    this.case_service.getCaseTypeForlist().subscribe(data => {
      const temp = [];
      for (let i = 0; i < data.length; i++) {
        let lis = new CaseTypeForList();
        lis.caseTypeName = data[i]
        temp.push(lis);
        this.caseTypeList = temp;
      }
    });
  }
  getCaseReson() {
    this.caseReasonForList = [];
    this.case_service.getCaseReasonAdvFORList().subscribe(data => {
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
    this._btserviceAppointment.getStatusForList().subscribe(data => {
      const temp = []
      for (let j = 0; j < data.length; j++) {
        let lis = new SAStatus;
        lis.statusname = data[j]
        temp.push(lis);
        this.sastatusList = temp;
      }
    })
  }
  getClaimStatusForList() {
    this.claimstatuslist = [];
    this._btserviceAppointment.getClaimStatus().subscribe(data => {
      this.claimstatuslist = data;
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
