
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { caseService } from '../../services/caseService.service';
import { BordingDARouting } from './bording-da.routing';
import { BordingDaComponent } from './bording-da.component';
import { SearchfiltPipe } from './searchfilt.pipe';
import { createboardingModule } from '../../DirectivesView/createboarding/createboarding.module';
import { PagefilterService } from '../../services/pagefilterservice';



@NgModule({
  imports: [
    CommonModule,
    BordingDARouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    createboardingModule,
    OrderModule
  ],
  declarations: [BordingDaComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [caseService, NgxSpinnerService, FieldValidation,PagefilterService]
})
export class BordingDAModule{

}