import { CirclesService } from './../../services/circles.services';
import {
  ExpenseManagementExpensePurpose, ExpenseManagementExpenseType, ExpenseManagementStatus,
  ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseManagementCostCenter, ContactForList, Page, ExpenseStatus
} from './../../services/model';
import { Loginservices } from './../../services/login.services';
import { ExpenseManagementService } from './../../services/expenses-management.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import {
  ExpenseManagementRequest,
  ExpenseManagementResponse
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-expensesmanagement',
  templateUrl: './expensesmanagement.component.html',
  styleUrls: ['./expensesmanagement.component.css']
})
export class ExpensesmanagementComponent implements OnInit {
  isExpensesmanagementOpen: boolean;
  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  CREATE_expensesmanagement_HEADING: string = 'New Expense';
  EDIT_expensesmanagement_HEADING: string = 'Edit Expense';
  NEXT_BATTERY_expensesmanagement_HEADING: string =
    'New Expense: Battery Div Cost Centers';
  NEXT_POWER_expensesmanagement_HEADING: string =
    'New Expense: Power Div Cost Centers';
  expensesmanagementList: Page<ExpenseManagementResponse> = new Page<ExpenseManagementResponse>()
  searchitem: string = '';
  isSelected = false;
  newexpensesmanagement: ExpenseManagementRequest = new ExpenseManagementRequest();
  expensesmanagementid;
  recentlyView: boolean = true;
  purposeForList: Array<ExpenseManagementExpensePurpose> = [];
  typeList: Array<ExpenseManagementExpenseType> = [];
  statusForList: Array<ExpenseManagementStatus> = [];
  businessAreaForList: Array<ExpenseManagementBusinessArea> = [];
  businessPlaceForList: Array<ExpenseManagementBusinessPlace> = [];
  costcenterForList: Array<ExpenseManagementCostCenter> = [];
  contactsForlist: Array<ContactForList> = [];
  userforlist: Array<ContactForList> = [];
  loginData: any;
  ngClassAll: string = 'dropdown-item';
  ngClassFinance: string = 'dropdown-item';
  ngClassHOD: string = 'dropdown-item';
  ngClassZonalManager: string = 'dropdown-item';
  ngClassEClaim: string = 'dropdown-item';
  ngClassMyExpenses: string = 'dropdown-item';
  ngClassRecently: string = 'dropdown-item';
  ngClassRejectedClaims: string = 'dropdown-item';
  ngClassSubmmitted: string = 'dropdown-item';
  ngClassDraft: string = 'dropdown-item';

