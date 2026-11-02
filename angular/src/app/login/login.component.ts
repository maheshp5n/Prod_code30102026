import { Component, OnInit, ViewChild } from '@angular/core';
import { Loginservices } from '../services/login.services';
import { Login } from '../services/model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('fail') fail: ModalDirective;

  _login: Login = new Login();
  returnurl='/';
  constructor(private spinner: NgxSpinnerService, private _loginservice: Loginservices, private _router: Router,private _activatedRoute:ActivatedRoute) { }
  message: string;
  ngOnInit() {
    this._activatedRoute.queryParams.subscribe(param=>{
      if(param['returnurl']!=undefined&&param['returnurl']!=null){
        this.returnurl=param['returnurl'];
      }
    });
    if (localStorage.getItem('login')) {
      let nav = JSON.parse(localStorage.getItem('login'));
      if (nav.nav.length > 0) {
        this._router.navigate([nav.nav[0].defaultUrl]);
      }
      else {
        this._router.navigate(['/']);
      }
    }
  }
  doLogin() {
    this.spinner.show();
    this._loginservice.doLogin(this._login).subscribe(data => {
      localStorage.setItem('login', JSON.stringify(data));
      
      if (data.nav.length > 0) {
        if(this.returnurl==='/'){
          if (data.nav[0].defaultUrl != null) {
            this._router.navigate([data.nav[0].defaultUrl]);
          }
          else {
            this._router.navigate(['/']);
          }
        }else{
          this._router.navigateByUrl(this.returnurl);
        }
        
      }
      setTimeout(() => {
        this.spinner.hide();
      });

    }, err => {
      this.spinner.hide();
      if (err.error.status == 409) {
        if (confirm(err.error.message)) {
          this.doForceLogin();
        }
        return;
      }
      if (err.status == 401) {
        this.message = "Incorrect User Id/Password ...";
      }
      else if (err.status == 400) {
        this.message = "Validation Failed";
      }
      else if (err.status == 428) {
        this.message = "Password does not match Exicom Password Policy. Please change your password";
      }
      else {
        this.message = "Login Failed";
      }
      this.fail.show();
    });
  }
  doForceLogin() {
    this.spinner.show();
    this._loginservice.doforceLogin(this._login).subscribe(data => {
      localStorage.setItem('login', JSON.stringify(data));
      if (data.nav.length > 0) {
        if (data.nav[0].defaultUrl != null) {
          this._router.navigate([data.nav[0].defaultUrl]);
        }
        else {
          this._router.navigate(['/']);
        }
      }
      setTimeout(() => {
        this.spinner.hide();
      });

    }, err => {
      this.spinner.hide();

      if (err.status == 401) {
        this.message = "Incorrect UserId/Password ...";
      }
      else if (err.status == 400) {
        this.message = "Validation Failed";
      }
      else if (err.status == 428) {
        this.message = "Password does not match Exicom Password Policy. Please change your password";
      }
      else {
        this.message = "Login Failed";
      }
      this.fail.show();
    });
  }

}
