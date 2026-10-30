import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { AssetsResponse, AssetsRequest, ServiceReportStatus } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ServiceReportService } from '../../services/servicereport.services';

@Component({
  selector: 'app-create-service-report',
  templateUrl: './create-service-report.component.html',
  styleUrls: ['./create-service-report.component.scss']
})
export class CreateServiceReportComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  assetResponse: AssetsResponse = new AssetsResponse();
  message: string;
  istrue: boolean;
  loginData;
  status: Array<ServiceReportStatus>=[];
  
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(assetResponse: AssetsResponse) {
    this.assetResponse = assetResponse;

  }

  get editAccountData(): AssetsResponse {
    return this.assetResponse;
  }

  @Output()
  onAssetCreated: EventEmitter<AssetsRequest> = new EventEmitter<AssetsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private spinner:NgxSpinnerService,private loginService: Loginservices,
    private serviceReportService:ServiceReportService) { }

  ngOnInit() {
    this.ownerName();
    this.getStatus();
  }
  
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }
  getStatus(){
    this.serviceReportService.getStatus().subscribe(data =>{
      this.status = data;
    })
  }
  
  postCreateAccount(){

  }

  cancelData() {
    //this.assetRequest = new AssetsRequest();
    this.onCancle.emit('cancled');
  }
  
  openPopUp() {
    if (this.istrue == true) {
      this.message = "Submitted Successfully";
      setTimeout(() => {
        this.spinner.hide();
        this.popupSuccessModel.show();

        setTimeout(() => {
          this.cancelData()
          this.popupSuccessModel.hide()
        }, 2000
        );


      }, 1000
      );



    } else {
      this.message = "Submittion Failed";
      this.spinner.show();
      setTimeout(() => {
        this.spinner.hide();
        this.popupSuccessModel.show();

        setTimeout(() => {
          this.popupSuccessModel.hide()
        }, 2000
        );


      }, 1000
      );

    }

  }
  popcheck() {
    setTimeout(() => {
      this.spinner.hide();
      this.popupSuccessModel.show();

      setTimeout(() => {
        this.popupSuccessModel.hide()
      }, 2000
      );


    }, 1000
    );

  }
  
}
