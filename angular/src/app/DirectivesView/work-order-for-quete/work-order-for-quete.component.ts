import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { WorkOrderRequest, QuoteResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { workOrderForQuetService } from '../../services/workorderforquote.services';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
@Component({
  selector: 'app-work-order-for-quete',
  templateUrl: './work-order-for-quete.component.html',
  styleUrls: ['./work-order-for-quete.component.scss']
})
export class WorkOrderForQueteComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;
  QueteId:number;
  _isEditMode: boolean;
  @Output()
  onworkOrderCreated: EventEmitter<WorkOrderRequest> = new EventEmitter<WorkOrderRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Input()
  set QueteID(QuteId: number) {
    this.QueteId = QuteId;
  }

  get QueteID(): number {
    return this.QueteId;
  }
  @Input()
  set editworkOrder(quoteResponse: QuoteResponse) {
    this.quoteResponse = quoteResponse;
  }

  get editworkOrder(): QuoteResponse {
    return this.quoteResponse;
  }
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  
  @Output()
  // addWorkCreated: EventEmitter<cityDetailsReq> = new EventEmitter<cityDetailsReq>();

  today=new Date();
  message:string;
  popupNgClass: string = 'alert alert-danger center center-block';
  dateCustomClasses: DatepickerDateCustomClasses[];
  workOrderRequest:WorkOrderRequest=new WorkOrderRequest()
  quoteResponse: QuoteResponse = new QuoteResponse();
  constructor(private workorderservice:workOrderForQuetService,private datePipe: DatePipe,private spinner:NgxSpinnerService,private loginService: Loginservices) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
   }

  ngOnInit() {
  }
  postworkOrrder(){
    this.spinner.show();
  this.workOrderRequest.quoteId=this.QueteId
  this.workOrderRequest.workOrderDate=this.quoteResponse.workOrderDate;
  this.workOrderRequest.workOrderNumber=this.quoteResponse.workOrderNumber;
    console.log(this.workOrderRequest)
    this.workorderservice.updateCaseInvoice(this.workOrderRequest).subscribe(data=>{
      this.spinner.hide();
      this.onworkOrderCreated.emit(data);
      this.message = 'Updated Successfully';
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show()
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();

    })

  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  workOrdercancelData(){
    this.onCancle.emit('cancled');
  }

}
