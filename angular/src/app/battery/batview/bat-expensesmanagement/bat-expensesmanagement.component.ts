import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CirclesService } from '../../../services/circles.services';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { Loginservices } from '../../../services/login.services';
import { ContactForList, ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseManagementCostCenter, ExpenseManagementExpensePurpose, ExpenseManagementExpenseType, ExpenseManagementRequest, ExpenseManagementResponse, ExpenseManagementStatus, ExpenseStatus, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BatteryExpenseManagementRequest, BatteryExpenseManagementResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-expensesmanagement',
  templateUrl: './bat-expensesmanagement.component.html',
  styleUrls: ['./bat-expensesmanagement.component.css']
})
export class BatExpensesmanagementComponent implements OnInit {
  isExpensesmanagementOpen: boolean;
  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  CREATE_expensesmanagement_HEADING: string = 'New Battery Expense';
  EDIT_expensesmanagement_HEADING: string = 'Edit Battery Expense';
  NEXT_BATTERY_expensesmanagement_HEADING: string =
    'New Battery Expense: Battery Div Cost Centers';
  NEXT_POWER_expensesmanagement_HEADING: string =
    'New Battery Expense: Power Div Cost Centers';
  expensesmanagementList: Page<BatteryExpenseManagementResponse> = new Page<BatteryExpenseManagementResponse>()
  searchitem: string = '';
  isSelected = false;
  newexpensesmanagement: BatteryExpenseManagementRequest = new BatteryExpenseManagementRequest();
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
  sortColumnName: string = 'created_date'; // first column variable
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

  @ViewChild('createexpensesmanagementModel')
  createexpensesmanagementModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;
  @ViewChild('createTourExpenseModel')
  createTourExpenseModel: ModalDirective;
  @ViewChild('deleteExpModel')
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
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Claim Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Employee',
      sortColumnName: 'cnt.first_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Employee Code',
      sortColumnName: 'cnt.employee_code',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Expense Purpose',
      sortColumnName: 'ep.expense_purpose_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Expense Type',
      sortColumnName: 'et.expense_type_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Total Expense',
      sortColumnName: 'total_expense',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Amount to Disburse',
      sortColumnName: 'net_amount_to_pay',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Document Date',
      sortColumnName: 'document_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Purpose',
      sortColumnName: 'purpose_of_visit',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Status',
      sortColumnName: 'es.status_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];
  advancesearch = {
    manualId: '',
    employee: '',
    employeeCode:'',
    expensePurposeName:'',
    expenseTypeName:'',
    statusName:'',
    documentDate:''
    
  };

  //done

