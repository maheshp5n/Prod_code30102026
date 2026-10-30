import { GlobalserviceService } from './../../globalservice.service';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { caseService } from '../../services/caseService.service';
import { AssetsForList, ProductsForList, BillOfMaterialResponse, DCTRepairRequest, DctRepairConsumedItemsRequest, dctRepairItemList, Repaireng, LPRequest, LPItemRequest } from '../../services/model';
import { AssetsService } from '../../services/assets.services';
import { BillOfmaterialService } from '../../services/billOfMaterial.service';
import { ModalDirective } from 'ngx-bootstrap';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceOrderJobCardService } from '../../services/serviceorderjobcardservice';
import { LocationService } from '../../services/location.service';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-create-rpair-dct',
  templateUrl: './create-rpair-dct.component.html',
  styleUrls: ['./create-rpair-dct.component.scss']
})
export class CreateRpairDCTComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  assetList: Array<AssetsForList> = [];
  _productList: Array<ProductsForList> = [];
  dctreq = new DCTRepairRequest();
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  _isEditMode: boolean;
  description: string;
  locationid: number;
  qty: number=1;
  description2: string;
  OkProductsList: Array<string> = [];
  serialNumber: Array<string>;
  BomList: Array<BillOfMaterialResponse> = [];
  productStatusList: Array<string> = [];
  itemsLine: Array<DctRepairConsumedItemsRequest> = [];
  item: Array<dctRepairItemList> = [];
  isSerialReq: string = 'form-group';
  lblbtn: string = '';
  btn1: boolean = true;
  locationName: string = null;
  rawproduct: string;
  repairEnglist:Array<Repaireng>=[];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set EditEntityDCTReq(dctReq: DCTRepairRequest) {
    // console.log(dctReq.partCode);
    this.dctreq = dctReq;
    this.getProblemMaster(this.dctreq.partCode);
  }

  get EditEntityDCTReq(): DCTRepairRequest {
    return this.dctreq;

  }
  @Input()
  set prodescription(desc: string) {
    this.description = desc;
  }
  get prodescription(): string {
    return this.description;
  }
  locationItemId: number;
  @Input()
  set LocationItemID(locationitemid: number) {
    this.locationItemId = locationitemid; //treated as job card id;
  }
  get LocationItemID(): number {
    return this.locationItemId;
  }

  @Output()
  onDCTRCreated: EventEmitter<DCTRepairRequest> = new EventEmitter<DCTRepairRequest>();
  @Output()
  onDCTRCancel: EventEmitter<string> = new EventEmitter<string>();

  modelmeta = { isModelOpen: true };

  problemMaster = [];
  dummyProblem = [];

  resulationByProblem = [];

  _prob = {
    problem: '',
    resulation: 0
  };
  avilabStockAtU: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  avilableStockAtSource: any = {
    ok_quantity: 0,
    quantity_in_on_hand: 0
  };
  _lprequest: LPRequest = new LPRequest();
  _lpitems: LPItemRequest = new LPItemRequest();

  resulationprovided = [];
  loginData:any;
  Loginlocationid:any;
  constructor(private serviceorderjobcardservice:ServiceOrderJobCardService,
    private bomService: BillOfmaterialService,private _locationservice:LocationService,
    private dctservice: DCTRepairservice, private spinner: NgxSpinnerService, private _global: GlobalserviceService,
    private loginservice:Loginservices) { }

  ngOnInit() {
    this.getBOMList();
    this.ProductStatus();
    this.getRpairng();
    this.getLoggedInUser();
    //  this.getOKProducts();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.Loginlocationid = this.loginData['locationId']
    console.log(this.Loginlocationid);
  }

  getBOMList() {
    this.bomService.getBOM(this.dctreq.partCode).subscribe(data => {
      this.BomList = data;

    });
  }
  ProductStatus() {
    this.dctservice.getProductStatus().subscribe(data => {
      this.productStatusList = data;
    });
  }
  postRDCT() {

    const temp = [];
    for (let i = 0; i < this.resulationprovided.length; i++) {
      temp.push(this.resulationprovided[i].id);
    }

    this.dctreq.items = this.itemsLine;
    this.dctreq.jobCardId = this.locationItemId;
    this.dctreq.problemmaster = temp;
    if (this.dctreq.problemmaster.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please provide resolution' });
      return;
    }
    if (this.dctreq.productStatus == undefined || this.dctreq.productStatus == null || this.dctreq.productStatus == 0) {
      this._global.alert({ type: 'alert', message: 'Please provide status' });
      return;
    }
    this.spinner.show();
    this.dctservice.postDCTRpair(this.dctreq).subscribe(data => {
      this.spinner.hide();
      this._global.alert({ type: '',message: 'Created Successfully'});
      this.onDCTRCreated.emit(data);
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  cancelRDCTData() {
    this.onDCTRCancel.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onDCTRCancel.emit('cancled');
    }
  }
  addItem() {
    if(this.qty>this.avilabStockAtU.ok_quantity){
      this.message='Quantity should be Less Aur Equal to Destination Qunatity';
      this.popupSuccessModel.show();
    }

     else if ((this.serialNumber == null || this.serialNumber == undefined) && this.isSerialReq == 'form-group required') {
      this.message = 'Please fill serial Number';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    } else {
      const dctritem = new DctRepairConsumedItemsRequest();
      dctritem.locationId = this.locationid;
      dctritem.qty = this.qty;
      dctritem.serialNumber = this.serialNumber;

      this.itemsLine.push(dctritem);
      this.listItm();

    }

  }

  listItm() {

    const items = new dctRepairItemList();
    items.description = this.description2;
    items.location = this.locationName;
    items.locationid = this.locationid;
    items.rawParcode = this.rawproduct;
    this.itemsLine.forEach(e => {
      items.quantity = e.qty;
      items.serialNO = e.serialNumber;
    });
    this.item.push(items);

    this.locationid = null;
    this.rawproduct = null;
    this.locationid = null;
    this.qty = null;
    this.description2 = null;
    this.serialNumber = null;


  }

  getOKProducts($event: BillOfMaterialResponse) {
    if ($event != null) {
      this.description2 = $event.rawProductDescription;
      this.locationName = $event.locationName;
      this.rawproduct = $event.rawProductPartCode;
      if ($event.rawProductType === 'Component') {
        this.isSerialReq = 'form-group';
        this.serialNumber = [];
        this.OkProductsList = [];
        this.btn1 = true;
      } else {
        this.spinner.show();
        this.dctservice.getOkproductByPartCode($event.rawProductPartCode).subscribe(data => {
          this.OkProductsList = data;
          this.spinner.hide();
          this.btn1 = false;
          this.isSerialReq = 'form-group required';
        }, error => {
          this.isSerialReq = 'form-group required';
          this.spinner.hide();
          this.message = error.error.message;
          this.popupSuccessModel.show();
        });

      }
    } else {
      this.description2 = null;
      this.locationName = null;
    }
    // this.getAvilableStockAtSource();
    this.getAvilableStockAtU();


  }
  removeProductList(it: dctRepairItemList) {
    const counter = this.item.length;
    const temp = [];
    for (let i = 0; i < counter; i++) {
      if (this.item[i].rawParcode != it.rawParcode) {
        temp.push(this.item[i]);
      }
    }
    this.item = temp;
    const _tp = [];

    for (let i = 0; i < this.itemsLine.length; i++) {
      if (this.itemsLine[i].locationId != it.locationid) {
        _tp.push(this.itemsLine[i]);
      }
    }
    this.itemsLine = _tp;
  }

  getProblemMaster(partCode) {
    this.dctservice.getProblemMaster(partCode).subscribe(data => {
      this.problemMaster = data;
      this.dummyProblem=data;
    });
  }

  faultDDlChange() {
    let temp = [];
    for (let i = 0; i < this.problemMaster.length; i++) {
      if (this.problemMaster[i].id == this._prob.problem) {
        temp.push({id:this.problemMaster[i].id,resolution:this.problemMaster[i].resolution});
      }
    }
    this.resulationByProblem = temp;
    this._prob.resulation=0;
    
  }

  addResulation() {
    
    if (this._prob.resulation == undefined || this._prob.resulation == null || this._prob.resulation == 0) {
      this._global.alert({ type: 'alert', message: 'Please select resolution' });
      return;
    }
    for (let i = 0; i < this.problemMaster.length; i++) {
      if (this._prob.resulation == this.problemMaster[i].id) {
        this.resulationprovided.push(this.problemMaster[i]);
      }
    }
  }

  removeResulation(id) {
    const temp = [];
    for (let i = 0; i < this.resulationprovided.length; i++) {
      if (this.resulationprovided[i].id !== id) {
        temp.push(this.resulationprovided[i]);
      }
    }
    this.resulationprovided = temp;
  }
  // fullName:string;
  getRpairng(){
    this.serviceorderjobcardservice.getRpairEngList().subscribe(data=>{
      // this.repairEnglist=data;
      this.repairEnglist=[];
      data.forEach(ct=>{
        let _ct=new Repaireng;
        _ct.firstName = ct.firstName;
        _ct.lastName = ct.lastName;
        _ct.middleName = ct.middleName;
        _ct.emailId = ct.emailId;
        if(ct.middleName==null){
          _ct.fullName = ct.firstName + ' ' + ct.lastName;
        } else if(ct.lastName==null){
          _ct.fullName = ct.firstName + ' ' + ct.middleName;
        }
        else{
          _ct.fullName = ct.firstName + ' ' + ct.middleName + ' ' + ct.lastName;
        }
        //console.log(_ct.fullName);
        this.repairEnglist.push(_ct);
      });
      //console.log(this.repairEnglist);
    });

  }
   // avalable stock
   

  getAvilableStockAtSource() {
    
    this.avilableStockAtSource = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._locationservice.getStockByWarehouseAndProductPartCode(
      this.Loginlocationid,
      this.rawproduct)
      .subscribe(data => this.avilableStockAtSource = data);
  }
  getAvilableStockAtU() {
    this.avilabStockAtU = {
      ok_quantity: 0,
      quantity_in_on_hand: 0
    };
    this._locationservice.getStockByWarehouseAndProductPartCode(
      this.Loginlocationid,
      this.rawproduct)
      .subscribe(data =>{
        console.log(data);
        this.avilabStockAtU = data;
        console.log(JSON.stringify(this.avilabStockAtU));
      if(JSON.stringify(this.avilabStockAtU)=="{}"){
          this.avilabStockAtU = {
            ok_quantity: 0,
            quantity_in_on_hand: 0
          };
          this.avilabStockAtU.ok_quantity=0;
          this.avilabStockAtU.quantity_in_on_hand=0;
          
        }
        else{
         
        
        }
      }
         
        );
  }

}
