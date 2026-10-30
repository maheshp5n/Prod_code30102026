import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { Page, WccRequest, WCCResponse } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { WCCService } from '../../../services/wcc.services';
import { BtWccServiceService } from '../../bat_services/bt-wcc-service.service';

@Component({
  selector: 'app-bat-wcc',
  templateUrl: './bat-wcc.component.html',
  styleUrls: ['./bat-wcc.component.scss']
})
export class BatWccComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createWCCModel', { static: false }) createWCCModel: ModalDirective;

  @ViewChild('alertModal', { static: false }) alertModal: ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };


  message: string = '';
  // @ViewChild('agGrid') agGrid: AgGridAngular;
  popupNgClass: string = 'alert alert-danger center center-block';
  dataStyle: string;

  showHide: boolean = false;
  showFilter: boolean = false;
  sortDisplay: boolean = true;
  table: boolean = true;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create WCC ';
  EDIT_ACCOUNT_HEADING: string = 'Edit WCC '
  order: string = 'siteId'; // first column variable
  reverse: boolean = false;
  recentUser: boolean = false
  wccId: number = 1;
  editRowId: boolean = false;
  loginData: any
  searchitem: string;
  i: any
  isModelOpen: boolean = false;
  _cFlag: string;
  pindtselection = false;
  wccreq: WccRequest = new WccRequest();
  WccID: number;
  wccList: Page<WCCResponse> = new Page<WCCResponse>();
    //filter
    dropdownsList = [{
      sqId: 1,
      name: 'All',
      clickEventParam: '0',
      class: 'dropdown-item dropdown-item-checked',
      pined: false
    }
    ];
  
  
    dropdownSelected = {
      sqId: 0,
      name: 'All',
      filtreparam: '0',
      sortname: 'created_date',
      sortorder: 'desc',
      page: 0,
      size: 20,
      querypram: false
    };
    requestTableHeaders = [
      {
        id: 1,
        name: ' WCC',
        sortColumnName: 'id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Vendor Name',
        sortColumnName: 'act.name',
        icon: ''
      },
      {
        id: 3,
        name: 'Vendor Code',
        sortColumnName: 'cod.employee_code',
        icon: ''
      },
      {
        id: 4,
        name: 'Service Center',
        sortColumnName: 'lg.legal_entity_name',
        icon: ''
      },
      {
        id: 5,
        name: 'Wcc Type',
        sortColumnName: 'wt.wcc_type_name',
        icon: ''
      },
      {
        id: 6,
        name: 'Approval Status',
        sortColumnName: '',
        icon: ''
      },
      {
        id: 7,
        name: 'Created Date',
        sortColumnName: 'created_date',
        icon: ''
      },
      {
        id:8,
        name: 'Approved Date',
        sortColumnName: '',
        icon: ''
      },
      {
        id: 9,
        name: 'Invoiced',
        sortColumnName: 'vendor_invoice_number',
        icon: ''
      }
     
    ];
  
    //done
  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private btwccservice: BtWccServiceService, private _activatedRoute: ActivatedRoute, private loginservice: Loginservices, private orderPipe: OrderPipe,
    private _global : GlobalserviceService) {

    this._activatedRoute.params.subscribe(pram => {
      this.wccId = pram['wccid'];

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
    return this.loginservice.isUserValidFor(object, right);
  }
  
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_wcc').subscribe(data => {
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

      this.getWccList();
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

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getWccList();
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
        this.getWccList();
      } else {
        tbl.icon = '';
      }
    });
  }

  editWCCModelshow(wccid) {
    this.isEditMode = true;
    this.heading = "Edit WCC";
    this.createWCCModel.show();

    this.spinner.show();
    this.btwccservice.getWccById(wccid).subscribe(data => {
      this.spinner.hide();
      this.wccreq = new WccRequest();
      this.WccID = data.id;
      this.wccreq.circleName = data.circleName;
      this.wccreq.wccTypeId = data.wccTypeId;
      this.wccreq.startDate = data.fromDate;
      this.wccreq.toDate = data.toDate;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
 
  changeShowStatus() {

    if (this.showHide == true) {
      this.showHide = false;
    }
    else {
      this.showHide = true;
    }
  }
  
  getWccList() {
    this.spinner.show();
    this.wccList.content=[];
    this.btwccservice.getWccList(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
    this.spinner.hide();
      this.wccList = data;
    }, error => {
     this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })

  }


  ngOnInit() {

    this.getLoggedInUser();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }


  RowDelete(id: any) {

  }
  createShowModal() {
    // this.res=new SiteDetailsResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.wccreq = new WccRequest();
    this.createWCCModel.show();
  }

  cancelData() {
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createWCCModel.hide();
    this.wccreq = new WccRequest();

  }

  onWccCreated($event) {
    this.createWCCModel.hide();
    this.getWccList();
    this.showMessage('Created Successfully');
  }

  showAlert(message) {
    this.alertmeta.ngClass = 'alert alert-danger';
    this.alertmeta.message = message;
    this.alertModal.show();
  }
  showMessage(message) {
    this.alertmeta.ngClass = 'alert alert-success';
    this.alertmeta.message = message;
    this.alertModal.show();
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(pagenumber == null || pagenumber==0 || pagenumber == ""){
    this._global.alert({type:'alert',message:'Please Enter A Valid Input'});
  }
 else if(this.wccList.totalPages< pagenumber){
    this._global.alert({type:'alert',message:'Please Enter A Valid Input'});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getWccList();
}
}
advancesearch = {
  manualId: '',
  vendorName: '',
  vendorCode: '',
  serviceCenter: '',
  wccType: '',
  isInvoiced:''

};

searchwcc() {
  if(this.advancesearch.vendorCode=='' && this.advancesearch.serviceCenter==''
   && this.advancesearch.wccType=='' && this.advancesearch.manualId=='' &&this.advancesearch.vendorName==''&&
   this.advancesearch.isInvoiced==''){
   this._global.alert({type:'alert',message:'Please Enter A Valid Key For Search'});
  }
  else{
    this.spinner.show();
    this.btwccservice.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.wccList.content= data;
      this.wccList.first= true;
      this.wccList.last= true;
    },error=>{
      this.spinner.hide();    
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

}
ResetWCC(){
  this.advancesearch.manualId = '';
  this.advancesearch.vendorName = '';
  this.advancesearch.vendorCode = '';
  this.advancesearch.serviceCenter = '';
  this.advancesearch.wccType = '';
  this.getWccList();
  
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

}z

}
