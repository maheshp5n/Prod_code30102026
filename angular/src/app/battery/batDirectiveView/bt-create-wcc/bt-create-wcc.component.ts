import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtWccServiceService } from '../../bat_services/bt-wcc-service.service';

@Component({
  selector: 'app-bt-create-wcc',
  templateUrl: './bt-create-wcc.component.html',
  styleUrls: ['./bt-create-wcc.component.scss']
})
export class BtCreateWccComponent implements OnInit {

  today = new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  wccId: number;

  _isEditMode: boolean;
  vendorPoGlcode:number=0;

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set ElementWCCID(WccId: number) {
    this.wccId = WccId;
    if (this._isEditMode) {
      this.btwccservice.getWccById(this.wccId).subscribe(data => {
        this._wccdetails.id = data.id;
        this._wccdetails.wccTypeId = data.wccTypeId;
        this._wccdetails.startDate = data.fromDate;
        this._wccdetails.toDate = data.toDate;
        this.getPO(data.wccTypeId);
      
      });
      this.getWccsa(this.wccId);
    }
  }
  get ElementWCCID(): number {
    return this.wccId;
  }

  // variable decleare

  wcctypeList: Array<any> = [];

  // wcc use for edit mode
  wccSaList: Array<any> = [];

  _wccdetails: any = {
    wccTypeId: '0',
    startDate: this.today,
    toDate: this.today,
    amount: 0, taxAmount: 0, totalAmount: 0
  };
  _wccSa: Array<any> = [];
  _poArray: Array<any> = [];
  _onlyPo: Array<string> = [];

  @Output() onWccCreated: EventEmitter<string> = new EventEmitter<string>();

