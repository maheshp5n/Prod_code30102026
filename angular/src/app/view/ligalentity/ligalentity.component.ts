import { Component, OnInit, ViewChild } from '@angular/core';

import { SitesForList, Login, AccountCreate, LegalEntityResponse, Page, LegaLEntityType } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { ligalEntityService } from '../../services/ligalentity.services';
import { Loginservices } from '../../services/login.services';

import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';
@Component({
  selector: 'app-ligalentity',
  templateUrl: './ligalentity.component.html',
  styleUrls: ['./ligalentity.component.css']
})
export class LigalentityComponent implements OnInit {
  @ViewChild('createAcountModel',{static:false}) createAcountModel: ModalDirective;
  @ViewChild('createLegalENtityModel',{static:false}) createLegalENtityModel: ModalDirective;
  @ViewChild('changeLegalENtityModel',{static:false}) changeLegalENtityModel: ModalDirective;
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;

  recentlyView: boolean = false;
  isSelected = false;
  dataStyle: string;
  ngClassAll: string='dropdown-item dropdown-item-checked';
  related: boolean = true;
  table: boolean = true;
  ngClassKanban: string;
  ngClassTable: string = 'fa fa-check';
  modalMsg:string;
  ngClassRelated: string;
  inboundClick: boolean = true;
  showHide: boolean = false;
  showFilter: boolean = false;
  isEditMode:boolean=false;
  heading : string;
  edit:boolean=false;
  CREATE_ACCOUNT_HEADING:string= 'Create Legal Entity';
  EDIT_ACCOUNT_HEADING:string= 'Edit Legal Entity'
  tabledata:Array<LegalEntityResponse>
  inputrow: boolean = false;
  legalentityId:number;
   accountOwnername: Login = new Login();
  _login: Login = new Login();
  values: string;
  req: LegalEntityResponse = new LegalEntityResponse();
  res: LegalEntityResponse = new LegalEntityResponse();
  contactPage: Page<LegalEntityResponse> = new Page<LegalEntityResponse>();
 page: number = 0;
  searchitem:string
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size:number;
  loginData:any;
  order: string = 'legalENtityName';
  reverse: boolean = false;
  recentUser:boolean=true;
  i: number;
  pindtselection = false;
   LgTyoeforList:Array<LegaLEntityType>=[]
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
        sortColumnName: 'legalEntityName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Type',
        sortColumnName: 'legalEntityType',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Fixed Payout',
        sortColumnName: 'legalEntityFixedPayout',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 4,
        name: 'Created Date',
        sortColumnName: 'createdDate',
        icon: 'fa fa-angle-down pull-right'
      },
     
    ];
  
    //done



  _cFlag:string;
  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private ligalentitservice: ligalEntityService, 
    private _global:GlobalserviceService,private _activatedRoute: ActivatedRoute,private loginservice: Loginservices,private orderPipe: OrderPipe) {

    this._activatedRoute.params.subscribe(pram => {
      this.legalentityId = pram['id'];
    
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


    // this.recent = orderPipe.transform(this.tabledata, this.order);
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  ngOnInit() {
    this.spinner.show();
    this.getLoggedInUser()
    this.getTypeForList();
 }

 getFilterList() {
   this.pagefilterservice.getPagefilter('legalentity').subscribe(data => {
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

     this.showLigalEntity()
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
      // if(this.dropdownSelected.filtreparam=='related'){
      //   this.recentVisit();
      // }
      // else{
        this.showLigalEntity();
      // }
     
     
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
     
        this.showLigalEntity();
     
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
// SORT

  
showCreateLegalEntityView(){
  this.req=new LegalEntityResponse();
  this.isEditMode = false;
  this.heading =this.CREATE_ACCOUNT_HEADING;
  this.createAcountModel.show();
  } 
  createLegalENtityModelshow(res:LegalEntityResponse){
    this.req=res;
    this.spinner.show();
    this.ligalentitservice.getLigalENtityById(this.req.legalentityId).subscribe(data=>{
  
      this.req=data
      this.spinner.hide();
     });
   this.isEditMode = true;
      this.heading =this.EDIT_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }
 
  message:string;
 


  
showLigalEntity() {

    this.spinner.show();
    this.recent.content=[];
    this.ligalentitservice.getLegalEntity(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe((data) => {
      this.spinner.hide()
    this.recent = data;
      // this.totalPages = data.totalPages;
  
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));
 },error=>{
   this.spinner.hide();
   this.message=error.error.message;
   this._global.alert({type:'alert',message:this.message});
 })



  }
 

  recent:Page<LegalEntityResponse>=new Page<LegalEntityResponse>();
  recentVisit() {
    this.spinner.show();
    this.recent.content=[];
    this.ligalentitservice.getRecentVisit(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
        this.spinner.hide()
        this.contactPage = data;
        this.recent = data;
    });
 
  }

 
  RowDeleteRecent(legalentityId) {
    for (let i = 0; i < this.recent.content.length; ++i) {
      if (this.recent[i].legalentityId === legalentityId) {
        this.recent.content.splice(i, 1);
      }
    }
  }


  changeShowStatus() {
   
    if (this.showHide == true) {
      this.showHide = false;
    }
    else {
      this.showHide = true;
    }
  }
  changeShowFilter() {
    if (this.showFilter == true) {
      this.showFilter = false;

    }
    else {
      this.showFilter = true;
    }
  }


  

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
 
  
  cancelData() {
 this.isEditMode=false;
 this.req=new LegalEntityResponse();
  this.createAcountModel.hide();
  this.popupSuccessModel.hide();
  }
  onEntityCreated($event:LegalEntityResponse){
    if(!this.isEditMode){
      this.recent.content.unshift($event);
    }else{
      let index = 0;
      this.recent.content.forEach(element => {
        if(element.legalentityId == $event.legalentityId){
          this.recent[index]=$event;
        }else{
          index++;
        }
      });
    }
 
 
 // this.recent.push($event);

  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.recent.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this._global.alert({type:'alert',message:this.message});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.showLigalEntity();
}
}
advancesearch = {
  legalEntityName: '',
  type: ''

};

searchLeg() {
   
  if(this.advancesearch.legalEntityName=='' && this.advancesearch.type==''){
   this.message="Please Enter valid Key for Search";
   this._global.alert({type:'alert',message:this.message});
  // this.popupSuccessModel.show();
  }
  else{
    this.spinner.show();
    this.ligalentitservice.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.recent.content= data;
      // this.page = 0;
      // this.size = data.length;
      // this.first = true;
      // this.last = true;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
  }

}
searchreset(){
  this.advancesearch.legalEntityName= ''
  this.advancesearch.type= ''

}
getTypeForList(){
  this.LgTyoeforList=[];
  this.spinner.show();
  this.ligalentitservice.getLegalEntityType().subscribe(data=>{
    this.spinner.hide();
    this.LgTyoeforList=data;
  })
}


}
