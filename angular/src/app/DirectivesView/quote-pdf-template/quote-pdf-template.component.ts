import { Component, OnInit, ViewChild, EventEmitter, Output, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { StateProvinceDetails, QuotePdfHeaderAndFooterResponse, QuotePdfHeaderAndFooterRequest, AccountsForList, SiteDetailsResponse, CountryDetails, BusinessDomain, StateWithQuotePdf } from '../../services/model';
import { FieldValidation } from '../../services/validation.services';
import { QuotePDFTemplateServices } from '../../services/quotepdftemplate.services';
import { FormGroup, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { siteService } from '../../services/siteService.services';

@Component({
  selector: 'app-quote-pdf-template',
  templateUrl: './quote-pdf-template.component.html',
  styleUrls: ['./quote-pdf-template.component.scss']
})
export class QuotePdfTemplateComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('fileUploadPopUpModel') fileUploadPopUpModel: ModalDirective;

  @Output()
  onQuotePDFTemplateCreated: EventEmitter<QuotePdfHeaderAndFooterResponse> = new EventEmitter<QuotePdfHeaderAndFooterResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  stateList:Array<any>
  @Input()
  set stateLists(stateList: Array<StateWithQuotePdf>) {
    console.log(this.quotePdfHeaderAndFooterResponse.stateList);
    // this.quotePdfHeaderAndFooterResponse.stateList = stateList;

  }

  get stateLists(): Array<StateWithQuotePdf> {
    console.log(this.quotePdfHeaderAndFooterResponse.stateList)
    return this.quotePdfHeaderAndFooterResponse.stateList;
  }
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editQuotePDFTemplateData(quotePdfHeaderAndFooterResponse: QuotePdfHeaderAndFooterResponse) {
    this.quotePdfHeaderAndFooterResponse = quotePdfHeaderAndFooterResponse;
    this.quotePdfHeaderAndFooterResponse.stateList=quotePdfHeaderAndFooterResponse.stateList;



    this.countryService.getAllCountry().subscribe(data => this._countryDetails = data)
 
    this.countryService.getAllCountry().subscribe(data => this._countryDetails = data)
    this.countryService.getStateByCountryCode(this.countryId).subscribe(data => {
      this._billingState = data;
 
    }, err => {

    });

      
    
  }
  get editQuotePDFTemplateData(): QuotePdfHeaderAndFooterResponse {
    return this.quotePdfHeaderAndFooterResponse;
    
    
  }

  AccountList: Array<AccountsForList> = [];
  _isEditMode: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';

  countryId: string = 'In'
  // stateList: Array<StateProvinceDetails> = [];
  buisnessDomain:Array<BusinessDomain>
  quotePdfHeaderAndFooterResponse: QuotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
  quotePdfHeaderAndFooterRequest: QuotePdfHeaderAndFooterRequest = new QuotePdfHeaderAndFooterRequest();
  tempId: number;
  tempManual: string;

  siteres: SiteDetailsResponse = new SiteDetailsResponse();
  constructor(private spinner: NgxSpinnerService,private siteservice:siteService, private countryService: CountryDetailsServices,
    private _quotePDFTemplateServices: QuotePDFTemplateServices, private formBuilder: FormBuilder,
    private _http: HttpClient) { }

  ngOnInit() {
    this.getBuisnessDomain();
    this.getstateByCountry(this.countryId);
    this.getAllCountryDetails();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    
  }
  getstateByCountry(countryId: string) {
    this.countryService.getStateByCountryCode(countryId).subscribe(data => {
      this.stateList = data;
    }, err => {

    });
  }
  uploadForm: FormGroup;
  uploadFilesLength: number;
  fileList: string[] = [];
  formData: FormData = new FormData()
  fileName: string;
  imageUrl: string;

  uploadForm1: FormGroup;
  uploadFilesLength1: number;
  fileList1: string[] = [];
  formData1: FormData = new FormData()
  fileName1: string;
  imageUrl1: string;

  onFileSelect(event) {
    this.fileList = [];
    if (event.target.files.length > 0) {
      this.uploadFilesLength = event.target.files.length;
      this.fileList[0] = event.target.files[0];
    }
  }
  onSubmit() {
    this.formData.append('file', this.fileList[0]);
    this.spinner.show();
    this._http.post<any>('api/uploadFile/image', this.formData).subscribe(data => {
      this.quotePdfHeaderAndFooterResponse.logo = data.fileDownloadUri;
      this.message = 'Files have been uploaded successfully';
      this.popupNgClass = 'alert alert-success center center-block';
      if (data != null) {
        this.spinner.hide();
      }
      this.fileUploadPopUpModel.show();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    });
    this.formData = new FormData();
  }

  onFileSelectSignature(event) {
    this.fileList1 = [];
    if (event.target.files.length > 0) {
      this.uploadFilesLength1 = event.target.files.length;
      this.fileList1[0] = event.target.files[0];
    }
  }
  onSubmitSignature() {
    this.formData1.append('file', this.fileList1[0]);
    this.spinner.show();
    this._http.post<any>('api/uploadFile/image', this.formData1).subscribe(data => {
      this.quotePdfHeaderAndFooterResponse.signature = data.fileDownloadUri;
      this.message = 'Files have been uploaded successfully';
      this.popupNgClass = 'alert alert-success center center-block';
      if (data != null) {
        this.spinner.hide();
      }
      this.fileUploadPopUpModel.show();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    });
    this.formData = new FormData();
  }

  postCreate() {
    if (this.quotePdfHeaderAndFooterResponse.templateName == null || this.quotePdfHeaderAndFooterResponse.templateName == '') {
      this.message = 'Please fill the template';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.templateName != undefined && (this.quotePdfHeaderAndFooterResponse.templateName).length > 200) {
      this.message = 'Please fill the template under 200 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
   else if (this.quotePdfHeaderAndFooterResponse.logo == null || this.quotePdfHeaderAndFooterResponse.logo == '') {
      this.message = 'Please select the logo';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.signature == null || this.quotePdfHeaderAndFooterResponse.signature == '') {
      this.message = 'Please select the signature';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.companyName == null || this.quotePdfHeaderAndFooterResponse.companyName == '') {
      this.message = 'Please select the company';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.companyName != undefined && (this.quotePdfHeaderAndFooterResponse.companyName).length > 200) {
      this.message = 'Please fill the company under 200 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.addressLine1 == null || this.quotePdfHeaderAndFooterResponse.addressLine1 == '') {
      this.message = 'Please fill the address line1 ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.addressLine1 != undefined && (this.quotePdfHeaderAndFooterResponse.addressLine1).length > 200) {
      this.message = 'Please fill the address line1 under 200 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    // else if (this.quotePdfHeaderAndFooterResponse.addressLine2 == null || this.quotePdfHeaderAndFooterResponse.addressLine2 == '') {
    //   this.message = 'Please fill the address line2 ';
    //   this.popupNgClass = 'alert alert-danger center center-block';
    //   this.popupSuccessModel.show();
    // }
    else if (this.quotePdfHeaderAndFooterResponse.addressLine2 != undefined && (this.quotePdfHeaderAndFooterResponse.addressLine2).length > 200) {
      this.message = 'Please fill the address line2 under 200 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.city == null || this.quotePdfHeaderAndFooterResponse.city == '') {
      this.message = 'Please fill the city ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.city != undefined && (this.quotePdfHeaderAndFooterResponse.city).length > 50) {
      this.message = 'Please fill the city under 50 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.pincode == null || FieldValidation.isValidPostalCode(this.quotePdfHeaderAndFooterResponse.pincode) == false) {
      this.message = 'Please fill the 6 digit postal code';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.stateName == null || this.quotePdfHeaderAndFooterResponse.stateName == '0') {
      this.message = 'Please fill the state ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.cin == null || this.quotePdfHeaderAndFooterResponse.cin == '') {
      this.message = 'Please fill the cin ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.cin != undefined && (this.quotePdfHeaderAndFooterResponse.cin).length > 50) {
      this.message = 'Please fill the cin under 50 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.documentNumber == null || this.quotePdfHeaderAndFooterResponse.documentNumber == '') {
      this.message = 'Please fill the document number ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.documentNumber != undefined && (this.quotePdfHeaderAndFooterResponse.documentNumber).length > 50) {
      this.message = 'Please fill the document number under 50 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.telephone == null || (FieldValidation.isValidTelephoneNumber(this.quotePdfHeaderAndFooterResponse.telephone) == false)) {
      this.message = 'Please fill the 10 digit telephone no ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.gstNumber == null || this.quotePdfHeaderAndFooterResponse.gstNumber == '') {
      this.message = 'Please fill the gst number';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.gstNumber != undefined && (this.quotePdfHeaderAndFooterResponse.gstNumber).length > 50) {
      this.message = 'Please fill the gst number under 50 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.website == null || this.quotePdfHeaderAndFooterResponse.website == '') {
      this.message = 'Please fill the website';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.website != undefined && (this.quotePdfHeaderAndFooterResponse.website).length > 50) {
      this.message = 'Please fill the website under 50 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }

    else if (this.quotePdfHeaderAndFooterResponse.footerline1 == null || this.quotePdfHeaderAndFooterResponse.footerline1 == '') {
      this.message = 'Please fill the footer line-1';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.footerline1 != undefined && (this.quotePdfHeaderAndFooterResponse.footerline1).length > 500) {
      this.message = 'Please fill the footer line-1 under 500 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.footerline2 != undefined && (this.quotePdfHeaderAndFooterResponse.footerline2).length > 500) {
      this.message = 'Please fill the footer line-2 under 500 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.footerline3 != undefined && (this.quotePdfHeaderAndFooterResponse.footerline3).length > 500) {
      this.message = 'Please fill the footer line-3 under 500 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.footerline4 != undefined && (this.quotePdfHeaderAndFooterResponse.footerline4).length > 500) {
      this.message = 'Please fill the footer line-4 under 500 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.footerline5 != undefined && (this.quotePdfHeaderAndFooterResponse.footerline5).length > 500) {
      this.message = 'Please fill the footer line-5 under 500 characters ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.businessDomainId == 0) {
      this.message = 'Please Select Buissness Domain  ID ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.conuntryId == 0) {
      this.message = 'Please Select Contry ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else if (this.quotePdfHeaderAndFooterResponse.stateList.length == 0 || this.quotePdfHeaderAndFooterResponse.stateList.length == null) {
      this.message = 'Please Select State ';
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    }
    else {

      console.log( this.quotePdfHeaderAndFooterResponse.stateList);
      
     
      
      this.quotePdfHeaderAndFooterRequest.templateName = this.quotePdfHeaderAndFooterResponse.templateName;
      this.quotePdfHeaderAndFooterRequest.logo = this.quotePdfHeaderAndFooterResponse.logo;
      this.quotePdfHeaderAndFooterRequest.signature = this.quotePdfHeaderAndFooterResponse.signature;
      this.quotePdfHeaderAndFooterRequest.companyName = this.quotePdfHeaderAndFooterResponse.companyName;
      this.quotePdfHeaderAndFooterRequest.addressLine1 = this.quotePdfHeaderAndFooterResponse.addressLine1;
      this.quotePdfHeaderAndFooterRequest.addressLine2 = this.quotePdfHeaderAndFooterResponse.addressLine2;
      this.quotePdfHeaderAndFooterRequest.city = this.quotePdfHeaderAndFooterResponse.city;
      this.quotePdfHeaderAndFooterRequest.pincode = this.quotePdfHeaderAndFooterResponse.pincode;
      this.quotePdfHeaderAndFooterRequest.stateName = this.quotePdfHeaderAndFooterResponse.stateName;
      this.quotePdfHeaderAndFooterRequest.cin = this.quotePdfHeaderAndFooterResponse.cin;
      this.quotePdfHeaderAndFooterRequest.documentNumber = this.quotePdfHeaderAndFooterResponse.documentNumber;
      this.quotePdfHeaderAndFooterRequest.telephone = this.quotePdfHeaderAndFooterResponse.telephone;
      this.quotePdfHeaderAndFooterRequest.gstNumber = this.quotePdfHeaderAndFooterResponse.gstNumber;
      this.quotePdfHeaderAndFooterRequest.website = this.quotePdfHeaderAndFooterResponse.website;
      this.quotePdfHeaderAndFooterRequest.footerline1 = this.quotePdfHeaderAndFooterResponse.footerline1;
      this.quotePdfHeaderAndFooterRequest.footerline2 = this.quotePdfHeaderAndFooterResponse.footerline2;
      this.quotePdfHeaderAndFooterRequest.footerline3 = this.quotePdfHeaderAndFooterResponse.footerline3;
      this.quotePdfHeaderAndFooterRequest.footerline4 = this.quotePdfHeaderAndFooterResponse.footerline4;
      this.quotePdfHeaderAndFooterRequest.footerline5 = this.quotePdfHeaderAndFooterResponse.footerline5;
      this.quotePdfHeaderAndFooterRequest.active = this.quotePdfHeaderAndFooterResponse.active;
      this.quotePdfHeaderAndFooterRequest.businessDomainId=this.quotePdfHeaderAndFooterResponse.businessDomainId;
      this.quotePdfHeaderAndFooterRequest.conuntryId=this.quotePdfHeaderAndFooterResponse.conuntryId;
      
      console.log(this.quotePdfHeaderAndFooterRequest.stateList);
      
      // this.quotePdfHeaderAndFooterRequest.stateList=this.quotePdfHeaderAndFooterResponse.stateList[0].state.stateId;
       
      if (!this._isEditMode) {
        this.spinner.show();
        this._quotePDFTemplateServices.saveTemplate(this.quotePdfHeaderAndFooterRequest).subscribe(data => {
          this.tempId = data.id;
          this.tempManual = data.templateName;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onQuotePDFTemplateCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupNgClass = 'alert alert-danger center center-block';
          this.popupSuccessModel.show();
        });
      }
      else {
        this.quotePdfHeaderAndFooterRequest.id = this.quotePdfHeaderAndFooterResponse.id;
        this.quotePdfHeaderAndFooterRequest.templateName = this.quotePdfHeaderAndFooterResponse.templateName;             
        this.quotePdfHeaderAndFooterRequest.logo = this.quotePdfHeaderAndFooterResponse.logo;
        this.quotePdfHeaderAndFooterRequest.signature = this.quotePdfHeaderAndFooterResponse.signature;
        this.quotePdfHeaderAndFooterRequest.companyName = this.quotePdfHeaderAndFooterResponse.companyName;        
        this.quotePdfHeaderAndFooterRequest.addressLine1 = this.quotePdfHeaderAndFooterResponse.addressLine1;
        this.quotePdfHeaderAndFooterRequest.addressLine2 = this.quotePdfHeaderAndFooterResponse.addressLine2;
        this.quotePdfHeaderAndFooterRequest.city = this.quotePdfHeaderAndFooterResponse.city;
        this.quotePdfHeaderAndFooterRequest.pincode = this.quotePdfHeaderAndFooterResponse.pincode;
        this.quotePdfHeaderAndFooterRequest.stateName = this.quotePdfHeaderAndFooterResponse.stateName;
        this.quotePdfHeaderAndFooterRequest.cin = this.quotePdfHeaderAndFooterResponse.cin;
        this.quotePdfHeaderAndFooterRequest.documentNumber = this.quotePdfHeaderAndFooterResponse.documentNumber;
        this.quotePdfHeaderAndFooterRequest.telephone = this.quotePdfHeaderAndFooterResponse.telephone;
        this.quotePdfHeaderAndFooterRequest.gstNumber = this.quotePdfHeaderAndFooterResponse.gstNumber;
        this.quotePdfHeaderAndFooterRequest.website = this.quotePdfHeaderAndFooterResponse.website;
        this.quotePdfHeaderAndFooterRequest.footerline1 = this.quotePdfHeaderAndFooterResponse.footerline1;
        this.quotePdfHeaderAndFooterRequest.footerline2 = this.quotePdfHeaderAndFooterResponse.footerline2;
        this.quotePdfHeaderAndFooterRequest.footerline3 = this.quotePdfHeaderAndFooterResponse.footerline3;
        this.quotePdfHeaderAndFooterRequest.footerline4 = this.quotePdfHeaderAndFooterResponse.footerline4;
        this.quotePdfHeaderAndFooterRequest.footerline5 = this.quotePdfHeaderAndFooterResponse.footerline5;
        this.quotePdfHeaderAndFooterRequest.active = this.quotePdfHeaderAndFooterResponse.active;
        this.quotePdfHeaderAndFooterRequest.businessDomainId=this.quotePdfHeaderAndFooterResponse.businessDomainId;
        this.quotePdfHeaderAndFooterRequest.conuntryId=this.quotePdfHeaderAndFooterResponse.conuntryId;
         this.quotePdfHeaderAndFooterRequest.stateList=this.quotePdfHeaderAndFooterResponse.stateList
        // this.quotePdfHeaderAndFooterResponse.stateList.forEach((element)=>{
        //   this.quotePdfHeaderAndFooterRequest.stateList=element.state.stateId;
        // })
        this.spinner.show();
        this._quotePDFTemplateServices.updateTemplate(this.quotePdfHeaderAndFooterRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onQuotePDFTemplateCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupNgClass = 'alert alert-danger center center-block';
          this.popupSuccessModel.show();
        }
        );
      }
    }
  }

  cancelData() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  fileUploadPopUpClose(){
    this.fileUploadPopUpModel.hide();    
  }
  
  _billingState: Array<StateProvinceDetails> = [];
  _countryDetails:Array<CountryDetails>=[];
  getAllCountryDetails() {
    this.countryService.getAllCountry().subscribe(data => this._countryDetails = data)
  }
  billingState(billingCountryId: any) {
    if (billingCountryId != null && billingCountryId != undefined) {
      this.spinner.show();
      this.countryService.getStateByCountryCode(billingCountryId).subscribe(data => {
        this._billingState = data;
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {

      });
    }
  }
  // get buisnessDomain
  getBuisnessDomain(){
    this._quotePDFTemplateServices.getBuisnessDomains().subscribe(data=>{
      this.buisnessDomain=data;
    })
  }
}
