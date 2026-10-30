import { DatePipe } from '@angular/common';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { __read } from 'tslib';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtCustomerInventoryService } from '../../bat_services/bt-customer-inventory.service';
import { BtRmaService } from '../../bat_services/bt-rma.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtAssetForLIst, BtCustomerInventoryInRequest, BtProductList, BtRmaList } from '../../bt_model';

@Component({
  selector: 'app-bt-customer-in',
  templateUrl: './bt-customer-in.component.html',
  styleUrls: ['./bt-customer-in.component.css']
})
export class BtCustomerInComponent implements OnInit {

  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();
  circleList: any[];
  loginData;
  message: string;
  minimumDate = new Date();
  minReceiveDate = new Date();
  btProductList: Array<BtProductList> = [];
  rmaManualIdList: Array<BtRmaList> = [];
  BtassetForList: Array<BtAssetForLIst> = [];
  stageList = ['Receive Confirm', 'Assigned to Engineer', 'Repaired by Engineer', 'QC Approved', 'Dispatched', 'Customer GRN Done', 'Document Awaited', 'Document in transit', 'Invoiced'];
  statusList = ['Open', 'Under Repaired', 'Repaired/BER'];
  customerPlantList = ['Project', 'O&M', 'Spare Sale'];
  packingCondtionList = ['Blue Box', 'Corrugated Box', 'Wodden Box', 'Bubble Wrap', 'Without Packing'];
  customerInventoryInReq: BtCustomerInventoryInRequest = new BtCustomerInventoryInRequest();

  @Output()
  onSave: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancel: EventEmitter<string> = new EventEmitter<string>();
  _brcdetails;
  editMode = false;
  @Input() set brccasedetails(_brc: any) {
    this._brcdetails = _brc;
    if (_brc) {
      this.editMode = true;
      this.customerInventoryInReq = new BtCustomerInventoryInRequest();
      this.customerInventoryInReq.id = _brc.customerInventoryInId;
      this.customerInventoryInReq.accountSapNumber = _brc.customerAccountSapNumber;
      this.customerInventoryInReq.assetsSerialNumber = _brc.customerInventoryAssetsSerialNumber;
      this.customerInventoryInReq.challanNumber = _brc.customerChallanNumber;
      this.customerInventoryInReq.circle = _brc.customerCircleName;
      this.customerInventoryInReq.contactName = _brc.contactName;
      this.customerInventoryInReq.contactNumber = _brc.contactNumber;
      this.customerInventoryInReq.customerCurierPartnarName = _brc.customerCourierpartnerName;
      this.customerInventoryInReq.customerInventoryStage = _brc.customerInventoryStage;
      this.customerInventoryInReq.customerInventoryStatus = _brc.customerInventoryStatu;
      this.customerInventoryInReq.customerLrNumber = _brc.customerLRNumber;
      this.customerInventoryInReq.customerPlant = _brc.customerPlant;
      this.customerInventoryInReq.dummayLocationName = _brc.dummayLocation;
      this.customerInventoryInReq.dummayWarehouseName = _brc.dummayWarehouse;
      this.customerInventoryInReq.pacingCondation = _brc.packingCondation;
      this.customerInventoryInReq.partCode = _brc.customerInventoryProductPartcode;
      this.customerInventoryInReq.physicalDamage = _brc.physicalDamage;
      this.customerInventoryInReq.receiveChallanDate = _brc.receivedChallanDate;
      this.customerInventoryInReq.receiveDate = _brc.receivedDate;
      this.customerInventoryInReq.remarks = _brc.cRemarks;
      this.customerInventoryInReq.rmaManualId = _brc.rmaManualId;
    }
    else {
      this.editMode = false;
      this.getCircleList();
      this.getLocation();
    }
  }
  get brccasedetails() {
    return this._brcdetails;
  }

