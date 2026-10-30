
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Loginservices } from '../../../services/login.services';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtproductService } from '../../bat_services/btproduct.service';

import { AddBattery, AssetRequest, batteryCommisionFsr, BtActiveAlarm, BtAssetMasterUpdate, BTassetResp, BTAssetsForList, BTCaseForList, BTCaseResponse, BtCollingMethod, BtEquipmentSrNO, BtFsrCommissionDataGetById, BtFsrData, BtFsrServiceDataGetById, BtPendingReasonValue, BtPowerPlantMake, BtResolutionMethod, Btrow, BtserialNoArray, btServiceAppointmentResponseWithSCH, BtSiteEnvCondition, BtSiteType, BtSolutionImparted, BT_CaseGeneralINFO, BT_ProductForListDetail, EquipmentSrNO, ProductDetailResponse } from '../../bt_model';
import { BtFsrService } from '../../bat_services/bt-fsr.service';
import { GlobalserviceService } from '../../../globalservice.service';
import { DatePipe } from '@angular/common';
import { ServiceAppointmentResponse } from '../../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../../services/validation.services';
import { element } from 'protractor';
import * as moment from 'moment';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';




@Component({
  selector: 'app-bt-create-fsr-service',
  templateUrl: './bt-create-fsr-service.component.html',
  styleUrls: ['./bt-create-fsr-service.component.css']
})
export class BtCreateFsrServiceComponent implements OnInit {
cbmsNA: boolean = false;   //cbms00
  datediff:number;
  _caseStatusVal:boolean = true;
  btIndex: number;
  pendingBucketdemo:boolean=true;
  showFaultyRow:Boolean = false;
  equipmentSrNO:Array<EquipmentSrNO>=[];
  assetList: Array<BTAssetsForList>=[];
  cbmsAssetList: Array<BTAssetsForList>=[];
  assets:any;
  assests: BTassetResp = new BTassetResp();

  cbmsRowObj: AddBattery = new AddBattery();
  disabledMinusbtn: boolean = false;
  reasonDropdown: boolean = false;
  reasonText: boolean = false;
  disabledAddbtn: boolean = true;
  productDetailResponse: ProductDetailResponse;
  bt_caseGeneralINFO: BT_CaseGeneralINFO = new BT_CaseGeneralINFO();
  addBatteryrow: Array<AddBattery> = [];
  addFaultyBatteryObj: AddBattery = new AddBattery();
  btCaseResponse: BTCaseResponse =new BTCaseResponse();
  btCreateFsr: BtFsrData = new BtFsrData();
  btAssetMasterUpdate:Array<BtAssetMasterUpdate>=[];
  assetRequest: AssetRequest = new AssetRequest();
  assetCbms:AssetRequest = new AssetRequest();
  btEquipmentSrNO:Array<BtEquipmentSrNO>=[];

  res: BTCaseResponse = new BTCaseResponse();

  currentCondition: number;
  dunlicateElemnt:any = [];
  ngSelect:any;
  serialNoArray:Array<BtserialNoArray>=[];

  dateOfCaseGen: Date = new Date();
  dateCaseGen: string;
  // selectedBattery:Array<SelectedBattery>;
  selectedBattery:number=20;

  productDetailRes: Array<ProductDetailResponse> = [];
  btPendingReasonValue: BtPendingReasonValue[];
  btResolutionMethod: BtResolutionMethod[];
  btPowerPlantMake: BtPowerPlantMake[];
  btCollingMethod: BtCollingMethod[];
  btSolutionImparted: BtSolutionImparted[];
  btSiteEnvCondition: BtSiteEnvCondition[];
  btSiteType: BtSiteType[];
  btActiveAlarm:BtActiveAlarm[];
  SAResponse:btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  fsrcommisionReq:batteryCommisionFsr = new batteryCommisionFsr();
  caseList: Array<BTCaseForList> = [];
  today = new Date();
  caseResponse:BTCaseResponse;
  caseid:string;
  todayDate = new Date();
  _visitDate = new Date();
  message:string='';
  _productList: Array<BT_ProductForListDetail> = [];
  productID: number;
  _isCommisionFsr:boolean;
  Datepicker: any;
  editFsrMode:boolean;
  searchitem: string = '';
  CBMSArray:Array<any>=[];
  createDateMin:Date;
  _todayDate3:Date= new Date();
  _datetoday3:Date= new Date();
  EQSruniqueFlag:boolean = true;
  cbmsassetId:number;
   btFsrCommissionDataGetById:BtFsrCommissionDataGetById=new BtFsrCommissionDataGetById();
   servicefsrById:BtFsrServiceDataGetById = new BtFsrServiceDataGetById();

  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @Input()
  set caseResponseById(caseResponse:BTCaseResponse)
  {
    this.btCaseResponse = caseResponse;
    // this.caseid = this.caseResponse.caseId;
    // this.changecaseNumber(this.caseid);

    this.bt_caseGeneralINFO.siteManualId = this.btCaseResponse.siteManualId;
    this.bt_caseGeneralINFO.siteName = this.btCaseResponse.siteName;
    this.bt_caseGeneralINFO.createdDate = this.btCaseResponse.createdDate;

    this.bt_caseGeneralINFO.createdDate = this.datepipe.transform(this.bt_caseGeneralINFO.createdDate, 'yyyy/MM/dd');
    console.log(this.bt_caseGeneralINFO.createdDate);
    
    this.bt_caseGeneralINFO.contactPersonFirstName = this.btCaseResponse.contactPersonFirstName +" "+this.btCaseResponse.contactPersonLastName;
    this.bt_caseGeneralINFO.contactPersonContactNo = this.btCaseResponse.contactPersonContactNo;
    this.bt_caseGeneralINFO.createdByFirstName = this.btCaseResponse.contactPersonFirstName +" "+this.btCaseResponse.contactPersonLastName;
     this.bt_caseGeneralINFO.assetsSerialNumber= this.btCaseResponse.assetsSerialNumber
    this.bt_caseGeneralINFO.ownerFirstName = this.btCaseResponse.contactPersonFirstName;
    this.bt_caseGeneralINFO.cMP = this.btCaseResponse.cmp;

    this.bt_caseGeneralINFO.circle= this.btCaseResponse.circleName;
    this.bt_caseGeneralINFO.caseLoggedByContactNo = this.btCaseResponse.contactPersonContactNo;
    this.bt_caseGeneralINFO.faultObservedByCustomer =this.btCaseResponse.problemReported;
    this.dateCaseGen = this.datepipe.transform(this.bt_caseGeneralINFO.createdDate, 'yyyy/MM/dd')
    this.dateOfCaseGen = new Date(this.dateCaseGen);


 // New Logic**************************
    
 let _todayDate = new Date();

 let createDateMin=this.datepipe.transform(this.bt_caseGeneralINFO.createdDate, 'yyyy-MM-dd');
 this.createDateMin = new Date(createDateMin); 
 if( this.createDateMin.getDate() <= _todayDate.getDate() || this.createDateMin.getMonth() <= _todayDate.getMonth() || this.createDateMin.getFullYear() <= _todayDate.getFullYear())
    {

   this.datediff = Math.abs(_todayDate.getDate() - this.createDateMin.getDate());
   console.log(this.datediff);
   

 if(this.datediff>=4)
 {
   _todayDate.setDate(_todayDate.getDate() -3);
   this._todayDate3 = _todayDate;
     let datetoday3 = this.datepipe.transform(this._todayDate3,'yyyy-MM-dd');
     this._datetoday3 = new Date(datetoday3);
 }
 else{
   this._datetoday3 = this.createDateMin;
 }

 }
    // let createDateMin=this.datepipe.transform(this.bt_caseGeneralINFO.createdDate, 'yyyy-MM-dd');
    // this.createDateMin = new Date(createDateMin);    
    // let _todayDate = new Date();
    // _todayDate.setDate(_todayDate.getDate() -2);
    // let _todayDate3 = this.datepipe.transform(_todayDate,'yyyy-MM-dd');
    
    // if( this.createDateMin.getDate() < _todayDate.getDate() || this.createDateMin.getMonth() < _todayDate.getMonth() || this.createDateMin.getFullYear() < _todayDate.getFullYear())
    // {
    //   this._todayDate3 = _todayDate;
    //   let datetoday3 = this.datepipe.transform(this._todayDate3,'yyyy-MM-dd');
    //   this._datetoday3 = new Date(datetoday3);
    //   console.log(this._datetoday3);
    // }
    else{
      this._datetoday3 = this.today; 
    }
  }
  get caseResponseById():BTCaseResponse{
    return this.btCaseResponse;
  }
  //data from service appointment
  @Input()
  set appointmentNo(serviceAppointmentResponse: btServiceAppointmentResponseWithSCH) {
    this.SAResponse=serviceAppointmentResponse;
    this.btCreateFsr.appointment = serviceAppointmentResponse.appointmentId;
  }
  get appointmentNo(): btServiceAppointmentResponseWithSCH {
    return this.SAResponse;
  }


  //Use for edit
  @Input()
  set fsrDataGetById(fsrDataGetById:any)
  
