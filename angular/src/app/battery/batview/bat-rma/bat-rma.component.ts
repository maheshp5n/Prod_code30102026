import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { Page, SSAResponse } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { ssaService } from '../../../services/ssa.services';
import { BtRmaService } from '../../bat_services/bt-rma.service';
import { RMAResponse } from '../../bt_model';
import * as fileSaver from 'file-saver';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-bat-rma',
  templateUrl: './bat-rma.component.html',
  styleUrls: ['./bat-rma.component.css']
})
export class BatRmaComponent implements OnInit {


  @ViewChild('choosenFiles') choosenFiles: ModalDirective;

  recentlyView: boolean = true;
  searchitem: string = '';
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  recently: boolean = true;
  ngClassRelated: string;
  related: boolean = true;
  datainput: boolean = false
  ngClassTable: string = 'fa fa-check';
  values: string;
  SSATableList: Page<SSAResponse> = new Page<SSAResponse>();
  RMAResp: Page<RMAResponse> = new Page<RMAResponse>();
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create SSA ';
  EDIT_ACCOUNT_HEADING: string = 'Edit SSA '
  order: string = 'ssaName'; // first column variable
  reverse: boolean = false;
  page: number = 0;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  showFilter: boolean = false
  showHide: boolean = false
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 3;
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  i: any
  message: string = "";
  ssaId: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  ssares: SSAResponse = new SSAResponse();
  _cFlag: string;

  fileList: any[] = [];
  filyType: string;
  formData = new FormData();
  fileToUpload: File;
  getFile: any;
  uploadForm: FormGroup;


