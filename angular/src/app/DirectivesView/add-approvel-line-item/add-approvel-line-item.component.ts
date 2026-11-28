import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { ApprovalStepRequest, UserForList, UserRoleResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ApprovalService } from '../../services/approval.services';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-add-approvel-line-item',
  templateUrl: './add-approvel-line-item.component.html',
  styleUrls: ['./add-approvel-line-item.component.scss']
})
export class AddApprovelLineItemComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel :ModalDirective;
itemAddReq:ApprovalStepRequest=new ApprovalStepRequest();
_isEditMode: boolean;
popupNgClass:string='alert alert-danger center center-block';
message:string;
authorities:string=null;
userrole:string=null;
userEmailId:string=null;
letString1:string;
letString2:string;
@Input()
set isEditMode(isEditMode: boolean) {
  this._isEditMode = isEditMode;
}

get isEditMode(): boolean {
  return this._isEditMode;
}


@Input()
set editAddItemData(itemAddReq:ApprovalStepRequest) {
 this.itemAddReq = itemAddReq;
}

get editAddItemData():ApprovalStepRequest  {
 return this.itemAddReq
}
approvalStages:number;
@Input()
set approvalID(id:number){
  this.itemAddReq.approvalId=id;
}
get approvalID():number{
  return this.itemAddReq.approvalId;
}
stepId:number;
@Input()
set stpID(stpid:number){
  this.stepId=stpid;

}
get stpID():number{
  return this.stepId;
}
@Input()
set stages(stage:number){
  this.approvalStages=stage;
}
get stages():number{
  return this.approvalStages;
}
@Output()
onAddProductCreated: EventEmitter<ApprovalStepRequest> = new EventEmitter<ApprovalStepRequest>();
@Output()
onAddProductCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _approvalService: ApprovalService, private loginService: Loginservices) { }

  ngOnInit() {

  }

  postAddLineItem(){
    if(this.userselect==true){
      // this.letString1="ROLE:"+this.userrole;
      this.letString2=this.userrole+":" +this.authorities;
  
    }else{
      // this.letString1="ROLE:"+this.userrole;
      this.letString2=this.userrole +":" +this.userEmailId;
    }
    if(this.itemAddReq.approvedStage> this.approvalStages){
      this.message="Please Select Stage Less Than" + " "+this.approvalStages;
      this.popupNgClass='alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }else{
      if(this.isEditMode){
        this.itemAddReq.id=this.stepId;
        this.itemAddReq.approvedBy=this.letString2;
        this.spinner.show();
        this._approvalService.putlinItmApproval(this.itemAddReq).subscribe(data=>{
          this.spinner.hide();
          this.message = 'Submitted Successfully !';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAddProductCreated.emit(data)
          this.popupSuccessModel.show();
        },err=>{
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        })

      }else{
        this.itemAddReq.approvedBy=this.letString2;
        this.spinner.show()
        this._approvalService.postApprovalLineItem(this.itemAddReq).subscribe(data=>{
          this.spinner.hide();
          this.message = 'Submitted Successfully !';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAddProductCreated.emit(data)
          this.popupSuccessModel.show();
    
        },err=>{
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        })
  

      }
     
    }
   
  }
  cancelAddItem(){
    this.onAddProductCancle.emit('cancel');
  }
  // 
  roleselect:boolean=true
  userForList: Array<UserForList> = [];
  userUserList:Array<UserForList>=[];
  userselect:boolean;
  ListUser:Array<UserRoleResponse>=[];
  getRolesList(user){
    console.log(user)
    if(user=='user'){
      this.spinner.show();
      this._approvalService.getUsersList().subscribe(data=>{
       this.userUserList=data;
       this.roleselect=true;
       this.userselect=false
       this.spinner.hide();
      })
    }else{
      this.spinner.show();
      this._approvalService.getRoleList().subscribe(data => {
        this.spinner.hide();
        this.userselect=true;
        this.roleselect=false;
          this.ListUser=data;
    
      }, error => {
        this.spinner.hide();
        alert(error.error.message);
      });
    }}


    popupClose() {
      this.popupSuccessModel.hide();
      if (this.popupNgClass === 'alert alert-success center center-block') {
        this.onAddProductCancle.emit('cancled');
        this.itemAddReq=new ApprovalStepRequest;
      }
    }
}
