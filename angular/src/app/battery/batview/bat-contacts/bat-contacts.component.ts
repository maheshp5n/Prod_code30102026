import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AccountService } from '../../../services/accounts.services';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { Loginservices } from '../../../services/login.services';
import { AccountsForList, BoardingLoading, Contacts } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { Page } from '../../bt_model';

@Component({
  selector: 'app-bat-contacts',
  templateUrl: './bat-contacts.component.html',
  styleUrls: ['./bat-contacts.component.css']
})
export class BatContactsComponent implements OnInit {
  isContactOpen: boolean;
  isEditMode: boolean = false;
  message: string;
  heading: string;
  edit: boolean = false;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  contactsList: Page<Contacts> = new Page<Contacts>();
  searchitem: string = '';
  isSelected = false;
  newcontact: Contacts = new Contacts();
  accountForList: Array<AccountsForList> = [];
  contactid;
  recentlyView: boolean = false;
  serialNumber: number;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  contactsListById;
  stateId: string;
  postBoardingLoading: BoardingLoading = new BoardingLoading();
  contactsList2: Contacts = new Contacts();
  contactId;
  sortColumn: number = 12;
  sortColumnName: string = 'createdOn'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false];
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  // all recently View dropdown
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassEmployees: string = 'dropdown-item';
  ngClassCustomer: string = 'dropdown-item';
  ngClassVendor: string = 'dropdown-item';
  ngClassRelated: string = 'dropdown-item';
  recently: boolean = false;
  contactAlias: string = 'All';
  _cFlag: string;
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
      name: 'Employee',
      clickEventParam: 'Emplayee',
      class: 'dropdown-item',
      pined: false
    },
    {
      sqId: 4,
      name: 'Customer',
      clickEventParam: 'customer',
      class: 'dropdown-item',
      pined: false
    },
    {
      sqId: 5,
      name: 'Vendor',
      clickEventParam: 'vendor',
      class: 'dropdown-item',
      pined: false
    }
    
   
   ];
  
  
    dropdownSelected = {
      sqId: 1,
      name: 'All',
      filtreparam: 'all',
      sortname: 'createdOn',
      sortorder: 'desc',
      page: 0,
      size: 20,
      querypram: false
    };
    requestTableHeaders = [
      {
        id: 1,
        name: 'Name',
        sortColumnName: 'firstName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Contact Type',
        sortColumnName: 'contactType',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Account Name',
        sortColumnName: 'account.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 4,
        name: 'Email',
        sortColumnName: 'emailId',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 5,
        name: 'Mobile Number',
        sortColumnName: 'mobile',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 6,
        name: 'Grade',
        sortColumnName: 'grade.boardingName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 7,
        name: 'Employee/Vendor Code',
        sortColumnName: 'employeeCode',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 8,
        name: 'Designation',
        sortColumnName: 'contactDesignation',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 9,
        name: 'Legal Entity',
        sortColumnName: 'legalEntity.legalEntityName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 10,
        name: 'City',
        sortColumnName: 'addressInfo.city',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 11,
        name: 'Created Date',
        sortColumnName: 'createdOn',
        icon: 'fa fa-angle-down pull-right'
      }
    ];
  
    contactSearch={
      contact_name:'',
      contact_email:'',
      contact_phone:'',
      contact_account:'',
      contact_type:'',
      emp_code:''
    };

    //done
  
  @ViewChild('createContactModel', { static: false }) createContactModel: ModalDirective;
  @ViewChild('GradeModel', { static: false }) GradeModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  constructor(private _global: GlobalserviceService,private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _contactsService: BtcontactsService,private bordingloding: BoardingLoadingService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.contactid = pram['id'];
    });
    this._activatedRoute.queryParams.subscribe(param => {
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
      this._cFlag = param['_c'];
      this.getFilterList();
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.searchitem = '';
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_contacts').subscribe(data =>{
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        console.log(d.filterparam);
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

      this.getContacts();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        console.log("Filterparam=="+item.clickEventParam,+'Name=='+item.name);
        this.dropdownSelected.page = 0;
        if(item.clickEventParam=='All'){
          
          this.getContacts();
        }
        else if(item.clickEventParam=='related'){
         this.getContactsRecent();
        }
        else { 
          this.getContactsByType();
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
        if(this.dropdownSelected.filtreparam=='All'){
          
          this.getContacts();
        }
        else if(this.dropdownSelected.filtreparam=='related'){
         this.getContactsRecent();
        }
        else { 
          this.getContactsByType();
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

  getContacts() {
    this.spinner.show();
    this.contactsList.content=[];
    console.log(this.contactsList.content.length);
    this._contactsService.getContacts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.contactsList = data;
      console.log(this.contactsList.content.length);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  getContactsRecent() {
    this.spinner.show();
    this.contactsList.content=[];
    this._contactsService.getRecentContacts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.contactsList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  

  getContactsByType() {
    this.spinner.show();
    this.contactsList.content=[];
    this._contactsService.getContactsByType( `type=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {

      this.contactsList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  // getEmployees(column: string, order: string) {
  //   this.spinner.show();
  //   this._contactsService.getEmployees(this.page, column, order).subscribe(data => {
  //     this.contactsList = data.content;
  //     this.first = data.first;
  //     this.last = data.last;
  //     this.size = data.size;
  //     this.serialNumber = ((this.page) * this.size);
  //     this.spinner.hide();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //     setTimeout(() => {
  //       this.popupSuccessModel.hide();
  //     }, 3000);
  //   });
  // }

  // getCustomer(column: string, order: string) {
  //   this.spinner.show();
  //   this._contactsService.getCustomer(this.page, column, order).subscribe(data => {
  //     this.contactsList = data.content;
  //     this.first = data.first;
  //     this.last = data.last;
  //     this.size = data.size;
  //     this.serialNumber = ((this.page) * this.size);
  //     this.spinner.hide();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;

  //     this.popupSuccessModel.show();
  //     setTimeout(() => {
  //       this.popupSuccessModel.hide();
  //     }, 3000);
  //   });
  // }

  // getVendor(column: string, order: string) {
  //   this.spinner.show();
  //   this._contactsService.getVendor(this.page, column, order).subscribe(data => {
  //     this.contactsList = data.content;
  //     this.first = data.first;
  //     this.last = data.last;
  //     this.size = data.size;
  //     this.serialNumber = ((this.page) * this.size);
  //     this.spinner.hide();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //     setTimeout(() => {
  //       this.popupSuccessModel.hide();
  //     }, 3000);
  //   });
  // }

  cancel() {
    this.newcontact = new Contacts();
  }

  modo(value: string) {
    if (value === 'myVal') {
      this.GradeModel.show();
    }
  }

  postBoarding() {
    this.bordingloding.postBoardingList(this.postBoardingLoading).subscribe(data => {
      this.postBoardingLoading = new BoardingLoading();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  save() {
    this.postBoarding();
    this.ngOnInit();
    this.GradeModel.hide();
  }

  onClickEdit(i, y) {
    this.spinner.show();
    this.newcontact = i;
    this.contactId = y;
    this._contactsService.getContact(y).subscribe((data) => {
      this.spinner.hide();
      this.newcontact = data;
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CONTACT_HEADING;
    this.createContactModel.show();
  }

  showCreateContactView() {
    this.newcontact = new Contacts();
    this.isEditMode = false;
    this.heading = this.CREATE_CONTACT_HEADING;
    this.createContactModel.show();
  }

  cancelData() {
    this.createContactModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_CONTACT_HEADING;
    this.newcontact = new Contacts();
  }


  onContactCreated($event: Contacts) {
    this.getContacts();
    if (!this.isEditMode) {
      this.contactsList.content.unshift($event);
    } else {
      let index = 0;
      this.contactsList.content.forEach(element => {
        if (element.contactid === $event.contactid) {
          this.contactsList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  // }
  advanceSearch(){
  
    if(this.contactSearch.contact_account=='' && this.contactSearch.contact_email=='' && this.contactSearch.contact_name=='' &&
    this.contactSearch.contact_phone=='' && this.contactSearch.contact_type=='' && this.contactSearch.emp_code== ''){
      this.message="Please Enter Valid Input To Search";
      this._global.alert({ type: 'alert', message: this.message });
    }
 
    else {
      this.spinner.show();
      this._contactsService.getAdvanceSearch(this.contactSearch).subscribe(data=>{
        this.spinner.hide();
        this.contactsList.content=data;
        this.contactsList.first=true;
        this.contactsList.last=true;
      },error=>{
        this.spinner.hide();
      });
    }

  }
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    if(pagenumber!=null && pagenumber!==''){
      if(pagenumber>this.contactsList.totalPages || pagenumber==0){
        this._global.alert({type:'alert',message:'Page Count Should Be Less Then Total Page'});
      }
else{
  this.contactsList.number=this.pagenumber-1;
    this.dropdownSelected.page=pagenumber-1;
    this.getContacts();
}

}
else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
else{
  this._global.alert({type:'alert',message:'Please Input A Valid Number'});
 }
}
resetSearch(){
  this.contactSearch.contact_name=''
  this.contactSearch.contact_email=''
  this.contactSearch.contact_phone=''
  this.contactSearch.contact_account=''
  this.contactSearch.contact_type=''
  this.contactSearch.emp_code=''
this.getContacts();
}

}
