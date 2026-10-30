import { Component, OnInit, Input, Output,EventEmitter } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtProductReturnService } from '../../bat_services/bt-product-return.service';
import { BtProductReturnAddItemRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-add-product-return-items',
  templateUrl: './bt-add-product-return-items.component.html',
  styleUrls: ['./bt-add-product-return-items.component.scss']
})
export class BtAddProductReturnItemsComponent implements OnInit {

  constructor(private _btReturnReceiveService: BtProductReturnService, private _global: GlobalserviceService, private _spinner: NgxSpinnerService) { }
  btReturnItemsList: Array<any> = [];
  productId: number;
  btAddProductItemReq: BtProductReturnAddItemRequest = new BtProductReturnAddItemRequest();
  @Input()
  set ProductId(_productId: number) {
    this.productId = _productId;
  }
  get ProductId(): number {
    return this.productId;
  }
  @Output()
  onBtAddItem: EventEmitter<string> = new EventEmitter<string>();
  ngOnInit(): void {
    this.getListForProducts();
  }

  getListForProducts() {
    this._btReturnReceiveService.btPendingItemToReturn().subscribe(data => {
      this.btReturnItemsList = data;
    }, error => {

    })
  }
  getChangeProduct(value) {
    this.btReturnItemsList.forEach(data => {
      if (data.productId == value) {
        this.btAddProductItemReq.productId = data.productId;
        this.btAddProductItemReq.exicomIvnentory = data.exicomTotalFaultyQuantity;
        this.btAddProductItemReq.customerInventory = data.customerTotalFaultyQuantity;

      }
    })
  }

  addItem() {
    if (this.btAddProductItemReq.productId == 0 || this.btAddProductItemReq.productId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Product Part Code' });
    }
    else if (this.btAddProductItemReq.exicomIvnentory == null || this.btAddProductItemReq.exicomIvnentory == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Exicom Faulty Items' });
    }
    else if (this.btAddProductItemReq.customerInventory == null || this.btAddProductItemReq.customerInventory == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Customer Faulty Items' });
    }
    else {
      this._spinner.show();
      this._btReturnReceiveService.addBtReturnItems(this.productId, this.btAddProductItemReq).subscribe(data => {
        this._spinner.hide();
        this.onBtAddItem.emit(data);
      }, error => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
  }
  //Validation for numric tye value
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

}
