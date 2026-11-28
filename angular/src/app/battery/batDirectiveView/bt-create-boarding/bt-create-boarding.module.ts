import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BLDAdetailMoule } from '../../../DirectivesView/boding-lodging-da-detail/boding-lodging-da-detail.module';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { BtCreateBoardingComponent } from './bt-create-boarding.component';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { ContactsService } from '../../../services/contacts.services';
import { CirclesService } from '../../../services/circles.services';
import { Loginservices } from '../../../services/login.services';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';



@NgModule({
  declarations: [BtCreateBoardingComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    ContactcreateModule,
    // TypeaheadModule.forRoot(),
    BsDatepickerModule.forRoot(),
    // TimepickerModule.forRoot(),
    PopoverModule.forRoot(),
    RouterModule,
    NgSelectModule,BLDAdetailMoule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateBoardingComponent],
  providers: [DatePipe, BtexpensemanagementService, NgxSpinnerService, BtlegalentityService, ContactsService, CirclesService, Loginservices,
    //  AmazingTimePickerService
  ]
})
export class BtCreateBoardingModule { }
