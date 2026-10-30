import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';
import { CaseResponse, CaseInvoiceRequest, CaseProofOfDeliveryRequest } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatePipe } from '@angular/common';
import { CommercialService } from '../../services/commercial.services';
import { caseService } from '../../services/caseService.service';

@Component({
  selector: 'app-create-proove-of-delivery',
  templateUrl: './create-proove-of-delivery.component.html',
  styleUrls: ['./create-proove-of-delivery.component.scss']
})
export class CreateProoveOfDeliveryComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  message: string;
  istrue: boolean;
  noMatchCase: string;
  iscaseSelected: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempManualId: string;
  tempId: number;
  today = new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  casePDRequest: CaseProofOfDeliveryRequest = new CaseProofOfDeliveryRequest();
  caseres: CaseResponse = new CaseResponse()
  caseid: any;
  podDate:any;
  expectDate:any;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set caseID(caseid: any) {
    this.caseid = caseid;
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set editPDData(caseres: CaseResponse) {
    this.caseres = caseres;
    // this.casePDRequest.podNumber = caseres.podnumber;
    // this.casePDRequest.podDate = caseres.poddate;

    if(this.caseres.poddate==null||caseres.poddate==undefined ||this.caseres.podShippingExpectedDate==undefined || this.caseres.podShippingExpectedDate==null){
     this.podDate=this.today;
     this.expectDate=this.today;
    }
    else
  {
    this.expectDate=this.dateConversion(this.caseres.podShippingExpectedDate);
    this.podDate=this.dateConversion(this.caseres.poddate);
  }
  this.casePDRequest.podNumber=this.caseres.podnumber;
  // this.casePDRequest.podDate=this.caseres.poddate;
  // this.casePDRequest.podShippingExpectedDate=this.caseres.podShippingExpectedDate;
  this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
  this.casePDRequest.podShippingToName=this.caseres.podShippingToName;
  this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;

  }
  get editPDData(): CaseResponse {
    return this.caseres;
  }

  @Output()
  onCasePDCreated: EventEmitter<CaseResponse> = new EventEmitter<CaseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe, private caseservice: caseService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
  }
  postPD() {
    
     if (this.casePDRequest.podNumber === null || this.casePDRequest.podNumber === undefined) {
      this.message = "Please Fill POD Number"
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.casePDRequest.podDate === null || this.casePDRequest.podDate === undefined) {
      this.message = "Please Fill POD Date"
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else {
      this.isEditMode = true;
      this.casePDRequest.caseId = this.caseid;
      this.spinner.show();
      this.caseservice.updatePD(this.casePDRequest).subscribe(data => {
        this.onCasePDCreated.emit(data);
        this.spinner.hide()
        this.message = 'Updated Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.popupSuccessModel.show();
      }, error => {
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = error.error.message;
        this.popupSuccessModel.show();
      }
      )
    }
  }
  cancelPDData() {
    this.casePDRequest = new CaseProofOfDeliveryRequest();
    this.onCancle.emit('cancled');

  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.casePDRequest=new CaseProofOfDeliveryRequest();
    }
  }

  dateConversion(date:Date){
    let d=new Date(date);
    d.setMinutes(d.getMinutes());
    return d;
  }
  onexpectchange($event){
    this.casePDRequest.podShippingExpectedDate=$event;
  }
  onpodateDatechange($event){
    this.casePDRequest.podDate=$event;
  }
}
