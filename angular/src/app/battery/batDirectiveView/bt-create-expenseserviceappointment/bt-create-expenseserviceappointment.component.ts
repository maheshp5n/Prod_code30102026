import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { TourExpenseRequest } from '../../../services/model';
import { BtexpenseServiceappointmentService } from '../../bat_services/btexpense-serviceappointment.service';
import { btExpenseCostCenter, btExpSA, btExpServiceAppointmentlist, btExpServiceAppointmentReq, btExpServiceAppointmentResp, Page } from '../../bt_model';

@Component({
  selector: 'app-bt-create-expenseserviceappointment',
  templateUrl: './bt-create-expenseserviceappointment.component.html',
  styleUrls: ['./bt-create-expenseserviceappointment.component.css']
})
export class BtCreateExpenseserviceappointmentComponent implements OnInit {

    // Tour starts
    @Input()
    set isEditModeExpSA(isEditModeExpSA: boolean) {
      this._isEditModeExpSA = isEditModeExpSA;
    }
  
    get isEditModeExpSA(): boolean {
      return this._isEditModeExpSA;
    }
  
    @Input()
    set editSAExpData(exp: btExpServiceAppointmentResp) {
      if (this._isEditModeExpSA === false) {
        this.expresp = new btExpServiceAppointmentResp();
        // this.exp.btExpId = exp.btExpenseManagementId;
        this.getAllDataClaim();
      }
      this.expresp = exp;
      this.expClaim=[];
      this.expClaim.push({btServiceAppointmentId:this.expresp.btServiceAppointmentId, caseId:this.expresp.caseId, btServiceAppointmentManualId:this.expresp.btServiceAppointmentManualId,caseManualId: this.expresp.caseManualId, circleName: this.expresp.circleName, siteId:this.expresp.siteId, siteName: this.expresp.siteName, claimAmount: this.expresp.claimAmount.toString(), isChecked: true, totalexp: this.expresp.claimAmount})
      this.exp.costCenter=this.expresp.costCenter;
      this.totalExp=this.expresp.claimAmount;
      this.exp.btExpId = this.expresp.btExpenseManagementId;

      // this.exp.expenseManagementManualId = otherChargesRequest.btExpenseManagementManualId;
  
  
      // if (this.otherChargesRequest.businessDomainId !== 0) {
      //   this.getOtherChargesTypeList(this.otherChargesRequest.businessDomainId);
      // }
    }
  
    get editSAExpData(): btExpServiceAppointmentResp {
      return this.expresp;
    }
  //   @Input()
  // set LocalamntToDisbursed(localamountToDisbursed:number){
  //   this.otherChargesRequest.amountToDisbursed=localamountToDisbursed;
  // }
  // get BodamntToDisbursed():number{
  //   return this.otherChargesRequest.amountToDisbursed;
  // }

  public _isEditModeExpSA: boolean;

  message: string;
  expClaim: Array<btExpServiceAppointmentlist> = [];
  expcostCenter: Array<btExpenseCostCenter>=[];
  totalExp:number=0;
  claimexps=[];
  expresp:btExpServiceAppointmentResp = new btExpServiceAppointmentResp();
  exp:btExpServiceAppointmentReq = new btExpServiceAppointmentReq();
  btExpSA:Array<btExpSA>=[];
  accepted:boolean=false;

  @Input()
  set expenseId(expenseId:number){
    this.exp.btExpId=expenseId;
  }
  get expenseId():number{
    return this.exp.btExpId;
  }

  
  @Input()
  set totalAmt(totalExp:number){
    this.exp.totalExp=totalExp;
  }
  get totalAmt():number{
    return this.exp.totalExp;
  }
  @Input()
  set costCenter(_costCenter:string){
    this.exp.costCenter=_costCenter;
  }
  get costCenter():string{
    return this.exp.costCenter;
  }
  

  @Output()
  onCancleSA: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onExpSACreated: EventEmitter<btExpServiceAppointmentResp> = new EventEmitter<btExpServiceAppointmentResp>();

  constructor(private _global: GlobalserviceService, private serviceappointmentexp:BtexpenseServiceappointmentService,private spinner: NgxSpinnerService ) { }

  ngOnInit(): void {
    // this.getBoardingExpenseDetails();
    // this.getCostCenter();
    // this.getData();
    if(this._isEditModeExpSA==false)
    {
    this.getAllDataClaim();
    }
    console.log(this.exp)
  }

  // getBoardingExpenseDetails() {
  //   this.spinner.show();
  //   this.serviceappointmentexp.getServiceAppointmentExpenseDetails(this.exp.btExpId).subscribe((data) => {
  //     this.spinner.hide();
  //     // this.expClaim=data;
  //   });
  // }

  // getCostCenter()
  // {
  //   this.serviceappointmentexp.getcostcenter().subscribe(data=>{
  //     this.expcostCenter=data;
  //   })

  // }

  getData()
  {
    this.spinner.show();
    this.serviceappointmentexp.getServiceAppointmentExpenseDetailsById(this.exp.btExpId).subscribe((data) => {
      this.spinner.hide();
      // this.expClaim=data;
      console.log(data)

    });
  }

