import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServicereportindetailsRouting } from './servicereportindetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { ServicereportindetailsComponent } from './servicereportindetails.component';
import { AssetsService } from '../../services/assets.services';
import { CreateServiceReportModule } from '../../DirectivesView/create-service-report/create-service-report.module';
import { GlobalserviceService } from '../../globalservice.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ServicereportindetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateServiceReportModule
],
  declarations: [ServicereportindetailsComponent],
  providers: [ AssetsService, NgxSpinnerService,GlobalserviceService]
})
export class ServicereportindetailsModule { }
