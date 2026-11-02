import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import { ServiceAppointmentProductConsumedRequest, ServiceAppointmentProductConsumedLineitemRequest, LocationProductItemForList, Page, ProductConsumptionType, ServiceAppointmentProductConsumedResponse, LptransferOkSRN_details } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ProductsService } from '../../services/products.services';

@Component({
  selector: 'app-serviceappointment-productconsumed',
  templateUrl: './serviceappointment-productconsumed.component.html',
  styleUrls: ['./serviceappointment-productconsumed.component.scss']
})
export class ServiceappointmentProductconsumedComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel') createProductModel: ModalDirective;

  @Output()
  onProductConsumedCreated: EventEmitter<ServiceAppointmentProductConsumedRequest> = new EventEmitter<ServiceAppointmentProductConsumedRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  message: string;
  // createProduct: Product = new Product();
  // _productList: Array<ProductsForList> = [];
  // productsForList: ProductsForList = new ProductsForList();  
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  serviceAppointmentProductConsumedResponse: ServiceAppointmentProductConsumedResponse = new ServiceAppointmentProductConsumedResponse();
  serviceAppointmentProductConsumedRequest: ServiceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
  consumeTypeList: Array<ProductConsumptionType> = [];
  okProductList: Array<LocationProductItemForList> = [];

  productLineItemRequest: ServiceAppointmentProductConsumedLineitemRequest = new ServiceAppointmentProductConsumedLineitemRequest();
  productLineItemList: Array<ServiceAppointmentProductConsumedLineitemRequest> = [];
  okProductSerialNumberList: Array<string> = [];
  faultyRequesed:string="col-md-6 col-sm-6 form-group";
  typeNameReplaced: boolean = false;
  typeNameSale: boolean = false;
  typeNameConsume: boolean = true;
  productType: string
  engineergrndate:string;
  schGRNdate:string;
    faultyProductsList=[];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editServiceAppointmentData(serviceAppointmentProductConsumedRequest: ServiceAppointmentProductConsumedRequest) {
    this.serviceAppointmentProductConsumedRequest = serviceAppointmentProductConsumedRequest;
  }
  get editServiceAppointmentData(): ServiceAppointmentProductConsumedRequest {
    return this.serviceAppointmentProductConsumedRequest;
  }

  constructor(private spinner: NgxSpinnerService, private serviceAppointment: ServiceAppointmentService,private _productservice:ProductsService) { }

  ngOnInit() {
    this.serviceAppointmentProductConsumedRequest.consumedQty = 1;
    this.serviceAppointmentProductConsumedRequest.consumeType = '0';
    this.getOkProduct();
    // this.getProductConsumedType();
    this.faPlus;
  }
  getProductConsumedType() {
    this.spinner.show();
    this.serviceAppointment.getProductConsumedType().subscribe(data => {
      this.consumeTypeList = data;
      if (data != null) {
        this.spinner.hide();
      }
    })
  }
  okproductListtemp=[];
  getOkProduct() {
    this.serviceAppointment.getOkProduct(this.serviceAppointmentProductConsumedRequest.serviceAppointmentNumber).subscribe(data => {
    //  this.okProductList = data;
      data.forEach(e=>{
        let pro=new LocationProductItemForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        pro.locationProductItemId=e.locationProductItemId;
        pro.productTypeName=e.productTypeName;
        pro.id=e.id;
        this.okproductListtemp.push(pro);
      })
      this.okProductList=this.okproductListtemp;
      
    });
  }
  // lpt_srn_detail:Array<LptransferOkSRN_details>=[]
 
  onSelectSerial(serial){
    this.engineergrndate='';
    this.schGRNdate='';
console.log(serial)
if(serial!=null){
this.spinner.show();
this.serviceAppointment.getOKSRNDetails(serial).subscribe(data=>{
  this.spinner.hide();
if(data!=null){
if(data.length>=2){
  this.engineergrndate=data[0].ModifiedDate;
  this.schGRNdate=data[1].ModifiedDate;
  
  }
  else{
    this.schGRNdate=data[0].ModifiedDate;
  }
}else{
  this.schGRNdate=''
  this.engineergrndate='';
}

// data.forEach(e=>{
//   if(e.role=='SCH Meerut'){
//     this.schGRNdate=e.ModifiedDate;

//   }else{
//   this.engineergrndate=e.ModifiedDate;
//   }
// })
  this.spinner.hide();
},error=>{
  this.spinner.hide();
})
}
  }
  getOkproductSerialNumberByPartcode(partCode) {
    let okId: number;
    if (partCode !== null && partCode !== undefined) {
      this.okProductList.forEach(data => {
        if (partCode === data.productPartCode) {
          okId = data.id;
          this.productType = data.productTypeName;
          this.okProductSerialNumberList=[];
          this.productLineItemRequest.consumedItemSerialNumber=null
          this.spinner.show();
          this.serviceAppointment.getOkproductSerialNumber(okId).subscribe(data => {
            this.okProductSerialNumberList = data;
            this.spinner.hide();
          })
          if (this.productType === 'Component') {
            this.consumeTypeList=[];
            this.serviceAppointmentProductConsumedRequest.consumeType = 'Consumed';
            this.typeNameConsume = true;
            this.typeNameReplaced = false;
            this.typeNameSale = false;
            const compaonettype="Consumed";
            // type dropdown
            this.spinner.show();
            this.serviceAppointment.getProductConsumedType().subscribe(data => {
             
              // this.consumeTypeList = data;
              data.forEach(e=>{
                if(e.consumptionTypeName==compaonettype){
                  this.consumeTypeList.push(e);
                }
              })
              this.spinner.hide();
              
            })
          }
          else {
            //ADDING DEFAULT PARTCODE
            this.faultyProductsList=[];
            this.faultyProductsList.push(data);
            this.productLineItemRequest.faultyPartcode=data.productPartCode;

            this.consumeTypeList=[];
            // const compaonettype="Replaced";
            // console.log(compaonettype);
            // type dropdown


            this.spinner.show();
            this.serviceAppointment.getProductConsumedType().subscribe(data => {
             let typeList:Array<ProductConsumptionType>=[];
             let typeListsale:Array<ProductConsumptionType>=[];
              // this.consumeTypeList = data;
              data.forEach(e=>{
                
                if(e.consumptionTypeName=="Replaced" ){
                  typeList.push(e);
                }
                if(e.consumptionTypeName=="Sale"){
                  typeListsale.push(e);
                }
               
              });
              typeListsale.forEach(e=>{
                typeList.push(e)
              })
              typeList.forEach(e=>{
                this.consumeTypeList.push(e)
              });
              // this.consumeTypeList=typeList;
                this.spinner.hide();  
            })
            this.serviceAppointmentProductConsumedRequest.consumeType = 'Replaced';
            this.typeNameReplaced = true;
            this.typeNameSale = false;
            this.typeNameConsume = false;
            // this.faultyRequesed="col-md-6 col-sm-6 form-group required"
            this.onClickType(this.serviceAppointmentProductConsumedRequest.consumeType)
          
          }
        }
      })
    }
  }
  okProSerialRequired:string="col-md-6 col-sm-6 form-group";
  onClickType(consumeType: string) {
    // console.log($event);
    if (consumeType === 'Replaced') {
      this.typeNameReplaced = true;
      // this.typeNameSale = false;
      this.typeNameConsume = false;
      this.okProSerialRequired="col-md-6 col-sm-6 form-group required";
      this.faultyRequesed="col-md-6 col-sm-6 form-group required";
    } else if (consumeType === 'Sale') {
      // this.typeNameSale = true;
      this.typeNameReplaced = true;
      // this.typeNameReplaced = false;
      this.typeNameConsume = false;
      this.okProSerialRequired="col-md-6 col-sm-6 form-group required";
      this.faultyRequesed="col-md-6 col-sm-6 form-group";
    } else {
      this.typeNameConsume = true;
      this.typeNameReplaced = false;
      this.faultyRequesed="col-md-6 col-sm-6 form-group";
      this.okProSerialRequired="col-md-6 col-sm-6 form-group";
    }
  }
  addProductList(item: ServiceAppointmentProductConsumedLineitemRequest) {
    let list = new ServiceAppointmentProductConsumedLineitemRequest();
    if (item.consumedItemSerialNumber != null) {
      list.consumedItemSerialNumber = item.consumedItemSerialNumber;
      list.replacedItemSerialNumber = item.replacedItemSerialNumber;
      this.productLineItemList.push(list);
      // this.productLineItemRequest.consumedItemSerialNumber = null;
      // this.productLineItemRequest.replacedItemSerialNumber = null;
    }
  }
  removeProductList(item: ServiceAppointmentProductConsumedLineitemRequest) {
    if (item != null) {
      const index: number = this.productLineItemList.indexOf(item);
      if (index !== -1) {
        this.productLineItemList.splice(index, 1);
      }
    }
  }

  postCreate() {
    const pattern= new RegExp('^[a-zA-Z0-9]*$');
    this.serviceAppointmentProductConsumedRequest.productLineItem = [];
    if (this.serviceAppointmentProductConsumedRequest.consumeType == "Consumed") {

      this.serviceAppointmentProductConsumedRequest.productLineItem = [];
    }
    if (this.serviceAppointmentProductConsumedRequest.consumeType == "Replaced") {
      this.serviceAppointmentProductConsumedRequest.consumedQty = 1;
      this.serviceAppointmentProductConsumedRequest.productLineItem.push(this.productLineItemRequest);
    }
    if (this.serviceAppointmentProductConsumedRequest.consumeType == "Sale") {
      this.serviceAppointmentProductConsumedRequest.consumedQty = 1;
      this.productLineItemRequest.replacedItemSerialNumber = '';
      this.serviceAppointmentProductConsumedRequest.productLineItem.push(this.productLineItemRequest);
    }

    if (this.serviceAppointmentProductConsumedRequest.partCode == null || this.serviceAppointmentProductConsumedRequest.partCode === '0') {
      this.message = 'Please fill the part code';
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentProductConsumedRequest.consumeType == null || this.serviceAppointmentProductConsumedRequest.consumeType === '0') {
      this.message = 'Please fill the type name';
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentProductConsumedRequest.consumedQty == null || this.serviceAppointmentProductConsumedRequest.consumedQty < 1) {
      this.message = 'Please fill the quantity 1 or more than 1';
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentProductConsumedRequest.remark != undefined && (this.serviceAppointmentProductConsumedRequest.remark).length > 1000) {
      this.message = 'Please fill the remarks under 1000 characters ';
      this.popupSuccessModel.show();
    }
    else if (this.productLineItemRequest.replacedItemSerialNumber != undefined && (this.productLineItemRequest.replacedItemSerialNumber).length > 16) {
      this.message = 'Please fill the replaced item serial number under 16 alphanumeric characters ';
      this.popupSuccessModel.show();
    }
    else if(this.typeNameReplaced==true && this.productLineItemRequest.replacedItemSerialNumber==undefined){
      this.message="Please fill Faulty Product";
      this.popupSuccessModel.show();
    }

    else if(this.typeNameReplaced==true&&!pattern.test(this.productLineItemRequest.replacedItemSerialNumber)){
      this.message="Please fill the replaced item serial number under 16 alphanumeric characters ";
      this.popupSuccessModel.show();
    }
    else if(this.typeNameReplaced==true && this.productLineItemRequest.consumedItemSerialNumber==undefined){
      this.message="Please fill OK Product Serial Number";
      this.popupSuccessModel.show();
    }
    else if(this.typeNameReplaced==true&&(this.productLineItemRequest.faultyPartcode==undefined||this.productLineItemRequest.faultyPartcode==null||this.productLineItemRequest.faultyPartcode=='')){
      this.message="Please provide faulty part code";
      this.popupSuccessModel.show();
    }

    else {
      this.spinner.show();
      this.serviceAppointment.postconsumeProductDetails(this.serviceAppointmentProductConsumedRequest.serviceAppointmentNumber, this.serviceAppointmentProductConsumedRequest).subscribe(data => {
        this.message = "Updated Successfully";
        this.popupNgClass = "alert alert-success center center-block";
        this.onProductConsumedCreated.emit(data);
        if (data != null) {
          this.spinner.hide();
        }
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
  }

  cancelData() {
    this.serviceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  searchfaultProduct(terms){
    
    this._productservice.getSmallForlist(terms.term).subscribe(data=> this.faultyProductsList=data);
  }

}
