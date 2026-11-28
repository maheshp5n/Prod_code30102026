import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { LocationProductItemForList, ProductConsumptionType, ServiceAppointmentProductConsumedLineitemRequest, ServiceAppointmentProductConsumedRequest, ServiceAppointmentProductConsumedResponse } from '../../../services/model';
import { ProductsService } from '../../../services/products.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtproductService } from '../../bat_services/btproduct.service';
import { BTProductForListResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-seviceappoint-product-consumed',
  templateUrl: './bat-seviceappoint-product-consumed.component.html',
  styleUrls: ['./bat-seviceappoint-product-consumed.component.scss']
})
export class BatSeviceappointProductConsumedComponent implements OnInit {
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
  productForList :Array<BTProductForListResponse> =[]; 

  productLineItemRequest: ServiceAppointmentProductConsumedLineitemRequest = new ServiceAppointmentProductConsumedLineitemRequest();
  productLineItemList: Array<ServiceAppointmentProductConsumedLineitemRequest> = [];
  okProductSerialNumberList: Array<string> = [];
  faultyRequesed:string="col-md-6 col-sm-6 form-group";
  typeNameReplaced: boolean = false;
  typeNameSale: boolean = false;
  typeNameConsume: boolean = true;
  productType: string

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
    console.log(this.serviceAppointmentProductConsumedRequest.serviceAppointmentId)
  }
  get editServiceAppointmentData(): ServiceAppointmentProductConsumedRequest {
    return this.serviceAppointmentProductConsumedRequest;
  }

  constructor(private spinner: NgxSpinnerService, private serviceAppointment: ServiceAppointmentService,private _productservice:ProductsService,
    private _productService : BtproductService,private _btserviceAppointment : BtServiceappointmentService) { }

  ngOnInit() {
    this.serviceAppointmentProductConsumedRequest.consumedQty = 1;
    this.serviceAppointmentProductConsumedRequest.consumeType = '0';
    // this.getOkProduct();
    // this.getProductConsumedType();
    this.faPlus;
  }
  tempproductlst;
  searchBtProductList($event){
    this._productService.getAllProducts($event.term).subscribe(data=>{
      //Edit by shagufta this.productForList = data;
      this.tempproductlst=[];
      
      let temp=[]
      data.forEach(e=>{
        let assetsList=new BTProductForListResponse();
     assetsList.id=e.id;
     assetsList.partCode=e.partCode;
     assetsList.productType=e.productType;
     assetsList.productDescription=e.productDescription;
    //  assetsList.asstPartCode=e.serialNumber+'-'+e.productPartCode;
     this.tempproductlst.push(assetsList);
      })
      this.productForList=this.tempproductlst;
      console.log(this.productForList);
    })

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
  // getOkProduct() {
  //   this.serviceAppointment.getOkProduct(this.serviceAppointmentProductConsumedRequest.serviceAppointmentNumber).subscribe(data => {
  //   //  this.okProductList = data;
  //     data.forEach(e=>{
  //       let pro=new LocationProductItemForList();
  //       pro.productPartCode=e.productPartCode;
  //       pro.productDescription=e.productDescription;
  //       pro.bothvalues=e.productPartCode+' '+e.productDescription;
  //       pro.locationProductItemId=e.locationProductItemId;
  //       pro.productTypeName=e.productTypeName;
  //       pro.id=e.id;
  //       this.okproductListtemp.push(pro);
  //     })
  //     this.okProductList=this.okproductListtemp;
      
  //   });
  // }
  // getOkproductSerialNumberByPartcode(partCode) {
  //   let okId: number;
  //   if (partCode !== null && partCode !== undefined) {
  //     this.okProductList.forEach(data => {
  //       if (partCode === data.productPartCode) {
  //         okId = data.id;
  //         this.productType = data.productTypeName;
  //         this.okProductSerialNumberList=[];
  //         this.productLineItemRequest.consumedItemSerialNumber=null
  //         this.spinner.show();
  //         this.serviceAppointment.getOkproductSerialNumber(okId).subscribe(data => {
  //           this.okProductSerialNumberList = data;
  //           this.spinner.hide();
  //         })
  //         if (this.productType === 'Component') {
  //           this.consumeTypeList=[];
  //           this.serviceAppointmentProductConsumedRequest.consumeType = 'Consumed';
  //           this.typeNameConsume = true;
  //           this.typeNameReplaced = false;
  //           this.typeNameSale = false;
  //           const compaonettype="Consumed";
  //           // type dropdown
  //           this.spinner.show();
  //           this.serviceAppointment.getProductConsumedType().subscribe(data => {
             
  //             // this.consumeTypeList = data;
  //             data.forEach(e=>{
  //               if(e.consumptionTypeName==compaonettype){
  //                 this.consumeTypeList.push(e);
  //               }
  //             })
  //             this.spinner.hide();
              
  //           })
  //         }
  //         else {
  //           //ADDING DEFAULT PARTCODE
  //           this.faultyProductsList=[];
  //           this.faultyProductsList.push(data);
  //           this.productLineItemRequest.faultyPartcode=data.productPartCode;

  //           this.consumeTypeList=[];
  //           // const compaonettype="Replaced";
  //           // console.log(compaonettype);
  //           // type dropdown


  //           this.spinner.show();
  //           this.serviceAppointment.getProductConsumedType().subscribe(data => {
  //            let typeList:Array<ProductConsumptionType>=[];
  //            let typeListsale:Array<ProductConsumptionType>=[];
  //             // this.consumeTypeList = data;
  //             data.forEach(e=>{
                
  //               if(e.consumptionTypeName=="Replaced" ){
  //                 typeList.push(e);
  //               }
  //               if(e.consumptionTypeName=="Sale"){
  //                 typeListsale.push(e);
  //               }
               
  //             });
  //             typeListsale.forEach(e=>{
  //               typeList.push(e)
  //             })
  //             typeList.forEach(e=>{
  //               this.consumeTypeList.push(e)
  //             });
  //             // this.consumeTypeList=typeList;
  //               this.spinner.hide();  
  //           })
  //           this.serviceAppointmentProductConsumedRequest.consumeType = 'Replaced';
  //           this.typeNameReplaced = true;
  //           this.typeNameSale = false;
  //           this.typeNameConsume = false;
  //           // this.faultyRequesed="col-md-6 col-sm-6 form-group required"
  //           this.onClickType(this.serviceAppointmentProductConsumedRequest.consumeType)
          
  //         }
  //       }
  //     })
  //   }
  // }
  okProSerialRequired:string="col-md-6 col-sm-6 form-group";
  onClickType(consumeType: string) {
    console.log(consumeType)
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
    if (this.serviceAppointmentProductConsumedRequest.productConsumption == null || this.serviceAppointmentProductConsumedRequest.productConsumption === '0') {
      this.message = 'Please Fill The Type';
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentProductConsumedRequest.consumedProducts == null || this.serviceAppointmentProductConsumedRequest.consumedProducts === '0') {
      this.message = 'Please Fill The Consumed Products';
      this.popupSuccessModel.show();
    }
    else if (this.typeNameConsume==true && (this.serviceAppointmentProductConsumedRequest.quantity == null || this.serviceAppointmentProductConsumedRequest.quantity < 1)){
      this.message = 'Please Fill The Quantity 1 Or More Than 1';
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentProductConsumedRequest.remark != undefined && (this.serviceAppointmentProductConsumedRequest.remark).length > 1000) {
      this.message = 'Please Fill The Remarks Under 1000 Characters ';
      this.popupSuccessModel.show();
    }
    else if(this.typeNameReplaced==true&&(this.serviceAppointmentProductConsumedRequest.replacedProducts==""||this.serviceAppointmentProductConsumedRequest.replacedProducts==null||this.serviceAppointmentProductConsumedRequest.replacedProducts==undefined)){
      this.message="Please Fill Faulty Products";
      this.popupSuccessModel.show();
    }
    else {
      this.spinner.show();
      this._btserviceAppointment.postconsumeProductDetails(this.serviceAppointmentProductConsumedRequest.serviceAppointmentId, this.serviceAppointmentProductConsumedRequest).subscribe(data => {
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

   //Validation for numric tye value
   numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }


}
