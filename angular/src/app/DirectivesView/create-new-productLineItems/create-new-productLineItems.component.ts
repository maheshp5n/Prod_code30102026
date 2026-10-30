import {
  Component,
  OnInit,
  ViewChild,
  Output,
  EventEmitter,
  Input
} from "@angular/core";
import { ModalDirective, TypeaheadMatch } from "ngx-bootstrap";
import {
  ProductLineItemsRequest,
  ProductsResponse,
  ProductLineItemProductState,
  ProductLineItemStatus
} from '../../services/model';
import { NgxSpinnerService } from "ngx-spinner";
import { FieldValidation } from "../../services/validation.services";
import { ProductLineItemsService } from "../../services/product-line-items.service";
import { ProductsService } from "../../services/products.services";

@Component({
  selector: "app-create-new-productlineitems",
  templateUrl: "./create-new-productLineItems.component.html",
  styleUrls: ["./create-new-productLineItems.component.css"]
})
export class CreateNewProductLineItemsComponent implements OnInit {
  @ViewChild("popupSuccessModel", { static: false })
  popupSuccessModel: ModalDirective;

  constructor(
    private spinner: NgxSpinnerService,
    private _productlineitemsService: ProductLineItemsService,
    private productService: ProductsService
  ) { }

  newProductLineItems: ProductLineItemsRequest = new ProductLineItemsRequest();
  flag: boolean;
  message: string;
  productById: ProductsResponse = new ProductsResponse();
  _isEditMode: boolean;
  isProductStateSelected: string = "fa fa-search";
  isStatusSelected: string = "fa fa-search";
  stateForList: Array<ProductLineItemProductState> = [];
  statusForList: Array<ProductLineItemStatus> = [];
  popupNgClass: string = "alert alert-danger center center-block";

  ngOnInit() {
    this.newProductLineItems = new ProductLineItemsRequest();
    this.newProductLineItems.partCode = "";
    this.getProductLineItemStateForList();
    this.getProductLineItemStatusForList();
  }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editProductLineItemsData(
    ProductLineItemsRequest: ProductLineItemsRequest
  ) {
    this.popupNgClass = "alert alert-danger center center-block";
    this.newProductLineItems = ProductLineItemsRequest;
  }

  get editProductLineItemsData(): ProductLineItemsRequest {
    return this.newProductLineItems;
  }

  @Output()
  onProductLineItemsCreated: EventEmitter<
    ProductLineItemsRequest
  > = new EventEmitter<ProductLineItemsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  createProductLineItems() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newProductLineItems.serialNumber)) {
      this.message = "Please fill Serial Number";
    } else if (FieldValidation.isEmpty(this.newProductLineItems.productItem)) {
      this.message = "Please fill Product Item";
    } else {
      this.flag = true;

      if (this._isEditMode == false) {
        this.spinner.show();
        this._productlineitemsService
          .postProductLineItems(this.newProductLineItems)
          .subscribe(
            data => {
              this.message = "Submitted Successfully";
              this.popupNgClass = "alert alert-success center center-block";
              this.onProductLineItemsCreated.emit(data);
              this.spinner.hide();
              this.popupSuccessModel.show();
              // this.onProductLineItemsCreated.emit(data);
              // if (data != null) {
              //   this.spinner.hide();
              // }
              // this.popupSuccessModel.show();
              // setTimeout(() => {
              //   this.popupSuccessModel.hide();
              //   this.onCancle.emit("cancled");
              // }, 2000);
            },
            err => {
              this.spinner.hide();
              this.message = err.status + " # " + err.error.message;
              this.popupSuccessModel.show();
              // setTimeout(() => {
              //   this.popupSuccessModel.hide();
              //   this.onCancle.emit("cancled");
              // }, 3000);
            }
          );
      } else {
        this.spinner.show();
        this._productlineitemsService
          .putProductLineItems(
            this.newProductLineItems.serialNumber,
            this.newProductLineItems
          )
          .subscribe(
            data => {
              this.message = "Updated Successfully";
              this.popupNgClass = "alert alert-success center center-block";
              this.spinner.hide();
              this.popupSuccessModel.show();
              this.onProductLineItemsCreated.emit(data);
              // if (data != null) {
              //   setTimeout(() => {
              //     this.spinner.hide();
              //     this.popupSuccessModel.show();
              //     setTimeout(() => {
              //       this.popupSuccessModel.hide();
              //       this.onProductLineItemsCreated.emit(data);
              //       this.onCancle.emit("cancled");
              //     }, 2000);
              //   }, 2500);
              // }
            },
            err => {
              this.spinner.hide();
              this.message = err.status + " # " + err.error.message;
              this.popupSuccessModel.show();
              // setTimeout(() => {
              //   this.popupSuccessModel.hide();
              //   this.onCancle.emit("cancled");
              // }, 3000);
            }
          );
      }
    }

    if (this.flag != true) {
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 2000);
    }
  }

  cancelData() {
    this.newProductLineItems = new ProductLineItemsRequest();
    this.onCancle.emit("cancled");
  }

  getProductDetails(partCode) {
    this.spinner.show;
    this.productService.getProductDetails(partCode).subscribe(data => {
      this.spinner.hide();
      this.productById = data;
      // this.newProductLineItems.partCode = this.productById.partCode;
      // this.newProductLineItems.productType = this.productById.productType;
    },
      err => {
        this.spinner.hide();
        this.message = err.status + " # " + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  // Status Forlist

  onKeydownStatus($event) {
    this.isStatusSelected = "fa fa-times";
  }

  statusFieldSelected($event: TypeaheadMatch) {
    // this.newProductLineItems.oppertunityStatusId = $event.item.oppertunityStatusId;
  }

  statusTimes() {
    this.isStatusSelected = "fa fa-search";
    // this.newProductLineItems.oppertunityStatusId = null;
    // this.newProductLineItems.statusName = null;
  }

  // ProductState Forlist

  onKeydownProductState($event) {
    this.isProductStateSelected = "fa fa-times";
  }

  productStateFieldSelected($event: TypeaheadMatch) {
    // this.newProductLineItems.oppertunityProductStateId =
    //   $event.item.oppertunityProductStateId;
  }

  productStateTimes() {
    this.isProductStateSelected = "fa fa-search";
    // this.newProductLineItems.oppertunityProductStateId = null;
    // this.newProductLineItems.productStateName = null;
  }

  getProductLineItemStateForList() {
    this._productlineitemsService.getProductLineItemStateForList().subscribe(data => {
      this.stateForList = data;
    },
      err => {
        this.spinner.hide();
        this.message = err.status + " # " + err.error.message;
        this.popupSuccessModel.show();
      });
  }


  getProductLineItemStatusForList() {
    this._productlineitemsService.getProductLineItemStatusForList().subscribe(data => {
      this.statusForList = data;
    },
      err => {
        this.spinner.hide();
        this.message = err.status + " # " + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == "alert alert-success center center-block") {
      this.onCancle.emit("cancled");
    }
  }

}
