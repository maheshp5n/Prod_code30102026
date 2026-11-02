import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatePipe } from '@angular/common';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { POType, VendorPOcategory, VendorInvoiceRequest, WCCResponse, WccPendingInvoice, PODetails, vendorInvoiceResponse } from '../../services/model';
import { GlobalserviceService } from '../../globalservice.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { BtInvoiceService } from '../../battery/bat_services/bt-invoice.service';

@Component({
  selector: 'app-new-invoice',
  templateUrl: './new-invoice.component.html',
  styleUrls: ['./new-invoice.component.scss']
})
export class NewInvoiceComponent implements OnInit {
  // uploadForm:FormGroup;
  today = new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  venReq: VendorInvoiceRequest = new VendorInvoiceRequest();
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  VPOtype: Array<POType> = [];
  VPOcategory: Array<VendorPOcategory> = [];
  popupNgClass: string = 'alert alert-danger center center-block';
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 7;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editwccId(wccid: number) {
    this.spinner.show();
    // this.venReq.wccId = wccid;
    this.vendorinvoiceservice.getPendingInvoice().subscribe(data => {
      this.spinner.hide();
      this.wccpendingList = data;
      data.forEach(e => {
        if (wccid == e.wccId) {
          this.venReq.wccId = wccid;
          this.spinner.show();
          this.vendorinvoiceservice.getPOByTypeId(e.wccTypeId).subscribe(data => {
            this.spinner.hide();
            this.podetails = data;
            this.podetails.forEach(e => {
              this.venInvoicePOdate = e.poDate;
              this.venInvoicePoNumber = e.poNumber;
              this.venReq.vendorPoId = e.id;

            });
          }, error => {
            this.spinner.hide();
            this.message = error.error.message;
            this.popupSuccessModel.show();
          })
        }
      })
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });

  }
  get editwccId(): number {
    return this.venReq.wccId;
  }

  @Output()
  onInvoiceCreated: EventEmitter<vendorInvoiceResponse> = new EventEmitter<vendorInvoiceResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private http: HttpClient, private formBuilder: FormBuilder,
    private spinner: NgxSpinnerService,
    private datePipe: DatePipe,
    private _global: GlobalserviceService,
    private vendorinvoiceservice: VendorInvoiceService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  uploadForm2: FormGroup;


  ngOnInit() {
    this.getPOType();
    this.getPendingInvoice();
    this.getPoCat();
    this.uploadForm2 = this.formBuilder.group({
      profile: ['']
    });
  }
  getPOType() {
    this.spinner.show();
    this.vendorinvoiceservice.getPOTypes().subscribe(data => {
      this.spinner.hide();
      this.VPOtype = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  getPoCat() {
    this.spinner.show();
    this.vendorinvoiceservice.getPOCategory().subscribe(data => {
      this.spinner.hide();
      this.VPOcategory = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  venInvoicePoNumber: string;
  venInvoicePOdate: string;
  id: number = 1;
  podetails: Array<PODetails> = [];
  getPObyID($event: WccPendingInvoice) {
    console.log($event.wccTypeId)
    this.spinner.show();
    this.vendorinvoiceservice.getPOByTypeId($event.wccTypeId).subscribe(data => {
      this.spinner.hide();
      this.podetails = data;
      this.podetails.forEach(e => {
        this.venInvoicePOdate = e.poDate;
        this.venInvoicePoNumber = e.poNumber;
        this.venReq.vendorPoId = e.id;
        e.poNumber;
      })
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  wccpendingList: Array<WccPendingInvoice> = [];
  getPendingInvoice() {
    this.spinner.show();
    this.vendorinvoiceservice.getPendingInvoice().subscribe(data => {
      this.spinner.hide();
      this.wccpendingList = data;

    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }


  tempID: number;
  tempManualID: string;

  postInvoice() {
    if (this.venReq.vendorInvoiceNumber == null || this.venReq.vendorInvoiceNumber == '') {
      this.message = 'Please Fill Vendor invoice Number';
    }
    else if (this.venReq.vendorInvoiceDate == null || this.venReq.vendorInvoiceDate == undefined) {
      this.message = 'Please Fill Vendor Invoice Date';
    }

    else if (this.venReq.wccId == null || this.venReq.wccId == undefined) {
      this.message = 'Please fill Wcc Number';
    }


    else {
      this.istrue = true;
      if (this._isEditMode != true) {
        this.spinner.show();
        this.vendorinvoiceservice.saveInvoice(this.venReq).subscribe(data => {
          this.onInvoiceCreated.emit(data);

        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this.popupSuccessModel.show()
          setTimeout(() => {
            this.popupSuccessModel.hide();
          }, 2000
          );
        }

        );
      } else {
      }
    }
    this.popupSuccessModel.show();

  }




  cancelData() {
    if (this.isEditMode != true) {
      // this.res = new cityDetailsResponse();
    }
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');

    }
  }
  // uploadFilesLength: number;
  // fileList: string[] = [];
  file: File;
  filname: string;
  onFileSelect($event) {
    this.file = $event.target.files[0];
    this.filname = this.file.name;
    this.uploadForm2.get('profile').setValue(this.file);
    // this.uploadForm.get('filName').setValue(this.filname);

  }
  data: any
  onSubmit() {
    // let file = this.generate_dummy_file(this.file.size);
    // console.log(this.uploadForm.getRawValue());
    // this.data=JSON.stringify(this.uploadForm.getRawValue());
    if (this.venReq.vendorInvoiceNumber == undefined || this.venReq.vendorInvoiceNumber == null || this.venReq.vendorInvoiceNumber == '') {
      this._global.alert({ type: 'alert', message: 'Please provide Invoice Number' });
      return;
    }
    if (this.venReq.vendorInvoiceDate == undefined || this.venReq.vendorInvoiceDate == null || this.venReq.vendorInvoiceDate == '') {
      this._global.alert({ type: 'alert', message: 'Please provide Invoice date' });
      return;
    }
    if (this.file == undefined || this.file == null) {
      this._global.alert({ type: 'alert', message: 'Please provide invoce soft copy' });
      return;
    }

    const fd = new FormData;
    fd.append('file', this.file);
    let newDate = this.datePipe.transform(this.venReq.vendorInvoiceDate, 'yyyy-MM-dd HH:mm:ss');
    this.spinner.show();
    this.vendorinvoiceservice.vcuploadFiless(this.venReq.wccId, this.venReq.vendorInvoiceNumber, newDate, this.venReq.remarks, fd).subscribe(data => {
      this.popupNgClass = 'alert alert-success center center-block';

      this.onInvoiceCreated.emit(data);
      this.spinner.hide();

    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
}
