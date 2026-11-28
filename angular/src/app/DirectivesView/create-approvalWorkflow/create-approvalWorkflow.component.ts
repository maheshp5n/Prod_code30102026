import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import {
  ResourceType,
  LocationForList,
  UserForList,
  ApprovalWorkflowRequest,
  ApprovalResponse,
  BusinessDomain,
  userRole,
  ObjectDefinition,
  RoleClass,
  UserRoleResponse,
  ApprovalStepRequest
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ApprovalService } from '../../services/approval.services';
import { FieldValidation } from '../../services/validation.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';

@Component({
  selector: 'app-create-approval',
  templateUrl: './create-approvalWorkflow.component.html',
  styleUrls: ['./create-approvalWorkflow.component.scss']
})
export class CreateApprovalComponent implements OnInit {
  userRoleList: Array<any> = [];
  userUserList:Array<UserForList>=[];
  ListUser:Array<UserRoleResponse>=[];
  itemAddReq:ApprovalStepRequest=new ApprovalStepRequest();
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  @Input()
  set ID(id: number) {
    this.approvalRequest.id = id;
  }

  get ID(): number {
    return this.approvalRequest.id;
  }
  @Input()
  set editApprovalData(approvalRequest: ApprovalWorkflowRequest) {
    this.spinner.show();
    this.popupNgClass = 'alert alert-danger center center-block';
    this.approvalRequest = approvalRequest;
    if (!this._isEditMode) {
      // this.approvalRequest.approvalStatusId = 1;
      this.spinner.hide();
    }
  }

