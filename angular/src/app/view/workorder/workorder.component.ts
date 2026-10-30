import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';
import { AccountCreate } from '../../services/model';

@Component({
  selector: 'app-workorder',
  templateUrl: './workorder.component.html',
  styleUrls: ['./workorder.component.scss']
})
export class WorkorderComponent implements OnInit {
  @ViewChild('createWorkOrderModel', { static: false }) createWorkOrderModel: ModalDirective;  
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;    
  
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  
  searchitem: string = '';
  recentlyView: boolean = true;
  ngClassAll: string;
  related: boolean = true;
  all: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Work Order';
  EDIT_ACCOUNT_HEADING: string = 'Edit Work Order';

   page: number = 0;
   totalPages;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
i: number;

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';

  accountList: Array<AccountCreate> = [];
  createAccount: AccountCreate = new AccountCreate();
  loginData;
  message: string='';
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

  //done

  constructor(private spinner: NgxSpinnerService,private _accountServices: AccountService, private loginService: Loginservices) { }

  ngOnInit() {
    this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
  }

  getAccountDetails() {
    this.spinner.show();
    this._accountServices.getAccounts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.accountList = data.content;
      if (data != null) {
        this.spinner.hide();
      }
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    },err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecentlyViewed(column: string, order: string) {
    this.spinner.show();
    this._accountServices.getRecentVisit(this.page, column, order).subscribe(data => {
      this.accountList = data.content;
      if (data != null) {
        this.spinner.hide();
      }

      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    },err => {
     this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    })
  }

  popcheck() {
    setTimeout(() => {
      this.spinner.hide();
      this.popupSuccessModel.show();

      setTimeout(() => {
        this.popupSuccessModel.hide()
      }, 2000
      );


    }, 1000
    );

  }

  createWorkOrderModelhide() {
    this.createAccount = new AccountCreate();
    this.onCancle.emit('cancled');
    this.createWorkOrderModel.hide();
  }

  
  showCreateWorkOrderView() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createWorkOrderModel.show();
  }

  createEditModelshow(createAccount: AccountCreate) {
    this.spinner.show();
    this._accountServices.getAccount(createAccount.sampAccountNumber).subscribe(data => {
      this.createAccount = data;
      if (data != null) {
        this.spinner.hide();
      }
      if (data.parentAccountSapNumber == null) {
        this.createAccount.parentAccountSapNumber = '0';
      }

      if (data.accountCategoryId == null) {
        this.createAccount.accountCategoryId = 0;
      }

    }),err => {
      this.spinner.hide()
      this.message = err.error.message;
      this.popupSuccessModel.show();
    };
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createWorkOrderModel.show();
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createWorkOrderModel.hide();
  }

  dropdownChange(tabName: string) {
    if (tabName == 'related') {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.related = true;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
      this.recentlyView = true;
      this.page = 0;
      this.serialNumber = (((this.page) * this.size));
    }
    else {
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.related = false;
      this.recentlyView = false;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getAccountDetails();
      this.page = 0;
      this.serialNumber = (((this.page) * this.size));
    }
    return false;
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    //this.serialNumber = (((this.page) * this.size));
    if (this.related == false) {
      this.getAccountDetails();
    }
    else {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (this.related == false) {
      this.getAccountDetails();
    }
    else {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    //this.serialNumber = (((this.page) * this.size));
    if (this.related == false) {
      this.getAccountDetails();
    }
    else {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
  }


  setOrder(value: string, column: number) {
    console.log(value, column);
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[7] = true;
        }
        break;
    }

    if (this.recentlyView) {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    } else {
      this.getAccountDetails();
    }


    //this.sortColumn = column;
    //this.order = value;
  }
onAccountCreated($event){
  // this.SSATableList.unshift($event);
  if(!this.isEditMode){
    this.accountList.unshift($event);
  }else{
    let index = 0;
    this.accountList.forEach(element => {
      if(element.sampAccountNumber == $event.sampAccountNumber){
        this.accountList[index]=$event;
      }else{
        index++;
      }
    });
  }

}


}
