import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { Page } from '../../services/model';
import { ActivatedRoute } from '@angular/router';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import * as XLSX from 'xlsx';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
@Component({
  selector: 'app-myinventorydetail',
  templateUrl: './myinventorydetail.component.html',
  styleUrls: ['./myinventorydetail.component.scss']
})
export class MyinventorydetailComponent implements OnInit {

  @ViewChild('datatable',{static:false})datatable:ElementRef;

  @ViewChild('newpartcodechangeWindow',{static:false})newpartcodechangeWindow:ModalDirective;

  search = {
    partCode: '',
    serialNumber: '',
    description: '',
    state: 0,
    advancesearch: false,
    gserch: ''
  };

  queryparameter = {
    page: 0,
    size: 20
  };
  pageReuslt: Page<any> = new Page();
  searchResult = [];

  openWindow={
    newpartcodechange:false,
    data:undefined,
    userValid:false
  }
  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'mn.customerInventory',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Part Code',
      sortColumnName: 'mn.partCode',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Discription',
      sortColumnName: 'mn.productDescription',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Type',
      sortColumnName: 'mn.productType',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Total Quantity On Hand',
      sortColumnName: 'mn.quantityOnHand',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Total Ok Quantity',
      sortColumnName: 'mn.okQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Total Faulty Quantity',
      sortColumnName: 'mn.faultyQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Total Scrap Quantity ',
      sortColumnName: 'mn.scrapQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Total Reject Quantity',
      sortColumnName: 'mn.rejectedQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Total Customer Inventory Quantity',
      sortColumnName: 'mn.customerInventoryQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Total BER Quantity',
      sortColumnName: 'mn.berQty',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Serial Number',
      sortColumnName: 'mn.serialNumber',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Customer Inventory',
      sortColumnName: 'mn.customerInventory',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Status',
      sortColumnName: '',
      icon: ''
    },
   
  ];

  constructor(
    private _location: LocationService,
    private _ngx: NgxSpinnerService,
    private _global: GlobalserviceService,
    private _activated: ActivatedRoute,
    private loginservice:Loginservices
  ) {
    this._activated.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.queryparameter.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.queryparameter.size = Number(param['size']);
      }
      this.getPageable();
    });
  }

  rights(object, right): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    //this.getSerchResult();
    if(this.rights('LocationProductLineItems','edit')){
      if(this.rights('LocationProductItem','edit')){
        this.openWindow.userValid=true;
      }
      else{
        this.openWindow.userValid=false;
      }
      
    }
    else{
      this.openWindow.userValid=false;
    }
  }
  serialNumber
  getPageable() {
    this._ngx.show();
    this._location.getMyInventory(`?page=${this.queryparameter.page}&${this.queryparameter.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`).subscribe(data => {
      this.searchResult = data.content;
      this.pageReuslt = data;
      this.serialNumber = (((this.queryparameter.page) * this.queryparameter.size));
      this._ngx.hide();
    }, error => this._ngx.hide());
  }

  getSerchResult() {
    if(this.search. partCode==''&&
    this.search.serialNumber=='' &&
    this.search.description==''&&
    this.search.state==0 &&
    this.search.advancesearch==false &&
    this.search.gserch== ''){
      this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });

    }
    else{   this.searchResult = [];
      this._ngx.show();
      this._location.searchMyInventory(this.search).subscribe(data => {
        this.searchResult = data;
        this._ngx.hide();
      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });}
  
  }
  chagneStatus(item) {
    if(item.remarks==undefined||item.remarks==null||item.remarks==''){
      this._global.alert({type:'alert',message:'Please fill remarks'});
      return;
    }
    this._ngx.show();
    this._location.changeStatus(item.lineItemId, item.serialNumberStateId,item.remarks).subscribe(data => {
      this._ngx.hide();
      this._global.alert({ type: '', message: 'Product status updated' });
    }, erro => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: erro.error.message });
    });
  }
  exportToExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.datatable.nativeElement);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    XLSX.writeFile(workbook, 'myinventory_search_result.xlsx');
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pageReuslt.totalPages< pagenumber){

    this._global.alert({type:'alert',message:"Enter Valid Input"});
  }
else{
    this.queryparameter.page=pagenumber-1;
    this.getPageable();
}
}
reset(){
  this.search.partCode= ''
  this.search.serialNumber= ''
  this.search.description= ''
  this.search.state= 0
  this.search.advancesearch= false
  this.search.gserch=''
}

onPartcodeEditClick(data){
  this.openWindow.data=data;
  this.newpartcodechangeWindow.show();
}

onPartcodechangeCancel(){
  this.newpartcodechangeWindow.hide();
}
onPartcodechangeSave(){
  this.newpartcodechangeWindow.hide();
  this.reset();
  this.getPageable();

}
getSortOrder(tblId) {
  this.requestTableHeaders.forEach((tbl) => {
    if (tbl.id == tblId) {
      this.dropdownSelected.sortname = tbl.sortColumnName;
      this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
      tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
      this.dropdownSelected.page = 0;
     this.getPageable();
    } else {
      tbl.icon = '';
    }
  });
}

}
