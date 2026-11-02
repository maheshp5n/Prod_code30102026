import { Component, OnInit, OnDestroy, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { PingPoingService } from '../../services/ping.service';
import { WabsocketService } from '../../services/wabsocket.service';
import { LayoutService } from '../../services/layout.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Notifications, Login, ChangePassword, Page } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ChangePasswordService } from '../../services/change-password.service';
import { exicomNavItems } from '../../_nav';
import { SearchService, GlobalSearchIndex } from '../../services/search.service';
import { Loginservices } from '../../services/login.services';
import { GlobalserviceService } from '../../globalservice.service';
import { DOCUMENT } from '@angular/common';
@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit, OnDestroy {
  // constructor(private _search: SearchService, private _webSocket: WabsocketService, private _changePasswordService: ChangePasswordService, private router: Router, private _layoutService: LayoutService, private spinner: NgxSpinnerService, private _router: Router, private _ping: PingPoingService) { }
  public navItems = [];//exicomNavItems;
  public sidebarMinimized = true;

  @ViewChild('searchResult') searchResult: ModalDirective;
  searchKyeString: string;
  @ViewChild('menu') menu: ModalDirective;
  @ViewChild('changePass') changePass: ModalDirective;
  @ViewChild('passChanged') passChanged: ModalDirective;
  @ViewChild('customerInventoryBulk') customerInventoryBulk: ModalDirective;
  @ViewChild('adsearchResult') adsearchResult: ModalDirective
  numberOfNotification: number = 0;
  loginData: any;
  interval;
  globalSearch: Array<GlobalSearchIndex> = [];
  isRunning: boolean = false;
  previousNotificationsPage: Page<Notifications> = new Page();
  previousNotificationsList: Array<Notifications> = [];
  adSearch = false;
  navsearch = '';
  customerInvnetorySch;
  customerInventoryDataKeys = [];
  customerInventoryData = [];

  private changes: MutationObserver;
  public element: HTMLElement;

  constructor(@Inject(DOCUMENT) _document: any,
    private _search: SearchService,
    private _webSocket: WabsocketService,
    private _changePasswordService: ChangePasswordService,
    private _layoutService: LayoutService,
    private spinner: NgxSpinnerService,
    private _router: Router,
    private _ping: PingPoingService,
    private loginservice: Loginservices,
    private _global: GlobalserviceService,
    private _login: Loginservices

  ) {
    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
    });
    this.element = _document.body;
    this.changes.observe(<Element>this.element, {
      attributes: true,
      attributeFilter: ['class']
    });
  }
  intervals = [];
  notif;
  ir;
  message: string;
  _changePasswordModel: ChangePassword = new ChangePassword();
  ngOnInit() {
    this.getLoggedInUser();
    this.getMasterTab();
    //console.log(this.navItems);
    this.ir = [
      "accounts", "assets", "city", "circles",
      "contacts", "expense", "home", "ligalentity", "products", "sites",
      "Warehouse", "zone", "Opportunities", "Quote", "ProductLineItems", "Location"
    ];
    this._webSocket.connect();
    this.getPreviousNotif();
    this._webSocket.newNotification.subscribe(data => {
      this.previousNotificationsList.unshift(data);
    });
    this.interval = setInterval(() => this.doPing(), 1000 * 15);
    this.customerInvnetorySch = this._global.getCustomerInvnentorydata().subscribe(data => {
      if (data.length > 0) {
        this.customerInventoryDataKeys = Object.keys(data[0]);
      }
      this.customerInventoryData = data;
      this.customerInventoryBulk.show();
    });
  }
  getPreviousNotif() {
    this.spinner.show();
    this._layoutService.getPrevNot().subscribe(data => {
      this.spinner.hide();
      this.previousNotificationsPage = data;
      this.previousNotificationsList = data.content;
      this.numberOfNotification = 0;
      this.previousNotificationsList.forEach(noti => {
        if (!noti.notificationRead) {
          this.numberOfNotification = this.numberOfNotification + 1;
        }
      });
    }, err => { this.spinner.hide(); });
  }

  searchDetails() {
    this.spinner.show();
    this._search.searchdata(this.searchKyeString).subscribe(data => {
      if (data.length == 1) {
        this.searchKyeString = '';
        this.spinner.hide();
        this._router.navigateByUrl(data[0].objectDetailsUrl);
      }
      else {
        this.globalSearch = data;
        this.searchResult.show();
      }

    }, err => this.spinner.hide());
  }
  ngOnDestroy() {
    try {

      this.changes.disconnect();

    } catch (e) {

    }

    try {
      clearInterval(this.interval);
    } catch (e) {

    }
    try {
      localStorage.clear();
    } catch (e) {

    }
    try {
      this._webSocket.disconnect();
    } catch (e) {

    }
    try {
      this.customerInvnetorySch.unsubscribe();
    } catch (e) { }

  }

  doPing() {
    // if (!this.isRunning) {
    //   this.isRunning = true;
    //   this._ping.ping().subscribe(data => { this.isRunning = false; }, err => {
    //     this.isRunning = false;
    //     localStorage.removeItem('login');
    //     localStorage.clear();
    //     this._router.navigate(['/login']);
    //   });
    // }
  }
  changeNavigation(index: number) {

  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  doLogout() {
    this._ping.logout().subscribe(data => {
      localStorage.removeItem('login');
      localStorage.clear();
      this._router.navigate(['/login']);
    }, error => {
      localStorage.removeItem('login');
      localStorage.clear();
      this._router.navigate(['/login']);
    });

  }
  close() {
    this.menu.hide();
    this.spinner.show();
    setTimeout(() => {
      this.spinner.hide();
    }, 1500
    );
  }

  spin() {
    this.spinner.show();
    setTimeout(() => {
      this.spinner.hide();
    }, 1000
    );
  }
  changePasswordShowModel() {
    this.changePass.show();
  }
  changePassword() {
    console.log(this._changePasswordModel.newPassword.length);
    if (this._changePasswordModel.newPassword.length < 5 || this._changePasswordModel.newPassword.length > 15) {
      this.message = '** Password length must be between 5 - 15 Character';
      this.passChanged.show();
    } else if (this._changePasswordModel.newPassword === this._changePasswordModel.confirmPassword) {
      this.spinner.show();
      this._changePasswordService.changePassword(this._changePasswordModel).subscribe(data => {
        if (data != null) {
          this.spinner.hide();
        }
        this.message = 'Password successfully changed ...';
        this.passChanged.show();
        setTimeout(() => {
          this.changePass.hide();
          this.doLogout();
          this._router.navigate(['/login']);
        }, 1500
        );
      }, err => {
        alert('Something Wrong');
      });
    } else {
      this.message = '** New Password and Confirm Password should match!!!';
      this.passChanged.show();
    }
  }

  cancelData() {
    this._changePasswordModel = new ChangePassword();
    this.changePass.hide();
  }
  getMasterTab() {
    let nav = [];
    let item = JSON.parse(localStorage.getItem('login')).nav;
    for (let i = 0; i < item.length; i++) {
      if (item[i].parentName == null) {
        let ch = this.getChilds(item[i].name);
        if (ch.length > 0) {
          item[i].children = ch;
        }
        nav.push(item[i]);
      }
    }
    this.navItems = nav.sort((a, b) => a.orderId - b.orderId);
  }
  getChilds(name: string) {
    let nav = [];
    let item = JSON.parse(localStorage.getItem('login')).nav;
    for (let i = 0; i < item.length; i++) {
      if (item[i].parentName == name) {
        nav.push(item[i]);
      }
    }
    try {
      nav = nav.sort((a, b) => {
        if (a.name > b.name) {
          return 1;
        }
        return -1;
      });
      console.log(`Sorting by name `);
    }
    catch (e) {
      console.log(e);
    }
    return nav;
  }

  notificationNavigation(url, id: number) {
    this._layoutService.getPrevNotsById(id).subscribe(data => {
      this.getPreviousNotif();
    });
    this._router.navigateByUrl(url);
  }


  readAllNotification() {
    this._layoutService.readAllNotification().subscribe(data => {
    });
    return false;
  }
  adsearchResultShow() {
    if (this.searchKyeString != null && this.searchKyeString != undefined  || this.searchKyeString!='') {
     this.searchKyeString =  this.searchKyeString.trim();
      this.adsearchResult.show();
    }
    else {
      this._global.alert({ type: 'alert', message: 'Enter Valid Key for Search' });
    }

  }

  getNavigation(id) {
    this.spinner.show();
    this._login.getNavigation(id).subscribe(data=>{
      this.spinner.hide();
      let item = JSON.parse(localStorage.getItem('login'));
      item.nav=data;
      localStorage.setItem('login',JSON.stringify(item));
      this.getMasterTab();

    },error=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

}



export interface MenueBar {
  path: string;
  name: string;
  downArrow: boolean;
}