  {
    this.btFsrCommissionDataGetById = fsrDataGetById;
    this.servicefsrById = fsrDataGetById;  
    if( this.servicefsrById.serviceFSRId!=undefined)
    {
      //**For Sevice */
      for(let i=0;i< this. servicefsrById.asseResponse.length-2;i++)
      { 
        this.addBattery(i);
      }

        this.btCreateFsr.serviceFSRId =  this. servicefsrById.serviceFSRId.toString();

        this.btCreateFsr.activeRectifiers= this. servicefsrById.activeRectifiers;
        this.btCreateFsr.ambientTemp= this. servicefsrById.ambientTemp;
        this.btCreateFsr.btSiteType= this. servicefsrById.siteTypeId;
        this.btCreateFsr.btCoolingMethod= this. servicefsrById.coolingMethodId;
        this.btCreateFsr.btPowerPlantMake= this. servicefsrById.powerPlantMakeId;
        this.btCreateFsr.btResolutionMethod= this. servicefsrById.resolutionMethodId;
        this.btCreateFsr.btSiteEnvironmentalConditions= this. servicefsrById.siteEnvironmentalConditionsId;
        this.btCreateFsr.earthigStatus= this. servicefsrById.earthigStatus;
        this.btCreateFsr.totalEquipmentDcLoad= this. servicefsrById.totalEquipmentDcLoad;
        this.btCreateFsr.smpsToCbmsVolGap= this. servicefsrById.smpsToCbmsVolGap;
        this.btCreateFsr.cbmsToBatVolGap= this. servicefsrById.cbmsToBatVolGap;
        this.btCreateFsr.installtionStatus =  this. servicefsrById.installationStatus;
        this.btCreateFsr.dcLoadCurrent= this. servicefsrById.dcLoadCurrent;
        this.btCreateFsr.maxModuleTemp= this. servicefsrById.maxModuleTemp;
        this.btCreateFsr.faultObservedByExicomEng= this. servicefsrById.faultObservedByExicomEng;
        this.btCreateFsr.correctiveActionInDetails= this. servicefsrById.correctiveActionInDetails;
        this.btCreateFsr.btResolutionMethod= this. servicefsrById.resolutionMethodId;
        this.btCreateFsr.btSolutionImparted= this. servicefsrById.solutionImpartedId;
        this.btCreateFsr.caseStatus= this. servicefsrById.caseStatus;
        this.btCreateFsr.pendingBucket= this. servicefsrById.pendingBucket;
        this.btCreateFsr.complainAttendByEngName= this. servicefsrById.complainAttendByEngName;
        let engNumber = new String( this. servicefsrById.engNumber);
        this.btCreateFsr.engNumber=engNumber.toString();

        this.btCreateFsr.totalEquipmentDcLoad =  this. servicefsrById.totalEquipmentDcLoad;
        this.btCreateFsr.contactPersonAtSite =  this. servicefsrById.contactPersonAtSite;
        this.btCreateFsr.contactPersonAtSiteNo =  this. servicefsrById.contactPersonAtSiteNo.toString();
        this.btCreateFsr.customerRemark= this. servicefsrById.customerRemark;
        /* Observation*/
        this.btCreateFsr.observation1= this. servicefsrById.observation1;
        this.btCreateFsr.observation2= this. servicefsrById.observation2;
        this.btCreateFsr.observation3= this. servicefsrById.observation3;
        this.btCreateFsr.observation4= this. servicefsrById.observation4;

        this.btCreateFsr.btPendingReasonCustomerBucket= this. servicefsrById.pendingReasonCustomerBucketId;
        this.btCreateFsr.pendingReasonExicomBucket= this. servicefsrById.pendingReasonExicomBucket;



        this.bt_caseGeneralINFO.dateOfSiteVisit = new Date(this. servicefsrById.dateOfsiteVisit);


            if(this.btCreateFsr.caseStatus=='Incompleted'){
              this._caseStatusVal= false;
            }else{
              this._caseStatusVal= true;

            }
            if(this.btCreateFsr.pendingBucket=="Exicom")
            {
              this.reasonText=true;
              this.pendingBucketdemo=false;

            }else if(this.btCreateFsr.pendingBucket=="Customer")
            {
              this.reasonDropdown=true;
              this.pendingBucketdemo=false;
            }


            //for Assest Data for CBMS
            if(this.servicefsrById.asseResponse.length!=0)
            {
              this.cbmsRowObj.Part_Code =  this. servicefsrById.asseResponse[0].productPartCode;
              this.cbmsRowObj.Equipment_Sr_No =  this. servicefsrById.asseResponse[0].equipmentSrNo.trim();
              this.cbmsRowObj.Equipment_Type =  this. servicefsrById.asseResponse[0].equipmentType;
              this.cbmsRowObj.Model_Number =  this. servicefsrById.asseResponse[0].productDescription;
              this.cbmsRowObj.Software_Version =  this. servicefsrById.asseResponse[0].softwareVersion;
              this.cbmsRowObj.SOC =  this. servicefsrById.asseResponse[0].soc;
              this.cbmsRowObj.SOH =  this. servicefsrById.asseResponse[0].soh;
              this.cbmsRowObj.Warranty_Status =  this. servicefsrById.asseResponse[0].warrantyStatus;
              this.cbmsRowObj.Current_Condition =  this. servicefsrById.asseResponse[0].currentConditionId;
              this.cbmsRowObj.assetsSerialNumber =  this. servicefsrById.asseResponse[0].assetId.toString();
              this.cbmsRowObj.Installation_Date =  this. servicefsrById.asseResponse[0].installationDate;

              if( this.cbmsRowObj.Installation_Date!=null ||  this.cbmsRowObj.Installation_Date!=undefined)
              { 
              this.cbmsRowObj.Installation_Date = this.datepipe.transform(this.cbmsRowObj.Installation_Date,'yyyy/MM/dd');
              }
              this.cbmsRowObj.id =  this. servicefsrById.asseResponse[0].assetId;
              
            }
      //for Assest Data for battery
      
      if(this.servicefsrById.asseResponse.length!=0)
      {

        for(let i = 1; i< this. servicefsrById.asseResponse.length;i++)
          {
              this.addBatteryrow[i-1].Part_Code =  this. servicefsrById.asseResponse[i].productPartCode;
              this.addBatteryrow[i-1].Equipment_Sr_No =  this. servicefsrById.asseResponse[i].equipmentSrNo.trim();
              this.addBatteryrow[i-1].Equipment_Type =  this. servicefsrById.asseResponse[i].equipmentType;
              this.addBatteryrow[i-1].Model_Number =  this. servicefsrById.asseResponse[i].productDescription;
              this.addBatteryrow[i-1].Software_Version =  this. servicefsrById.asseResponse[i].softwareVersion;
              this.addBatteryrow[i-1].SOC =  this. servicefsrById.asseResponse[i].soc;
              this.addBatteryrow[i-1].SOH =  this. servicefsrById.asseResponse[i].soh;
              this.addBatteryrow[i-1].Warranty_Status =  this. servicefsrById.asseResponse[i].warrantyStatus;
              this.addBatteryrow[i-1].Installation_Date =  this. servicefsrById.asseResponse[i].installationDate;

           if(this.addBatteryrow[i-1].Installation_Date!=null || this.addBatteryrow[i-1].Installation_Date!=undefined)
           {   
             let install_Date = new Date( this.addBatteryrow[i-1].Installation_Date);
             this.addBatteryrow[i-1].Installation_Date = this.datepipe.transform(install_Date,'yyyy/MM/dd');
            }
            
            this.addBatteryrow[i-1].Current_Condition =  this. servicefsrById.asseResponse[i].currentConditionId;
            this.addBatteryrow[i-1].id =  this. servicefsrById.asseResponse[i].assetId;


            console.log(this.servicefsrById.faultyAsset.length);
            
        if(this.servicefsrById.faultyAsset.length!=0)
          {

            if( this. servicefsrById.asseResponse[i].equipmentType ==   this.servicefsrById.faultyAsset[0].equipmentName)
            {
              this.addBatteryrow[i-1].flag=1;
              
              console.log(this.addBatteryrow[i-1].flag);
              
            }
            else{
              this.addBatteryrow[i-1].flag=0;
            }
            
          }
        }
      }
     for(let i=0;i<this.addBatteryrow.length;i++)
     {
       if(this.addBatteryrow[i].flag==1)
       {
        this.selectedBattery = i+1;
        this.addFaultyBatteryObj.vol =  this. servicefsrById.faultyAsset[0].voltage;
        this.addFaultyBatteryObj.SOC =  this. servicefsrById.faultyAsset[0].soc;
        this.addFaultyBatteryObj.SOH =  this. servicefsrById.faultyAsset[0].soh;
        this.addFaultyBatteryObj.activeAlarms =  this. servicefsrById.faultyAsset[0].activeAlarmsId;
        this.addFaultyBatteryObj.problemSince =  this. servicefsrById.faultyAsset[0].problemSince;
        this.addFaultyBatteryObj.problemSince = new Date( this. servicefsrById.faultyAsset[0].problemSince);
        this.addFaultyBatteryObj.minCellVol =  this. servicefsrById.faultyAsset[0].minCellVoltage;
        this.addFaultyBatteryObj.maxCellVol =  this. servicefsrById.faultyAsset[0].maxCellVoltage;
        this.addFaultyBatteryObj.volGap =  this. servicefsrById.faultyAsset[0].volGap;
        this.addFaultyBatteryObj.batteryTemp =  this. servicefsrById.faultyAsset[0].batteryTemp;
        this.addFaultyBatteryObj.lastCycleDel =  this. servicefsrById.faultyAsset[0].lastCycleDel;
        this.addFaultyBatteryObj.logStatus =  this. servicefsrById.faultyAsset[0].logStatus;
        this.addFaultyBatteryObj.betteryReqBRC =  this. servicefsrById.faultyAsset[0].batteryRequiredAtBRC;

       }
     }
     console.log(this.addBatteryrow);
     
        

        for(let i=0;i<this.addBatteryrow.length;i++)
        {
              console.log( this. servicefsrById.asseResponse[i].currentConditionId);
              
          if(this.addBatteryrow[i].Current_Condition == 1)
          {
            
            this.addFaultyBatteryRow();
          }
        }



      
    }
    // else{
      if( this.btFsrCommissionDataGetById.commFSRId!=undefined)
      {
      console.log("Commsision fsr");

         //**********************For Commisionn ***********************/

        this.btFsrCommissionDataGetById;
    
      console.log(this.btFsrCommissionDataGetById);
      

      for(let i=0;i<this.btFsrCommissionDataGetById.asseResponse.length-2;i++)
      {
        this.addBattery(i);
      }

    this.fsrcommisionReq.commFSRId =  this.btFsrCommissionDataGetById.commFSRId;


    this.btCreateFsr.activeRectifiers= this.btFsrCommissionDataGetById.activeRectifers;
    this.btCreateFsr.ambientTemp= this.btFsrCommissionDataGetById.ambientTemp;
    this.btCreateFsr.btSiteType= this.btFsrCommissionDataGetById.siteTypeId;
    this.btCreateFsr.btCoolingMethod= this.btFsrCommissionDataGetById.coolingMethodId;
    this.btCreateFsr.btPowerPlantMake= this.btFsrCommissionDataGetById.powerPlantMakeId;
    this.btCreateFsr.btResolutionMethod= this.btFsrCommissionDataGetById.resolutionMethodId;
    this.btCreateFsr.btSiteEnvironmentalConditions= this.btFsrCommissionDataGetById.siteEnvironmentalConditionsId;
    this.btCreateFsr.earthigStatus= this.btFsrCommissionDataGetById.earthingStatus;
    this.btCreateFsr.totalEquipmentDcLoad= this.btFsrCommissionDataGetById.totalEquipmentDcLoad;
    this.btCreateFsr.smpsToCbmsVolGap= this.btFsrCommissionDataGetById.smpsToCbmsVolGap;
    this.btCreateFsr.cbmsToBatVolGap= this.btFsrCommissionDataGetById.cbmsToBatVolGap;
    this.fsrcommisionReq.commissionScope =  this.btFsrCommissionDataGetById.commissionScope;
    this.btCreateFsr.commissionScope =  this.btFsrCommissionDataGetById.commissionScope;
    this.btCreateFsr.installtionStatus =  this.btFsrCommissionDataGetById.installationStatus;
   
    this.btCreateFsr.chargeVoltage =  this.btFsrCommissionDataGetById.chargeVoltage.toString();
    this.btCreateFsr.floatVoltage =  this.btFsrCommissionDataGetById.floatVoltage.toString();
    this.fsrcommisionReq.installtionStatus =  this.btFsrCommissionDataGetById.installationStatus;
    this.btCreateFsr.dcLoadCurrent= this.btFsrCommissionDataGetById.dcLoadCurrent;
    this.btCreateFsr.maxModuleTemp= this.btFsrCommissionDataGetById.maxModuleTemp;
    this.btCreateFsr.faultObservedByExicomEng= this.btFsrCommissionDataGetById.faultObservedByExicomEng;
    this.btCreateFsr.correctiveActionInDetails= this.btFsrCommissionDataGetById.correctiveActionInDetails;
    this.btCreateFsr.btResolutionMethod= this.btFsrCommissionDataGetById.btResolutionMethod;
    this.btCreateFsr.btSolutionImparted= this.btFsrCommissionDataGetById.btSolutionImparted;
    this.btCreateFsr.caseStatus= this.btFsrCommissionDataGetById.caseStatus;
    this.btCreateFsr.pendingBucket= this.btFsrCommissionDataGetById.pendingBucket;
    this.btCreateFsr.complainAttendByEngName= this.btFsrCommissionDataGetById.complainAttandByEngName;
    this.fsrcommisionReq.engNumber= this.btFsrCommissionDataGetById.engineerNumber.toString();
     this.btCreateFsr.engNumber= this.fsrcommisionReq.engNumber;
    this.btCreateFsr.caseStatus =  this.btFsrCommissionDataGetById.commStatus;
    this.btCreateFsr.totalEquipmentDcLoad =  this.btFsrCommissionDataGetById.totalEquipmentDCLoad;
    this.btCreateFsr.contactPersonAtSite =  this.btFsrCommissionDataGetById.contactPersonAtSite;
    this.btCreateFsr.contactPersonAtSiteNo =  this.btFsrCommissionDataGetById.contactPersonAtSiteContactNumber;
    this.btCreateFsr.customerRemark= this.btFsrCommissionDataGetById.exicomEngRemarks;
      /* observations */
    this.btCreateFsr.observation1= this.btFsrCommissionDataGetById.observation1;
    this.btCreateFsr.observation2= this.btFsrCommissionDataGetById.observation2;
    this.btCreateFsr.observation3= this.btFsrCommissionDataGetById.observation3;
    this.btCreateFsr.observation4= this.btFsrCommissionDataGetById.observation4;

    this.btCreateFsr.btPendingReasonCustomerBucket= this.btFsrCommissionDataGetById.pendingReasonCustomerBucketId;
    this.btCreateFsr.pendingReasonExicomBucket= this.btFsrCommissionDataGetById.pendingReasonExicomBucket;



    this.bt_caseGeneralINFO.dateOfSiteVisit = new Date( this.btFsrCommissionDataGetById.dateOfSiteVisit);
    if(this.btCreateFsr.caseStatus=='Incompleted'){
      this._caseStatusVal= false;
    }else{
      this._caseStatusVal= true;

    }
    if(this.btCreateFsr.pendingBucket=="Exicom")
    {
      this.reasonText=true;
      this.pendingBucketdemo=false;

    }else if(this.btCreateFsr.pendingBucket=="Customer")
    {
      this.reasonDropdown=true;
      this.pendingBucketdemo=false;
    }


    //for Assest Data for CBMS
    
       this.cbmsRowObj.Part_Code =  this.btFsrCommissionDataGetById.asseResponse[0].partCode;
    
      this.cbmsRowObj.Equipment_Sr_No =  this.btFsrCommissionDataGetById.asseResponse[0].equipmentSrNo.trim();
      this.cbmsRowObj.Equipment_Type =  this.btFsrCommissionDataGetById.asseResponse[0].equipmentType;
      this.cbmsRowObj.Model_Number =  this.btFsrCommissionDataGetById.asseResponse[0].model;
      this.cbmsRowObj.Software_Version =  this.btFsrCommissionDataGetById.asseResponse[0].softwareVersion;
      this.cbmsRowObj.SOC =  this.btFsrCommissionDataGetById.asseResponse[0].soc;
      this.cbmsRowObj.SOH =  this.btFsrCommissionDataGetById.asseResponse[0].soh;
      this.cbmsRowObj.Warranty_Status =  this.btFsrCommissionDataGetById.asseResponse[0].warrantyStatus;
      this.cbmsRowObj.Current_Condition =  this.btFsrCommissionDataGetById.asseResponse[0].currentConditionId;
      this.cbmsRowObj.assetsSerialNumber =  this.btFsrCommissionDataGetById.asseResponse[0].assetId.toString();
      this.cbmsRowObj.Installation_Date =  this.btFsrCommissionDataGetById.asseResponse[0].installationDate;
      this.cbmsRowObj.id =  this.btFsrCommissionDataGetById.asseResponse[0].assetId;

      
    if(this.cbmsRowObj.Installation_Date!=null || this.cbmsRowObj.Installation_Date!=undefined)
    {
      let install_Date = new Date(this.cbmsRowObj.Installation_Date);
      this.cbmsRowObj.Installation_Date = this.datepipe.transform(install_Date,'yyyy/MM/dd');
      
    } 

      //for Assest Data for battery

    console.log(this.btFsrCommissionDataGetById.asseResponse);

      for(let i=1;i<this.btFsrCommissionDataGetById.asseResponse.length;i++)
        {

          this.addBatteryrow[i-1].Part_Code =  this.btFsrCommissionDataGetById.asseResponse[i].partCode;
            this.addBatteryrow[i-1].Equipment_Sr_No =  this.btFsrCommissionDataGetById.asseResponse[i].equipmentSrNo.trim();
            this.addBatteryrow[i-1].Equipment_Type =  this.btFsrCommissionDataGetById.asseResponse[i].equipmentType;
            this.addBatteryrow[i-1].Model_Number =  this.btFsrCommissionDataGetById.asseResponse[i].model;
            this.addBatteryrow[i-1].Software_Version =  this.btFsrCommissionDataGetById.asseResponse[i].softwareVersion;
            this.addBatteryrow[i-1].SOC =  this.btFsrCommissionDataGetById.asseResponse[i].soc;
            this.addBatteryrow[i-1].SOH =  this.btFsrCommissionDataGetById.asseResponse[i].soh;
            this.addBatteryrow[i-1].Warranty_Status =  this.btFsrCommissionDataGetById.asseResponse[i].warrantyStatus;
            this.addBatteryrow[i-1].Installation_Date =  this.btFsrCommissionDataGetById.asseResponse[i].installationDate;

      if(this.addBatteryrow[i-1].Installation_Date!=null || this.addBatteryrow[i-1].Installation_Date!=undefined)
      {

        let install_Date = new Date( this.addBatteryrow[i-1].Installation_Date);
        this.addBatteryrow[i-1].Installation_Date = this.datepipe.transform(install_Date,'yyyy/MM/dd');
        
      }
      


            this.addBatteryrow[i-1].Current_Condition =  this.btFsrCommissionDataGetById.asseResponse[i].currentConditionId;
            this.addBatteryrow[i-1].id =  this.btFsrCommissionDataGetById.asseResponse[i].assetId;

        }


      }
      

  }
  get fsrDataGetById():any{
    return this.btFsrCommissionDataGetById;
  }
  @Input()
  set isEditFsr(isEditFsr:boolean){
    this.editFsrMode=isEditFsr;
    // if(isEditFsr==true)
    // {

    // }
  }

