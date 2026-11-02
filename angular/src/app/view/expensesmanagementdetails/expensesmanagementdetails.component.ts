import { OtherChargesService } from './../../services/otherCharges.service';
import { LocalConveyanceExpenseService } from './../../services/localConveyanceExpense.service';
import { TourExpenseService } from './../../services/tourExpense.service';
import {
  TourExpenseRequest, TourExpenseResponse, LodgingExpenseResponse, LodgingExpenseRequest,
  LocalConveyanceExpenseRequest, LocalConveyanceExpenseResponse, OtherChargesExpenseRequest,
  OtherChargesExpenseResponse,
  BoardingLoadingDetailsResponse,
  GLCodeResponse,
  ExpenseAccountsOnlyRequest,
  ActionHistoryObjectAndId
} from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  ExpenseManagementRequest,
  ExpenseManagementResponse,
  BoardingExpenseRequest,
  BoardingExpenseResponse,
  ToBeApprovedHistoryResponse,
  FileUploadResponse
} from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ligalEntityService } from '../../services/ligalentity.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { LodgingExpenseService } from '../../services/LodgingExpenseService';
import { BoardingExpenseService } from '../../services/boardingExpense.service';
import { Loginservices } from '../../services/login.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { FileUploader } from 'ng2-file-upload';
import * as fileSaver from 'file-saver'; // npm i --save file-saver
import { PdfServiceService } from '../../services/pdf-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { DatePipe } from '@angular/common';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { FileDeleteService } from '../../services/filedeletService.services';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-expensesmanagementdetails',
  templateUrl: './expensesmanagementdetails.component.html',
  styleUrls: ['./expensesmanagementdetails.component.css']
})

export class ExpensesmanagementdetailsComponent implements OnInit {

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

