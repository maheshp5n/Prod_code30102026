import { ObjectServices } from './../../services/objecct.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ItemsToBeApprovedService } from '../../services/itemstobeapprovedservices';
import { NgxSpinner } from 'ngx-spinner/lib/ngx-spinner.enum';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToBeApproved, Page, EmployeeDetails, UserForList, ApprovalRequest } from '../../services/model';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from '../../globalservice.service';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-itemtobeapproved',
  templateUrl: './itemtobeapproved.component.html',
  styleUrls: ['./itemtobeapproved.component.scss']
})
export class ItemtobeapprovedComponent implements OnInit {

  advanceSearch={
    request_number:'',
    type_name:'',
    date_from:'',
    date_to:'',
    action:''
  }

  pageItemsToBeApproved: Page<ToBeApproved> = new Page<ToBeApproved>();
  @ViewChild('approvalModule') approvalModule: ModalDirective;
  @ViewChild('MULTIPLEAPPROVAL') MULTIPLEAPPROVAL: ModalDirective;
  @ViewChild('alertModule') alertModule: ModalDirective;
  alerttypes = {
    type: 'danger',
    message: ''
  }
  toBeApprovedSelected: ToBeApproved = new ToBeApproved();
  approvalRequest: ApprovalRequest = new ApprovalRequest();
  users: Array<UserForList> = [];
  sortColumn: number = 4;
  columnVisibility: Array<boolean> = [false, false, false];
  columnSort: Array<boolean> = [false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  serialNumber: number;
  objectNames: Array<any> = [];
  globalcheckbox = false;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(private _tobeapprovedservice: ItemsToBeApprovedService,
    private _ngxspinner: NgxSpinnerService,private datePipe:DatePipe,
    private _userDetailsService: ServiceResourceService,
    private _objectService: ObjectServices,private _global:GlobalserviceService
  ) { }
  page: number = 0;
  sortColumnName: string = 'assignedDate'; // first column variable
  sortOrder: string = 'desc';
  ngOnInit() {
    this.getItemsToBeApproved(this.sortColumnName, this.sortOrder);
    //this.getUserList();
    this.getObjectNames();
  }

  getItemsToBeApproved(column: string, order: string) {
    this._ngxspinner.show();
    this.pageItemsToBeApproved.content=[];
    this._tobeapprovedservice.getItemsToBeApproved(this.page, column, order).subscribe(data => {
      this._ngxspinner.hide();
      this.pageItemsToBeApproved = data;
      this.serialNumber = (((this.page) * data.size));
    }, error => this._ngxspinner.hide());
  }
  onShow(approvalId) {
    this.pageItemsToBeApproved.content.forEach(d => {
      if (d.id == approvalId) {
        this.toBeApprovedSelected = d;
        if (this.toBeApprovedSelected.action == undefined || this.toBeApprovedSelected.action == null) {
          this.toBeApprovedSelected.action = ''
        }
        let a = new ApprovalRequest();
        a.approvalId = d.id;
        a.action = '';
        this.approvalRequest = a;
      }
    });
    this.approvalModule.show();
  }
  cancelApproval() {
    this.toBeApprovedSelected = new ToBeApproved();
    this.toBeApprovedSelected.action = '';
    this.approvalRequest = new ApprovalRequest();
  }
  getUserList() {
    this._userDetailsService.getUserForList().subscribe(data => {
      data.forEach(u => {
        u.fullName = u.userFirstName + ' ' + u.userLastName;
        this.users.push(u);
      });
    });
  }
  doApproveOrReject() {
    if (this.approvalRequest.approvalId == undefined || this.approvalRequest.approvalId == null || this.approvalRequest.approvalId == 0) {
      this.showAlert('Some thing went wrong,please reload the page');
      return;
    }
    if (this.approvalRequest.action == undefined || this.approvalRequest.action == null || this.approvalRequest.action == '' || this.approvalRequest.action === 'select') {
      this.showAlert('Please choose action Approve or Reject');
      return;
    }
    if (this.approvalRequest.remark != null && this.approvalRequest.remark != undefined && this.approvalRequest.remark.length > 200) {
      this.showAlert('Please write remark in less than 200 characters');
      return;
    }

    this._ngxspinner.show();
    this._tobeapprovedservice.approveRejectItems(this.approvalRequest.approvalId, this.approvalRequest).subscribe(data => {
      this._ngxspinner.hide();
      this.approvalModule.hide();
      this.getItemsToBeApproved(this.sortColumnName, this.sortOrder);
      this.showMessage('Your Action Performed Successfully');
    }, error => {
      this._ngxspinner.hide();
      this.showAlert(`Error : ${error.error.message}`);
    });
  }
  showMessage(message) {
    this.alerttypes.type = 'success';
    this.alerttypes.message = message;
    this.alertModule.show();
  }
  showAlert(message) {
    this.alerttypes.type = 'danger';
    this.alerttypes.message = message;
    this.alertModule.show();
  }
  pageChanged($event) {
    this._ngxspinner.show();
    this.page = $event.page - 1;
    this._tobeapprovedservice.getItemsToBeApproved($event.page - 1, this.sortColumnName, this.sortOrder).subscribe(data => {
      this._ngxspinner.hide();
      this.pageItemsToBeApproved = data;
      this.serialNumber = (((this.page) * data.size));
    }, error => this._ngxspinner.hide());
  }

  gotOpage(pagenumber) {
    this._ngxspinner.show();
    this.page = pagenumber;
    this._tobeapprovedservice.getItemsToBeApproved(pagenumber, this.sortColumnName, this.sortOrder).subscribe(data => {
      this._ngxspinner.hide();
      this.pageItemsToBeApproved = data;
      this.serialNumber = (((this.page) * data.size));
    }, error => this._ngxspinner.hide());
  }


  setOrder(value: string, column: number) {

    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[3] = true;
        }
        break;
    }

