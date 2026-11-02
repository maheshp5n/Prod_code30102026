import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { NgxSpinner } from 'ngx-spinner/lib/ngx-spinner.enum';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCASEPODLIST, BT_CaseReasonForList, BT_CaseTypeForList } from '../../bt_model';
import { BtCaseRejectModule } from '../bat-reject-case/bat-reject-case.module';

@Component({
  selector: 'app-casetype-caseresn-popup',
  templateUrl: './casetype-caseresn-popup.component.html',
  styleUrls: ['./casetype-caseresn-popup.component.scss']
})
export class CasetypeCaseresnPopupComponent implements OnInit {
  @ViewChild('createPD') createPD:ModalDirective;
  caseReasonId: any;
  caseTypeId: string="";
  casetypeid:number
  caseReason:string="";
  casetypeNameofpod:string;
  newcaseReasonForList:Array<BT_CaseReasonForList>=[];
  caseTypeList:Array<BT_CaseTypeForList>=[];
  caseListPOD:Array<BTCASEPODLIST>=[]
  caseselectlist:Array<any>=[];
  selectedId:number;
  caseidselect:boolean;
  allselect:boolean=false;
  isEditMode:boolean=false;
  brcRepairCase:boolean=false;
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor( private spinner:NgxSpinnerService,private btcaseservice:BtCaseserviceService,private _global:GlobalserviceService) {
  }

  ngOnInit() {
    this.caseType();
  }

  getCaseReason(caseTypeId) {

    this.spinner.show();
    this.btcaseservice.getCaseReason(caseTypeId)
    .subscribe((data) => {
      if (data != null) {
        this.spinner.hide();
      }
      this.newcaseReasonForList = data;
        if (caseTypeId === 1) {
          this.caseReason = "Audit";
          return;
        }
        if (caseTypeId === 2) { 
          this.caseReason = "Installation";
         
          return;
        }
        if (caseTypeId === 3) {
        
          this.caseReason = "Corrective Maintenance"
          return;
        }
        if (caseTypeId=== 4) {
          this.caseReason = "Corrective Maintenance";
          return;
        }
        if(caseTypeId==11){
        this.caseReason=  "Manpower"
        }
      
    }, error => {
      this.spinner.hide();
    });
  
  }


  getCasereasonchange(caseTypeId) {
    console.log(caseTypeId);
  
    // this.res.caseTypeName=
    if(caseTypeId==1){
      this.casetypeNameofpod="Exceptional Case";
      this.getCaseReason(caseTypeId);
      return;
     
    }
    else if(caseTypeId==2){
      this.casetypeNameofpod="Installation";
      this.getCaseReason(caseTypeId);
      return;
    }
    else if(caseTypeId==3){
    this.casetypeNameofpod="Under Warranty";
    this.getCaseReason(caseTypeId);
    return;
    }
    else if(caseTypeId==4){
      this.casetypeNameofpod="Without Warranty";
      this.getCaseReason(caseTypeId);
    }
    else if(caseTypeId==11){
      this.casetypeNameofpod="Manpower";
      this.getCaseReason(caseTypeId);
      return;
    }
    this.caseReason="";
    this.caseListPOD=[];
    console.log( this.casetypeNameofpod);
    
    
 }

    
caseType() {
  this.btcaseservice.getCaseTypes(1)
  .subscribe(data => {
    this.caseTypeList = data;
  });

  this.caseReason="";
  this.caseListPOD=[];
}
  findCase(reason){
   if (reason!=undefined && this.casetypeNameofpod!=undefined){
        this.spinner.show();
        this.btcaseservice.getCasebyTypereason(this.casetypeNameofpod,reason).subscribe(data=>{
          this.spinner.hide();
          this.caseListPOD=data;
          this.caseselectlist=[];
        },error=>{
          this.spinner.hide();
          this._global.alert({type:'alert',message:error.error.message});
        })
   }
   else{
    this._global.alert({type:'alert',message:'Please select case type OR Case Reason'});
   }
  }

  cancelinvPOD(){
    this.caseTypeId='';
    this.caseReason='';
    this.caseListPOD=[];
    this.allselect=false;
    this.cancelPDData();
    this.newcaseReasonForList=[];
    this.caseType();

  }
  submittopod(){
    if(this.caseselectlist.length==0){
      this._global.alert({type:'alert',message:"Please choose atleast one case"});
    }else
      {
        if(this.caseListPOD[0].caseReason=="BRC Repair")
        {
          this.brcRepairCase=true;
        }
        else
        {
          this.brcRepairCase=false;
        }
         this.createPD.show();
         }
 
   
   }

   recalculateall(event){
    this.caseselectlist=[];
    this.allselect=true;
    // const index = this.caseListPOD.indexOf(event);
     if(event.target.checked==true)
     { this.caseListPOD.forEach(el=>{
        
        el.caseidselected= true;
        this.caseselectlist.push(el.id);
      
      })}
      else{
        this.allselect=false;
        this.caseselectlist=[];
        this.caseListPOD.forEach(el=>{
        
          el.caseidselected= false;
          // this.caseselectlist.splice(el.id);
        
        })
      }
   }
   recalculate($event,sa:BTCASEPODLIST){
    this.allselect=false;
    const index = this.caseListPOD.indexOf(sa);
    if(sa.caseidselected==true){
      this.caseselectlist.push(sa.id);
    }
    else{
      this.caseselectlist.splice(index,sa.id);
     }
  
  }
  backclik(ids){
    this.caseselectlist=[]
     ids.forEach(e=>{
     const templist=[]
      this.caseListPOD.forEach(el=>{
        
        if(e==el.id)
        el.caseidselected= true;
        // el.id=e
      
      })
      // this.caseselectlist=templist;
      this.caseselectlist.push(e);
    })
    
   
    this.createPD.hide();
  }
  onCasePDCreated($event){
    // this.showCase();
 
    this.createPD.hide();
    // this.caseforpodupdate.hide();

  }
  cancelPDData(){
    this.createPD.hide();
    // this.caseselectlist=[];
    this.onCancle.emit('canceld');
    this.caseReason="";
    this.caseListPOD=[];
    this.caseTypeList=[];
    this.caseType();

    this.caseListPOD.forEach(e=>{
      e.caseidselected=false;
    })
    
  }
}
