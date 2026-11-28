import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { QuotePdfHeaderAndFooterResponse, QuoteTermsAndConditions, WorkOrderRequest, StateWithQuotePdf, ActionHistoryObjectAndId } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { QuotePDFTemplateServices } from '../../services/quotepdftemplate.services';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-quete-template-tand-cdetails',
  templateUrl: './quete-template-tand-cdetails.component.html',
  styleUrls: ['./quete-template-tand-cdetails.component.scss']
})
export class QueteTemplateTandCDetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('quotePDFTemplateModel') quotePDFTemplateModel: ModalDirective;
  @ViewChild('submitApprovelModel') submitApprovelModel:ModalDirective
 @ViewChild('workOrderForQuoteModel') workOrderForQuoteModel:ModalDirective;
  public message: string = '';
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: number;
  _cFlag : string;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_QUOTETEMPLATE_HEADING: string = 'Edit Quote Template';  
  
  serialNumber: number;
  isModelShow: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';

  quoteTemplate: QuotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
  quotePdfHeaderAndFooterResponse: QuotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
  quoteTemplateList: Array<QuotePdfHeaderAndFooterResponse> = [];
  
  termsAndConditions: Array<QuoteTermsAndConditions>;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _quotePDFTemplateServices: QuotePDFTemplateServices,private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getTemplate(this.sapId);
      this.actionHistoryObjectAndId.objectId = '' + this.sapId;
      this.actionHistoryObjectAndId.objectName = 'QuotePdfHeaderAndFooter';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }
  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }
  ngOnInit() {
  }
stateList:Array<StateWithQuotePdf>=[];
  getTemplate(id: number) {
    this.spinner.show();
    this._quotePDFTemplateServices.getTemplateById(id).subscribe(data => { 
        this.spinner.hide();  
      this.quoteTemplate = data;  
     
    //  this.quoteTemplate.stateList.forEach((e)=>(
       
    //    console.log(e.state.stateName)
    //  ) );
    //  console.log()
     
      this.termsAndConditions = data.termsAndConditions;   
      //  this.stateList=data.stateList;
      //  console.log(this.stateList); 
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.spinner.hide();
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getTemplate(this.sapId);
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getTemplate(this.sapId);
    }
    return false;
  }
  quoteTemplateModelHide() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.quotePDFTemplateModel.hide();
  }
  createEditModelshow(template: QuotePdfHeaderAndFooterResponse) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_QUOTETEMPLATE_HEADING;
    this._quotePDFTemplateServices.getTemplateById(template.id).subscribe(data => {
     this.spinner.hide();
      this.quotePdfHeaderAndFooterResponse = data;
      this.stateList=data.stateList;
      console.log(this.stateList); 
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
   
    this.quotePDFTemplateModel.show();
  }

  cancelData() {
    this.quotePdfHeaderAndFooterResponse = new QuotePdfHeaderAndFooterResponse();
    this.quotePDFTemplateModel.hide();
  }

  onQuotePDFTemplateCreated($event) {
    let templateEvent = new QuotePdfHeaderAndFooterResponse();
    this.spinner.show();
    this._quotePDFTemplateServices.getTemplateById($event.id).subscribe(data => {
      this.spinner.hide()
      templateEvent = data;
    });
    this.quoteTemplateList.push(templateEvent);
    this.quoteTemplate = $event;
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }
 
}
