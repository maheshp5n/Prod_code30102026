import { Component, OnInit, ViewChild } from '@angular/core';
import { ActionHistoryObjectAndId, UserResponse } from '../../services/model';
import { UserService } from '../../services/user.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ModalDirective } from 'ngx-bootstrap';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-userprofile-details',
  templateUrl: './userprofile-details.component.html',
  styleUrls: ['./userprofile-details.component.scss']
})
export class UserprofileDetailsComponent implements OnInit {
  users = new UserResponse();
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  message: string;
  tabledata: any
  userName:string;
  isEditMode: boolean = false;
  heading: string;
  page:number=0;
  serialNumber:number;
  first: boolean;
  last: boolean;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create User';
  EDIT_ACCOUNT_HEADING: string = 'Edit User';
  popupNgClass: string = 'alert alert-danger center center-block';
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private userservice:UserService,private spinner: NgxSpinnerService,
    private _loginService: Loginservices,private _activatedRoute: ActivatedRoute) { 
      this._activatedRoute.params.subscribe(pram => {
        this.userName = pram['userName'];
        this.actionHistoryObjectAndId.objectId = this.userName;
        this.actionHistoryObjectAndId.objectName = 'LoginUser';
     
      });
    }

  ngOnInit() {
    this.getUserDetail();
  }
  goToDetails(managerEmailId){
    this.userName=managerEmailId;
    console.log(this.userName);
    this._activatedRoute.params.subscribe(pram => {
      this.userName = pram['userName'];
      this.spinner.show();
      this.userservice.getUserByuserId(this.userName).subscribe(data=>{
        this.spinner.hide();
        this.users=data;
      },error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this.popupSuccessModel.show();
      })
    });
  
}
  getRefres($event){
    console.log($event)
    this.userName=$event.managerEmailId;
    this.getUserDetail();
  }
 
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }
  getUserDetail(){
    this.spinner.show();
    this.userservice.getUserByuserId(this.userName).subscribe(data=>{
      this.spinner.hide();
      this.users=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }
 
}