  get isEditFsr():boolean
  {
    return this.editFsrMode;
  }
  @Input()
  set isCommisionFsr(isCommisionFsr:boolean){
    this._isCommisionFsr=isCommisionFsr;

  }

  get isCommisionFsr(): boolean{
    return this._isCommisionFsr;
  }

 

  faPlus: string = 'btn btn-primary fa fa-plus';
  faMinus: string = 'btn btn-danger fa fa-minus';
  myClass:string;

  constructor(private _global: GlobalserviceService,
    private btFsrService: BtFsrService,
    private btprocuctService: BtproductService,
    private caseService: BtCaseserviceService,
    private loginService: Loginservices,
    private btproductservice: BtproductService,
    private datepipe: DatePipe,private _assetsService:BTassetserviceService,
    private spinner: NgxSpinnerService,)
    
    {
      this.getBtSiteEnvironmentalConditions();
      this.getBtCoolingMethod();
      this.getBtPowerPlantMake();
      this.getBtResolutionMethod();
      this.getBtPendingReasonCustomerBucket();
      this.getBtSolutionImparted();
      this.getBtSiteType();
      this.getActiveAlarm();
      this.dateCustomClasses = [
        { date: this.today, classes: ['bg-secondary'] }
      ];
      
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit(): void {
    this.searchitem = '';
    this.showFaultyRow = false
    // this.getCaseList();
    // this.batteryDataArray.push(this.addBatteryrow)
    // console.log(this._isCommisionFsr)
    // this.getBtSiteEnvironmentalConditions();
    // this.getBtCoolingMethod();
    // this.getBtPowerPlantMake();
    // this.getBtResolutionMethod();
    // this.getBtPendingReasonCustomerBucket();
    // this.getBtSolutionImparted();
    // this.getBtSiteType();
    // this.getActiveAlarm();



    let addBattery: AddBattery = new AddBattery();
    addBattery.Equipment_Type="Battery-"+1;
    addBattery.Sr_No = 1;
    this.addBatteryrow.push(addBattery);
    // for(let i=0; i<=this.addBatteryrow.length;i++){

        
    //     console.log("after puch "+this.addBatteryrow[i].Equipment_Type);
    //   }
    if (this.addBatteryrow.length == 0) {
      this.disabledMinusbtn = false;
    }

    if(this.SAResponse.caseId!=null)
    {
      // this.changecaseNumber(this.SAResponse.caseId);
    }

    // this.bt_caseGeneralINFO.dateOfCaseGenerated = "my date";
    // let latest_date = this.datepipe.transform(this.bt_caseGeneralINFO.dateOfCaseGenerated, '2020-06-02');


  }
  
  dateCustomClasses: DatepickerDateCustomClasses[];

  getBtSiteEnvironmentalConditions() {

    this.btFsrService.getSitesEnvironmentaList().subscribe((res) => {

      this.btSiteEnvCondition = res;
    }, (error) => {
    });

  }
  getBtCoolingMethod() {
    this.btFsrService.getBtCoolingMethod().subscribe((res) => {
      this.btCollingMethod = res;
    }, (error) => {

    })
  }
  getBtPowerPlantMake() {
    this.btFsrService.getBtPowerPlantMake().subscribe((res) => {
      this.btPowerPlantMake = res;
    }, (error) => {

    })
  }
  getBtResolutionMethod() {
    this.btFsrService.getBtResolutionMethod().subscribe((res) => {
      this.btResolutionMethod = res;

    }, (error) => {

    })
  }

  getBtPendingReasonCustomerBucket() {
    if(this._isCommisionFsr==true)
    {
      this.btFsrService.getBtPendingReasonCustomerBucketforcomm().subscribe((res) => {
        this.btPendingReasonValue = res;
  
      }, (error) => {
  
      })
    }
    else
    {
    this.btFsrService.getBtPendingReasonCustomerBucket().subscribe((res) => {
      this.btPendingReasonValue = res;

    }, (error) => {

    })
  }
  }
  getBtSolutionImparted() {
    this.btFsrService.getBtSolutionImparted().subscribe((res) => {
      this.btSolutionImparted = res;

    }, (error) => {

    })

  }
  getActiveAlarm() {
    this.btFsrService.getActiveAlarm().subscribe((res) => {
      this.btActiveAlarm = res;

    }, (error) => {

    })

  }
  getBtSiteType() {
    this.btFsrService.getBtSiteType().subscribe((res) => {
      this.btSiteType = res;

    }, (error) => {

    })
  }

  getCaseList() {
    this.caseService.getCaseLists().subscribe(data => {
      this.caseList = data;
    }, err => {

    });
  }



searchProducts(key) {
    console.log(key);

    this._assetsService.getSmallForList(key.term).subscribe(data=>{
      this.assets=data;
      this.assetList=[];
      
      // NA option nahi - battery ke liye
      data.forEach(e=>{
        let assetsList=new BTAssetsForList();
        assetsList.id=e.id;
        assetsList.serialNumber=e.serialNumber;
        assetsList.productPartCode=e.productPartCode;
        assetsList.productDescription=e.productDescription;
        assetsList.asstPartCode=e.serialNumber+'-'+e.productPartCode;
        this.assetList.push(assetsList);
      })
    });
}


searchProductsCbms(key) {
    this._assetsService.getSmallForList(key.term).subscribe(data=>{
      this.cbmsAssetList = [];
      
      // NA option sirf CBMS ke liye
      let naOption = new BTAssetsForList();
      naOption.id = 0;
      naOption.serialNumber = 'NA';
      naOption.productPartCode = 'NA';
      naOption.productDescription = 'Not Available';
      naOption.asstPartCode = 'NA';
      this.cbmsAssetList.push(naOption);

      data.forEach(e=>{
        let assetsList = new BTAssetsForList();
        assetsList.id = e.id;
        assetsList.serialNumber = e.serialNumber;
        assetsList.productPartCode = e.productPartCode;
        assetsList.productDescription = e.productDescription;
        assetsList.asstPartCode = e.serialNumber+'-'+e.productPartCode;
        this.cbmsAssetList.push(assetsList);
      })
    });
}
  // filteredData:Array<BtEquipmentSrNO>=[];


  searchProductsEquipment($event) {
      console.log($event);
      console.log($event.term);

     

  }

  //on select CBMS Row

  assettSelectedCbms($event) {
    console.log($event.id)

    this.getAssetsBySerialNmber($event.id);
  }


        getAssetsBySerialNmber(assetId)
      {
              
        console.log(assetId);
        this.cbmsassetId = assetId;

        this.caseService.getCaseAssets(assetId).subscribe((data) =>{
          console.log(data);
          this.assests = data;
          this.cbmsRowObj.Part_Code = this.assests.productPartCode;
          this.cbmsRowObj.id = this.assests.id;
          this.btAssetDetailsCBMS(this.assests.serialNumber); 
          
        },(error)=>{



        }) 

      }


      btAssetDetailsCBMS(serialNo){
        if(serialNo==null || serialNo==0 || serialNo==undefined || serialNo=='')
        {
          this.cbmsRowObj.Software_Version=null;
          this.cbmsRowObj.Warranty_Status=null;
          this.cbmsRowObj.Installation_Date=null;
          this.cbmsRowObj.Model_Number=null;
          this.cbmsRowObj.Current_Condition=3;
          this.equipmentSrNO=[];
        }
        else{
    
        this.cbmsRowObj.Software_Version=null;
        this.cbmsRowObj.Warranty_Status=null;
        this.cbmsRowObj.Installation_Date=null;
        this.cbmsRowObj.Model_Number=null;
        this.cbmsRowObj.Current_Condition=3;
         this.btFsrService.searchForPartcode(this.cbmsRowObj.Part_Code,serialNo).subscribe((res) => {
          this.btAssetMasterUpdate = res;
          // this.btAssetMasterUpdate.id;
    
          // let equipmentSrNO = new EquipmentSrNO(); 
    
          // equipmentSrNO._equipmentSrNo = serialNo;
          // this.equipmentSrNO.push(equipmentSrNO);
    
          this.cbmsRowObj.Warranty_Status = this.btAssetMasterUpdate[0].warrantyStatus;
          this.cbmsRowObj.Installation_Date = this.btAssetMasterUpdate[0].installtionDate;
    
          if(this.cbmsRowObj.Installation_Date!=null || this.cbmsRowObj.Installation_Date!=undefined)
          {
    
            let install_Date = new Date(this.cbmsRowObj.Installation_Date);
            this.cbmsRowObj.Installation_Date = this.datepipe.transform(install_Date,'yyyy/MM/dd');
           
          }
    
          this.cbmsRowObj.Model_Number = this.btAssetMasterUpdate[0].productDescription;
          this.cbmsRowObj.id = this.assests.id;
    
    
        }, (error) => {
          // let message = "Part code is not found!"
          // // console.log(error);
          // this._global.alert({ type: 'alert', message: message });
          return false;
    
          
          
        })
        
      }
    }

     
  assetSelected(assetId, i,$event) {
    console.log($event)

    this.productID = $event.id;
    this.addBatteryrow[i].id = $event.id;
    console.log(this.addBatteryrow[i].id);
    
    this.caseService.getCaseAssets($event.id).subscribe((data) =>{
      console.log(data);
      this.assests = data;
      this.addBatteryrow[i].Part_Code = this.assests.productPartCode;
      console.log(this.addBatteryrow);
      

    this.getProductById(this.assests.serialNumber,i)
  })
}

  //get Product details by id (Part Code)
  getProductById(assetSerialNo, i) {

    console.log("Product id is :  "+assetSerialNo);
    this.addBatteryrow[i].Model_Number = null;
    this.addBatteryrow[i].Warranty_Status = null;
    this.addBatteryrow[i].Installation_Date = null;

    this.btIndex = i;
    this.addBatteryrow[i].Current_Condition=3;
   
    
    this.btFsrService.searchForPartcode(this.addBatteryrow[i].Part_Code,assetSerialNo).subscribe((data) => {
      this.btAssetMasterUpdate = data;
      this.btEquipmentSrNO = data;
      console.log(this.btEquipmentSrNO);
      if(this.btEquipmentSrNO.length!=0)
      {

        this.addBatteryrow[i].Model_Number = this.btEquipmentSrNO[0].productDescription;
        this.addBatteryrow[i].Warranty_Status = this.btEquipmentSrNO[0].warrantyStatus;
        this.addBatteryrow[i].Installation_Date = this.btEquipmentSrNO[0].installtionDate;
        if( this.addBatteryrow[i].Installation_Date!=null || this.addBatteryrow[i].Installation_Date!=undefined)
        {
          this.addBatteryrow[i].Installation_Date = this.datepipe.transform(this.addBatteryrow[i].Installation_Date,'yyyy/MM/dd');
        }
      }

     
    
        data.forEach(e => {

          let eq = new  BtEquipmentSrNO();
          eq.serialNumber = e.serialNumber;
          eq.serialNumber = e.serialNumber;
     
          eq.id = e.id;
          eq.productDescription = e.productDescription;
          eq.bothvalues = e.serialNumber + ' ' + e.productDescription;

          console.log(this.btEquipmentSrNO);
        



    })
  });
// }
  }
  tcode:string;
  isUniqueAsset:boolean=false;
  btAssetDetails(serialNo,i,$event:any){
   
    console.log($event);
    console.log(this.tcode);
    


      
      this.btFsrService.searchForPartcode(this.productID,serialNo).subscribe((res) => {
      this.btAssetMasterUpdate = res;
      console.log(this.btAssetMasterUpdate);
      

     this.equipmentSrNO=[];      

      this.addBatteryrow[i].Warranty_Status = this.btAssetMasterUpdate[0].warrantyStatus;
      this.addBatteryrow[i].Installation_Date = this.btAssetMasterUpdate[0].installtionDate;

      console.log( this.addBatteryrow[i].Installation_Date);


      let install_Date = new Date( this.addBatteryrow[i].Installation_Date);
      this.addBatteryrow[i].Installation_Date = this.datepipe.transform(install_Date,'yyyy/MM/dd');
      console.log( this.addBatteryrow[i].Installation_Date);
      
      this.addBatteryrow[i].Model_Number = this.btAssetMasterUpdate[0].productDescription;
      this.addBatteryrow[i].id = this.assests.id;


    }, (error) => {
      // let message = "Part code is not found!"
      // // console.log(error);
      // this._global.alert({ type: 'alert', message: message });
      return false;


      
    })
  }
  



  //add battery new row on + btn

  equType =[];
  addBattery(j) {

    let btindex = this.addBatteryrow.length;
    if (this.addBatteryrow.length < 8) {

      this.disabledAddbtn = true
      let addBattery: AddBattery = new AddBattery();
      addBattery.Equipment_Type = "Battery-"+(btindex+1);
      addBattery.Sr_No = this.addBatteryrow.length+1;
      // console.log("Add equpment type is "+addBattery.Equipment_Type);
      this.assetRequest.equipmentType=this.addBatteryrow[j].Equipment_Type;
      this.addBatteryrow.push(addBattery);
      
      if (this.addBatteryrow.length >= 1) {

        this.disabledMinusbtn = true;
      }
    } else {
      let message = "You Can't seleted above 8 battery"
      this._global.alert({ type: 'alert', message: message });
      
    }

  }


  //-----------Remove battery row on - btn----------
  removeBattery(index) {


    // console.log("remove fun is called...." + index);
    // this.disabledMinusbtn = true;
    this.addBatteryrow.splice(index, 1);
    // this.btrow.splice(index,1);
  this.addFaultyBatteryRow();
    this.disabledAddbtn = true;

    // this.addFaultyBatteryRow();

  }

  
  //cancel btn dismis the model/pannel of fsr 
  cancelData() {
    // console.log("Cancel function is called..");
      this.assetRequest = new AssetRequest();
    this.addBatteryrow=[] ;     // console.log("on cancel btn value of form install date : " + element.Installation_Date);

      this.btCreateFsr=new BtFsrData();
      this.bt_caseGeneralINFO = new BT_CaseGeneralINFO();
      this.cbmsRowObj = new AddBattery();
      this.fsrcommisionReq = new batteryCommisionFsr();
      this.addFaultyBatteryObj = new AddBattery();

      this.fsrDataGetById =  new BtFsrCommissionDataGetById();
      
      // console.log(this.assetRequest);
      // console.log( this.addBatteryrow);
      // console.log( this.btCreateFsr);
      // console.log( this.bt_caseGeneralINFO);
      // console.log( this.fsrcommisionReq);


      

  
    this.onCancle.emit("cancled");
  }
  

btrow:Array<Btrow> =[];
  addFaultyBatteryRow() {
    // console.log("Change fun is called..");
    this.btrow=[];
  
    for (let i = 0; i < this.addBatteryrow.length; i++) {
      if(this.addBatteryrow[i].Current_Condition==1)
      {
        console.log("inside addBattery row");
        let btrow:Btrow  = new Btrow();
        btrow.equipmentType = "Battery-"+(i+1);
        btrow.id = i+1;
        this.btrow.push(btrow);
        // console.log(i);
        
      }


    }
  }
 
      
  //Validation for numric tye value
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

  //Validation for numeric type value

  keyPressNumbersWithDecimal(event) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode != 46 && charCode > 31
      && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
      return false;
    }
    return true;
  }

  isNumberKeyOnlyOneDecimal(txt, evt) {
    
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 46) {
      //Check if the text already contains the . character
      if (txt.target.value.indexOf('.') === -1) {
        return true;
      } else {
        return false;
      }
    } else {
      if (charCode > 31 &&
        (charCode < 48 || charCode > 57))
        return false;
    }
    return true;
  }


  mobileValidation(event: any) {
    const pattern = /[0-9\+\-\ ]/;

    let inputChar = String.fromCharCode(event.charCode);
    if (event.keyCode != 8 && !pattern.test(inputChar)) {
      event.preventDefault();
    }
  }
  pendingBucketIf() {
    if (this.btCreateFsr.pendingBucket == "Exicom") {
      this.pendingBucketdemo = false;
      this.reasonText = true;
      this.reasonDropdown = false;
      this.btCreateFsr.btPendingReasonCustomerBucket=0;
    } else
      if (this.btCreateFsr.pendingBucket == "Customer") {
        this.pendingBucketdemo = false;
        this.reasonDropdown = true;
        this.reasonText = false;
        this.btCreateFsr.pendingReasonExicomBucket='';
      }

  }
  onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(key.target.value.length>9){
      this._global.alert({ type: 'alert', message: 'More Than 10 Digits Can Not Be Accept' });     
      return false;
    }
    return true;

  }
  caseStatusChange(){
    if(this.btCreateFsr.caseStatus=="Completed")
    {
      this.reasonText = false;
      this.reasonDropdown = false;
      this.pendingBucketdemo = true;
      this._caseStatusVal = true;
      this.btCreateFsr.pendingBucket='';
      this.btCreateFsr.pendingReasonExicomBucket='';
      this.btCreateFsr.btPendingReasonCustomerBucket=0;
      console.log(this.btCreateFsr.caseStatus);
      
      this.btCreateFsr.pendingReasonExicomBucket='';
    }
    else if(this.btCreateFsr.caseStatus=="Incompleted")
    {
      this.pendingBucketdemo = true;
      this._caseStatusVal = false;
    }
    
  }


