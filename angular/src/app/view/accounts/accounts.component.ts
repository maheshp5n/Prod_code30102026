import { Component, OnInit, ViewChild, Output, EventEmitter, ElementRef } from '@angular/core';
import { AccountCreate, Page, CustomerForList } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { SearchService } from '../../services/search.service';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  @ViewChild('datatable',{static:false})datatable:ElementRef;
  @ViewChild('createAcountModel', { static: false }) createAcountModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  pagenumber:any=null;
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
      name: 'Account Name',
      sortColumnName: 'name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'SAP Customer Number',
      sortColumnName: 'sapCustomerNumber',
      icon: ''
    },
    {
      id: 3,
      name: 'Parent Customer',
      sortColumnName: 'parentCustomer.customerName',
      icon: ''
    },
    {
      id: 4,
      name: 'Billing State Name',
      sortColumnName: 'billingInfo.state.stateName',
      icon: ''
    },
    {
      id: 5,
      name: 'Phone Number',
      sortColumnName: 'phoneNumber',
      icon: ''
    },
    {
      id: 6,
      name: 'Owner',
      sortColumnName: 'accountOwnerDetails.firstName',
      icon: ''
    },
    {
      id: 7,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: ''
    },
   
  ];

  searchContent={
    name:'',
    sap_number:'',
    type:'',
    email:'',
    phone:'',
    address:'',
    pincode:'',
    state:''
  };
  //done
  accountList: Page<AccountCreate> = new Page<AccountCreate>();
  createAccount: AccountCreate = new AccountCreate();
  searchitem: string = '';

  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  recentlyView: boolean = false;
  related: boolean = false;
  all: boolean = true;
  allView: boolean = true;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Account';
  EDIT_ACCOUNT_HEADING: string = 'Edit Account';

  message: string;
  page: number = 0;
  totalPages;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  i: number;
  pindtselection = false;
  popupNgClass: string = 'alert alert-danger center center-block';
  isModelShow = false;
  _cFlag: string;
  
  constructor(private pagefilterservice:PagefilterService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _accountServices: AccountService,
     private loginService: Loginservices,private searchservice:SearchService) {
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
    // this.getFilterList();
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
    // this.getAccountDetails();
  }

  getAccountDetails() {
    this.spinner.show();
    this.accountList.content=[]
    this._accountServices.getAccounts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.accountList = data;
        this.spinner.hide();
      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }


  createAcountModelhide() {
    this.createAccount = new AccountCreate();
    this.createAcountModel.hide();
  }

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getAccountDetails();
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
        this.getAccountDetails();
      } else {
        tbl.icon = '';
      }
    });
  }
  showCreateAccountView() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  createEditModelshow(createAccount: AccountCreate) {
    this.spinner.show();
    this._accountServices.getAccount(createAccount.sampAccountNumber).subscribe(data => {
      this.createAccount = data;
      if (data.parentAccountSapNumber == null) {
        this.createAccount.parentAccountSapNumber = '0';
      }

      if (data.accountCategoryId == null) {
        this.createAccount.accountCategoryId = 0;
      }
      if (data.shippingCountryId == null) {
        this.createAccount.shippingCountryId = '0';
      }
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
    this.createAcountModel.show();
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.createAcountModel.hide();
  }


 

  onAccountCreated($event: AccountCreate) {
    if (!this.isEditMode) {
      this.accountList.content.unshift($event);
    } else {
      let index = 0;
      this.accountList.content.forEach(element => {
        if (element.sampAccountNumber == $event.sampAccountNumber) {
          this.accountList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }
  popupClose() {
    this.popupSuccessModel.hide();
  }
  
  getFilterList() {
    this.pagefilterservice.getPagefilter('accounts').subscribe(data => {
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

      this.getAccountDetails();
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
baseUrl:string='';
  advanceSearch(){
    this.baseUrl= 'api/accounts';
    
    if(this.searchContent.name=='' && this.searchContent.address=='' &&
    this.searchContent.email=='' && this.searchContent.pincode==''&&
    this.searchContent.state=='' && this.searchContent.phone=='' && 
    this.searchContent.type=='' && this.searchContent.sap_number==''){
     this.message="Please Enter valid Key for Search";
      this.popupSuccessModel.show()
    }
    else{
      this.spinner.show();
      this._accountServices.getSearchResult(this.searchContent).subscribe(data=>{
        this.spinner.hide();
        this.accountList.content=data;
        this.accountList.first=true;
        this.accountList.last=true;
      },error=>{
        this.spinner.hide();
      });
    }
  }
  jumpPage(pagenumber){
    if(pagenumber!=null)
   {
    this.accountList.number=this.pagenumber-1;
  }
else{

}
    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.accountList.content=[];
    this._accountServices.getAccounts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.accountList = data;
        this.spinner.hide();
      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  

  }
  resetSearch(){
    this.searchContent.name=''
    this.searchContent.sap_number=''
    this.searchContent.type=''
    this.searchContent.email=''
    this.searchContent.phone=''
    this.searchContent.address=''
    this.searchContent.pincode=''
    this.searchContent.state=''
  }
  
  exportToExcel() {
  this.requestTableHeaders.forEach(e=>{
    const header= e.name;
    console.log(header);
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(header);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    XLSX.writeFile(workbook, 'account_search_result.xlsx');
  });

  }

}
