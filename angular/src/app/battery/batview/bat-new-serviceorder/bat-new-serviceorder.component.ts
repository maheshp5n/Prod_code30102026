import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtServiceOrderService } from '../../bat_services/bt-service-order.service';
import { BtInventoryStock, BtInventoryStockByProductId, BtSericeOrderRequest, BtServiceOrderItems, BtServiceOrderRepairCenterLIst } from '../../bt_model';

@Component({
  selector: 'app-bat-new-serviceorder',
  templateUrl: './bat-new-serviceorder.component.html',
  styleUrls: ['./bat-new-serviceorder.component.css']
})
export class BatNewServiceorderComponent implements OnInit {
  btRepairCenterlist: Array<BtServiceOrderRepairCenterLIst> = [];
  btServiceOrderRequest: BtSericeOrderRequest = new BtSericeOrderRequest();
  challanNumber;
  requestTableHeaders = [
    {
      id: 1,
      name: 'Part code'
    },
    {
      id: 4,
      name: 'Description'
    },
    {
      id: 5,
      name: 'Type'
    },
    {
      id: 2,
      name: 'Exicom Faulty Qty',
    },
    {
      id: 3,
      name: 'Customer Faulty Qty'
    }
  ];
  btInventoryStockList: Array<BtInventoryStock> = [];
  constructor(private _global: GlobalserviceService, private _router: Router, private _btserviceOrderService: BtServiceOrderService,
    private _spinner: NgxSpinnerService) {
  }

  ngOnInit(): void {
   
  }
  getPendingStockList() {
    this._spinner.show();
    this._btserviceOrderService.getPendingStock().subscribe(data => {
      this._spinner.hide();
      this.btInventoryStockList = data;
      this.btInventoryStockList.forEach(d => {
        d.expended = true;
        d.btinventoryStock = [];
        d.exicomSerialnumbers = [];
        d.globalcustoermerinventorycheck = true;
        d.globalcustomerinventoryinput = new BtInventoryStockByProductId();
        this.getCustomerInventoryStockByProductId(d.productId,this.challanNumber);
      });

    }, error => {
      this._spinner.hide();
    });
  }
  getCustomerInventoryStockByProductId(productId,challanNumber) {
    this._spinner.show();
    this._btserviceOrderService.btCustomerInventoryPendingStockByIdAndChallanNumber(productId,this.challanNumber).subscribe(data => {
      this._spinner.hide();
      const letn = this.btInventoryStockList.length;
      data.forEach(d => {
        d.isChecked = true;
        d.isCustome = false;
      })
      for (let i = 0; i < letn; i++) {

        if (this.btInventoryStockList[i].productId === productId) {
          this.btInventoryStockList[i].btinventoryStock = data;
        }
      }
    }, error => {
      this._spinner.hide();
    })
  }
  addExiSerialNoList: any[] = [{
    exiSerialNo: ''
  }];


