import { Page } from './../../services/model';
import { ServiceOrderJobCardService } from './../../services/serviceorderjobcardservice';
import { GlobalserviceService } from './../../globalservice.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { RepairDCTResponse, LocationFaultyLineItemResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { OrderPipe } from 'ngx-order-pipe';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { PagefilterService } from '../../services/pagefilterservice';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import * as fileSaver from 'file-saver';
import { FormBuilder, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-repair-dct',
  templateUrl: './repair-dct.component.html',
  styleUrls: ['./repair-dct.component.scss']
})
export class RepairDCTComponent implements OnInit {
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  
  searchitem: string;
  pagedata: Page<any> = new Page<any>();
  advanceSearch={
    box:false,
    job_card_number:'',
    product_serial_number:'',
    product_part_code:'',
    product_description:'',
    assigned_date_from:'',
    assigned_date_to:'',
    isReturn:false,
    isRepair:false
  }
  dropdownsList = [{
    sqId: 1,
    name: 'Repaired',
    clickEventParam: 'Repaired',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }];
  dropdownSelected = {
    sqId: 1,
    name: 'Repaired',
    filtreparam: 'Repaired',
    sortname: 'manual_id',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'JOB Card Number',
      sortColumnName: 'manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Product Serial Number',
      sortColumnName: 'item.serial_number',
      icon: ''
    },
    {
      id: 3,
      name: 'Product Part code',
      sortColumnName: 'item.product_product_part_code',
      icon: ''
    },
    {
      id: 4,
      name: 'Product description',
      sortColumnName: 'prd.pdoduct_description',
      icon: ''
    }, {
      id: 5,
      name: 'Assigned Date',
      sortColumnName: 'assigned_date',
      icon: ''
    }, {
      id: 6,
      name: 'Repaired',
      sortColumnName: 'reparied',
      icon: ''
    }, 
    {
      id: 7,
      name: 'Returned',
      sortColumnName: 'returned',
      icon: ''
    }, 
    {
      id: 7,
      name: 'Assigned To',
      sortColumnName: '',
      icon: 'res.name'
    }
  ];
_cFlag:string;
today = new Date();
dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(private spinner: NgxSpinnerService,
    private loginservice: Loginservices,
    private _global: GlobalserviceService,
    private _serviceorderjobcard: ServiceOrderJobCardService,
    private _activatedRoute:ActivatedRoute,
    private pagefilterservice:PagefilterService,private datePipe:DatePipe,
    private formBuilder:FormBuilder
  ) {
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
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    //  this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  pindtselection=false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('repairdct').subscribe(data => {
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
      this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
    
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
  serialNumber;
  getJobCards(filter?) {
    this.spinner.show();
    this._serviceorderjobcard.getJobCards(filter).subscribe(data => {
      this.spinner.hide();
      this.pagedata = data;
      this.serialNumber = (((this.dropdownSelected.page) *data.size));
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
        this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
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
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
      } else {
        tbl.icon = '';
      }
    });
  }
  goToNext(pageNumber) {
    this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=${pageNumber + 1}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  goToPrevious(pageNumber) {
    this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=${pageNumber - 1}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  resetsearch(){
    this.advanceSearch.box=false
    this.advanceSearch.job_card_number=''
    this.advanceSearch.product_serial_number=''
    this.advanceSearch.product_part_code=''
    this.advanceSearch.product_description=''
    this.advanceSearch.assigned_date_from=''
    this.advanceSearch.assigned_date_to=''
    this.advanceSearch.isReturn=false
    this.advanceSearch.isRepair=false

  }

  getAdvanceSearchResult(){
    this.advanceSearch.job_card_number
    if(this.advanceSearch.job_card_number==''&&
    this.advanceSearch.product_serial_number==''&&
    this.advanceSearch.product_part_code==''&&
    this.advanceSearch.product_description==''&&
     this.advanceSearch.assigned_date_from==''&&
     this.advanceSearch.isRepair==false&& this.advanceSearch.isReturn==false&&
    this.advanceSearch.assigned_date_to==''){
      this._global.alert({type:'alert',message:'Please Input Valid key for search'});
    }
    else{
      if(this.advanceSearch.assigned_date_from!=''){
        this.advanceSearch.assigned_date_from = this.datePipe.transform(this.advanceSearch.assigned_date_from, 'yyyy-MM-dd');
      }
    
      if(this.advanceSearch.assigned_date_to!='' ){
        this.advanceSearch.assigned_date_to = this.datePipe.transform(this.advanceSearch.assigned_date_to, 'yyyy-MM-dd');
       
      }
      this.spinner.show();
      this._serviceorderjobcard.getAdvanceSearchResult(this.advanceSearch).subscribe(data=>{
        this.spinner.hide();
        this.pagedata.content=data;
        this.pagedata.last=false;
        this.pagedata.first=false;
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });

    }
 
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pagedata.totalPages< pagenumber){
   this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getJobCards(`filtername=${this.dropdownSelected.filtreparam}&page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
    
  }
}
getTemplateExcel() {
  this._serviceorderjobcard.getExcelTemplate().subscribe(data => {
    // this.downloadFile(data);
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "Template" + ".xlsx");
  }, error => {
    this.spinner.hide();
    this._global.alert({type:'alert',message:error.message});
  })
}

// file select
getFile:any;
fileType:string;
fileList:any[]=[];
fileToUpload:File;
uploadForm:FormGroup;
onFileSelect(event) {
  // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
  if(event!=null){
    
  this.getFile = event.target.files.id;
  this.fileType = event.target.id;
  // this.podgetFile=event.target.files.id;
 
  console.log(this.fileType);
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
onSubmit(){
  this.spinner.show();
   this._serviceorderjobcard.uploadFiles(this.fileToUpload).subscribe(data=>{
    this.spinner.hide();
    this.cancelChoosen();
   },error=>{
     this.spinner.hide();
       this._global.alert({type:'alert',message:error.message});

   })

  }
  cancelChoosen() {
    (<HTMLInputElement>document.getElementById('getFile')).value=null
    this.choosenFiles.hide();
    // this.uploadForm = new FormData();
    this.fileList = [];
    this.getFile = "";
  }
}
