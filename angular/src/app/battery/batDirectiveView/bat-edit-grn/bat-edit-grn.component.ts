import { Component, OnInit,Input, Output,EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtCustomerInventoryService } from '../../bat_services/bt-customer-inventory.service';
import { EditGrnRequest } from '../../bt_model';

@Component({
  selector: 'app-bat-edit-grn',
  templateUrl: './bat-edit-grn.component.html',
  styleUrls: ['./bat-edit-grn.component.css']
})
export class BatEditGRNComponent implements OnInit {
editGrnReq : EditGrnRequest = new EditGrnRequest();
dateCustomClasses: DatepickerDateCustomClasses[];
today = new Date();
outId;
  @Input()
  set grnDetails(grn) {
    this.outId = grn.customerInventoryInId;
  }

  get grnDetails(): any {
     return this.editGrnReq;
  }
  @Output()
  onSaveOut: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancelGrn: EventEmitter<String> = new EventEmitter<string>();
  constructor(private _spinner : NgxSpinnerService,private _grnServie : BtCustomerInventoryService,private _global : GlobalserviceService) { }

  ngOnInit(): void {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  updateGrn(){
    if(this.editGrnReq.grnDate == ''|| this.editGrnReq.grnDate == undefined){
      this._global.alert({type:'alert',message : 'Please Fill GRN Date'});
     return; 
    }
    else if(this.editGrnReq.grnNumber == ''|| this.editGrnReq.grnNumber == undefined){
      this._global.alert({type:'alert',message:'Please Fill GRN No.'});
      return;
    }
    this._spinner.show();
    this._grnServie.editGrn(this.outId,this.editGrnReq).subscribe(data=>{
      this._spinner.hide();
      this.onSaveOut.emit(data);
    },err=>{
      this._spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});
    })

  }
  cancelGrn(){
    this.onCancelGrn.emit('cancel');
  }
  

}
