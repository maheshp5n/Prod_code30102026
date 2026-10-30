import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { Page, ServiceResourseRequest, ServiceResourseResponse } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';

@Component({
  selector: 'app-bat-serviceresource',
  templateUrl: './bat-serviceresource.component.html',
  styleUrls: ['./bat-serviceresource.component.css']
})
export class BatServiceresourceComponent implements OnInit {

  @ViewChild('createServiceResourceModel', { static: false }) createServiceResourceModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  public message: string = '';
  isServiceResourceOpen: boolean;
  serviceResourceList: Page<ServiceResourseResponse> = new Page<ServiceResourseResponse>()
  searchitem: string = '';
  serviceResourceResponse = new ServiceResourseResponse();
  public page: number = 0;
  private pages: Array<number>;
  private totalPages: number;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Service Resource';
  EDIT_ServiceResource_HEADING: string = 'Edit Service Resource';
  i: number;
  newServiceResource: ServiceResourseRequest = new ServiceResourseRequest();
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  popupNgClass: string = 'alert alert-danger center center-block';

  ngClassAll: string = 'fa fa-check';
  ngClassRelated: string;
  ngClassMyService: string;
  related: boolean;
  recentlyView: boolean;
  _cFlag: string;
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
      name: ' Name',
      sortColumnName: 'name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'User',
      sortColumnName: 'user.emailId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Resource Type',
      sortColumnName: 'recourceType',
      icon: ''
    },{
      id:5,
      name:'Location',
      sortColumnName:'location.locationName',
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

  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private _serviceResourceService: BtserviceresourseService
    , private _activatedRoute: ActivatedRoute, private loginService: Loginservices,private _global:GlobalserviceService) {
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
    return this.loginService.isUserValidFor(object, right);
  }


  ngOnInit() {
    this.serviceResourceList.content=[];
    // this.ngClassAll = 'fa fa-check';
    // this.ngClassRelated = '';
    // this.related = false;
    // this.recentlyView = false;
    // this.sortColumnName = 'createdDate';
    // this.sortOrder = 'desc';
    // this.page = 0;
    // this.getServiceResource(this.sortColumnName, this.sortOrder);
  }

  getServiceResource() {
    this.spinner.show();
    this.serviceResourceList.content=[];
    this._serviceResourceService.getServiceResource(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.serviceResourceList = data;
      // this.pages = new Array(data['totalPages']);
      // this.totalPages = data['totalPages'];
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  getRecentServiceResource() {
    this.spinner.show();
    this.serviceResourceList.content=[];
    this._serviceResourceService.getRecentServiceResource(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.serviceResourceList = data;
      // this.pages = new Array(data['totalPages']);
      // this.totalPages = data.totalPages;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));
      this.spinner.hide();
    });
  }

  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_serviceResources').subscribe(data => {
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
 
      this.getServiceResource();
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
     if(this.dropdownSelected.filtreparam=='related'){
       this.getRecentServiceResource();
     }
     else{
       this.getServiceResource();
     }
      
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
      if(this.dropdownSelected.filtreparam=='related'){
        this.getRecentServiceResource();
      }
      else{
        this.getServiceResource();
      }
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

  createServiceResourceModelhide() {
    this.serviceResourceResponse = new ServiceResourseResponse();
    this.onCancle.emit('cancled');
    this.createServiceResourceModel.hide();
  }

  showCreateAccountView() {
    this.newServiceResource = new ServiceResourseRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createServiceResourceModel.show();
  }

  onClickEdit(serviceResource: { serviceResourceId: number; }) {
    this.spinner.show();
    this._serviceResourceService.getServiceResourceById(serviceResource.serviceResourceId).subscribe(data => {
      this.newServiceResource.serviceResourceId = data.serviceResourceId;
      this.newServiceResource.name = data.name;
      this.newServiceResource.userEmailId = data.userEmailId;
      this.newServiceResource.locationId = data.locationId;
      this.newServiceResource.recourceTypeId = data.resourceTypeId;
      this.newServiceResource.contactId = data.contactId;
      this.newServiceResource.active = data.active;
      this.newServiceResource.active = data.active;
      this.newServiceResource.locationName = data.locationName;

      this.newServiceResource.createdByFirstName = data.createdByFirstName;
      this.newServiceResource.createdByMiddleName = data.createdByMiddleName;
      this.newServiceResource.createdByLastName = data.createdByLastName;
      this.newServiceResource.createdDate = data.createdDate;
      this.newServiceResource.modifiedByFirstName = data.modifiedByFirstName;
      this.newServiceResource.modifiedByMiddleName = data.modifiedByMiddleName;
      this.newServiceResource.modifiedByLastName = data.modifiedByLastName;
      this.newServiceResource.modifiedDate = data.modifiedDate;
      this.serviceResourceResponse = data;
      if (this.newServiceResource.locationName !== null || this.newServiceResource.locationName !== '') {
        this.newServiceResource.isLocationSelected = 'fa fa-times';
      } else {
        this.newServiceResource.isLocationSelected = 'fa fa-search';
      }
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
  
    });

    this.isEditMode = true;
    this.heading = this.EDIT_ServiceResource_HEADING;
    this.createServiceResourceModel.show();
  }

  cancelData() {
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createServiceResourceModel.hide();
    this.newServiceResource = new ServiceResourseRequest();
  }



  isDisabledPrevious() {
    if (this.first === true) {
      return true;
    } else {
      return false;
    }
  }

  isDisabledNext() {

    if (this.last === true) {
      return true;
    } else {
      return false;
    }
  }

  onServiceResourseCreated($event: ServiceResourseResponse) {
    if (!this.isEditMode) {
      this.serviceResourceList.content.unshift($event);
    } else {
      // console.log("hhhhhhhhhhhh")
      this.getServiceResource()
      // let index = 0;
      // this.serviceResourceList.content.forEach(element => {
      //   if (element.serviceResourceId === $event.serviceResourceId) {
      //     this.serviceResourceList[index] = $event;
      //   } else {
      //     index++;
      //   }
      // });
    }
  }


  popupClose() {
    //this.popupSuccessModel.hide();

  }
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    if(pagenumber!=null && pagenumber!==''){
      if(pagenumber>this.serviceResourceList.totalPages || pagenumber==0){
        this._global.alert({type:'alert',message:'Page Count Should Be Less Then Total Page'});
      }

else{
  this.serviceResourceList.number=this.pagenumber-1;

    this.dropdownSelected.page=pagenumber-1;
   this.getServiceResource()
    // this.serialNumber = (((this.dropdownSelected.page) * this.size));
  }
}
else{
  this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
 }
}

// search
advancesearch = {
  name: '',
  userEmailId: '',
  type: '',
  location: '',
  
};
searchSR() {
  if(this.advancesearch.name== ''&&
  this.advancesearch.userEmailId== '' &&
  this.advancesearch.type== ''&&
  this.advancesearch.location== ''){
    this._global.alert({ type: 'alert', message:"Please Enter Valid Key For Search" });
  }
  else{
    this.spinner.show();
  this._serviceResourceService.getSearchResult(this.advancesearch).subscribe(data => {
    this.spinner.hide();
    this.serviceResourceList.content = data;
    this.serviceResourceList.first = true;
    this.serviceResourceList.last = true;
  
  },
  error => {this.spinner.hide()
    this.message=error.error.message;
    this._global.alert({ type: 'alert', message: this.message });
  });

  }
  
}
resetSearch(){
 this.advancesearch.name=''
 this.advancesearch.userEmailId=''
 this.advancesearch.type=''
 this.advancesearch.location= ''
 this.getServiceResource();
}
}
