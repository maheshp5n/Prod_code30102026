import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserrolesServices } from '../../services/userroles.services';
import { UserRoleResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-roledetails',
  templateUrl: './roledetails.component.html',
  styleUrls: ['./roledetails.component.scss']
})
export class RoledetailsComponent implements OnInit {
  nodes: any = [
  ];

  userroles: Array<UserRoleResponse> = [];
  selecttedUserrole: string;
  actiondetails = {
    selectedUserrole: '',
    newRole: false,
    changeParent: false,
    nRoleValue: '',
    pRoleValue: '',
    roleGroup: 0
  }
  rolesGroup = [];
  @ViewChild('actionWidows') actionWidows: ModalDirective;
  @ViewChild('alertmodule') alertmodule: ModalDirective;
  alertDetails = {
    type: 'danger',
    message: ''
  }
  directionh = 'horizontal';

  constructor(private _ngxSpinner: NgxSpinnerService, private _userrolesService: UserrolesServices, private _global:GlobalserviceService) { }

  ngOnInit() {
    this.getUserRoles();
    this.getRoleGroup();
  }
  test($event) {
    this.actiondetails.selectedUserrole = $event.name;
    this.userroles.forEach(u => {
      if (u.authorities == this.actiondetails.selectedUserrole) {
        this.actiondetails.pRoleValue = u.parentUserAuthorities;
      }
    });
    this.actionWidows.show();

  }
  onActionHide() {
    this.actiondetails.selectedUserrole = '';
    this.actiondetails.newRole = false;
    this.actiondetails.changeParent = false;
    this.actiondetails.nRoleValue = '';
    this.actiondetails.pRoleValue = '';
  }
  getUserRoles() {
    this._ngxSpinner.show();
    this._userrolesService.getUserRoleResponse().subscribe(data => {
      this._ngxSpinner.hide();
      this.userroles = data;
      this.makeDataPrepare();
    }, error => this._ngxSpinner.hide());
  }
  makeDataPrepare() {
    this.nodes = [];
    for (let i = 0; i < this.userroles.length; i++) {
      if (this.userroles[i].parentUserAuthorities == null || this.userroles[i].parentUserAuthorities == '') {
        this.nodes.push({ name: this.userroles[i].authorities, cssClass: 'ngx-org-head', image: 'assets/images/role.png', title: '', childs: this.getChilds(this.userroles[i].authorities) });
      }
    }
  }
  getChilds(parentName: string) {
    let temp = [];
    for (let i = 0; i < this.userroles.length; i++) {
      if (this.userroles[i].parentUserAuthorities == parentName) {
        temp.push({ name: this.userroles[i].authorities, cssClass: 'ngx-org-head', image: 'assets/images/role.png', title: '', childs: this.getChilds(this.userroles[i].authorities) });
      }
    }
    return temp;
  }
  changeAction(btn) {

    if (btn == 'n') {
      this.actiondetails.newRole = true;
      this.actiondetails.changeParent = false;
    }
    else {
      this.actiondetails.newRole = false;
      this.actiondetails.changeParent = true;
    }
  }
  saveUserroles() {
    let s = new UserRoleResponse();
    if (this.actiondetails.changeParent) {
      s.authorities = this.actiondetails.selectedUserrole;
      if(this.actiondetails.pRoleValue!="" && this.actiondetails.pRoleValue!=null && this.actiondetails.pRoleValue!=undefined)
      {
      s.parentUserAuthorities = this.actiondetails.pRoleValue;
      }
      else{
        this._global.alert({type:'alert',message:"Please Fill Parent Role"});
        return;

      }
    }
    if (this.actiondetails.newRole) {
      s.authorities = this.actiondetails.nRoleValue;
      s.parentUserAuthorities = this.actiondetails.selectedUserrole;
      s.roleGroup = this.actiondetails.roleGroup;
    }
    if (s.authorities == undefined || s.authorities == null || s.authorities == '') {
      this._global.alert({type:'alert',message:"No Data Found For Action"});

      // this.showAlert(`No data found for action`);
      return;
    }
    this._ngxSpinner.show();
    this._userrolesService.createUserroles(s).subscribe(data => {
      this.actionWidows.hide();
      this._ngxSpinner.hide();
      this.getUserRoles();
    }, error => {
      this._ngxSpinner.hide();
      this._global.alert({type:'alert',message:error.error.message});

      // this.showAlert(error.error.message);
    });
  }
  removeCurrentSelection() {
    this._ngxSpinner.show();
    this._userrolesService.deleteRoles(this.actiondetails.selectedUserrole).subscribe(data => {
      this._ngxSpinner.hide();
      this.actionWidows.hide();
      this.getUserRoles();
    }, error => {
      this._ngxSpinner.hide();
      this._global.alert({type:'alert',message:error.error.message});

    });
  }
  // showAlert(message) {
  //   this.alertDetails.type = 'danger';
  //   this.alertDetails.message = message;
  //   this.alertmodule.show();
  // }
  showMessage(message) {
    this.alertDetails.type = 'success';
    this.alertDetails.message = message;
    this.alertmodule.show();
  }

  getRoleGroup() {
    this._userrolesService.getRoleGroup().subscribe(data => this.rolesGroup = data);
  }
}
