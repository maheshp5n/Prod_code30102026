import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';

import { FileDeleteService } from '../../../services/filedeletService.services';

import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, BoardingLoadingDetailsResponse, ExpenseAccountsOnlyRequest, ExpenseManagementRequest, ExpenseManagementResponse, FileUploadResponse, GLCodeResponse, LocalConveyanceExpenseRequest, LocalConveyanceExpenseResponse, LodgingExpenseRequest, LodgingExpenseResponse, OtherChargesExpenseRequest, OtherChargesExpenseResponse, ToBeApprovedHistoryResponse, TourExpenseRequest, TourExpenseResponse } from '../../../services/model';
import { PdfServiceService } from '../../../services/pdf-service.service';
import * as fileSaver from 'file-saver';
import { FileUploader } from 'ng2-file-upload';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BatteryExpenseManagementRequest, BatteryExpenseManagementResponse, BtBoardingExpenseRequest, BtBoardingExpenseResponse, BtExpenseAccountsOnlyRequest, btExpServiceAppointmentlist, btExpServiceAppointmentReq, btExpServiceAppointmentResp, BtLocalConveyanceExpenseRequest, BtLocalConveyanceExpenseResponse, BtLodgingExpenseRequest, BtLodgingExpenseResponse, BtOtherChargesExpenseRequest, BtOtherChargesExpenseResponse, BtTourExpenseRequest, BtTourExpenseResponse } from '../../bt_model';
import { BtboardingExpenseService } from '../../bat_services/btboarding-expense.service';
import { BttourExpenseService } from '../../bat_services/bttour-expense.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtotherChargesExpenseService } from '../../bat_services/btother-charges-expense.service';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlodgingExpenseService } from '../../bat_services/btlodging-expense.service';
import { BtlocalConveyanceExpenseService } from '../../bat_services/btlocal-conveyance-expense.service';
import { BtexpenseServiceappointmentService } from '../../bat_services/btexpense-serviceappointment.service';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';

@Component({
  selector: 'app-bat-expensesmanagement-detail',
  templateUrl: './bat-expensesmanagement-detail.component.html',
  styleUrls: ['./bat-expensesmanagement-detail.component.css']
})
export class BatExpensesmanagementDetailComponent implements OnInit {

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

  constructor(private tobeapproved:BtTobeApprovedService,private loginservice:Loginservices,private _expenseManagementService: BtexpensemanagementService, private tourExpenseService: BttourExpenseService,
    private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private ligalentitservice: BtlegalentityService,
    private lodgingService: BtlodgingExpenseService, private boardingService: BtboardingExpenseService,
    private localConveyanceService: BtlocalConveyanceExpenseService, private otherChargesService: BtotherChargesExpenseService,
    protected _sanitizer: DomSanitizer, private _approvalHistoryService: ApprovalHistoryService,
    private formBuilder: UntypedFormBuilder, private _http: HttpClient,
    private deleteservice:FileDeleteService,private _global:GlobalserviceService,
    private datePipe: DatePipe,private boadingservice:BtboardingLoadingExpenseService,private expSAservice:BtexpenseServiceappointmentService,
    private _pdfService: PdfServiceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.expensesmanagement.id = pram['id'];
      this.getexpensesmanagementById();
      this.actionHistoryObjectAndId.objectId = '' + this.expensesmanagement.id;
      this.actionHistoryObjectAndId.objectName = 'BtExpenseManagement';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

    this.getApprovalButton();
  }

  _cFlag: string;
  oneAtATime: boolean = true;
  fileToUpload: File = null;
  // popupNgClass: string = 'alert alert-danger center center-block';

  isExpensesmanagementOpen: boolean;
  isTourOpen: boolean;
  isLodgingOpen: boolean;
  isBoardingOpen: boolean;
  isLocalConveyanceOpen: boolean;
  isOtherChargesOpen: boolean;
  isSAExpOpen: boolean;


  isEditMode: boolean = false;
  message: string;
  heading: string;
  legalEntityList;
  expensesmanagementListById;
  ngClassDetails: string = 'activ';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: string = '';
  expanceApprovalId:number;

