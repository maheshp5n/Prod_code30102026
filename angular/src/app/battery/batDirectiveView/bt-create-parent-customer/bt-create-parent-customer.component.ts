import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import { CustomerForList } from '../../../services/model';

@Component({
  selector: 'app-bt-create-parent-customer',
  templateUrl: './bt-create-parent-customer.component.html',
  styleUrls: ['./bt-create-parent-customer.component.scss']
})
export class BtCreateParentCustomerComponent implements OnInit {
 // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<CustomerForList> = new EventEmitter<CustomerForList>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  parentcustomer: CustomerForList = new CustomerForList();
  loginData;
  message: string;
  _isEditMode: boolean;

  popupNgClass: string = 'alert alert-danger center center-block';

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(accountCreate: CustomerForList) {
    this.parentcustomer = accountCreate;
  }
  get editAccountData(): CustomerForList {
    return this.parentcustomer;
  }


  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices, private customerService: CustomerService,
    private _global: GlobalserviceService) { }

  ngOnInit() {
    this.ownerName();
  }
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }

  postCreateAccount() {
    if (this.parentcustomer.customerName == null || this.parentcustomer.customerName == '' || this.parentcustomer.customerName == undefined) {   
      this._global.alert({ type: 'alert', message: 'Please Fill The Customer Name' });
    } 
    else if (this.parentcustomer.customerName != undefined && (this.parentcustomer.customerName).length > 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Customer Name Under 200 Characters' });
    } 
    else {
      this.spinner.show();
      this.customerService.postCustomer(this.parentcustomer).subscribe(data => {
        this._global.alert({ type: '', message: data.customerName+' Submitted Successfully' });
        this.onAccountCreated.emit(data);            
        this.onCancle.emit('cancled');
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
    }
  }

  cancelData() {
    this.parentcustomer = new CustomerForList();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }


}
