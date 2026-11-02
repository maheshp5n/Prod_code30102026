import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AssetsResponse, BatteryFSRResponse, Page } from '../../services/model';
import { AssetsService } from '../../services/assets.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-battery-fsrcbmsli-lonbattery',
  templateUrl: './battery-fsrcbmsli-lonbattery.component.html',
  styleUrls: ['./battery-fsrcbmsli-lonbattery.component.scss']
})
export class BatteryFSRCBMSLiLonbatteryComponent implements OnInit {

  public message: string = '';
  @ViewChild('createBFSRModel') createBFSRModel: ModalDirective;
 @ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  assetList: Page<BatteryFSRResponse> =new Page<BatteryFSRResponse>()
  searchitem: string = '';

  recentlyView: boolean = false;
  ngClassAll: string='dropdown-item dropdown-item-checked';
  related: boolean = false;
  // all: boolean = true;
  ngClassRelated:string;
  BFSRres: BatteryFSRResponse = new BatteryFSRResponse();
  public page: number = 0;
  pages: Array<number>;
  totalPages;
  first: boolean;
  last: boolean;
  serialNum: number;
  size: number;
  i: number;
  
  pindtselection = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Battery FSR CBMS & Li-lon Battery';
  EDIT_ACCOUNT_HEADING: string = 'Edit Battery FSR CBMS & Li-lon Battery';

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false];
  columnSort: Array<boolean> = [false, false,true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  serialNumber: number;
  id:number;
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
          name: 'FSR CBMS & Li Lon Battery Name',
          sortColumnName: 'manualId',
          icon: 'fa fa-angle-down pull-right'
        },
        {
          id: 2,
          name: 'Created Date',
          sortColumnName: 'createdDate',
          icon: 'fa fa-angle-down pull-right'
        },
       
      ];
    
      //done

_cFlag:string;
  constructor(private pagefilterservice:PagefilterService,private loginservice:Loginservices,private spinner: NgxSpinnerService, private _assetsService: AssetsService,private BFSRservice:BatrryFSRService,private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.params.subscribe(pram => {
      this.id =pram['id']; 
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
    // this.AllCase = orderPipe.transform(this.AllCase, this.order);
   }
   rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  ngOnInit() {
    // this.getAllBattery(this.sortColumnName, this.sortOrder);
    
   
  }

  getAllBattery() {
    this.spinner.show();
    this.assetList.content=[];
    this.BFSRservice.getBatrryFSRAllList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.assetList = data;
      if (data != null) {
        this.spinner.hide();
      }

      this.pages = new Array(data['totalPages']);
      this.totalPages = data['totalPages'];
      this.first = data['first'];
      this.last = data['last'];
      this.size = data['size'];
      this.serialNum = (((this.page) * this.size));
      
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    }
    );
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getAllBattery();
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
        this.getAllBattery();
      } else {
        tbl.icon = '';
      }
    });
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('batteryFSRCBMS').subscribe(data =>{
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

      this.getAllBattery();
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

  showCreateAccountView() {
    this.BFSRres = new BatteryFSRResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createBFSRModel.show();
  }

  createEditModelshow(btr: BatteryFSRResponse) {
    this.BFSRres=btr;
    //let ob = a.serialNumber;
    this.spinner.show();
    this.BFSRservice.getDetailById(btr.id).subscribe(data => {
      this.BFSRres = data;
        this.spinner.hide();
      
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });

    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createBFSRModel.show();
  }

  cancelBFSRModelhide() {
    this.BFSRres = new BatteryFSRResponse();
    this.isEditMode = false;
    this.createBFSRModel.hide();
  }

  onBFSRCreated($event: BatteryFSRResponse) {
    if (!this.isEditMode) {
      this.assetList.content.unshift($event);
    } else {
      let index = 0;
      this.assetList.content.forEach(element => {
        // console.log('element',element);
        if (element.id == $event.id) {
          this.assetList[index] = $event;
          //console.log('index',index);
        } else {
          index++;
        }
      });
    }
  }

 

}
