import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { PdfServiceService } from '../../../services/pdf-service.service';
import { BtCustomerInventoryService } from '../../bat_services/bt-customer-inventory.service';
import * as fileSaver from 'file-saver';
import { FileUploader } from 'ng2-file-upload';

@Component({
  selector: 'app-bat-customer-inventory-in-detail',
  templateUrl: './bat-customer-inventory-in-detail.component.html',
  styleUrls: ['./bat-customer-inventory-in-detail.component.css']
})
export class BatCustomerInventoryInDetailComponent implements OnInit {
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
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  btCustomerInventoryId;
  btCusotmerInventoryInDetail;
  openCustomerInventoryIn = false;
  message: String;
  openGRN = { editGRN: false }
  displayButtons = { info: { visibal: true, icon: 'fa fa-angle-down' }, sysInfo: { visibal: true, icon: 'fa fa-angle-down' } };

  @ViewChild('editCustomerInventoryIn') editCustomerInventoryIn: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup: ModalDirective;
  @ViewChild('editGRN') editGRN: ModalDirective;
  grnDetail;
  constructor(private _spiner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _btCustomerInventoryService: BtCustomerInventoryService, private _global: GlobalserviceService,
    private loginservice: Loginservices) {
    this._activatedRoute.params.subscribe(param => {
      this.btCustomerInventoryId = param['id'];
    })
  }

  ngOnInit(): void {
    this.getBtCustomerInventoryDetailById(this.btCustomerInventoryId);
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }


  subTabChange(tabname) {
    if (tabname === 'details') {
      this.navigationMenue.details.ngclass = 'active';
      this.navigationMenue.releted.ngclass = '';
      this.navigationMenue.details.view = true;
      this.navigationMenue.releted.view = false;
      this.vDetails = true;
      this.getBtCustomerInventoryDetailById(this.btCustomerInventoryId);
    } else {
      this.navigationMenue.details.ngclass = '';
      this.navigationMenue.releted.ngclass = 'active';
      this.navigationMenue.details.view = false;
      this.navigationMenue.releted.view = true;
      this.vDetails = false;

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
    }
  }

  getBtCustomerInventoryDetailById(id) {
    this._spiner.show();
    this._btCustomerInventoryService.getBtCustomerInventoryById(id).subscribe(data => {
      this._spiner.hide();
      this.btCusotmerInventoryInDetail = data;
      if(this.btCusotmerInventoryInDetail.repairApproved){
        this.btCusotmerInventoryInDetail.repairApproved = 'Yes';
      }
      else{       
        this.btCusotmerInventoryInDetail.repairApproved = 'No';
      }

      this.grnDetail = this.btCusotmerInventoryInDetail.customerInventoryInId;
    }, err => {
      this._spiner.hide();
    })
  }

  cancelInventoryIn() {
    this.editCustomerInventoryIn.hide();
  }

  onSaveCustomerInventoryIn() {
    this.editCustomerInventoryIn.hide();
    this._global.alert({ type: '', message: 'Updated Successfully' })
  }

  createPDF() {
    this._spiner.show();
    this._btCustomerInventoryService.createPDFForBrc(this.btCusotmerInventoryInDetail.customerInventoryInId).subscribe(data => {
      const file = new Blob([data], { type: 'application/pdf' });
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(file);
      } else {
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      }
      this._spiner.hide();
      this.message = 'Created successfully';
      this.confirmationPopup.hide();
      this._global.alert({ type: '', message: this.message });
    }, err => {
      this._spiner.hide();
      this.confirmationPopup.hide();
      this.message = err.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  getEditGRN(obj){
    this.editGRN.show();
    this.grnDetail = obj;
  }
  onSaveGrnDetail() {
    this._global.alert({ type: '', message: 'Successfully Updated' });
    this.editGRN.hide();
    this.getBtCustomerInventoryDetailById(this.btCustomerInventoryId);
  }
  CancelGrn() {
    this.editGRN.hide();

  }
}
