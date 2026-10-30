
import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { caseService } from '../../../services/caseService.service';
import { BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtChangeCaseTypeCommerComponent } from './bt-change-case-type-commer.component';
@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),
      RouterModule,NgSelectModule
  
    ],
    declarations: [BtChangeCaseTypeCommerComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [BtChangeCaseTypeCommerComponent],
    providers: [NgxSpinnerService, caseService, Loginservices,DatePipe]
  })
export class BtChangeCaseTypeModule{

}