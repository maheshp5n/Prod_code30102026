import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { LocationResponse, LocationForList, LPReturnOrderRequest, LocationFaultyItemResponse, LPReturnOrderLineItemRequest, LPReturnOrderItemRequest } from '../../services/model';
import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';

@Component({
  selector: 'app-productreturnorderdirective',
  templateUrl: './productreturnorderdirective.component.html',
  styleUrls: ['./productreturnorderdirective.component.scss']
})
export class ProductreturnorderdirectiveComponent implements OnInit {

  sourceLocation: LocationResponse = new LocationResponse();
  locationList: Array<LocationForList> = [];
  _returnOrderRequest: LPReturnOrderRequest = new LPReturnOrderRequest();
  falutyItems: Array<LocationFaultyItemResponse> = [];
  alertDeetails = { type: 'danger', message: '' };

  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();

  @ViewChild('alertModel') alertModel: ModalDirective;
  @Input()
  public set location(loc: LocationResponse) {
    if (loc) {
      this.sourceLocation = loc;
      this._returnOrderRequest.destinationLocationId = Number(this.sourceLocation.parentLocationId);
      this._returnOrderRequest.sourceLocationId = this.sourceLocation.locationId;
      this.getFaultyItems(this.sourceLocation.locationId);
    }
  }
  public get() {
    return this.sourceLocation;
  }
  @Output() onProductReturnSaved: EventEmitter<any> = new EventEmitter();
  @Output() onCanled: EventEmitter<string> = new EventEmitter();

  constructor(private _locationService: LocationService, private _ngxspinner: NgxSpinnerService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    //this.getLocationList();
    this.getSiblingLocationList();
    this._returnOrderRequest.shippingMode = '';
  }
  getLocationList() {
    this._ngxspinner.show();
    this._locationService.locationForlist().subscribe(loc => {
      this._ngxspinner.hide();
      this.locationList = loc;
    }, error => this._ngxspinner.hide());
  }
  getFaultyItems(locationId) {
    this._ngxspinner.show();
    this._locationService.getFaultyItems(locationId).subscribe(item => {
      this._ngxspinner.hide();
      this.falutyItems = item;
    }, error => { this._ngxspinner.hide(); });
  }
  returnItems() {
    if (this._returnOrderRequest.destinationLocationId == undefined || this._returnOrderRequest.destinationLocationId == null || this._returnOrderRequest.destinationLocationId == 0) {
      this.showAlert("Please select destination Location");
      return;
    }
    if (this._returnOrderRequest.shippingMode == undefined || this._returnOrderRequest.shippingMode == null || this._returnOrderRequest.shippingMode == '') {
      this.showAlert("Please select shipping mode");
      return;
    }
    if (this._returnOrderRequest.shipToName == undefined || this._returnOrderRequest.shipToName == null || this._returnOrderRequest.shipToName == '') {
      this.showAlert("Please provide Ship to Name");
      return;
    }

    if (this._returnOrderRequest.trackingNumber == undefined || this._returnOrderRequest.trackingNumber == null || this._returnOrderRequest.trackingNumber == '') {
      this.showAlert('Please provide Tracking number');
      return;
    }
    if (this._returnOrderRequest.destinationLocationId == this._returnOrderRequest.sourceLocationId) {
      this.showAlert("Destination location and source location can not be same");
      return;
    }
    let ite = [];
    this.falutyItems.forEach(item => {
      let lineItems = [];
      item.lineItems.forEach(lineitem => {
        if (lineitem.isSelected) {
          let _l = new LPReturnOrderLineItemRequest();
          _l.productLineItemSerialNumber = lineitem.serialNumber;
          _l.quantityReturned = 1;
          lineItems.push(_l);
        }
      });
      if (lineItems.length > 0) {
        let _ri = new LPReturnOrderItemRequest();
        _ri.lineItems = lineItems;
        _ri.productItemId = item.locationItemId;
        _ri.productPartCode = item.partCode;
        _ri.totalReturnQuantity = lineItems.length;
        ite.push(_ri);
      }
    });
    if (ite.length == 0) {
      this.showAlert('No item selected to return');
      return;
    }
    this._returnOrderRequest.items = ite;
    this._ngxspinner.show();
    this._locationService.saveReturnOrder(this.sourceLocation.locationId, this._returnOrderRequest).subscribe(data => {
      this._ngxspinner.hide();
      this.onProductReturnSaved.emit(data);
    }, err => {
      this._ngxspinner.hide();
      this.showAlert(`Error : ${err.error.message}`);
    }
    )
  }
  cancelReturnORder() {
    this.onCanled.emit('cancled');
  }
  showAlert(message: string) {
    this.alertDeetails.type = 'danger';
    this.alertDeetails.message = message;
    this.alertModel.show();
  }
  manchecked(item) {
    const d = item.lineItems.length;
    for (let i = 0; i < d; i++) {
      item.lineItems[i].isSelected = item.checked;
    }
    item.checkedlen=0;
    item.lineItems.forEach((value, index)=>{
     if(value.isSelected)
     {
       item.checkedlen=item.checkedlen+1;
     }

   })
  }
  getSiblingLocationList(){
    this._ngxspinner.show();
    this._locationService.getLocationParentSiblingAndChilds().subscribe(data=>{
      this._ngxspinner.hide();
      this.locationList=data;
    },error=>this._ngxspinner.hide());
  }
  check()
  {
    this.falutyItems.forEach(e=>{
      e.checkedlen=0;
       e.lineItems.forEach((value, index)=>{
        if(value.isSelected)
        {
          e.checkedlen=e.checkedlen+1;
        }

      })
    })
  }
}

