import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule, DatepickerModule } from 'ngx-bootstrap/datepicker';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { AssetsService } from '../../../services/assets.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateNewServiceApppointmentRouting } from './bt-create-new-service-appoitnment.routing';
import { CreateassetModule } from '../../../DirectivesView/createasset/createasset.module';
import { BtCreateNewServiceAppointmentComponent } from './bt-create-new-service-appointment.component';
import { CreateaccountModule } from '../../../DirectivesView/createaccount/createaccount.module';
import { CreatecaseModule } from '../../../DirectivesView/createcase/createcase.module';
import { CreateNewProductModule } from '../../../DirectivesView/create-new-product/create-new-product.module';
import { Loginservices } from '../../../services/login.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { caseService } from '../../../services/caseService.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    //DatetimePopupModule.forRoot(),
    TimepickerModule.forRoot(),
    PopoverModule.forRoot(),
    BtCreateNewServiceApppointmentRouting,
    NgxSpinnerModule,
    CreateaccountModule,
    CreatecaseModule,
    CreateNewProductModule,
    CreateassetModule,
    NgSelectModule
  ],
  declarations: [BtCreateNewServiceAppointmentComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BtCreateNewServiceAppointmentComponent],
  providers:[NgxSpinnerService,Loginservices,ServiceAppointmentService,caseService,AssetsService]

})
export class BtCreateNewServiceAppointmentsModule { }
