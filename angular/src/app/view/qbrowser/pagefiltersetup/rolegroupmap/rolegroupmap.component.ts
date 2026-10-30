import { GlobalserviceService } from '../../../../globalservice.service';
import { UserrolesServices } from '../../../../services/userroles.services';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rolegroupmap',
  templateUrl: './rolegroupmap.component.html',
  styleUrls: ['./rolegroupmap.component.scss']
})
export class RolegroupmapComponent implements OnInit {

  roles = [];
  roleGroups = [];
  constructor(private _global: GlobalserviceService, private _userRole: UserrolesServices) { }

  ngOnInit() {
    this.getRoleGroup();
    this.getRoles();
  }

  udpateRoleGroup(r) {
    this._userRole.updateRoleGroup(r).subscribe(data => {
      this._global.alert({ type: '', message: 'Role group updated successfully' });
    }, error => {
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  getRoleGroup() {
    this._userRole.getRoleGroup().subscribe(data => this.roleGroups = data);
  }
  getRoles() {
    this._userRole.getRoles().subscribe(data => this.roles = data);
  }

}
