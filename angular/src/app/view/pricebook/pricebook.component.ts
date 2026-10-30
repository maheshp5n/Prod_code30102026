import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { OrderPipe } from 'ngx-order-pipe';
import { Page, PriceBookResponse } from '../../services/model';
import { PriceBookService } from '../../services/pricebook.service';
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-pricebook',
  templateUrl: './pricebook.component.html',
  styleUrls: ['./pricebook.component.scss']
})
export class PricebookComponent implements OnInit {

  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @ViewChild('createpriceBookModel',{static:false}) createpriceBookModel: ModalDirective;

  recentlyView: boolean = true;
  searchitem: string = '';
  private name: string;
  ngClassAll:string = 'dropdown-item dropdown-item-checked';
  recently: boolean = true;
  ngClassRelated: string;
  related: boolean = true;
  datainput: boolean = false
  ngClassTable: string = 'fa fa-check';
  values: string;
  priceResp: PriceBookResponse = new PriceBookResponse();
 AllpriceTable: Page<PriceBookResponse> = new Page<PriceBookResponse>()
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create Price Book ';
  EDIT_ACCOUNT_HEADING: string = 'Edit Price Book '
  order: string = 'name'; // first column variable
  reverse: boolean = false;
  page: number = 0;
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  showFilter:boolean=false
  showHide:boolean=false
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 3;
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  i:any
  message:string="";
  isPriceBook: boolean;
  id:number;
  _cFlag:string;
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
        name: 'Price Book Name',
        sortColumnName: 'name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Description',
        sortColumnName: 'description',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Active',
        sortColumnName: 'mslRequestStatus',
        icon: ''
      },
      {
        id: 4,
        name: 'Last Modified By Date',
        sortColumnName: 'modifiedDate',
        icon: 'fa fa-angle-down pull-right'
      },
     
    ];
  
    //done

  constructor(private pagefilterservice:PagefilterService,private loginservice:Loginservices ,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private orderPipe: OrderPipe,private pricebooservice:PriceBookService) {

  this.AllpriceTable = orderPipe.transform(this.AllpriceTable, this.order);
  this._activatedRoute.params.subscribe(pram => {
    this.id = pram['id'];
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
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  ngOnInit() {
    // this.PriceBookTable(this.sortColumnName, this.sortOrder)
   this.priceResp=new PriceBookResponse()
    
  }
  
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('pricebook').subscribe(data => {
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
 
      this.PriceBookTable()
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
     this.PriceBookTable();
      
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
      this.PriceBookTable();
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
  PriceBookTable(){
    this.spinner.show();
    this.AllpriceTable.content=[];
    this.pricebooservice.getpriceBook(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
      this.spinner.hide();
      this.AllpriceTable=data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber=((this.page) * data.size);
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }
  createPriceBookModal(){
    this.priceResp=new PriceBookResponse();
    this.isEditMode=false;
    this.heading=this.CREATE_ACCOUNT_HEADING

    this.createpriceBookModel.show();
  }
  cancelPricebookData(){
    this.createpriceBookModel.hide();
  }
  onpricebookCreated($event:PriceBookResponse){
    if(!this.isEditMode){
      this.AllpriceTable.content.unshift($event);
    }else{
      let index = 0;
      this.AllpriceTable.content.forEach(element => {
        if(element.id == $event.id){
          this.AllpriceTable[index]=$event;
        }else{
          index++;
        }
      });
    }
  }
  editpricebookModelshow(priceres:PriceBookResponse){
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.pricebooservice.getDetailById(priceres.id).subscribe(data=>{
      this.priceResp=data;
      this.spinner.hide();
   this.createpriceBookModel.show();

    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });

  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.AllpriceTable.totalPages< pagenumber){
    this.message="Enter Valid input";
    this.popupSuccessModel.show();
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.PriceBookTable();
}
}
 
}
