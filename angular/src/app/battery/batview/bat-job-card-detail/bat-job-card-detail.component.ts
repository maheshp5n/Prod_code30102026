import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { BtJobCardServiceService } from '../../bat_services/bt-job-card-service.service';
import { BtJobCardResponse} from '../../bt_model';

@Component({
  selector: 'app-bat-job-card-detail',
  templateUrl: './bat-job-card-detail.component.html',
  styleUrls: ['./bat-job-card-detail.components.scss']
})
export class BatJobCardDetailComponent implements OnInit {
  navigationMenue = {
    details: {
      ngclass: '',
      view: false
    },
    releted: {
      ngclass: '',
      view: false
    }
  };
  jobCardId: any;
  openModel = {
    editJobCard: false
  };
  btJobCardDetail: BtJobCardResponse = new BtJobCardResponse();
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  btConsumedProducts: Array<any> = [];
  btItemsForQC: Array<any> = [];
  totalSparPartReplaced: number;
  fileResponse;
  heading: string;
  reAssignJobCard: BtJobCardResponse = new BtJobCardResponse();
  reAssingJobCardModelShow: boolean = false;
  loginData;
  @ViewChild('editJobCardModel') editJobCardModel: ModalDirective;
  @ViewChild('reAssingJobCardModel') reAssingJobCardModel: ModalDirective;
  displayButtons = { info: { visibal: true, icon: 'fa fa-angle-down' }, engRemarks: { visibal: true, icon: 'fa fa-angle-down' }, sysInfo: { visibal: true, icon: 'fa fa-angle-down' } };
  constructor(private _activatedRoute: ActivatedRoute, private _spinner: NgxSpinnerService, private _btJobCardService: BtJobCardServiceService,
    private _global: GlobalserviceService,private _loginService: Loginservices) {
    this._activatedRoute.params.subscribe(param => {
      this.jobCardId = param['id'];
    })
  }

  ngOnInit(): void {
    this.getBtJobCardById(this.jobCardId);
    this.getConsumedItes();
    this.loginData = this._loginService.getLoggedInUser();
  }
  subTabChange(tabname) {
    if (tabname === 'details') {
      this.navigationMenue.details.ngclass = 'active';
      this.navigationMenue.releted.ngclass = '';
      this.navigationMenue.details.view = true;
      this.navigationMenue.releted.view = false;
      this.vDetails = true;
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.getBtJobCardById(this.jobCardId);
    } else {
      this.navigationMenue.details.ngclass = '';
      this.navigationMenue.releted.ngclass = 'active';
      this.navigationMenue.details.view = false;
      this.navigationMenue.releted.view = true;
      this.vDetails = false;
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.getConsumedItes();
      this.getItemsForQc();
    }
  }
  changeButtonDisplay(bname) {
    switch (bname) {
      case 'info': {
        this.displayButtons.info.visibal = !this.displayButtons.info.visibal;
        if (this.displayButtons.info.visibal) {
          this.displayButtons.info.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.info.icon = 'fa fa-angle-up';
        }
        break;
      }
      case 'sysinfo': {
        this.displayButtons.sysInfo.visibal = !this.displayButtons.sysInfo.visibal;
        if (this.displayButtons.sysInfo.visibal) {
          this.displayButtons.sysInfo.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.sysInfo.icon = 'fa fa-angle-up';
        }
        break;
      }
      case 'engremarks': {
        this.displayButtons.engRemarks.visibal = !this.displayButtons.engRemarks.visibal;
        if (this.displayButtons.engRemarks.visibal) {
          this.displayButtons.engRemarks.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.engRemarks.icon = 'fa fa-angle-up';
        }
      }
    }
  }
  getBtJobCardById(JobCardId) {
    this._spinner.show();
    this._btJobCardService.getBtJobCardByJobCardId(JobCardId).subscribe(data => {
      this._spinner.hide();
      this.getUploadFile();
      this.btJobCardDetail = data;
      if (this.btJobCardDetail.engRemarks == '0') {
        this.btJobCardDetail.engRemarks = '';
      }
      if (this.btJobCardDetail.berRemarks == '0') {
        this.btJobCardDetail.berRemarks = '';
      }
      if (this.btJobCardDetail.jobCardRepairProductState == 1) {
        this.btJobCardDetail.currentStatusType = 'OK';
      }
      if (this.btJobCardDetail.jobCardRepairProductState == 5) {
        this.btJobCardDetail.currentStatusType = 'BER';
      }
    }, err => {
      this._spinner.hide();
    })
  }
  onEditJobCard() {
    this._global.alert({ type: '', message: 'Job Card Updated Successfully!' });
    this.editJobCardModel.hide();
    this.getBtJobCardById(this.jobCardId);
  }
  getConsumedItes() {
    this.totalSparPartReplaced = 0;
    this._spinner.show();
    this._btJobCardService.btJobCardProductConsume(this.jobCardId, 'yes').subscribe(data => {
      this._spinner.hide();
      this.btConsumedProducts = data;
      for (let i = 0; i < this.btConsumedProducts.length; i++) {
        this.totalSparPartReplaced = this.totalSparPartReplaced + this.btConsumedProducts[i].quantityConsumed;
      }
    }, err => {
      this._spinner.hide();
    })
  }
  getItemsForQc() {
    this._spinner.show();
    this._btJobCardService.btItemsForQC(this.jobCardId, 'yes').subscribe(data => {
      this._spinner.hide();
      this.btItemsForQC = data;
    }, err => {
      this._spinner.hide();
    })
  }
  fileFound: boolean = false;
  getUploadFile() {
    this._spinner.show();
    this._btJobCardService.getUploadFile(this.jobCardId).subscribe(data => {
      this._spinner.hide();
      this.btJobCardDetail.fileId = data.id;
      if (this.btJobCardDetail.fileId != undefined) {
        this.fileFound = true;
      }
      else {
        this.fileFound = false;
      }
      console.log(this.btJobCardDetail.fileId)
      this.btJobCardDetail.filename = data.file_name;
      this.btJobCardDetail.fileCreatedDate = data.created_date;
    }, err => {
      this._spinner.hide();
    })
  }
  getReAssignModel(res) {
    this.reAssignJobCard = res;
    this.heading = 'Re-Assign Job Card'
    this.reAssingJobCardModel.show();
  }

  reAssingJobCardModelHide() {
    this.reAssingJobCardModel.hide();
  }
  onReassignJobCardCreated() {
    this._global.alert({ type: '', message: 'Job Card Successfully Re-Assigned!' });
    this.reAssingJobCardModel.hide();
    this.getBtJobCardById(this.jobCardId);

  }
  cancelJobCardReAssign() {
    this.reAssingJobCardModel.hide();
  }
}

