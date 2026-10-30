import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { CaseResponse, CaseRequest } from '../../services/model';
import { caseService } from '../../services/caseService.service';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bulkupload',
  templateUrl: './bulkupload.component.html',
  styleUrls: ['./bulkupload.component.scss']
})
export class BulkuploadComponent implements OnInit {
  @ViewChild('createCaseModel', { static: false }) createCaseModel: ModalDirective;
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;
   res: CaseResponse = new CaseResponse();
   req: CaseRequest = new CaseRequest();
   AllCase: Array<CaseResponse> = [];
   isVisible: boolean = false;
   isEditMode: boolean = false;
   heading: string;
   edit: boolean = false;
   CREATE_ACCOUNT_HEADING: string = 'New Case ';
   EDIT_ACCOUNT_HEADING: string = 'Edit Case '
   order: string = 'caseId'; // first column variable
   reverse: boolean = false;
   page: number = 0;
   totalPages: any;
   first: boolean;
   last: boolean;
   serialNumber: number;
   size: number;
   showFilter: boolean = false;
   showHide: boolean = false;
   sortColumnName: string = 'createDate'; // first column variable
   sortOrder: string = 'desc';
   sortColumn: number = 5;
   columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false];
   columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, true];
   ASCENDING: string = 'asc';
   DESCENDING: string = 'desc';
   closeView:boolean=false;
   openView:boolean=false;
   allView:boolean=true;
   toBeInvoiceView:boolean=false;
   workBenchView:boolean=false;
   recentlyView: boolean = false;
   searchitem: string = '';
   ngClassAll: string='dropdown-item dropdown-item-checked';
   ngClassclose: string;
   ngClassOpen:string;
   ngClassToBeInvoice:string;
   ngClassWorkBench:string;
   closeStatus:string='Closed';
   OpenStatus: string='Open';
   toBeInvoiced:string='To Be Invoiced';
   toBeInvoice:boolean;
   recently: boolean = true;
   ngClassRelated: string;
   related: boolean = true;
   open:boolean=false;
   close:boolean=false;
   datainput: boolean = false;
   ngClassTable: string = 'fa fa-check';
   values: string;
   caseId: any;
   recentUser: boolean = true;
   i: any;
   popupNgClass: string = 'alert alert-danger center center-block';
   caseReasonvar: string;
   caseReasonId: any;
   caseTypeId: string;
   caseTypeName: string;
   message: string = '';
   isCaseModelOpen: boolean = false;
   _cFlag:string;
   constructor(private case_service: caseService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
     private orderPipe: OrderPipe,private loginService:Loginservices) {
     this._activatedRoute.params.subscribe(pram => {
       this.caseId = pram['caseId'];
     });
     this._activatedRoute.queryParams.subscribe(pram => {
       this._cFlag = pram['_c'];
     });
     this.AllCase = this.orderPipe.transform(this.AllCase, this.order);
   }
   rights(object,right:string):boolean{
     return this.loginService.isUserValidFor(object,right);
   }
 

  ngOnInit() {
  }
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[7] = true;
        }
        break;
      case 8:
        if (this.columnSort[8]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[8] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[8] = true;
        }
        break;
      case 9:
        if (this.columnSort[9]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[9] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[9] = true;
        }
        break;
      case 9:
        if (this.columnSort[10]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[10] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(11).fill(false);
          this.columnSort[10] = true;
        }
        break;
    }
    // if (this.recentlyView) {
    //   this.recentVisit(this.sortColumnName, this.sortOrder);
    // } else {
    //   this.showCase(this.sortColumnName, this.sortOrder);
    // }
  }
  dropdownChange(tabName: string) {
    if (tabName === 'related') {
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.ngClassAll = 'dropdown-item';
      this.ngClassclose='dropdown-item';
      this.ngClassOpen='dropdown-item';
      this.ngClassToBeInvoice='dropdown-item';
      this.ngClassWorkBench='dropdown-item';
      this.close=false;
      this.open=false;
      this.closeView=false;
      this.allView=false;
      this.openView=false;
      this.toBeInvoiceView=false;
      this.workBenchView=false;
      this.sortOrder = 'desc';
      this.page = 0;
      // this.recentVisit(this.sortColumnName, this.sortOrder);
      this.recentlyView = true;
     
      //  this.serialNumber = (((this.page) * this.size));
    }
    else if(tabName==='closed'){
      this.ngClassclose='dropdown-item dropdown-item-checked'
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated='dropdown-item';
      this.ngClassOpen='dropdown-item';
      this.ngClassToBeInvoice='dropdown-item';
      this.ngClassWorkBench='dropdown-item';
      this.sortOrder = 'desc';
      this.closeView=true;
      this.toBeInvoiceView=false;
      this.close=true;
      this.open=false;
        this.recentlyView = false;
        this.recentUser = false;
        this.workBenchView=false;
        this.openView=false;
        this.allView=false;
        this.page = 0;
      // this.CaseByStatus(this.sortColumnName, this.sortOrder,this.closeStatus)
  
    }
    else if(tabName==='open'){
      this.openView=true
      this.open=true;
      this.ngClassOpen='dropdown-item dropdown-item-checked';
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated='dropdown-item';
      this.ngClassclose='dropdown-item';
      this.ngClassToBeInvoice='dropdown-item';
      this.ngClassWorkBench='dropdown-item';
      this.sortOrder = 'desc';
      this.close=false;
      this.toBeInvoiceView=false;
       this.recentlyView = false;
        this.workBenchView=false;
        this.closeView=false;
        this.allView=false;
        this.page = 0;
      // this.CaseByStatus(this.sortColumnName, this.sortOrder,this.OpenStatus);
      }
    else if(tabName==='toBeInvoice'){
      this.toBeInvoice=true
      this.toBeInvoiceView=true;
      this.ngClassToBeInvoice='dropdown-item dropdown-item-checked';
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated='dropdown-item';
      this.ngClassclose='dropdown-item';
      this.ngClassOpen='dropdown-item';
      this.ngClassWorkBench='dropdown-item';
      this.sortOrder = 'desc';
      this.close=false;
       this.recentlyView = false;
        this.workBenchView=false;
        this.closeView=false;
        this.allView=false;
        this.openView=false;
        this.open=false;
        this.page = 0;
      // this.CaseByStage(this.sortColumnName, this.sortOrder,this.toBeInvoiced);

    }
    
    else if(tabName==='All') {
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.ngClassclose='dropdown-item';
      this.ngClassOpen='dropdown-item';
      this.ngClassToBeInvoice='dropdown-item';
      this.ngClassWorkBench='dropdown-item';
      this.closeView=false;
      this.recentlyView = false;
      this.workBenchView=false;
      this.toBeInvoiceView=false;
      this.recentUser = false;
      this.openView=false;
      this.sortOrder = 'desc';
      this.allView=true;
      this.page = 0;
      // this.showCase(this.sortColumnName, this.sortOrder);
      // this.serialNumber = ((this.page * this.size)); 
    }
  }
  popupClose(){
    this.popupSuccessModel.hide();
  }

}
