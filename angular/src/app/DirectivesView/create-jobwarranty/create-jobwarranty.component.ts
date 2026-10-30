import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AssetsResponse, AssetsRequest } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-create-jobwarranty',
  templateUrl: './create-jobwarranty.component.html',
  styleUrls: ['./create-jobwarranty.component.scss']
})
export class CreateJobwarrantyComponent implements OnInit {

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  assetResponse: AssetsResponse = new AssetsResponse();
  message: string;
  istrue: boolean;
  
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

  constructor(private spinner:NgxSpinnerService,private loginService: Loginservices) { }

  ngOnInit() {
    this.ownerName();
  }

  loginData;
  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
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