  //filter
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Recently Viewed',
    clickEventParam: 'recentvisit',
    class: 'dropdown-item',
    pined: false
  },


  ];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: ' RMA Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: ' RMA Status',
      sortColumnName: 'rma_status',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Created By',
      sortColumnName: 'created_by_email_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Case Number',
      sortColumnName: 'btcase.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Service Appointment Number',
      sortColumnName: ' ser.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
   
    {
      id: 6,
      name: 'Asset Serial No',
      sortColumnName: 'ast.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Part Code',
      sortColumnName: 'btP.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Site Id',
      sortColumnName: 'st.manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
   
    {
      id: 9,
      name: 'Circle',
      sortColumnName: 'st.circle_circle_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },
    


  ];

  //done
  constructor(private pagefilterservice: PagefilterService, private formBuilder: FormBuilder, private _gloabl: GlobalserviceService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private rmasservice: BtRmaService, private loginservice: Loginservices, private ssaservice: ssaService) {

    this._activatedRoute.params.subscribe(pram => {
      this.ssaId = pram['siteId'];

    });
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.size = Number(param['size']);
      }
      this.getFilterList();
    });
    // this.SSATableList = orderPipe.transform(this.SSATableList, this.order);
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.RMAResp.content = [];
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    //  this.getRmaDeatils();
    // this.getSSA(this.sortColumnName, this.sortOrder)
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_rma').subscribe(data => {
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.dropdownSelected.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.dropdownSelected.name = d.filterName;
            this.dropdownSelected.filtreparam = d.filterParam;
            this.dropdownSelected.sqId = d.userSummaryPageFilterId;
            this.pindtselection = true;
            this.dropdownSelected.page = 0;
          }

          else {
            if (d.sqId == this.dropdownSelected.sqId) {
              this.pindtselection = true;
            }
          }

        }
        this.dropdownsList.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }

      this.getRmaDeatils();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        console.log(this.dropdownSelected.filtreparam);
        this.dropdownSelected.page = 0;
        this.getRmaDeatils();

        if (item.pined) {
          this.pindtselection = true;
        } else {
          this.pindtselection = false;
        }
      } else {
        item.class = 'dropdown-item';
      }
    });
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
        this.dropdownSelected.page = 0;
        this.getRmaDeatils();
      } else {
        tbl.icon = '';
      }
    });
  }
  pinFilter() {
    this.spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this.spinner.hide();
    }, error => this.spinner.hide());
  }
  getSSA() {
    this.spinner.show();
    this.SSATableList.content = [];
    this.ssaservice.getSSA(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.SSATableList = data
      //  this.first=data.first;
      //  this.last=data.last;
      //  this.serialNumber = (((this.page) * data.size));
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      // this.popupSuccessModel.show();
    })
  }

  getRmaDeatils() {
    this.spinner.show();
    this.rmasservice.getRma(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      console.log(data)
      this.RMAResp = data;
      //  this.SSATableList=data
      //  this.first=data.first;
      //  this.last=data.last;
      //  this.serialNumber = (((this.page) * data.size));
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
    })

  }

  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (this.RMAResp.totalPages < pagenumber) {
      this.message = "Enter Valid Input";
      this._gloabl.alert({ type: 'alert', message: "Enter Valid input" });
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getRmaDeatils();
      // this.serialNumber = (((this.dropdownSelected.page) * this.size));
    }
  }
  
  /// for advance search
  advancesearch = {
    caseManualId: '',
    rmaStatus: '',
    rmaManualId :''
  };

  searchRMACase() {
    if (this.advancesearch.caseManualId == '' && this.advancesearch.rmaStatus == ''&& this.advancesearch.rmaManualId == '') {
      this._gloabl.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this.spinner.show();
      this.rmasservice.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.RMAResp.content = data;
        this.page = 0;
        this.RMAResp.size = data.length;
        this.RMAResp.first = true;
        this.RMAResp.last = true;
      }, error => {
        this.spinner.hide();
        this._gloabl.alert({ type: 'alert', message:error.error.message});
      });
    }
  }
  resetsearch() {
    this.advancesearch.rmaManualId = ''
    this.advancesearch.rmaStatus = ''   
    this.advancesearch.caseManualId = ''
    this.getRmaDeatils();
  }

  getRmaRejectTemplateExcel() {
    this.rmasservice.getRejectRmaTemplate().subscribe(data => {
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "RMA_Reject" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._gloabl.alert({ type: 'alert', message: error.message });
    })
  }
  getRmaStatusTemplateExcel() {
    this.rmasservice.getRmaStatusTemplate().subscribe(data => {
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "RMA_Change_Status_Template" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._gloabl.alert({ type: 'alert', message: error.message });
    })

  }

  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('file', this.fileList[i]);
    }
    if (this.filyType == "EditRMAgetFile") {
      this.rmasservice.rmaStatusBulkUpload(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];
        (<HTMLInputElement>document.getElementById('getFile')).value = null
        this.message = "Updated Successfully";
        this._gloabl.alert({ type: '', message: this.message });
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._gloabl.alert({ type: 'alert', message: error.message });
        this.getRmaDeatils();
      });
      this.formData = new FormData();
      this.getFile = "";
    }
    if (this.filyType == "RMARejectgetFile") {
      this.rmasservice.rejectBulkUploadRMA(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = [];
        (<HTMLInputElement>document.getElementById('getFile')).value = null
        this.message = "Updated Successfully";
        this._gloabl.alert({ type: '', message: this.message });
        this.getRmaDeatils();
      }, error => {
        this.spinner.hide();
        this.message = error.message;

        this._gloabl.alert({ type: 'alert', message: error.message });
      });
      this.formData = new FormData();
      this.getFile = "";
    }
  }


  //bulk upload function

  onFileSelect(event) {
    // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
    if (event != null) {

      this.getFile = event.target.files.id;
      this.filyType = event.target.id;
      // this.podgetFile=event.target.files.id;

      console.log(this.filyType);
      if (event.target.files.length > 0) {
        // this.chooscaseFiles.hide();
        this.choosenFiles.show();
        for (let i = 0; i < event.target.files.length; i++) {
          this.fileList.push(event.target.files[i]);
          this.fileToUpload = event.target.files[i];
          // console.log(this.fileToUpload);

        }
      }
    }
    else {
      console.log(event)
    }

  }

  downloadFile(data) {
    //  var blob = new Blob([<any>data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;'});
    //  var url= window.URL.createObjectURL(blob);
    //  fileSaver.saveAs(blob,data.fileName);
    //  window.open(url);
    // 
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "Report" + ".xlsx");



  }

  cancelChoosen() {
    if (this.filyType == 'getFile') {
      (<HTMLInputElement>document.getElementById('getFile')).value = null
    }
    if (this.filyType == 'select') {
      (<HTMLInputElement>document.getElementById('select')).value = null
    }
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = "";
  }

}