selectFalultyBettery(){

// console.log("Selected value is " + this.selectedBattery);
    // this.addBatteryrow[this.selectedBattery].flag = 1;

for(let i=0;i<this.addBatteryrow.length;i++)
{
  if(this.selectedBattery == this.addBatteryrow[i].Sr_No){
  //  console.log("btrow id is : "+this.btrow[i].id);
  //  console.log(this.btrow);
  //  console.log(this.addBatteryrow);
   
    
    this.addBatteryrow[i].flag = 1;
    // console.log(this.addBatteryrow[i].flag);


  
}
  else {

    // for(let i=0 ; i<this.btrow.length;i++)
    // {

    //   if(this.btrow[i].equipmentType=="CBMS")
    //   {
    //     this.btrow.splice(i,1);
    //     console.log("Inside if CBMS true : "+i);
        
    //   }

    // }
    this.addBatteryrow[i].flag = 0;
  }
}


//  for(let i=0;i<this.btCreateFsr.asseRequests.length;i++){
//   this.btCreateFsr.asseRequests[i].flag=0;
//  }
    
//      if(this.selectedBattery == "Battery 1" ){
//       this.btCreateFsr.asseRequests[1].flag=1;
//       this.btCreateFsr.asseRequests[1].equipmentName=this.selectedBattery;
//       console.log( "Equipment name is : "+this.btCreateFsr.asseRequests[1].equipmentName);
      
//     }

//   else if(this.selectedBattery == "Battery 2"){
//     this.btCreateFsr.asseRequests[2].flag=1;
//     this.btCreateFsr.asseRequests[2].equipmentName=this.selectedBattery;
      // this.cbmsRowObj.Equipment_Type="CBMS"

      
      if(this.isCommisionFsr==false)
      {
     
      

      }
      }
  //to save all data in into db => click on save btn
   batteryloop:boolean=false;
   fsrserviceloop:boolean=false;
   assetflag:boolean=false;

  onCbmsNASelect(event: any) {   //00
  if (event && event.serialNumber === 'NA') {
    this.cbmsNA = true;
    this.cbmsRowObj.Software_Version = null;
    this.cbmsRowObj.Current_Condition = 3;
    this.cbmsRowObj.Model_Number = null;
    this.cbmsRowObj.Warranty_Status = null;
    this.cbmsRowObj.Installation_Date = null;
    this.cbmsRowObj.Part_Code = null;
    this.cbmsRowObj.id = null;
    this.cbmsRowObj.SOC = null; 
    this.cbmsRowObj.SOH = null; 
  } else {
    this.cbmsNA = false;
    if (event) {
      this.assettSelectedCbms(event);
    }
  }
}
  createCircle() {
    this.batteryloop=false;
    this.fsrserviceloop=false;
    this.assetflag=false;

this.btCreateFsr.asseRequests=[]
this.assetCbms.srNo = 0;
this.assetCbms.equipmentName = this.cbmsRowObj.Equipment_Type="CBMS";
this.assetCbms.equipmentType = this.cbmsRowObj.Equipment_Type="CBMS";
this.assetCbms.btAsset = this.assests.id;
this.assetCbms.flag =0;
this.assetCbms.softwareVersion = this.cbmsRowObj.Software_Version;
// this.assetCbms.installationDate = this.cbmsRowObj.Installation_Date;
this.assetCbms.currentCondition = this.cbmsRowObj.Current_Condition;
this.assetCbms.voltage = this.cbmsRowObj.vol;
// this.assetCbms.warrantyStatus = this.cbmsRowObj.Warranty_Status;
this.assetCbms.serialNumber = this.cbmsRowObj.Part_Code;
if(this.cbmsRowObj.Equipment_Sr_No!=null && this.cbmsRowObj.Equipment_Sr_No!="")
{
this.assetCbms.equipmentSrNo = this.cbmsRowObj.Equipment_Sr_No.trim();
  }
  else
  {
    this.assetCbms.equipmentSrNo = this.cbmsRowObj.Equipment_Sr_No;
  }
console.log(this.assetCbms.equipmentSrNo);
this.assetCbms.soc = this.cbmsRowObj.SOC;
this.assetCbms.soh = this.cbmsRowObj.SOH;
this.assetCbms.activeAlarms = this.cbmsRowObj.activeAlarms;
this.assetCbms.problemSince = this.cbmsRowObj.problemSince;
this.assetCbms.minCellVoltage = this.cbmsRowObj.minCellVol;
this.assetCbms.maxCellVoltage = this.cbmsRowObj.maxCellVol;
this.assetCbms.volGap = this.cbmsRowObj.vol;
this.assetCbms.batteryTemp = this.cbmsRowObj.batteryTemp;
this.assetCbms.lastCycleDel = this.cbmsRowObj.lastCycleDel;
this.assetCbms.logStatus = this.cbmsRowObj.logStatus;
this.assetCbms.batteryRequiredAtBRC = this.cbmsRowObj.betteryReqBRC;
this.assetCbms.btAsset = this.cbmsRowObj.id;


this.btCreateFsr.asseRequests.push(this.assetCbms);
this.btCreateFsr.dateOfSiteVisit = this.datepipe.transform(this.bt_caseGeneralINFO.dateOfSiteVisit, 'yyyy-MM-dd');

for(let i=0;i<this.addBatteryrow.length;i++)
{
  let assetRequest:AssetRequest = new AssetRequest();

  assetRequest.srNo=this.addBatteryrow[i].Sr_No;
  assetRequest.equipmentType=this.addBatteryrow[i].Equipment_Type.trim();
  assetRequest.equipmentName=this.addBatteryrow[i].Equipment_Type.trim();
  assetRequest.btAsset =  this.addBatteryrow[i].id;
  assetRequest.flag=this.addBatteryrow[i].flag;
  assetRequest.softwareVersion =  this.addBatteryrow[i].Software_Version;
  // assetRequest.warrantyStatus =  this.addBatteryrow[i].Warranty_Status;
  // assetRequest.installationDate =  this.addBatteryrow[i].Installation_Date;
  assetRequest.currentCondition =  this.addBatteryrow[i].Current_Condition;
  assetRequest.serialNumber =  this.addBatteryrow[i].Part_Code;
  if(this.addBatteryrow[i].Equipment_Sr_No!=null && this.addBatteryrow[i].Equipment_Sr_No!="")
  {
  assetRequest.equipmentSrNo =  this.addBatteryrow[i].Equipment_Sr_No.trim();
  }
  else{
    assetRequest.equipmentSrNo =  this.addBatteryrow[i].Equipment_Sr_No
  }


  // this.btCreateFsr.asseRequests.push(this.assetRequest);
  
if(this.addBatteryrow[i].flag==1){

  assetRequest.voltage =  this.addFaultyBatteryObj.vol;
  assetRequest.soc =  this.addFaultyBatteryObj.SOC;
  assetRequest.soh =  this.addFaultyBatteryObj.SOH;
  assetRequest.activeAlarms =  this.addFaultyBatteryObj.activeAlarms;
  assetRequest.problemSince =  this.addFaultyBatteryObj.problemSince;
  assetRequest.minCellVoltage =  this.addFaultyBatteryObj.minCellVol;
  assetRequest.maxCellVoltage =  this.addFaultyBatteryObj.maxCellVol;
  assetRequest.volGap =  this.addFaultyBatteryObj.volGap;
  assetRequest.batteryTemp =  this.addFaultyBatteryObj.batteryTemp;
  assetRequest.lastCycleDel =  this.addFaultyBatteryObj.lastCycleDel;
  assetRequest.logStatus =  this.addFaultyBatteryObj.logStatus;
  assetRequest.batteryRequiredAtBRC =  this.addFaultyBatteryObj.betteryReqBRC;
}


  
  // this.selectFalultyBettery();  
  this.btCreateFsr.asseRequests.push(assetRequest);

}

 // Ayush For Multipal Equipment Serial No 
//  [1, 1, 2, 2, 3].filter((element, index, array) => array.indexOf(element) !== index) // [1, 2]
this.serialNoArray=[];
 for(let i=0;i<this.addBatteryrow.length;i++)
 {
   this.serialNoArray.push({indexOfSerialNo:this.addBatteryrow[i].Sr_No,eqSerialNo:this.addBatteryrow[i].Equipment_Sr_No});

   console.log("Push SR NO");
   
   console.log(this.serialNoArray);
   

 } 
 
//Validation for faulty asseet inside for loop

for (let i = 0; i < this.serialNoArray.length; i++) 
{ 
    for (let j = i + 1 ; j < this.serialNoArray.length; j++)
    { 
      if (this.serialNoArray[i].eqSerialNo==(this.serialNoArray[j].eqSerialNo)) 
        { 

          this.EQSruniqueFlag = true;
          this.dunlicateElemnt = this.serialNoArray[i].eqSerialNo;

          // this.message = 'Please Select Unique Equipment Sr.No In Each Equipment';
          // this._global.alert({ type: 'alert', message: this.message});    
        }else{
          this.EQSruniqueFlag=false;
        }
  }
}
 

  for (let i = 0; i < this.addBatteryrow.length; i++) {

     if(this.addBatteryrow[i].Equipment_Sr_No==null || this.addBatteryrow[i].Equipment_Sr_No=='')
    {
      console.log(this.addBatteryrow[i].Equipment_Sr_No);
      
      this.fsrserviceloop=false;
      this.message = 'Please Fill Equipment Serial Number In '+this.addBatteryrow[i].Equipment_Type+' !';
      this._global.alert({ type: 'alert', message: this.message});
    }
    // else if ((this.addBatteryrow[i].Equipment_Sr_No.length != 20)) {
    //   this.fsrserviceloop=false;
    //   this._global.alert({ type: 'alert', message: 'Please Fill Battery Equipment  Serial No  Must Be 20 length In '+this.addBatteryrow[i].Equipment_Type+' !' });
    // }
    else if (this.addBatteryrow[i].Software_Version == 0 || this.addBatteryrow[i].Software_Version == null || this.addBatteryrow[i].Software_Version == undefined) {
      this.fsrserviceloop=false;
      this._global.alert({ type: 'alert', message: 'Please Fill The Battery Software Version In '+this.addBatteryrow[i].Equipment_Type+' !' });
    }
    else if (this.addBatteryrow[i].Current_Condition == 3 || this.addBatteryrow[i].Current_Condition == null || this.addBatteryrow[i].Current_Condition == undefined) {
      this.fsrserviceloop=false;
      this._global.alert({ type: 'alert', message: 'Please Fill The Battery Current Condition In '+this.addBatteryrow[i].Equipment_Type+' !' });
    }
    // else if(this.EQSruniqueFlag == true)
    //   {
    //     this.message = 'Please Select Unique Equipment Sr.No In Each Equipment';
    //     this._global.alert({ type: 'alert', message: this.message});  
    //   }   
   // Validation to check dublicate asset 
    //   else if(this.EQSruniqueFlag == true)
    //   {
    //   for (let i = 0; i < this.addBatteryrow.length; i++) {
    //     if(this.addBatteryrow[i].Equipment_Sr_No == this.dunlicateElemnt)
    //     {
    //       this._global.alert({ type: 'alert', message: 'Dublicate Equipemnt Serial No Is Found At Battery : '+i}); 
    //     }
      
    //   }
    // }
     
    else
    {
      this.fsrserviceloop=true;
    }

  }
 

  if (this.btCreateFsr.appointment == 0 || (this.btCreateFsr.appointment  == null || this.btCreateFsr.appointment == undefined)) {
    this._global.alert({ type: 'alert', message: 'Please Fill Appointment no' });
  }
  else if ( FieldValidation.isEmpty(this.btCreateFsr.contactPersonAtSite)) {
    this._global.alert({ type: 'alert', message: 'Please Fill Contact Person Name At Site' });
  }

  else if (this.btCreateFsr.contactPersonAtSiteNo.length<10 || this.btCreateFsr.contactPersonAtSiteNo=='' || this.btCreateFsr.contactPersonAtSiteNo==null) {
    this.message = 'Please Fill Valid Contact Number 10 Digit!';
    this._global.alert({ type: 'alert', message: this.message });
  
  }
  else if ((this.btCreateFsr.dateOfSiteVisit) == null || (this.btCreateFsr.dateOfSiteVisit) == "" || this.btCreateFsr.dateOfSiteVisit == undefined) {
    this.message = 'Please Fill Date Of Site Visit !';
    this._global.alert({ type: 'alert', message: this.message });
  }

  else if ( this.btCreateFsr.btSiteType == 0 || this.btCreateFsr.btSiteType == null || this.btCreateFsr.btSiteType == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Site Type' });
  }
  else if ( this.btCreateFsr.btSiteEnvironmentalConditions == 0 || this.btCreateFsr.btSiteEnvironmentalConditions == null || this.btCreateFsr.btSiteEnvironmentalConditions == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Site Environmental Condition' });
  }
  else if (this.btCreateFsr.earthigStatus == null || this.btCreateFsr.earthigStatus == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Earthing Status' });
  }
  else if ( this.btCreateFsr.btCoolingMethod == 0 || this.btCreateFsr.btCoolingMethod == null || this.btCreateFsr.btCoolingMethod == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Colling Method' });
  }
  else if (this.btCreateFsr.ambientTemp == null || this.btCreateFsr.ambientTemp == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Ambident Temperature' });
  }
  else if ( this.btCreateFsr.btPowerPlantMake == 0 || this.btCreateFsr.btPowerPlantMake == null || this.btCreateFsr.btPowerPlantMake == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Power Plant Make' });
  }
  else if (this.btCreateFsr.totalEquipmentDcLoad == null || this.btCreateFsr.totalEquipmentDcLoad == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Total Equipment DC Load' });
  }
  else if (this.btCreateFsr.activeRectifiers == null || this.btCreateFsr.activeRectifiers == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Active Rectifieres' });
  }
  else if (this.btCreateFsr.smpsToCbmsVolGap == null || this.btCreateFsr.smpsToCbmsVolGap == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill SMPS To CBMS Voltage Gap ' });
  }
  else if (this.btCreateFsr.cbmsToBatVolGap == null || this.btCreateFsr.cbmsToBatVolGap == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill CBMS To SMPS Voltage Gap ' });
  }
  else if (this.btCreateFsr.dcLoadCurrent == null || this.btCreateFsr.dcLoadCurrent == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill DC Load Current' });
  }
  else if (this.btCreateFsr.maxModuleTemp == null || this.btCreateFsr.maxModuleTemp == undefined ) {
    this._global.alert({ type: 'alert', message: 'Please Fill Max Module Temprature' });
  }
else if(!this.cbmsNA && (this.assetCbms.equipmentSrNo==null || this.assetCbms.equipmentSrNo==''))
{
  this.message = 'Please Fill Equipment Serial Number In '+this.assetCbms.equipmentType;
  this._global.alert({ type: 'alert', message: this.message});
}

else if (!this.cbmsNA && (this.assetCbms.softwareVersion == 0 || this.assetCbms.softwareVersion == null || this.assetCbms.softwareVersion == undefined)) {
  this._global.alert({ type: 'alert', message: 'Please Fill The CBMS Software Version'});
}

else if (!this.cbmsNA && (this.assetCbms.currentCondition == null || this.assetCbms.currentCondition == 3 || this.assetCbms.currentCondition == undefined)) {
  this._global.alert({ type: 'alert', message: 'Please Fill The CBMS Current Condition'});
}

// else if(this.btCreateFsr.caseStatus=="Incompleted")
// {

//    if ( FieldValidation.isEmpty(this.btCreateFsr.pendingBucket)) {
//     this._global.alert({ type: 'alert', message: 'Please Fill Pending Bucket' });
//   }
//   else if ( FieldValidation.isEmpty(this.btCreateFsr.pendingReasonExicomBucket )  && this.btCreateFsr.pendingBucket == "Exicom") {
    
//     this._global.alert({ type: 'alert', message: 'Please Fill Pending Reason Exicom Bucket' });
//   }
//   else if ( (this.btCreateFsr.btPendingReasonCustomerBucket == 0 || this.btCreateFsr.btPendingReasonCustomerBucket == null || this.btCreateFsr.btPendingReasonCustomerBucket == undefined ) && this.btCreateFsr.pendingBucket == "Customer") {
//     this._global.alert({ type: 'alert', message: 'Please Fill Pending Reason Customer Bucket' });
//   }
// }
  else if ( FieldValidation.isEmpty(this.btCreateFsr.complainAttendByEngName)) {
    this._global.alert({ type: 'alert', message: 'Please Fill Engineer Name ' });
  }
  else if ( FieldValidation.isEmpty(this.btCreateFsr.engNumber)) {
    console.log(this.btCreateFsr.engNumber);
    
    this._global.alert({ type: 'alert', message: 'Please Fill Engineer Number' });

  }
  else if(this.btCreateFsr.engNumber.length<10){
    this._global.alert({ type: 'alert', message: 'Please Fill Enginner Number In 10 Digit' });
  }
  // else if ( FieldValidation.isEmpty(this.btCreateFsr.customerRemark) && this.btCreateFsr.pendingBucket == "Exicom") {
  //   this._global.alert({ type: 'alert', message: 'Please Fill Engineer Remark' });
  // }
  // else if ( FieldValidation.isEmpty(this.btCreateFsr.customerRemark)) {
  //   this._global.alert({ type: 'alert', message: 'Please Fill  Remark' });
  // }
  else if(this.fsrserviceloop==true)
  {

  //////////////////////////////////////////////Service FSR////////////////////////////////////

  if(this.isCommisionFsr==false)
  {
   if(this.btrow.length>0)
   {

   if (this.selectedBattery == null || this.selectedBattery == undefined || this.selectedBattery == 20) {
    this._global.alert({ type: 'alert', message: 'Please Select Any Faulty Equipment' });
  }
  else if (this.selectedBattery != null || this.selectedBattery != undefined) {
    for (let i = 0; i < this.btCreateFsr.asseRequests.length; i++) {
      if (this.btCreateFsr.asseRequests[i].flag == 1) {

        if (this.btCreateFsr.asseRequests[i].voltage == 0 || this.btCreateFsr.asseRequests[i].voltage == null || this.btCreateFsr.asseRequests[i].voltage == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The Voltage ' });
        }

        else if (this.btCreateFsr.asseRequests[i].activeAlarms == 0 || this.btCreateFsr.asseRequests[i].activeAlarms == null || this.btCreateFsr.asseRequests[i].activeAlarms == undefined) {
          this._global.alert({ type: 'alert', message: 'Please  Select The Active Alarm Value' });
        }
        else if (this.btCreateFsr.asseRequests[i].problemSince == null || this.btCreateFsr.asseRequests[i].activeAlarms == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill  The Problem Since Date' });
        }
        else if (this.btCreateFsr.asseRequests[i].minCellVoltage == 0 || this.btCreateFsr.asseRequests[i].minCellVoltage == null || this.btCreateFsr.asseRequests[i].minCellVoltage == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The Min Cell Voltage' });
        }
        else if (this.btCreateFsr.asseRequests[i].maxCellVoltage == 0 || this.btCreateFsr.asseRequests[i].maxCellVoltage == null || this.btCreateFsr.asseRequests[i].maxCellVoltage == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The Max Cell Voltage' });
        }
        else if (this.btCreateFsr.asseRequests[i].volGap == 0 || this.btCreateFsr.asseRequests[i].volGap == null || this.btCreateFsr.asseRequests[i].volGap == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The Voltage Gap' });
        }
        else if (this.btCreateFsr.asseRequests[i].batteryTemp == 0 || this.btCreateFsr.asseRequests[i].batteryTemp == null || this.btCreateFsr.asseRequests[i].batteryTemp == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The Battery Temprature' });
        }
        else if (this.btCreateFsr.asseRequests[i].lastCycleDel == 0 || this.btCreateFsr.asseRequests[i].lastCycleDel == null || this.btCreateFsr.asseRequests[i].lastCycleDel == undefined) {
          this._global.alert({ type: 'alert', message: 'Please Fill The LastCycle' });
        }
        else if (FieldValidation.isEmpty(this.btCreateFsr.asseRequests[i].logStatus)) {
          this._global.alert({ type: 'alert', message: 'Please Fill Log Status' });
        }
        else if (FieldValidation.isEmpty(this.btCreateFsr.asseRequests[i].batteryRequiredAtBRC)) {
          this._global.alert({ type: 'alert', message: 'Please Fill Bettery Require At BRC' });
        }
        else{
          this.assetflag=true;
        }



      }

    }
  }

  }
  else
  {
  this.assetflag=true;
  }



   //////////////////////////////Validation for all objects////////////////////////////////////////
     if(this.assetflag ==true)
    {
    if ( FieldValidation.isEmpty(this.btCreateFsr.caseStatus)) {
    this._global.alert({ type: 'alert', message: 'Please Fill Faulty Case Status' });
    }

   else if ( FieldValidation.isEmpty(this.btCreateFsr.faultObservedByExicomEng)) {
    this._global.alert({ type: 'alert', message: 'Please Fill Faulty Observation by Exicom Engineer' });
  }
else if ( FieldValidation.isEmpty(this.btCreateFsr.correctiveActionInDetails)) {
  this._global.alert({ type: 'alert', message: 'Please Fill Corrective Action Detail' });
}
// else if ( this.btCreateFsr.btPendingReasonCustomerBucket == 0 || this.btCreateFsr.btPendingReasonCustomerBucket == null || this.btCreateFsr.btPendingReasonCustomerBucket == undefined ) {
//   this._global.alert({ type: 'alert', message: 'Please Fill Pending Reason ' });
// }

else if ( this.btCreateFsr.btResolutionMethod == 0 || this.btCreateFsr.btResolutionMethod == null || this.btCreateFsr.btResolutionMethod == undefined ) {
  this._global.alert({ type: 'alert', message: 'Please Fill Resolution method' });
}

else if ( this.btCreateFsr.btSolutionImparted == 0 || this.btCreateFsr.btSolutionImparted == null || this.btCreateFsr.btSolutionImparted == undefined ) {
  this._global.alert({ type: 'alert', message: 'Please Fill Solution Imaprted' });
}



else{
  console.log(this.editFsrMode);
  
  if(this.editFsrMode==false)
  {
  console.log(this.btCreateFsr);
            
   
    this.spinner.show();
    console.log(this.btCreateFsr);
    

    this.btFsrService.saveFsr(this.btCreateFsr).subscribe((res) => {
      this.spinner.hide();
      // console.log("save res is " + res);
      this.btCreateFsr = new BtFsrData();
     
      this._global.alert({ type: '', message: "Service FSR Created Successfully"});

      this.onCancle.emit('cancled');
      this.btCreateFsr.asseRequests = [];
    }, (error) => {
     
      console.log(error.status);
      if(error.status==400)
      {
        this.spinner.hide();
        if((error.error==null || error.error==undefined))
        {

          this.spinner.hide();
          this._global.alert({ type: 'alert', message: 'Invalid Value Entered' });
        
        }else{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message: error.error.message });
        }
      }
      else{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
        
    }
    this.btCreateFsr.asseRequests = [];
    });

    }
  
  else
  {
    // console.log(this.editFsrMode);
    
    // console.log(this.btCreateFsr);
    
    this.spinner.show();
    this.btFsrService.UpdateServiceFsr(this.btCreateFsr,this.btCreateFsr.serviceFSRId).subscribe((res)=>{
      this.spinner.hide();
      this.btCreateFsr = new BtFsrData();
     
      this._global.alert({ type: '', message: "Service FSR Update Successfully"});
      this.onCancle.emit('cancled');
    },(error)=>{

      console.log(error.status);
      if(error.status==400)
      {
        this.spinner.hide();
        if((error.error==null || error.error==undefined))
        {

          this.spinner.hide();
          this._global.alert({ type: 'alert', message: 'Invalid Value Entered' });
        
        }else{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message: error.error.message });
        }
      }
      else{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
        
    }
      
    });
  
  }
}
  }

      //Calling API to create FSR 

  }
 else
    {
      this.fsrcommisionReq.btCommissioningAssetMasterUpdateRequests=[];
      
      this.fsrcommisionReq.appointment=this.btCreateFsr.appointment.toString();
      this.fsrcommisionReq.Voltage=5;
      this.fsrcommisionReq.activeRectifers=this.btCreateFsr.activeRectifiers
      this.fsrcommisionReq.ambientTemp=this.btCreateFsr.ambientTemp
      this.fsrcommisionReq.btCoolingMethod=this.btCreateFsr.btCoolingMethod
      this.fsrcommisionReq.btSiteEnvironmentalConditions=this.btCreateFsr.btSiteEnvironmentalConditions
      this.fsrcommisionReq.cbmsToBatVolGap=this.btCreateFsr.cbmsToBatVolGap;
      this.fsrcommisionReq.chargeVoltage=this.btCreateFsr.chargeVoltage;
      this.fsrcommisionReq.commStatus=this.btCreateFsr.caseStatus;
      // this.fsrcommisionReq.commissionScope=this.btCreateFsr.commissionScope;
      this.fsrcommisionReq.commissionScope=this.fsrcommisionReq.commissionScope;

      this.fsrcommisionReq.complainAttandByEngName=this.btCreateFsr.complainAttendByEngName;
      this.fsrcommisionReq.contactPersonAtSite=this.btCreateFsr.contactPersonAtSite;
      this.fsrcommisionReq.contactPersonAtSiteContactNumber=this.btCreateFsr.contactPersonAtSiteNo;
      this.fsrcommisionReq.customerRemarks=this.btCreateFsr.customerRemark;
      this.fsrcommisionReq.dateOfSiteVisit= this.datepipe.transform(this.btCreateFsr.dateOfSiteVisit, 'yyyy-MM-dd');
      this.fsrcommisionReq.dcLoadCurrent=this.btCreateFsr.dcLoadCurrent
      this.fsrcommisionReq.earthigStatus=this.btCreateFsr.earthigStatus
      this.fsrcommisionReq.engNumber=this.btCreateFsr.engNumber;
      this.fsrcommisionReq.exicomEngRemarks=this.btCreateFsr.customerRemark;
      /*observations */
      this.fsrcommisionReq.observation1=this.btCreateFsr.observation1;
      this.fsrcommisionReq.observation2=this.btCreateFsr.observation2;
      this.fsrcommisionReq.observation3=this.btCreateFsr.observation3;
      this.fsrcommisionReq.observation4=this.btCreateFsr.observation4;
      
      this.fsrcommisionReq.floatVoltage=this.btCreateFsr.floatVoltage;
      this.fsrcommisionReq.installtionStatus=this.fsrcommisionReq.installtionStatus;
      this.fsrcommisionReq.maxModuleTemp=this.btCreateFsr.maxModuleTemp
      this.fsrcommisionReq.pendingBucket=this.btCreateFsr.pendingBucket;
      this.fsrcommisionReq.pendingReasonCustomerBucket=this.btCreateFsr.btPendingReasonCustomerBucket;
      this.fsrcommisionReq.pendingReasonExicomBucket=this.btCreateFsr.pendingReasonExicomBucket;
      this.fsrcommisionReq.powerPlantMake=this.btCreateFsr.btPowerPlantMake;
      this.fsrcommisionReq.siteType=this.btCreateFsr.btSiteType
      this.fsrcommisionReq.smpsToCbmsVolGap=this.btCreateFsr.smpsToCbmsVolGap

      this.fsrcommisionReq.totalEquipmentDcLoad=this.btCreateFsr.totalEquipmentDcLoad

      this.fsrcommisionReq.btCommissioningAssetMasterUpdateRequests.push({srNo:1,btAsset:this.cbmsRowObj.id, currentCondition:this.cbmsRowObj.Current_Condition, equipmentSrNo:this.cbmsRowObj.Equipment_Sr_No, equipmentType:'CBMS', soc:this.cbmsRowObj.SOC, softwareVersion:this.cbmsRowObj.Software_Version, soh:this.cbmsRowObj.SOH})
      for(let i=0;i<this.addBatteryrow.length;i++)
      {
        this.fsrcommisionReq.btCommissioningAssetMasterUpdateRequests.push({srNo:i+1+1,btAsset:this.addBatteryrow[i].id,currentCondition:this.addBatteryrow[i].Current_Condition,equipmentSrNo:this.addBatteryrow[i].Equipment_Sr_No,equipmentType:this.addBatteryrow[i].Equipment_Type,soc:this.addBatteryrow[i].SOC,softwareVersion:this.addBatteryrow[i].Software_Version,soh:this.addBatteryrow[i].SOH})
      }

      for(let j=0;j<this.addBatteryrow.length;j++)
      {
        if(this.addBatteryrow[j].SOC==null || this.addBatteryrow[j].SOC==undefined || this.addBatteryrow[j].SOC==0)
        {
          this.batteryloop=false;
             this.message = 'Please Fill SOC In '+this.addBatteryrow[j].Equipment_Type+' !';
             this._global.alert({ type: 'alert', message: this.message});
        }
        else if(this.addBatteryrow[j].SOH==null || this.addBatteryrow[j].SOH==undefined || this.addBatteryrow[j].SOH==0)
        {
          this.batteryloop=false;
             this.message = 'Please Fill SOH In '+this.addBatteryrow[j].Equipment_Type+' !';
             this._global.alert({ type: 'alert', message: this.message});
        }
        else
        {
          this.batteryloop=true;
        }
      }
      
    
      if(FieldValidation.isEmpty(this.fsrcommisionReq.commissionScope))
      {
        this.message = 'Please Fill Commission Scope !';
        this._global.alert({ type: 'alert', message: this.message});
      }
      else if(FieldValidation.isEmpty(this.fsrcommisionReq.installtionStatus))
      {
        this.message = 'Please Fill Installation Status !';
        this._global.alert({ type: 'alert', message: this.message});
      }
      else if(FieldValidation.isEmpty(this.fsrcommisionReq.chargeVoltage))
      {
        this.message = 'Please Fill Charge Voltage !';
        this._global.alert({ type: 'alert', message: this.message});
      }
      else if(FieldValidation.isEmpty(this.fsrcommisionReq.floatVoltage))
      {
        this.message = 'Please Fill Float Voltage !';
        this._global.alert({ type: 'alert', message: this.message});
      }
     else if(!this.cbmsNA && (this.cbmsRowObj.SOC==null || this.cbmsRowObj.SOC==undefined))
{
  this.message = 'Please Fill CBMS SOC !';
  this._global.alert({ type: 'alert', message: this.message});
}
else if(!this.cbmsNA && (this.cbmsRowObj.SOH==null || this.cbmsRowObj.SOH==undefined))
{
  this.message = 'Please Fill CBMS SOH !';
  this._global.alert({ type: 'alert', message: this.message});
}
      else if ( FieldValidation.isEmpty(this.btCreateFsr.caseStatus)) {
        this._global.alert({ type: 'alert', message: 'Please Fill COMM Status' });
      }
      else if(this.batteryloop==true && this.fsrserviceloop==true)
      {
      // else if(FieldValidation.isEmpty(this.fsrcommisionReq.commStatus))
      // {
      //   this.message = 'Please Fill Comm Status !';
      //   this._global.alert({ type: 'alert', message: this.message});
      // }
      // else if(FieldValidation.isEmpty(this.fsrcommisionReq.pendingBucket))
      // {
      //   this.message = 'Please Fill Pending Bucket!';
      //   this._global.alert({ type: 'alert', message: this.message});
      // }
    
      //   else if(FieldValidation.isEmpty(this.btCreateFsr.complainAttendByEngName))
      //   {
      //     this.message = 'Please Fill Engineer/Customer Name !';
      //     this._global.alert({ type: 'alert', message: this.message});
      //   }
      //   else if(FieldValidation.isEmpty(this.btCreateFsr.engNumber))
      //   {
      //     this.message = 'Please Fill Contact Number !';
      //     this._global.alert({ type: 'alert', message: this.message});
      //   }
      //   else if(this.fsrcommisionReq.engNumber !==undefined && this.fsrcommisionReq.engNumber !== '' &&
      //   FieldValidation.isValidMobileNumber(this.fsrcommisionReq.engNumber) === false)
      //   {
      //     this.message = 'Please Fill Valid Contact Number !';
      //     this._global.alert({ type: 'alert', message: this.message});
      //   }      
      // else if(FieldValidation.isEmpty(this.fsrcommisionReq.exicomEngRemarks))
      // {
      //   this.message = 'Please Fill Remarks!';
      //   this._global.alert({ type: 'alert', message: this.message});
      // }

    //  else
    //  {      

      // console.log(this.fsrcommisionReq);
      if(this.editFsrMode==false)
      {
      console.log(this.fsrcommisionReq);


      console.log("this.fsrcommisionReq")
    this.spinner.show();
      this.btFsrService.savecommisionFsr(this.fsrcommisionReq).subscribe((res) => {
      this.spinner.hide();
      // console.log("save res is " + res);
      this.fsrcommisionReq = new batteryCommisionFsr();
      this._global.alert({ type: '', message: "Commision FSR Created Successfully"});
         this.onCancle.emit('cancled');
      this.btCreateFsr.asseRequests = [];
    }, (error) => {
      console.log(error.status);
      if(error.status==400)
      {
        this.spinner.hide();
        if((error.error==null || error.error==undefined))
        {

          this.spinner.hide();
          this._global.alert({ type: 'alert', message: 'Invalid Value Entered' });
        
        }else{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message: error.error.message });
        }
      }
      else{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
        
    }
      this.btCreateFsr.asseRequests = [];

      


    });

    }
    else
    {
      console.log(this.fsrcommisionReq)
      
      this.spinner.show();
      this.btFsrService.UpdateCommFsr(this.fsrcommisionReq,this.fsrcommisionReq.commFSRId).subscribe((res)=>{
        this.spinner.hide();
        this.fsrcommisionReq = new batteryCommisionFsr();
        this._global.alert({ type: '', message: "Commision FSR Update Successfully"});
        this.onCancle.emit('cancled');
      },(error)=>{
       
        console.log(error.status);
        if(error.status==400)
        {
          this.spinner.hide();
          if((error.error==null || error.error==undefined))
          {
  
            this.spinner.hide();
            this._global.alert({ type: 'alert', message: 'Invalid Value Entered' });
          
          }else{
            this.spinner.hide();
            this._global.alert({ type: 'alert', message: error.error.message });
          }
        }
        else{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message: error.error.message });
          
      }
        
      });
     
    }

    //  }
    }
    }
  }

  
  }
}
