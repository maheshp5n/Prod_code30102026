import { Component, OnInit, ViewChild } from '@angular/core';
import { LocationForList, Page, ProductsForList } from '../../../services/model';
import { LocationService } from '../../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ProductsService } from '../../../services/products.services';

@Component({
  selector: 'app-hreshold',
  templateUrl: './hreshold.component.html',
  styleUrls: ['./hreshold.component.scss']
})
export class HresholdComponent implements OnInit {

  warehouses:Array<LocationForList>=[];
  locationId:number;
  warehouseitemss:Page<any>=new Page<any>();
  searckeys={
    warehouse:'',
    items:''
  }
  config={
    allItem:true,
    serial:false,
    nonserial:false
  };
  productForlist:Array<ProductsForList>=[];
  allselection:boolean=false;
  selecteditems:Array<any>=[];
  
  selectedWarehouse:any;
  selectedproduct:any;
  newaproduct={
    locationId:null,
    productPartcode:null,
    msl:null,
    hsl:null,
  }
  alertDetails={
    type:'danger',
    message:''
  }
  indiname:string='';
  @ViewChild('alertWindows')alertWindows:ModalDirective;
  @ViewChild('resetwindows')resetwindows:ModalDirective;
  constructor(private _locationservice:LocationService,private _ngxservice:NgxSpinnerService,private _activatedRoute:ActivatedRoute,private _productservice:ProductsService) {
    this._activatedRoute.params.subscribe(pram=>{
      this.locationId=pram['id'];
      if(this.locationId!=undefined&&this.locationId!=null&&this.locationId!=0){
        this.getLocationitemsByLocationId();
      }
      else{
        this.getLocationItems();
      }
      this.config.allItem=true;
      this.config.nonserial=false;
      this.config.serial=false;
    });
   }

