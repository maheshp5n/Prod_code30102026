import { CreateAnnexureModule } from './../../DirectivesView/create-annexure/create-annexure.module';
import { AnnexureLineItemdetailsComponent } from './annexureLineItemdetails.component';
import { CreateAnnexureLineItemModule } from './../../DirectivesView/create-annexureLineItem/create-annexureLineItem.module';
import { AnnexureLineItemdetailsRouting } from './annexureLineItemdetails.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AnnexureLineItemdetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateAnnexureLineItemModule,
    BsDropdownModule,
    CreateAnnexureModule
  ],
  declarations: [AnnexureLineItemdetailsComponent],
  providers: [NgxSpinnerService]
})
export class AnnexureLineItemdetailsModule { }
