import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { Zonedetails } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { ZoneService } from '../../services/zone.service';

@Component({
  selector: 'app-zonecreate',
  templateUrl: './zonecreate.component.html',
  styleUrls: ['./zonecreate.component.css']
})
export class ZonecreateComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  zoneId;
  // flag: boolean;
  message: string;
  newzone: Zonedetails = new Zonedetails();
  public _isEditMode: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';

  constructor(private spinner: NgxSpinnerService, private _zonesService: ZoneService) {
  }

  ngOnInit() {
  }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editZoneData(Zone: Zonedetails) {
    this.newzone = Zone;
  }

  get editZoneData(): Zonedetails {
    return this.newzone;
  }

  @Output()
  onZoneCreated: EventEmitter<Zonedetails> = new EventEmitter<Zonedetails>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onZoneCreatedAndNew: EventEmitter<Zonedetails> = new EventEmitter<Zonedetails>();

  onClickEdit(i, y) {
    this.newzone = i;
    this.zoneId = y;
  }

  cancelData() {
    this.newzone = new Zonedetails();
    this.onCancle.emit('cancled');
  }

  createZone() {
    if (FieldValidation.isEmpty(this.newzone.zoneName)) {
      this.message = 'Please fill valid Zone Name';
    } else {
      if (this._isEditMode === false) {
        this.spinner.show();
        this._zonesService.postZone(this.newzone).subscribe(data => {
          this.message = 'Created Successfully';
          this.onZoneCreated.emit(data);
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.popupNgClass = 'alert alert-danger center center-block';
          this.message = err.status + ' # ' + err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
      }
      this.newzone = new Zonedetails();
    }
    // if (this.flag != true) {
    this.popupNgClass = 'alert alert-danger center center-block';
    this.popupSuccessModel.show();
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}