  ngOnInit() {
    this.getLocations();
    this.getAllproducts();
  //  this.getLocationItems();
  }
  getLocations(){
    this._ngxservice.show();
    this._locationservice.locationForlist('warehouse').subscribe(data=>{
      this.warehouses=data;
      this._ngxservice.hide();
    },error=>this._ngxservice.hide());
  }
  getLocationItems(filter?){
    this._ngxservice.show();
    this._locationservice.getLocationThreshold(filter).subscribe(data=>{
      data.content.forEach(d=>d.selected=false);
      this._ngxservice.hide();
      if(this.locationId==undefined||this.locationId==null||this.locationId==0)
        this.warehouseitemss=data;
    },error=>{
      this._ngxservice.hide();
    });
  }
  getLocationitemsByLocationId(filter?){
    this._ngxservice.show();
    this._locationservice.getLocationThresholdByLocationId(this.locationId,filter).subscribe(data=>{
      data.content.forEach(d=>d.selected=false);
      this._ngxservice.hide();
      this.warehouseitemss=data;
    },error=>{
      this._ngxservice.hide();
      
    });
  }
  changeInventoryType(type){
    if(type=='all'){
      this.config.allItem=true;
      this.config.serial=false;
      this.config.nonserial=false;
    }
    if(type=='serial'){
      this.config.allItem=false;
      this.config.serial=true;
      this.config.nonserial=false;
    }
    if(type=='nonserial'){
      this.config.allItem=false;
      this.config.serial=false;
      this.config.nonserial=true;
    }
    if(this.locationId==undefined||this.locationId==null||this.locationId==0){
      this.getLocationItems(`producttype=${type}`);
    }
    else{
      this.getLocationitemsByLocationId(`producttype=${type}`);
    }
  }
  getpillClass(type){
    if(type=='all'){
      if(this.locationId==undefined||this.locationId==null||this.locationId==0){
        return 'm-pills m-pills-selected';
      }
      else{
        return 'm-pills'
      }
    }
    else{
      if(this.locationId==undefined||this.locationId==null||this.locationId==0){
        this.indiname='';
        return 'm-pills';
      }
      else{
        this.indiname=this.getWarehouseNameById(this.locationId);
        return 'm-pills  m-pills-selected';
      }
    }
  }
  getWarehouseNameById(id):string{
    for(let i=0;i<this.warehouses.length;i++){
      if(this.warehouses[i].locationId==id){
        return this.warehouses[i].locationName;
      }
    }
    return '';
  }
  changeSelection(){
    this.warehouseitemss.content.forEach(d=>{
        d.selected=this.allselection;
    });
  }
  onResetButtonClick(){
    this.warehouseitemss.content.forEach(d=>{
      if(d.selected){
        this.selecteditems.push({
          'locationId':d.locationId,
          'locationName':d.locationName,
          'partCode':d.partCode,
          'partDescription':d.partDescription,
          'partType':d.partType,
          'msl':d.msl,
          'hsl':d.hsl,
          'okQuantity':d.okQuantity
        });
      }
    });
    if(this.locationId!=undefined&&this.locationId!=null&&this.locationId!=0){
      this.newaproduct.locationId=this.locationId;
    }
    this.resetwindows.show();
  }
  onResetWindowHide(){
    this.selecteditems=[];
  }
  getAllproducts(){
    this._productservice.getProductForList().subscribe(data=>{
      this.productForlist=data;
    });
  }
  removeSelectedItem(locationId,productPartCode){
    let temp=[];
    for(let i=0;i<this.selecteditems.length;i++){
      if(this.selecteditems[i].locationId!=locationId||this.selecteditems[i].partCode!=productPartCode){
        temp.push(this.selecteditems[i]);
      }
    }
    this.warehouseitemss.content.forEach(d=>{
      if(d.partCode==productPartCode&&d.locationId==locationId){
        d.selected=false;
        this.allselection=false;
      }
    })
    this.selecteditems=temp;
  }
  additemstoSelectedList(){
    if(this.newaproduct.msl==undefined||this.newaproduct.msl==null||this.newaproduct.msl==''){
      this.newaproduct.msl=0;
    }
    if(this.newaproduct.hsl==undefined||this.newaproduct.hsl==null||this.newaproduct.hsl==''){
      this.newaproduct.hsl=0;
    }

    if(this.newaproduct.msl>this.newaproduct.hsl){
      this.showAlert(`MSL can not be greater then HSL`,'danger');
      return;
    }
    let found=false;
    for(let i=0;i<this.selecteditems.length;i++){
      if(this.selecteditems[i].locationId==this.newaproduct.locationId&&this.newaproduct.productPartcode==this.selecteditems[i].partCode){
        found=true;
        break;
      }
    }
    if(found){
      this.showAlert(`Item already edixsts`,'danger');
      return;
    }
    this.selecteditems.push({
      'locationId':this.newaproduct.locationId,
      'locationName':this.getLocationnameWidhtId(this.newaproduct.locationId),
      'partCode':this.newaproduct.productPartcode,
      'partDescription':this.getproductDescriptionFromPartCode(this.newaproduct.productPartcode),
      'partType':'-',
      'msl':this.newaproduct.msl,
      'hsl':this.newaproduct.hsl,
      'okQuantity':0
    });
  }
  getLocationnameWidhtId(loid):string{
    for(let i=0;i<this.warehouses.length;i++){
      if(this.warehouses[i].locationId==loid){
        return this.warehouses[i].locationName;
      }
    }
  }
  getproductDescriptionFromPartCode(partCode):string{
    for(let i=0;i<this.productForlist.length;i++){
      if(this.productForlist[i].productPartCode==partCode){
        return this.productForlist[i].productDescription;
      }
    }
  }
  pageChange($event){
    let type='all';
    if(this.config.allItem) type='all';
    if(this.config.nonserial) type='nonserial'
    if(this.config.serial) type='serial';
    let pnumer=($event.page-1);

    if(this.locationId==undefined||this.locationId==null||this.locationId==0){
      this.getLocationItems(`page=${pnumer}&producttype=${type}`);
    }
    else{
      this.getLocationitemsByLocationId(`page=${pnumer}&producttype=${type}`);
    }
  }
  showAlert(message,type){
    this.alertDetails.message=message;
    this.alertDetails.type=type;
    this.alertWindows.show();
  }
  submitResetValue(){
    if(this.selecteditems.length>0){
      this._ngxservice.show();
      this._locationservice.resetMSL(this.selecteditems).subscribe(data=>{
        this.showAlert(`MSL for selected waraehouse has been modified`,'success');
        this.resetwindows.hide();
        this.changeInventoryType('all');
      },error=>{
        this._ngxservice.hide();
        this.showAlert(`Error: ${error.error.message}`,'danger');
      });
    }
  }
}
