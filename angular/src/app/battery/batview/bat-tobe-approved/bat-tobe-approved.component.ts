import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ToBeApproved, Page, ApprovalRequest } from '../../../services/model';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';

@Component({
  selector: 'app-bat-tobe-approved',
  templateUrl: './bat-tobe-approved.component.html',
  styleUrls: ['./bat-tobe-approved.component.scss']
})
export class BatTobeApprovedComponent implements OnInit {

 // chng
  toBeApprovedSelected: ToBeApproved = new ToBeApproved();
  approvalRequest: ApprovalRequest = new ApprovalRequest();

  today = new Date();

  pageoptions = {
    page: 0,
    size: 20,
    sort: 'id',
    sortby: 'asc'
  };

  // global checkbox
  globalcheckbox = false;
  globalcheckboxchange() {
    this.pendingdata.content.forEach(d => {
      d.selected = this.globalcheckbox;
    });
  }

  aprdata;
  advanceSearch = {
    request_number: '',
    type_name: '',
    date_from: '',
    date_to: '',
    action: ''
  };

  headers = [
    {
      id: 1,
      name: 'Request Number',
      sort: 'object_manual_id',
      sortby: ''
    },
    {
      id: 2,
      name: 'Type',
      sort: 'object_name',
      sortby: ''
    },
    {
      id: 3,
      name: 'Assigned Date',
      sort: 'assigned_date',
    },
    {
      id: 4,
      name: 'Initiator',
      sort: 'initiator_email_id',
      sortby: ''
    },
    {
      id: 5,
      name: 'Action',
      sort: 'status',
      sortby: ''
    }
  ];

  // multiapproval
  @ViewChild('MULTIPLEAPPROVAL') MULTIPLEAPPROVAL: ModalDirective;
  @ViewChild('approvalModule') approvalModule: ModalDirective;

  pendingdata: Page<any> = new Page<any>();