  constructor(private _btCirleService: BtcircleService, private _spinner: NgxSpinnerService, private _customerInService: BtCustomerInventoryService,
    private loginService: Loginservices, private _global: GlobalserviceService, private _btRmaService: BtRmaService,
    private _btAssetService: BTassetserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] },
    ];

    let mindate = new Date();
    mindate.setMonth(0);
    mindate.setDate(8);
    mindate.setFullYear(2022);    
    console.log(mindate);

    this.minimumDate = mindate;
    console.log(this.minimumDate);
    
  }
  ngOnInit(): void {
    this.getCircleList();
    this.getLocation();
  }
  getLocation() {
    this.loginData = this.loginService.getLoggedInUser();
  }
  getCircleList() {
    this._spinner.show();
    this._btCirleService.getCircleForList().subscribe(data => {
      this._spinner.hide();
      this.circleList = data;
    }, error => {
      this._spinner.hide();
    })

  }
  searchBtAsset($event) {
    this._btAssetService.btAssetForLIst($event.term).subscribe(data => {
      const temp = [];
      data.forEach(d => {
        let btAssetList = new BtAssetForLIst();
        btAssetList.serialNumber = d.serialNumber;
        btAssetList.productPartCode = d.productPartCode;
        temp.push(btAssetList)
      });
      this.BtassetForList = temp;
    }, err => {
    })
  }
  getProductPartByAssetId(assetId) {
    this.BtassetForList.forEach(d => {
      if (assetId == d.serialNumber) {
        this.customerInventoryInReq.partCode = d.productPartCode;
      }
    });
  }
  searchRma($event) {
    this._btRmaService.rmaForList($event.term).subscribe(data => {
      const temp = [];
      data.forEach(d => {
        let btRmaList = new BtRmaList();
        btRmaList.rmaManualId = d.rmaManualId;
        temp.push(btRmaList)
      });
      this.rmaManualIdList = temp;
    }, err => {
    })
  }
  saveCustomerInventoryIn() {
    if (!this.editMode) {
      if (this.customerInventoryInReq.assetsSerialNumber == '' || this.customerInventoryInReq.assetsSerialNumber == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Asset Serial No.' });
        return;
      }
      else if (this.customerInventoryInReq.partCode == '' || this.customerInventoryInReq.partCode == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Part Code' });
        return;
      }
      else if (this.customerInventoryInReq.customerInventoryStage == '0' || this.customerInventoryInReq.customerInventoryStage == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill customer Inventory Stage' });
        return;
      }
      else if (this.customerInventoryInReq.customerInventoryStatus == '0' || this.customerInventoryInReq.customerInventoryStatus == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Customer Inventory Status' });
        return;
      }
      else if (this.customerInventoryInReq.dummayLocationName == '' || this.customerInventoryInReq.dummayLocationName == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Location' });
        return;
      }
      else if (this.customerInventoryInReq.contactName == '' || this.customerInventoryInReq.contactName == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Contact Name' });
        return;
      }
      else if (this.customerInventoryInReq.contactNumber == '' || this.customerInventoryInReq.contactNumber == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Contact Number' });
        return;
      }
      else if (this.customerInventoryInReq.challanNumber == '' || this.customerInventoryInReq.challanNumber == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Receive Challan No.' });
        return;
      }
      else if (this.customerInventoryInReq.receiveChallanDate == '' || this.customerInventoryInReq.receiveChallanDate == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Receive Challan Date' });
        return;
      }
      else if (this.customerInventoryInReq.circle == '' || this.customerInventoryInReq.circle == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Cirle Name' });
        return;
      }
      else if (this.customerInventoryInReq.customerLrNumber == '' || this.customerInventoryInReq.customerLrNumber == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Customer LR No.' });
        return;
      }
      else if (this.customerInventoryInReq.customerCurierPartnarName == '' || this.customerInventoryInReq.customerCurierPartnarName == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill ACustomer Courier Partner Name' });
        return;
      }
      else if (this.customerInventoryInReq.customerPlant == '0' || this.customerInventoryInReq.customerPlant == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Customer Plant' });
        return;
      }
      else if (this.customerInventoryInReq.receiveDate == '' || this.customerInventoryInReq.receiveDate == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill Receive Date' });
        return;
      }
      else if (this.customerInventoryInReq.pacingCondation == '0' || this.customerInventoryInReq.pacingCondation == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill  Packing Condition' });
        return;
      }
      else if (this.customerInventoryInReq.physicalDamage == '0' || this.customerInventoryInReq.physicalDamage == undefined) {
        this._global.alert({ type: 'alert', message: 'Please Fill  Physical Damage' });
        return;
      }
      else {

        this._spinner.show();
        this._customerInService.btpostCustomerInventoryIn(this.customerInventoryInReq).subscribe(data => {
          this._spinner.hide();
          this._global.alert({ type: '', message: `BRC Case ${data.customerInventoryInManualId} Is Created!` });
          this.onSave.emit(data);

        }, err => {
          this._spinner.hide();
          this._global.alert({ type: 'alert', message: err.error.message });
        });


      }

    }
    else {


      this._spinner.show();
      this._customerInService.updateCustomerInventoryIn(this.customerInventoryInReq).subscribe(data => {
        this._spinner.hide();
        this.onSave.emit(data);

      }, error => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: '' + error.error.message });
      });

    }




  }
  cancelCustomerInventoryIn() {
    this.onCancel.emit('cancelled');
  }
  LimteExceed: boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if (this.LimteExceed == true) {
      return false;
    }
    return true;

  }
  onKey(event): any {
    if (event.target.value.length == 10) {
      if (FieldValidation.isValidMobileNumber(event.target.value) === false) {
        this.message = "Please Fill Valid Phone No";
        this._global.alert({ type: 'alert', message: this.message });
        return false;
      }
    }
    if (event.target.value.length > 9) {
      this.LimteExceed = true;

    }
    else {
      this.LimteExceed = false;
    }
  }

}
