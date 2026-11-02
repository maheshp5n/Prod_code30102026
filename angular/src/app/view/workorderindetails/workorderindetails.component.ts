import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AccountCreate } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';

@Component({
  selector: 'app-workorderindetails',
  templateUrl: './workorderindetails.component.html',
  styleUrls: ['./workorderindetails.component.scss']
})
export class WorkorderindetailsComponent implements OnInit {
  @ViewChild('createWorkOrderModel') public createWorkOrderModel: ModalDirective;
  
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  
  account: AccountCreate = new AccountCreate();
  accountList: Array<AccountCreate> = [];
  
  createAccount: AccountCreate = new AccountCreate();
  
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Work Order';
  EDIT_ACCOUNT_HEADING: string = 'Fill Consumption Detail';


  sapId: string = '';
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  addressDisplay1 = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _accountServices: AccountService,
    private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];  
    });
  }

  ngOnInit() {
    this.getAccount();
    this.ownerName();
  }

  loginData;
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()

  }
  getAccount() {
    this.spinner.show();
    this._accountServices.getAccount(this.sapId).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.account = data;
    }); 

  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeAddressDisplay1() {
    this.addressDisplay1.open = !this.addressDisplay1.open
    if (this.addressDisplay1.open) {
      this.addressDisplay1.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay1.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getAccount();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.getContacts();
    }
    return false;
  }
  
  createEditModelshow(createAccount: AccountCreate) {
    //this.createAccount=createAccount;   
    this.spinner.show();
    this._accountServices.getAccount(createAccount.sampAccountNumber).subscribe(data => {
      this.createAccount = data;
      if (data != null) {
        this.spinner.hide();
      }      
    })
    //this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createWorkOrderModel.show();
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createWorkOrderModel.hide();
  }
  

}
