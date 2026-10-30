import { Component, Input, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LocationForList } from '../../../services/model';
import { BtJobCardServiceService } from '../../bat_services/bt-job-card-service.service';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BtConsumedItemsForRepair, BtJobCardResponse, BtProductList, BtUpdateJobCardRequest, IsCheckEditJobCard } from '../../bt_model';

@Component({
  selector: 'app-bat-edit-job-card',
  templateUrl: './bat-edit-job-card.component.html',
  styleUrls: ['./bat-edit-job-card.component.css']
})
export class BatEditJobCardComponent implements OnInit {
  isChecked: IsCheckEditJobCard = new IsCheckEditJobCard();
  jobCardResonse: BtJobCardResponse = new BtJobCardResponse();
  btEditjobCardRequest: BtUpdateJobCardRequest = new BtUpdateJobCardRequest();
  btConsumeItemsReq: BtConsumedItemsForRepair = new BtConsumedItemsForRepair();
  btConsumeItemList: Array<BtConsumedItemsForRepair> = [];
  qcEngineerList: Array<any> = [];
  ngClassBerRemarks: string;
  repairStateList = [
    {
      status: 'OK',
      value: 1,
    },
    {
      status: 'BER',
      value: 5,
    }
  ];
  avilabStockAtDestination: any = {
    exicomTotalOkQuantity: 0,
    quantity_in_on_hand: 0
  };

  locationForList: Array<LocationForList> = [];
  btProductId;

  resolutionMethods = [];
  batteryBalanceAt = [];
  berRemarks = [];
  filename: string;
  // new 
  BRCMethod = [];
  EngRemarksMethods = [];

  @Input()
  set jobCardRes(jobCardRes: BtJobCardResponse) {
    this.jobCardResonse = jobCardRes;
    this.btEditjobCardRequest.assignToQc = jobCardRes.isAssigned == null || !jobCardRes.isAssigned ? false : true;
    this.btEditjobCardRequest.batteryAhAft = jobCardRes.batteryAhAft;
    this.btEditjobCardRequest.batteryVoltageAft = jobCardRes.batteryVoltageAft;
    this.btEditjobCardRequest.batteryVoltageBef = jobCardRes.batteryVoltageBef;
    //observations
    this.btEditjobCardRequest.observation1 = jobCardRes.observation1;
    this.btEditjobCardRequest.observation2 = jobCardRes.observation2;
    this.btEditjobCardRequest.observation3 = jobCardRes.observation3;
    this.btEditjobCardRequest.observation4 = jobCardRes.observation4;
    
    this.btEditjobCardRequest.repairState = Number(jobCardRes.jobCardRepairProductState);
    if (this.btEditjobCardRequest.repairState == 1 || this.btEditjobCardRequest.repairState == null) {
      this.btEditjobCardRequest.berRemarks = '0';
      this.ngClassBerRemarks = 'form-group';

    }
    if (this.btEditjobCardRequest.repairState == 5) {
      if (jobCardRes.berRemarks == null || jobCardRes.berRemarks == '') {
        this.btEditjobCardRequest.berRemarks = '0';
        this.ngClassBerRemarks = 'form-group required';
      }
      else {
        this.btEditjobCardRequest.berRemarks = jobCardRes.berRemarks;
        this.ngClassBerRemarks = 'form-group required';
      }
    }
    this.btEditjobCardRequest.btBalanceAt = jobCardRes.btBalanceAt == undefined || jobCardRes.btBalanceAt == null ? '0' : jobCardRes.btBalanceAt;
    this.btEditjobCardRequest.cellBledge = jobCardRes.cellBledge;
    this.btEditjobCardRequest.cellVmax = jobCardRes.cellVmax;
    this.btEditjobCardRequest.cellVmin = jobCardRes.cellVmin;
    
    // old 
    // this.btEditjobCardRequest.correctiveAction = jobCardRes.correctiveAction;
    // new 
    this.btEditjobCardRequest.correctiveAction = jobCardRes.correctiveAction == undefined || jobCardRes.correctiveAction == null ? '0' : jobCardRes.correctiveAction;
    
    this.btEditjobCardRequest.engRemarks = jobCardRes.engRemarks;
    this.btEditjobCardRequest.engResolutionMethod = jobCardRes.engResolutionMethod == undefined || jobCardRes.engResolutionMethod == null ? '0' : jobCardRes.engResolutionMethod;
    this.btEditjobCardRequest.jobCardId = jobCardRes.jobCardId;
    this.btEditjobCardRequest.matSorteg = jobCardRes.matSorteg;
    this.btEditjobCardRequest.filename = jobCardRes.filename;
    //observations
    this.btEditjobCardRequest.observation1 = jobCardRes.observation1;
    this.btEditjobCardRequest.observation2 = jobCardRes.observation2;
    this.btEditjobCardRequest.observation3 = jobCardRes.observation3;
    this.btEditjobCardRequest.observation4 = jobCardRes.observation4;
    

  }
  get jobCardRes(): BtJobCardResponse {
    return this.jobCardResonse;
  }
  @Output()
  cancled: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  editJobCard: EventEmitter<BtJobCardResponse> = new EventEmitter<BtJobCardResponse>();
  @ViewChild('choosenFiles', { static: false }) choosenFiles: ModalDirective;
  btProductList: Array<BtProductList> = [];
  constructor(
    private _btProdReqService: BtproductrequestService, private _global: GlobalserviceService, private _jobCardService: BtJobCardServiceService,
    private _spinner: NgxSpinnerService,
    private _btWareHouseservice: BtwarehouseService) { }