  @Output() onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private spinner: NgxSpinnerService,
    private datePipe: DatePipe,
    private btwccservice: BtWccServiceService,private _global : GlobalserviceService) {
    this.date = this.datePipe.transform(this.date, 'dd-MM-yyyy');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];

  }


  ngOnInit() {
    this.getWccType();
  }
  getWccType() {
    this.spinner.show();
    this.btwccservice.getType().subscribe(data => {
      this.wcctypeList = data;
      this.spinner.hide();
    }, error => this.spinner.hide());
   
  }
  WCCTypeId : number;
  getTypeId(typeId){
   this.WCCTypeId = typeId;
   for(let i=0;i<this.wcctypeList.length;i++)
   {
     if(this.WCCTypeId==this.wcctypeList[i].id)
     {
       this.vendorPoGlcode=this.wcctypeList[i].vendorPoGlcode;
     }
   }
  }

  findSa() {
    console.log("Find SA");
    this.getTypeId(this._wccdetails.wccTypeId);
    this.spinner.show();
    this.btwccservice.getPendingSA(this._wccdetails.wccTypeId,
        this.datePipe.transform(this._wccdetails.startDate, 'yyyy-MM-dd HH:mm:ss'), this.datePipe.transform(this._wccdetails.toDate, 'yyyy-MM-dd HH:mm:ss')).subscribe(data => {
        this.spinner.hide();
        this._wccSa = data; 
              
        if (this.wccSaList.length > 0) {
          this.wccSaList.forEach(d => {
            this._wccSa.push({
              selected: true,
              appointmentNumber: d.appointmentNumber,
              caseCloseDate: d.caseCloseDate,
              caseId: d.caseId,
              customerName: d.customerName,
              caseType: d.caseType,
              caseReason: d.caseReason,
              circleName: d.circleName,
              poNumber: d.poNumber,
              poId: d.poDetailsId,
              service_appointment_id: d.id,
              amount: d.amount,
              taxRate: d.taxRate,
              taxAmount: d.taxAmount,
              totalAmount: d.totalAmount
            });
          });
        }
        
        this.recalculate();
        this.getPO(this.vendorPoGlcode);
        
      }, error => this.spinner.hide());
  }
  cancelData() {
    this.onCancle.emit('cancled');
  }

  getWccsa(wccId) {
    this.spinner.show();
    this.btwccservice.getSAbyWccId(wccId).subscribe(data => {
      this.wccSaList = data;
      console.log(this.wccSaList);
      data.forEach(d => {
        this._wccSa.push({
          selected: true,
          appointmentNumber: d.appointmentNumber,
          caseCloseDate: d.caseCloseDate,
          caseId: d.caseId,
          customerName: d.customerName,
          caseType: d.caseType,
          caseReason: d.caseReason,
          circleName: d.circleName,
          poNumber: d.poNumber,
          poId: d.poDetailsId,
          service_appointment_id: d.id,
          amount: d.amount,
          taxRate: d.taxRate,
          taxAmount: d.taxAmount,
          totalAmount: d.totalAmount
        });
      });
      
      this.spinner.hide();
      this.recalculate();
    }, error => this.spinner.hide());
  }

  getPO(id) {
    this.btwccservice.getPObyTypeId(id).subscribe(data => {
      this.spinner.hide();
      this._poArray = data;
      this._onlyPo = [];
      data.forEach(d => {
        let found = false;
        for (let i = 0; i < this._onlyPo.length; i++) {
          if (d.poNumber === this._onlyPo[i]) {
            found = true;
          }
        }
        if (!found) {
          this._onlyPo.push(d.poNumber);
        }
      });
      this.recalculate();
    }, error => this.spinner.hide());
  }
  getWcc(id) {
    this.btwccservice.getWccById(id).subscribe(data => {
      this._wccdetails.id = data.id;
      this._wccdetails.wccTypeId = data.wccTypeId;
      this._wccdetails.startDate = data.fromDate;
      this._wccdetails.toDate = data.toDate;
      this.getPO(data.wccTypeId);
      this.recalculate();
    });
  }

  getActivityPriceList(poNumber): Array<any> {
    const temp = [];
    for (let i = 0; i < this._poArray.length; i++) {
      if (this._poArray[i].poNumber === poNumber) {
        temp.push(this._poArray[i]);
      }
    }
    return temp;
  }
  postWCC() {
    this._wccdetails.saIds = [];
  
    this._wccdetails.startDate = this.datePipe.transform(this._wccdetails.startDate, 'yyyy-MM-dd');
    this._wccdetails.toDate = this.datePipe.transform(this._wccdetails.toDate, 'yyyy-MM-dd');
    if (this._wccdetails.wccTypeId == undefined || this._wccdetails.wccTypeId == null || this._wccdetails.wccTypeId == 0) {    
    this._global.alert({type:'alert',message:'Wcc Type Not found'});
      return;
    }
   
    if (!this._isEditMode) {
      for (let i = 0; i < this._wccSa.length; i++) {
     
        if (this._wccSa[i].selected) {
          this._wccdetails.saIds.push({ saId: this._wccSa[i].service_appointment_id, poId: this._wccSa[i].poId });
        }
        if (this._wccdetails.saIds.length == 0) {
          this._global.alert({type:'alert',message:'Please Select SA From List'});
          return;
        }
      }
      this.spinner.show();
      this.btwccservice.postWCC(this._wccdetails).subscribe(data => {
        this.spinner.hide();
        this._global.alert({type:'',message:data.manulId+' WCC Created'});
        this.onWccCreated.emit('cancled');
      }, error => {
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
    if (this._isEditMode) {
      if (this._wccdetails.id == undefined || this._wccdetails.id == null || this._wccdetails.id == '' || this._wccdetails.id == 0) {     
        this._global.alert({type:'alert',message:`Wcc ID Missing`});
        return;
      }
// add by shagufta
      for (let i = 0; i < this._wccSa.length; i++) {
        if (this._wccSa[i].selected) {
          this._wccdetails.saIds.push({ saId: this._wccSa[i].service_appointment_id, poId: this.ponumberid });
        }
      }
      this.spinner.show();
      this.btwccservice.updateWCC(this._wccdetails, this.ElementWCCID).subscribe(data => {
        this.spinner.hide();
        this._global.alert({type:'',message:data.manulId+' WCC Updated'});
        this.onWccCreated.emit('cancled');
      }, error => {
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
  }

  findPoLocall(poid) {
    const ln = this._poArray.length;
    for (let i = 0; i < ln; i++) {
      if (this._poArray[i].id == poid) {
        console.log(this._poArray)
        return this._poArray[i];
        break;
      }
    }
  }

  recalculate() {
    const lt = this._wccSa.length;
    let wccAmount = 0;
    let wccTaxamount = 0;
    let wccTotalAmount = 0;
    for (let i = 0; i < lt; i++) {
      let po = this.findPoLocall(this._wccSa[i].poId);
      try {
        this._wccSa[i].amount = po.activityPrice;
        this._wccSa[i].taxRate = po.taxRate;
        this._wccSa[i].taxAmount = po.activityPrice * po.taxRate / 100;
        this._wccSa[i].totalAmount = this._wccSa[i].amount + this._wccSa[i].taxAmount;
        if (this._wccSa[i].selected) {
          wccAmount += this._wccSa[i].amount;
          wccTaxamount += this._wccSa[i].taxAmount;
          wccTotalAmount += this._wccSa[i].totalAmount;
        }
      } catch (e) {
        console.log(e);
      }

    }
    this._wccdetails.amount = wccAmount;
    this._wccdetails.taxAmount = wccTaxamount;
    this._wccdetails.totalAmount = wccTotalAmount;
  }

  // showAlert(message) {
  //   this.alertmeta.ngClass = 'alert alert-danger';
  //   this.alertmeta.message = message;
  //   this.popupSuccessModel.show();
  // }
  // showMessage(message) {
  //   this.alertmeta.ngClass = 'alert alert-success';
  //   this.alertmeta.message = message;
  //   this.popupSuccessModel.show();
  // }

  getRemainingQuanitty(poid) {
    const len = this._poArray.length;
    for (let i = 0; i < len; i++) {
      if (this._poArray[i].id == poid) {
        return this._poArray[i].remainingQuantity;
      }
    }
  }
  ponumberid:any;
  pochange(ponum){
    console.log(ponum);
    this._poArray.forEach(e=>{
      if(e.poNumber==ponum){
      
     this.ponumberid=e.id;
      }
    })
  }

}
