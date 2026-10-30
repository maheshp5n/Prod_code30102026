import { QuoteService } from './../../services/Quote.services';
import { Page, QueteStatus, QuoteRequest, QuoteResponse } from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-Quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.css']
})

export class QuoteComponent implements OnInit {
  today = new Date();
dateCustomClasses: DatepickerDateCustomClasses[];
  dropdownsList = [{
    sqId: 1,
    name: 'All ',
    clickEventParam: 'All',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Draft',
    clickEventParam: 'Draft',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
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
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Quote Name',
      sortColumnName: 'quoteName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Quote Number',
      sortColumnName: 'quoteNumber',
      icon: ''
    },
    {
      id: 3,
      name: 'Account Name',
      sortColumnName: 'oppertunity.accounts.name',
      icon: ''
    },
    {
      id: 4,
      name: 'Opportunity Name',
      sortColumnName: 'oppertunity.oppertunityNumber',
      icon: ''
    },
    {
      id: 5,
      name: 'Validity Dates',
      sortColumnName: 'validityDays',
      icon: ''
    },
  
    {
      id: 6,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: ''
    },
    {
      id: 7,
      name: 'Expiry Date',
      sortColumnName: 'expiryDate',
      icon: ''
    }, {
      id: 8,
      name: 'Status',
      sortColumnName: 'quoteStatus.name',
      icon: ''
    }
  ];
  advanceSearch={
    quoteNumber:'',
    quoteName:'',
    accountName:'',
    oppertunityNumber:'',
    status:'',
    expiryDate:''
  }
  pindtselection = false;
  constructor(private pagefilterservice:PagefilterService,private _quoteService: QuoteService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private loginService: Loginservices,private _global:GlobalserviceService,private datepipe:DatePipe) {
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

  isEditMode: boolean = false;
  heading: string;
  CREATE_Quote_HEADING: string = 'New Quote';
  EDIT_Quote_HEADING: string = 'Edit Quote';
  isSelected = false;
  reverse: boolean = false;
  searchitem: string = '';
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  message: string;
  productId;
  page: number = 0;
  quoteRequest: QuoteRequest = new QuoteRequest();
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  quoteList: Page<QuoteResponse> = new Page<QuoteResponse>()
  isQuote: boolean;
  // ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  recently: boolean;
  recentlyView: boolean;
  _cFlag: string;

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createNewQuote', { static: false }) createNewQuote: ModalDirective;

  dropdownHeading: string = 'All';
  ngClassAll: string = 'dropdown-item';
  ngClassDraft: string = 'dropdown-item';
  ngClassWorkOrderReceived: string = 'dropdown-item';
  ngClassRejected: string = 'dropdown-item';
  ngClassQuoteSent: string = 'dropdown-item';
  ngClassWorkOrderNotReceived: string = 'dropdown-item';
  ngClassSubmittedForApproval: string = 'dropdown-item';
  ngClassApproved: string = 'dropdown-item';
  ngClassInvoiced: string = 'dropdown-item';
  ngClassWithdrawn: string = 'dropdown-item';
  ngClassApprovedbyStore: string = 'dropdown-item';
  ngClassRejectedbyStore: string = 'dropdown-item';
  caseManualId
  oppoId
  dropdown = [
    'All', 'Draft','Recently Viewed', 'Work Order Received', 'Rejected', 'Quote Sent', 'Work Order Not Received', 'Submitted For Approval', 'Approved',
    'Invoiced', 'Withdrawn', 'Approved by Store', 'Rejected by Store'
  ];

  tabname: string = 'All';

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    // this.setOrder('createdDate', 6);
    this.serialNumber = 1;
    this.getStatusList();
    
  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('Quote').subscribe(data => {
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
 
      this.getQuote()
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
       if(this.dropdownSelected.filtreparam=='all'){
         this.getQuote();
       }
      else if(this.dropdownSelected.filtreparam=='Recently Viewed'){
         this.getRecentQuote();
       }
       else
      { 
        this.getQuotesByStatus();}
      
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
       if(this.dropdownSelected.filtreparam=='all'){
        this.getQuote();
      }
     else if(this.dropdownSelected.filtreparam=='related'){
        this.getRecentQuote();
      }
      else
     { 
       this.getQuotesByStatus();}
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
  resetNgClasses() {
    this.ngClassAll = 'dropdown-item';
    this.ngClassDraft = 'dropdown-item';
    this.ngClassWorkOrderReceived = 'dropdown-item';
    this.ngClassRejected = 'dropdown-item';
    this.ngClassQuoteSent = 'dropdown-item';
    this.ngClassWorkOrderNotReceived = 'dropdown-item';
    this.ngClassSubmittedForApproval = 'dropdown-item';
    this.ngClassApproved = 'dropdown-item';
    this.ngClassInvoiced = 'dropdown-item';
    this.ngClassWithdrawn = 'dropdown-item';
    this.ngClassApprovedbyStore = 'dropdown-item';
    this.ngClassRejectedbyStore = 'dropdown-item';
  }

  
 

  
  // SORT ENDS

  showCreateQuoteView() {
    this.isEditMode = false;
    this.heading = this.CREATE_Quote_HEADING;
    this.createNewQuote.show();
  }

  cancelData() {
    this.createNewQuote.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Quote_HEADING;
    // this.quoteRequest = new QuoteRequest();
  }

  _onQuoteCreated($event: QuoteResponse) {
    if (!this.isEditMode) {
      this.quoteList.content.unshift($event);
    } else {
      let index = 0;
      this.quoteList.content.forEach(element => {
        if (element.id === $event.id) {
          this.quoteList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  onClickEdit(quote: QuoteResponse) {
    this.spinner.show();
    this._quoteService.getQuoteDetails(quote.id).subscribe(data => {
      this.quoteRequest = data;
      this.caseManualId=data.caseNumber;
      this.oppoId = data.oppertunityManualId;
      this.isEditMode = true;
      this.heading = this.EDIT_Quote_HEADING;
      this.createNewQuote.show();
      this.spinner.hide();
    },err=>{
      this.spinner.hide();
      this.message=err.error.message;
      this.popupSuccessModel.show();
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }

  getQuote() {
    this.spinner.show();
    this.quoteList.content=[];
    this._quoteService.getQuote(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.quoteList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecentQuote() {
    this.spinner.show();
    this.quoteList.content=[];
    this._quoteService.getRecentQuote(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.quoteList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getQuotesByStatus() {
    this.spinner.show();

    this.quoteList.content=[];
    this._quoteService.getQuotesByStatus(`status=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      console.log('data' + data);
      this.quoteList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.quoteList.totalPages< pagenumber){
  this.message="Enter Valid Input";
  this.popupSuccessModel.show();
  //  this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
   this.getQuote()
    this.serialNumber = (((this.dropdownSelected.page) * this.size));
  }
}
resetsearch(){
  this.advanceSearch.quoteNumber='';
    this.advanceSearch. quoteName='';
    this.advanceSearch.accountName='';
    this.advanceSearch.oppertunityNumber='';
    this.advanceSearch.status='';
    this.advanceSearch.expiryDate='';
}
getSearchResult(){
  if(this.advanceSearch.quoteNumber==''&&
    this.advanceSearch. quoteName==''&&
    this.advanceSearch.accountName==''&&
    this.advanceSearch.oppertunityNumber==''&&
    this.advanceSearch.status==''&&
    this.advanceSearch.expiryDate==''){
    // this.showAlert("Enter Valid key for search");
    this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });


  }

  else{
    if(this.advanceSearch.expiryDate!=''){
      this.advanceSearch.expiryDate= this.datepipe.transform(this.advanceSearch.expiryDate, 'yyyy-MM-dd');
    }

    this.spinner.show();
    this._quoteService.getSearchResult(this.advanceSearch).subscribe(data=>{
      this.spinner.hide();
      this.quoteList.content=data;
      this.quoteList.first=true;
      this.quoteList.last=true;
     
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message:error.error.message });

    });
  }

}
statusList:Array<QueteStatus>=[];
getStatusList(){
  this._quoteService.getQuoteStatusForList().subscribe(data=>{
  const temp=[];
  for(let i=0;i<data.length;i++){
    let lis = new QueteStatus();
    lis.statusName=data[i];
    temp.push(lis);
    this.statusList=temp;
  }
  })
}


}