  ngOnInit(): void {
    this.getListOfRepairEnginner();
    this.getSiblingsLocation();
    this.getBerRemarks();
    this.getResultionMethod();
    this.getBatterybalanceAt();
    // new
    this.getBRCMethod();
    this.getengRemarksMethods1();

  }
  destinationLocation: string;
  destinationId: number;
  getSiblingsLocation() {
    this._spinner.show();
    this._btWareHouseservice.getLocationParentSiblingAndChilds().subscribe(data => {
      this._spinner.hide();
      this.locationForList = data;
      this.destinationLocation = this.locationForList[0].locationName;
      this.destinationId = this.locationForList[0].locationId;
    }, error => this._spinner.hide());
  }
  getAvilableStockAtDestination() {
    this.avilabStockAtDestination = {
      exicomTotalOkQuantity: 0,
      quantity_in_on_hand: 0
    };
    this._btWareHouseservice.getAvailableStock(
      this.destinationId,
      this.btProductId)
      .subscribe(data => this.avilabStockAtDestination = data);
  }
  getAssignQC(val) {
    if (val) {
      this.isChecked.showAssignEngineerBox = false;
      this.btEditjobCardRequest.assignToQcEmailId = '0';
    }
    else {
      this.isChecked.showAssignEngineerBox = true;
      this.btEditjobCardRequest.assignToQcEmailId = '0';
    }

  }

