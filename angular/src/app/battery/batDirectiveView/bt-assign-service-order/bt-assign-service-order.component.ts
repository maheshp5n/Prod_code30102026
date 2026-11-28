import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtJobCardServiceService } from '../../bat_services/bt-job-card-service.service';
import { BtServiceOrderService } from '../../bat_services/bt-service-order.service';
import { BtServiceOrderAssignReq, BtServiceOrderRecieveItems } from '../../bt_model';

@Component({
  selector: 'app-bt-assign-service-order',
  templateUrl: './bt-assign-service-order.component.html',
  styleUrls: ['./bt-assign-service-order.component.css']
})
export class BtAssignServiceOrderComponent implements OnInit {
  @Output()
  assignSO: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  cancled: EventEmitter<any> = new EventEmitter<any>();
  btServiceOrderAssgnItemsList: Array<BtServiceOrderRecieveItems> = [];
  btServiceOrderAssignReq: Array<BtServiceOrderAssignReq> = [];
  repairEngineerList: Array<any> = [];
  global: boolean = true;
  repairEng: string = '0';
  soNumber: number = undefined;
  @Input()
  set serviceOrder(serviceOrderId: number) {
    this.soNumber = serviceOrderId;
    if (serviceOrderId == undefined) {
      this.getItemsForPendingToAssign(undefined);
    }
    else {
      this.getItemsForPendingToAssign(this.soNumber);
    }

    console.log(`Service Order id for assigned to eng ${serviceOrderId} and so number is ${this.soNumber}`);
  }
  get(): number {
    return this.soNumber;
  }

  constructor(private _spinner: NgxSpinnerService,
    private _btSOService: BtServiceOrderService,
    private _btJobCardService: BtJobCardServiceService, private _global: GlobalserviceService) { }


  ngOnInit(): void {
    this.getListOfRepairEngineer();
  }
  getItemsForPendingToAssign(serviceOrderId) {
    this._spinner.show();
    this._btSOService.btSOItemsForAssign(serviceOrderId).subscribe(data => {
      this._spinner.hide();
      this.btServiceOrderAssgnItemsList = data;
      this.btServiceOrderAssgnItemsList.forEach(d => {
        d.isChecked = true;
      })
      console.log(this.btServiceOrderAssgnItemsList)
    }, err => {
      this._spinner.hide();
    })

  }
  globalselectchange(check) {
    const lent = this.btServiceOrderAssgnItemsList.length;
    for (let i = 0; i < lent; i++) {
      this.btServiceOrderAssgnItemsList[i].isChecked = this.global;
    }
  }
  getListOfRepairEngineer() {
    this._spinner.show();
    this._btJobCardService.btServiceOrderRepairEngineerList().subscribe(data => {
      this._spinner.hide();
      this.repairEngineerList = data;
    }, err => {
      this._spinner.hide();
    })

  }
  SaveAssignServiceOrder() {
    if (this.repairEng == '0' || this.repairEng == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Repair Engineer Name' });
      return;
    }
    this.btServiceOrderAssgnItemsList.forEach(d => {
      if (d.isChecked) {
        let tm = new BtServiceOrderAssignReq();
        tm.assignedTo = this.repairEng;
        tm.serviceOrderItemId = d.serviceOrderItemId;
        this.btServiceOrderAssignReq.push(tm);
      }
    });
    if (this.btServiceOrderAssignReq.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please Select Items To Assign' });
      return;
    }
    this._spinner.show();
    this._btJobCardService.postBtAssignServiceOrder(this.btServiceOrderAssignReq).subscribe(data => {
      this._spinner.hide();
      this._global.alert({ type: '', message: `Assign SO Successfully!` });
      this.getItemsForPendingToAssign(this.soNumber);
      console.log(this.btServiceOrderAssignReq);

      this.btServiceOrderAssignReq = [];
    }, err => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });
  }
  canelAssignServiceOrder() {
    this.cancled.emit('cancelled');

  }

}
