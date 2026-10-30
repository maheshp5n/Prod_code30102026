import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import {
  PackingCondition, FaultObserved, BRCRepiarStatus, CaseResponse, CaseForList, SiteDetailsResponse,
  LegalEntityResponse, AssetsForList, ContactForList, ProductsForList, AssetsResponse, Contacts, Product,
  ServiceCenterForList, SiteDetailsForList, BRCInventoryReceiveResponse, BRCRepairInventoryReceiveRequest
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { AssetsService } from '../../services/assets.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ContactsService } from '../../services/contacts.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-create-manageinventory',
  templateUrl: './create-manageinventory.component.html',
  styleUrls: ['./create-manageinventory.component.scss']
})
export class CreateManageinventoryComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createCaseModel', { static: false }) createCaseModel: ModalDirective;
  @ViewChild('createAssetModel', { static: false }) createAssetModel: ModalDirective;
  @ViewChild('createSiteModel', { static: false }) createSiteModel: ModalDirective;
  @ViewChild('createContactModel', { static: false }) createContactModel: ModalDirective;
  @ViewChild('createProductModel', { static: false }) createProductModel: ModalDirective;
  @ViewChild('createLegalENtityModel', { static: false }) createLegalENtityModel: ModalDirective;

  _isEditMode: boolean
  message: string;

  brcRepairInventoryReceiveRequest: BRCRepairInventoryReceiveRequest = new BRCRepairInventoryReceiveRequest();
  bRCInventoryReceiveResponse: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
  packingConditionList: Array<PackingCondition> = [];
  faultObservedList: Array<FaultObserved> = [];
  physicalDamageList: Array<any> = [];
  bRCRepiarStatusList: Array<BRCRepiarStatus> = [];
  assetResponse: AssetsResponse = new AssetsResponse();
  caseResponse: CaseResponse = new CaseResponse();
  siteResponse: SiteDetailsResponse = new SiteDetailsResponse();
  req: LegalEntityResponse = new LegalEntityResponse();
  newcontact: Contacts = new Contacts();
  createProduct: Product = new Product();
  productList: Array<ProductsForList> = []
  productsForList: ProductsForList = new ProductsForList();
  assetList: Array<AssetsForList> = [];
  assetForlist: AssetsForList = new AssetsForList();
  legalEntityList: Array<ServiceCenterForList> = [];
  legalEntityForList: ServiceCenterForList = new ServiceCenterForList();
  caseList: Array<CaseForList> = [];
  caseForList: CaseForList = new CaseForList();
  siteList: Array<SiteDetailsForList> = [];
  siteForList: SiteDetailsForList = new SiteDetailsForList();
  contactList: Array<ContactForList> = [];
  contactForList: ContactForList = new ContactForList();

  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  isProductModelShow: boolean = false;
  isAssetModelShow: boolean = false;
  isLegalEntityModelShow: boolean = false;
  isCaseModelShow: boolean = false;
  isSiteModelShow: boolean = false;
  isContactModelShow: boolean = false;
  tempId: number;
  tempManual: string;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editManageInventoryData(bRCInventoryReceiveResponse: BRCInventoryReceiveResponse) {
    this.bRCInventoryReceiveResponse = bRCInventoryReceiveResponse;
  }

  get editManageInventoryData(): BRCInventoryReceiveResponse {
    return this.bRCInventoryReceiveResponse;
  }

  @Output()
  onManageInventoryCreated: EventEmitter<BRCRepairInventoryReceiveRequest> = new EventEmitter<BRCRepairInventoryReceiveRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private mangaeInventoryService: ManageInventoryService,
    private assetService: AssetsService, private legalEntityService: ligalEntityService,
    private serviceAppointment: ServiceAppointmentService, private contactService: ContactsService,
    private _serviceAppointment: ServiceAppointmentService, private datePipe: DatePipe, private loginService: Loginservices) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }

  ngOnInit() {
    this.getPackingCondation();
    this.getFaultObserved();
    this.getPhysicalDamage();
    this.getStatus();
    this.getProductList();
    this.getLegalEntity();
    this.getCaseList();
    this.faPlus;
  }
  getPackingCondation() {
    this.mangaeInventoryService.getPackingCondation().subscribe(data => {
      this.packingConditionList = data;
    }, err => {

    })
  }
  getFaultObserved() {
    this.mangaeInventoryService.getFaultObserved().subscribe(data => {
      this.faultObservedList = data;
    }, err => {

    })
  }
  getPhysicalDamage() {
    this.mangaeInventoryService.getPhysicalDamage().subscribe(data => {
      this.physicalDamageList = data;
    }, err => {

    })
  }

  getStatus() {
    this.mangaeInventoryService.getStatus().subscribe(data => {
      this.bRCRepiarStatusList = data;
    }, err => {

    })
  }

  getProductList() {
    this.spinner.show();
    this.assetService.getProductForList().subscribe(data => {
      this.spinner.hide();
      // this.productList = data;
      // const pro=new ProductsForList();
      this.productList = data;
      data.forEach(e=>{
        let pro=new ProductsForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this.productList.push(pro);
      })
    }, err => {
      this.spinner.hide();
      this.message=err.error.message;
      this.popupSuccessModel.show();
    })
  }

  getLegalEntity() {
    this.legalEntityService.getLegalEntityForList().subscribe(data => {
      this.legalEntityList = data;
    }, err => {

    })
  }
  getCaseList() {
    this.serviceAppointment.getCaseLists().subscribe(data => {
      this.caseList = data;
    }, err => {

    })
  }
  getCaseListByid(caseId: number) {
    if (caseId != null) {
      this.spinner.show();
      this._serviceAppointment.getCaseById(caseId).subscribe(data => {
        this.caseResponse = data;
        this.bRCInventoryReceiveResponse.contactId = this.caseResponse.contactPersonId;
        this.bRCInventoryReceiveResponse.contactFirstName = this.caseResponse.contactPersonFirstName;
        this.bRCInventoryReceiveResponse.contactLastName = this.caseResponse.contactPersonLastName;
        this.bRCInventoryReceiveResponse.contactmiddleName = this.caseResponse.contactPersonMiddleName;

        this.bRCInventoryReceiveResponse.assetsSerialNumber = this.caseResponse.assetsSerialNumber;
        this.bRCInventoryReceiveResponse.siteId = this.caseResponse.siteId;
        this.bRCInventoryReceiveResponse.siteManualId = this.caseResponse.siteManualId;
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
  }

  postCreateManageInventory() {
    if (this.bRCInventoryReceiveResponse.productPartCode == null || this.bRCInventoryReceiveResponse.productPartCode === '') {
      this.message = 'Please fill the inventory';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.caseId == null || this.bRCInventoryReceiveResponse.caseId === 0) {
      this.message = 'Please fill the Case';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.legalEntityId == null || this.bRCInventoryReceiveResponse.legalEntityId === 0) {
      this.message = 'Please fill the legal entity';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.invoiceNumber != undefined && (this.bRCInventoryReceiveResponse.invoiceNumber).length >= 200) {
      this.message = 'please fill the invoice number under 200 characters'
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.alterSerialNumber == null || this.bRCInventoryReceiveResponse.alterSerialNumber == '') {
      this.message = 'Please fill the serial number';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.alterSerialNumber != undefined && (this.bRCInventoryReceiveResponse.alterSerialNumber).length >= 200) {
      this.message = 'Please fill the serial no under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.challanNumber == null || this.bRCInventoryReceiveResponse.challanNumber == '') {
      this.message = 'Please fill the challan number ';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.challanNumber != undefined && (this.bRCInventoryReceiveResponse.challanNumber).length >= 200) {
      this.message = 'Please fill the challan number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.locationStateOrCircle == null || this.bRCInventoryReceiveResponse.locationStateOrCircle == '') {
      this.message = 'Please fill the location';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.locationStateOrCircle != undefined && (this.bRCInventoryReceiveResponse.locationStateOrCircle).length >= 200) {
      this.message = 'Please fill the location under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.packingConditionId == null || this.bRCInventoryReceiveResponse.packingConditionId === 0) {
      this.message = 'Please fill the packing condition';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.receivedDate == null) {
      this.message = 'Please fill the receive date';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.physicalDamageIfAny == null || this.bRCInventoryReceiveResponse.physicalDamageIfAny === '0') {
      this.message = 'Please fill the physical damage if any';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.faultObservedId == null || this.bRCInventoryReceiveResponse.faultObservedId === 0) {
      this.message = 'Please fill the fault observed';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair == null || this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair == '') {
      this.message = 'Please fill the battery voltage before repair';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair != undefined && (this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair).length >= 200) {
      this.message = 'Please fill the battery voltage before repair under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.bRCInventoryReceiveResponse.brcRepairStatusId == null || this.bRCInventoryReceiveResponse.brcRepairStatusId === 0) {
      this.message = 'Please fill the status';
      this.popupSuccessModel.show();
    }
    else if ((this.bRCInventoryReceiveResponse.remarks != undefined && (this.bRCInventoryReceiveResponse.remarks).length >= 500)) {
      this.message = 'Please fill remarks under 500 characters';
      this.popupSuccessModel.show();
    }
    else
      if (!this._isEditMode) {
        this.brcRepairInventoryReceiveRequest.productPartCode = this.bRCInventoryReceiveResponse.productPartCode;
        this.brcRepairInventoryReceiveRequest.assetsSerialNumber = this.bRCInventoryReceiveResponse.assetsSerialNumber;
        this.brcRepairInventoryReceiveRequest.legalEntityId = this.bRCInventoryReceiveResponse.legalEntityId;
        this.brcRepairInventoryReceiveRequest.caseDetailsId = this.bRCInventoryReceiveResponse.caseId;
        this.brcRepairInventoryReceiveRequest.siteId = this.bRCInventoryReceiveResponse.siteId;
        this.brcRepairInventoryReceiveRequest.invoiceNumber = this.bRCInventoryReceiveResponse.invoiceNumber;
        this.brcRepairInventoryReceiveRequest.contactId = this.bRCInventoryReceiveResponse.contactId;
        this.brcRepairInventoryReceiveRequest.invoiceDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.invoiceDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.alterSerialNumber = this.bRCInventoryReceiveResponse.alterSerialNumber;
        this.brcRepairInventoryReceiveRequest.challanNumber = this.bRCInventoryReceiveResponse.challanNumber;
        this.brcRepairInventoryReceiveRequest.rjilChallanDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.rjilChallanDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.locationStateOrCircle = this.bRCInventoryReceiveResponse.locationStateOrCircle;
        this.brcRepairInventoryReceiveRequest.packingConditionId = this.bRCInventoryReceiveResponse.packingConditionId;
        this.brcRepairInventoryReceiveRequest.receivedDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.receivedDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.physicalDamageIfAny = this.bRCInventoryReceiveResponse.physicalDamageIfAny;
        this.brcRepairInventoryReceiveRequest.faultObservedId = this.bRCInventoryReceiveResponse.faultObservedId;
        this.brcRepairInventoryReceiveRequest.batteryVoltageBefoureRepair = this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair;
        this.brcRepairInventoryReceiveRequest.remarks = this.bRCInventoryReceiveResponse.remarks;
        this.brcRepairInventoryReceiveRequest.brcRepariStatus = this.bRCInventoryReceiveResponse.brcRepairStatusId;

        this.brcRepairInventoryReceiveRequest.receivingName = this.bRCInventoryReceiveResponse.receivingName;
        this.spinner.show();
        this.mangaeInventoryService.saveRequest(this.brcRepairInventoryReceiveRequest).subscribe(data => {
          this.tempId = data.receivingId;
          this.tempManual = data.receivingName;
          this.message = 'Submitted Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onManageInventoryCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      else {
        this.brcRepairInventoryReceiveRequest.receivingId = this.bRCInventoryReceiveResponse.receivingId;
        this.brcRepairInventoryReceiveRequest.productPartCode = this.bRCInventoryReceiveResponse.productPartCode;
        this.brcRepairInventoryReceiveRequest.assetsSerialNumber = this.bRCInventoryReceiveResponse.assetsSerialNumber;
        this.brcRepairInventoryReceiveRequest.legalEntityId = this.bRCInventoryReceiveResponse.legalEntityId;
        this.brcRepairInventoryReceiveRequest.caseDetailsId = this.bRCInventoryReceiveResponse.caseId;
        this.brcRepairInventoryReceiveRequest.siteId = this.bRCInventoryReceiveResponse.siteId;
        this.brcRepairInventoryReceiveRequest.invoiceNumber = this.bRCInventoryReceiveResponse.invoiceNumber;
        this.brcRepairInventoryReceiveRequest.contactId = this.bRCInventoryReceiveResponse.contactId;
        this.brcRepairInventoryReceiveRequest.invoiceDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.invoiceDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.alterSerialNumber = this.bRCInventoryReceiveResponse.alterSerialNumber;
        this.brcRepairInventoryReceiveRequest.challanNumber = this.bRCInventoryReceiveResponse.challanNumber;
        this.brcRepairInventoryReceiveRequest.rjilChallanDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.rjilChallanDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.locationStateOrCircle = this.bRCInventoryReceiveResponse.locationStateOrCircle;
        this.brcRepairInventoryReceiveRequest.packingConditionId = this.bRCInventoryReceiveResponse.packingConditionId;
        this.brcRepairInventoryReceiveRequest.receivedDate = this.datePipe.transform(this.bRCInventoryReceiveResponse.receivedDate, 'yyyy-MM-dd');
        this.brcRepairInventoryReceiveRequest.physicalDamageIfAny = this.bRCInventoryReceiveResponse.physicalDamageIfAny;
        this.brcRepairInventoryReceiveRequest.faultObservedId = this.bRCInventoryReceiveResponse.faultObservedId;
        this.brcRepairInventoryReceiveRequest.batteryVoltageBefoureRepair = this.bRCInventoryReceiveResponse.batteryVoltageBefoureRepair;
        this.brcRepairInventoryReceiveRequest.remarks = this.bRCInventoryReceiveResponse.remarks;
        this.brcRepairInventoryReceiveRequest.brcRepariStatus = this.bRCInventoryReceiveResponse.brcRepairStatusId;

        this.brcRepairInventoryReceiveRequest.receivingName = this.bRCInventoryReceiveResponse.receivingName;
        this.spinner.show();
        this.mangaeInventoryService.updateRequest(this.brcRepairInventoryReceiveRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onManageInventoryCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }

  }

  cancelData() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  addNewProduct() {
    this.createProduct = new Product();
    this.isEditMode = false;
    this.createProductModel.show();
  }
  createProductModelhide() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  cancelDataProd() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  onProductCreated($event: Product) {
    this.getProductList();
    this.bRCInventoryReceiveResponse.productPartCode = $event.partCode;
  }

  addNewCase() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.show();
  }
  createCaseModelhide() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.hide();
  }
  cancelDatacase() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.hide();
  }
  onCaseCreated($event: CaseResponse) {
    this.getCaseList();
    this.bRCInventoryReceiveResponse.caseId = $event.caseId;
  }

  addNewLegalEntity() {
    this.req = new LegalEntityResponse();
    this.createLegalENtityModel.show();
  }
  legalEntityModalHide() {
    this.req = new LegalEntityResponse();
    this.createLegalENtityModel.hide();
  }
  cancelDataLegalEntity() {
    this.req = new LegalEntityResponse();
    this.createLegalENtityModel.hide();
  }
  onEntityCreated($event: LegalEntityResponse) {
    this.getLegalEntity();
    this.bRCInventoryReceiveResponse.legalEntityId = $event.legalentityId;
  }

}
