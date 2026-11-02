import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { CityCatagory, BillOfMaterialRequest, ProductsForList, combinedList } from '../../services/model';
import { BillOfmaterialService } from '../../services/billOfMaterial.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AssetsService } from '../../services/assets.services';

@Component({
  selector: 'app-prout-lin-itm',
  templateUrl: './prout-lin-itm.component.html',
  styleUrls: ['./prout-lin-itm.component.scss']
})
export class ProutLinItmComponent implements OnInit {
@ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective
  constructor(private _assetService:AssetsService,private bomservice:BillOfmaterialService,private spinner: NgxSpinnerService) { }
  billReq:BillOfMaterialRequest=new BillOfMaterialRequest();
  message:string;
  partCode:string;
  _isEditMode:boolean;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editEntityBOM(res: BillOfMaterialRequest) {
    this.billReq = res;
  }

  get editEntityBOM(): BillOfMaterialRequest {
    return this.billReq;
  }
  @Input()
  set PartCODE(partcode:string){
    this.partCode=partcode;
  }
  get PartCODE():string{
    return this.partCode;
  }
  @Input()
  set location(locationName:string){
    // if(this.isEditMode && locationName!='' ){
    //   this.loationString=locationName;
    // this.requiredLocationList.push(this.loationString);
    // this.drawingStringList.push(this.drawaingRefString);
    // this.billReq.locationName=this.requiredLocationList;
    // this.billReq.drawaingRef=this.drawingStringList;
    //   let c=new combinedList();
    //   c.drawingRef=  this.drawaingRefString;
    //   c.location=this.loationString
    //   this.combinList.push(c);
    // }else{
    //   this.loationString='';
    //   this.combinList=[];
    // }
  }
  get location():string{
    return this.loationString;
  }
  @Output()
  onEntityCreated: EventEmitter<Array<BillOfMaterialRequest>> = new EventEmitter<Array<BillOfMaterialRequest>>();
  @Output()
  onCancel: EventEmitter<string> = new EventEmitter<string>();
  requiredLocationList:Array<string>=[];
  drawingStringList:Array<string>=[];
  _productList:Array<ProductsForList>=[];

  ngOnInit() {
    this.getProductDetails();
  }
  productdiscription:string;
  getProductDetails() {
    this._assetService.getProductForList('Product').subscribe(data => {
      this._productList = data;
      data.forEach((e)=>{
        this.productdiscription="";
      this.productdiscription=  e.productDescription
      console.log(this.productdiscription)
      })
    })
  }
  loationString:string=null;
  drawaingRefString:string=null;
  quntity:string;
  combinList:Array<combinedList>=[];

  AddList(){
   
    console.log(this.loationString);
    this.requiredLocationList.push(this.loationString);
    this.drawingStringList.push(this.drawaingRefString);
    this.billReq.locationName=this.requiredLocationList;
    this.billReq.drawaingRef=this.drawingStringList;
      let c=new combinedList();
      c.drawingRef=  this.drawaingRefString;
      c.location=this.loationString
      this.combinList.push(c);

  }
  popupNgClass: string = 'alert alert-danger center center-block';
   arrayBOM:Array<BillOfMaterialRequest>=[];
   bOMReq:BillOfMaterialRequest=new BillOfMaterialRequest();
  postPTLineItm(){
   if(this.billReq.productPartCode==null || this.billReq.productPartCode==undefined){
     this.message='Please Fill Product Part Code';
     this.popupNgClass='alert alert-danger center center-block';
     this.popupSuccessModel.show();
   }
   else if(this.billReq.requiredQuantity==null ||this.billReq.requiredQuantity==undefined){
    this.message='Please Fill Required Quantity';
    this.popupNgClass='alert alert-danger center center-block';
    this.popupSuccessModel.show();
   }else if(this.loationString==null || this.billReq.locationName.length< this.billReq.requiredQuantity){
    this.message='Please Fill Location Required in Number'+" "+this.billReq.requiredQuantity;
    this.popupNgClass='alert alert-danger center center-block';
    this.popupSuccessModel.show();
   }
   else{

    if(this._isEditMode){
      this.spinner.show();
      // this.billReq.locationName=this.requiredLocationList
      // this.billReq.drawaingRef=this.drawingStringList;
      this.bOMReq.locationName=this.billReq.locationName;
      this.bOMReq.productPartCode=this.billReq.productPartCode;
      this.bOMReq.drawaingRef=this.billReq.drawaingRef;
      this.bOMReq.locationName=this.billReq.locationName;
     this.bOMReq.requiredQuantity=this.billReq.requiredQuantity;
     this.bOMReq.productPartCode=this.billReq.productPartCode;
    //  this.arrayBOM.push(this.bOMReq);
      this.bomservice.addBOM(this.partCode,this.bOMReq).subscribe(data=>{
        this.spinner.hide();
        this.message="Updated Successfully";
        this.popupNgClass='alert alert-success center center-block';
        this.popupSuccessModel.show();
         this.onEntityCreated.emit(data);
         this.billReq=new BillOfMaterialRequest();
         this.loationString='';
         this.drawaingRefString='';
         this.combinList=[];
      },err=>{
        this.spinner.hide();
        this.message=err.err.message;
        this.popupSuccessModel.show();
      })
  
    }else{
      this.billReq.locationName=this.requiredLocationList
      this.billReq.drawaingRef=this.drawingStringList;
      this.bOMReq.locationName=this.billReq.locationName;
      this.bOMReq.productPartCode=this.billReq.productPartCode;
      this.bOMReq.drawaingRef=this.billReq.drawaingRef;
      this.bOMReq.locationName=this.billReq.locationName;
     this.bOMReq.requiredQuantity=this.billReq.requiredQuantity;
     this.bOMReq.productPartCode=this.billReq.productPartCode;
     this.arrayBOM.push(this.bOMReq);
    this.spinner.show();
    this.bomservice.saveBOM(this.partCode,this.arrayBOM).subscribe(data=>{
      this.spinner.hide();
      this.message="Updated Successfully";
      this.popupNgClass='alert alert-success center center-block';
      this.popupSuccessModel.show();
      this.onEntityCreated.emit(data);
      this.billReq=new BillOfMaterialRequest();
      this.loationString='';
      this.drawaingRefString='';
      this.combinList=[];
    },err=>{
      this.spinner.hide();
      this.message=err.err.message;
      this.popupSuccessModel.show();
    })

    }

   }
 
    
  
  }
 
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancel.emit('cancel');
    }
  }
  cancelPTLineData(){
    this.onCancel.emit('cancel');
  }
}
