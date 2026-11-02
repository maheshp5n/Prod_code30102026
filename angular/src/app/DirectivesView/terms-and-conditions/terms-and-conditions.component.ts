import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-terms-and-conditions',
  templateUrl: './terms-and-conditions.component.html',
  styleUrls: ['./terms-and-conditions.component.scss']
})
export class TermsAndConditionsComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  // @Output()
  // onTermAndCondintionCreated: EventEmitter<any> = new EventEmitter<any>();
  // @Output()
  // onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';


  constructor(private spinner: NgxSpinnerService) { }

  ngOnInit() {
  }

  addTermAndCondition() {
    
  }
  removeTermAndCondition() {
    
  }

  postCreate(){

  }

  cancelData(){
    //this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
   // this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
     // this.onCancle.emit('cancled');
    }
  }

}