  CREATE_expensesmanagement_HEADING: string = 'New Expenses';
  EDIT_expensesmanagement_HEADING: string = 'Edit Expenses';
  newexpensesmanagement: BatteryExpenseManagementRequest = new BatteryExpenseManagementRequest();
  expensesmanagement: BatteryExpenseManagementResponse = new BatteryExpenseManagementResponse();
  expensesmanagementList: Array<BatteryExpenseManagementResponse> = [];

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
  tourExpenseRequest: BtTourExpenseRequest = new BtTourExpenseRequest();
  tourExpenseResponse: BtTourExpenseResponse = new BtTourExpenseResponse();
  tourListByExpenseId: Array<BtTourExpenseResponse> = [];

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
  lodgingExpenseRequest: BtLodgingExpenseRequest = new BtLodgingExpenseRequest();
  lodgingExpenseResponse: BtLodgingExpenseResponse = new BtLodgingExpenseResponse();
  lodgingListByExpenseId: Array<BtLodgingExpenseResponse> = [];

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
  boardingExpenseRequest: BtBoardingExpenseRequest = new BtBoardingExpenseRequest();
  boardingExpenseResponse: BtBoardingExpenseResponse = new BtBoardingExpenseResponse();
  boardingListByExpenseId: Array<BtBoardingExpenseResponse> = [];

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
  localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
  localConveyanceExpenseResponse: BtLocalConveyanceExpenseResponse = new BtLocalConveyanceExpenseResponse();
  localConveyanceListByExpenseId: Array<BtLocalConveyanceExpenseResponse> = [];
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
  otherChargesExpenseRequest: BtOtherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
  otherChargesExpenseResponse: BtOtherChargesExpenseResponse = new BtOtherChargesExpenseResponse();
  otherChargesListByExpenseId: Array<BtOtherChargesExpenseResponse> = [];
  CREATE_ExpSA_HEADING: string = 'New Service Appointment Expense';
  EDIT_ExpSA_HEADING: string = 'Edit Service Appointment Expense';
  headingExpSA: string;
  expSAListByExpenseId: Array<btExpServiceAppointmentResp> =[];
  isEditModeExpSA:boolean = false;
  firstExpSA: boolean;
  lastExpSA: boolean;
  sizeExpSA: number;
  serialNumberExpSA: number;
  public pageExpSA: number = 0;
  totalElementsExpSA: number;
  totalElementsApprovalHistory: number=0;
  SAExpenseRequest: btExpServiceAppointmentReq = new btExpServiceAppointmentReq();
  SAExpenseResponse: btExpServiceAppointmentResp = new btExpServiceAppointmentResp();
  expClaim: Array<btExpServiceAppointmentlist> = [];
  glcodeList:Array<GLCodeResponse>=[];
  
  accountOnlyReq:BtExpenseAccountsOnlyRequest=new BtExpenseAccountsOnlyRequest();

  i: number;
  objectName: string = 'BtExpenseManagement';
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
  @ViewChild('createExpSAModel') createExpSAModel: ModalDirective;
  @ViewChild('createexpensesmanagementModel') createexpensesmanagementModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createexpensesMangementRelatedModel') createexpensesMangementRelatedModel: ModalDirective;
  @ViewChild('searchResult') searchResult: ModalDirective;
  @ViewChild('showPreviewModel') showPreviewModel: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup: ModalDirective;
  @ViewChild('borloddtailModel') borloddtailModel: ModalDirective;
  @ViewChild('onlyAccountModel') onlyAccountModel : ModalDirective;
  @ViewChild('deleteExpModel') deleteExpModel: ModalDirective;
  @ViewChild('submitapprovalmodel') submitapprovalmodel:ModalDirective;
  @ViewChild('approvalModule')approvalModule:ModalDirective;
  uploadForm: UntypedFormGroup;

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
    return this.loginservice.isUserValidFor(object, right);
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
      this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
      this.getApprovalHistoryList();
      // if (this.tourListByExpenseId.length === 0) {
        this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
     
