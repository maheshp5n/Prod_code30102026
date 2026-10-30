import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { OrderPipe } from 'ngx-order-pipe';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { vendorInvoiceResponse, Page, InvoiceStatus } from '../../services/model';
import { GlobalserviceService } from '../../globalservice.service';
import { PagefilterService } from '../../services/pagefilterservice';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import * as fileSaver from 'file-saver';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss']
})
export class InvoiceComponent implements OnInit {

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createInvoiceModel', { static: false }) createInvoiceModel: ModalDirective
  @ViewChild('choosenFiles', { static: false }) choosenFiles: ModalDirective;
  message: string = '';
  // @ViewChild('agGrid') agGrid: AgGridAngular;
  popupNgClass: string = 'alert alert-danger center center-block';
  dataStyle: string;

  showHide: boolean = false;
  showFilter: boolean = false;
  sortDisplay: boolean = true;
  table: boolean = true;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create Invoice ';
  EDIT_ACCOUNT_HEADING: string = 'Edit Invoice '
  order: string = 'siteId'; // first column variable
  reverse: boolean = false;
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  // contactPage: Page<SiteDetailsResponse> = new Page<SiteDetailsResponse>();
  page: number = 0;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number = 0;
  size: number;
  recentUser: boolean = false;
  invoiceId: number;
  related: boolean = false;
  recentlyView: boolean = false;
  editRowId: boolean = false;
  loginData: any
  searchitem: string
  sortColumnName: string = 'created_date'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 7;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  i: any
  isModelOpen: boolean = false;
  _cFlag: string;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  statusLi:Array<InvoiceStatus>=[];

  getFile:any;
  filyType:any;
  fileList:any=[];
  fileToUpload: File;
  formData = new FormData();
  uploadForm: FormGroup;
  //filter
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: '0',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }
  ];


  dropdownSelected = {
    sqId: 0,
    name: 'All',
    filtreparam: '0',
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: ' Name',
      sortColumnName: 'manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Vendor Name',
      sortColumnName: 'vendor_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Vendor Code',
      sortColumnName: 'po.vendor_code',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Circle',
      sortColumnName: 'po.circle_circle_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'PO Cost Center',
      sortColumnName: 'po.cost_centre',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'PO Number',
      sortColumnName: 'po.po_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'PO Date',
      sortColumnName: 'po.po_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Vendor Invoice Number',
      sortColumnName: 'vendor_invoice_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Vendor Invoice Date',
      sortColumnName: 'vendor_invoice_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 10,
      name: 'Approval Status',
      sortColumnName: 'invs.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },

  ];

  //done

  constructor(
    private formBuilder: FormBuilder,
    private pagefilterservice: PagefilterService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private loginservice: Loginservices, private orderPipe: OrderPipe, private vendorinvoiceservice: VendorInvoiceService,
    private _globa: GlobalserviceService,private datepipe:DatePipe) {

    this._activatedRoute.params.subscribe(pram => {
      this.invoiceId = pram['invoiceid'];

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
    // this.siteList = orderPipe.transform(this.siteList, this.order);
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('invoice').subscribe(data => {
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

      this.getInvoice();
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

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getInvoice();
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
        this.getInvoice();
      } else {
        tbl.icon = '';
      }
    });
  }
  changeShowStatus() {

    if (this.showHide == true) {
      this.showHide = false;
    }
    else {
      this.showHide = true;
    }
  }
  cancelData() {
    this.createInvoiceModel.hide();
  }
  InvoiceList: Page<vendorInvoiceResponse> = new Page<vendorInvoiceResponse>()
  getInvoice() {
    this.spinner.show()
    this.InvoiceList.content = [];
    this.vendorinvoiceservice.getVenInvoice(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.InvoiceList = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })

  }



  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    this.getStatusForLIs();
    this.getLoggedInUser();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }


  RowDelete(id: any) {

  }

  createShowModal() {
    // this.res=new SiteDetailsResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createInvoiceModel.show();
  }

  cancelInvoiceData() {
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createInvoiceModel.hide();

  }

  onInvoiceCreated($event: vendorInvoiceResponse) {
    this.getInvoice();
    this._globa.alert({ type: '', message: 'Invoice Created Successfully' });

  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.InvoiceList.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this.popupSuccessModel.show();
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getInvoice();
}
}

