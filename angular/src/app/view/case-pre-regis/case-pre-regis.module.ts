import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { caseService } from '../../services/caseService.service';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { CasePreRegisRouting } from './case-pre-regis.routing';
import { CaseCreatePreRegisComponent } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.component';
import { CasePreRegisComponent } from './case-pre-regis.component';
import { SearchfiltPipe } from './searchfilt.pipe';
import { CreatePreRegisCase } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.module';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { PagefilterService } from '../../services/pagefilterservice';



@NgModule({
  imports: [
    CommonModule,
    CasePreRegisRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreatePreRegisCase,
    OrderModule,ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [CasePreRegisComponent,SearchfiltPipe],
 exports: [SearchfiltPipe],
  providers: [PagefilterService,caseService, NgxSpinnerService, FieldValidation,PreRegCaseService]
})
export class CasePreRegisModule{

}