import { NgxSpinnerService } from 'ngx-spinner';
import { LocationService } from './../../services/location.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { LocationType } from '../../services/model';
import { BtwarehouseService } from '../../battery/bat_services/btwarehouse.service';

@Component({
  selector: 'app-locationchilddetails',
  templateUrl: './locationchilddetails.component.html',
  styleUrls: ['./locationchilddetails.component.scss']
})
export class LocationchilddetailsComponent implements OnInit {
  LocaChildadvancesearch = {
    locationName: '',
    locationTypeName: '',
    legalEntityName: '',
    owner: '',
    plantcode: '',
    mobileWarehoue:'',
    inventoryWarehouse:''
  
  };

  btpath:boolean=false;

  batwarehouse:string;
  locationId;
  location;
  childLocations;
  queryparam = {
    page: 0,
    size: 20,
    sort: 'desc',
    sortc: 'locationName',
    clm: 1
  };
  tableHeader = [{
    name: 'Sl.No',
    sortc: 'locationProductItemId'
  },
  {
    name: 'Warehouse Name',
    sortc: 'locationName'
  },
  {
    name: 'Type',
    sortc: 'locationType.locationTypeName'
  },
  {
    name: 'Mobile warehouse',
    sortc: 'mobileLocation'
  },
  {
    name: 'Inventory warehouse',
    sortc: 'inventoryLocation'
  },
  {
    name: 'Owner',
    sortc: 'owner.firstName'
  }, {
    name: 'Legal Entity',
    sortc: 'legalEntity.legalEntityName'
  }, {
    name: 'Plant',
    sortc: 'plant'
  }
  ];
  constructor(
    private _activatedRouter: ActivatedRoute,
    private _location: LocationService, private btLocation:BtwarehouseService,
    private _ngxSpinner: NgxSpinnerService
  ) {

    this._activatedRouter.params.subscribe(pram => {
      this.locationId = pram['locationid'];
      this.batwarehouse = pram['battery'];
      console.log(this.batwarehouse);
    });
    this._activatedRouter.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.queryparam.page = Number(param['page']);
      }
      if (param['size'] != null && param['size'] != undefined) {
        this.queryparam.size = Number(param['size']);
      }
      if (param['sort'] != null && param['sort'] != undefined) {
        this.queryparam.sort = param['sort'];
      }
      if (param['sortc'] != null && param['sortc'] != undefined) {
        this.queryparam.sortc = param['sortc'];
      }
      if (param['clm'] != null && param['clm'] != undefined) {
        this.queryparam.clm = param['clm'];
      }
      this.getChildLocation();
    });
    if(this.batwarehouse!="battery")
    {
      this.btpath=false;
    }
    else
    {
      this.btpath=true;

    }

  }

  ngOnInit() {
    this.getLocationDetails();
    this.getLocationTypeForList();
  }

  getChildLocation() {
    console.log(this.batwarehouse)
    if(this.batwarehouse!="battery")
    {
    this._ngxSpinner.show();
    this._location.getChildLocations(this.locationId, `page=${this.queryparam.page}&size=${this.queryparam.size}&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
      this._ngxSpinner.hide();
      this.childLocations = data;
    }, error => this._ngxSpinner.hide());
  }
  else
  {
    this._ngxSpinner.show();
    this.btLocation.getChildLocations(this.locationId, `page=${this.queryparam.page}&size=${this.queryparam.size}&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
      this._ngxSpinner.hide();
      this.childLocations = data;
    }, error => this._ngxSpinner.hide());
  }
  }
  sortOrder(i) {
    if (i == this.queryparam.clm) {
      this.queryparam.sort = this.queryparam.sort == 'desc' ? 'asc' : 'desc';
    }
    else {
      this.queryparam.clm = i;
      this.queryparam.sortc = this.tableHeader[i].sortc;
    }
    this.getChildLocation();
  }
  getLocationDetails() {
    if(this.batwarehouse!="battery")
    {
    this._location.getLocationById(this.locationId).subscribe
      (data => {
        this.location = data;
      });
    }
    else
    {
      this.btLocation.getLocationById(this.locationId).subscribe
      (data => {
        this.location = data;
      });
    }
  }
  locationtypeList:Array<LocationType>=[];
  searchChildLoc() {
   
    if(this.LocaChildadvancesearch.legalEntityName=='' && this.LocaChildadvancesearch.locationName==''
     && this.LocaChildadvancesearch.locationTypeName=='' && this.LocaChildadvancesearch.owner=='' &&
     this.LocaChildadvancesearch.plantcode==''  && this.LocaChildadvancesearch.inventoryWarehouse=='' &&
     this.LocaChildadvancesearch.mobileWarehoue==''){
    //  this.message="Please Enter valid Key for Search";
    //  this.popupSuccessModel.show();
    }
    else{
      if(this.batwarehouse!="battery")
      {
      this._ngxSpinner.show();
      this._location.getChildLocSearchResult(this.LocaChildadvancesearch,this.locationId).subscribe(data => {
        this._ngxSpinner.hide();
        this.childLocations.content = data;
        // this.page = 0;
        // this.size = data.length;
        // this.first = true;
        // this.last = true;
      },error=>{
        this._ngxSpinner.hide();
        // this.message=error.error.message;
        // this.popupSuccessModel.show();
      });
    }
    else
    {
      this._ngxSpinner.show();
      this.btLocation.getChildLocSearchResult(this.LocaChildadvancesearch,this.locationId).subscribe(data => {
        this._ngxSpinner.hide();
        this.childLocations.content = data;
        // this.page = 0;
        // this.size = data.length;
        // this.first = true;
        // this.last = true;
      },error=>{
        this._ngxSpinner.hide();
        // this.message=error.error.message;
        // this.popupSuccessModel.show();
      });
    }

    }
  
  }
  locChildresetsearch(){
    this.LocaChildadvancesearch.locationName= ''
    this.LocaChildadvancesearch.locationTypeName= ''
   this.LocaChildadvancesearch.legalEntityName=''
   this.LocaChildadvancesearch.owner= ''
   this.LocaChildadvancesearch.plantcode= ''
   this.LocaChildadvancesearch.inventoryWarehouse=''
   this.LocaChildadvancesearch.mobileWarehoue=''
  //  this.LocaChildadvancesearch.
  
  }
  getLocationTypeForList(){
    this._ngxSpinner.show();
    this._location.locationTypeForlist().subscribe(data=>{
      this._ngxSpinner.hide();
      this.locationtypeList=data;
    })
  }

}
