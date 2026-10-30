import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtServiceOrderReturnService } from '../../bat_services/bt-service-order-return.service';

@Component({
  selector: 'app-bat-return-order-receive-dir',
  templateUrl: './bat-return-order-receive-dir.component.html',
  styleUrls: ['./bat-return-order-receive-dir.component.scss']
})
export class BatReturnOrderReceiveDirComponent implements OnInit {

  pendingdata=[];
  globalcheck=true;

  @Output() onSave:EventEmitter<any>=new EventEmitter();
  @Output() onCancel:EventEmitter<any>=new EventEmitter();

  constructor(private _global: GlobalserviceService, private _btsoreturnService: BtServiceOrderReturnService,private _spinner:NgxSpinnerService) { }

  ngOnInit() {
    this.getpendingItemToreceive();
  }

  getpendingItemToreceive(){
    this._spinner.hide();
    this._btsoreturnService.getpendingitemtoreceive().subscribe(data=>{
      try {
        this.pendingdata=data;
        this.pendingdata.forEach(d=>d.checked=true);  
      } catch (error) {
        
      }      
    },error=>this._spinner.hide());
  }

  onGlobalChange($event){
    this.pendingdata.forEach(d=>d.checked=this.globalcheck);
  }

  cancelDetails(){
    this.onCancel.emit('Cancel');
  }

  saveDetails(){
    
    const temp=[];
    const lent=this.pendingdata.length;
    for(let i=0;i<lent;i++){
      if(this.pendingdata[i].checked){
        temp.push({serviceOrderReturnItemId:this.pendingdata[i].itemReturnId});
      }
    }

    if(temp.length==0){
      this._global.alert({type:'alert',message:'Please select item to receive'});
      return;
    }
    this._spinner.show();
    this._btsoreturnService.receiveReturnOrder(temp).subscribe(data=>{
      try {
          this.onSave.emit(data);
      } catch (error) {
        
      }
      this._spinner.hide();
    },error=>{
      this._spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
    
  }
}
