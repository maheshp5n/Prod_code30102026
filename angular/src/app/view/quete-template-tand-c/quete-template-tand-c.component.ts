import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { QuotePdfHeaderAndFooterResponse, QuotePdfHeaderAndFooterRequest, QuoteTemplateEnableAndDisableRequest, Page } from '../../services/model';
import { QuotePDFTemplateServices } from '../../services/quotepdftemplate.services';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-quete-template-tand-c',
  templateUrl: './quete-template-tand-c.component.html',
  styleUrls: ['./quete-template-tand-c.component.scss']
})
export class QueteTemplateTandCComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('quotePDFTemplateModel', { static: false }) quotePDFTemplateModel: ModalDirective;

  quoteTemplateList: Page<QuotePdfHeaderAndFooterResponse> = new Page<QuotePdfHeaderAndFooterResponse>();
  quotePdfHeaderAndFooterResponse: QuotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
  quoteTemplateEnableAndDisableRequest: QuoteTemplateEnableAndDisableRequest = new QuoteTemplateEnableAndDisableRequest();
  searchitem: string = '';

  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  recentlyView: boolean = false;
  related: boolean = false;
  all: boolean = true;
  allView: boolean = true;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_QUOTETEMPLATE_HEADING: string = 'New Quote Template';
  EDIT_QUOTETEMPLATE_HEADING: string = 'Edit Quote Template';

  message: string;
  page: number = 0;
  totalPages;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  i: number;

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  popupNgClass: string = 'alert alert-danger center center-block';
  isModelShow: boolean = false;
  _cFlag: string;
  
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _quotePDFTemplateServices: QuotePDFTemplateServices, private loginService: Loginservices) {
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getAll(this.sortColumnName, this.sortOrder);
  }

  getAll(column: string, order: string) {
    this.spinner.show();
    this._quotePDFTemplateServices.getAllTemplate(this.page, column, order).subscribe(data => {
      this.quoteTemplateList = data;
      if (data != null) {
        this.spinner.hide();
      }
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecentlyViewed(column: string, order: string) {
    this.spinner.show();
    this.quoteTemplateList.content=[];
    this._quotePDFTemplateServices.getAllTemplate(this.page, column, order).subscribe(data => {
      this.quoteTemplateList = data;
      if (data != null) {
        this.spinner.hide();
      }

      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  dropdownChange(tabName: string) {
    if (tabName == 'all') {
      this.all = true;
      this.allView = true;
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.related = false;
      this.recentlyView = false;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getAll(this.sortColumnName, this.sortOrder);
      this.serialNumber = (((this.page) * this.size));
    }
    else {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.related = true;
      this.recentlyView = true;
      this.all = false;
      this.allView = false;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
      this.serialNumber = (((this.page) * this.size));
    }
    return false;
  }

  quoteTemplateModelShow() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_QUOTETEMPLATE_HEADING;
    this.quotePDFTemplateModel.show();
  }

  quoteTemplateModelHide() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.quotePDFTemplateModel.hide();
  }
  enableDisableUser(quotePdfHeaderAndFooterResponse: QuotePdfHeaderAndFooterResponse, flag) {
    this.quoteTemplateEnableAndDisableRequest.id = quotePdfHeaderAndFooterResponse.id;
    this.quoteTemplateEnableAndDisableRequest.active = flag;
    this.spinner.show();
    this._quotePDFTemplateServices.enableAndDisableTemplate(this.quoteTemplateEnableAndDisableRequest).subscribe(data => {
      this.spinner.hide();
      this.getAll(this.sortColumnName, this.sortOrder);
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  cancelData() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.quotePDFTemplateModel.hide();
  }


  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    //this.serialNumber = (((this.page) * this.size));
    if (this.all == false) {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (this.all == false) {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    //this.serialNumber = (((this.page) * this.size));
    if (this.all == false) {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }


  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[6] = true;
        }
        break;
    }
    if (this.all) {
      this.getAll(this.sortColumnName, this.sortOrder);
    } else {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }

  }

  onQuotePDFTemplateCreated($event: QuotePdfHeaderAndFooterResponse) {
    if (!this.isEditMode) {
      this.quoteTemplateList.content.unshift($event);
    } else {
      let index = 0;
      this.quoteTemplateList.content.forEach(element => {
        if (element.id == $event.id) {
          this.quoteTemplateList[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getAll(this.sortColumnName,this.sortOrder);
  }
  popupClose() {
    this.popupSuccessModel.hide();
  }

  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.quoteTemplateList.totalPages< pagenumber){
  this.message="Enter Valid Input";
  this.popupSuccessModel.show();
  //  this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.page=pagenumber-1;
    if (this.all) {
      this.getAll(this.sortColumnName, this.sortOrder);
    } else {
      this.getRecentlyViewed(this.sortColumnName, this.sortOrder);
    }
    this.serialNumber = (((this.page) * this.size));
  }
}
}
