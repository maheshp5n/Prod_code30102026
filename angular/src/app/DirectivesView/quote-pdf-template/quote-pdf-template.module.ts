import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuotePdfTemplateComponent } from './quote-pdf-template.component';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { Routes, RouterModule } from '@angular/router';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { NgSelectModule } from '@ng-select/ng-select';
import { siteService } from '../../services/siteService.services';

const routes: Routes = [
  {
    path: '', component: QuotePdfTemplateComponent
  }
]
@NgModule({
  imports: [
    CommonModule,NgSelectModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    PdfViewerModule    
  ],
  declarations: [QuotePdfTemplateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [QuotePdfTemplateComponent,RouterModule],
  providers: [NgxSpinnerService,CountryDetailsServices,siteService]
})
export class QuotePdfTemplateModule { }