  get editApprovalData(): ApprovalWorkflowRequest {
    return this.approvalRequest;
  }

  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices,
    private _approvalService: ApprovalService, private _accountCatagoryServices: AccountsCatagoryServices) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  userselect:boolean=false;
  _isEditMode: boolean;
  approvalRequest: ApprovalWorkflowRequest = new ApprovalWorkflowRequest();
  message: string;
  istrue: boolean;
  roleselect:boolean=true
  userForList: Array<UserForList> = [];
  resourceTypeList: Array<ResourceType> = [];
  locationTypeList: Array<LocationForList> = [];
  faPlus: string = 'btn btn-primary fa fa-plus';
  flag: boolean;
  noMatchLocation: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempServiceId: number;
  tempServiceName: string;
  noMatchLegalEntity: string;
  today = new Date();

  @Output()
  onApprovalCreated: EventEmitter<ApprovalResponse> = new EventEmitter<ApprovalResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  dateCustomClasses: DatepickerDateCustomClasses[];
  loginData;
  _catagory: Array<BusinessDomain> = [];

  objectLableNames: Array<ObjectDefinition> = [];

  ngOnInit() {
    this.ownerName();
    this.getBusinessDomain();
    this.getObjectLableNames();
    // this.getAuthorities();
    const loginDetails = JSON.parse(sessionStorage.getItem('login'));
  }
  // getRoles() {
  //   this.spinner.show();
  //   this._approvalService.getRoleList().subscribe(data => {
  //     this.userRoleList = data;
  //     if (data != null) {
  //       this.spinner.hide();
  //     }

  //   }, error => {
  //     this.spinner.hide();
  //     alert('something went wrong')
  //   });
  // }

  getBusinessDomain() {
    this._accountCatagoryServices.getCatagory().subscribe(data => this._catagory = data);
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
  }

  cancelData() {
    this.approvalRequest = new ApprovalWorkflowRequest();
    this.onCancle.emit('cancled');
  }

  cancelData2() {
    this.isEditMode = false;
  }
  authorities:string=null;
  userrole:string=null;
  userEmailId:string=null;
  submitterList:Array<string>=[];
  USER:Array<string>=[];
  listDONE:Array<string>=[];
  postCreateApproval() {
    let temp = new ApprovalResponse();
    this.flag = false;
    if (FieldValidation.isEmpty(this.approvalRequest.objectClass)) {
      this.message = 'Please fill Object';
    } else {
      this.flag = true;
      
      if(this.userselect==true){
        this.submitterList=[];
        this.USER=[];
        // this.submitterList.concat( {ROLE:this.userrole,USER:this.authorities});
        this.submitterList.push("ROLE:"+this.userrole)
        this.USER.push("USER:" +this.authorities)
      this.listDONE=  this.submitterList.concat( this.USER);
     
      
      }else{
        this.USER=[];
        this.submitterList=[];
        this.submitterList.push("ROLE:"+this.userrole)
        this.USER.push( "USER:"+this.userEmailId);
      this.listDONE=  this.submitterList.concat( this.USER);
      }
      if (this._isEditMode === false) {
       
        this.spinner.show();
        this.approvalRequest.initialSubmitterList=this.listDONE;
        this._approvalService.postApproval(this.approvalRequest).subscribe(data => {
          this.tempServiceName = data.objectName;
          this.message = 'Submitted Successfully !';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onApprovalCreated.emit(data);
          this.popupSuccessModel.show();
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      } else {
        this.spinner.show();
        this.approvalRequest.id;
        this.approvalRequest.initialSubmitterList=this.listDONE;
        this._approvalService.putApproval(this.approvalRequest).subscribe(data => {
            this.message = 'Updated Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.spinner.hide();
            this.popupSuccessModel.show();
            this.onApprovalCreated.emit(data);
          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          });
      }
    }
    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }

  getObjectLableNames() {
    this._approvalService.getObjectDef().subscribe(data => {
      this.objectLableNames = data;
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  cancelDataApprovalLineItem() {
    this.onCancle.emit('can');
  }

  // setProps() {
  //   console.log(this.approvalRequest.objectName);
  //   this.getObjectNames(this.approvalRequest.objectName);
  // }

  // getObjectNames(id) {
  //   this._approvalService.getObjectNames(id).subscribe(data => {
  //     this.approvalRequest.objectClassName = data;
  //     console.log(this.approvalRequest.objectClassName);
  //     this.getObjectIdFiledName(this.approvalRequest.objectClassName);
  //   }, err => {

  //   });
  // }

  // getObjectIdFiledName(id) {
  //   this._approvalService.getObjectIdFiledName(id).subscribe(data => {
  //     this.approvalRequest.objectIdFiledName = data;
  //   }, err => {

  //   });
  // }
  // getRoleForUsers(){
  //   this._approvalService.getUsersList().subscribe(data=>{
  //     this.userRoleList=data;
  //   })
  // }
  label:string;
  value:string
  model:RoleClass = new RoleClass();
  name:string;
  list = new Array<RoleClass>();
  getRolesList(user){
console.log(user)
if(user=='user'){
  this.spinner.show();
  this._approvalService.getUsersList().subscribe(data=>{
   this.userUserList=data;
   this.roleselect=true;
   this.userselect=false
   this.spinner.hide();
      //  data.forEach((e)=>{
      //   let _ct = new RoleClass();
      //   _ct.valuesValue=e.userEmailId;
      //     _ct.labelsValue = e.userFirstName +' '+e.userMiddleName+ ' '+e.userLastName;
      //     this.ListUser.push(_ct); 
      //     this.spinner.hide();
      // });
    
    console.log(this.ListUser)
  })
}else{
  this.spinner.show();
  this._approvalService.getRoleList().subscribe(data => {
    this.spinner.hide();
    this.userselect=true;
    this.roleselect=false;
    // for(let i=0; i<data.length; i++){
      this.ListUser=data;
      // data.forEach((e)=>{
      //   this.model = new RoleClass();
      //   this.model.valuesValue=e.authorities;
      //     this.model.labelsValue = e.parentUserAuthorities;    
      // this.ListUser.push(this.model); 
      // });
    console.log(this.ListUser)
    // }

  }, error => {
    this.spinner.hide();
    alert(error.error.message);
  });
}
  }
  // avid sir code

  
  
 
}
