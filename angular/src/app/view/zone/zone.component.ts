import { Component, OnInit, ViewChild } from '@angular/core';
import { Page, Zonedetails } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ZoneService } from '../../services/zone.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-zone',
  templateUrl: './zone.component.html',
  styleUrls: ['./zone.component.css']
})
export class ZoneComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createZoneModel') createZoneModel: ModalDirective;

  isEditMode: boolean = false;
  heading: string;
  CREATE_PRODUCT_HEADING: string = 'New Zone Details';
  EDIT_PRODUCT_HEADING: string = 'Edit Zone Details';
  zoneList: Page<Zonedetails> = new Page<Zonedetails>();
  isSelected = false;
  order: string = 'createdDate'; // first column variable
  reverse: boolean = false;
  flag: boolean;
  serialNumber: number;
  searchitem: string = '';
  ngClassAll: string;
  ngClassRelated: string = 'fa fa-check';
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  message: string;
  productId;
  sortColumn: number = 3;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false];
  columnSort: Array<boolean> = [false, false, false];
  productById: Zonedetails = new Zonedetails();
  newZone;
  loginData: any;
  domainId: any;
  createByUrl:string='';

  pindtselection:boolean=false;
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
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: ' Zone',
      sortColumnName: 'zoneName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Create By',
      sortColumnName: 'createdBy.firstName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    }
   
  ];

  //done
  _cFlag:string;
  constructor(private pagefilterservice:PagefilterService, private loginservice: Loginservices, private _activatedRoute:ActivatedRoute,private spinner: NgxSpinnerService, private _zoneService: ZoneService, private loginService: Loginservices) {
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
    this.getLoggedInUser();
    // this.setOrder('createdDate', 3);
  }
   
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId'];

  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('zone').subscribe(data => {
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

      this.getZone();
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
        this.getZone();
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
        this.getZone();
      } else {
        tbl.icon = '';
      }
    });
  }

  getZone() {
    this.spinner.show();
    this.zoneList.content=[];
    this._zoneService.getZone(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
        this.spinner.hide();
      this.zoneList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));

    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
  }

 
  // SORT ENDS

  // PAGINATION
  // setPagePrevious(event: any) {
  //   event.preventDefault();
  //   this.page = this.page - 1;
  //   this.getZone(this.sortColumnName, this.sortOrder);
  // }

  // setPage(event: any) {
  //   event.preventDefault(); this.getZone(this.sortColumnName, this.sortOrder);
  // }

  // setPageNext(event: any) {
  //   event.preventDefault();
  //   this.page = this.page + 1;
  //   this.getZone(this.sortColumnName, this.sortOrder);
  // }

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

  // PAGINATION ENDS

  isValidNumber(n: string): boolean {
    const numberPattern = '[0-9]';
    if (n.match(numberPattern)) {
      return true;
    } else {
      return false;
    }
  }



  showCreateZoneView() {
    this.newZone = new Zonedetails();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.createZoneModel.show();
  }

  cancelData() {
    this.createZoneModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.newZone = new Zonedetails();
  }

  onZoneCreated($event: Zonedetails) {
    if (!this.isEditMode) {
      this.zoneList.content.unshift($event);
    } else {
      let index = 0;
      this.zoneList.content.forEach(element => {
        if (element.zoneName === $event.zoneName) {
          this.zoneList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.zoneList.totalPages< pagenumber){
    this.message="Enter Valid Input";
    this.popupSuccessModel.show();
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getZone();
}
}
}
