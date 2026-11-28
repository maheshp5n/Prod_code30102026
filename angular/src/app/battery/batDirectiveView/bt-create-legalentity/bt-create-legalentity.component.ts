import { Component, OnInit, ViewChild, Input, EventEmitter, Output } from '@angular/core';
import { ContactForList, LegalEntityResponse, LegaLEntityType, Login, UserForList } from '../../../services/model';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { FieldValidation } from '../../../services/validation.services';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { CirclesService } from '../../../services/circles.services';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bt-create-legalentity',
  templateUrl: './bt-create-legalentity.component.html',
  styleUrls: ['./bt-create-legalentity.component.css']
})
export class BtCreateLegalentityComponent implements OnInit {
  public _isEditMode: boolean;
  istrue: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  userforlist: Array<UserForList> = [];
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

  constructor(private spinner: NgxSpinnerService, private loginservice: Loginservices, private ligalentitservice: ligalEntityService,
    private _contactService: BtcontactsService, private _btLegalEntityService: BtlegalentityService,
    private _userRegService: BatuserregistrationService, private _global: GlobalserviceService) { }

  ngOnInit() {
    this.getLoggedInUser()
    this.getLegalENtityForList()
    this.getUserForList()


    if (!this.isEditMode) {
      this.req = new LegalEntityResponse();

    }
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  serviceCenter: Array<LegaLEntityType>
  getLegalENtityForList() {
    this._btLegalEntityService.getLegalEntityType().subscribe(data => {
      this.serviceCenter = data;

    })
  }
  getUserForList() {
    this._userRegService.getUserForList2().subscribe(data => {
      this.userforlist = data;
    })
  }

  postLigalEntity() {
    if (FieldValidation.isEmpty(this.req.legalEntityName)) {
      this._global.alert({ type: 'alert', message: 'Please Fill Legal Entity Name' });
    } else if (FieldValidation.isValidName(this.req.legalEntityName) == false) {
      this._global.alert({ type: 'alert', message: 'Please Fill Valid  Entity Name' });
    } else if (this.req.legalEntityName !== undefined && this.req.legalEntityName.length > 200) {
      this._global.alert({ type: 'alert', message: 'Name Exceeds 200 Characters' });
    }
    else if (this.req.legalEntityTypeId == 0 || this.req.legalEntityTypeId == null || this.req.legalEntityTypeId == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Type' });
    }
    else if ((this.req.legalEntiytFixedLayou !== null && this.req.legalEntiytFixedLayou !== undefined && this.req.legalEntiytFixedLayou !== '') && (!/^[0-9]+$/.test(this.req.legalEntiytFixedLayou))) {
      this._global.alert({ type: 'alert', message: 'Please Fill Valid Fix Payout' });
    } else if ((this.req.legalEntiytFixedLayou !== undefined) && (this.req.legalEntiytFixedLayou.length > 8)) {
      this._global.alert({ type: 'alert', message: 'Fix Payout Exceeds 10000000 Rupees' });
    }
    else if (this.req.ownerAccountId == null || this.req.ownerAccountId == "") {
      this._global.alert({ type: 'alert', message: 'Please Fill Circle Manager' });
    }

    else {
      this.istrue = true;

      if (this._isEditMode != true) {
        this.spinner.show()
        this._btLegalEntityService.saveLigalEntity(this.req).subscribe(data => {
          this.legalEntityManualTemp = data.legalentityId;
          this.leganEntityNameTemp = data.legalEntityName;
          this.req = new LegalEntityResponse();
          this.onEntityCreated.emit(data);
          this._global.alert({ type: '', message: 'Created Successfully' });
                
          this.onCancle.emit('cancled');
          this.spinner.hide();
        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this._global.alert({ type: 'alert', message: this.message });

        });

      }
      else {
        this.spinner.show()
        this._btLegalEntityService.updateEntity(this.req, this.req.legalentityId).subscribe(data => {
          this.legalEntityManualTemp = data.legalentityId;
          this.leganEntityNameTemp = data.legalEntityName;
          this.onEntityCreated.emit(data);
                
          this.onCancle.emit('cancled');
          this.spinner.hide();
          this._global.alert({ type: '', message: 'Updated Successfully' });

        },
          error => {
            this.spinner.hide()
            this.message = error.error.message;
            this._global.alert({ type: 'alert', message: this.message });

          }
        );
      }
    }

    if (!this.istrue) {
      // this.popupSuccessModel.show();
    }


  }

  cancelData() {
    if (this.isEditMode != true) {
      this.req = new LegalEntityResponse();
      this.onCancle.emit('cancled');

    }

  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }


}
