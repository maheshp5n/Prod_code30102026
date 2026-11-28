import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { deliverychallanRouting } from './deliverychallan.routing';
import { CreateNewdeliverychallanModule } from '../../DirectivesView/CreateNewDeliveryChallan/create-new-deliverychallan.module';
import { deliverychallanService } from '../../services/delivery-challan.service';
import { deliverychallanComponent } from './deliverychallan.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    deliverychallanRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    CreateNewdeliverychallanModule
  ],
  declarations: [deliverychallanComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ FieldValidation, NgxSpinnerService, deliverychallanService]
})

export class deliverychallanModule { }
