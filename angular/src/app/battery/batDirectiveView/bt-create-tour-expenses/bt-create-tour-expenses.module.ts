import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BtCreateTourExpensesComponent } from './bt-create-tour-expenses.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, DatepickerModule, ModalModule, PopoverModule, TimepickerModule, TooltipModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';
import { RouterModule } from '@angular/router';
import { BLDAdetailMoule } from '../../../DirectivesView/boding-lodging-da-detail/boding-lodging-da-detail.module';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { CirclesService } from '../../../services/circles.services';
import { Loginservices } from '../../../services/login.services';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';



@NgModule({
  declarations: [BtCreateTourExpensesComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BtCreatecontactsModule,
    TypeaheadModule.forRoot(),
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    PopoverModule.forRoot(),
    RouterModule,
    TooltipModule,BLDAdetailMoule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateTourExpensesComponent],
  providers: [DatePipe, BtexpensemanagementService, NgxSpinnerService, BtlegalentityService, BtcontactsService, CirclesService, Loginservices]
})
export class BtCreateTourExpensesModule { }
