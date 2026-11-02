import { Component, Input, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { LocationForList, LocationResponse, ProductsForList } from '../../../services/model';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BatNewProductRequest, BtProductRequestResponse, ProductReqItem} from '../../bt_model';
import {BtwarehouseService} from '../../bat_services/btwarehouse.service';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bt-newproductrequestdirective',
  templateUrl: './bt-newproductrequestdirective.component.html',
  styleUrls: ['./bt-newproductrequestdirective.component.css']
})
export class BtNewproductrequestdirectiveComponent implements OnInit {
  _location: LocationResponse = new LocationResponse();
  dateCustomClasses: DatepickerDateCustomClasses[]; 
  locationId : number;
  today = new Date();
  destinationLocationName : string;
  productReqId : number;

  @Input()
  public set destLoc(location: BtProductRequestResponse) {
    if (location) {
       this.editProdReq.destinationLocationId = location.productRequestDestinationLocationId;
        this.destinationLocationName = location.productRequestDestinationLocationName;
        this.editProdReq.productRequestId = location.productRequestId; 
        this.editProdReq.sourceLocationId = location.productRequestSourceLocationId;  
        this.editProdReq.needByTime = location.productRequestNeedByDateTime;
        this.editProdReq.description = location.productRequestDescription;
    }
  }
  
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onUpdatePr: EventEmitter<BtProductRequestResponse> = new EventEmitter<BtProductRequestResponse>();
  productItemList: Array<ProductsForList> = [];
  locationForList: Array<LocationForList> = [];
  
  editProdReq : BatNewProductRequest = new BatNewProductRequest();
  partcodeDescription: string;
  prItemReq : ProductReqItem = new ProductReqItem();

  alertMessage = { alertClass: 'alert alert-success center center-block', alertMessage: '' };

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  constructor(
    private _btWareHouseservice: BtwarehouseService,
    private _spinnerService: NgxSpinnerService,
    private _btProdReqService : BtproductrequestService,
    private _global : GlobalserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getSiblingsLocation();
  }

  getSiblingsLocation(){
    this._spinnerService.show();
    this._btWareHouseservice.getLocationParentSiblingAndChilds().subscribe(data=>{
      this._spinnerService.hide();
      this.locationForList=data;
    },error=>this._spinnerService.hide());
  }
  UpdateProductRequest(){
    if(this.editProdReq.sourceLocationId == null|| this.editProdReq.sourceLocationId == undefined){  
      this._global.alert({ type: 'alert', message:'Source Warehouse Details Not Found'});
      return; 
    }
    else if(this.editProdReq.needByTime == null || this.editProdReq.needByTime == undefined){
     this._global.alert({ type: 'alert', message:'Expected Date Not Found'});
      return; 
    }
    
    else{
      this._spinnerService.show();
      this._btProdReqService.updateBtProductReq(this.editProdReq,this.editProdReq.productRequestId).subscribe(data=>{      
        this._global.alert({ type: '', message:'Updated Successfully'});
        this.onUpdatePr.emit(data);       
        this.onCancle.emit('canled');    
        this._spinnerService.hide();
      },error=>{ 
        this._spinnerService.hide();         
        this._global.alert({ type: 'alert', message: error.error.message});
      })
    }

  }
  cancled() {
    this.onCancle.emit('canled');
  }

}
