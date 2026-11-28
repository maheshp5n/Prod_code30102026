import { ApprovalRequest, Page } from './../../services/model';
import { OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ApprovalResponse, ApprovalWorkflowRequest } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ApprovalService } from '../../services/approval.services';
import { Loginservices } from '../../services/login.services';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-approval',
  templateUrl: './approval.component.html',
  styleUrls: ['./approval.component.scss']
})
export class ApprovalComponent implements OnInit {
  public message: string = '';
  @ViewChild('createApprovalModel') createApprovalModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  approvalRequest: ApprovalWorkflowRequest = new ApprovalWorkflowRequest();
  isApprovalOpen: boolean;
  approvalList: Page<ApprovalResponse> = new Page<ApprovalResponse>();
  searchitem: string = '';
  recentlyView: boolean = false;
  ngClassAll: string;
  ngClassMyService: string;
  related: boolean = false;
  all: boolean = true;
  ngClassRelated: string = 'fa fa-check';
  approvalResponse = new ApprovalResponse();
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
  CREATE_Approval_HEADING: string = 'New Approval';
  EDIT_Approval_HEADING: string = 'Edit Approval';
  i: number;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 10;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  popupNgClass: string = 'alert alert-danger center center-block';
  iseditProductOpen: boolean;
  loginEmailId: string;
  vendorCode: string;
  _cFlag: string;
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
        name: 'Object',
        sortColumnName: 'objectName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Object Class',
        sortColumnName: 'objectClassName',
        icon: ''
      },
      {
        id: 3,
        name: 'Object Id Field',
        sortColumnName: 'objectIdFiledName',
        icon: ''
      },
      {
        id: 4,
        name: 'Approval Stages',
        sortColumnName: 'approvalStages',
        icon: ''
      },
      {
        id: 5,
        name: 'Description',
        sortColumnName: 'description',
        icon: ''
      },
      {
        id: 6,
        name: 'Active',
        sortColumnName: 'active',
        icon: ''
      },
      {
        id: 7,
        name: 'Active Entry Criteria',
        sortColumnName: 'entryCreteria',
        icon: ''
      },
      {
        id: 8,
        name: 'Initial Submitters',
        sortColumnName: 'initialSubmitters',
        icon: ''
      },
      {
        id: 9,
        name: 'Created Date',
        sortColumnName: 'createdDate',
        icon: ''
      },
      {
        id: 10,
        name: 'Created By',
        sortColumnName: 'createdBy.firstName',
        icon: ''
      },
      {
        id: 11,
        name: 'Modified By',
        sortColumnName: 'modifiedBy.firstName',
        icon: ''
      },
      {
        id: 12,
        name: 'Business Domain',
        sortColumnName: 'businessDomain.businessDomainName',
        icon: ''
      },

     
    ];
  
    //done

  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private _approvalService: ApprovalService, private loginService: Loginservices,
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

  getApproval() {
    this.spinner.show();
    this.approvalList.content=[];
    this._approvalService.getApproval(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.approvalList = data
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
        this.getApproval();
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
        this.getApproval();
      } else {
        tbl.icon = '';
      }
    });
  }

  showCreateApprovalView() {
    this.approvalRequest = new ApprovalWorkflowRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_Approval_HEADING;
    this.createApprovalModel.show();
  }

  onClickEdit(approval) {
    //     this.spinner.show();
    //     this._approvalService.getApprovalById(approval.approvalId).subscribe(data => {
    //       this.approvalRequest.approvalId = data.approvalId;
    //       this.approvalRequest.name = data.name;
    //       this.approvalRequest.userEmailId = data.userEmailId;
    //       this.approvalRequest.locationId = data.locationId;
    //       this.approvalRequest.recourceTypeId = data.resourceTypeId;
    //       this.approvalRequest.contactId = data.contactId;
    //       this.approvalRequest.active = data.active;
    //       this.approvalRequest.active = data.active;
    //       this.approvalRequest.locationName = data.locationName;

    //       this.approvalRequest.createdByFirstName = data.createdByFirstName;
    //       this.approvalRequest.createdByLastName = data.createdByLastName;
    //       this.approvalRequest.createdDate = data.createdDate;
    //       this.approvalRequest.modifiedByFirstName = data.modifiedByFirstName;
    //       this.approvalRequest.modifiedByLastName = data.modifiedByLastName;
    //       this.approvalRequest.modifiedDate = data.modifiedDate;
    //       this.approvalResponse = data;
    //       if (this.approvalRequest.locationName !== null || this.approvalRequest.locationName !== '') {
    //         this.approvalRequest.isLocationSelected = 'fa fa-times';
    //       } else {
    //         this.approvalRequest.isLocationSelected = 'fa fa-search';
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
    //     this.heading = this.EDIT_Approval_HEADING;
    //     this.createApprovalModel.show();
  }

  cancelData() {
    this.isEditMode = false;
    this.heading = this.CREATE_Approval_HEADING;
    this.createApprovalModel.hide();
    this.approvalRequest = new ApprovalWorkflowRequest();
  }



  onApprovalCreated($event: ApprovalResponse) {
    if (!this.isEditMode) {
      this.approvalList.content.unshift($event);
    } else {
      let index = 0;
      this.approvalList.content.forEach(element => {
        if (element.id === $event.id) {
          this.approvalList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  // onApprovalCreated($event: ApprovalResponse) {

  // }



  popupClose() {
    this.popupSuccessModel.hide();
  }
   id:number;
  onClickEditApproval(id) {
    this.spinner.show();
    this._approvalService.getApprovalById(id).subscribe(data => {
    this.approvalRequest.active=data.active;
    this.approvalRequest.approvalStages=data.approvalStages;
    this.approvalRequest.businessDomainId=data.businessDomainId;
    this.approvalRequest.descriptions=data.description;
    this.approvalRequest.entryCriteria=data.entryCreteria;
  this.id=data.id;
    this.approvalRequest.objectClass=data.objectClassName;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
    });
    this.isEditMode = true;
    this.heading = 'Edit Approval';
    this.createApprovalModel.show();
  }

  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('approval').subscribe(data => {
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

      this.getApproval();
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
}
