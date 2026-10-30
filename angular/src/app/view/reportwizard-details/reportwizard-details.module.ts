

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { ReportwizardDetailsRouting } from './reportwizard-details.routing';
import { ReportwizardDetailsComponent } from './reportwizard-details.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { createReportModule } from '../../DirectivesView/createreport/createreport.module';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReportwizardDetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),NgSelectModule,createReportModule
    
],
  declarations: [ReportwizardDetailsComponent],
  providers: [NgxSpinnerService]
})
export class ReportwizardModule{

}