  getProductConsume(val) {
    if (val) {
      this.isChecked.showProductConsumeInput = true;
    }
    else {
      this.isChecked.showProductConsumeInput = false;
    }

  }
  getListOfRepairEnginner() {
    this.qcEngineerList = [];
    this._spinner.show();
    this._jobCardService.btServiceOrderRepairEngineerList().subscribe(data => {
      for (let i = 0; i < data.length; i++) {
        if (data[i].designationId == 51 || data[i].designationId == 52) {
          this.qcEngineerList.push(data[i]);
        }
      }
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getConsumeFromStock(val) {
    if (val) {
      this.isChecked.isShowAvStock = true;

    }
    else {
      this.isChecked.isShowAvStock = false;
    }
  }
  searchBtProductList($event) {
    this._btProdReqService.fetchProductList($event.term).subscribe(data => {
      const temp = [];
      data.forEach(d => {
        let btPrList = new BtProductList();
        btPrList.id = d.id;
        btPrList.partCode = d.partCode;
        btPrList.productType = d.productType;
        btPrList.bothvalues = d.partCode + ' ' + d.productType;
        temp.push(btPrList);
      })
      this.btProductList = temp;
    })
  }
  getChangePartCode(id) {
    for (let i = 0; i < this.btProductList.length; i++) {
      if (this.btProductList[i].id == id) {
        this.btConsumeItemsReq.partCode = this.btProductList[i].partCode;
      }
    }
    this.btProductId = id;
    this.getAvilableStockAtDestination();
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
  addItemInList() {
    this.btConsumeItemsReq.isFromStock = this.isChecked.isStockConsume;
    if (this.btConsumeItemsReq.consumedProductId == null || this.btConsumeItemsReq.consumedProductId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Product Part Code' });
      return;
    }
    if (this.btConsumeItemsReq.quantity == null || this.btConsumeItemsReq.quantity == 0 || this.btConsumeItemsReq.quantity == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Valid Quantity' });
      return;
    }
    let temp = new BtConsumedItemsForRepair();
    temp.consumedProductId = this.btConsumeItemsReq.consumedProductId;
    temp.quantity = this.btConsumeItemsReq.quantity;
    temp.isFromStock = this.btConsumeItemsReq.isFromStock;
    temp.partCode = this.btConsumeItemsReq.partCode;
    if (temp.isFromStock) {
      temp.consumeFromStock = 'Yes';
    }
    else {
      temp.consumeFromStock = 'No';
    }
    if (this.btEditjobCardRequest.consumedItems == null || this.btEditjobCardRequest.consumedItems == undefined) {
      this.btEditjobCardRequest.consumedItems = [];
    }
    let alreadyExist = false;
    for (let i = 0; i < this.btEditjobCardRequest.consumedItems.length; i++) {
      if (this.btEditjobCardRequest.consumedItems[i].consumedProductId == temp.consumedProductId) {
        alreadyExist = true;
        break;
      }
    }
    if (alreadyExist) {
      this._global.alert({ type: 'alert', message: `Part code ${temp.partCode} Already Exists,Please Select Different` });
      return;
    }
    this.btEditjobCardRequest.consumedItems.push(temp);
    this.btConsumeItemsReq.consumedProductId = null;
    this.btConsumeItemsReq.quantity = null;
    this.isChecked.isStockConsume = false;
    this.avilabStockAtDestination.exicomTotalOkQuantity = null;
  }
  removeItem(indexNumber) {
    let counter = this.btEditjobCardRequest.consumedItems.length;
    let temp = [];
    for (let i = 0; i < counter; i++) {
      if (i != indexNumber) {
        temp.push(this.btEditjobCardRequest.consumedItems[i]);
      }
    }
    this.btEditjobCardRequest.consumedItems = temp;
  }
  getChangeCurrentStatus() {
    if (this.btEditjobCardRequest.repairState == 1) {
      this.ngClassBerRemarks = 'form-group';
      this.btEditjobCardRequest.berRemarks = '0';
    }
    else {
      this.ngClassBerRemarks = 'form-group required';
    }
  }
  fileList = [];
  onFileSelect($event) {
    if ($event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < $event.target.files.length; i++) {
        this.fileList.push($event.target.files[i]);
      }
    }
  }
  uploadFile() {
    let formData = new FormData();
    formData.append('file', this.fileList[0]);
    this._spinner.show();
    this._jobCardService.postUploadFile(formData, this.jobCardResonse.jobCardId).subscribe(data => {
      this._spinner.hide();
      this.choosenFiles.hide();
      this._global.alert({ type: '', message: 'Successfully Uploaded!' });
      this.fileList = [];
      this.getUploadFile();
    }, err => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    })
  }
  cancelChoosen() {
    this.fileList = [];
    this.choosenFiles.hide();
  }
  getUploadFile() {
    this._spinner.show();
    this._jobCardService.getUploadFile(this.jobCardResonse.jobCardId).subscribe(data => {
      this._spinner.hide();
      this.btEditjobCardRequest.filename = data.file_name;
    }, err => {
      this._spinner.hide();
    });
  }
  updateJobCard() {
    this.btEditjobCardRequest.assignToQc = this.isChecked.isAssignQC;
    this.btEditjobCardRequest.jobCardId = this.jobCardRes.jobCardId;
    if (this.btEditjobCardRequest.assignToQc == false) {
      this._global.alert({ type: 'alert', message: 'Please Select Assign To QC' });
      return;
    }
    if (this.btEditjobCardRequest.assignToQcEmailId == '0' || this.btEditjobCardRequest.assignToQcEmailId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select QC Engineer' });
      return;
    }
    if (this.btEditjobCardRequest.repairState == 0 || this.btEditjobCardRequest.repairState == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Current Status' });
      return;
    }
    if (this.btEditjobCardRequest.batteryVoltageBef == undefined || this.btEditjobCardRequest.batteryVoltageBef == null || this.btEditjobCardRequest.batteryVoltageBef == '' || this.btEditjobCardRequest.batteryVoltageBef == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Battery Voltage Before Repair' });
      return;
    }
    if (this.btEditjobCardRequest.batteryAhAft == undefined || this.btEditjobCardRequest.batteryAhAft == null || this.btEditjobCardRequest.batteryAhAft == '0' || this.btEditjobCardRequest.batteryAhAft == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill Battery AH After Repair' });
      return;
    }
    if (this.btEditjobCardRequest.batteryVoltageAft == undefined || this.btEditjobCardRequest.batteryVoltageAft == null || this.btEditjobCardRequest.batteryVoltageAft == '' || this.btEditjobCardRequest.batteryVoltageAft == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Battery Voltage After  Repair' });
      return;
    }
    if (this.btEditjobCardRequest.cellVmax == undefined || this.btEditjobCardRequest.cellVmax == null || this.btEditjobCardRequest.cellVmax == '' || this.btEditjobCardRequest.cellVmax == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Cell Vmax (v) ' });
      return;
    }
    if (this.btEditjobCardRequest.cellVmin == undefined || this.btEditjobCardRequest.cellVmin == null || this.btEditjobCardRequest.cellVmin == '' || this.btEditjobCardRequest.cellVmin == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Cell Vmin (v) ' });
      return;
    }
    // if (this.btEditjobCardRequest.correctiveAction == undefined || this.btEditjobCardRequest.correctiveAction == null || this.btEditjobCardRequest.correctiveAction == '' || this.btEditjobCardRequest.correctiveAction == '0') {
    //   this._global.alert({ type: 'alert', message: 'Please Fill BRC Eng. Corrective Action ' });
    //   return;
    // }
    if (this.btEditjobCardRequest.correctiveAction == undefined || this.btEditjobCardRequest.correctiveAction == null || this.btEditjobCardRequest.correctiveAction == '' || this.btEditjobCardRequest.correctiveAction == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill BRC Eng. Corrective Action ' });
      return;
    }
    if (this.btEditjobCardRequest.engResolutionMethod == undefined || this.btEditjobCardRequest.engResolutionMethod == null || this.btEditjobCardRequest.engResolutionMethod == '' || this.btEditjobCardRequest.engResolutionMethod == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill BRC Eng. Resolution Method ' });
      return;
    }
    if (this.btEditjobCardRequest.matSorteg == undefined || this.btEditjobCardRequest.matSorteg == null || this.btEditjobCardRequest.matSorteg == '' || this.btEditjobCardRequest.matSorteg == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Material Shortage (If Any) ' });
      return;
    }
    if (this.btEditjobCardRequest.repairState == 5 && (this.btEditjobCardRequest.berRemarks == undefined || this.btEditjobCardRequest.berRemarks == null || this.btEditjobCardRequest.berRemarks == '' || this.btEditjobCardRequest.berRemarks == '0')) {
      this._global.alert({ type: 'alert', message: 'Please Fill BER Remark' });
      return;
    }
    if (this.btEditjobCardRequest.btBalanceAt == undefined || this.btEditjobCardRequest.btBalanceAt == null || this.btEditjobCardRequest.btBalanceAt == '' || this.btEditjobCardRequest.btBalanceAt == '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill Battery Balance' });
      return;
    }
    if (this.btEditjobCardRequest.cellBledge == undefined || this.btEditjobCardRequest.cellBledge == null || this.btEditjobCardRequest.cellBledge == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill Cell Bulged' });
      return;
    }
    if (this.btEditjobCardRequest.engRemarks == undefined || this.btEditjobCardRequest.engRemarks == null || this.btEditjobCardRequest.engRemarks == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill Engineer Remarks' });
      return;
    }
    if (this.btEditjobCardRequest.filename == '' || this.btEditjobCardRequest.filename == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Upload File' });
      return;
    }
    this._spinner.show();
    this._jobCardService.btUpdateJobCard(this.jobCardRes.jobCardId, this.btEditjobCardRequest).subscribe(data => {
      this._spinner.hide();
      this.editJobCard.emit(data);
    }, err => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });

  }
  // new BRC Eng. Corrective Action
  getBRCMethod() {
    this._jobCardService.getBRC().subscribe(data => {
      this.BRCMethod = data;
    });
  }

  getengRemarksMethods1() {
    this._jobCardService.getengRemarksMethods().subscribe(data => {
      this.EngRemarksMethods = data;
      console.log('dssa', this.EngRemarksMethods )
  
    });
  }


  cancelJobCard() {
    this.cancled.emit();

  }

  getResultionMethod() {
    this._jobCardService.getResolution().subscribe(data => {
      this.resolutionMethods = data;
    });
  }

  getBatterybalanceAt() {
    this._jobCardService.getBatteryBalanceAt().subscribe(data => {
      this.batteryBalanceAt = data;
    });
  }

  getBerRemarks() {
    this._jobCardService.getBerRemrks().subscribe(data => this.berRemarks = data);
  }


}
