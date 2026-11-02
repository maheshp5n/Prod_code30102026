import { Component, OnInit, ViewChild, Input, EventEmitter, Output } from '@angular/core';
import { LegalEntityResponse, ServiceCenterForList, LegaLEntityType, Login, ContactForList } from '../../services/model';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { FieldValidation } from '../../services/validation.services';
import { CirclesService } from '../../services/circles.services';

@Component({
  selector: 'app-createlegalentity',
  templateUrl: './createlegalentity.component.html',
  styleUrls: ['./createlegalentity.component.css']
})
export class CreatelegalentityComponent implements OnInit {
  public _isEditMode: boolean;
  istrue: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editEntityData(res: LegalEntityResponse) {
    console.log(res);
    this.req = res;
  }

  get editEntityData(): LegalEntityResponse {
    return this.req;
  }

  @Output()
  onEntityCreated: EventEmitter<LegalEntityResponse> = new EventEmitter<LegalEntityResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onAccountCreatedAndNew: EventEmitter<LegalEntityResponse> = new EventEmitter<LegalEntityResponse>();
  loginData: Login;
  message: string;
  req: LegalEntityResponse = new LegalEntityResponse();
  legalEntityManualTemp: number;
  leganEntityNameTemp: string;
  rowData = [

    { legalEntityType: 'factory' },
    { legalEntityType: 'service center' },
    { legalEntityType: 'repair center' }
  ];

  constructor(private spinner: NgxSpinnerService, private loginservice: Loginservices,
     private ligalentitservice: ligalEntityService, private _circlesService :CirclesService) { }

  ngOnInit() {
    this.getLoggedInUser()
    this.getLegalENtityForList()
    this.getUserForList();

    if (!this.isEditMode) {
      this.req = new LegalEntityResponse();

    }
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  serviceCenter: Array<LegaLEntityType>
  getLegalENtityForList() {
    this.ligalentitservice.getLegalEntityType().subscribe(data => {
      this.serviceCenter = data;

    })
  }

  postLigalEntity() {
    if (FieldValidation.isEmpty(this.req.legalEntityName)) {
      this.message = 'Please Fill Legal Entity Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }else if (FieldValidation.isValidName(this.req.legalEntityName) == false) {
      this.message = 'Please Fill Valid  Entity Name';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }else if(this.req.legalEntityName!==undefined && this.req.legalEntityName.length>200){
       this.message='Name Exceeds 200 Characters';
    }
     else if ((this.req.legalEntiytFixedLayou !==null && this.req.legalEntiytFixedLayou !== undefined && this.req.legalEntiytFixedLayou !== '') && (!/^[0-9]+$/.test(this.req.legalEntiytFixedLayou))) {
      this.message = 'Please fill Valid Fix Payout';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }else if((this.req.legalEntiytFixedLayou!==undefined) &&( this.req.legalEntiytFixedLayou.length>8)){
      this.message = 'Fix Payout Exceeds 10000000 Rupees';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
     else {
      this.istrue = true;

      if (this._isEditMode != true) {
        this.spinner.show()
        this.ligalentitservice.saveLigalEntity(this.req).subscribe(data => {
          this.legalEntityManualTemp = data.legalentityId;
          this.leganEntityNameTemp = data.legalEntityName;
          this.req = new LegalEntityResponse();
          this.onEntityCreated.emit(data);
          this.popupNgClass = 'alert alert-success center center-block';
          this.message = 'Created Successfully';
          this.spinner.hide();
          this.popupSuccessModel.show();



        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this.popupSuccessModel.show()
      
        });

      }
      else {
        this.spinner.show()
        this.ligalentitservice.updateEntity(this.req, this.req.legalentityId).subscribe(data => {
          this.legalEntityManualTemp = data.legalentityId;
          this.leganEntityNameTemp = data.legalEntityName;
          this.onEntityCreated.emit(data);
          this.popupNgClass = 'alert alert-success center center-block';
          this.message = 'Updated Successfully'
          this.spinner.hide();
          this.popupSuccessModel.show();

        },
          error => {
            this.spinner.hide()
            this.message = error.error.message;
            this.popupSuccessModel.show()
           
          }
        );
      }
    }

    if (!this.istrue) {
      this.popupSuccessModel.show();
    }


  }

  cancelData() {
    if (this.isEditMode != true) {
      this.req = new LegalEntityResponse();
      this.onCancle.emit('cancled');

    }

    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  userforlist: Array<ContactForList> = [];
  getUserForList() {
    this._circlesService.getContactListForCircle().subscribe(data => {
      this.userforlist = data;
    });
  }

}
