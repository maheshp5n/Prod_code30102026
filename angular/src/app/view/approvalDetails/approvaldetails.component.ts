import {
  ApprovalResponse, ApprovalRequest, ApprovalStepsResponse, ApprovalStepRequest, ActionHistoryObjectAndId
} from '../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ServiceResourseResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ApprovalService } from '../../services/approval.services';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-approvaldetails',
  templateUrl: './approvaldetails.component.html',
  styleUrls: ['./approvaldetails.component.scss']
})

export class ApprovaldetailsComponent implements OnInit {
  @ViewChild('createApprovalModel') createApprovalModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createApprovalLineItemModel') createApprovalLineItemModel: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup: ModalDirective;
  @ViewChild('createaddProductModel') createaddProductModel:ModalDirective;
  @Output()
  onAccountCreated: EventEmitter<ServiceResourseResponse> = new EventEmitter<ServiceResourseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  ApprovelLineItem:Array<ApprovalStepsResponse>=[];
  serialNumberLineItems:number;
  isApprovalOpen: boolean;
  locationListByApproval: number;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  approvalId: number = 0;
  message: string;
  isEditMode: boolean = false;
  edit: boolean = false;
  approvalRequest: ApprovalRequest = new ApprovalRequest();
  approvalResponse: ApprovalResponse = new ApprovalResponse();
  CREATE_Approval_HEADING: string = 'New Approval';
  EDIT_Approval_HEADING: string = 'Edit Approval';
  headingApproval: string;
  isEditModeApproval: boolean;
  isEditModeApprovalLineItem: boolean;
  firstApproval: boolean;
  lastApproval: boolean;
  sizeApproval: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  objectName: string = 'Approval';
  _cFlag: string;
  headingaddPro:string;
  viewAddPriceBook:boolean;
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  purchaseDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  warrantyDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  amcDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  loginData: any;
  itemAddReq:ApprovalStepRequest=new ApprovalStepRequest();
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _approvalService: ApprovalService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.approvalId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.approvalId;
      this.actionHistoryObjectAndId.objectName = 'Approval';
    });
    this._activatedRoute.queryParams.subscribe(queryParam => {
      this._cFlag = queryParam['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    if (this.approvalId !== 0) {
      this.getApprovalById(this.approvalId);
    }
    this.loginData = JSON.parse(sessionStorage.getItem('login'));
    this.getLineItemList();
  }

  getApprovalById(approvalId) {
    this.spinner.show();
    this._approvalService.getApprovalById(this.approvalId).subscribe(data => {
      this.approvalResponse = data;
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changePurchaseDisplay() {
    this.purchaseDisplay.open = !this.purchaseDisplay.open;
    if (this.purchaseDisplay.open) {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeWarrantyDisplay() {
    this.warrantyDisplay.open = !this.warrantyDisplay.open;
    if (this.warrantyDisplay.open) {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeAmcDisplay() {
    this.amcDisplay.open = !this.amcDisplay.open;
    if (this.amcDisplay.open) {
      this.amcDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.amcDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open;
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getApprovalById(this.approvalId);
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getLineItemList();
    }
    return false;
  }

  cancelData() {
    this.createApprovalModel.hide();
    this.createApprovalLineItemModel.hide();
    this.onCancle.emit('cancled');
    this.approvalRequest = new ApprovalRequest();
  }

  onApprovalCreated($event: ApprovalResponse) {
    this.approvalResponse = $event;
    this.getApprovalById(this.approvalId);
    
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  aprovelStages:number;
  approveREQ:boolean;
  addProduct(){
    this.isEditMode=false;
    this.headingaddPro = "Choose Price Book"
    this.viewAddPriceBook = true;
    this.approveREQ=true;
  this.approvalId=this.approvalResponse.id
  this.aprovelStages=this.approvalResponse.approvalStages;
    this.createaddProductModel.show();
  }
  cancelDataaddLineItem(){
    this.itemAddReq=new ApprovalStepRequest;
    this.createaddProductModel.hide();
  }
  LineItemList:Array<ApprovalStepsResponse>=[];
  getLineItemList(){
      this.spinner.show();
      this._approvalService.getLineItemListById(this.approvalId).subscribe(data=>{
      this.spinner.hide();
      this.LineItemList=data;
    })
  }
  ApprovedBy:string;
  stpId:number;
  openEditLineItem(id){
    this.isEditMode=true;
    this.createaddProductModel.show();
    this.spinner.show();
    this._approvalService.getLineItemById(id).subscribe(data=>{
      console.log(data.approvedBy)
      this.spinner.hide();
      this.itemAddReq.approvalId=data.approvalId;
      this.itemAddReq.approvedBy=data.approvedBy;
      this.ApprovedBy=data.approvedBy;
      this.itemAddReq.approvedStage=data.approvedStage;
      this.itemAddReq.stepName=data.stepName;
      this.itemAddReq.objectFieldName=data.objectFiledName;
      this.itemAddReq.onApprovedValue=data.onAprovedValue;
      this.itemAddReq.onRejectedValue=data.onRejectedValue;
      this.stpId=data.id;
    })
  }
  deleteLineItem(id){
    this.spinner.show()
    this._approvalService.deletApprovalItem(id).subscribe(h=>{
      this.spinner.hide();
      this.getLineItemList();
    });
  }
  onaddProductCreated($event){
    this.getLineItemList();
    this.LineItemList.push($event);
  }
}

