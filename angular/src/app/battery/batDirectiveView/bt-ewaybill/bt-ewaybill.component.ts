import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bt-ewaybill',
  templateUrl: './bt-ewaybill.component.html',
  styleUrls: ['./bt-ewaybill.component.css']
})
export class BtEwaybillComponent implements OnInit {

  @Input() ptNumber: string = "";   
  @Input() ptId: number;            

  ewayBillNumber: string = "";
  ewayBillDate: string = "";
  uploadedFile: File | null = null;

  message: string;
 istrue: boolean;


  @Output() onSaved = new EventEmitter<any>();
  @Output() onCancel = new EventEmitter<any>();

  constructor(
    private http: HttpClient,
    private _spinner: NgxSpinnerService,
     private _global: GlobalserviceService
  ) {}

  ngOnInit(): void {}

  onFileSelected(event: any) {
    this.uploadedFile = event.target.files[0];
  }
 saveEwayBill() {

  this.istrue = false;

  
  if (this.ewayBillNumber == null || this.ewayBillNumber.trim() === '') {
    this.message = 'Please Enter E-Way Bill Number';
  }
  else if (this.ewayBillDate == null || this.ewayBillDate === '') {
    this.message = 'Please Select E-Way Bill Date';
  }
  else if (this.uploadedFile == null) {
    this.message = 'Please Upload E-Way Bill File';
  }
  else {
    this.istrue = true;
  }

  
  if (!this.istrue) {
    this._global.alert({ type: 'alert', message: this.message });
    return;
  }


  const fd = new FormData();
  fd.append("ptNumber", this.ptNumber);
  fd.append("ewaybillNumber", this.ewayBillNumber);
  fd.append("ewaybillDate", this.ewayBillDate);
  fd.append("file", this.uploadedFile as File);

  this._spinner.show();

  this.http.post(
    `/bt/api/producttransfer/${this.ptId}/ewaybill`,
    fd
  ).subscribe({
    next: (res: any) => {
  this._spinner.hide();

  // 👉 CASE 1: E-Way Bill already saved
  if (res && res.number === 1) {
    this._global.alert({
      type: '',
      message: 'E-Way Bill already saved'
    });
    return;
  }

  // 👉 CASE 2: Fresh save success
  this._global.alert({
    type: '',
    message: 'E-Way Bill Saved Successfully'
  });

  this.onSaved.emit(res);
},

    error: (err) => {
      this._spinner.hide();

      const msg = (err.error && err.error.message)
        ? err.error.message
        : 'Something went wrong';

      this._global.alert({ type: 'alert', message: msg });
    }
  });
}
}
