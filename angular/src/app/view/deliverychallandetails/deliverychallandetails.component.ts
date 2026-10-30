import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { deliverychallan } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-deliverychallandetails',
  templateUrl: './deliverychallandetails.component.html',
  styleUrls: ['./deliverychallandetails.component.css']
})
export class deliverychallandetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createNewdeliverychallan', { static: false }) createNewdeliverychallan: ModalDirective;
  @ViewChild('changeStatus', { static: false }) changeStatus: ModalDirective;

  classFlagindex: number;
  classFlags: string[];
  classFlagsBefore: string[];
  classFlagsAfter: string[];
  flag: boolean;
  ngClassDetails: string = 'active';
  ngClassActivity: string = '';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  vActivity: boolean;
  vReleated: boolean;
  message: string;
  isEditMode: boolean = false;
  heading: string;
  CREATE_deliverychallan_HEADING: string = 'New Delivery Challan';
  EDIT_deliverychallan_HEADING: string = 'Edit Delivery Challan';
  newdeliverychallan: deliverychallan = new deliverychallan();
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  shippingDetailsDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  totalDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  useDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.params.subscribe(pram => {
      // this.productid = pram['id'];
    });
  }

  ngOnInit() {
    this.classFlags = ['complete', 'complete', 'current', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending'];
  }

  // changeBreadcrumb(i) {

  // }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }

  changeShippingDetailsDisplay() {
    this.shippingDetailsDisplay.open = !this.shippingDetailsDisplay.open;
    if (this.shippingDetailsDisplay.open) {
      this.shippingDetailsDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.shippingDetailsDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeTotalDisplay() {
    this.totalDisplay.open = !this.totalDisplay.open;
    if (this.totalDisplay.open) {
      this.totalDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.totalDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  useInfoDisplay() {
    this.useDisplay.open = !this.useDisplay.open;
    if (this.useDisplay.open) {
      this.useDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.useDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.ngClassActivity = '';
      this.vDetails = true;
      this.vReleated = false;
      this.vActivity = false;
    } else if (tabName === 'releated') {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.ngClassActivity = '';
      this.vReleated = true;
      this.vDetails = false;
      this.vActivity = false;
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = '';
      this.ngClassActivity = 'active';
      this.vActivity = true;
      this.vDetails = false;
      this.vReleated = false;
    }
    return false;
  }


  onClickEdit() {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_deliverychallan_HEADING;
    this.createNewdeliverychallan.show();
    this.spinner.hide();
  }

  cancelData() {
    this.createNewdeliverychallan.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_deliverychallan_HEADING;
    this.newdeliverychallan = new deliverychallan();
  }


  ondeliverychallanCreated($event: deliverychallan) {
    // this.product = $event;
  }
  // previousVal;
  // previousValCount=1;
  setBreadcrumb(i: number) {
    // this.classFlags = ['complete', 'complete', 'current', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending'];
    this.classFlagindex = i;
    // this.previousVal=this.classFlags[classFlagindex];
    this.classFlags[(this.classFlags.indexOf('current'))] = "currentOld";;
    this.classFlags[i] = "current";
    // previousValCount++;
  }
  countBef = 0;
  countAft = 0;
  changeBreadcrumb() {
    this.classFlags = ['pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending'];
    this.classFlagsBefore = this.classFlags.slice(0, this.classFlagindex);
    this.classFlagsAfter = this.classFlags.slice(this.classFlagindex + 1);

    this.classFlagsBefore.forEach(x => {
      this.classFlags.splice(this.countBef, this.classFlagindex, 'complete');
      this.countBef++;
    });
    this.countAft = this.countBef;
    this.classFlags.splice(this.classFlagindex, 1, 'current');
    // this.classFlags[this.classFlagindex] = 'current';
    this.classFlagsAfter.forEach(x => {
      this.classFlags.splice(this.countAft + 1, this.classFlags.length, 'pending');
      this.countAft++;
    });
    this.countBef = 0;
    this.countAft = 0;
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      // this.onCancle.emit('cancled');
    }
  }
}
