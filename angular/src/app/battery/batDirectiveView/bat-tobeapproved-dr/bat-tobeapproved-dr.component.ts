import { Component, OnInit,Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';
import { BTApprovalRequest, BTToBeApproved } from '../../bt_model';

@Component({
  selector: 'app-bat-tobeapproved-dr',
  templateUrl: './bat-tobeapproved-dr.component.html',
  styleUrls: ['./bat-tobeapproved-dr.component.scss']
})
export class BatTobeapprovedDrComponent implements OnInit {

 
  @ViewChild('approvalModule', { static: false }) approvalModule: ModalDirective;

  _aprdata;
  btnshow:boolean=false;
  objName:string;
  objId:number;
  objmanualId:string;
  toBeApprovedSelected: BTToBeApproved = new BTToBeApproved();
  approvalRequest: BTApprovalRequest = new BTApprovalRequest();

  @Input() set objectName(_Objdata){
      this.objName = _Objdata;
console.log(this.objName);

  }
  get objectName(){
    return this.objName;
  }
  @Input() set _objectId(objId)
  {
    
    this.objId = objId;
    this.actiondata.id = this.objId;
    console.log("object Input "+this.objId);
    console.log(objId);
 
  }

  get _objectId(){
    
    return  this.objId;
  }
  @Input() set manualId(_manualId)
  {
    this.objmanualId = _manualId;
  }
  get manualId(){
    return this.objmanualId;
  }
  
  actiondata={
    action:'00',
    remarks:undefined,
    id:0
  }


  @Output() onSaveDetails:EventEmitter<any>=new EventEmitter();

  @Input() set approvaldata(_data){
    this._aprdata=_data;
    console.log("Approved Data "+this.objId);

    if(this.objId != undefined && this.objId!=null && this.objId != 0)
    {
      this.actiondata.id = this.objId; 
      console.log("if "+this.actiondata.id);
    }
    else{
      this.actiondata.id = _data.id;
      console.log("else "+this.actiondata.id);
    }
    
  }

  @Output()onCancel:EventEmitter<any>=new EventEmitter();
  get approvaldata(){
    this.approvalModule.show();
    return this._aprdata;
  }

  constructor(private tobeapproved:BtTobeApprovedService,private _global:GlobalserviceService, private spinner: NgxSpinnerService,) {
  
   }

  ngOnInit() {
    this.btnshow=true;
   
    // this.approvalModule.show();

  }
  // getApprovalButton() {
  //   this.spinner.show();
  //   // console.log(this.objectName);
  //   // console.log(this.objId);
    
  //   this.tobeapproved.getItemToBeApprovdButton(this.objectName, this.objId).subscribe(data => {
  //     console.log(data);
  //     this.spinner.hide();
  //     if( data.id != null && data != null && data != undefined)
  //     {

  //       this.btnshow = true;
  //       this.toBeApprovedSelected = data;
  //       this.approvalRequest.approvalId = data.id;
  //       this.actiondata.id = data.id;
  //       // console.log(this.actiondata.id);
  //       // console.log(this.approvalRequest);
        
  //     }
      
  //     else {
  //       this.btnshow = false;
  //     }
  //   }, error => this.btnshow = false);
  // }

  saveDetails(){
    // this.getApprovalButton();
    if ( this.actiondata.id == undefined ||  this.actiondata.id == null ||  this.actiondata.id == 0) {
      this._global.alert({type:'alert',message:'Some thing went wrong,please reload the page'});
      return;
    }
    if(this.actiondata.action==='00'){
      this._global.alert({type:'alert',message:'Please Select Action'});
      return;
    }
    if(this.actiondata.remarks==undefined || this.actiondata.remarks==null || this.actiondata.remarks==''){
      this._global.alert({type:'alert',message:'Please Fill Remarks'});
      return;
    }
    if(this.actiondata.id==0){
      this._global.alert({type:'alert',message:'Something Went Wrong'});
      return;
    }

    
   this.spinner.show()
    this.tobeapproved.updateAction(this.actiondata.id,this.actiondata).subscribe(data=>{
  
        if(this.actiondata.action=="Approved")
        {
          this._global.alert({type:'',message:'Approved Successfully'});
        }
        else
        {
          this._global.alert({type:'',message:'Rejected Successfully'});
        }
      
        // this._global.alert({type:'',message:'Submitted Successfully'});
      
      this.spinner.hide();
      this.onSaveDetails.emit(data);
    },error=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });

    
  }


}
