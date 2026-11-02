import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { deliverychallandetailsRequest, LegalEntityResponse } from '../../services/model';
import { ligalEntityService } from '../../services/ligalentity.services';
import { deliverychallanService } from '../../services/delivery-challan.service';

@Component({
  selector: 'app-create-new-deliverychallan',
  templateUrl: './create-new-deliverychallan.component.html',
  styleUrls: ['./create-new-deliverychallan.component.css']
})
export class CreateNewdeliverychallanComponent implements OnInit {

  public _isEditMode: boolean;
  productFamily;
  flag: boolean;
  message: string;
  legalEntityList: Array<LegalEntityResponse> = [];
  newdeliverychallan: deliverychallandetailsRequest = new deliverychallandetailsRequest();
  popupNgClass: string = "alert alert-danger center center-block";
  today = new Date();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editdeliverychallanData(deliverychallandetailsRequest: deliverychallandetailsRequest) {
    this.popupNgClass = "alert alert-danger center center-block";
    this.newdeliverychallan = deliverychallandetailsRequest;
  }

  get editdeliverychallanData(): deliverychallandetailsRequest {
    return this.newdeliverychallan;
  }

  @Output()
  ondeliverychallanCreated: EventEmitter<deliverychallandetailsRequest> = new EventEmitter<deliverychallandetailsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  constructor(private _legalEntityService: ligalEntityService, private spinner: NgxSpinnerService, private _deliverychallanService: deliverychallanService) { }

  ngOnInit() {
    // this.getLegalEntity();
    // this.isLegalEntitySelected = "fa fa-search";
  }

  createdeliverychallan() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newdeliverychallan.infoName)) {
      this.message = "Please fill Name";
    }
    else {
      this.flag = true;
      if (this._isEditMode == false) {
        //   this.spinner.show();
        //   this._deliverychallanService.postdeliverychallan(this.newdeliverychallan).subscribe(data => {
        //     this.message = "Submitted Successfully";
        //     this.ondeliverychallanCreated.emit(data);
        //     if (data != null) {
        //       this.spinner.hide();
        //     }
        //     this.popupSuccessModel.show();
        //     setTimeout(() => {
        //       this.popupSuccessModel.hide()
        //       this.onCancle.emit('cancled');
        //     }, 2000
        //     );
        //   }, err => {
        //     this.spinner.hide();
        //     this.message = "Server Error !";
        //     this.popupSuccessModel.show();
        //     setTimeout(() => {
        //       this.popupSuccessModel.hide();
        //       this.onCancle.emit('cancled');
        //     }, 2000
        //     );
        //   });
      }
      else {

        //   this.spinner.show();
        //   this._deliverychallanService.putdeliverychallan(this.newdeliverychallan.deliverychallanId, this.newdeliverychallan).subscribe(data => {

        // this.message = "Updated Successfully";
        //         this.popupNgClass = "alert alert-success center center-block";
        //         this.spinner.hide();
        //         this.popupSuccessModel.show();
        //         this.ondeliverychallanCreated.emit(data);

        //     if (data != null) {
        //       setTimeout(() => {
        //         this.spinner.hide();
        //         this.popupSuccessModel.show();

        //         setTimeout(() => {
        //           this.popupSuccessModel.hide();
        //           this.ondeliverychallanCreated.emit(data);
        //           this.onCancle.emit('cancled');
        //         }, 2000
        //         );
        //       }, 2500
        //       );
        //     }
        //   }, err => {
        //     this.spinner.hide();
        //     this.message = "Server Error !";
        //     this.popupSuccessModel.show();
        //     setTimeout(() => {
        //       this.popupSuccessModel.hide();
        //       this.onCancle.emit('cancled');
        //     }, 2000
        //     );
        //   }
        //   );
      }
    }
    if (this.flag != true) {
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide()
      // }, 2000
      // );
    }
  }

  cancelData() {
    this.newdeliverychallan = new deliverychallandetailsRequest();
    this.onCancle.emit('cancled');
  }

  // getLegalEntity() {
  //   this.spinner.show();
  //   this._legalEntityService.getLegalEntityForList().subscribe(data => {
  //     if (data != null) {
  //       this.spinner.hide();
  //     }
  //     this.legalEntityList = data;
  //   });
  // }

  // isLegalEntitySelected: string;
  // legalEntitySelected() {
  //   this.isLegalEntitySelected = "fa fa-times";
  // }

  // legalEntityTimes() {
  //   this.newdeliverychallan.legalEntityId = null;
  //   this.isLegalEntitySelected = "fa fa-search";
  // }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == "alert alert-success center center-block") {
      this.onCancle.emit("cancled");
    }
  }
}
