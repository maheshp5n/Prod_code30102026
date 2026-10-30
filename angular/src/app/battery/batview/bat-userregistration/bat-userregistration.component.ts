import { Component, OnInit, ViewChild } from '@angular/core';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { userRole, AccountsForList, ContactForList, CreateLoginRequest, DisplayDropdownList, DisplayTableHeaders, Page, UserResponse, LocationForList, UserProfile, UserForList, LocationRequest, ObjectAccessResponse, ResetpasswordRequest, ResourceType } from '../../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';
import { GlobalserviceService } from '../../../globalservice.service';
import { FieldValidation } from '../../../services/validation.services';

@Component({
  selector: 'app-bat-userregistration',
  templateUrl: './bat-userregistration.component.html',
  styleUrls: ['./bat-userregistration.component.scss']
})
export class BatUserregistrationComponent implements OnInit {
  userRoleList: Array<userRole> = [];
  accountList: Array<AccountsForList> = [];
  contactDetail: Array<ContactForList> = [];
  locationList: Array<LocationForList> = [];
  resourceTypeList: Array<ResourceType> = [];
  usersforlist: Array<UserForList> = [];
  reqUserData: CreateLoginRequest = new CreateLoginRequest();
  searchkey: string;
  editMode: boolean = false;
  openModels = { locationModal: false };
  message: string;
  alertDetails = {
    type: 'danger',
    message: ''
  }

  dropdownlists: Array<DisplayDropdownList> = [{
    sqId: 1,
    name: 'All User',
    class: 'dropdown-item dropdown-item-checked',
    clickEventParam: 'all'
  },
  {
    sqId: 2,
    name: 'Blocked User',
    class: 'dropdown-item',
    clickEventParam: 'blockeduser',
  }, {
    sqId: 3,
    name: 'Enabled User',
    class: 'dropdown-item',
    clickEventParam: 'enableduser'
  }
  ]
  selectedDropdown = {
    filtreparam: 'all',
    sqId: 1,
    sortname: 'username',
    name: 'All User',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  }
  tableHeaders: Array<DisplayTableHeaders> = [
    {
      id: 1,
      name: 'User ID'
    },
    {
      id: 2,
      name: 'First Name',
    }, {
      id: 3,
      name: 'Middle Name',

    }, {
      id: 4,
      name: 'Last Name',

    },
    {
      id: 5,
      name: 'Account '
    }, {
      id: 6,
      name: 'Account SAP Number',

    }, {
      id: 7,
      name: 'Employee Code',

    }, {
      id: 8,
      name: 'Business Domain'
    },
    {
      id: 9,
      name: 'Expired'
    }, {
      id: 10,
      name: 'Locked'
    }, {
      id: 11,
      name: 'Enabled'
    }, {
      id: 12,
      name: 'Profile'
    }, {
      id: 13,
      name: 'Role'
    }, {
      id: 14,
      name: 'Manager'
    },
    {
      id: 15,
      name: 'Approval Manager'
    }
  ]
  pageUers: Page<UserResponse> = new Page<UserResponse>();
  userprofiles: Array<UserProfile> = [];
  objectAccess: Array<ObjectAccessResponse> = [];
  resetPassword: ResetpasswordRequest = new ResetpasswordRequest();

