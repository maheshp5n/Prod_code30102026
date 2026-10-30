import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatPartcodeChangeService } from '../../bat_services/bat-partcode-change-service';
import { BtproductService } from '../../bat_services/btproduct.service';

@Component({
  selector: 'app-bat-partcode-change-dir',
  templateUrl: './bat-partcode-change-dir.component.html',
  styleUrls: ['./bat-partcode-change-dir.component.scss']
})
export class BatPartcodeChangeDirComponent implements OnInit {

  @Output() onSave:EventEmitter<any> =new EventEmitter();
  @Output() onCancel:EventEmitter<any> =new EventEmitter();

  toProducts=[];
  fromProducts=[];
  chanageReq={
    fromProductId:0,
    toProductId:0,
    productStateId:0,
    qty:1,
    remarks:''
  }

  constructor(private _productsService:BtproductService,private _ngxspinner:NgxSpinnerService,private _partcodechange:BatPartcodeChangeService,private _global:GlobalserviceService) { }

  ngOnInit() {

  }

  getValueOfPartCodeFrom($event){
    this._ngxspinner.show();
    this._productsService.getSmallForlist($event.term).subscribe(data=>{
      try {
        this.fromProducts=data;
      } catch (error) {
        
      }
      this._ngxspinner.hide();
    },error=>this._ngxspinner.hide());
  }
  getValueOfPartCodeTo($event){
    this._ngxspinner.show();

    this._productsService.getSmallForlist($event.term).subscribe(data=>{
      try {
        this.toProducts=data;  
      } catch (error) {
        
      }
      this._ngxspinner.hide();
    },error=>this._ngxspinner.hide());
  }

  saveDetails(){
    if(this.chanageReq.fromProductId==undefined||this.chanageReq.fromProductId==null||this.chanageReq.fromProductId==0){
      this._global.alert({type:'alert',message:'Please select from partcode'});
      return;
    }
    if(this.chanageReq.toProductId==undefined||this.chanageReq.toProductId==null||this.chanageReq.toProductId==0){
      this._global.alert({type:'alert',message:'Please select to partcode'});
      return;
    }

    if(this.chanageReq.fromProductId==this.chanageReq.toProductId){
      this._global.alert({type:'alert',message:'Please select different product part code'});
      return;
    }
    if(this.chanageReq.qty==0){
      this._global.alert({type:'alert',message:'Please select quantity'});
      return;
    }
    if(this.chanageReq.productStateId==0){
      this._global.alert({type:'alert',message:'Please select product state'});
      return;
    }
    if(this.chanageReq.remarks==undefined||this.chanageReq.remarks==null||this.chanageReq.remarks==''){
      this._global.alert({type:'alert',message:'Please fill remarks'});
      return;
    }

    
    this._ngxspinner.show();
    this._partcodechange.postDetails(this.chanageReq).subscribe(data=>{
        this.onSave.emit('');
        this._ngxspinner.hide();
    },error=>{
      this._global.alert({type:'alert',message:error.error.message});
      this._ngxspinner.hide();
    });
  }
  cancelDetails(){
    this.onCancel.emit('Cancled');
  }


}