  changeArrow(item) {
    const lent = this.btInventoryStockList.length;
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId === item.productId) {
        this.btInventoryStockList[i].expended = !this.btInventoryStockList[i].expended;
      }
    }

  }

  addExicomInventory(item) {
    const lent = this.btInventoryStockList.length;
    if (item.globalexiserialnumber == undefined || item.globalexiserialnumber == null || item.globalexiserialnumber == '') {
      this._global.alert({ type: 'alert', message: 'Can not insert blank serial number' });
      return;
    }
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId === item.productId) {
        const ser = this.btInventoryStockList[i].globalexiserialnumber;
        let found = false;
        for (let j = 0; j < this.btInventoryStockList[i].exicomSerialnumbers.length; j++) {
          if (this.btInventoryStockList[i].exicomSerialnumbers[j] == ser) {
            found = true;
            break;
          }
        }
        if (!found) {
          this.btInventoryStockList[i].exicomSerialnumbers.push(ser);
          this.btInventoryStockList[i].globalexiserialnumber = '';
        }
        else {
          this._global.alert({ type: 'alert', message: 'Serial number ' + ser + ' for partcode ' + item.productPartcode + ' already exists' });
        }

      }
    }


  }

  remoteSerialNumber(sr, item) {
    const lent = this.btInventoryStockList.length;
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId === item.productId) {
        const temp = [];
        for (let j = 0; j < this.btInventoryStockList[i].exicomSerialnumbers.length; j++) {
          if (this.btInventoryStockList[i].exicomSerialnumbers[j] != sr) {
            temp.push(this.btInventoryStockList[i].exicomSerialnumbers[j]);
          }

        }
        this.btInventoryStockList[i].exicomSerialnumbers = temp;
      }
    }
  }

  changeInCustomerInvetoryCheckBox(item) {
    const lent = this.btInventoryStockList.length;
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId === item.productId) {
        const itemlent = this.btInventoryStockList[i].btinventoryStock.length;
        for (let j = 0; itemlent; j++) {
          this.btInventoryStockList[i].btinventoryStock[j].isChecked = this.btInventoryStockList[i].globalcustoermerinventorycheck;
        }
      }
    }
  }

  addCustomerSerialNumber(item) {
    const lent = this.btInventoryStockList.length;
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId == item.productId) {
        const btproduct = Object.assign({}, this.btInventoryStockList[i].globalcustomerinventoryinput);
        if (btproduct.serialNumber == undefined || btproduct.serialNumber == null || btproduct.serialNumber == '') {
          this._global.alert({ type: 'alert', message: 'Can not insert blank serial number' });
          return;
        }
        let found = false;
        for (let j = 0; j < this.btInventoryStockList[i].btinventoryStock.length; j++) {
          if (this.btInventoryStockList[i].btinventoryStock[j].serialNumber == btproduct.serialNumber) {
            found = true;
            break;
          }
        }
        if (!found) {
          btproduct.productDescription = this.btInventoryStockList[i].productDescription;
          btproduct.productId = this.btInventoryStockList[i].productId;
          btproduct.productType = this.btInventoryStockList[i].productType;
          btproduct.isChecked = true;
          btproduct.isCustome = true;
          this.btInventoryStockList[i].btinventoryStock.push(btproduct);
          this.btInventoryStockList[i].globalcustomerinventoryinput = new BtInventoryStockByProductId();
        }
        else {
          this._global.alert({ type: 'alert', message: 'Serial Number ' + btproduct.serialNumber + ' for part code ' + this.btInventoryStockList[i].productPartcode + ' already exists' });
        }

      }
    }
  }

  removeCustomerInventory(cs, item) {
    const lent = this.btInventoryStockList.length;
    for (let i = 0; i < lent; i++) {
      if (this.btInventoryStockList[i].productId == item.productId) {
        const itemlent = this.btInventoryStockList[i].btinventoryStock.length;
        const temp = [];
        for (let j = 0; j < itemlent; j++) {
          if (this.btInventoryStockList[i].btinventoryStock[j].serialNumber != cs.serialNumber) {
            temp.push(this.btInventoryStockList[i].btinventoryStock[j]);
          }
        }
        this.btInventoryStockList[i].btinventoryStock = temp;
      }
    }
  }

  getListOfServiceOrderRepairCenter() {
    this._spinner.show();
    this._btserviceOrderService.btServiceOrderRepairCenter().subscribe(data => {
      this._spinner.hide();
      this.btRepairCenterlist = data;
      if(this.btRepairCenterlist.length==1){
        this.btServiceOrderRequest.destinationLocationId=this.btRepairCenterlist[0].locationId;
      }
    }, err => {
      this._spinner.hide();
    })
  }
  postServiceOrder() {
    if (this.btServiceOrderRequest.destinationLocationId == undefined || this.btServiceOrderRequest.destinationLocationId == null || this.btServiceOrderRequest.destinationLocationId == 0) {
      this._global.alert({ type: 'alert', message: 'Please Select Repair Center' });
      return;
    }

    if (this.btServiceOrderRequest.shippingMode == '0') {
      this._global.alert({ type: 'alert', message: 'Please Select Shipping Mode' });
      return;
    }
    if (this.btServiceOrderRequest.shippingTo == undefined || this.btServiceOrderRequest.shippingTo == null || this.btServiceOrderRequest.shippingTo == '') {
      this._global.alert({ type: 'alert', message: 'Please Provide Shipping To Name' });
      return;
    }
    if (this.btServiceOrderRequest.trackingNumber == undefined || this.btServiceOrderRequest.trackingNumber == null || this.btServiceOrderRequest.trackingNumber == '') {
      this._global.alert({ type: 'alert', message: 'Please Provide Tracking Number' });
      return;
    }
    this.btServiceOrderRequest.items = [];
    for (let i = 0; i < this.btInventoryStockList.length; i++) {
      for (let j = 0; j < this.btInventoryStockList[i].btinventoryStock.length; j++) {
        if (this.btInventoryStockList[i].btinventoryStock[j].isChecked) {
          let temp = new BtServiceOrderItems();
          temp.productId = this.btInventoryStockList[i].productId;
          temp.serialNumber = this.btInventoryStockList[i].btinventoryStock[j].serialNumber;
          temp.customerInventoryInId = this.btInventoryStockList[i].btinventoryStock[j].brcId;
          temp.isCustomerInventory = true;
          this.btServiceOrderRequest.items.push(temp);
        }
      }
      for (let j = 0; j < this.btInventoryStockList[i].exicomSerialnumbers.length; j++) {
        let temp = new BtServiceOrderItems();
        temp.isCustomerInventory = false;
        temp.productId = this.btInventoryStockList[i].productId;
        temp.serialNumber = this.btInventoryStockList[i].exicomSerialnumbers[j];
        this.btServiceOrderRequest.items.push(temp);
      }
    }

    if (this.btServiceOrderRequest.items.length == 0) {
      this._global.alert({ type: 'alert', message: 'Item  List Not Found' });
      return;
    }
    this._spinner.show();
    this._btserviceOrderService.postBtSeriveOrder(this.btServiceOrderRequest).subscribe(data => {
    this._spinner.hide(); 
    this._global.alert({ type: '', message: `Service Order ${data.manualId} is Created!` });
    this._router.navigate(['/bat_serviceorder'])
    }, err => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });

  }
  cancelSeriviceOrder() {
    this._router.navigate(['/bat_serviceorder'])
  }

  getProductWith($event){
    if(this.challanNumber=='' || this.challanNumber==undefined){
      this._global.alert({type:'alert',message:'Please Fill Challan Number'});
      return;
    }
    this.getPendingStockList();
    this.getListOfServiceOrderRepairCenter();
  }
}