  constructor(

    private _expenseManagementServices: ExpenseManagementService,
    private _expenseManagementService: BtexpensemanagementService,private pagefilterservice:PagefilterService,
    private spinner: NgxSpinnerService, private datePipe:DatePipe,
    private loginservice: Loginservices, private _activatedRoute: ActivatedRoute,
    private _circlesService: CirclesService, private loginService: Loginservices, private _global:GlobalserviceService
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
    // this.requestTableHeaders=[];
    this.expensesmanagementList.content=[];
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
    this.expensesmanagementList.content=[];
    console.log(this.requestTableHeaders);
    
  }
  
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_expense').subscribe(data => {
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
        if (this.dropdownSelected.filtreparam === 'All') {
          this.getexpensesmanagement();
        } else if (this.dropdownSelected.filtreparam === 'Recently') {
          this.getRecentexpensesmanagement();
        } else {
          this.getExpenseByStatus();
        }
        // this.getExpenseByStatus();
      } else {
        tbl.icon = '';
      }
    });
  }


  // PAGINATION ENDS

  getexpensesmanagement() {
    this.spinner.show();
    this.expensesmanagementList.content=[];
    this._expenseManagementService.getexpensesmanagement(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`)
      .subscribe(data => {
          this.expensesmanagementList = data;
          // this.first = data.first;
          // this.last = data.last;
          // this.size = data.size;
          // this.serialNumber = this.page * this.size;
          this.spinner.hide();
        },
        err => {
          this.spinner.hide();
          this.message = err.error.status + ' # ' + err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
  }

  getExpenseByStatus() {
    this.spinner.show();
    this._expenseManagementService
      .getexpensesmanagement(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`)
      .subscribe(
        data => {
          this.expensesmanagementList = data;
          // this.first = data.first;
          // this.last = data.last;
          // this.size = data.size;
          // this.serialNumber = this.page * this.size;
          this.spinner.hide();
        },
        err => {
          this.spinner.hide();
          this.message = err.error.status + ' # ' + err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        }
      );
  }

  // recent;
  getRecentexpensesmanagement() {
    this.spinner.show();
    this._expenseManagementService.getRecentexpensesmanagement(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`)
      .subscribe(
        data => {
          if (data != null) {
            this.spinner.hide();
          }
          this.expensesmanagementList = data;
          // this.first = data.first;
          // this.last = data.last;
          // this.size = data.size;
          // this.serialNumber = this.page * this.size;
        },
        err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
          // setTimeout(() => {
          //   this.popupSuccessModel.hide();
          // }, 3000);
        }
      );
  }

  onClickEdit(expensesmanagement:BatteryExpenseManagementResponse) {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(expensesmanagement.id).subscribe((data) => {
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

  onexpensesmanagementCreated($event: BatteryExpenseManagementResponse) {
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

  // popupClose() {
  //   this.popupSuccessModel.hide();

  // }
  approvalmessage:string='';
  baseUrl = '';
  Confirm(resp:BatteryExpenseManagementResponse){
    // this.spinner.show();this
    this.baseUrl='bt/api/expense'
    this.approvalmessage="Are You Sure For Delete This Expense"
    this.expensesmanagementid=resp.id
    this.deleteExpModel.show();

  }
  onSubmitted(){
    this.getexpensesmanagement();
  }
  noRequestForSure()
  {
    this.deleteExpModel.hide();

  }

  pagenumber:any=null;
  jumpPage(pagenumber){
    

    if(pagenumber!=null)
    {
      let pageno:string = pagenumber;
      if(pageno.match(/[a-z]/i))
      {
        pagenumber=null;
        this.message="Enter Valid Input";
        this._global.alert({ type: 'alert', message: this.message });
      }


  if(this.expensesmanagementList.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this._global.alert({ type: 'alert', message: this.message });
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getexpensesmanagement();
}
}
else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
else{
  this._global.alert({type:'alert',message:'Please Input Number'});
 }
}
resetExpenc(){
  this.advancesearch.documentDate=''
   this.advancesearch.employee=''
  this.advancesearch.employeeCode='' 
  this.advancesearch.manualId=''
  this.advancesearch.expensePurposeName=''
   this.advancesearch.expenseTypeName=''
  this.advancesearch.statusName=''
  this.getFilterList();
}
searchExpenc(){
  if(this.advancesearch.documentDate=='' && this.advancesearch.employee=='' &&
  this.advancesearch.employeeCode=='' && this.advancesearch.manualId==''&&
  this.advancesearch.expensePurposeName==''&& this.advancesearch.expenseTypeName==''&&
  this.advancesearch.statusName==''){
    this.message="Enter Key For Search";
    this._global.alert({ type: 'alert', message: this.message });

  }
  else{
    this.spinner.show();
    this.advancesearch.documentDate = this.datePipe.transform(this.advancesearch.documentDate, 'yyyy-MM-dd');
    this._expenseManagementService.getSearchResult(this.advancesearch).subscribe(data=>{
      this.spinner.hide();
      this.expensesmanagementList.content = data;
   
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    })
  }
 
}


yesRequestFor(){
  this.spinner.show();
if(this.expensesmanagementid!==null){
  // this.url='api/tourexpense'
  this._expenseManagementService.deleteExpense(this.expensesmanagementid,this.baseUrl).subscribe(data=>{
 
    this.spinner.hide();
     this.message='Deleted Successfully';
     this._global.alert({ type: '', message: this.message });
     this.deleteExpModel.hide();
     this.onSubmitted();

   },error=>{
     this.spinner.hide();
     this.message=error.error.message;
     this._global.alert({ type: 'alert', message: this.message });
    })
}

}

statusList:Array<ExpenseStatus>=[]
getStatusForList(){
  this._expenseManagementServices.statusForList().subscribe(data=>{
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
