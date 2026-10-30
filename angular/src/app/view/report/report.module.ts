import { GlobalSearchPipeModule } from './../../pipe/global-search-pipe.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportComponent } from './report.component';
import { ReportRouting } from './report.routing';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ReportService } from '../../services/report.service';
import { ModalModule, BsDropdownModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { DatePipe } from '@angular/common';
import { LazyreportexicuationComponent } from './lazyreportexicuation/lazyreportexicuation.component';
import { LazyreportpersentationComponent } from './lazyreportpersentation/lazyreportpersentation.component';

@NgModule({
  imports: [
    CommonModule,
    ReportRouting,
    ModalModule, FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    NgSelectModule,
    GlobalSearchPipeModule
  ],
  declarations: [ReportComponent, SearchfiltPipe, LazyreportexicuationComponent, LazyreportpersentationComponent],
  exports: [SearchfiltPipe],
  providers: [ReportService, NgxSpinnerService, DatePipe]
})
export class ReportModule { }