searchContent={
  invoice_number:'',
  vendor_name:'',
  vendor_code:'',
  circle_name:'',
  po_number:'',
  po_date:'',
  vendor_invoice_number:'',
  vendor_invoice_date:'',
  invoice_status:''
 
};
resetAdvanceSearchresult(){
  this.searchContent.invoice_number=''
  this.searchContent.vendor_name=''
  this.searchContent.vendor_code=''
  this.searchContent.circle_name=''
  this.searchContent.po_number=''
  this.searchContent.po_date=''
  this.searchContent.vendor_invoice_number=''
  this.searchContent.vendor_invoice_date=''
  this.searchContent.invoice_status=''

}
getAdvanceSearchresult(){
  
  if(this.searchContent.invoice_number=="" &&  this.searchContent.invoice_number==''&&
  this.searchContent.vendor_name=='' &&
  this.searchContent.vendor_code=='' &&
  this.searchContent.circle_name==''&&
  this.searchContent.po_number=='' && this.searchContent.po_date=='' && this.searchContent.vendor_invoice_number=='' &&
  this.searchContent.invoice_status=='' && this.searchContent.vendor_invoice_date=='')
    {
      this._globa.alert({ type: 'alert', message: "Please Enter valid Input to Search" });
    }
  
    else{
      if(this.searchContent.vendor_invoice_date!=''){
        this.searchContent.vendor_invoice_date = this.datepipe.transform(this.searchContent.vendor_invoice_date, 'yyyy-MM-dd');
      }
    
      if(this.searchContent.po_date!='' ){
        this.searchContent.po_date = this.datepipe.transform(this.searchContent.po_date, 'yyyy-MM-dd');
       
      }
      this.spinner.show();
     
      this.vendorinvoiceservice.getSearchResult(this.searchContent).subscribe(data=>{
        this.spinner.hide();
        this.InvoiceList.content=data;
        // this.siteList.first=true;
        // this.siteList.last=true;
      },error=>{
        this.spinner.hide();
        this._globa.alert({ type: 'alert', message: error.error.message });
    
        
      });
    }

}

getStatusForLIs(){
  this.vendorinvoiceservice.getStatusForList().subscribe(data=>{
    const temp=[];
    for(let i=0;i<data.length;i++){
    let lis = new InvoiceStatus();
    lis.statusname=data[i];
    temp.push(lis);
    this.statusLi=temp;
    }
  })
}

  // bulk upload
  
  onFileSelect(event) {
    // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
    if(event!=null){
      
    this.getFile = event.target.files.id;
    this.filyType = event.target.id;
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
        // console.log(this.fileToUpload);

      }
    }
    }
    else{
      console.log(event)
    }
   
  }
  // submit file choosen
  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
      // this.fileToUpload = event.target.fileList[i];

    }
    if (this.filyType == "getFile") {
      this.vendorinvoiceservice.uploadFiles1(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null
        this.message = "Updated Successfully";
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this.popupSuccessModel.show();
      });
      this.formData = new FormData();
      this.getFile = "";
    }
  }
  // get response file downloaded
  cancelChoosen() {
    (<HTMLInputElement>document.getElementById('getFile')).value=null
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = "";
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

getTemplateExcel() {
  this.vendorinvoiceservice.getExcelTemplate().subscribe(data => {
    // this.downloadFile(data);
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "GRNUpdate" + ".xlsx");
  }, error => {
    this.spinner.hide();
    this.message = error.message;
    this.popupSuccessModel.show();
  })
}

}
