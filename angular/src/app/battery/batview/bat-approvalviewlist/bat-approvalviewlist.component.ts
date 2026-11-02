import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Page } from '../../../services/model';
import { ObjectServices } from '../../../services/objecct.service';
import { FieldValidation } from '../../../services/validation.services';
import { BtApprovalService } from '../../bat_services/btapprovalservice';

@Component({
  selector: 'app-bat-approvalviewlist',
  templateUrl: './bat-approvalviewlist.component.html',
  styleUrls: ['./bat-approvalviewlist.component.scss']
})
export class BatApprovalviewlistComponent implements OnInit {

  @ViewChild('#newapprovalflow') newapprovalflow: ModalDirective;

  openWindow = {
    newapprovalflow: false
  }
  activepagedetails = {
    page: 0,
    size: 20,
    sort: 'id',
    sortby: 'ASC'
  }
  headerdetails = [{
    id: 1,
    name: 'Name',
    sort: 'object_name',
    currentsortby: ''
  }, {
    id: 2,
    name: 'Applied Object Name',
    sort: 'object_class_name',
    currentsortby: ''
  }, {
    id: 3,
    name: 'Initiator',
    sort: 'initial_submitters',
    currentsortby: ''
  }, {
    id: 4,
    name: 'Steps',
    sort: 'total_stapes',
    currentsortby: ''
  }, {
    id: 5,
    name: 'Description',
    sort: 'description',
    currentsortby: ''
  }, {
    id: 6,
    name: 'Active',
    sort: 'active',
    currentsortby: ''
  }];
  objectsDetails = [];

  approvalpage: Page<any> = new Page();
  pagenumber: number;
  constructor(private approvalservice: BtApprovalService, private activatedRotue: ActivatedRoute, private objectService: ObjectServices, private ngxspinner: NgxSpinnerService, private global: GlobalserviceService) {
    this.activatedRotue.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.activepagedetails.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.activepagedetails.size = Number(param['size']);
      }
      if (param['sort'] != undefined && param['sort'] != null && param['sort'] != '') {
        this.activepagedetails.sort = param['sort'];
      }
      if (param['sortby'] != undefined && param['sortby'] != null && param['sortby'] != '') {
        this.activepagedetails.sortby = param['sortby'];
      }
      this.getAllApproval();
    });
  }

  ngOnInit() {
    // this.headerdetails=[];
    this.approvalpage.content = [];
    this.getObjects();

  }


  onNewApprovalSave() {
    this.newapprovalflow.hide();
    this.getAllApproval();
  }

  getAllApproval() {
    this.ngxspinner.show();
    console.log(this.activepagedetails.page)
    this.approvalservice.getApprovalPage(`page=${this.activepagedetails.page}&size=${this.activepagedetails.size}&sort=${this.activepagedetails.sort},${this.activepagedetails.sortby}`).subscribe(data => {
      this.ngxspinner.hide();
      this.approvalpage = data;
      console.log(this.approvalpage.first);
    }, error => {
      this.ngxspinner.hide();
    });
  }

  getObjects() {
    this.ngxspinner.show();
    this.objectService.getObjectForList().subscribe(data => {
      this.ngxspinner.hide();
      this.objectsDetails = data
    }, error => {
      this.ngxspinner.hide();
    });
  }

  getObjectLabelName(objectname): string {
    const lent = this.objectsDetails.length;
    for (let i = 0; i < lent; i++) {
      if (this.objectsDetails[i].object_name === objectname) {
        return this.objectsDetails[i].object_lable_name;
      }
    }
  }

  changeOrderBy(id) {
    const letn = this.headerdetails.length;
    for (let i = 0; i < letn; i++) {
      if (this.headerdetails[i].id == id) {
        if (this.headerdetails[i].currentsortby == 'asc') {
          this.headerdetails[i].currentsortby = 'desc';
        }
        else if (this.headerdetails[i].currentsortby == 'desc') {
          this.headerdetails[i].currentsortby = 'asc';
        }
        else {
          this.headerdetails[i].currentsortby = 'asc';
        }

        this.activepagedetails.sort = this.headerdetails[i].sort;
        this.activepagedetails.sortby = this.headerdetails[i].currentsortby;
        this.getAllApproval();
      }
      else {
        this.headerdetails[i].currentsortby = '';
      }
    }
  }
  enalbleDisableApprovalFlow(item) {
    this.ngxspinner.show();
    this.approvalservice.enableDisableApprovalFlow(item.id, !item.active).subscribe(data => {
      this.getAllApproval();
    }, error => {
      this.ngxspinner.hide();
      this.global.alert({ type: 'alert', message: error.error.message });
    });
  }
  jumpPage(pagenumber) {
    if (pagenumber != null) {
      if (this.approvalpage.totalPages < pagenumber || pagenumber == 0) {
        this.global.alert({ type: 'alert', message: "Page Count Should Be Less The Total Page Count" });
      }
      else if (FieldValidation.isLetter(pagenumber)) {
        this.global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
        pagenumber = null;
      }
      else {

        this.activepagedetails.page = pagenumber - 1;
        this.getAllApproval();
      }

    }
    else {
      this.global.alert({ type: 'alert', message: 'Please Input Valid Number' });
    }
  }

  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
  advancesearch = { 
    name: '',
    objectName:'',
    description : '',
    initiator :''

  };
  searchAdvance() {
    if (this.advancesearch.name == '' &&this.advancesearch.objectName=='' &&this.advancesearch.description == '' &&this.advancesearch.initiator == '' ) {
      this.global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this.ngxspinner.show();
      this.approvalservice.getSearchResult(this.advancesearch).subscribe(data => {
        this.ngxspinner.hide();
        this.approvalpage.content = data;
        this.approvalpage.size = data.length;
        this.approvalpage.first = true;
        this.approvalpage.last = true;
      }, error => {
        this.ngxspinner.hide();
        this.global.alert({ type: 'alert', message:error.error.message});
      });
    }
  }
  resetsearch() {
    this.advancesearch.name = '';
    this.advancesearch.objectName = '';
    this.advancesearch.description = '';
    this.advancesearch.initiator = '';
    this.getAllApproval();
  }

}
