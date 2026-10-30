import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { QueteTemplateTandCDetailsRouting } from './quete-template-tand-cdetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { QueteTemplateTandCDetailsComponent } from './quete-template-tand-cdetails.component';
import { Loginservices } from '../../services/login.services';
import { QuotePDFTemplateServices } from '../../services/quotepdftemplate.services';
import { QuotePdfTemplateModule } from '../../DirectivesView/quote-pdf-template/quote-pdf-template.module';
import { workOrderForQuetService } from '../../services/workorderforquote.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';


@NgModule({  
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    QueteTemplateTandCDetailsRouting,
    NgxSpinnerModule,
    PdfViewerModule,
    QuotePdfTemplateModule,
    PdfViewerModule,
    ActionhistoryModule
  ],
  declarations: [QueteTemplateTandCDetailsComponent],
  providers: [ NgxSpinnerService,Loginservices,QuotePDFTemplateServices,workOrderForQuetService]
})
export class QueteTemplateTandCDetailsModule { }