    this.getItemsToBeApproved(this.sortColumnName, this.sortOrder);


  }
  getObjectNames() {
    this._objectService.getObjectForList().subscribe(data => this.objectNames = data);
  }
  findName(objectClassName): string {
    let n = '';
    const len = this.objectNames.length;
    for (let i = 0; i < len; i++) {
      if (this.objectNames[i].object_name == objectClassName) {
        n = this.objectNames[i].object_lable_name;
        break;
      }
    }
    return n;
  }

  globalcheckboxchange() {
    this.pageItemsToBeApproved.content.forEach(d => {
      d.selected = this.globalcheckbox;
    });
  }

  doMultipleApproval() {
    const appitems = [];
    this.pageItemsToBeApproved.content.forEach(d => {
      if (d.selected) {
        const req: ApprovalRequest = new ApprovalRequest();
        req.action = this.approvalRequest.action;
        req.approvalId = d.id;
        req.remark = this.approvalRequest.remark;
        appitems.push(req);
      }
    });

    if (appitems.length == 0) {
      this.showAlert('Please select Object to approve');
      return;
    }
    if (this.approvalRequest.remark == undefined || this.approvalRequest.remark == null || this.approvalRequest.remark == '') {
      this.showAlert('Please provide remarks');
      return;
    }
    if (this.approvalRequest.remark != null && this.approvalRequest.remark != undefined && this.approvalRequest.remark.length > 200) {
      this.showAlert('Please write remark in less than 200 characters');
      return;
    }
    if (this.approvalRequest.action == 'select') {
      this.showAlert('Please select action');
      return;
    }

    this._ngxspinner.show();
    this._tobeapprovedservice.multipleApproval(appitems).subscribe(data => {
      this._ngxspinner.hide();
      this.globalcheckbox = false;
      this.MULTIPLEAPPROVAL.hide();
      this.showMessage('Your Action Performed Successfully');
      this.getItemsToBeApproved(this.sortColumnName, this.sortOrder);
    }, error => {
      this._ngxspinner.hide();
      this.showAlert(error.error.message);
    });
  }

  getSearchResult(){
    if(this.advanceSearch.request_number==''&&
      this.advanceSearch. type_name==''&&
      this.advanceSearch.date_from==''&&
      this.advanceSearch.date_to==''&&
      this.advanceSearch.action==''){
      // this.showAlert("Enter Valid key for search");
      this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });


    }
    else if(this.advanceSearch.date_from=='' && this.advanceSearch.date_to!=''){
      this._global.alert({ type: 'alert', message:"Please Enter Date from for Search" });

    }
    else if(this.advanceSearch.date_from!='' && this.advanceSearch.date_to==''){
      this._global.alert({ type: 'alert', message:"Please Enter Date To for Search" });

    }
    else{
      this._ngxspinner.show();
      if(this.advanceSearch.date_from!=''){
        this.advanceSearch.date_from = this.datePipe.transform(this.advanceSearch.date_from, 'yyyy-MM-dd');

      } if(this.advanceSearch.date_to!='')
    { this.advanceSearch.date_to = this.datePipe.transform(this.advanceSearch.date_to, 'yyyy-MM-dd');}
   
      this._tobeapprovedservice.getAdvanceSearchResult(this.advanceSearch).subscribe(data=>{
        this._ngxspinner.hide();
        this.pageItemsToBeApproved.content = data;
        this.page=0;
        this.pageItemsToBeApproved.first=true;
        this.pageItemsToBeApproved.last=true;
        this.pageItemsToBeApproved.numberOfElements=data.length;
        this.pageItemsToBeApproved.totalElements=data.length;
        this.pageItemsToBeApproved.totalPages=0;
      },error=>{
        this._ngxspinner.hide();
        this.showAlert(error.error.message);
      });
    }
  
  }
  resetSearchResult(){
   this.advanceSearch.request_number=''
   this.advanceSearch.type_name=''
   this.advanceSearch.date_from=''
   this.advanceSearch.date_to=''
   this.advanceSearch.action=''
  }

  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pageItemsToBeApproved.totalPages< pagenumber){
    this._global.alert({ type: 'alert', message:"Enter Valid Input" });
  }
else{
   this.gotOpage(pagenumber-1)
}
}
}