  serialNumber: number;
  recently: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  reverse: boolean = false;
  sortColumn: number = 5;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false];
  page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  dropdownHeading: string = 'All';
  popupNgClass: string = 'alert alert-danger center center-block';
  status: string;
  _cFlag: string;

  @ViewChild('createexpensesmanagementModel', { static: false })
  createexpensesmanagementModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false })
  popupSuccessModel: ModalDirective;
  @ViewChild('createTourExpenseModel', { static: false })
  createTourExpenseModel: ModalDirective;
  @ViewChild('deleteExpModel', { static: false })
  deleteExpModel: ModalDirective;
  
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
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Claim Number',
      sortColumnName: 'expenseManagementManualId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Employee',
      sortColumnName: 'contact.firstName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Employee Code',
      sortColumnName: 'contact.employeeCode',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Expense Purpose',
      sortColumnName: 'expensePurpose.expensePurposeName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Expense Type',
      sortColumnName: 'expenseType.expenseTypeName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Total Expense',
      sortColumnName: 'totalExpense',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Amount to Disburse',
      sortColumnName: 'netAmountToPay',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Document Date',
      sortColumnName: 'documentDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Purpose',
      sortColumnName: 'purposeOfVisit',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Status',
      sortColumnName: 'status.statusName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];
  advancesearch = {
    expenseManagementManualId: '',
    employee: '',
    employeeCode:'',
    expensePurposeName:'',
    expenseTypeName:'',
    statusName:'',
    documentDate:''
  };

  //done

  constructor(
    private _expenseManagementService: ExpenseManagementService,private pagefilterservice:PagefilterService,
    private spinner: NgxSpinnerService, private datePipe:DatePipe,
    private loginservice: Loginservices, private _activatedRoute: ActivatedRoute,
    private _circlesService: CirclesService, private loginService: Loginservices
  ) {
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
    this.getStatusForList();
    // this.setOrder('createdDate', 5);
    // this.newexpensesmanagement.divCostCenters == 1;
    this.loginData = JSON.parse(sessionStorage.getItem('login'));
    this.serialNumber = 1;
    // this.sortColumnName = 'createdDate';
    // this.sortOrder = 'desc';
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('expensesmanagement').subscribe(data => {
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
      const submitted = this.dropdownsList.find(d => d.clickEventParam === 'Submitted');

if (submitted && this.dropdownSelected.querypram == false) {
  this.dropdownsList.forEach(d => d.class = 'dropdown-item');

  submitted.class = 'dropdown-item dropdown-item-checked';

  this.dropdownSelected.name = submitted.name;
  this.dropdownSelected.filtreparam = submitted.clickEventParam;
  this.dropdownSelected.sqId = submitted.sqId;
  this.dropdownSelected.page = 0;

  this.pindtselection = submitted.pined;
}

      this.getexpensesmanagement();
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
    this.ngClassFinance = 'dropdown-item';
    this.ngClassHOD = 'dropdown-item';
    this.ngClassZonalManager = 'dropdown-item';
    this.ngClassEClaim = 'dropdown-item';
    this.ngClassMyExpenses = 'dropdown-item';
    this.ngClassRecently = 'dropdown-item';
    this.ngClassRejectedClaims = 'dropdown-item';
    this.ngClassSubmmitted = 'dropdown-item';
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
        // if (this.dropdownSelected.filtreparam === 'All') {
        //   this.getexpensesmanagement();
        // } else if (this.dropdownSelected.filtreparam === 'Recently') {
        //   this.getRecentexpensesmanagement();
        // } else {
        //   this.getExpenseByStatus();
        // }
        this.getexpensesmanagement();
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
        // if (this.dropdownSelected.filtreparam === 'All') {
        //   this.getexpensesmanagement();
        // } else if (this.dropdownSelected.filtreparam === 'Recently') {
        //   this.getRecentexpensesmanagement();
        // } else {
        //   this.getExpenseByStatus();
        // }
        // this.getExpenseByStatus();
        this.getexpensesmanagement();
      } else {
        tbl.icon = '';
      }
    });
  }


  // PAGINATION ENDS

  getexpensesmanagement() {
    this.spinner.show();
    this.expensesmanagementList.content=[];
    this._expenseManagementService.getexpensesmanagement(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=20`)
      .subscribe(data => {
          this.expensesmanagementList = data;
          this.spinner.hide();
        },
        err => {
          this.spinner.hide();
          this.message = err.error.status + ' # ' + err.error.message;
          this.popupSuccessModel.show();
        });
  }

  // getExpenseByStatus() {
  //   this.spinner.show();
  //   this._expenseManagementService
  //     .getExpenseByStatus(`status=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=20`)
  //     .subscribe(
  //       data => {
  //         this.expensesmanagementList = data;
  //         // this.first = data.first;
  //         // this.last = data.last;
  //         // this.size = data.size;
  //         // this.serialNumber = this.page * this.size;
  //         this.spinner.hide();
  //       },
  //       err => {
  //         this.spinner.hide();
  //         this.message = err.error.status + ' # ' + err.error.message;
  //         this.popupSuccessModel.show();
  //       }
  //     );
  // }

  // recent;
  // getRecentexpensesmanagement() {
  //   this.spinner.show();
  //   this._expenseManagementService.getRecentexpensesmanagement(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`)
  //     .subscribe(
  //       data => {
  //         if (data != null) {
  //           this.spinner.hide();
  //         }
  //         this.expensesmanagementList = data;
  //         // this.first = data.first;
  //         // this.last = data.last;
  //         // this.size = data.size;
  //         // this.serialNumber = this.page * this.size;
  //       },
  //       err => {
  //         this.spinner.hide();
  //         this.message = err.error.message;
  //         this.popupSuccessModel.show();
  //         // setTimeout(() => {
  //         //   this.popupSuccessModel.hide();
  //         // }, 3000);
  //       }
  //     );
  // }

  onClickEdit(expensesmanagement) {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(expensesmanagement.expenseManagementId).subscribe((data) => {
      this.newexpensesmanagement = data;
      // this.newexpensesmanagement.employeeFirstName = data.contactFirstName;
      // this.newexpensesmanagement.employeeFullName = data.contactFirstName + ' ' + data.contactLastName;
      // if (this.newexpensesmanagement.employeeFirstName != null || this.newexpensesmanagement.employeeFirstName != '') {
      //   this.newexpensesmanagement.isEmployeeSelected = 'fa fa-times';
      // }
      // else {
      //   this.newexpensesmanagement.isEmployeeSelected = 'fa fa-search';
      // }
      this.spinner.hide();
    },
      err => {
        this.spinner.hide();
        this.message = err.error.message;
      });
    this.isEditMode = true;
    this.heading = this.EDIT_expensesmanagement_HEADING;
    this.createexpensesmanagementModel.show();
  }


  createExpenseManagement() {
    // this.newexpensesmanagement = new ExpenseManagementRequest();

    this.isEditMode = false;
    // this.newexpensesmanagement.next = false;
    this.heading = this.CREATE_expensesmanagement_HEADING;
    this.createexpensesmanagementModel.show();
  }

  cancelData() {
    this.createexpensesmanagementModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_expensesmanagement_HEADING;
    this.newexpensesmanagement = new ExpenseManagementRequest();
  }

  // onNext() {
  //   this.createexpensesmanagementModel.hide();
  //   if (this.newexpensesmanagement.divCostCenters === 1) {
  //     this.heading = this.NEXT_BATTERY_expensesmanagement_HEADING;
  //   } else {
  //     this.heading = this.NEXT_POWER_expensesmanagement_HEADING;
  //   }
  //   this.createexpensesmanagementModel.show();
  // }

  onexpensesmanagementCreated($event: ExpenseManagementResponse) {
    if (!this.isEditMode) {
      this.expensesmanagementList.content.unshift($event);
    } else {
      let index = 0;
      this.expensesmanagementList.content.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.expensesmanagementList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }



  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  getUserForList() {
    this._circlesService.getContactListForCircle().subscribe(data => {
      this.userforlist = data;
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();

  }
  approvalmessage:string='';
  baseUrl = '';
  Confirm(resp:ExpenseManagementResponse){
    // this.spinner.show();this
    this.baseUrl='api/expensemanagement'
    this.approvalmessage="Are You Sure For Delete This Expense"
    this.expensesmanagementid=resp.expenseManagementId
    this.deleteExpModel.show();

  }
  onSubmitted($event){
    this.getexpensesmanagement();
  }
  noRequestFor(){
    this.deleteExpModel.hide();

  }

  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.expensesmanagementList.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this.popupSuccessModel.show();
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getexpensesmanagement();
}
}
resetExpenc(){
  this.advancesearch.documentDate=''
   this.advancesearch.employee=''
  this.advancesearch.employeeCode='' 
  this.advancesearch.expenseManagementManualId=''
  this.advancesearch.expensePurposeName=''
   this.advancesearch.expenseTypeName=''
  this.advancesearch.statusName=''
}
searchExpenc(){
  if(this.advancesearch.documentDate=='' && this.advancesearch.employee=='' &&
  this.advancesearch.employeeCode=='' && this.advancesearch.expenseManagementManualId==''&&
  this.advancesearch.expensePurposeName==''&& this.advancesearch.expenseTypeName==''&&
  this.advancesearch.statusName==''){
    this.message="Enter Key for Search";
    this.popupSuccessModel.show();

  }

  else{
    if(this.advancesearch.documentDate!=''){
      this.advancesearch.documentDate = this.datePipe.transform(this.advancesearch.documentDate, 'yyyy-MM-dd');
    }
    this.spinner.show();
    // this.advancesearch.documentDate = this.datePipe.transform(this.advancesearch.documentDate, 'yyyy-MM-dd');
    this._expenseManagementService.getSearchResult(this.advancesearch).subscribe(data=>{
      this.spinner.hide();
      this.expensesmanagementList.content = data;
   
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }
 
}
statusList:Array<ExpenseStatus>=[]
getStatusForList(){
  this._expenseManagementService.statusForList().subscribe(data=>{
    const temp=[];
    for(let i=0;i<data.length;i++){
      let lis=new ExpenseStatus;
      lis.statusName=data[i];
      temp.push(lis);
      this.statusList=temp;
    }
  })
}
}
