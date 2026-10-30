import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../services/login.services';
import { GlobalserviceService } from '../globalservice.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgetpass',
  templateUrl: './forgetpass.component.html',
  styleUrls: ['./forgetpass.component.scss']
})
export class ForgetpassComponent implements OnInit {


  userdetails = {
    email: '',
    otp: '',
    disableemail: false,
    resetdone: false
  };

  constructor(private _ngx: NgxSpinnerService, private _login: Loginservices, private _global: GlobalserviceService) { }

  ngOnInit() {
  }

  saveClick() {
    if (!this.userdetails.disableemail) {
      this._ngx.show();
      this._login.getOtp(this.userdetails).subscribe(data => {
        this._ngx.hide();
        this.userdetails.disableemail = true;
      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
    if (this.userdetails.disableemail) {
      this._ngx.show();
      this._login.getRecover(this.userdetails).subscribe(data => {
        this._ngx.hide();
        this.userdetails.resetdone = true;
      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }

  }

}
