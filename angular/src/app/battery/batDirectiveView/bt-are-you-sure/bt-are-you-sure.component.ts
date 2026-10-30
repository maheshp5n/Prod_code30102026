import { Component, OnInit, Output, ViewChild,EventEmitter,Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CommercialService } from '../../../services/commercial.services';
import { CaseResponse, QuoteResponse } from '../../../services/model';
import { QuoteService } from '../../../services/Quote.services';

@Component({
  selector: 'app-bt-are-you-sure',
  templateUrl: './bt-are-you-sure.component.html',
  styleUrls: ['./bt-are-you-sure.component.scss']
})
export class BtAreYouSureComponent implements OnInit {
  popupNgClass: string = 'alert alert-danger center center-block';
  message:string;
  caseid:number;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };
  quoteId:number;
  approvalmessage:string;
  constructor(private _quoteService:QuoteService,private spinner: NgxSpinnerService,private commercialService:CommercialService,
    private _global : GlobalserviceService) { }
  @Output()
  onDOcumnetSuccess: EventEmitter<CaseResponse> = new EventEmitter<CaseResponse>();
  onSubmitted:EventEmitter<QuoteResponse>=new EventEmitter<QuoteResponse>();
  yesQuote:boolean;
  @Input()
  set caseID(caseid: any) {
    this.caseid=caseid;
    console.log(this.caseid);
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set QuoteID(quoteid:number){
  this.quoteId=quoteid;
  }
  get QuoteID():number{
    return this.quoteId;
  }
  @Input()
  set ApprovalMessage(approvalmessage:string){
    this.approvalmessage=approvalmessage;
  }
  get ApprovalMessage():string{
    return this.approvalmessage;
  }
  @Input()
  set YesQuote(yesQuote:boolean){
    this.yesQuote=yesQuote;
  }
  get YesQuote():boolean{
    return this.yesQuote;
  }
  @Output()
  noRequestFor: EventEmitter<string> = new EventEmitter<string>();
  ngOnInit() {
  }
  noRequestForSure() {
    this.noRequestFor.emit('cancled');
  }
  yesRequestFor(){
    if(this.yesQuote==true){
      this.spinner.show();
      this._quoteService.postsubmitforapproval(this.quoteId).subscribe(data=>{
        this.spinner.hide();
        this._global.alert({ type: '', message: 'Submitted Successfully' });
        this.onSubmitted.emit(data);
      },error=>{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});
      })
    }
    else{
      this.spinner.show();
      this.commercialService.updateDocRecieved(this.caseid).subscribe(data=>{
        this.spinner.hide();
        this.popupNgClass='alert alert-success center center-block';
        this.message='Updated Successfully';
          this.showMessage(this.message);
         this.onDOcumnetSuccess.emit(data);
        // this.popupSuccessModel.show();
      },error=>{
        this.spinner.hide();
        this.showAlert(error.error.message)
        // this.popupSuccessModel.show();
      });
    }
    

  }
  showAlert(message) {
    this.alertmeta.ngClass = 'alert alert-danger';
    this.alertmeta.message = message;
    this.popupSuccessModel.show();
  }
  showMessage(message) {
    this.alertmeta.ngClass = 'alert alert-success';
    
    this.alertmeta.message = message;
    this.popupSuccessModel.show();
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.noRequestFor.emit('cancled');
    }
  }

}
