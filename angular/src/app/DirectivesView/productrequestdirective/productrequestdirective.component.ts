import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { LocationResponse, LocationProductItemForList, LocationForList, ProductsForList, LPRequest, LPItemRequest } from '../../services/model';
import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductsService } from '../../services/products.services';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';

@Component({
  selector: 'app-productrequestdirective',
  templateUrl: './productrequestdirective.component.html',
  styleUrls: ['./productrequestdirective.component.scss']
})
export class ProductrequestdirectiveComponent implements OnInit {
  _location: LocationResponse = new LocationResponse();
  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();
  @Input()
  public set loc(location: LocationResponse) {
    if (location) {
      this._location = location;
      this._lprequest.destinationLocationId = location.locationId;
      if (location.parentLocationName != null && location.parentLocationName != undefined) {
        this._lprequest.sourceLocationId = Number(location.parentLocationId);
      }
    }
  }
  productItemList: Array<ProductsForList> = [];
  locationForList: Array<LocationForList> = [];
  _lprequest: LPRequest = new LPRequest();
  partcodeDescription: string;
  _lpitems: LPItemRequest = new LPItemRequest();
  alertMessage = { alertClass: 'alert alert-success center center-block', alertMessage: '' };
  @Output()
  onCancled: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onSave: EventEmitter<LPRequest> = new EventEmitter();

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  public get loc() {
    return this._location;
  }

  avilableStockAtSource: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  avilabStockAtU: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  constructor(
    private _locationservice: LocationService,
    private _spinnerService: NgxSpinnerService,
    private _productService: ProductsService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    //this.getLocationProductList();
    //this.getLocations();
    this.getSiblingsLocation();
  }
  prolist:Array<ProductsForList>=[];
  getLocationProductList() {
    this._spinnerService.show();
    this._productService.getProductForList().subscribe(data => {
      this._spinnerService.hide();
      // this.productItemList = data;
      data.forEach(e=>{
        let pro=new ProductsForList();
      
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
       pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this.prolist.push(pro);
      })
      this.productItemList=this.prolist;
    }, err => this._spinnerService.hide());
  }

  getSiblingsLocation(){
    this._spinnerService.show();
    this._locationservice.getLocationParentSiblingAndChilds().subscribe(data=>{
      this._spinnerService.hide();
      this.locationForList=data;
    },error=>this._spinnerService.hide());
  }

  getLocations() {
    this._spinnerService.show();
    this._locationservice.locationForlist().subscribe(data => {
      this.locationForList = data;
      this._spinnerService.hide();
    }, err => this._spinnerService.hide());
  }
  addItemToList() {
    console.log(this._lpitems.productPartCode);

    if (this._lpitems.productPartCode == null || this._lpitems.productPartCode == undefined || this._lpitems.productPartCode == '') {
      this.showAlert('Please select partcode');
      return;
    }
    if (this._lpitems.requestQuantity == null || this._lpitems.requestQuantity == undefined || this._lpitems.requestQuantity == '0' || this._lpitems.requestQuantity == '') {
      this.showAlert('Please provide valid quantity');
      return;
    }
    let tempitem = new LPItemRequest();
    tempitem.productPartCode = this._lpitems.productPartCode;
    tempitem.requestQuantity = this._lpitems.requestQuantity;

    if (this._lprequest.lileItems == null || this._lprequest.lileItems == undefined) {
      this._lprequest.lileItems = [];
    }
    let alreadyExist = false;
    for (let c = 0; c < this._lprequest.lileItems.length; c++) {
      if (this._lprequest.lileItems[c].productPartCode == tempitem.productPartCode) {
        alreadyExist = true;
        break;
      }
    }
    if (alreadyExist) {
      this.showAlert(`Part code ${tempitem.productPartCode} already exists,Please select different`);
      return;
    }
    this._lprequest.lileItems.push(tempitem);
    this._lpitems.productPartCode = null;
    this._lpitems.requestQuantity = null;
    this.partcodeDescription=null;
  }
  removeItem(indexNumber) {
    let counter = this._lprequest.lileItems.length
    let temp = [];
    for (let i = 0; i < counter; i++) {
      if (i != indexNumber) {
        temp.push(this._lprequest.lileItems[i]);
      }
    }
    this._lprequest.lileItems = temp;
  }
  saveProductRequest() {
    console.log(this._lprequest.lileItems)
    if(this._lprequest.lileItems==undefined || this._lprequest.lileItems.length==0 || this._lprequest.lileItems==null){
      this.showAlert('Please Select Part Code');
      return;
    }
    if(this._lprequest)
    if (this._lprequest.destinationLocationId == null || this._lprequest.destinationLocationId == undefined || this._lprequest.destinationLocationId == 0) {
      this.showAlert('Destination Warehouse Details not found');
      return;
    }
    if (this._lprequest.sourceLocationId == null || this._lprequest.sourceLocationId == undefined || this._lprequest.sourceLocationId == 0) {
      this.showAlert('Source Warehouse details not found');
      return;
    }

    if (this._lprequest.descirption != null && this._lprequest.descirption.length > 500) {
      this.showAlert('Description must be less than 500 characters');
      return;
    }

    this._spinnerService.show();
    this._locationservice.saveProductRequest(this._location.locationId, this._lprequest).subscribe(data => {
      this._spinnerService.hide();
      this.onSave.emit(data);
    }, err => {
      this._spinnerService.hide();
      this.showAlert(`Unabel to save : ${err.error.message}`);
    });
  }
  showAlert(message: string) {
    this.alertMessage.alertClass = 'alert alert-danger center center-block';
    this.alertMessage.alertMessage = message;
    this.popupSuccessModel.show();
  }
  showMessage(message: string) {
    this.alertMessage.alertClass = 'alert alert-success center center-block';
    this.alertMessage.alertMessage = message;
    this.popupSuccessModel.show();
  }
  cancled() {
    this.onCancled.emit('canled');
  }

  getAvilableStock($event) {
    if ($event != null) {
      this.partcodeDescription = $event.productDescription;
    } else {
      this.partcodeDescription = null;
    }
    this.getAvilableStockAtSource();
    this.getAvilableStockAtU();
  }
  getAvilableStockAtSource() {
    
    this.avilableStockAtSource = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._locationservice.getStockByWarehouseAndProductPartCode(
      this._lprequest.sourceLocationId,
      this._lpitems.productPartCode)
      .subscribe(data => this.avilableStockAtSource = data);
  }
  getAvilableStockAtU() {
    this.avilabStockAtU = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._locationservice.getStockByWarehouseAndProductPartCode(
      this._lprequest.destinationLocationId,
      this._lpitems.productPartCode)
      .subscribe(data => this.avilabStockAtU = data);
  }
  searchProducts($event){
    this._productService.getSmallForlist($event.term).subscribe(data=>{
      const temp=[];
      data.forEach(e=>{
        let pro=new ProductsForList();
      
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        temp.push(pro);
      })
      this.productItemList=temp;
    });
  }
}
