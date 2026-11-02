import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BulkUploadRouting } from './bulkupload.routing';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BulkuploadComponent } from './bulkupload.component';
import { Loginservices } from '../../services/login.services';
import { caseService } from '../../services/caseService.service';

@NgModule({
    imports: [
      CommonModule,
      BulkUploadRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      OrderModule
    ],
    declarations: [BulkuploadComponent],
//    exports: [SearchfiltPipe],
    providers: [ NgxSpinnerService,Loginservices,caseService]
  })
export class BulkUploadModule{

}