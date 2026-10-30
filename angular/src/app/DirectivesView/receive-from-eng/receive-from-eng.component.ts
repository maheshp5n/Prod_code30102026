import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import {
  BRCRepairType, BRCRepiarBalancedAt, BRCRepiarResolutionMethod, BRCRepairBERRemark,
  BRCRepiarResolutionMethodChoosenRequest, ServiceResourseForList, ServiceResourseRequest, BRCInventoryReceiveResponse, BRCRepairInventoryReceiveItemRequest
} from '../../services/model';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-receive-from-eng',
  templateUrl: './receive-from-eng.component.html',
  styleUrls: ['./receive-from-eng.component.scss']
})
export class ReceiveFromEngComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createServiceResourceModel', { static: false }) createServiceResourceModel: ModalDirective;

  @Output()
  onReceiveFromEngCreated: EventEmitter<BRCRepairInventoryReceiveItemRequest> = new EventEmitter<BRCRepairInventoryReceiveItemRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  message: string;
  _isEditMode: boolean;
  today = new Date();
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  bRCRepairTypeList: Array<BRCRepairType> = [];
  bRCRepiarBalancedList: Array<BRCRepiarBalancedAt> = [];
  bRCRepairBERRemarkList: Array<BRCRepairBERRemark> = [];
  bRCRepiarResolutionMethodList: Array<BRCRepiarResolutionMethod> = [];
  choosenList: Array<BRCRepiarResolutionMethod> = [];
  resolution: BRCRepiarResolutionMethod = new BRCRepiarResolutionMethod();
  brcRepiarResolutionMethodChoosenRequest: BRCRepiarResolutionMethodChoosenRequest = new BRCRepiarResolutionMethodChoosenRequest();
  brcRepiarResolutionMethodChoosenRequestList: Array<BRCRepiarResolutionMethodChoosenRequest> = [];
  isManageInventoryModelShow: boolean;
  isServiceResourceModel: boolean;
  newServiceResource: ServiceResourseRequest = new ServiceResourseRequest();
  resourceForList: ServiceResourseForList = new ServiceResourseForList();
  resourceList: Array<ServiceResourseForList> = [];
  brcRepairInventoryReceiveItemRequest: BRCRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
  brcInventoryReceiveResponse: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
  dateCustomClasses: DatepickerDateCustomClasses[];

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
    this.brcRepairInventoryReceiveItemRequest.serviceResourceId = brcRepairInventoryReceiveItemRequest.serviceResourceId;
    this.brcRepairInventoryReceiveItemRequest.engineerName = brcRepairInventoryReceiveItemRequest.engineerName;  
  }
  get editEngData(): BRCRepairInventoryReceiveItemRequest {
    return this.brcRepairInventoryReceiveItemRequest;
  }

  constructor(private spinner: NgxSpinnerService, private mangaeInventoryService: ManageInventoryService, private dataPipe: DatePipe) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getTypes();
    this.getRemarks();
    this.getBalanceAt();
    this.getResulutionMethods();
    this.getServiceResourceList();
    this.faPlus;
  }

  getTypes() {
    this.spinner.show();
    this.mangaeInventoryService.getTypes().subscribe(data => {
      this.spinner.hide();
      this.bRCRepairTypeList = data;
    })
  }
  getRemarks() {
    this.mangaeInventoryService.getRemarks().subscribe(data => {
      this.bRCRepairBERRemarkList = data;
    })
  }
  getBalanceAt() {
    this.mangaeInventoryService.getBalanceAt().subscribe(data => {
      this.bRCRepiarBalancedList = data;
    })
  }

  getResulutionMethods() {
    this.spinner.show();
    this.mangaeInventoryService.getResulutionMethods().subscribe(data => {
      this.spinner.hide();
      this.bRCRepiarResolutionMethodList = data;
    })
  }

  getServiceResourceList() {
    this.mangaeInventoryService.serviceResourceList().subscribe(data => {
      this.resourceList = data;
    })
  }

  moveRight(selected: BRCRepiarResolutionMethod) {
    if (selected[0] !== undefined) {
      const index = this.bRCRepiarResolutionMethodList.indexOf(selected[0]);
      this.choosenList.push(selected[0]);
      this.choosenList.forEach(element => {
        this.brcRepiarResolutionMethodChoosenRequest.repariItemId = this.brcRepairInventoryReceiveItemRequest.repairId;
        this.brcRepiarResolutionMethodChoosenRequest.resolutionMethodsId = element.resolutionMethodId;
      })

      this.brcRepiarResolutionMethodChoosenRequestList.push(this.brcRepiarResolutionMethodChoosenRequest);

      this.bRCRepiarResolutionMethodList.splice(index, 1);
      this.resolution = new BRCRepiarResolutionMethod();
      this.brcRepiarResolutionMethodChoosenRequest = new BRCRepiarResolutionMethodChoosenRequest();
    } else {
      this.message = "Please select any 1 resolution method";
      this.popupSuccessModel.show();
    }
  }

  moveLeft(unselect) {
    if (unselect[0] !== undefined) {
      const index = this.choosenList.indexOf(unselect[0]);
      this.bRCRepiarResolutionMethodList.push(unselect[0]);

      this.choosenList.splice(index, 1);
      this.resolution = new BRCRepiarResolutionMethod();
    } else {
      this.message = "Please select any 1 resolution method";
      this.popupSuccessModel.show()
    }
  }
  postCreate() {
    if (this.brcRepairInventoryReceiveItemRequest.brcRepariType == null || this.brcRepairInventoryReceiveItemRequest.brcRepariType === 0) {
      this.message = 'Please fill the type';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.battryVoltageAfterRepair != undefined && (this.brcRepairInventoryReceiveItemRequest.battryVoltageAfterRepair).length >= 200) {
      this.message = 'Please fill the battry voltage after repair under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.battryAHAfterRepair != undefined && (this.brcRepairInventoryReceiveItemRequest.battryAHAfterRepair).length >= 200) {
      this.message = 'Please fill the battry ah after repair under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.battryEngCorrectiveAction != undefined && (this.brcRepairInventoryReceiveItemRequest.battryEngCorrectiveAction).length >= 500) {
      this.message = 'Please fill the battry eng corrective action under 500 characters';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.battryEngRemarks != undefined && (this.brcRepairInventoryReceiveItemRequest.battryEngRemarks).length >= 500) {
      this.message = 'Please fill the battry eng remarks under 500 characters';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.battryBalanceAtId == null || this.brcRepairInventoryReceiveItemRequest.battryBalanceAtId === 0) {
      this.message = 'Please fill the battry balance at';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.brcBERRemarksId == null || this.brcRepairInventoryReceiveItemRequest.brcBERRemarksId === 0) {
      this.message = 'Please fill the brc ber remarks';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.materialSortage != undefined && (this.brcRepairInventoryReceiveItemRequest.materialSortage).length >= 200) {
      this.message = 'Please fill the material sortage under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.brcRepairInventoryReceiveItemRequest.cellBulged != undefined && (this.brcRepairInventoryReceiveItemRequest.cellBulged).length >= 200) {
      this.message = 'Please fill the cell bulged under 200 characters';
      this.popupSuccessModel.show();
    }
    else {
      this.brcRepairInventoryReceiveItemRequest.methodChoosen = this.brcRepiarResolutionMethodChoosenRequestList;
      this.spinner.show();
      this.mangaeInventoryService.receiveFromEng(this.brcRepairInventoryReceiveItemRequest, this.brcRepairInventoryReceiveItemRequest.repairId).subscribe(data => {
        this.message = 'Submitted Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.onReceiveFromEngCreated.emit(data);
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
  addNewServiceResource() {
    this.newServiceResource = new ServiceResourseRequest();
    this.createServiceResourceModel.show();
  }
  createServiceResourceModelhide() {
    this.newServiceResource = new ServiceResourseRequest();
    this.createServiceResourceModel.hide();
  }
  cancelDataServiceResource() {
    this.newServiceResource = new ServiceResourseRequest();
    this.createServiceResourceModel.hide();
  }
  onServiceResourseCreated($event: ServiceResourseRequest) {
    this.getServiceResourceList();
    this.brcRepairInventoryReceiveItemRequest.serviceResourceId = $event.serviceResourceId;
  }
}
