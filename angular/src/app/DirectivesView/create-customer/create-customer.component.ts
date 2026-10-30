import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { CustomerForList } from '../../services/model';
import { Loginservices } from '../../services/login.services';
import { CustomerService } from '../../services/customers.services';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.scss']
})
export class CreateCustomerComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

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


  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices, private customerService: CustomerService) { }

  ngOnInit() {
    this.ownerName();
  }
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }

  postCreateAccount() {
    if (this.parentcustomer.customerName == null || this.parentcustomer.customerName == '' || this.parentcustomer.customerName == undefined) {
      this.message = 'Please fill the customer name';
      this.popupSuccessModel.show();
    } 
    else if (this.parentcustomer.customerName != undefined && (this.parentcustomer.customerName).length > 200) {
      this.message = 'Please fill the customer name under 200 characters';
      this.popupSuccessModel.show();
    } 
    else {
      this.spinner.show();
      this.customerService.postCustomer(this.parentcustomer).subscribe(data => {
        this.message = 'Submitted Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.onAccountCreated.emit(data);
        if (data != null) {
          this.spinner.hide();
        }
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
  }

  cancelData() {
    this.parentcustomer = new CustomerForList();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }


}
