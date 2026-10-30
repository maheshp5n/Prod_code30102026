import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { PartcodeChangeRequest, ProductsForList } from '../../services/model';
import { PartcodechangeService } from '../../services/partcodechange.service';
import { ProductsService } from '../../services/products.services';


@Component({
  selector: 'app-partchangemapproval',
  templateUrl: './partchangemapproval.component.html',
  styleUrls: ['./partchangemapproval.component.scss']
})
export class PartchangemapprovalComponent implements OnInit {

  _changeRequest:PartcodeChangeRequest=new PartcodeChangeRequest();
  _initFaultyPartCode=new ProductsForList();

  @Input()
  set partcodeChangeRequest(partcodeChange:PartcodeChangeRequest){
    Object.assign(this._changeRequest,partcodeChange);
    //this._changeRequest=partcodeChange;
    this._initFaultyPartCode.productPartCode=partcodeChange.faulty_product_product_part_code;
    this._initFaultyPartCode.productDescription=partcodeChange.faulty_product_description;
    this._initFaultyPartCode.bothvalues=this._initFaultyPartCode.productPartCode+' '+this._initFaultyPartCode.productDescription;
  }

  @Output() cancel:EventEmitter<any>=new EventEmitter<any>();
  @Output() onUpdate:EventEmitter<any>=new EventEmitter<any>();

  get partcodeChangeRequest(){
    return this._changeRequest;

  }

  productItemList:Array<ProductsForList>=[];

  constructor(
    private _productService:ProductsService,
    private _ngx:NgxSpinnerService,
    private _partchage:PartcodechangeService,
    private _global:GlobalserviceService
    ) { }

  ngOnInit() {
  }


  onCancel(){
    this.cancel.emit();
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
      temp.push(this._initFaultyPartCode);
      this.productItemList=temp;
    });

  }

  saveDetails(){
    if(this._changeRequest.faulty_product_product_part_code==null||this._changeRequest.faulty_product_product_part_code==undefined||this._changeRequest.faulty_product_product_part_code==''){
      this._global.alert({type:'alert',message:'Please select faulty part code'});
      return;
    }
    if(this._changeRequest.faulty_product_serial_number==null||this._changeRequest.faulty_product_serial_number==undefined||this._changeRequest.faulty_product_serial_number==''){
      this._global.alert({type:'alert',message:'Please provide valid serial number'});
      return;
    }

    const pattern= new RegExp('^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$');
    
    if(!pattern.test(this._changeRequest.faulty_product_serial_number)){
      this._global.alert({type:'alert',message:'Please provide valid serial number'});
      return;
    }
    if(this._changeRequest.faulty_product_serial_number.length>16||this._changeRequest.faulty_product_serial_number.length<1){
      this._global.alert({type:'alert',message:'Please provide valid serial number'});
      return;
    }
    if(confirm(`Are you sure to submit the records!`)){
      this._ngx.show();
      this._partchage.approveById(this._changeRequest.id,this._changeRequest).subscribe(data=>{
        this._ngx.hide();
        this.onUpdate.emit();
      },error=>{
        this._ngx.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
    
  }

}
