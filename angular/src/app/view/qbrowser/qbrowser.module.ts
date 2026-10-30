import { NgxSpinnerService } from 'ngx-spinner';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { QbrowserRouting } from './qbrowser.routing';
import { QbrowserComponent } from './qbrowser.component';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { TabsModule } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportwizardComponent } from './reportwizard/reportwizard.component';
import { PagefiltersetupComponent } from './pagefiltersetup/pagefiltersetup.component';
import { RolegroupmapComponent } from './pagefiltersetup/rolegroupmap/rolegroupmap.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { createReportModule } from '../../DirectivesView/createreport/createreport.module';



@NgModule({
  declarations: [QbrowserComponent, ReportwizardComponent, PagefiltersetupComponent, RolegroupmapComponent],
  imports: [
    CommonModule,
    QbrowserRouting,
    FormsModule,
    GlobalSearchPipeModule,
    TabsModule,
    ModalModule,
    NgxSpinnerModule,NgSelectModule,createReportModule
  ],
  providers: [Loginservices, NgxSpinnerService]
})
export class QbrowserModule { }
