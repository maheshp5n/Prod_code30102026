import { GlobalserviceService } from './../../globalservice.service';
import { selected } from './../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ProductReturnService } from './../../services/productreturnservice';
import { NgxSpinnerService } from 'ngx-spinner';
import { LocationService } from './../../services/location.service';
import { Component, OnInit, ViewChild, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-productreturnadditems',
  templateUrl: './productreturnadditems.component.html',
  styleUrls: ['./productreturnadditems.component.scss']
})
export class ProductreturnadditemsComponent implements OnInit {

  returnorderd;
  faultyproducts = [];
  openModels = {
    receiveOrder: false
  };

  globalcheck = false;
  @Input() set returnOrder(returnOrderd) {
    this.returnorderd = returnOrderd;
  }
  get returnOrder() {
    return this.returnorderd;
  }

  @Output()
  ItemAdded: EventEmitter<any> = new EventEmitter();

  @ViewChild('addItem') addItem: ModalDirective;

  constructor(
    private _location: LocationService,
    private _ngs: NgxSpinnerService,
    private _return: ProductReturnService,
    private _global: GlobalserviceService
  ) { }

  ngOnInit() {
  }

  showItemBox() {
    this.addItem.show();
    this.getItemToReturn();
  }

  getItemToReturn() {
    this.faultyproducts = [];
    this._ngs.show();
    this._location.getFaultyItems(this.returnorderd.sourceLocationId).subscribe(data => {
      this._ngs.hide();
      data.forEach(d => {
        d.lineItems.forEach(l => {
          this.faultyproducts.push({
            locationLineItemId: l.locationLineItemId,
            serialNumber: l.serialNumber,
            partCode: d.partCode,
            partDescription: d.partDescription,
            selected: this.globalcheck
          });
        });
      });
    }, error => this._ngs.hide());
  }
  globalCheckAction() {
    this.faultyproducts.forEach(d => d.selected = this.globalcheck);
  }
  saveitems() {
    const items = [];
    const ln = this.faultyproducts.length;
    for (let i = 0; i < ln; i++) {
      if (this.faultyproducts[i].selected) {
        items.push(this.faultyproducts[i].locationLineItemId);
      }
    }
    if (items.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please select item from list' });
      return;
    }
    this._ngs.show();
    this._return.addItem(this.returnorderd.returnOrderId, items).subscribe(data => {
      this.getItemToReturn();
      this.ItemAdded.emit('d');
      this._ngs.hide();
      this._global.alert({ type: data.e > 0 ? 'alert' : '', message: `item added ${data.s}, error ${data.e}` });

    }, error => {
      this._ngs.hide();
      this._global.alert({ type: 'alert', message: error.error });
      //this.onItemAdded.emit('');
    });
  }
}