      if (this.lodgingListByExpenseId.length === 0) {
        this.getLodgingListByExpenseId(this.expensesmanagement.id);
      }
      if (this.boardingListByExpenseId.length === 0) {
        this.getBoardingListByExpenseId(this.expensesmanagement.id);
      }
      if (this.localConveyanceListByExpenseId.length === 0) {
        this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      }
      if (this.otherChargesListByExpenseId.length === 0) {
        this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
      }
      if(this.expSAListByExpenseId.length === 0){
        this.getSAListByExpenseId(this.expensesmanagement.id)
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
    this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
  }

  setPageTour(event: any) {
    event.preventDefault();
    this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextTour(event: any) {
    event.preventDefault();
    this.pageTour = this.pageTour + 1;
    this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
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

  onexpensesmanagementCreated($event: BatteryExpenseManagementResponse) {
    this.spinner.show();
    this.expensesmanagement = $event;
    this.spinner.hide();
    this.getexpensesmanagementById();
    this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
    this.getApprovalHistoryList();
    if (this.tourListByExpenseId.length === 0) {
      this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
    }
    if (this.lodgingListByExpenseId.length === 0) {
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
    }
    if (this.boardingListByExpenseId.length === 0) {
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
    }
    if (this.localConveyanceListByExpenseId.length === 0) {
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
    }
    if (this.otherChargesListByExpenseId.length === 0) {
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
    }
    if(this.expSAListByExpenseId.length === 0)
    {
      this.getSAListByExpenseId(this.expensesmanagement.id)
    }
  this.getGLCodes();
  }

  cc(value) {
  }

  onExpensesAdded() {
    this.spinner.show();
    this.getexpensesmanagementById();
    this.getLodgingListByExpenseId(this.expensesmanagement.id);
    this.spinner.hide();
  }
grade:string;
  getexpensesmanagementById() {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.id).subscribe((data) => {
      this.expensesmanagement = data;
     
      this.grade=data.gradeName;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
  }

  onClickEdit() {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.id).subscribe((data) => {
      this.newexpensesmanagement = data;
      this.isEditMode = true;
      this.heading = this.EDIT_expensesmanagement_HEADING;
      this.createexpensesmanagementModel.show();
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
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
        this._global.alert({type:'alert',message:this.message});
      });
  }

  showTourExpenses() {
    this.isEditModeTourExpense = false;
    this.headingTourExpenses = this.CREATE_TourExpenses_HEADING;
    this.tourExpenseRequest = new BtTourExpenseRequest();
    this.tourExpenseRequest.btExpenseManagementId = this.expensesmanagement.id;
    this.tourExpenseRequest.expenseManagementManualId = this.expensesmanagement.manualId;
    // this.createTourExpenseModel.show();
  }
  showApprovalWindow(){
    this.approvalModule.show();
  }
  onApprovalDone(){
    this.approvalModule.hide();
    // this.getPendingPage();
  }

  onClickEditTourExpense(expenseManagementId,amountToDisbursed) {
    this.tourExpenseRequest = new BtTourExpenseRequest();
    this.touramountToDisbursed=amountToDisbursed;
    this.isEditModeTourExpense=true;
    this.spinner.show();
    this.tourExpenseService.getTourExpenseDetails(expenseManagementId).subscribe((data) => {
      this.tourExpenseRequest.id = data.id;
      this.tourExpenseRequest.btExpenseManagementId = data.id;
      this.tourExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
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
      this.tourExpenseRequest.manualId = data.manual_id;
      // this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'dd-MM-yyyy');
      // this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'dd-MM-yyyy');

      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
    this.isEditModeTourExpense = true;
    this.headingTourExpenses = this.EDIT_TourExpenses_HEADING;
    this.createTourExpenseModel.show();
  }

  onTourExpensesCreated($event) {
    this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
    if (!this.isEditModeTourExpense) {
      this.tourListByExpenseId.unshift($event);
      this.totalElementsTour = this.totalElementsTour + 1;
    } else {
      let index = 0;
      this.tourListByExpenseId.forEach(element => {
        if (element.btExpenseManagementId === $event.id) {
          this.tourListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
     this.getGLCodes();
     this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  // Tour Ends

  // Lodging starts

  setPagePreviousLodging(event: any) {
    event.preventDefault();
    this.pageLodging = this.pageLodging - 1;
    this.getLodgingListByExpenseId(this.expensesmanagement.id);
  }

  setPageLodging(event: any) {
    event.preventDefault();
    this.getLodgingListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextLodging(event: any) {
    event.preventDefault();
    this.pageLodging = this.pageLodging + 1;
    this.getLodgingListByExpenseId(this.expensesmanagement.id);
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
        this._global.alert({type:'alert',message:this.message});
      });
  }

  showLodging() {
    this.isEditModeLodging = false;
    this.headingLodging = this.CREATE_Lodging_HEADING;
    this.lodgingExpenseRequest = new BtLodgingExpenseRequest();
    this.lodgingExpenseRequest.btExpenseManagementId = this.expensesmanagement.id;
    this.lodgingExpenseRequest.expenseManagementManualId = this.expensesmanagement.manualId;
    // this.createLodgingModel.show();
  }

  onClickEditLodging(id,amountToDisbursed) {
    
    this.lodgingExpenseResponse = new BtLodgingExpenseResponse();
    this.lodamountToDisbursed=amountToDisbursed;
    this.spinner.show();
    this.isEditModeLodging=true;
    this.lodgingService.getLodgingExpenseDetails(id).subscribe((data) => {
      this.lodgingExpenseResponse = data;
      this.lodgingExpenseResponse.id = data.id;
      this.lodgingExpenseResponse.btExpenseManagementId = data.id;
      this.lodgingExpenseResponse.btExpenseManagementManualId = data.btExpenseManagementManualId;
      // this.lodgingExpenseRequest.fromDate = this.datePipe.transform(this.lodgingExpenseRequest.fromDate, 'dd-MM-yyyy');
      // this.lodgingExpenseRequest.toDate = this.datePipe.transform(this.lodgingExpenseRequest.toDate, 'dd-MM-yyyy');
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
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
        if (element.btExpenseManagementId === $event.id) {
          this.lodgingListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
     this.getGLCodes();
     this.getSAListByExpenseId(this.expensesmanagement.id);
  }
  // Lodging Ends

  // Boarding starts

  setPagePreviousBoarding(event: any) {
    event.preventDefault();
    this.pageBoarding = this.pageBoarding - 1;
    this.getBoardingListByExpenseId(this.expensesmanagement.id);
  }

  setPageBoarding(event: any) {
    event.preventDefault();
    this.getBoardingListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextBoarding(event: any) {
    event.preventDefault();
    this.pageBoarding = this.pageBoarding + 1;
    this.getBoardingListByExpenseId(this.expensesmanagement.id);
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
        this._global.alert({type:'alert',message:this.message});
      });
  }

  showBoarding() {
    this.isEditModeBoarding = false;
    this.headingBoarding = this.CREATE_Boarding_HEADING;
    this.boardingExpenseRequest = new BtBoardingExpenseRequest();
    this.boardingExpenseRequest.btExpenseManagementId = this.expensesmanagement.id;
    this.boardingExpenseRequest.expenseManagementManualId = this.expensesmanagement.manualId;
    // this.createBoardingModel.show();
  }
  bodamountToDisbursed:number;
  onClickEditBoarding(id,bodamountToDisbursed) {
    this.boardingExpenseRequest = new BtBoardingExpenseRequest();
    this.bodamountToDisbursed=bodamountToDisbursed;
    this.isEditModeBoarding=true;
    this.spinner.show();
    this.boardingService.getBoardingExpenseDetails(id).subscribe((data) => {
      this.boardingExpenseResponse = data;
      this.boardingExpenseResponse.id = data.id;
      this.boardingExpenseResponse.btExpenseManagementId = data.id;
      this.boardingExpenseResponse.btExpenseManagementManualId = data.btExpenseManagementManualId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
    this.isEditModeBoarding = true;
    this.headingBoarding = this.EDIT_Boarding_HEADING;
    this.createBoardingModel.show();
  }

  onBoardingCreated($event) {
    this.getBoardingListByExpenseId(this.expensesmanagement.id);
    if (!this.isEditModeBoarding) {
      this.boardingListByExpenseId.unshift($event);
      this.totalElementsBoarding = this.totalElementsBoarding + 1;
    } else {
      let index = 0;
      this.boardingListByExpenseId.forEach(element => {
        if (element.btExpenseManagementId === $event.id) {
          this.boardingListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
     this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
     this.getGLCodes();
     this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  // Boarding Ends

  // LocalConveyance starts

  setPagePreviousLocalConveyance(event: any) {
    event.preventDefault();
    this.pageLocalConveyance = this.pageLocalConveyance - 1;
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
  }

  setPageLocalConveyance(event: any) {
    event.preventDefault();
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextLocalConveyance(event: any) {
    event.preventDefault();
    this.pageLocalConveyance = this.pageLocalConveyance + 1;
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
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
        this._global.alert({type:'alert',message:this.message});
      });
  }

  showLocalConveyance() {
    this.isEditModeLocalConveyance = false;
    this.headingLocalConveyance = this.CREATE_LocalConveyance_HEADING;
    this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
    this.localConveyanceExpenseRequest.btExpenseManagementId = this.expensesmanagement.id;
    this.localConveyanceExpenseRequest.expenseManagementManualId = this.expensesmanagement.manualId;
    // this.createLocalConveyanceModel.show();
  }
  localamountToDisbursed:number;
  onClickEditLocalConveyance(id,localamountToDisbursed) {
    this.localConveyanceExpenseResponse = new BtLocalConveyanceExpenseResponse();
    this.localamountToDisbursed=localamountToDisbursed;
    this.spinner.show();
    this.localConveyanceService.getLocalConveyanceExpenseDetails(id).subscribe((data) => {
      this.localConveyanceExpenseResponse = data;
      this.localConveyanceExpenseResponse.id = data.id;
      this.localConveyanceExpenseResponse.btExpenseManagementId = data.id;
      this.localConveyanceExpenseResponse.btExpenseManagementManualId = data.btExpenseManagementManualId;
      this.localConveyanceExpenseResponse.ownConveyanceId = data.ownConveyanceId;
      this.localConveyanceExpenseResponse.date=data.date;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
    this.isEditModeLocalConveyance = true;
    this.headingLocalConveyance = this.EDIT_LocalConveyance_HEADING;
    this.createLocalConveyanceModel.show();
  }

  onLocalConveyanceCreated($event) {

    // if (!this.isEditModeLocalConveyance) {
    //   this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
  
    //   this.localConveyanceListByExpenseId.unshift($event);
    //   this.totalElementsLocalConveyance = this.totalElementsLocalConveyance + 1;
    // } else {
    //   let index = 0;
    //   this.localConveyanceListByExpenseId.forEach(element => {
    //     if (element.btExpenseManagementId === $event.id) {
    //       this.localConveyanceListByExpenseId[index] = $event;
    //     } else {
    //       index++;
    //     }
    //   });
    // }
    this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);

    this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
    this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
     this.getGLCodes();
     this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  // LocalConveyance Ends

  // OtherCharges starts

  setPagePreviousOtherCharges(event: any) {
    event.preventDefault();
    this.pageOtherCharges = this.pageOtherCharges - 1;
    this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
  }

  setPageOtherCharges(event: any) {
    event.preventDefault();
    this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextOtherCharges(event: any) {
    event.preventDefault();
    this.pageOtherCharges = this.pageOtherCharges + 1;
    this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
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
        this._global.alert({type:'alert',message:this.message});
      });
  }


  showOtherCharges(id) {
    this.isEditModeExpSA = false;
    this.headingOtherCharges = this.CREATE_OtherCharges_HEADING;
    // this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.otherChargesExpenseRequest.btExpenseManagementId = id;
    // this.otherChargesExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    this.otherChargesExpenseRequest.businessDomainId = this.expensesmanagement.businessDomainId;
    // this.createOtherChargesModel.show();
  }
  loginData:any;
  otherchangeamountToDisbursed:number;
  onClickEditOtherCharges(id,otherchangeamountToDisbursed) {
    this.otherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
    this.otherchangeamountToDisbursed=otherchangeamountToDisbursed;
    this.spinner.show();
    this.otherChargesService.getOtherChargesDetails(id).subscribe((data) => {
      this.otherChargesExpenseResponse = data;
      this.otherChargesExpenseResponse.id = data.id;
      this.loginData = this.loginservice.getLoggedInUser();
      this.otherChargesExpenseResponse.businessDomainId = this.loginData.emptype['businessDomainId'];
      
      this.otherChargesExpenseResponse.btExpenseManagementId = data.id;
      this.otherChargesExpenseResponse.btExpenseManagementManualId = data.btExpenseManagementManualId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
    this.isEditModeOtherCharges = true;
    this.headingOtherCharges = this.EDIT_OtherCharges_HEADING;
    this.createOtherChargesModel.show();
    // this.spinner.hide();
  }

  onOtherChargesCreated($event) {
    this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
    if (!this.isEditModeOtherCharges) {
      this.otherChargesListByExpenseId.unshift($event);
      this.totalElementsOtherCharges = this.totalElementsOtherCharges + 1;
    } else {
      let index = 0;
      this.otherChargesListByExpenseId.forEach(element => {
        if (element.btExpenseManagementId === $event.id) {
          this.otherChargesListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
      this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
      this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      this.getSAListByExpenseId(this.expensesmanagement.id);
      this.getGLCodes();
  }

  // Service Appointment Expense
  setPagePreviousSAExp(event: any) {
    event.preventDefault();
    this.pageExpSA = this.pageExpSA - 1;
    this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  setPageSAExp(event: any) {
    event.preventDefault();
    this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  setPageNextSAExp(event: any) {
    event.preventDefault();
    this.pageExpSA = this.pageExpSA + 1;
    this.getSAListByExpenseId(this.expensesmanagement.id);
  }

  getSAListByExpenseId(expensesmanagementId) {
    this.spinner.show();
    this.expSAservice.getServiceAppointmentExpenseDetails(expensesmanagementId, this.pageOtherCharges).subscribe(
      data => {
        this.expSAListByExpenseId = data.content;
        this.firstExpSA = data.first;
        this.lastExpSA = data.last;
        this.sizeExpSA = data.size;
        this.totalElementsExpSA = data.totalElements;
        this.serialNumberExpSA = ((this.pageExpSA) * this.sizeExpSA);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' # ' + err.error.message;
        this._global.alert({type:'alert',message:this.message});
      });
  }


  showExpSA(id) {
    this.isEditModeExpSA = false;
    this.headingExpSA = this.CREATE_ExpSA_HEADING;
    // this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.SAExpenseRequest.btExpId = id;
    // this.otherChargesExpenseRequest.expenseManagementManualId = this.expensesmanagement.expenseManagementManualId;
    // this.otherChargesExpenseRequest.businessDomainId = this.expensesmanagement.businessDomainId;
    // this.createOtherChargesModel.show();
  }
  onClickEditExpSA(id) {
    this.SAExpenseRequest = new btExpServiceAppointmentReq();
    // this.otherchangeamountToDisbursed=otherchangeamountToDisbursed;
    this.spinner.show();
    this.expSAservice.getServiceAppointmentExpenseDetailsById(id).subscribe((data) => {
      this.SAExpenseResponse = data;
      this.SAExpenseResponse.id = data.id;
      this.loginData = this.loginservice.getLoggedInUser();
      // this.otherChargesExpenseResponse.businessDomainId = this.loginData.emptype['businessDomainId'];
      
      this.SAExpenseResponse.btExpenseManagementId = data.id;
      // this.SAExpenseResponse.btExpenseManagementManualId = data.btExpenseManagementManualId;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this._global.alert({type:'alert',message:this.message});
    });
    this.isEditModeExpSA = true;
    this.headingExpSA = this.EDIT_ExpSA_HEADING;
    this.createExpSAModel.show();
    // this.spinner.hide();
  }
  onExpSACreated($event)
  {
    this.getSAListByExpenseId(this.expensesmanagement.id);
    if (!this.isEditModeExpSA) {
      this.expSAListByExpenseId.unshift($event);
      this.totalElementsExpSA = this.totalElementsExpSA + 1;
    } else {
      let index = 0;
      this.expSAListByExpenseId.forEach(element => {
        if (element.btExpenseManagementId === $event.id) {
          this.expSAListByExpenseId[index] = $event;
        } else {
          index++;
        }
      });
    }
      this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
      this.getApprovalHistoryList();
      this.getLodgingListByExpenseId(this.expensesmanagement.id);
      this.getBoardingListByExpenseId(this.expensesmanagement.id);
      this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
      this.getOtherChargesListByExpenseId(this.expensesmanagement.id)
      this.getGLCodes();
  }
  // OtherCharges Ends




  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   // if (this.popupNgClass == 'alert alert-success center center-block') {
  //   //   this.onCancle.emit('cancled');
  //   // }
  // }
 remarks:string;
  onClickSSumitApproval() {
    this.spinner.show();
    this._expenseManagementService.postsubmitforapproval(this.expensesmanagement.id,
      this.expensesmanagement.businessDomainId,this.remarks).subscribe(data => {
        this.spinner.hide();
        this.message = 'Submitted Successfully';
        this._global.alert({type:'',message:this.message});
        this.submitapprovalmodel.hide();
        this.getexpensesmanagementById();
      }, error => {
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
    this.isEditModeExpSA = false;
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
  cancelSAExp() {
    // this.spinner.hide();
    // this.createexpensesmanagementModel.hide();
    this.createExpSAModel.hide();
    this.spinner.hide();
    // this.isEditModeOtherCharges = false;
    // this.headingOtherCharges = this.CREATE_OtherCharges_HEADING;
    // this.otherChargesExpenseRequest = new OtherChargesExpenseRequest();
    this.cancelExpensesmanagementRelated();
    this.spinner.hide();
  }


  cancelExpensesmanagementRelated() {
    this.createexpensesMangementRelatedModel.hide();
    this.tourExpenseRequest = new BtTourExpenseRequest();
    this.lodgingExpenseResponse = new BtLodgingExpenseResponse();
    this.boardingExpenseResponse = new BtBoardingExpenseResponse();
    this.localConveyanceExpenseResponse = new BtLocalConveyanceExpenseResponse();
    this.otherChargesExpenseResponse = new BtOtherChargesExpenseResponse();
    this.otherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
    this.lodgingExpenseRequest = new BtLodgingExpenseRequest();
    this.boardingExpenseRequest = new BtBoardingExpenseRequest();
    this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
  }

  getApprovalHistoryList() {
    this.spinner.show();
    this._approvalHistoryService.getApprovalStatusbt(this.objectName, this.expensesmanagement.id).subscribe(data => {
      this.approvalHistoryList = data;
      this.totalElementsApprovalHistory = data.length;
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
    this._http.post<any>('api/uploadMultipleFiles?objectName=BtExpenseManagement&objectId=' +
      this.expensesmanagement.id, this.formData).subscribe(data => {
        this.searchResult.hide();
        this.getFileList('BtExpenseManagement', this.expensesmanagement.id);
        // this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
        this.getApprovalHistoryList();
          this.getLodgingListByExpenseId(this.expensesmanagement.id);
          this.getBoardingListByExpenseId(this.expensesmanagement.id);
          this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
          this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
          this.getSAListByExpenseId(this.expensesmanagement.id)
         this.getGLCodes();
        this.spinner.hide();
        if (this.fileList.length === 0) {
          this.message = 'No File uploaded';
        } else {
          this.message = 'Updated Successfully';
        }
        this._global.alert({type:'',message:this.message});
        this.fileList = [];
        
    (<HTMLInputElement>document.getElementById('getFile')).value=null
      }, error => {
        this.fileList = [];
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({type:'alert',message:this.message});
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
      this._global.alert({type:'alert',message:this.message});
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
      this._global.alert({type:'alert',message:this.message});
    });
  }

  saveFile() {
    fileSaver.saveAs(this.imageUrl, this.fileName);
    this.spinner.hide();
  }

  createPDF() {
    this.spinner.show();
    this._pdfService.createPDF('bt/api/expense', 'btExpenseManagementId',
      this.expensesmanagement.id).subscribe(data => {
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
        this._global.alert({type:'',message:this.message});
      }, err => {
        this.spinner.hide();
        this.confirmationPopup.hide();
        this.message = err.message;
        this._global.alert({type:'alert',message:this.message});
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
    this._expenseManagementService.getGLSummaryList(this.expensesmanagement.id).subscribe(data=>
      {
        this.spinner.hide();
        this.glcodeList=data;
      },error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this._global.alert({type:'alert',message:this.message});
      }
      )
  }
onClickOnlyAccount()
{
  this.heading="Accounts Only"
 this.onlyAccountModel.show();
 this.spinner.show();
 this._expenseManagementService.getexpensesmanagementById(this.expensesmanagement.id).subscribe((data) => {
   if(data.businessAreaId==null){
    this.accountOnlyReq.businessAreaId =0;
   }else{
   this.accountOnlyReq.businessAreaId = data.businessAreaId;
   }
   if(data.businessPlaceId==null){
    this.accountOnlyReq.businessPlaceId =0;
   }else{
   this.accountOnlyReq.businessPlaceId=data.businessPlaceId;
   }
   this.accountOnlyReq.narration=data.narration;
   this.accountOnlyReq.documentDate=data.documentDate;
   this.accountOnlyReq.narration=data.narration;
   
  //  this.isEditMode = true;
   
   
   this.spinner.hide();
 }, err => {
   this.spinner.hide();
   this.message = err.status + ' # ' + err.error.message;
   this._global.alert({type:'alert',message:this.message});
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
  const objectId=this.expensesmanagement.id
  this.spinner.show();
  this.deleteservice.deleteAttachment(imageid,this.actionHistoryObjectAndId.objectName,objectId).subscribe(data=>{
    this.spinner.hide();
    this.getFileList(this.actionHistoryObjectAndId.objectName, this.actionHistoryObjectAndId.objectId);
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this._global.alert({type:'alert',message:this.message});
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
  this.baseUrl='bt/api/tourexpense'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeletelocaCovExpense(id){
  // this.spinner.show();
  this.baseUrl='bt/api/btlocalconveyance'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteBodingExpense(id){
  // this.spinner.show();
  this.baseUrl='bt/api/boardingexpense'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteOtherExpense(id){
  // this.spinner.show();
  this.baseUrl='bt/api/otherchargesexpenses'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteExpSA(id){
  // this.spinner.show();
  this.baseUrl='bt/api/btServiceAppointmentExp'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onDeleteLodginExpense(id){
  // this.spinner.show();
  this.baseUrl='bt/api/lodgingexpenses'
  this.approvalmessage="Are You Sure For Delete"
  this.tourId=id;
  this.tour=true;
  this.deleteExpModel.show();

}
onSubmitted($event){
  this.getTourExpenseListByExpenseId(this.expensesmanagement.id);
    
  this.getLodgingListByExpenseId(this.expensesmanagement.id);
  this.getBoardingListByExpenseId(this.expensesmanagement.id);
  this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
  this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
  this.getSAListByExpenseId(this.expensesmanagement.id)

}
noRequestFor(){
  this.deleteExpModel.hide();

}
btnshow:boolean=false;
getApprovalButton() {
  this.spinner.show()
  this.tobeapproved.getItemToBeApprovdButton(this.objectName, this.expensesmanagement.id).subscribe(data => {
    console.log(data);
    this.spinner.hide();
    if( data.id != null && data != null && data != undefined)
    {
      this.expanceApprovalId = data.id;
      this.btnshow = true;

    }
    else {
      this.btnshow = false;
      this.expanceApprovalId = this.expensesmanagement.id
    }
  }, error=>{

  });
}
}