  constructor(
    private appr: BtTobeApprovedService,
    private _global: GlobalserviceService,
    private _activatedRoute: ActivatedRoute,
    private datePipe: DatePipe,
    private _ngxspinner: NgxSpinnerService
  ) {
    this._activatedRoute.queryParams.subscribe(param => {
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.pageoptions.sort = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.pageoptions.sortby = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.pageoptions.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.pageoptions.size = Number(param['size']);
      }
      this.getPendingPage();
    });
  }

  ngOnInit() {
    this.aprdata.openwindow = false;
    this.getPendingPage();
  }

  getPendingPage() {
    this.appr.getPendingPage(`page=${this.pageoptions.page}&size=${this.pageoptions.size}&sort:${this.pageoptions.sort},${this.pageoptions.sortby}`)
      .subscribe(data => {
        this.pendingdata = data;
      }, error => {
        // handle error
      });
  }

  changeOrder(id) {
    const letn = this.headers.length;
    for (let i = 0; i < letn; i++) {
      if (this.headers[i].id === id) {
        this.headers[i].sortby = this.headers[i].sortby === 'asc' ? 'desc' : 'asc';
        this.pageoptions.sort = this.headers[i].sort;
        this.pageoptions.sortby = this.headers[i].sortby;
        this.getPendingPage();
      } else {
        this.headers[i].sortby = '';
      }
    }
  }

  openApprovalWindow() {
    this.aprdata.openwindow = true;
    this.approvalModule.show();
  }
  

  mousegya(item) {
    this.aprdata = item;
  }

  onApprovalDone() {
    this.approvalModule.hide();
    this.getPendingPage();
  }

  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber != null && pagenumber !== '') {
      if (pagenumber > this.pendingdata.totalPages) {
        this._global.alert({ type: 'alert', message: 'Page Count Should Be Less Than Total Page' });
      } else {
        this.pageoptions.page = pagenumber - 1;
        this.pendingdata.number = this.pagenumber - 1;
        this.pendingdata.content = [];
        this.getPendingPage();
      }
    }
  }

  getSearchResult() {
    if (
      this.advanceSearch.request_number === '' &&
      this.advanceSearch.type_name === '' &&
      this.advanceSearch.date_from === '' &&
      this.advanceSearch.date_to === '' &&
      this.advanceSearch.action === ''
    ) {
      this._global.alert({ type: 'alert', message: 'Please Enter valid Key for Search' });
    } else if (this.advanceSearch.date_from === '' && this.advanceSearch.date_to !== '') {
      this._global.alert({ type: 'alert', message: 'Please Enter Date From for Search' });
    } else if (this.advanceSearch.date_from !== '' && this.advanceSearch.date_to === '') {
      this._global.alert({ type: 'alert', message: 'Please Enter Date To for Search' });
    } else {
      if (this.advanceSearch.date_from !== '') {
        this.advanceSearch.date_from = this.datePipe.transform(this.advanceSearch.date_from, 'yyyy-MM-dd');
      }
      if (this.advanceSearch.date_to !== '') {
        this.advanceSearch.date_to = this.datePipe.transform(this.advanceSearch.date_to, 'yyyy-MM-dd');
      }
      this._ngxspinner.show();
      this.appr.getAdvanceSearchResult(this.advanceSearch).subscribe(data => {
        this._ngxspinner.hide();
        this.pendingdata.content = data;
        this.pageoptions.page = 0;
        this.pendingdata.first = true;
        this.pendingdata.last = true;
        this.pendingdata.numberOfElements = data.length;
        this.pendingdata.totalElements = data.length;
        this.pendingdata.totalPages = 0;
      }, error => {
        this._ngxspinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
  }

  resetSearchResult() {
    this.getPendingPage();
    this.advanceSearch.request_number = '';
    this.advanceSearch.type_name = '';
    this.advanceSearch.date_from = '';
    this.advanceSearch.date_to = '';
    this.advanceSearch.action = '';
  }

 

//   doMultipleApproval() {
//     const appitems = [];
//     this.pendingdata.content.forEach(d => {
//       if (d.selected) {
//         const req: ApprovalRequest = new ApprovalRequest();
//         req.action = this.approvalRequest.action;
//         req.approvalId = d.id;
//         req.remark = this.approvalRequest.remark;
//         appitems.push(req);
//       }
//     });

//     // Handle validations here
//   }

//   cancelApproval() {
//     this.toBeApprovedSelected = new ToBeApproved();
//     this.toBeApprovedSelected.action = '';
//     this.approvalRequest = new ApprovalRequest();
//   }
// }
  updateMultipleAction() {
    const appitems = [];
    
   
   const selectedItems = this.pendingdata.content.filter(item => item.selected);

   if (selectedItems.length === 0) {
    this._global.alert({ type: 'alert', message: 'Please select at least one item for approval.' });
    return;
   }

   if (!this.approvalRequest.action || this.approvalRequest.action === 'select') {
    this._global.alert({ type: 'alert', message: 'Please select an action (Approve or Reject).' });
    return;
   }

   if (!this.approvalRequest.remark || this.approvalRequest.remark.trim() === '') {
    this._global.alert({ type: 'alert', message: 'Please enter a remark.' });
    return;
   }

   //change
   const approvalRequests = selectedItems.map(item => ({
    action: this.approvalRequest.action,
    id: item.id,
    remarks: this.approvalRequest.remark
   }));
  

  this._ngxspinner.show();
  this.appr.updateMultipleAction(approvalRequests).subscribe(
    response => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'success', message: 'Selected items have been processed successfully.' });
      this.MULTIPLEAPPROVAL.hide();
      this.getPendingPage();
    },
    error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'error', message: 'Error occurred while processing approvals. Please try again.' });
    }
   );
}

cancelApproval() {
  this.globalcheckbox = false;
  this.pendingdata.content.forEach(item => (item.selected = false));
  this.approvalRequest = new ApprovalRequest();
};



}