  getAllDataClaim()
  {
    this.serviceappointmentexp.getServiceAppointmentExpClaim().subscribe(data =>{
      console.log(data)
      this.expClaim=data;
      this.expClaim.forEach(d=>{
        d.claimAmount=undefined;
        d.totalexp=0;
      })
    }
    )
 
  }

  checkedfield(ex:btExpServiceAppointmentlist,event)
  {

    //False all on chnage
    // for(let i=0;i<this.expClaim.length;i++)
    // {
    // this.expClaim[i].isChecked=false;
    // console.log(this.expClaim[i].isChecked);
    
    // }
    console.log(event.target.checked);
    
     
       for(let i=0;i<this.expClaim.length;i++)
         {
         
          if(this.expClaim[i].btServiceAppointmentManualId==ex.btServiceAppointmentManualId)
          {
             if(event.target.checked)
             {

               this.expClaim[i].isChecked=true;
               console.log(this.expClaim);
              }else{
                this.expClaim[i].isChecked=false;
                console.log(this.expClaim);

              }
            }
            
            // this._global.alert({ type:'alert', message: 'First You Have To Fill Claim Amount = '+i});
            // this.expClaim[i].isChecked=false;

          }
          
        }
      
      // else
      // {
      //   this.totalExp=0;
      //   for(let i=0; i<this.expClaim.length;i++)
      //   {
      //     if(this.expClaim[i].isChecked==true)
      //     {
      //      if(this.expClaim[i].claimAmount!=undefined)
      //      {
      //       this.totalExp=this.totalExp+parseFloat(this.expClaim[i].claimAmount);
      //      }
      //     }
      //   }
        // this.expClaim.forEach(e=>{
        //   if(e.isChecked==true)
        //   {
        //     console.log(e.isChecked)

        //   if(e.claimAmt!=undefined)
        //   {
        //     this.totalExp=this.totalExp+e.totalexp+parseFloat(e.claimAmt);
        //   }
        // }
      
        //   console.log(e.isChecked)
        //   if(e.claimAmt!=undefined)
        //   {
        //   this.totalExp=this.totalExp+e.totalexp+parseFloat(e.claimAmt);
        //   }
    
        
        //   console.log(this.totalExp)

        // })
     
       
      // }
   
  // checkedfield2(event){
  //  var tag =  event.target;
  //  tag.checked = false;
   
    

  // }
  checked:number=0;
  claimAmount()
  {


   
    this.btExpSA=[];
    this.exp.totalClaim=0;
    this.exp.btServicAppointmentExp= [];

   
 
    for(let i=0; i<this.expClaim.length;i++)
    {
    
      if(this.expClaim[i].isChecked==true)
      {
        this.checked++;
        console.log(this.checked);
        
      }
    }

    for(let i=0; i<this.expClaim.length;i++)
    {
      
      console.log(this.expClaim[i].isChecked);
      
    
      if(this.expClaim[i].isChecked==true)
      {
        console.log(this.expClaim);
        
        this.exp.btServicAppointmentExp.push({btServicAppointmentId:this.expClaim[i].btServiceAppointmentId.toString(),claimAmount:'0',amountToDisbursed:'0',id:this.exp.btExpId});
        console.log(this.exp.btServicAppointmentExp);
        
       if(this.expClaim[i].claimAmount!=undefined)
       {
        this.exp.totalClaim=this.exp.totalClaim+0;    
       }
      }
      else{
        this._global.alert({ type: 'alert', message: "Please Fill Atleast One Service Appointment To Claim" });

      }

    }

    // if(this.exp.costCenter=='')
    // {
    //   this._global.alert({ type: 'alert', message: "Please Fill Cost Center" });
    // }
  
       
    if(this.checked>0)
    {
      if(this._isEditModeExpSA== false)
      {
        console.log(this.exp);
      this.spinner.show();
      this.serviceappointmentexp.postExpenseSA(this.exp).subscribe(data=>{
        this.spinner.hide();
        this.onExpSACreated.emit(data);
        this._global.alert({ type: '', message: "Updated Successfully" });
        this.onCancleSA.emit('cancled');

      },err=>{
        this._global.alert({ type: 'alert', message: err.error.message });
        this.spinner.hide()
      })
      console.log("ADD")

    // console.log(this.exp)
    }
  }
   
     

    //     console.log(this.exp);
        
    //     this.spinner.show();
    //     this.serviceappointmentexp.updateExpenseSA(this.exp).subscribe(data=>{
    //       this.spinner.hide();
    //     this.onExpSACreated.emit(data);
    //     this._global.alert({ type: '', message: "Updated Successfully" });
    //     this.onCancleSA.emit('cancled');
    //   },err=>{
    //     this._global.alert({ type: 'alert', message: err.error.message });
    //     this.spinner.hide()
    //   })
    // }
    // else{
    //     this._global.alert({ type: 'alert', message: "Please Fill Atleast One Service Appointment To Claim" });

    //   }
    


    
  }
  onlyNumbersForRs(key): any {

    const charCode = (key.which) ? key.which : key.keyCode;
    console.log("Only number for rs function");
    if(charCode>31 && charCode==46){
      return true; 
     }
    else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
      return false;
    }
    else if(key.target.value.length>5){
      this.message="More Than 5 Digits Can Not Be Accepted";
      this._global.alert({ type: 'alert', message: this.message });
     return false;
   }
   
  }

}
