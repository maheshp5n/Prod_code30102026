import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ChangeOwnerRequest, LocationRequest, LocationResponse, LocationType, Page, UserForList } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { UserforlistService } from '../../../services/userforlist.service';
import { FieldValidation } from '../../../services/validation.services';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';


@Component({
  selector: 'app-bat-warehouse',
  templateUrl: './bat-warehouse.component.html',
  styleUrls: ['./bat-warehouse.component.css']
})
export class BatWarehouseComponent implements OnInit {

  @ViewChild('creatLocation', { static: false }) creatLocation: ModalDirective;
  @ViewChild('changeOwner', { static: false }) changeOwner: ModalDirective;
  isModelOpen = false;
  lcres: LocationResponse = new LocationResponse();
  lcreq: LocationRequest = new LocationRequest();
  localres: LocationResponse = new LocationResponse();
  AllView: boolean = true;
  recentlyView: boolean = false;
  myLocationView: boolean = false;
  isSelected = false;
  dataStyle: string;
  ngClassAll: string = 'fa fa-check';
  ngClassMyLocation: string;
  related: boolean = true;
  table: boolean = true;
  ngClassKanban: string;
  ngClassTable: string = 'fa fa-check';
  modalMsg: string;
  ngClassRelated: string;
  inboundClick: boolean = true;
  showHide: boolean = false;
  showFilter: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create Warehouse';
  EDIT_ACCOUNT_HEADING: string = 'Edit Warehouse';
  page: number = 0;
  searchitem: string
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  loginData: any;
  order: string = 'legalENtityName';
  reverse: boolean = false;
  recentUser: boolean = true
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 4;
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  locationId: number;
  locationdata: Page<LocationResponse>=new Page<LocationResponse>();
  message: string;
  selectionLocationId = [];
  userList: Array<UserForList> = [];
  changeowneruesrEmail: string = '';
  locationtypeList:Array<LocationType>=[];
  i: number;
  _cFlag: string;
  tabname = 'all';
   //filter
   dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'All',
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
    filtreparam: 'All',
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: ' Warehouse Name',
      sortColumnName: 'locationName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Warehouse Type',
      sortColumnName: 'locationType',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Legal Entity Name',
      sortColumnName: 'legalEntity.legalEntityName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Owner',
      sortColumnName: 'owner.firstName',
      icon: 'fa fa-angle-down pull-right'
    }
   
  ];
  advancesearch = {
    locationName: '',
    locationTypeName: '',
    legalEntityName: '',
    owner: '',
    parentLocationName: '',
  
  };

  //done
  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private _userforlist: UserforlistService, private _activatedRoute: ActivatedRoute,
    private loginservice: Loginservices,private _global: GlobalserviceService, private orderPipe: OrderPipe, private locationservice: BtwarehouseService, private _router: Router) {
    this._activatedRoute.params.subscribe(pram => {
      this.locationId = pram['id'];
    });
    this.locationdata = orderPipe.transform(this.locationdata, this.order);
    this._activatedRoute.queryParams.subscribe(param => {
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

      this._cFlag = param['_c'];
      this.page = param['page'] == undefined || param['page'] == null ? 0 : param['page'];
      this.tabname = param['tab'] == undefined || param['tab'] == null ? 'all' : param['tab'];
      if (param['ord'] != undefined && param['ord'] != null) {
        this.order = param['ord'];
      }
      if (param['sord'] != null || param['sord'] != undefined) {
        this.sortOrder = param['sord'];
      }
      if (param['cord'] != null || param['cord'] != undefined) {
        this.sortColumn = param['cord'];
      }
      this.dropdownChange(this.tabname);
    });
  }
  rights(object, right): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
 
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_warehouse').subscribe(data => {
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
 
      this.getLocation();
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
      //  if(this.dropdownSelected.filtreparam=='related'){
      //    this.getRecentLocation();
      //  }
      //  else
      // { 
        this.getLocation();
      
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
    //    if(this.dropdownSelected.filtreparam=='related'){
    //     this.getRecentLocation();
    //   }
    //   else
    //  { 
       this.getLocation();
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

  ngOnInit() {
    this.locationdata.content=[];
    this.getLocationTypeForList();
    //this.getLocation(this.sortColumnName, this.sortOrder)
  }




  getLocation() {
    this.spinner.show()
    this.locationdata.content=[];
    this.locationservice.getLocation(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide()
      this.locationdata = data;
  
    }, error => {
      this._global.alert({type:'alert',message:error.error.message});
      this.spinner.hide();

    })
  }
  getRecentLocation() {
    this.spinner.show();
    this.locationdata.content=[];
    this.locationservice.getLocationrecentvisit(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide()
  
      this.locationdata = data;

    }, error => {
      this.spinner.hide();
      this.message = error.error.message
      this._global.alert({type:'alert',message:error.error.message});
    })
  }

  editLocationModel(locres: LocationResponse) {
    this.localres = locres
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.locationservice.getLocationById(this.localres.locationId).subscribe(data => {
      this.spinner.hide()
      this.localres = data;
    }, error => {
      this._global.alert({type:'alert',message:error.error.message});
      this.spinner.hide();

    });
    this.creatLocation.show();


  }
  cancelLocationData() {
    this.isEditMode = false;
    this.creatLocation.hide()
  }
  onLocationCreated($event: LocationResponse) {

    if (!this.isEditMode) {
      this.locationdata.content.unshift($event);
    } else {
      let index = 0;
      this.locationdata.content.forEach(element => {
        if (element.locationId == $event.locationId) {
          this.locationdata[index] = $event;
        } else {
          index++;
        }
      });
    }

  }
  createLocationModel() {
    this.isModelOpen = true;
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.creatLocation.show()
  }
  changeOwnerDetails() {
    let totalSelection = 0;
    for (let c = 0; c < this.locationdata.content.length; c++) {
      if (this.locationdata.content[c].selectionChecked) {
        totalSelection++;
      }
    }
    this.getUserForlist();
    this.changeOwner.show();
  }
  getUserForlist() {
    this._userforlist.getUserForListBT().subscribe(data => this.userList = data);
  }
  updateChangeOwner() {
    this.spinner.show();
    let chReq = new ChangeOwnerRequest();
    chReq.ids = [];
    chReq.userEmailId = this.changeowneruesrEmail;
    for (let c = 0; c < this.locationdata.content.length; c++) {
      if (this.locationdata.content[c].selectionChecked) {
        chReq.ids.push(this.locationdata.content[c].locationId);
      }
    }
    this.locationservice.changeOwner(chReq).subscribe(data => {
      this.changeOwner.hide();
      this._global.alert({type:'',message:"Updated Successfully"});
      for (let c = 0; c < this.locationdata.content.length; c++) {
        this.locationdata.content[c].selectionChecked = false;
      }
      this.getFilterList();
      this.changeowneruesrEmail = '';
      this.spinner.hide();
    }, err => {this.spinner.hide()
      this._global.alert({type:'alert',message:err.error.message});
    }
    );
  }
  // onchecked($event){
  //   console.log($event.target.checked);
  
  changeDropdown(tabname) {
    this._router.navigate(['/bat_warehouse'], { queryParams: { page: 0, tab: tabname } });
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
    if(pagenumber!=null)
    {
  if(this.locationdata.totalPages< pagenumber){
    this.message="Page Count Should Be Less Then Total Page";
    this._global.alert({type:'alert',message:this.message});
  }
  else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getLocation();
}
}
else{
  this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
 }
}



searchLoc() {
   
  if(this.advancesearch.legalEntityName=='' && this.advancesearch.locationName==''
   && this.advancesearch.locationTypeName=='' && this.advancesearch.owner=='' &&this.advancesearch.parentLocationName==''){
   this.message="Please Enter Valid Key For Search";
   this._global.alert({type:'alert',message:this.message});
  }
  else{
    this.spinner.show();
    this.locationservice.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.locationdata.content = data;
      this.page = 0;
      this.size = data.length;
      this.first = true;
      this.locationdata.first = true;
      this.locationdata.last = true;
      this.last = true;
    });
  }

}
resetsearch(){
  this.advancesearch.locationName= ''
  this.advancesearch.locationTypeName= ''
 this.advancesearch.legalEntityName=''
 this.advancesearch.owner= ''
 this.advancesearch. parentLocationName= ''
this.getLocation();
}
getLocationTypeForList(){
  // this.spinner.show();
  this.locationservice.locationTypeForlist().subscribe(data=>{
    // this.spinner.hide();
    this.locationtypeList=data;
  })
}
}
