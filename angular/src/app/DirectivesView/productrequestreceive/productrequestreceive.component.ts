import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { LocationProductRequestResponse, LocationProductRequestPendingToReceive, LocationProductTransferReceiveRequest } from '../../services/model';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-productrequestreceive',
  templateUrl: './productrequestreceive.component.html',
  styleUrls: ['./productrequestreceive.component.scss']
})
export class ProductrequestreceiveComponent implements OnInit {
  locationProductRequestResponse: LocationProductRequestResponse = new LocationProductRequestResponse();
  pendingItemToReceive: Array<LocationProductRequestPendingToReceive> = [];
  @Input() set locationProductRequest(_locPr: LocationProductRequestResponse) {
    this.locationProductRequestResponse = _locPr;
    this.getPendingItemToReceive(_locPr.prId);
  }
  @Output() onReceived: EventEmitter<string> = new EventEmitter<string>();
  @Output() onCencled: EventEmitter<string> = new EventEmitter<string>();
  get locationProductRequest() {
    return this.locationProductRequestResponse;
  }
  constructor(private _locationProductRequestService: LocationProductRequestService, private _ngxspinner: NgxSpinnerService) { }

  ngOnInit() {

  }
  getPendingItemToReceive(id) {
    this._ngxspinner.show();
    this._locationProductRequestService.getPendingItemToReceive(id).subscribe(data => {
      this._ngxspinner.hide();
      this.pendingItemToReceive = [];
      data.forEach(item => {
        let tem = [];
        for (let i = 0; i < item.serialNumbers.length; i++) {
          tem.push({ selected: false, serialNumber: item.serialNumbers[i] });
        }
        item.serialNumbers = tem;
        if(item.productType=='Component'){
          item.actualReceive=(item.totalQuantityTransfred-item.totalQuantityReceived);
        }
        this.pendingItemToReceive.push(item);
      });
      this.pendingItemToReceive = data;

    }, err => this._ngxspinner.hide());
  }
  receiveItems() {
    let tem = [];
    for (let c = 0; c < this.pendingItemToReceive.length; c++) {
      let tempSerial = [];
      let transfer = new LocationProductTransferReceiveRequest();
      transfer.locationProductTransferItemId = this.pendingItemToReceive[c].productTransferItemId;
      this.pendingItemToReceive[c].serialNumbers.forEach(ser => {
        if (ser.selected) {
          tempSerial.push(ser.serialNumber);
        }
      });
      if (this.pendingItemToReceive[c].productType == 'Component') {
        transfer.quantityReceive = this.pendingItemToReceive[c].actualReceive;
      }
      else {
        transfer.quantityReceive = tempSerial.length;
        transfer.receivedSerialNumber = tempSerial;
      }
      if (transfer.quantityReceive != undefined && transfer.quantityReceive != null && transfer.quantityReceive != 0) {
        tem.push(transfer);
      }
      if(transfer.quantityReceive>this.pendingItemToReceive[c].totalQuantityTransfred){
        if(!confirm(`Are you sure want to receive more quantity then transfred Part code ${this.pendingItemToReceive[c].productPartCode}`)){
          return;
        }
      }
    }
    if (tem.length == 0) {
      alert("You have not selected items");
      return;
    }
    this._ngxspinner.show();
    this._locationProductRequestService.receiveItems(this.locationProductRequestResponse.prId, tem).subscribe(data => {
      this._ngxspinner.hide();
      this.onReceived.emit('Saved');
    }, error => {
      this._ngxspinner.hide();
      alert(`Some thing went wrong ${error.error.message}`)
    });

  }
  cancelItemReceive() {
    this.onCencled.emit('Cancled');
  }
  // mastercheccked(item) {
  //   for (let i = 0; i < item.serialNumbers.length; i++) {
  //     item.serialNumbers[i].selected = item.checked;
  //   }
  // }
  mastercheccked($event,partcode){
    console.log($event.target.checked)
    console.log("Item= ",partcode);
    for (let c = 0; c < this.pendingItemToReceive.length; c++) {
    let tempSerial = [];
      let transfer = new LocationProductTransferReceiveRequest();
      transfer.locationProductTransferItemId = this.pendingItemToReceive[c].productTransferItemId;
    
    this.pendingItemToReceive.forEach(serial=>{
     if(serial.productPartCode==partcode){
       serial.serialNumbers.forEach(itm=>{
         itm.selected=$event.target.checked;
       })
     }
    })
  }

  }
}

