import { state } from '@angular/animations';
import { Injectable } from '@angular/core';
import { CanActivate, Router, CanActivateChild, ActivatedRoute } from '@angular/router';

@Injectable()
export class LayoutgaurdGuard implements CanActivate, CanActivateChild {
  nav = [];
  constructor(private _router: Router) { }
  canActivate() {
    if (localStorage.getItem('login')) {
      return true;
    }
    this._router.navigate(['/login'],{queryParams:{returnurl:this._router.getCurrentNavigation().finalUrl}});
    return false;
  }
  canActivateChild() {
    const userNav = JSON.parse(localStorage.getItem('login'));
    const url = this._router.getCurrentNavigation().finalUrl;
    //console.log(`URL Routing ==>${url}`);

    for (let i = 0; i < userNav.nav.length; i++) {

      if (url.toString().indexOf(userNav.nav[i].url) >= 0) {
        return true;
      }
    }
    if(url.toString()==='/403' ||url.toString()==='/objectactionhistory'){
      return true;
    }
    if(url.toString().indexOf('/details')>=0){
      return true;
    }
    if(url.toString().indexOf('/objectactionhistory?object')>=0){
      return true;
    }
    this._router.navigate(['/403']);
    return false;
  }
}