  @ViewChild('createUserModal', { static: false }) createUserModal: ModalDirective;
  @ViewChild('accountdisablemodel', { static: false }) accountdisablemodel: ModalDirective;
  @ViewChild('alertModal', { static: false }) alertModal: ModalDirective;
  @ViewChild('createlocationModal', { static: false }) createlocationModal: ModalDirective;
  @ViewChild('userResetPassword', { static: false }) userResetPassword: ModalDirective;
  _cFlag: string;
  constructor(private spinner: NgxSpinnerService,
    private _loginService: Loginservices,
    private _account: BtaccountService,
    private _batuserservice: BatuserregistrationService,
    private _locationservice: BtwarehouseService,
    private pagefilterservice: PagefilterService,
    private _serviceResourceService: BtserviceresourseService, private _activatedRoute: ActivatedRoute,
    private _global: GlobalserviceService
  ) {
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.selectedDropdown.querypram = true;
        this.selectedDropdown.size = Number(param['size']);
      }
      this.getFilterList();
    });

  }

  ngOnInit() {
    this.pageUers.content = [];
    this.getAccountId();
    this.getAuthorities();
    this.getLocation();
    this.getUserProfile();
    this.getResourceTypeForList();
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_user').subscribe(data => {
      this.dropdownlists = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.selectedDropdown.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.selectedDropdown.name = d.filterName;
            this.selectedDropdown.filtreparam = d.filterParam;
            this.selectedDropdown.sqId = d.userSummaryPageFilterId;
            // this.pindtselection = true;
            this.selectedDropdown.page = 0;
          }

          else {
            if (d.sqId == this.selectedDropdown.sqId) {
              // this.pindtselection = true;
            }
          }

        }
        this.dropdownlists.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          // pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownlists.length > 0 && this.selectedDropdown.querypram == false) {
        this.selectedDropdown.name = this.dropdownlists[0].name;
        this.selectedDropdown.filtreparam = this.dropdownlists[0].clickEventParam;
        this.dropdownlists[0].class = 'dropdown-item dropdown-item-checked';
        this.selectedDropdown.sqId = this.dropdownlists[0].sqId;
        // this.pindtselection = true;
        this.selectedDropdown.page = 0;
      }

      this.getAllUsers();
    });
  }
  dropdownChange(sqId) {
    this.dropdownlists.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.selectedDropdown.name = item.name;
        this.selectedDropdown.filtreparam = item.clickEventParam;
        this.getAllUsers();
      } else {

        item.class = 'dropdown-item';
      }
    });
  }
  getAccountId() {
    this.spinner.show();
    this._account.getAccountId().subscribe(data => {
      this.spinner.hide();
      this.accountList = data;
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    });
  }
  getContactDetails(event) {
    if (event == undefined) {
      this.contactDetail = [];
      return;
    }
    if (event.sapAccountNumber == undefined || event.sapAccountNumber == null || event.sapAccountNumber == '') {
      this.contactDetail = [];
      return;
    }
    this.spinner.show();
    this._account.getContactDetailsNotUser(event.sapAccountNumber).subscribe(data => {
      this.spinner.hide();
      this.contactDetail = data;
      this.contactDetail.forEach(contact => {
        if (contact.middleName != null && contact.middleName !== '' && contact.middleName != undefined) {
          contact.fullName = contact.firstName + ' ' + contact.middleName + ' ' + contact.lastName;
        } else {
          contact.fullName = contact.firstName + ' ' + contact.lastName;
        }

      })
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  forGetContactDetailsWhenOneValueRecivedInAccount(acNumber) {
    this.spinner.show();
    this._account.getContactDetails(acNumber).subscribe(data => {
      this.contactDetail = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      alert('something went wrong')
    });
  }
  getAuthorities() {
    this.spinner.show();
    this._batuserservice.getAuthorities().subscribe(data => {
      this.userRoleList = data;
      if (data != null) {
        this.spinner.hide();
      }

    }, error => {
      this.spinner.hide();
      alert('something went wrong')
    });
  }

  createLoginUser() {
    if (!this.editMode) {
      if (this.reqUserData.accountSapNumber == undefined || this.reqUserData.accountSapNumber == null || this.reqUserData.accountSapNumber == "") {
        this._global.alert({ type: 'alert', message: 'Please Select Account Details' });
        return;
      }
      if (this.reqUserData.contactId == undefined || this.reqUserData.contactId == null || this.reqUserData.contactId == 0) {
        this._global.alert({ type: 'alert', message: 'Please Select Contact Details' });
        return;
      }
      if (this.reqUserData.password == undefined || this.reqUserData.password == null || this.reqUserData.password == '' || this.reqUserData.password != this.reqUserData.confirmPassword) {
        this._global.alert({ type: 'alert', message: 'Please Provide Valid Password And Confirm Password' });
        return;
      }
      if (this.reqUserData.password.length <= 4) {
        this._global.alert({ type: 'alert', message: 'Please Provide Valid Password, Password Must Have 5 Characters' });
        return;
      }
    }

    if (this.reqUserData.userProfileId == undefined || this.reqUserData.userProfileId == null || this.reqUserData.userProfileId == '') {
      this._global.alert({ type: 'alert', message: 'Please Select User Profile`' });
      return;
    }
    if (this.reqUserData.authorities == undefined || this.reqUserData.authorities == null || this.reqUserData.authorities == '') {
      this._global.alert({ type: 'alert', message: 'Please Select User Role' });
      return;
    }

    if (this.reqUserData.managerEmailId == undefined || this.reqUserData.managerEmailId == null || this.reqUserData.managerEmailId == '') {
      this._global.alert({ type: 'alert', message: 'Please Select Manager' });
      return;
    }

    if (this.reqUserData.approvarManagerEmailId == undefined || this.reqUserData.approvarManagerEmailId == null || this.reqUserData.approvarManagerEmailId == '') {
      this._global.alert({ type: 'alert', message: 'Please Select Approval Manager' });
      return;
    }

    this.spinner.show();
    if (!this.editMode) {
      this._loginService.createLoginUser(this.reqUserData).subscribe(data => {
        this.spinner.hide();
        this.createUserModal.hide();
        this.getAllUsers();
        this._global.alert({ type: '', message: 'Created Successfully' });
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
    else {
      this._loginService.updateLoginInfo(this.reqUserData).subscribe(data => {
        this.spinner.hide();
        this.createUserModal.hide();
        this.getAllUsers();      
        this._global.alert({ type: '', message:'Updated Successfully' });
        
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});
      });
    }

  }
  showAlert(message: string) {
    this.alertDetails.type = 'danger';
    this.alertDetails.message = message;
    this.alertModal.show();
  }
  showMessage(message: string) {
    this.alertDetails.type = 'success';
    this.alertDetails.message = message;
    this.alertModal.show();
  }
  getAllUsers() {
    this.spinner.show();
    this.pageUers.content = [];

    this._batuserservice.getUsers(`filtername=${this.selectedDropdown.filtreparam}&page=${this.selectedDropdown.page}&sort=${this.selectedDropdown.sortname},${this.selectedDropdown.sortorder}`).subscribe(user => {
      this.spinner.hide();
      this.pageUers = user;

    }, error => this.spinner.hide()
    );
    
  }
  getLocation() {
    this._locationservice.locationForlist().subscribe(data => {
      this.locationList = data;
    });
  }
  getUserProfile() {
    this._batuserservice.getUserProfile().subscribe(data => {
      this.userprofiles = data;
    })
  }

  edit($wholeEvent, $event) {
    console.log($wholeEvent);
    this.getUserList($wholeEvent);
    this.pageUers.content.forEach(user => {
      if ($event == user.userName) {
        this.reqUserData = new CreateLoginRequest();
        this.reqUserData.accountSapNumber = user.accountSapNumber;
        this.reqUserData.contactId = user.contactId;
        this.reqUserData.accountNonExpired = user.accountNotExpired;
        this.reqUserData.accountNonLocked = user.accountNonLocked;
        this.reqUserData.approvarManagerEmailId = user.approverManagerEmailId;
        this.reqUserData.authorities = user.userRole;
        this.reqUserData.credentialsNonExpired = user.creadentialNonExpired;
        this.reqUserData.enabled = user.enabled;
        this.reqUserData.managerEmailId = user.managerEmailId;
        this.reqUserData.userProfileId = this.getProfileIdbyName(user.profileName);
        this.editMode = true;
        this.reqUserData.resourceTypeId = user.resourceTypeId;
        this.createUserModal.show();
        this.getContactDetails({ sapAccountNumber: user.accountSapNumber });
        this.onProfileChange({ id: this.reqUserData.userProfileId });
      }
    });
  }

  modalOnHidee() {
    this.reqUserData = new CreateLoginRequest();
    this.editMode = false;
    this.objectAccess = [];
  }
  enableDisableUser(userEmailId, flag) {
    if(flag==false)
    {
      // console.log(flag);
      // console.log(userEmailId);
     this.spinner.show();
     this._batuserservice.enableDisableUsers(userEmailId, flag).subscribe(data => {
      this.spinner.hide();
      // this.getAllUsers();
      this.accountdisablemodel.hide();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    })
    }


  }
  userEmailID:string;
  userDisable(userEmailId,flag){

    this.accountdisablemodel.show();

    this.userEmailID = userEmailId;
      
      

  }
  onLocationCreated($event: LocationRequest) {
    this.getLocation();
    this.createlocationModal.hide();
    this.reqUserData.locationId = $event.locationId;
  }
  onLocationCanled($event) {
    this.createlocationModal.hide();
  }
  onProfileChange($event) {
    this.getProfileDetails($event.id);
  }
  getProfileDetails(id) {

    if (id == undefined || id == null || id == '') {
      return;
    }
    this.spinner.show();
    this._batuserservice.getObjectAccessByProfileId(id).subscribe(data => {
      this.spinner.hide();
      this.objectAccess = data;
    }, errror => {

      this.spinner.hide();
    });
  }
  resetPasswordModalOpen(username: string) {
    this.resetPassword = new ResetpasswordRequest();
    this.resetPassword.userName = username;
    this.userResetPassword.show();
  }
  onResetHide() {
    this.resetPassword = new ResetpasswordRequest();
  }
  resetPasswordDone() {
    if (this.resetPassword.userName == undefined || this.resetPassword.userName == null || this.resetPassword.userName == '') {
      this._global.alert({ type: 'alert', message: 'Some Thing Went Wrong,Please Close And Re-Open Modal' });
      return;
    }
    if (this.resetPassword.newPassword == undefined || this.resetPassword.newPassword == null || this.resetPassword.newPassword == '') {
      this._global.alert({ type: 'alert', message: 'Please Provide A Valid Password' });
      return;
    }
    if (this.resetPassword.newPassword.length < 4) {
      this._global.alert({ type: 'alert', message: 'Please Provide A Valid Password,  Password Must Have At Least 5 Characters' });
      return;
    }
    if (this.resetPassword.newPassword != this.resetPassword.confirmPassword) {
      this._global.alert({ type: 'alert', message: 'Password And Confirm Password Do Not Match' });
      return;
    }
    this.spinner.show();
    this._batuserservice.resetPassword(this.resetPassword).subscribe(data => {
      this.spinner.hide();
      this.userResetPassword.hide();
      this._global.alert({ type: '', message: 'Password Changed' });
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  getProfileIdbyName(profileName) {
    for (let i = 0; i < this.userprofiles.length; i++) {
      if (this.userprofiles[i].profileName == profileName) {
        return this.userprofiles[i].id;
      }
    }
  }


  getResourceTypeForList() {
    this._serviceResourceService.getResourceTypeForList().subscribe(data => {
      this.resourceTypeList = data;
    });
  }


  getUserList(event) {
    if (event === undefined) {
      this.usersforlist = [];
      return;
    }
    if (event.contactId === undefined || event.contactId === null || event.contactId === 0) {
      this.usersforlist = [];
      return;
    }
    this.spinner.show();
    this._batuserservice.getUserForList(event.contactId).subscribe(data => {
      this.usersforlist = data;
      this.usersforlist.forEach(user => {
        if (user.userMiddleName != null && user.userMiddleName !== '' && user.userMiddleName != undefined) {
          user.fullName = user.userFirstName + ' ' + user.userMiddleName + ' ' + user.userLastName;
        } else {
          user.fullName = user.userFirstName + ' ' + user.userLastName;
        }
      })
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
    });
  }
  searchContent = {
    userId: '',
    name: '',
    account: '',
    accountSapNumber: '',
    employeeCode: '',
    profile: '',
    role: '',
    manager: '',
    approverManager: ''

  };
  getAdvanceSearchresult() {

    if (this.searchContent.userId == "" && this.searchContent.name == "" && this.searchContent.userId == '' &&
      this.searchContent.name == '' &&
      this.searchContent.account == '' &&
      this.searchContent.accountSapNumber == '' &&
      this.searchContent.employeeCode == '' && this.searchContent.profile == '' && this.searchContent.role == '' &&
      this.searchContent.approverManager == '' && this.searchContent.manager == '') {
      this._global.alert({ type: 'alert', message: 'Please Enter Valid Input To Search' });
    }
    else {
      this.spinner.show();
      this._batuserservice.getSearchResult(this.searchContent).subscribe(data => {
        this.spinner.hide();
        this.pageUers.content = data;
      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
    }

  }
  ResetSearchresult() {
    this.searchContent.userId = ''
    this.searchContent.name = ''
    this.searchContent.userId = ''
    this.searchContent.name = ''
    this.searchContent.account = ''
    this.searchContent.accountSapNumber = ''
    this.searchContent.employeeCode = ''
    this.searchContent.profile = ''
    this.searchContent.role = ''
    this.searchContent.approverManager = ''
    this.searchContent.manager = ''
    this.getAllUsers();

  }

// Disable The User
  onClickDisableUser(){
    console.log("Ok");
    this.enableDisableUser(this.userEmailID,false);
  }

  pagenumber: any = null;
  jumpPage(pagenumber) {
    if(pagenumber == null ||pagenumber==0|| pagenumber == ""){    
      this._global.alert({type:'alert',message:"Please Enter Valid Input"}); 
    }
   else if (this.pageUers.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
    else {
      this.selectedDropdown.page = pagenumber - 1;
      this.getAllUsers();
    }
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
