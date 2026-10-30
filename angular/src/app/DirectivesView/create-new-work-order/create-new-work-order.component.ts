import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AccountCreate, AccountsForList, AssetsForList } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { WorkOrderService } from '../../services/workoders.services';
import { TypeaheadConfig, TypeaheadMatch } from 'ngx-bootstrap/typeahead';

@Component({
  selector: 'app-create-new-work-order',
  templateUrl: './create-new-work-order.component.html',
  styleUrls: ['./create-new-work-order.component.css']
})
export class CreateNewWorkOrderComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<AccountCreate> = new EventEmitter<AccountCreate>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  accountList: Array<AccountsForList> = [];
  assetList: Array<AssetsForList> = [];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editAccountData(accountCreate: AccountCreate) {
    this.createAccount = accountCreate;
  }

  get editAccountData(): AccountCreate {
    return this.createAccount;
  }

  _isEditMode: boolean;
  message: string;
  istrue: boolean;
  createAccount: AccountCreate = new AccountCreate();
  caseList: Array<string> = [];
  accountForList: Array<string> = [];
  isCaseSelected: string;
  isAssetSelected: string;
  isAccountSelected: string;
  isContactSelected: string;
  isWorkTypeSelected: string;
  faPlus: string = "btn btn-primary fa fa-plus";
  cantactList: any;
  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices, private _workOrderService: WorkOrderService) {

  }

  ngOnInit() {
    this.ownerName();
    this.getAccountForList();
    this.getAssetForList();
    this.isCaseSelected = "fa fa-search";
    this.isAssetSelected = "fa fa-search";
    this.isAccountSelected = "fa fa-search";
    this.isContactSelected = "fa fa-search";
    this.isWorkTypeSelected = "fa fa-search";
    this.faPlus;
  }

  getAccountForList() {
    this._workOrderService.getAccountsForList().subscribe(data => {
      this.accountList = data;
    })
  }

  getAssetForList() {
    this._workOrderService.getAssetsForList().subscribe(data => {
      this.assetList = data;
    })
  }

  // open drop down 
  getTypeaheadConfig(): TypeaheadConfig {
    return Object.assign(new TypeaheadConfig(), { hideResultsOnBlur: false });
  }
  // parent account drop down
  ParentAccountTimes() {
    this.isAccountSelected = "fa fa-search";
    this.createAccount.accountName = null;
  }
  onKeydownParentAccount($event) {
    this.isAccountSelected = "fa fa-times";
  }

  onSelectParentAccount($event: TypeaheadMatch): void {
    this.createAccount.sampAccountNumber = $event.item.sapAccountNumber;

  }

  moveRight() {

  }
  moveLeft() {

  }


  loginData;
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.onCancle.emit('cancled');
  }

  postCreateAccount() {

  }

  openPopUp() {
    if (this.istrue == true) {
      this.message = "Submitted Successfully";
      setTimeout(() => {
        this.spinner.hide();
        this.popupSuccessModel.show();

        setTimeout(() => {
          this.cancelData()
          this.popupSuccessModel.hide()
        }, 2000
        );


      }, 1000
      );



    } else {
      this.message = "Submittion Failed";
      this.spinner.show();
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
  workTypeList: Array<string> = [];
  available: string;
  chosen: string;
  onKeydownCase($event) { }
  onSelectCase($event) { }
  CaseTimes() { }
  addNewCase() { }
  onKeydownSerialNo($event) { }
  onSelectSerialNo($event) { }
  SerialNoTimes() { }
  addNewAsset() { }
  addNewAccount() { }
  onKeydownContact($event) { }
  onSelectContact($event) { }
  addNewContact() { }
  onKeydownWorkType($event) { }
  onSelectWorkType($event) { }
  WorkTypeTimes() { }
  addNewWorktype() { }

}
