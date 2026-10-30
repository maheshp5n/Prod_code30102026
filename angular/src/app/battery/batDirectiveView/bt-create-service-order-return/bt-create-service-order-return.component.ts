import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtServiceOrderReturnService } from '../../bat_services/bt-service-order-return.service';
import { BtServiceOrderReturnItems, BtServiceOrderReturnRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-create-service-order-return',
  templateUrl: './bt-create-service-order-return.component.html',
  styleUrls: ['./bt-create-service-order-return.component.css']
})
export class BtCreateServiceOrderReturnComponent implements OnInit {
  btServiceOrderReturnReq : BtServiceOrderReturnRequest = new BtServiceOrderReturnRequest();
  btLocationList : Array<any> = [];
  btSoReturnItemsList : Array<any> = [];
  destinationLocatonId  : string = '0';
  globalcheck=false;

  @Output() onSave:EventEmitter<any> = new EventEmitter();
  @Output() onCancel:EventEmitter<any> = new EventEmitter();

  constructor(private _global : GlobalserviceService,private _btServiceOrderReturn : BtServiceOrderReturnService,private _spineer : NgxSpinnerService) { }

  ngOnInit(): void {
    this.getPendingLocation();
  }

  getPendingLocation(){
    this._spineer.show();
    this._btServiceOrderReturn.getPendingLocation().subscribe(data=>{
      this._spineer.hide();
      this.btLocationList = data;
      if(this.btLocationList.length==1){
        this.btServiceOrderReturnReq.destinationLocationId=this.btLocationList[0].locationId;
      }
    },err=>{
      this._spineer.hide();
    })
  }
  getIdOfDestinationLocation(destinationLocatonId){
    this.btServiceOrderReturnReq.destinationLocationId = destinationLocatonId;
    this.getItemsByLocationId();
  }

  getItemsByLocationId(){
    this._spineer.show();
    this._btServiceOrderReturn.getPendingItems(this.btServiceOrderReturnReq.destinationLocationId).subscribe(data=>{
      this._spineer.hide();
      this.btSoReturnItemsList = data;
      this.btSoReturnItemsList.forEach(d=>d.checked=false);
    },err=>{
      this._spineer.hide();
    });
  }



  postServiceOrderReturn(){
    if(this.btServiceOrderReturnReq.destinationLocationId==undefined || this.btServiceOrderReturnReq.destinationLocationId==null||this.btServiceOrderReturnReq.destinationLocationId==0){
      this._global.alert({type:'alert',message : 'Please Fill Destination Location'});
      return;
    }
    
    const lent=this.btSoReturnItemsList.length;
    debugger;
    this.btServiceOrderReturnReq.items=[];
    for(let i=0;i<lent;i++){
      if(this.btSoReturnItemsList[i].checked){
        const ite=new BtServiceOrderReturnItems();
        ite.serviceOrderItemId=this.btSoReturnItemsList[i].serviceOrderItemId;
        this.btServiceOrderReturnReq.items.push(ite);        
      }
      
    }

    if(this.btServiceOrderReturnReq.items.length==0){
      this._global.alert({type:'alert',message:'No Item Found To Return'});
      return;
    }
    if(this.btServiceOrderReturnReq.shippingMode=='0' || this.btServiceOrderReturnReq.shippingMode==undefined){
      this._global.alert({type:'alert',message : 'Please Fill Shipping Mode'});
      return;
    }
    if(this.btServiceOrderReturnReq.shippingTo=='' || this.btServiceOrderReturnReq.shippingTo==undefined){
      this._global.alert({type:'alert',message : 'Please Fill Shipping Name'});
      return;
    }
    if(this.btServiceOrderReturnReq.shippingMode=='' || this.btServiceOrderReturnReq.shippingMode==undefined){
      this._global.alert({type:'alert',message : 'Please Fill Tracking Number'});
      return;
    }
    this._spineer.show();
    this._btServiceOrderReturn.postReturn(this.btServiceOrderReturnReq).subscribe(data=>{
      this._spineer.hide();
      this.onSave.emit(data);
    },error=>{
      this._spineer.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });

  }
  cancelServiceOrderReturn(){
    this.onCancel.emit('Cancel');
  }

  onGlobalChange($event){
    this.btSoReturnItemsList.forEach(d=>d.checked=this.globalcheck);
  }

}
