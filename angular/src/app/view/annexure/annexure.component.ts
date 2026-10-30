import { AnnexureRequest, Page } from './../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AnnexureResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { AnnexureService } from '../../services/annexure.services';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-annexure',
  templateUrl: './annexure.component.html',
  styleUrls: ['./annexure.component.scss']
})
export class AnnexureComponent implements OnInit {
  public message: string = '';
  @ViewChild('createAnnexureModel', { static: false }) createAnnexureModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  isAnnexureOpen: boolean;
  annexureList: Page<AnnexureResponse> = new Page<AnnexureResponse>();
  searchitem: string = '';
  recentlyView: boolean = true;
  ngClassAll: string;
  ngClassMyService: string;
  related: boolean = true;
  all: boolean = true;
  ngClassRelated: string = 'fa fa-check';
  annexureResponse = new AnnexureResponse();
  public page: number = 0;
  private pages: Array<number>;
  private totalPages;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_Annexure_HEADING: string = 'New Annexure';
  EDIT_Annexure_HEADING: string = 'Edit Annexure';
  i: number;
  annexureRequest: AnnexureRequest = new AnnexureRequest();
  popupNgClass: string = 'alert alert-danger center center-block';
  iseditProductOpen: boolean;
  loginEmailId: string;
  vendorCode: string;
  _cFlag: string;
  pindtselection = false;

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
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Name',
      sortColumnName: 'manualId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Vendor Name',
      sortColumnName: 'vendor.firstName',
      icon: ''
    },
    {
      id: 3,
      name: 'Vendor Code',
      sortColumnName: 'vendor.employeeCode',
      icon: ''
    },
    {
      id: 4,
      name: 'Service Center',
      sortColumnName: 'vendor.legalEntity.legalEntityName',
      icon: ''
    },
    {
      id: 5,
      name: 'PO Number',
      sortColumnName: 'poNumber',
      icon: ''
    },
    {
      id: 6,
      name: 'PO Date',
      sortColumnName: 'poDate',
      icon: ''
    },
    {
      id: 7,
      name: 'Approval Status',
      sortColumnName: 'approvalStatus.name',
      icon: ''
    },
    {
      id: 8,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: ''
    },
   
  ];

  //done
  constructor(private pagefilterservie:PagefilterService,private spinner: NgxSpinnerService, private _annexureService: AnnexureService, private loginService: Loginservices,
    private _activatedRoute: ActivatedRoute) {
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
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.searchitem = '';
  }

  getAnnexure() {
    this.spinner.show();
    this.annexureList.content=[];
    this._annexureService.getAnnexure(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.annexureList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getAnnexure();
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
        this.getAnnexure();
      } else {
        tbl.icon = '';
      }
    });
  }

  createAnnexureModelhide() {
    this.annexureResponse = new AnnexureResponse();
    this.onCancle.emit('cancled');
    this.createAnnexureModel.hide();
  }

  showCreateAnnexureView() {
    this.annexureRequest = new AnnexureRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_Annexure_HEADING;
    this.createAnnexureModel.show();
  }

  onClickEdit(annexure) {
    //     this.spinner.show();
    //     this._annexureService.getAnnexureById(annexure.annexureId).subscribe(data => {
    //       this.annexureRequest.annexureId = data.annexureId;
    //       this.annexureRequest.name = data.name;
    //       this.annexureRequest.userEmailId = data.userEmailId;
    //       this.annexureRequest.locationId = data.locationId;
    //       this.annexureRequest.recourceTypeId = data.resourceTypeId;
    //       this.annexureRequest.contactId = data.contactId;
    //       this.annexureRequest.active = data.active;
    //       this.annexureRequest.active = data.active;
    //       this.annexureRequest.locationName = data.locationName;

    //       this.annexureRequest.createdByFirstName = data.createdByFirstName;
    //       this.annexureRequest.createdByLastName = data.createdByLastName;
    //       this.annexureRequest.createdDate = data.createdDate;
    //       this.annexureRequest.modifiedByFirstName = data.modifiedByFirstName;
    //       this.annexureRequest.modifiedByLastName = data.modifiedByLastName;
    //       this.annexureRequest.modifiedDate = data.modifiedDate;
    //       this.annexureResponse = data;
    //       if (this.annexureRequest.locationName !== null || this.annexureRequest.locationName !== '') {
    //         this.annexureRequest.isLocationSelected = 'fa fa-times';
    //       } else {
    //         this.annexureRequest.isLocationSelected = 'fa fa-search';
    //       }
    //       if (data != null) {
    //         this.spinner.hide();
    //       }
    //     }, err => {
    //       this.spinner.hide();
    //       this.message = err.error.message;
    //       this.popupSuccessModel.show();
    // });

    //     this.isEditMode = true;
    //     this.heading = this.EDIT_Annexure_HEADING;
    //     this.createAnnexureModel.show();
  }

  cancelData() {
    this.isEditMode = false;
    this.heading = this.CREATE_Annexure_HEADING;
    this.createAnnexureModel.hide();
    this.annexureRequest = new AnnexureRequest();
  }

 


  // onAnnexureCreated($event: AnnexureResponse) {
  //   if (!this.isEditMode) {
  //     this.annexureList.unshift($event);
  //   } else {
  //     let index = 0;
  //     this.annexureList.forEach(element => {
  //       if (element.annexureId === $event.annexureId) {
  //         this.annexureList[index] = $event;
  //       } else {
  //         index++;
  //       }
  //     });
  //   }
  // }

  onAnnexureCreated($event: AnnexureResponse) {
      if (!this.isEditMode) {
      this.annexureList.content.unshift($event);
    } else {
      let index = 0;
      this.annexureList.content.forEach(element => {
        if (element.id === $event.id) {
          this.annexureList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }


  popupClose() {
    this.popupSuccessModel.hide();
  }

  onClickEditAnnexure(annexureResponse) {
    this.spinner.show();
    this._annexureService.getAnnexureById(annexureResponse.id).subscribe(data => {
      this.annexureRequest.id = data.id;
      this.annexureRequest.invoiceNumber = data.invoiceNumber;
      this.annexureRequest.invoiceDate = data.invoiceDate;
      this.annexureRequest.submittedDate = data.submittedDate;
      // this.annexureRequest.approvedByCommercialDate = data.approvedByCommercialDate;
      // this.annexureRequest.approvedByCircleDate = data.approvedByCircleDate;
      this.annexureRequest.isInvoiceReceived = data.isInvoiceReceived;
      this.annexureRequest.approvalStatusId = data.approvalStatusId;
      this.annexureRequest.businessDomainId = 2;
      this.annexureRequest.poNumber = data.poNumber;
      this.annexureRequest.poDate = data.poDate;
      // this.annexureRequest.createdByFirstName = data.createdByFirstName;
      // this.annexureRequest.createdByMiddleName = data.createdByMiddleName;
      // this.annexureRequest.createdByLastName = data.createdByLastName;
      // this.annexureRequest.createdDate = data.createdDate;
      // this.annexureRequest.modifiedByFirstName = data.modifiedByFirstName;
      // this.annexureRequest.modifiedByLastName = data.modifiedByLastName;
      // this.annexureRequest.modifiedDate = data.modifiedDate;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
    });
    this.isEditMode = true;
    this.heading = 'Edit Annexure';
    this.createAnnexureModel.show();
  }
 
  getFilterList() {
    this.pagefilterservie.getPagefilter('annexure').subscribe(data => {
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

      this.getAnnexure();
    });
  }

  pinFilter() {
    this.spinner.show();
    this.pagefilterservie.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
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
}
