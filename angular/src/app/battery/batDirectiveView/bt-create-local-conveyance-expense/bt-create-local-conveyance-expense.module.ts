import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BtCreateLocalConveyanceExpenseComponent } from './bt-create-local-conveyance-expense.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, DatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';
import { RouterModule } from '@angular/router';
import { BLDAdetailMoule } from '../../../DirectivesView/boding-lodging-da-detail/boding-lodging-da-detail.module';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BttourExpenseService } from '../../bat_services/bttour-expense.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { CirclesService } from '../../../services/circles.services';
import { Loginservices } from '../../../services/login.services';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';



@NgModule({
  declarations: [BtCreateLocalConveyanceExpenseComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BtCreatecontactsModule,
    TypeaheadModule.forRoot(),
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    RouterModule,
    TooltipModule,BLDAdetailMoule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateLocalConveyanceExpenseComponent],
  providers: [DatePipe, BtexpensemanagementService, NgxSpinnerService,
    BttourExpenseService,BtlegalentityService, BtcontactsService, CirclesService, Loginservices]
})
export class BtCreateLocalConveyanceExpenseModule { }
