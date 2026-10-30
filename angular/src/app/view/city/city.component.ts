import { Component, OnInit, ViewChild } from '@angular/core';
import { cityService } from '../../services/cityService.service';
import { ActivatedRoute } from '@angular/router';
import { cityDetailsReq, cityDetailsResponse, CityCatagory, Page} from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css']
})
export class CityComponent implements OnInit {
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @ViewChild('createCityModel',{static:false}) createCityModel: ModalDirective;

  recentlyView: boolean = false;
  searchitem: string = '';
  private name: string;
  ngClassAll: string='dropdown-item dropdown-item-checked';
  recently: boolean = true;
  ngClassRelated: string;
  related: boolean = true;
  datainput: boolean = false
  ngClassTable: string = 'fa fa-check';
  values: string;
  res: cityDetailsResponse = new cityDetailsResponse();
  catRes: CityCatagory = new CityCatagory();
  cityPage: Page<cityDetailsReq> = new Page<cityDetailsReq>();
  Allcity: Page<cityDetailsResponse> =new Page<cityDetailsResponse>();
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create city ';
  EDIT_ACCOUNT_HEADING: string = 'Edit city '
  order: string = 'cityName'; // first column variable
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
        name: 'City Name',
        sortColumnName: 'cityName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'City Category',
        sortColumnName: 'cityCatagory.cityCatagoryName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Created Date',
        sortColumnName: 'createdDate',
        icon: 'fa fa-angle-down pull-right'
      },
     
    ];
    advancesearch = {
      cityName: '',
      cityCatagory:'',
    };
  
    //done
  constructor(private pagefilterservice:PagefilterService,private loginservice:Loginservices,private spinner: NgxSpinnerService, private cityservice: cityService, 
    private _global:GlobalserviceService,private _activatedRoute: ActivatedRoute, private orderPipe: OrderPipe) {
    this.Allcity = orderPipe.transform(this.Allcity, this.order);
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
    
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('city').subscribe(data =>{
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

      this.showCity();
    });
  }



  showCity() {
    this.spinner.show();
 this.Allcity.content=[];
    this.cityservice.getCity(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
     
      this.spinner.hide()
    
      this.Allcity = data
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
      setTimeout(() => {
      this.popupSuccessModel.hide();
      }, 2000
    );
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
        this.showCity();
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
        this.showCity();
      } else {
        tbl.icon = '';
      }
    });
  }

  createShowModal() {
    this.res = new cityDetailsResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createCityModel.show();
  }

  editLegalENtityModelshow(response: cityDetailsResponse) {
    this.res = response;
    // let eventCity = new cityDetailsResponse();
    this.spinner.show();
    this.cityservice.getCityByCityname(this.res.cityName).subscribe(data => {
     
        this.spinner.hide();
     
      this.res=data
  
    });
    this.res=response
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createCityModel.show();
  }



  cancelData() {
    this.isEditMode = false;
    // this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createCityModel.hide();
    this.popupSuccessModel.hide();
  }


onCityCreated($event:cityDetailsResponse) {

if(!this.isEditMode){
  this.Allcity.content.unshift($event);
}else{
  let index = 0;
  this.Allcity.content.forEach(element => {
   
    if(element.cityName == $event.cityName){
      this.Allcity[index]=$event;
    }else{
      index++;
    }
  });
}

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
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.Allcity.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this.popupSuccessModel.show();
  }
else{

    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.Allcity.content=[];
    this.cityservice.getCity(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide()
      this.Allcity = data
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });

}}
searchCity() {
  if(this.advancesearch.cityName=='' && this.advancesearch.cityCatagory==''){
    this.message="Please Enter valid Key for Search";
   this._global.alert({type:'alert',message:this.message});
  }
  else{
    this.spinner.show();
  this.cityservice.getSearchResult(this.advancesearch).subscribe(data => {
    this.spinner.hide();
    this.Allcity.content = data;
    this.page = 0;
    this.size = data.length;
    this.first = true;
    this.Allcity.first = true;
    this.Allcity.last = true;
    this.last = true;
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
    
  });
  }

}
searchreset(){
  this.advancesearch.cityName= ''
  this.advancesearch.cityCatagory=''
}
}
