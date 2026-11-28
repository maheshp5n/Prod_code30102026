import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { AccountsForList, CirclesRequest, CirclesResponse, Page, Zonedetails } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { ZoneService } from '../../../services/zone.service';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtCirclesRequest, BtCirclesResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-circle',
  templateUrl: './bat-circle.component.html',
  styleUrls: ['./bat-circle.component.css']
})
export class BatCircleComponent implements OnInit {

  message: string;
  circleList: Page<BtCirclesResponse> = new Page<BtCirclesResponse>();
  searchitem: string;
  isSelected = false;
  isEditMode: boolean = false;
  heading: string;
  CREATE_CIRCLE_HEADING: string = 'New Circle';
  EDIT_CIRCLE_HEADING: string = 'Edit Circle';
  page: number = 0;
  order: string = 'createdDate'; // first column variable
  reverse: boolean = false;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  userforlist;
  recentlyView: boolean = false;
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  recently: boolean = false;
  ngClassRelated: string = 'dropdown-item';
  circleName;
  newCirclesRequest: BtCirclesRequest = new BtCirclesRequest();
  circleListById: BtCirclesResponse = new BtCirclesResponse();
  newcircles: BtCirclesRequest = new BtCirclesRequest();
  accountForList: Array<AccountsForList> = [];
  sortColumn: number = 7;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false, false];
  // popupNgClass: string = 'alert alert-danger center center-block';
  i: number;
  _cFlag; string;
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
      sortColumnName: 'circleName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Zone',
      sortColumnName: 'zone',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Circle Manager (Battery)',
      sortColumnName: 'circleManagerBattry.firstName',
      icon: 'fa fa-angle-down pull-right'
    },
    // {
    //   id: 4,
    //   name: 'Circle Manager (Power)',
    //   sortColumnName: 'circleManagerPower.firstName',
    //   icon: 'fa fa-angle-down pull-right'
    // },
    // {
    //   id: 5,
    //   name: 'Circle Manager (EV)',
    //   sortColumnName: 'circleManagerEv.firstName',
    //   icon: 'fa fa-angle-down pull-right'
    // },
    {
      id: 4,
      name: 'Country',
      sortColumnName: 'country.countryName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Legal Entity',
      sortColumnName: 'serviceCenterBattery.legalEntityName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    }
   
  ];

  //done

  @ViewChild('createZoneModel') createZoneModel: ModalDirective;
  @ViewChild('GradeModel') GradeModel: ModalDirective;
  @ViewChild('createCircleModel') createCircleModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private circlesService: BtcircleService,
    private zoneservice:ZoneService,
    private loginService: Loginservices, private _activatedRoute: ActivatedRoute,private _global: GlobalserviceService) {
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
    // this.dropdownChange('related');
    // this.setOrder('createdDate', 6);
    this.circleList.content=[];
    this.getZoneForList();
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_circle').subscribe(data =>{
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

      this.getCircles();
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
        this.getCircles();
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
        this.getCircles();
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
  onClickEdit(y: BtCirclesResponse, i: string) {
    this.spinner.show();
    // this.newcircles = y;
    this.newcircles.circleName = y.circleName;
    this.newcircles.countryCode = y.countryCode;
    this.newcircles.countryCode = y.countryCode;
    this.newcircles.legalEntity = y.batteryLegalEntityId;
    // this.newcircles.batteryLegalEntityName = y.batteryLegalEntityName;
    // this.newcircles.batteryLegalEntityId = y.batteryLegalEntityId
    // this.newcircles.circleManagerBattryId = y.circleManagerBattryId;
    // this.newcircles.circleManagerPowerId = y.circleManagerPowerId;
    // this.newcircles.circleManagerEv = y.circleManagerEvId;
    this.newcircles.zoneName = y.zoneBattery;

    this.circlesService.getCircleById(i).subscribe(data => {
      this.circleListById = data;
      // this.circleName = i;
      this.newcircles.circleName = data.circleName;
      this.newcircles.countryCode = data.countryCode;
      this.newcircles.countryCode = data.countryCode;
      this.newcircles.legalEntity = data.batteryLegalEntityId;

      // this.newcircles.batteryLegalEntityName = data.batteryLegalEntityName;
      // this.newcircles.batteryLegalEntityId = data.batteryLegalEntityId;
      // this.newcircles.circleManagerBattryId = data.circleManagerBattryId;
      // this.newcircles.circleManagerPowerId = data.circleManagerPowerId;
      // this.newcircles.circleManagerEv = data.circleManagerEvId;
      this.newcircles.zoneName = data.zoneBattery;
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CIRCLE_HEADING;
    this.createCircleModel.show();
    console.log(this.newcircles)
  }

  showCreateCircleView() {
    this.newcircles = new BtCirclesRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_CIRCLE_HEADING;
    this.createCircleModel.show();
  }

  getCircles() {
    this.spinner.show();
    this.circleList.content=[];
    this.circlesService.getCircles(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.circleList = data;
      // this.first = data['first'];
      // this.last = data['last'];
      // this.size = data['size'];
      // this.serialNumber = (((this.page) * this.size));
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }


  cancelData() {
    this.createCircleModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_CIRCLE_HEADING;
    this.newcircles = new BtCirclesRequest();
  }

  onCircleCreated($event: BtCirclesResponse) {
    this.getCircles();

    // if (!this.isEditMode) {
    //   this.circleList.content.unshift($event);
    // } else {
    //   let index = 0;
    //   this.circleList.content.forEach(element => {
    //     if (element.circleName === $event.circleName) {
    //       this.circleList[index] = $event;
    //     } else {
    //       index++;
    //     }
    //   });
    // }
   
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  // }
  pagenumber:any=null;
  jumpPage(pagenumber){
    // console.log(this.assetList.pa)
    if(pagenumber==null || pagenumber=='' || pagenumber==0)
   {
    this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
   }
    else if(this.circleList.totalPages< pagenumber ){
      this.message="Page Count Should Be Less Then Total Page";
      this._global.alert({ type: 'alert', message: this.message });

    }
  
else{
  this.circleList.number=this.pagenumber-1;

    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.circleList.content=[];
    this.circlesService.getCircles(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.circleList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
}
// else{
//   this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
//  }
  
  advancesearch = {
    circleName: '',
    zoneName: '',
    legalEntityName:'',
    circleManager:''
  };
  searchCircle() {
    if(this.advancesearch.circleName=='' && this.advancesearch.circleManager==''&&
    this.advancesearch.legalEntityName=='' && this.advancesearch.zoneName==''){
      this.message="Enter Key For Search";
      this._global.alert({ type: 'alert', message: this.message });
    }
    else{
      this.spinner.show();
    this.circlesService.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.circleList.content = data;
      this.circleList.first = true;
      this.circleList.last = true;
      this.page = 0;
      this.size = data.length;
      this.first = true;
      this.last = true;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
      
    });


    }
      }
      resetsearch(){

       this.advancesearch.circleName= ''
       this.advancesearch.zoneName= ''
       this.advancesearch.legalEntityName=''
       this.advancesearch.circleManager='' 
        this.getFilterList();
      }
     zoneList:Array<Zonedetails>=[]
      getZoneForList(){
        this.zoneList=[];
        this.circlesService.getBatteryZoneForList(1).subscribe(data=>{
        //  this.zoneList=data;
        const temp=[];
        for(let i=0;i<data.length;i++)
        {
        let lis=new Zonedetails;
        lis.zoneName=data[i]
        temp.push(lis);
        this.zoneList=temp;
        }
        })
      }

      numberOnly(event): boolean {
        const charCode = (event.which) ? event.which : event.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
          return false;
        }
        return true;
    
      }
}
