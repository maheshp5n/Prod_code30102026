import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule, BsDropdownModule, PopoverModule, BsDatepickerModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { Loginservices } from '../../services/login.services';
import { QueteTemplateTCRouting } from './quete-template-tand-c.routing';
import { QueteTemplateTandCComponent } from './quete-template-tand-c.component';
import { QuotePDFTemplateServices } from '../../services/quotepdftemplate.services';
import { QuotePdfTemplateModule } from '../../DirectivesView/quote-pdf-template/quote-pdf-template.module';
import { SearchfiltPipe } from '../quete-template-tand-c/searchfilt.pipe';

@NgModule({
  imports: [
    CommonModule,
    QueteTemplateTCRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule,
    BsDatepickerModule,
    NgxSpinnerModule,
    OrderModule,
    QuotePdfTemplateModule
  ],
  declarations: [QueteTemplateTandCComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [NgxSpinnerService,Loginservices,QuotePDFTemplateServices]  
})
export class QueteTemplateTCModule{

}