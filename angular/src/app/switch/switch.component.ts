import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-switch',
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss']
})
export class SwitchComponent implements OnInit {

  private userToken: string;
  private hash: string;
  private routeurl: string;

  constructor(private _http: HttpClient, private _router: Router, private _activatedRoute: ActivatedRoute) {
    _activatedRoute.queryParams.subscribe(param => {
      this.userToken = param['tokan'];
      this.hash = param['hash'];
      this.routeurl = param['url'];
      this.doLogin();
    });
  }

  ngOnInit() {

  }

  doLogin() {
    this._http.post('api/switch/login', { rt: this.userToken, hash: this.hash, url: this.routeurl }).subscribe(data => {
      sessionStorage.setItem('login', JSON.stringify(data));
      this._router.navigateByUrl(this.routeurl);
    }, error => {
      this._router.navigate(['/login']);
    });
  }

}