  constructor(private loginservice:Loginservices,private _expenseManagementService: ExpenseManagementService, private tourExpenseService: TourExpenseService,
    private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private ligalentitservice: ligalEntityService,
    private lodgingService: LodgingExpenseService, private boardingService: BoardingExpenseService,
    private localConveyanceService: LocalConveyanceExpenseService, private otherChargesService: OtherChargesService,
    private loginService: Loginservices, protected _sanitizer: DomSanitizer, private _approvalHistoryService: ApprovalHistoryService,
    private formBuilder: FormBuilder, private _http: HttpClient,
    private deleteservice:FileDeleteService,private _global:GlobalserviceService,
    private datePipe: DatePipe,private boadingservice:BoardingLoadingService,
    private _pdfService: PdfServiceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.expensesmanagement.expenseManagementId = pram['id'];
      this.getexpensesmanagementById();
      this.actionHistoryObjectAndId.objectId = '' + this.expensesmanagement.expenseManagementId;
      this.actionHistoryObjectAndId.objectName = 'ExpenseManagement';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  _cFlag: string;
  oneAtATime: boolean = true;
  fileToUpload: File = null;
  popupNgClass: string = 'alert alert-danger center center-block';

  isExpensesmanagementOpen: boolean;
  isTourOpen: boolean;
  isLodgingOpen: boolean;
  isBoardingOpen: boolean;
  isLocalConveyanceOpen: boolean;
  isOtherChargesOpen: boolean;

  isEditMode: boolean = false;
  message: string;
  heading: string;
  legalEntityList;
  expensesmanagementListById;
  ngClassDetails: string = 'activ';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: string = '';

  CREATE_expensesmanagement_HEADING: string = 'New Expenses';
  EDIT_expensesmanagement_HEADING: string = 'Edit Expenses';
  newexpensesmanagement: ExpenseManagementRequest = new ExpenseManagementRequest();
  expensesmanagement: ExpenseManagementResponse = new ExpenseManagementResponse();
  expensesmanagementList: Array<ExpenseManagementResponse> = [];

  CREATE_TourExpenses_HEADING: string = 'New Tour Expense';
  EDIT_TourExpenses_HEADING: string = 'Edit Tour Expense';
  headingTourExpenses: string;
  isEditModeTourExpense: boolean = false;
  firstTour: boolean;
  lastTour: boolean;
  sizeTour: number;
  serialNumberTour: number;
  public pageTour: number = 0;
  totalElementsTour: number;
  tourExpenseRequest: TourExpenseRequest = new TourExpenseRequest();
  tourExpenseResponse: TourExpenseResponse = new TourExpenseResponse();
  tourListByExpenseId: Array<TourExpenseResponse> = [];

  CREATE_Lodging_HEADING: string = 'New Lodging';
  EDIT_Lodging_HEADING: string = 'Edit Lodging';
  headingLodging: string;
  isEditModeLodging: boolean = false;
  firstLodging: boolean;
  lastLodging: boolean;
  sizeLodging: number;
  serialNumberLodging: number;
  public pageLodging: number = 0;
  totalElementsLodging: number;
  lodgingExpenseRequest: LodgingExpenseRequest = new LodgingExpenseRequest();
  lodgingExpenseResponse: LodgingExpenseResponse = new LodgingExpenseResponse();
  lodgingListByExpenseId: Array<LodgingExpenseResponse> = [];

  CREATE_Boarding_HEADING: string = 'New Boarding';
  EDIT_Boarding_HEADING: string = 'Edit Boarding';
  headingBoarding: string;
  isEditModeBoarding: boolean = false;
  firstBoarding: boolean;
  lastBoarding: boolean;
  sizeBoarding: number;
  serialNumberBoarding: number;
  public pageBoarding: number = 0;
  totalElementsBoarding: number;
  boardingExpenseRequest: BoardingExpenseRequest = new BoardingExpenseRequest();
  boardingExpenseResponse: BoardingExpenseResponse = new BoardingExpenseResponse();
  boardingListByExpenseId: Array<BoardingExpenseResponse> = [];

  CREATE_LocalConveyance_HEADING: string = 'New Local Conveyance';
  EDIT_LocalConveyance_HEADING: string = 'Edit Local Conveyance';
  headingLocalConveyance: string;
  isEditModeLocalConveyance: boolean = false;
  firstLocalConveyance: boolean;
  lastLocalConveyance: boolean;
  sizeLocalConveyance: number;
  serialNumberLocalConveyance: number;
  public pageLocalConveyance: number = 0;
  totalElementsLocalConveyance: number;
  localConveyanceExpenseRequest: LocalConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
  localConveyanceExpenseResponse: LocalConveyanceExpenseResponse = new LocalConveyanceExpenseResponse();
  localConveyanceListByExpenseId: Array<LocalConveyanceExpenseResponse> = [];
  response:BoardingLoadingDetailsResponse=new BoardingLoadingDetailsResponse();
  CREATE_OtherCharges_HEADING: string = 'New Other Charges';
  EDIT_OtherCharges_HEADING: string = 'Edit Other Charges';
  headingOtherCharges: string;
  isEditModeOtherCharges: boolean = false;
  firstOtherCharges: boolean;
  lastOtherCharges: boolean;
  sizeOtherCharges: number;
  serialNumberOtherCharges: number;
  public pageOtherCharges: number = 0;
  totalElementsOtherCharges: number;
  otherChargesExpenseRequest: OtherChargesExpenseRequest = new OtherChargesExpenseRequest();
  otherChargesExpenseResponse: OtherChargesExpenseResponse = new OtherChargesExpenseResponse();
  otherChargesListByExpenseId: Array<OtherChargesExpenseResponse> = [];
  glcodeList:Array<GLCodeResponse>=[];
  
  accountOnlyReq:ExpenseAccountsOnlyRequest=new ExpenseAccountsOnlyRequest();

  i: number;
  objectName: string = 'ExpenseManagement';
  approvalHistoryList: Array<ToBeApprovedHistoryResponse>;
  isExpensesmanagementRelatedOpen: boolean;

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
  departmentOnlyInfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  daysInfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  amountInfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  @ViewChild('createTourExpenseModel') createTourExpenseModel: ModalDirective;
  @ViewChild('createLodgingModel') createLodgingModel: ModalDirective;
  @ViewChild('createBoardingModel') createBoardingModel: ModalDirective;
  @ViewChild('createLocalConveyanceModel') createLocalConveyanceModel: ModalDirective;
  @ViewChild('createOtherChargesModel') createOtherChargesModel: ModalDirective;
  @ViewChild('createexpensesmanagementModel') createexpensesmanagementModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createexpensesMangementRelatedModel') createexpensesMangementRelatedModel: ModalDirective;
  @ViewChild('searchResult') searchResult: ModalDirective;
  @ViewChild('showPreviewModel') showPreviewModel: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup: ModalDirective;
  @ViewChild('borloddtailModel') borloddtailModel: ModalDirective;
  @ViewChild('onlyAccountModel') onlyAccountModel : ModalDirective;
  @ViewChild('deleteExpModel') deleteExpModel:ModalDirective;
  @ViewChild('submitapprovalmodel') submitapprovalmodel:ModalDirective;
  uploadForm: FormGroup;

  public uploader: FileUploader = new FileUploader({
    isHTML5: true
  });

  fileList: string[] = [];


  fileUploadList: Array<FileUploadResponse> = [];

  uploadFilesLength: number;

  formData = new FormData();

  imageUrl: any;
  fileName: string;

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: []
    });
  }

  tourShow() { }
  tourHide() { }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

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

  changedepartmentOnlyInfoDisplay() {
    this.departmentOnlyInfoDisplay.open = !this.departmentOnlyInfoDisplay.open;
    if (this.departmentOnlyInfoDisplay.open) {
      this.departmentOnlyInfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.departmentOnlyInfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changedaysInfoDisplay() {
    this.daysInfoDisplay.open = !this.daysInfoDisplay.open;
    if (this.daysInfoDisplay.open) {
      this.daysInfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.daysInfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeamountInfoDisplay() {
    this.amountInfoDisplay.open = !this.amountInfoDisplay.open;
    if (this.amountInfoDisplay.open) {
      this.amountInfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.amountInfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'activ';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getexpensesmanagementById();
    } else {
      this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
      this.getApprovalHistoryList();
      // if (this.tourListByExpenseId.length === 0) {
        this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
     
      if (this.lodgingListByExpenseId.length === 0) {
        this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
      }
      if (this.boardingListByExpenseId.length === 0) {
        this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
      }
      if (this.localConveyanceListByExpenseId.length === 0) {
        this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
      }
      if (this.otherChargesListByExpenseId.length === 0) {
        this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
      }
    this.getGLCodes();
      this.ngClassDetails = '';
      this.ngClassReleated = 'activ';
      this.vDetails = false;
    }
    return false;
  }

  // Tour Starts
  setPagePreviousTour(event: any) {
    event.preventDefault();
    this.pageTour = this.pageTour - 1;
    this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageTour(event: any) {
    event.preventDefault();
    this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageNextTour(event: any) {
    event.preventDefault();
    this.pageTour = this.pageTour + 1;
    this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  getLegalEntityId() {
    this.ligalentitservice.getLegalEntityId().subscribe(data => {
      this.legalEntityList = data;
    });
  }

  showCreateexpensesmanagementView() {
    this.isEditMode = false;
    this.heading = this.CREATE_expensesmanagement_HEADING;
    this.createexpensesmanagementModel.show();
  }

  cancelData() {
    this.createexpensesmanagementModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_expensesmanagement_HEADING;
    this.newexpensesmanagement = new ExpenseManagementRequest();
  }

  onexpensesmanagementCreated($event: ExpenseManagementResponse) {
    this.spinner.show();
    this.expensesmanagement = $event;
    this.spinner.hide();
    this.getexpensesmanagementById();
    this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    this.getApprovalHistoryList();
    if (this.tourListByExpenseId.length === 0) {
      this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
    }
    if (this.lodgingListByExpenseId.length === 0) {
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
    }
    if (this.boardingListByExpenseId.length === 0) {
      this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
    }
    if (this.localConveyanceListByExpenseId.length === 0) {
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
    }
    if (this.otherChargesListByExpenseId.length === 0) {
      this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
    }
  this.getGLCodes();
  }

  cc(value) {
  }

  onExpensesAdded() {
    this.spinner.show();
    this.getexpensesmanagementById();
    this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
    this.spinner.hide();
  }
grade:string;
  getexpensesmanagementById() {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.expenseManagementId).subscribe((data) => {
      this.expensesmanagement = data;
     
      this.grade=data.gradeName;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
  }

  onClickEdit() {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.expenseManagementId).subscribe((data) => {
      this.newexpensesmanagement = data;
      this.isEditMode = true;
      this.heading = this.EDIT_expensesmanagement_HEADING;
      this.createexpensesmanagementModel.show();
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
  }
touramountToDisbursed:number;
lodamountToDisbursed:number;
  getTourExpenseListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.tourExpenseService.getTourExpenseListByExpenseId(expensesmanagementId, this.pageTour).subscribe(
      data => {
     data.content.forEach(el => {
      el.arrivalDate = this.datePipe.transform(el.arrivalDate, 'dd-MM-yyyy');
      // el.arrivalTime = el.arrivalTime
      el.arrivalDateTime=el.arrivalDate+' '+el.arrivalTime;
      el.departureDate=this.datePipe.transform(el.departureDate,'dd-MM-yyyy');
      el.departuredateTime=el.departureDate+' '+el.departureTime;
      // this.touramountToDisbursed=el.amountToDisbursed;
     });
      
        this.tourListByExpenseId = data.content;
        this.firstTour = data.first;
        this.lastTour = data.last;
        this.sizeTour = data.size;
        this.totalElementsTour = data.totalElements;
        this.serialNumberTour = ((this.pageTour) * this.sizeTour);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  showTourExpenses() {
    this.isEditModeTourExpense = false;
    this.headingTourExpenses = this.CREATE_TourExpenses_HEADING;
    this.tourExpenseRequest = new TourExpenseRequest();
    this.tourExpenseRequest.expenseManagementId = this.expensesmanagement.expenseManagementId;
    this.tourExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    // this.createTourExpenseModel.show();
  }

  onClickEditTourExpense(expenseManagementId,amountToDisbursed) {
    this.tourExpenseRequest = new TourExpenseRequest();
    this.touramountToDisbursed=amountToDisbursed;
    this.isEditModeTourExpense=true;
    this.spinner.show();
    this.tourExpenseService.getTourExpenseDetails(expenseManagementId).subscribe((data) => {
      this.tourExpenseRequest.id = data.id;
      this.tourExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.tourExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      this.tourExpenseRequest.modeOfTravelId = data.modeOfTravelId;
      this.tourExpenseRequest.ownConveyanceId = data.ownConveyanceId;
      this.tourExpenseRequest.departureDate = data.departureDate;
      this.tourExpenseRequest.departureTime = data.departureTime;
      this.tourExpenseRequest.arrivalDate = data.arrivalDate;
      this.tourExpenseRequest.arrivalTime = data.arrivalTime;
      this.tourExpenseRequest.fromPlace = data.fromPlace;
      this.tourExpenseRequest.toPlace = data.toPlace;
      this.tourExpenseRequest.companyPaid = data.companyPaid;
      this.tourExpenseRequest.fare = data.fare;
      this.tourExpenseRequest.approvedAmount = data.approvedAmount;
      this.tourExpenseRequest.distance = data.distance;
      this.tourExpenseRequest.remark = data.remark;
      this.tourExpenseRequest.manualId = data.manualId;
      // this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'dd-MM-yyyy');
      // this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'dd-MM-yyyy');

      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeTourExpense = true;
    this.headingTourExpenses = this.EDIT_TourExpenses_HEADING;
    this.createTourExpenseModel.show();
  }

  onTourExpensesCreated($event) {
    this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
    if (!this.isEditModeTourExpense) {
      this.tourListByExpenseId.unshift($event);
      this.totalElementsTour = this.totalElementsTour + 1;
    } else {
      let index = 0;
      this.tourListByExpenseId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.tourListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
     this.getGLCodes();
  }

  // Tour Ends

  // Lodging starts

  setPagePreviousLodging(event: any) {
    event.preventDefault();
    this.pageLodging = this.pageLodging - 1;
    this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageLodging(event: any) {
    event.preventDefault();
    this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageNextLodging(event: any) {
    event.preventDefault();
    this.pageLodging = this.pageLodging + 1;
    this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  getLodgingListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.lodgingService.getLodgingExpenseListByExpenseId(expensesmanagementId, this.pageLodging).subscribe(
      data => {
        this.lodgingListByExpenseId = data.content;
        data.content.forEach(el => {
          el.fromDate = this.datePipe.transform(el.fromDate, 'dd-MM-yyyy');
          // el.arrivalTime = el.arrivalTime
          el.arrivalDateTime=el.fromDate+' '+el.fromTime;
          el.toDate=this.datePipe.transform(el.toDate,'dd-MM-yyyy');
          el.DepartureDateTime=el.toDate+' '+el.toTime;
          // this.xpenseamountToDisbursed=el.amountToDisbursed;
         });
        this.firstLodging = data.first;
        this.lastLodging = data.last;
        this.sizeLodging = data.size;
        this.totalElementsLodging = data.totalElements;
        this.serialNumberLodging = ((this.pageLodging) * this.sizeLodging);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  showLodging() {
    this.isEditModeLodging = false;
    this.headingLodging = this.CREATE_Lodging_HEADING;
    this.lodgingExpenseRequest = new LodgingExpenseRequest();
    this.lodgingExpenseRequest.expenseManagementId = this.expensesmanagement.expenseManagementId;
    this.lodgingExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    // this.createLodgingModel.show();
  }

  onClickEditLodging(id,amountToDisbursed) {
    
    this.lodgingExpenseRequest = new LodgingExpenseRequest();
    this.lodamountToDisbursed=amountToDisbursed;
    this.spinner.show();
    this.isEditModeLodging=true;
    this.lodgingService.getLodgingExpenseDetails(id).subscribe((data) => {
      this.lodgingExpenseRequest = data;
      this.lodgingExpenseRequest.id = data.id;
      this.lodgingExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.lodgingExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      // this.lodgingExpenseRequest.fromDate = this.datePipe.transform(this.lodgingExpenseRequest.fromDate, 'dd-MM-yyyy');
      // this.lodgingExpenseRequest.toDate = this.datePipe.transform(this.lodgingExpenseRequest.toDate, 'dd-MM-yyyy');
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeLodging = true;
    this.headingLodging = this.EDIT_Lodging_HEADING;
    this.createLodgingModel.show();
    // this.spinner.hide();
  }

  onLodgingCreated($event) {
    if (!this.isEditModeLodging) {
      this.lodgingListByExpenseId.unshift($event);
      this.totalElementsLodging = this.totalElementsLodging + 1;
    } else {
      let index = 0;
      this.lodgingListByExpenseId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.lodgingListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
     this.getGLCodes();
  }
  // Lodging Ends

  // Boarding starts

  setPagePreviousBoarding(event: any) {
    event.preventDefault();
    this.pageBoarding = this.pageBoarding - 1;
    this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageBoarding(event: any) {
    event.preventDefault();
    this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageNextBoarding(event: any) {
    event.preventDefault();
    this.pageBoarding = this.pageBoarding + 1;
    this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  getBoardingListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.boardingService.getBoardingExpenseListByExpenseId(expensesmanagementId, this.pageBoarding).subscribe(
      data => {
        this.boardingListByExpenseId = data.content;
        data.content.forEach(el => {
          el.fromDate = this.datePipe.transform(el.fromDate, 'dd-MM-yyyy');
          // el.arrivalTime = el.arrivalTime
          el.arrivalDateTime=el.fromDate+' '+el.fromTime;
          el.toDate=this.datePipe.transform(el.toDate,'dd-MM-yyyy');
          el.DepartureDateTime=el.toDate+' '+el.toTime;
         });
        this.firstBoarding = data.first;
        this.lastBoarding = data.last;
        this.sizeBoarding = data.size;
        this.totalElementsBoarding = data.totalElements;
        this.serialNumberBoarding = ((this.pageBoarding) * this.sizeBoarding);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  showBoarding() {
    this.isEditModeBoarding = false;
    this.headingBoarding = this.CREATE_Boarding_HEADING;
    this.boardingExpenseRequest = new BoardingExpenseRequest();
    this.boardingExpenseRequest.expenseManagementId = this.expensesmanagement.expenseManagementId;
    this.boardingExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    // this.createBoardingModel.show();
  }
  bodamountToDisbursed:number;
  onClickEditBoarding(id,bodamountToDisbursed) {
    this.boardingExpenseRequest = new BoardingExpenseRequest();
    this.bodamountToDisbursed=bodamountToDisbursed;
    this.isEditModeBoarding=true;
    this.spinner.show();
    this.boardingService.getBoardingExpenseDetails(id).subscribe((data) => {
      this.boardingExpenseRequest = data;
      this.boardingExpenseRequest.id = data.id;
      this.boardingExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.boardingExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeBoarding = true;
    this.headingBoarding = this.EDIT_Boarding_HEADING;
    this.createBoardingModel.show();
  }

  onBoardingCreated($event) {
    this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
    if (!this.isEditModeBoarding) {
      this.boardingListByExpenseId.unshift($event);
      this.totalElementsBoarding = this.totalElementsBoarding + 1;
    } else {
      let index = 0;
      this.boardingListByExpenseId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.boardingListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
     this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
     this.getGLCodes();
  }

  // Boarding Ends

  // LocalConveyance starts

  setPagePreviousLocalConveyance(event: any) {
    event.preventDefault();
    this.pageLocalConveyance = this.pageLocalConveyance - 1;
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageLocalConveyance(event: any) {
    event.preventDefault();
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageNextLocalConveyance(event: any) {
    event.preventDefault();
    this.pageLocalConveyance = this.pageLocalConveyance + 1;
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  getLocalConveyanceListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.localConveyanceService.getLocalConveyanceExpenseListByExpenseId(expensesmanagementId, this.pageLocalConveyance).subscribe(
      data => {
        this.localConveyanceListByExpenseId = data.content;
        this.firstLocalConveyance = data.first;
        this.lastLocalConveyance = data.last;
        this.sizeLocalConveyance = data.size;
        this.totalElementsLocalConveyance = data.totalElements;
        this.serialNumberLocalConveyance = ((this.pageLocalConveyance) * this.sizeLocalConveyance);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  showLocalConveyance() {
    this.isEditModeLocalConveyance = false;
    this.headingLocalConveyance = this.CREATE_LocalConveyance_HEADING;
    this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
    this.localConveyanceExpenseRequest.expenseManagementId = this.expensesmanagement.expenseManagementId;
    this.localConveyanceExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    // this.createLocalConveyanceModel.show();
  }
  localamountToDisbursed:number;
  onClickEditLocalConveyance(id,localamountToDisbursed) {
    this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
    this.localamountToDisbursed=localamountToDisbursed;
    this.spinner.show();
    this.localConveyanceService.getLocalConveyanceExpenseDetails(id).subscribe((data) => {
      this.localConveyanceExpenseRequest = data;
      this.localConveyanceExpenseRequest.id = data.id;
      this.localConveyanceExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.localConveyanceExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      this.localConveyanceExpenseRequest.ownConveyanceId = data.ownConveyanceId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeLocalConveyance = true;
    this.headingLocalConveyance = this.EDIT_LocalConveyance_HEADING;
    this.createLocalConveyanceModel.show();
  }

  onLocalConveyanceCreated($event) {

    if (!this.isEditModeLocalConveyance) {
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
  
      this.localConveyanceListByExpenseId.unshift($event);
      this.totalElementsLocalConveyance = this.totalElementsLocalConveyance + 1;
    } else {
      let index = 0;
      this.localConveyanceListByExpenseId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.localConveyanceListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
     this.getGLCodes();
  }

  // LocalConveyance Ends

  // OtherCharges starts

  setPagePreviousOtherCharges(event: any) {
    event.preventDefault();
    this.pageOtherCharges = this.pageOtherCharges - 1;
    this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageOtherCharges(event: any) {
    event.preventDefault();
    this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  setPageNextOtherCharges(event: any) {
    event.preventDefault();
    this.pageOtherCharges = this.pageOtherCharges + 1;
    this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
  }

  getOtherChargesListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.otherChargesService.getOtherChargesListByExpenseId(expensesmanagementId, this.pageOtherCharges).subscribe(
      data => {
        this.otherChargesListByExpenseId = data.content;
        this.firstOtherCharges = data.first;
        this.lastOtherCharges = data.last;
        this.sizeOtherCharges = data.size;
        this.totalElementsOtherCharges = data.totalElements;
        this.serialNumberOtherCharges = ((this.pageOtherCharges) * this.sizeOtherCharges);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this.popupSuccessModel.show();
      });
  }

  showOtherCharges(id) {
    this.isEditModeOtherCharges = false;
    this.headingOtherCharges = this.CREATE_OtherCharges_HEADING;
    // this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.otherChargesExpenseRequest.expenseManagementId = id;
    // this.otherChargesExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    this.otherChargesExpenseRequest.businessDomainId = this.expensesmanagement.businessDomainId;
    // this.createOtherChargesModel.show();
  }
  loginData:any;
  otherchangeamountToDisbursed:number;
  onClickEditOtherCharges(id,otherchangeamountToDisbursed) {
    this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.otherchangeamountToDisbursed=otherchangeamountToDisbursed;
    this.spinner.show();
    this.otherChargesService.getOtherChargesDetails(id).subscribe((data) => {
      this.otherChargesExpenseRequest = data;
      this.otherChargesExpenseRequest.id = data.id;
      this.loginData = this.loginservice.getLoggedInUser();
      this.otherChargesExpenseRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
      
      this.otherChargesExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.otherChargesExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeOtherCharges = true;
    this.headingOtherCharges = this.EDIT_OtherCharges_HEADING;
    this.createOtherChargesModel.show();
    // this.spinner.hide();
  }

  onOtherChargesCreated($event) {
    this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
    if (!this.isEditModeOtherCharges) {
      this.otherChargesListByExpenseId.unshift($event);
      this.totalElementsOtherCharges = this.totalElementsOtherCharges + 1;
    } else {
      let index = 0;
      this.otherChargesListByExpenseId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.otherChargesListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
      this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
      this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
      this.getGLCodes();
  }

  // OtherCharges Ends




  popupClose() {
    this.popupSuccessModel.hide();
    // if (this.popupNgClass == 'alert alert-success center center-block') {
    //   this.onCancle.emit('cancled');
    // }
  }
 remarks:string;
  onClickSSumitApproval() {
    this.spinner.show();
    this._expenseManagementService.postsubmitforapproval(this.expensesmanagement.expenseManagementId,
      this.expensesmanagement.businessDomainId,this.remarks).subscribe(data => {
        this.spinner.hide();
        this.popupNgClass = 'alert alert-success center center-block';
        this.message = 'Submitted Successfully';
        this._global.alert({type:'',message:this.message});
        this.submitapprovalmodel.hide();
        this.getexpensesmanagementById();
      }, error => {
        this.popupNgClass= 'alert alert-danger center center-block';
        this.spinner.hide();
        this.message = error.status + ' # ' + error.error.message;
        // this.popupSuccessModel.show();
        this._global.alert({type:'alert',message:this.message});
      });
  }

  addExpense() {
    this.isEditModeTourExpense = false;
    this.isEditModeLodging = false;
    this.isEditModeBoarding = false;
    this.isEditModeLocalConveyance = false;
    this.isEditModeOtherCharges = false;
    this.createexpensesMangementRelatedModel.show();
  }

  onexpensesmanagementRelatedCreated($event) {

  }

  cancelTourExpense() {
    // this.createexpensesmanagementModel.hide();
    this.createTourExpenseModel.hide();
    // this.isEditModeTourExpense = false;
    // this.headingTourExpenses = this.CREATE_TourExpenses_HEADING;
    // this.tourExpenseRequest = new TourExpenseRequest();
    this.cancelExpensesmanagementRelated();
  }

  cancelLodging() {
    // this.createexpensesmanagementModel.hide();
    this.createLodgingModel.hide();
    // this.isEditModeLodging = false;
    // this.headingLodging = this.CREATE_Lodging_HEADING;
    // this.lodgingExpenseRequest = new LodgingExpenseRequest();
    this.cancelExpensesmanagementRelated();
  }

  cancelBoarding() {
    // this.createexpensesmanagementModel.hide();
    this.createBoardingModel.hide();
    // this.isEditModeBoarding = false;
    // this.headingBoarding = this.CREATE_Boarding_HEADING;
    // this.boardingExpenseRequest = new BoardingExpenseRequest();
    this.cancelExpensesmanagementRelated();
  }

  cancelLocalConveyance() {
    // this.createexpensesmanagementModel.hide();
    this.createLocalConveyanceModel.hide();
    // this.isEditModeLocalConveyance = false;
    // this.headingLocalConveyance = this.CREATE_LocalConveyance_HEADING;
    // this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
    this.cancelExpensesmanagementRelated();
  }

  cancelOtherCharges() {
    this.spinner.hide();
    // this.createexpensesmanagementModel.hide();
    this.createOtherChargesModel.hide();
    this.spinner.hide();
    // this.isEditModeOtherCharges = false;
    // this.headingOtherCharges = this.CREATE_OtherCharges_HEADING;
    // this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.cancelExpensesmanagementRelated();
    this.spinner.hide();
  }

  cancelExpensesmanagementRelated() {
    this.createexpensesMangementRelatedModel.hide();
    this.tourExpenseRequest = new TourExpenseRequest();
    this.lodgingExpenseRequest = new LodgingExpenseRequest();
    this.boardingExpenseRequest = new BoardingExpenseRequest();
    this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
    this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
  }

  getApprovalHistoryList() {
    this.spinner.show();
    this._approvalHistoryService.getApprovalStatus(this.objectName, this.expensesmanagement.expenseManagementId).subscribe(data => {
      this.approvalHistoryList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }

  removeFile(fileName) {
    if (this.fileList.length > 0) {
      for (const value of this.fileList) {
        if (value['name'] === fileName) {
          this.fileList.splice(this.fileList.indexOf(value), 1);
        }
      }
      this.searchResult.show();
    }
  }

  onFileSelect(event) {
    this.fileList = [];
    if (event.target.files.length > 0) {
      this.uploadFilesLength = event.target.files.length;
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
      }
      this.searchResult.show();
    }
  }

  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
    }
    this._http.post<any>('api/uploadMultipleFiles?objectName=ExpenseManagement&objectId=' +
      this.expensesmanagement.expenseManagementId, this.formData).subscribe(data => {
        this.searchResult.hide();
        this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
        // this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
        this.getApprovalHistoryList();
          this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
          this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
          this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
          this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);
         this.getGLCodes();
        this.spinner.hide();
        this.popupNgClass = 'alert alert-success center center-block';
        if (this.fileList.length === 0) {
          this.message = 'No File uploaded';
        } else {
          this.message = 'Updated Successfully';
        }
        this.popupSuccessModel.show();
        this.fileList = [];
        
    (<HTMLInputElement>document.getElementById('getFile')).value=null
      }, error => {
        this.fileList = [];
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = error.error.message;
        this.popupSuccessModel.show();
      });
    this.formData = new FormData();
  }

  getFileList(objectName: string, objectId: number) {
    this.spinner.show();
    this._expenseManagementService.getFileList(objectName, objectId).subscribe(data => {
      this.fileUploadList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  closeUploadFiles() {
    this.searchResult.hide();
    
    (<HTMLInputElement>document.getElementById('getFile')).value=null
    this.formData = new FormData();
  }

  showPreview(url, fileName, fileContentType) {
    this.fileName = fileName;
    this.spinner.show();
    this.imageUrl = url;
    this._expenseManagementService.downloadFile(fileName).subscribe(data => {
      this.showPreviewModel.show();
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this.message = 'Error while showing preview';
      this.popupSuccessModel.show();
    });
  }

  saveFile() {
    fileSaver.saveAs(this.imageUrl, this.fileName);
    this.spinner.hide();
  }

  createPDF() {
    this.spinner.show();
    this._pdfService.createPDF('api/expensemanagement', 'expenseManagementId',
      this.expensesmanagement.expenseManagementId).subscribe(data => {
        const file = new Blob([data], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this.spinner.hide();
        this.message = 'Created successfully';
        this.confirmationPopup.hide();
        this.popupNgClass = 'alert alert-success center center-block';
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.confirmationPopup.hide();
        this.message = err.message;
        this.popupNgClass = 'alert alert-danger center center-block';
        this.popupSuccessModel.show();
      });
  }
  OpnGrade(bordingName){
    this.borloddtailModel.show();
    this.heading=bordingName;
    this.spinner.show();
    this.boadingservice.getDetailByboadingName(bordingName).subscribe(data=>{
      this.spinner.hide();
      this.response=data;
    });

  }
  getGLCodes(){
    this.spinner.show();
    this._expenseManagementService.getGLSummaryList(this.expensesmanagement.expenseManagementId).subscribe(data=>
      {
        this.spinner.hide();
        this.glcodeList=data;
      },error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this.popupSuccessModel.show();
      }
      )
  }
onClickOnlyAccount()
{
  this.heading="Accounts Only"
 this.onlyAccountModel.show();
 this.spinner.show();
 this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.expenseManagementId).subscribe((data) => {
   this.accountOnlyReq.businessAreaId = data.businessAreaId;
   this.accountOnlyReq.businessPlaceId=data.businessPlaceId;
   this.accountOnlyReq.narration=data.narration;
   this.accountOnlyReq.documentDate=data.documentDate;
   this.accountOnlyReq.narration=data.narration;
   
  //  this.isEditMode = true;
   
   
   this.spinner.hide();
 }, err => {
   this.spinner.hide();
   this.message = err.status + ' # ' + err.error.message;
   this.popupSuccessModel.show();
 });
}
onCancleAccountsOnly(){
  this.onlyAccountModel.hide();
}
onAccountsOnlyCreated($event){
  this.getexpensesmanagementById();
  // this.expensesmanagement=$event;
}
DeletAttachment(imageid){
  const objectId=this.expensesmanagement.expenseManagementId
  this.spinner.show();
  this.deleteservice.deleteAttachment(imageid,this.actionHistoryObjectAndId.objectName,objectId).subscribe(data=>{
    this.spinner.hide();
    this.getFileList(this.actionHistoryObjectAndId.objectName, this.actionHistoryObjectAndId.objectId);
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
  })
}

approvalmessage:string='';
tourId:any;
tour:boolean=false;
 baseUrl = '';
  // private baseUrllodging = 'api/tourexpense';
  // private baseUrllocalCon = 'api/tourexpense';
  // private baseUrlOther = 'api/tourexpense';
onDeleteTourExpense(id){
  // this.spinner.show();
  console.log(id);
  this.baseUrl='api/tourexpense'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeletelocaCovExpense(id){
  // this.spinner.show();
  this.baseUrl='api/localconveyance'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteBodingExpense(id){
  // this.spinner.show();
  this.baseUrl='api/boardingexpense'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteOtherExpense(id){
  // this.spinner.show();
  this.baseUrl='api/otherchargesexpenses'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteLodginExpense(id){
  // this.spinner.show();
  this.baseUrl='api/lodgingexpense'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onSubmitted($event){
  this.getTourExpenseListByExpenseId(this.expensesmanagement.expenseManagementId);
    
  this.getLodgingListByExpenseId(this.expensesmanagement.expenseManagementId);
  this.getBoardingListByExpenseId(this.expensesmanagement.expenseManagementId);
  this.getLocalConveyanceListByExpenseId(this.expensesmanagement.expenseManagementId);
  this.getOtherChargesListByExpenseId(this.expensesmanagement.expenseManagementId);

}
noRequestFor(){
  this.deleteExpModel.hide();

}

}

