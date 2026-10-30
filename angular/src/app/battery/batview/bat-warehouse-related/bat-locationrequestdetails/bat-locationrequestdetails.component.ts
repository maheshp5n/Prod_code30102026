import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { BtwarehouseService } from '../../../bat_services/btwarehouse.service';

@Component({
  selector: 'app-bat-locationrequestdetails',
  templateUrl: './bat-locationrequestdetails.component.html',
  styleUrls: ['./bat-locationrequestdetails.component.css']
})
export class BatLocationrequestdetailsComponent implements OnInit {

  locationId;
  location;
  requesation;
  queryparam = {
    page: 0,
    size: 20,
    sort: 'desc',
    sortc: 'id',
    clm: 1,
    typ: 'sent'
  };
  tableHeader = [{
    name: 'S.No',
    sortc: 'id'
  },
  {
    name: 'Product Request Number',
    sortc: 'id'
  },
  {
    name: 'Source Warehouse',
    sortc: 'sloc.location_name'
  },
  {
    name: 'Destination Warehouse',
    sortc: 'dloc.location_name'
  },
  {
    name: 'Expected Date',
    sortc: 'need_by_date_time'
  },
  {
    name: 'Quantity Required',
    sortc: 'total_quantity_required'
  },
  {
    name: 'Quantity Issued',
    sortc: 'total_quantity_issued'
  },
  {
    name: 'Quantity Received',
    sortc: 'total_quantity_received'
  }
  ];
  SendReqAdvancesearch = {
    productRequestManualId:'',
    productRequestStatus:'',
    productRequestFromDate:'',
    productRequestSoruceLocationName:'',
    productRequestDestinationLocationName:''
  
  };
  
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(
    private _activatedRouter: ActivatedRoute,
    private _location: BtwarehouseService,
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
      this.SendReqAdvancesearch.productRequestDestinationLocationName=='' &&
      this.SendReqAdvancesearch.productRequestFromDate == '' &&
      this.SendReqAdvancesearch.productRequestManualId==''&&
      this.SendReqAdvancesearch.productRequestStatus==''&&
      this.SendReqAdvancesearch.productRequestSoruceLocationName==''
    ){
      this.global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    }
    else{ 
      if(this.SendReqAdvancesearch.productRequestFromDate!=''){
        this.SendReqAdvancesearch.productRequestFromDate= this.datePipe.transform(this.SendReqAdvancesearch.productRequestFromDate, 'yyyy-MM-dd');
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
    this.SendReqAdvancesearch.productRequestDestinationLocationName= ''
    this.SendReqAdvancesearch.productRequestFromDate = ''
    this.SendReqAdvancesearch.productRequestManualId= ''
    this.SendReqAdvancesearch.productRequestSoruceLocationName=''
    this.SendReqAdvancesearch.productRequestStatus=''

  }

}
