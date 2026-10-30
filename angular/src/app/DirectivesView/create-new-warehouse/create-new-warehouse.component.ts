// DEVELOPED BY ANKUR SAXENA
import {
  Component,
  OnInit,
  ViewChild,
  Output,
  EventEmitter,
  Input
} from "@angular/core";
import { ModalDirective, TypeaheadMatch } from "ngx-bootstrap";
import { NgxSpinnerService } from "ngx-spinner";
import { WarehouseService } from "../../services/warehouse.service";
import { FieldValidation } from "../../services/validation.services";
import {
  WarehousedetailsRequest,
  LegalEntityResponse,
  ServiceCenterForList
} from '../../services/model';
import { ligalEntityService } from "../../services/ligalentity.services";

@Component({
  selector: "app-create-new-warehouse",
  templateUrl: "./create-new-warehouse.component.html",
  styleUrls: ["./create-new-warehouse.component.css"]
})
export class CreateNewWarehouseComponent implements OnInit {
  public _isEditMode: boolean;
  productFamily;
  flag: boolean;
  message: string;
  legalEntityList: Array<ServiceCenterForList> = [];
  newWarehouse: WarehousedetailsRequest = new WarehousedetailsRequest();
  faPlus: string = "btn btn-primary fa fa-plus";
  createLegalEntity: LegalEntityResponse = new LegalEntityResponse();
  heading: string;
  CREATE_LEGALENTITY_HEADING: string = "New LEGAL ENTITY";
  noMatchLegalEntity: string;
  newLegalEntity = new ServiceCenterForList();
  popupNgClass: string = "alert alert-danger center center-block";

  @ViewChild("createLegalEntityModel", { static: false })
  createLegalEntityModel: ModalDirective;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editWarehouseData(warehousedetailsRequest: WarehousedetailsRequest) {
    this.popupNgClass = "alert alert-danger center center-block";
    this.newWarehouse = warehousedetailsRequest;
    // if (warehousedetailsRequest.legalEntityName == null || warehousedetailsRequest.legalEntityName == '') {
    //   this.newWarehouse.isLegalEntitySelected = "fa fa-search";
    // }
    // else {
    //   this.newWarehouse.isLegalEntitySelected = "fa fa-times";
    // }
  }

  get editWarehouseData(): WarehousedetailsRequest {
    return this.newWarehouse;
  }

  @Output()
  onWarehouseCreated: EventEmitter<WarehousedetailsRequest> = new EventEmitter<
    WarehousedetailsRequest
  >();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild("popupSuccessModel", { static: false })
  popupSuccessModel: ModalDirective;

  constructor(
    private _legalEntityService: ligalEntityService,
    private spinner: NgxSpinnerService,
    private _warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.getLegalEntity();
  }

  createWarehouse() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newWarehouse.warehouseName)) {
      this.message = "Please fill Warehouse Name";
    } else if (this.noMatchLegalEntity == " ** Record not found") {
      this.message = "Legal Entity not found";
    }
    else if (this.newWarehouse.legalEntityId == 0) {
      this.message = "Please fill Legal Entity";
    }
    else if (this.newWarehouse.warehouseEmail == null) {
      this.message = "Please fill Email";
    } else if (
      FieldValidation.isValidEmail(this.newWarehouse.warehouseEmail) == false
    ) {
      this.message = "Please fill valid Email";
    } else if (FieldValidation.isEmpty(this.newWarehouse.warehousePlant)) {
      this.message = "Please fill Warehouse Plant Name";
    } else {
      this.flag = true;
      if (this._isEditMode == false) {
        this.spinner.show();
        this._warehouseService.postWarehouse(this.newWarehouse).subscribe(
          data => {
            this.message = "Submitted Successfully";
            this.onWarehouseCreated.emit(data);
            if (data != null) {
              this.spinner.hide();
            }
            this.popupSuccessModel.show();

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
        this._warehouseService
          .putWarehouse(this.newWarehouse.warehouseId, this.newWarehouse)
          .subscribe(
            data => {
              this.message = "Updated Successfully";
              this.popupNgClass = "alert alert-success center center-block";
              this.spinner.hide();
              this.popupSuccessModel.show();
              this.onWarehouseCreated.emit(data);
              // if (data != null) {
              //   setTimeout(() => {
              //     this.spinner.hide();
              //     this.popupSuccessModel.show();

              //     setTimeout(() => {
              //       this.popupSuccessModel.hide();
              //       this.onWarehouseCreated.emit(data);
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
    this.newWarehouse = new WarehousedetailsRequest();
    this.onCancle.emit("cancled");
  }

  getLegalEntity() {
    this.spinner.show();
    this._warehouseService.getLegalEntityForList().subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.legalEntityList = data;
    });
  }


  cancelData2() {
    this.createLegalEntity = new LegalEntityResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_LEGALENTITY_HEADING;
    this.createLegalEntityModel.hide();
  }

  createLegalEntityModelhide() {
    this.createLegalEntity = new LegalEntityResponse();
    this.createLegalEntityModel.hide();
  }

  // Legal Entity starts

  onKeydownLegalEntity($event) {
    this.newWarehouse.isLegalEntitySelected = "fa fa-times";
  }

  noMatchLegalEntityCheck() {
    if (this.newWarehouse.legalEntityName == null || this.newWarehouse.legalEntityName == '') {
      this.noMatchLegalEntity = '';
      this.newWarehouse.isLegalEntitySelected = "fa fa-search";
    }
  }

  legalEntitySelected($event: TypeaheadMatch) {
    this.newWarehouse.legalEntityId = $event.item.legalEntityId;
    this.noMatchLegalEntity = '';
  }

  typeaheadNoResultsLegalEntity($event) {
    if ($event) {
      this.noMatchLegalEntity = " ** Record not found";
    }
  }

  legalEntityTimes() {
    this.newWarehouse.isLegalEntitySelected = "fa fa-search";
    this.noMatchLegalEntity = '';
    this.newWarehouse.legalEntityName = null;
  }

  addnewLegalEntity() {
    this.createLegalEntity = new LegalEntityResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_LEGALENTITY_HEADING;
    this.createLegalEntityModel.show();
  }

  onlegalentityCreated($event) {
    this.newLegalEntity.legalEntityId = $event.legalentityId;
    this.newLegalEntity.legalEntityName = $event.legalEntityName;
    this.legalEntityList.push(this.newLegalEntity);
    this._isEditMode = false;
  }
  // Legal Entity ends

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == "alert alert-success center center-block") {
      this.onCancle.emit("cancled");
    }
  }

}
