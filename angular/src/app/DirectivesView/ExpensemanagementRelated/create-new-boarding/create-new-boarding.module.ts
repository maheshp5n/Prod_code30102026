import { PopoverModule } from 'ngx-bootstrap/popover';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { CirclesService } from '../../../services/circles.services';
import { ContactcreateModule } from '../../contactcreate/contactcreate.module';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { CreateNewBoardingComponent } from './create-new-boarding.component';
import { Loginservices } from '../../../services/login.services';
import { ContactsService } from '../../../services/contacts.services';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BLDAdetailMoule } from '../../boding-lodging-da-detail/boding-lodging-da-detail.module';

@NgModule({
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
  declarations: [CreateNewBoardingComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewBoardingComponent],
  providers: [DatePipe, ExpenseManagementService, NgxSpinnerService, ligalEntityService, ContactsService, CirclesService, Loginservices,
    //  AmazingTimePickerService
  ]
})
export class CreateNewBoardingModule { }
