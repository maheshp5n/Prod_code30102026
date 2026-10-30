import { BoardingExpenseService } from '../../../services/boardingExpense.service';
import { ActionHistoryObjectAndId, BoardingExpenseRequest, BoardingExpenseResponse } from '../../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../../services/login.services';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-boardingdetails',
  templateUrl: './boardingdetails.component.html',
  styleUrls: ['./boardingdetails.component.css']
})

export class BoardingdetailsComponent implements OnInit {
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditModeBoarding: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  boardingExpenseRequest: BoardingExpenseRequest = new BoardingExpenseRequest();
  boardingExpenseResponse: BoardingExpenseResponse = new BoardingExpenseResponse();
  isBoardingOpen: boolean;
  _cFlag: string;

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createBoardingModel', { static: false }) createBoardingModel: ModalDirective;

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private datePipe: DatePipe,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _boardingExpenseService: BoardingExpenseService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.boardingExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.boardingExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'BoardingExpense';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getBoardingExpenseDetails();
  }

  getBoardingExpenseDetails() {
    this.spinner.show();
    this._boardingExpenseService.getBoardingExpenseDetails(this.boardingExpenseResponse.id).subscribe((data) => {
      this.boardingExpenseResponse = data;
      data.toDate=this.datePipe.transform(data.toDate,'dd-MM-yyyy');
      data.toTime=data.toDate+' '+data.toTime;
      data.fromDate=this.datePipe.transform(data.fromDate,'dd-MM-yyyy');
      data.fromTime=data.fromDate+' '+data.fromTime;
      this.spinner.hide();
    });
  }

  cancelBoarding() {
    this.createBoardingModel.hide();
    this.isEditModeBoarding = false;
    this.boardingExpenseRequest = new BoardingExpenseRequest();
  }

  onClickEditBoarding(id) {
    this.boardingExpenseRequest = new BoardingExpenseRequest();
    this.spinner.show();
    this._boardingExpenseService.getBoardingExpenseDetails(id).subscribe((data) => {
      this.boardingExpenseRequest = data;
      this.boardingExpenseRequest.id = data.id;
      this.boardingExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.boardingExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      this.spinner.hide();
    });
    this.isEditModeBoarding = true;
    this.createBoardingModel.show();
  }

  onBoardingCreated($event) {
    this.boardingExpenseResponse = $event;
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }
}
