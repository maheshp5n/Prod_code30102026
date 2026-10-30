import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { caseService } from '../../../services/caseService.service';
import { Loginservices } from '../../../services/login.services';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BTCaseForList } from '../../bt_model';

@Component({
  selector: 'app-bat-fsr-service',
  templateUrl: './bat-fsr-service.component.html',
  styleUrls: ['./bat-fsr-service.component.css']
})
export class BatFsrServiceComponent implements OnInit {

  caseList: Array<BTCaseForList> = [];
  isFsrOpen: boolean;
  isEditMode: boolean = false;
  heading: string="Field Service Report";

  @ViewChild('createfsrserviceModel', { static: false })
  createfsrserviceModel: ModalDirective;
  constructor( private caseService: BtCaseserviceService, private loginService: Loginservices,) { }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit(): void {
    // this.getCaseList();

  }

  getCaseList() {
    this.caseService.getCaseLists().subscribe(data => {
      this.caseList = data;
    }, err => {

    });
  }

  fsr()
  {

    this.createfsrserviceModel.show();
  }

  cancelData() {
    this.createfsrserviceModel.hide();
    // this.isEditMode = false;
    // this.heading = this.CREATE_expensesmanagement_HEADING;
    // this.newexpensesmanagement = new ExpenseManagementRequest();
  }
}
