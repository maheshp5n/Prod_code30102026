
import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { FormsModule } from "@angular/forms";
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { createMslRqstModule } from '../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { mslRequestService } from '../../services/mslRequest.services';
import { createlocationModule } from '../../DirectivesView/create-location/create-location.module';
import { LocationService } from '../../services/location.service';
import { LocationdetailsComponent } from './locationdetails.component';
import { locationdetailsRouting } from './locationdetails.routing';
import { Loginservices } from '../../services/login.services';
import { ProductrequestdirectiveModule } from '../../DirectivesView/productrequestdirective/productrequestdirective.module';
import { ProductreturnorderdirectiveModule } from '../../DirectivesView/productreturnorderdirective/productreturnorderdirective.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';


@NgModule({
    imports: [
      CommonModule,
      locationdetailsRouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      createlocationModule,
      createMslRqstModule,
      ProductrequestdirectiveModule,
      AlertModule,
      ProductreturnorderdirectiveModule,
      ActionhistoryModule,BsDatepickerModule.forRoot()
    ],
    declarations: [LocationdetailsComponent],
    providers:[mslRequestService,NgxSpinnerService,LocationService,Loginservices,DatePipe]
  })
export class locationDetailsModule{

}