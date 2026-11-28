import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import {
  BRCRepairType, BRCRepiarBalancedAt, BRCRepiarResolutionMethod, BRCRepairBERRemark,
  BRCRepiarResolutionMethodChoosenRequest, ServiceResourseForList, ServiceResourseRequest, BRCInventoryReceiveResponse, BRCRepairInventoryReceiveItemRequest, ServiceCenterForList
} from '../../services/model';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { TypeaheadConfig, TypeaheadMatch } from 'ngx-bootstrap/typeahead';


@Component({
  selector: 'app-assign-to-eng',
  templateUrl: './assign-to-eng.component.html',
  styleUrls: ['./assign-to-eng.component.scss']
})
export class AssignToEngComponent implements OnInit {

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createServiceResourceModel') createServiceResourceModel: ModalDirective;

  @Output()
  onAssignToEngCreated: EventEmitter<BRCRepairInventoryReceiveItemRequest> = new EventEmitter<BRCRepairInventoryReceiveItemRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editEngData(brcRepairInventoryReceiveItemRequest: BRCRepairInventoryReceiveItemRequest) {
    this.brcRepairInventoryReceiveItemRequest = brcRepairInventoryReceiveItemRequest;
  }
  get editEngData(): BRCRepairInventoryReceiveItemRequest {
    return this.brcRepairInventoryReceiveItemRequest;
  }

  message: string;
  _isEditMode: boolean;
  today = new Date();   
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';  
  resourceList: Array<ServiceResourseForList> = [];
  brcRepairInventoryReceiveItemRequest: BRCRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
  brcInventoryReceiveResponse: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
  isServiceResourceModel: boolean = false;

  constructor(private spinner: NgxSpinnerService, private mangaeInventoryService: ManageInventoryService) { }

  ngOnInit() {
    this.getServiceResourceListByLegalEntity();    
  }
  getServiceResourceListByLegalEntity() {
    this.mangaeInventoryService.getServiceResourceListByLegalEntity(this.brcRepairInventoryReceiveItemRequest.legalEntityId).subscribe(data => {
      this.resourceList = data;
    })
  }


  postCreate() {
    if (this.brcRepairInventoryReceiveItemRequest.serviceResourceId == null || this.brcRepairInventoryReceiveItemRequest.serviceResourceId === 0) {
      this.message = 'Please fill the engineer';
      this.popupSuccessModel.show();
    }
    else {
      this.spinner.show();           
      this.mangaeInventoryService.assignToEng(this.brcRepairInventoryReceiveItemRequest.receiveDetailsId, this.brcRepairInventoryReceiveItemRequest.serviceResourceId).subscribe(data => {
        this.message = 'Submitted Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.onAssignToEngCreated.emit(data);
        if (data != null) {
          this.spinner.hide();
        }
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }

  }
  cancelData() {
    this.brcRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
