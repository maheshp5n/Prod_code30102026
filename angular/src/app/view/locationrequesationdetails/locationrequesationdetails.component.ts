import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { GlobalserviceService } from '../../globalservice.service';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-locationrequesationdetails',
  templateUrl: './locationrequesationdetails.component.html',
  styleUrls: ['./locationrequesationdetails.component.scss']
})
export class LocationrequesationdetailsComponent implements OnInit {

  locationId;
  location;
  requesation;
  queryparam = {
    page: 0,
    size: 20,
    sort: 'desc',
    sortc: 'locationProductRequestManualId',
    clm: 1,
    typ: 'sent'
  };
  tableHeader = [{
    name: 'S.No',
    sortc: 'locationProductRequestManualId'
  },
  {
    name: 'Product Request Number',
    sortc: 'locationProductRequestManualId'
  },
  {
    name: 'Source Warehouse',
    sortc: 'sourceLocation.locationName'
  },
  {
    name: 'Destination Warehouse',
    sortc: 'destinationLocation.locationName'
  },
  {
    name: 'Expected Date',
    sortc: 'needByDateTime'
  },
  {
    name: 'Quantity Required',
    sortc: 'totalQuantityRequired'
  },
  {
    name: 'Quantity Issued',
    sortc: 'totalQuantityIssued'
  },
  {
    name: 'Quantity Received',
    sortc: 'totalQuantityReceived'
  }
  ];
  SendReqAdvancesearch = {
    productRequestNumber: '',
    sourceWarehouse: '',
    destinationWarehouse: '',
    expectedDate: ''
  
  };
  
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(
    private _activatedRouter: ActivatedRoute,
    private _location: LocationService,
    private _ngxSpinner: NgxSpinnerService,
    private global:GlobalserviceService,
    private datePipe:DatePipe
  ) {
    this._activatedRouter.params.subscribe(pram => {
      this.locationId = pram['locationid'];
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
      if (param['typ'] != null && param['typ'] != undefined) {
        this.queryparam.typ = param['typ'];
      }

      this.getrequisition();
    });
  }

  ngOnInit() {
    this.getLocationDetails();
  }

  getLocationDetails() {
    this._location.getLocationById(this.locationId).subscribe
      (data => {
        this.location = data;
      });
  }
  getrequisition() {
    this._ngxSpinner.show();
    if (this.queryparam.typ == 'sent') {
      this._location.getSendproductRequests(this.locationId, `page=${this.queryparam.page}&size=${this.queryparam.size}&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
        this._ngxSpinner.hide();
        this.requesation = data;
      }, error => this._ngxSpinner.hide());
    }
    else {
      this._location.getReceivedRequests(this.locationId, `page=${this.queryparam.page}&size=${this.queryparam.size}&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
        this._ngxSpinner.hide();
        this.requesation = data;
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
    this.getrequisition();
  }
  SearchSendReq(){
    if(
      this.SendReqAdvancesearch.productRequestNumber=='' &&
      this.SendReqAdvancesearch.sourceWarehouse == '' &&
      this.SendReqAdvancesearch.destinationWarehouse==''&&
      this.SendReqAdvancesearch.expectedDate==''
    ){
      this.global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    }
    else{ 
      if(this.SendReqAdvancesearch.expectedDate!=''){
        this.SendReqAdvancesearch.expectedDate= this.datePipe.transform(this.SendReqAdvancesearch.expectedDate, 'yyyy-MM-dd');
      }
      if(this.queryparam.typ == 'sent')
     {
       this._ngxSpinner.show();
    this._location.getSendReqSearchResult(this.SendReqAdvancesearch,this.locationId).subscribe(data=>{
      this._ngxSpinner.hide();
      this.requesation.content = data;
    },error=>{
      this._ngxSpinner.hide();
      this.global.alert({ type: 'alert', message:error.error.message });
    })
  }else{
    this._ngxSpinner.show();
    this._location.getReciveReqSearchResult(this.SendReqAdvancesearch,this.locationId).subscribe(data=>{
      this._ngxSpinner.hide();
      this.requesation.content = data;
    },error=>{
      this._ngxSpinner.hide();
      this.global.alert({ type: 'alert', message:error.error.message });
    })

  }
  }
  }
  locSearchSenReqreset(){
    this.SendReqAdvancesearch.productRequestNumber= ''
    this.SendReqAdvancesearch.sourceWarehouse = ''
    this.SendReqAdvancesearch.destinationWarehouse= ''
    this.SendReqAdvancesearch.expectedDate=''

  }

}
