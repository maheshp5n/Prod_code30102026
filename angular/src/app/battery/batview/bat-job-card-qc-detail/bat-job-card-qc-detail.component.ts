import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatJObCardQcService } from '../../bat_services/bat-job-card-qc.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-bat-job-card-qc-detail',
  templateUrl: './bat-job-card-qc-detail.component.html',
  styleUrls: ['./bat-job-card-qc-detail.component.scss']
})
export class BatJobCardQcDetailComponent implements OnInit {
  btJobCardQCDetail;
  qcId: any;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  uploadForm: UntypedFormGroup;
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
  openModel = {
    editJobCardQc: false
  };
  fileResponse;
  fileFound : boolean = false;
  isQcDone : boolean = true;
  @ViewChild('editJobCardQCModel') editJobCardQCModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  displayButtons = { info: { visibal: true, icon: 'fa fa-angle-down' }, sysInfo: { visibal: true, icon: 'fa fa-angle-down' },qcEditDetails:{visibal: true, icon: 'fa fa-angle-down'} };
  constructor(private _btJobCardQcSerice: BatJObCardQcService, private _spiner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _global: GlobalserviceService, private _sanitizer: DomSanitizer) {
    this._activatedRoute.params.subscribe(param => {
      this.qcId = param['id'];
    })
  }

  ngOnInit(): void {
    this.getBtJobCardById(this.qcId);
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
      this.getBtJobCardById(this.qcId);
    } else {
      this.navigationMenue.details.ngclass = '';
      this.navigationMenue.releted.ngclass = 'active';
      this.navigationMenue.details.view = false;
      this.navigationMenue.releted.view = true;
      this.vDetails = false;
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      // this.getUploadFile();
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
      case 'qcEditDetails': {
        this.displayButtons.qcEditDetails.visibal = !this.displayButtons.qcEditDetails.visibal;
        if (this.displayButtons.qcEditDetails.visibal) {
          this.displayButtons.qcEditDetails.icon = 'fa fa-angle-down';
        }
        else {
          this.displayButtons.qcEditDetails.icon = 'fa fa-angle-up';
        }
        break;
      }
    }
  }
  getBtJobCardById(qcid) {
    this._spiner.show();
    this._btJobCardQcSerice.getBtJobCardQcByQcId(qcid).subscribe(data => {
      this._spiner.hide();
      this.btJobCardQCDetail = data;
      if(this.btJobCardQCDetail.qcFinalApproval=='Yes'||this.btJobCardQCDetail.qcFinalApproval=='No'){
        this.isQcDone = false;
      }
       this.getUploadFile();
    }, err => {
      this._spiner.hide();
    })
  }
  onEditJobCardQc() {
    this._global.alert({ type: '', message: 'Job Card QC Updated Successfully!' });
    this.editJobCardQCModel.hide();
    this.getBtJobCardById(this.qcId);
    this.isQcDone = false;
  }
  // method to upload file
  fileList = [];
  onFileSelect($event) {
    if ($event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < $event.target.files.length; i++) {
        this.fileList.push($event.target.files[i]);
      }
    }
  }
  uploadFile() {
    let formData = new FormData();
    formData.append('file', this.fileList[0]);
    this._spiner.show();
    this._btJobCardQcSerice.postUploadFile(formData, this.btJobCardQCDetail.qcId).subscribe(data => {
      this._spiner.hide();
      this.choosenFiles.hide();
      this._global.alert({ type: '', message: 'Successfully Uploaded!' });
      this.getBtJobCardById(this.qcId);
      this.fileList = [];
    }, err => {
      this._spiner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    })
  }
  fileId : number=undefined;
  getUploadFile() {
    this._spiner.show();
    this._btJobCardQcSerice.getUploadFile(this.btJobCardQCDetail.qcId).subscribe(data => {
      this._spiner.hide();
      this.fileResponse = data;
      this.fileId = data.id; 
      if(this.fileId!=undefined){
        this.fileFound = true;
      }
      else{
       this.fileFound = false;
      }
    }, err => {
      this._spiner.hide();
    });
  }

  cancelChoosen() {
    this.fileList = [];
    this.choosenFiles.hide();
  }

}
