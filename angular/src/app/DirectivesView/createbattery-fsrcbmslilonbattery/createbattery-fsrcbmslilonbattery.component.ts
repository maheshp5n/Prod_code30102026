import { Component, OnInit, ViewChild, Input, EventEmitter, Output } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AssetsRequest, BatteryFSRResponse, CaseForList, ProblemReported, BatterySiteType, PendingResponsibility, BatteryComplaintStatus, BatteryFSRRequest } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { caseService } from '../../services/caseService.service';
import { TypeaheadConfig, TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import { FieldValidation } from '../../services/validation.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-createbattery-fsrcbmslilonbattery',
  templateUrl: './createbattery-fsrcbmslilonbattery.component.html',
  styleUrls: ['./createbattery-fsrcbmslilonbattery.component.scss']
})
export class CreatebatteryFSRCBMSLilonbatteryComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  BFSRres: BatteryFSRResponse = new BatteryFSRResponse();
  BFSRreq:BatteryFSRRequest=new BatteryFSRRequest();
  message: string;
  istrue: boolean;
  noMatchCase: string;
  iscaseSelected:string;
  problemReportedDetails:Array<ProblemReported>=[];
  pendingres: Array<PendingResponsibility>;
  complainStatusList:Array<BatteryComplaintStatus>;
  siteTypeList:Array<BatterySiteType>
  popupNgClass: string = 'alert alert-danger center center-block';
  tempManualId:string;
  tempId:number;
  today=new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  getTypeaheadConfig(): TypeaheadConfig {
    return Object.assign(new TypeaheadConfig(), { hideResultsOnBlur: false });
  }
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(BFSRres: BatteryFSRResponse) {
    this.BFSRres = BFSRres;
    this.BFSRres.dateOfVisit=this.BFSRres.dateOfVisit;
  }

  get editAccountData(): BatteryFSRResponse {
    return this.BFSRres;
  }
  @Input()
  set problemReportedArray(problemReportedDetails:Array<ProblemReported>){
    this.problemReportedDetails=problemReportedDetails;
  }
  get problemReportedArray():Array<ProblemReported>{
    return this.problemReportedDetails;
  }

  @Output()
  onBFSRCreated: EventEmitter<BatteryFSRRequest> = new EventEmitter<BatteryFSRRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private datePipe: DatePipe,private spinner:NgxSpinnerService,private loginService: Loginservices,private BFSRservice:BatrryFSRService,private caseservice:caseService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
   }

  ngOnInit() {
    this.iscaseSelected='fa fa-search';
    this.ownerName();
    this.siteType();
    this.Risponsibility();
    this.getComplainStatus();
    if(this.isEditMode===true){
      this.BFSRres=new BatteryFSRResponse();
    }
  
  }

  siteType(){
    this.BFSRservice.getBattrySiteType().subscribe(data=>{
   this.siteTypeList=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    }
    )
  }
  Risponsibility() {
    this.caseservice.pendingrisponsibility().subscribe(data => {
      this.pendingres = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    }
    );
  }
  
  getComplainStatus(){
    this.BFSRservice.getComplainStatus().subscribe(data=>{
   this.complainStatusList=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    }
    )
  }

  loginData;
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }
  
  
  postCreateBFRS(){
   if(this.BFSRres.technicianContact===undefined || FieldValidation.isEmpty(this.BFSRres.technicianContact)){
     this.message='Please Fill Technitain Contact Number';
     this.popupSuccessModel.show();
    }
    else if(FieldValidation.isValidMobileNumber(this.BFSRres.technicianContact)===false){
        this.message='Please Fill Technitain Contact Number in 10 digits';
        this.popupSuccessModel.show();
     }
     else if(this.BFSRres.complaintStatusId===null){
       this.message="Please Fill Complain Status";
       this.popupSuccessModel.show();
     }
     else if(this.BFSRres.dateOfVisit==undefined){
       this.message="Please Fill Date Of Visit";
       console.log("Date is null");
       this.popupSuccessModel.show();
     } 
    else{
      this.istrue = true;
      if(!this.isEditMode){
        this.BFSRreq.id=this.BFSRres.id;
        this.BFSRreq.serviceAppointmentId=this.BFSRres.serviceAppointmentId;
        this.BFSRreq.dateOfVisit=this.BFSRres.dateOfVisit;
         this.BFSRreq.vendorEngName=this.BFSRres.vendorEngName;
         this.BFSRreq.vendorEngContact=this.BFSRres.vendorEngContact;
         this.BFSRreq.faultReportedByCustomer=this.BFSRres.faultReportedByCustomer;
         this.BFSRreq.faultObservedByEngineer=this.BFSRres.faultObservedByEngineer;
         this.BFSRreq.siteTypeId=this.BFSRres.siteTypeId;
         this.BFSRreq.siteEnvCond=this.BFSRres.siteEnvCond;
         this.BFSRreq.ebavailability=this.BFSRres.ebavailability;
         this.BFSRreq.dgrun=this.BFSRres.dgrun;
         this.BFSRreq.dgrating=this.BFSRres.dgrating;
         this.BFSRreq.earthingStatus=this.BFSRres.earthingStatus;
         this.BFSRreq.dcload=this.BFSRres.dcload;
         this.BFSRreq.powerPlantMakeAndCapacity=this.BFSRres.powerPlantMakeAndCapacity;
         this.BFSRreq.ambientTemp=this.BFSRres.ambientTemp;
         this.BFSRreq.softVersionCBMS=this.BFSRres.softVersionCBMS;
         this.BFSRreq.batteryVoltageBeforRepair=this.BFSRres.batteryVoltageBeforRepair;
         this.BFSRreq.batteryVoltageAfterRepair=this.BFSRres.batteryVoltageAfterRepair;
         this.BFSRreq.correctiveAction=this.BFSRres.correctiveAction;
         this.BFSRreq.sparePartReplaced=this.BFSRres.sparePartReplaced;
         this.BFSRreq.pendingResponsibilityId=this.BFSRres.pendingResponsibilityId;
         this.BFSRreq.customerOrSiteProblem=this.BFSRres.customerOrSiteProblem;
         this.BFSRreq.complaintStatusId=this.BFSRres.complaintStatusId;
         this.BFSRreq.otherPendingIssue=this.BFSRres.otherPendingIssue;
         this.BFSRreq.technicianContact=this.BFSRres.technicianContact;
         this.BFSRreq.problemCustomerOrExicom=this.BFSRres.problemCustomerOrExicom;
         this.BFSRreq.remark=this.BFSRres.remark;
         this.spinner.show();
         this.BFSRservice.saveBattryFSR(this.BFSRreq).subscribe(data=>{
          this.spinner.hide();
          this.onBFSRCreated.emit(data);
          this.tempId=data.id;
          this.tempManualId=data.manualId;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.popupSuccessModel.show();
         },error=>{
          this.spinner.hide();
          this.message=error.error.message;
          this.popupSuccessModel.show();
        });
      }
      else{
        // put mapping
        this.BFSRreq.id=this.BFSRres.id;
        this.BFSRreq.serviceAppointmentId=this.BFSRres.serviceAppointmentId;
        this.BFSRreq.dateOfVisit=this.BFSRres.dateOfVisit;
         this.BFSRreq.vendorEngName=this.BFSRres.vendorEngName;
         this.BFSRreq.vendorEngContact=this.BFSRres.vendorEngContact;
         this.BFSRreq.faultReportedByCustomer=this.BFSRres.faultReportedByCustomer;
         this.BFSRreq.faultObservedByEngineer=this.BFSRres.faultObservedByEngineer;
         this.BFSRreq.siteTypeId=this.BFSRres.siteTypeId;
         this.BFSRreq.siteEnvCond=this.BFSRres.siteEnvCond;
         this.BFSRreq.ebavailability=this.BFSRres.ebavailability;
         this.BFSRreq.dgrun=this.BFSRres.dgrun;
         this.BFSRreq.dgrating=this.BFSRres.dgrating;
         this.BFSRreq.earthingStatus=this.BFSRres.earthingStatus;
         this.BFSRreq.dcload=this.BFSRres.dcload;
         this.BFSRreq.powerPlantMakeAndCapacity=this.BFSRres.powerPlantMakeAndCapacity;
         this.BFSRreq.ambientTemp=this.BFSRres.ambientTemp;
         this.BFSRreq.softVersionCBMS=this.BFSRres.softVersionCBMS;
         this.BFSRreq.batteryVoltageBeforRepair=this.BFSRres.batteryVoltageBeforRepair;
         this.BFSRreq.batteryVoltageAfterRepair=this.BFSRres.batteryVoltageAfterRepair;
         this.BFSRreq.correctiveAction=this.BFSRres.correctiveAction;
         this.BFSRreq.sparePartReplaced=this.BFSRres.sparePartReplaced;
         this.BFSRreq.pendingResponsibilityId=this.BFSRres.pendingResponsibilityId;
         this.BFSRreq.customerOrSiteProblem=this.BFSRres.customerOrSiteProblem;
         this.BFSRreq.complaintStatusId=this.BFSRres.complaintStatusId;
         this.BFSRreq.otherPendingIssue=this.BFSRres.otherPendingIssue;
         this.BFSRreq.technicianContact=this.BFSRres.technicianContact;
         this.BFSRreq.problemCustomerOrExicom=this.BFSRres.problemCustomerOrExicom;
         this.BFSRreq.remark=this.BFSRres.remark;
         this.spinner.show();
         this.BFSRservice.updateBattryFSR(this.BFSRreq).subscribe(data=>{
           this.spinner.hide();
           this.onBFSRCreated.emit(data);
           this.tempId=data.id;
           this.tempManualId=data.manualId;
           this.message = 'Updated Successfully';
           this.popupNgClass = 'alert alert-success center center-block';        
           this.popupSuccessModel.show();
         },error=>{
          this.spinner.hide();
          this.message=error.error.message;
          this.popupSuccessModel.show();
        });
       }
     
    }

    

  }

  cancelData() {
    //this.assetRequest = new AssetsRequest();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  



 


  

   
}
