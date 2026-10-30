import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { BRCRepairInventoryDispatchRequest } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';

@Component({
  selector: 'app-dispatch-customer',
  templateUrl: './dispatch-customer.component.html',
  styleUrls: ['./dispatch-customer.component.scss']
})
export class DispatchCustomerComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onDispatchCustomerCreated: EventEmitter<BRCRepairInventoryDispatchRequest> = new EventEmitter<BRCRepairInventoryDispatchRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  message: string;
  _isEditMode: boolean;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];


  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editDispatchData(dispatchRequest: BRCRepairInventoryDispatchRequest) {
    this.dispatchRequest = dispatchRequest;
    this.dispatchRequest.dispatcherName = dispatchRequest.dispatcherName;
  }
  get editDispatchData(): BRCRepairInventoryDispatchRequest {
    return this.dispatchRequest;
  }

  dispatchRequest: BRCRepairInventoryDispatchRequest = new BRCRepairInventoryDispatchRequest();
  popupNgClass: string = 'alert alert-danger center center-block';

  constructor(private spinner: NgxSpinnerService, private mangaeInventoryService: ManageInventoryService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
  }

  postCreate() {
    if (this.dispatchRequest.dispatchMode == null || this.dispatchRequest.dispatchMode =='0') {
      this.message = ' Please fill the dispatch mode';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.dispatchShipmentNo == null || this.dispatchRequest.dispatchShipmentNo =='') {
      this.message = ' Please fill the dispatch shippment number';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.dispatchShipmentNo != undefined && (this.dispatchRequest.dispatchShipmentNo).length >= 200) {
      this.message = ' Please fill the dispatch shippment number under 200 characters';
      this.popupSuccessModel.show();
    }    
    else if (this.dispatchRequest.sapDeleveryChallanNumber == null || this.dispatchRequest.sapDeleveryChallanNumber =='') {
      this.message = ' Please fill the sap delivery challan number';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.sapDeleveryChallanNumber != undefined && (this.dispatchRequest.sapDeleveryChallanNumber).length >= 200) {
      this.message = ' Please fill the sap delivery challan number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.podNumber == null || this.dispatchRequest.podNumber == '') {
      this.message = ' Please fill the pod number';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.podNumber != undefined && (this.dispatchRequest.podNumber).length >= 200) {
      this.message = ' Please fill the pod number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.dispatchDate == null) {
      this.message = ' Please fill the dispatch date';
      this.popupSuccessModel.show();
    }
    else if (this.dispatchRequest.dispatchRemark != undefined && (this.dispatchRequest.dispatchRemark).length >= 500) {
      this.message = ' Please fill the remarks under 500 characters';
      this.popupSuccessModel.show();
    }
    else {
      this.spinner.show();
      this.mangaeInventoryService.saveDispatch(this.dispatchRequest, this.dispatchRequest.repariItemId).subscribe(data => {
        this.message = 'Submitted Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.onDispatchCustomerCreated.emit(data);
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
    this.dispatchRequest = new BRCRepairInventoryDispatchRequest();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
