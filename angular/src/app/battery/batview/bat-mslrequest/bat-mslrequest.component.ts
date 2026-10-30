import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LegalEntityResponse, Login, MSLItemRequestRequest, MSLRequestResponse, MSLRequestStatus, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtMslrequestService } from '../../bat_services/bt-mslrequest.service';

@Component({
  selector: 'app-bat-mslrequest',
  templateUrl: './bat-mslrequest.component.html',
  styleUrls: ['./bat-mslrequest.component.scss']
})
export class BatMslrequestComponent implements OnInit {

  @ViewChild('createRequestModel', { static: false }) createRequestModel: ModalDirective;

  message: string = ""
  recentlyView: boolean = false;
  MSLTable: Page<MSLRequestResponse>=new Page<MSLRequestResponse>()
  isSelected = false;
  dataStyle: string;
  ngClassAll: string = 'fa fa-check';
  related: boolean = false;
  ngClassKanban: string;
  ngClassTable: string = 'fa fa-check';
  modalMsg: string;
  ngClassRelated: string;
  inboundClick: boolean = true;
  showHide: boolean = false;
  showFilter: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_MSL_HEADING: string = 'Create MSL Request';
  EDIT_MSL_HEADING: string = 'Edit MSL Request'
  tabledata: Array<LegalEntityResponse>
  inputrow: boolean = false;
  legalentityId: number;
  accountOwnername: Login = new Login();
  _login: Login = new Login();
  values: string;
  mslreq: Array<MSLRequestStatus>
  req: LegalEntityResponse = new LegalEntityResponse();
  res: MSLRequestResponse = new MSLRequestResponse();
  contactPage: Page<MSLRequestResponse> = new Page<MSLRequestResponse>();
  page: number = 0;
  searchitem: string
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  loginData: any;
  order: string = 'legalENtityName';
  reverse: boolean = false;
  recentUser: boolean = false;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 4;
  columnVisibility: Array<boolean> = [false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  i: number;
  MSLItem: MSLItemRequestRequest = new MSLItemRequestRequest()
  productsList: Array<MSLItemRequestRequest> = [];
  MSLTableData: Array<MSLRequestResponse>
  _cFlag: string;
  pindtselection = false;
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
    clickEventParam: 'recentvisit',
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
      name: ' Name',
      sortColumnName: 'manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Warehouse',
      sortColumnName: 'warehouse',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Status',
      sortColumnName: 'mslRequestStatus',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];

  //done
  constructor(private spinner: NgxSpinnerService, private orderPipe: OrderPipe,
    private _activatedRoute: ActivatedRoute,private _global: GlobalserviceService,private pagefilterservice:PagefilterService,
    private _btMslRequest : BtMslrequestService) {
    // this.MSLTable = orderPipe.transform(this.tabledata, this.order);
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

  ngOnInit() {
  }
  getMSLRecordTable() {
    this.spinner.show()
    this.MSLTable.content=[];
    this._btMslRequest.getMslTable(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide()
      this.MSLTable = data
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
    })
  }
  recentMsl() {
    this.spinner.show();
    this.MSLTable.content=[];
    this._btMslRequest.getRecentVisit(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      // this.contactPage = data;
      this.MSLTable = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
    })
  }
 
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_mslrequest').subscribe(data => {
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
 
      this.getMSLRecordTable()
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
      //  if(this.dropdownSelected.filtreparam=='related'){
      //    this.recentMsl();
      //  }
      //  else
      // { 
        this.getMSLRecordTable();
      
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
    //    if(this.dropdownSelected.filtreparam=='related'){
    //     this.recentMsl();
    //   }
    //   else
    //  { 
       this.getMSLRecordTable();
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

  showCreateRequestView() {
    this.MSLItem = new MSLItemRequestRequest()
    this.res = new MSLRequestResponse()
    this.productsList = [];
    this.isEditMode = false;
    this.heading = this.CREATE_MSL_HEADING;
    this.createRequestModel.show();
  }


  onMSLCreated($event: MSLRequestResponse) {
    if (!this.isEditMode) {
      this.MSLTable.content.unshift($event);
    } else {
      let index = 0;
      this.MSLTable.content.forEach(element => {
        if (element.mslId == $event.mslId) {
          this.MSLTable[index] = $event;
        } else {
          index++;
        }
      });
    }

  }

  editMSLModelshow(res: MSLRequestResponse) {
    this.res = res
    this.createRequestModel.show();
    this.isEditMode = true;
    this.heading = this.EDIT_MSL_HEADING;
    this.spinner.show()
    this._btMslRequest.getMslById(res.mslId).subscribe(data => {
      this.spinner.hide();
      this.res = data;
      console.log(data);
    })


  }
  cancelData() {
    this.isEditMode = false;
    this.res = new MSLRequestResponse();
    this.MSLItem = new MSLItemRequestRequest();
    this.productsList = [];
    this.createRequestModel.hide();

  }
  advancesearch = {
    manualId: '',
    warehouse:'',
    status: ''
  
  };
  searchLoc() {
    
    if(this.advancesearch.manualId=='' && this.advancesearch.status=='' && this.advancesearch.warehouse==''){
     this.message="Please Enter Valid Key For Search";
     this._global.alert({type:'alert',message:this.message});
    }
    else{
      this.spinner.show();
      this._btMslRequest.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.MSLTable.content = data;
        this.page = 0;
        this.size = data.length;
        this.first = true;
        this.last = true;
      });
    }
  
  }
  reset(){
   this.advancesearch. manualId= ''
    this.advancesearch.warehouse=''
    this.advancesearch.status=''
    this.getMSLRecordTable();

  }

}
