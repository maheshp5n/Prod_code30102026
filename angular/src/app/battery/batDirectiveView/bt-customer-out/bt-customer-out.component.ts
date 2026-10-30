import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtCustomerInventoryService } from '../../bat_services/bt-customer-inventory.service';
import { BtCustomerInventoryOutRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-customer-out',
  templateUrl: './bt-customer-out.component.html',
  styleUrls: ['./bt-customer-out.component.css']
})
export class BtCustomerOutComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];
  outInventoryReq: BtCustomerInventoryOutRequest = new BtCustomerInventoryOutRequest();
  courierPartner = ['Aanpurna Logistics', 'DTDC', 'Fedex', 'Gati KWE', 'R.N Logistics', 'Rivigo', 'Somharsh Enterprise', 'Speed Air Logistics', 'TrackOn Courier', 'Manually Handover'];
  today = new Date();
  brcCaseNumber;

  @Input()
  set customerInventoryOut(inventoryOut) {
   this.brcCaseNumber = inventoryOut.customerInventoryManualId;
   this.outInventoryReq.inId = inventoryOut.customerInventoryInId;

  }

  get customerInventoryOut(): any {
     return this.outInventoryReq;
  }

  @Output()
  onSaveOut: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancelOutInventory: EventEmitter<String> = new EventEmitter<string>();

  constructor(private _spinner: NgxSpinnerService, private _btInventoryOutService: BtCustomerInventoryService, private _global: GlobalserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit(): void {
  }

  saveCustomerInventoryOut() {
    if (this.outInventoryReq.inId == null || this.outInventoryReq.inId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill BRC Case No.' });
      return;
    }
    else if (this.outInventoryReq.actualOutDate == '' || this.outInventoryReq.actualOutDate == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Dispatched Date' });
      return;
    }
    else if (this.outInventoryReq.outLrDocNumber == '' || this.outInventoryReq.outLrDocNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Exicom Documentation No.' });
      return;
    }
    else if (this.outInventoryReq.outCourierPartner == '0' || this.outInventoryReq.outCourierPartner == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Courier Partner' });
      return;
    }
    else if (this.outInventoryReq.outdeleveryChallanNumber == '' || this.outInventoryReq.outdeleveryChallanNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Exicom Delivery Challan No.' });
      return;
    }
    else if (this.outInventoryReq.trackingNumber == '' || this.outInventoryReq.trackingNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill  E-way Bill No.' });
      return;
    }
    this._spinner.show();
    this._btInventoryOutService.btpostCustomerInventoryOut(this.outInventoryReq).subscribe(data => {
      this._spinner.hide();
      this.onSaveOut.emit(data);
      this.onCancelOutInventory.emit('cancelled');
    }, error => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  cancelCustomerInventoryOut() {
    this.onCancelOutInventory.emit('cancelled');
  }

}
