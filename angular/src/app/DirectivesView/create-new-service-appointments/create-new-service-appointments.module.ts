import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateNewServiceAppointmentsComponent } from './create-new-service-appointments.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { caseService } from '../../services/caseService.service';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { CreatecaseModule } from '../createcase/createcase.module';
import { CreateNewServiceApppointmentRouting } from './create-new-service-appointments.routing';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { CreateassetModule } from '../createasset/createasset.module';
import { AssetsService } from '../../services/assets.services';
import { NgSelectModule } from '@ng-select/ng-select';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    //DatetimePopupModule.forRoot(),
    TimepickerModule.forRoot(),
    PopoverModule.forRoot(),
    CreateNewServiceApppointmentRouting,
    NgxSpinnerModule,
    CreateaccountModule,
    CreatecaseModule,
    CreateNewProductModule,
    CreateassetModule,
    NgSelectModule
  ],
  declarations: [CreateNewServiceAppointmentsComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateNewServiceAppointmentsComponent],
  providers:[NgxSpinnerService,Loginservices,ServiceAppointmentService,caseService,AssetsService]

})
export class CreateNewServiceAppointmentsModule { }
