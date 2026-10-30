import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { ProductsForList } from '../../services/model';
import { PartcodechangeService } from '../../services/partcodechange.service';
import { ProductsService } from '../../services/products.services';

@Component({
  selector: 'app-partcodechangenew',
  templateUrl: './partcodechangenew.component.html',
  styleUrls: ['./partcodechangenew.component.scss']
})
export class PartcodechangenewComponent implements OnInit {

  consumedpartcodeList=[];
  saId:0;
  postData={
    consumption_part_code:'',
    consumption_serial_number:'',
    faulty_part_code:'',
    faulty_serial_number:'',
    conmuption_id:0
  };

  item_to_show;

  productItemList:Array<ProductsForList>=[];
  
  @Output() onCancel:EventEmitter<any>=new EventEmitter();
  @Output() onSave:EventEmitter<any>=new EventEmitter();

  constructor( 
    private _partchangeservice:PartcodechangeService,
    private _global:GlobalserviceService,
    private _ngx:NgxSpinnerService,
    private _productserivice:ProductsService
    ) { }

  ngOnInit() {
    this.getEligiablepartcode();
  }


  getEligiablepartcode(){
    this._ngx.show();
    this._partchangeservice.getEligableForchange().subscribe(data=>{
      this._ngx.hide();
      this.consumedpartcodeList=data;
    },error=>{
      this._ngx.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

  changeSa(id){
    const len=this.consumedpartcodeList.length;
    for(let i=0;i<len;i++){
      if(this.consumedpartcodeList[i].id==id){
        this.item_to_show=this.consumedpartcodeList[i];
        this.postData.conmuption_id=id;
        this.postData.consumption_part_code=this.consumedpartcodeList[i].faulty_product_product_part_code;
        this.postData.consumption_serial_number=this.consumedpartcodeList[i].faulty_product_serial_number;
        this.postData.faulty_part_code=this.consumedpartcodeList[i].faulty_product_product_part_code;
        this.postData.faulty_serial_number=this.consumedpartcodeList[i].faulty_product_serial_number;
        this.productItemList=[];
        this.productItemList.push({productPartCode:this.postData.faulty_part_code,productDescription:this.consumedpartcodeList[i].faulty_part_code});
        break;
      }
    }
  }

  saveDetails(){
    this._ngx.show();
    this._partchangeservice.createNewWorkFlow(this.postData).subscribe(data=>{
      this._ngx.hide();
      this.onSave.emit();
    },error=>{
      this._ngx.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

  onCancelEvent(){
    this.onCancel.emit();
  }
  
  searchProduts($event){
    this._productserivice.getSmallForlist($event.term).subscribe(data=>{
      this.productItemList=data;
    });
  }

}
