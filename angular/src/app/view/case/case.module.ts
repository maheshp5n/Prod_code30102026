import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { CaseComponent } from './case.component';
import { caseRouting } from './case.routing';
import { caseService } from '../../services/caseService.service';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { ActionHistoryComponent } from '../../DirectivesView/action-history/action-history.component';
import { Pm_asset_site_service } from "../../services/pm_asset_site.service";



@NgModule({
  imports: [
    CommonModule,
    caseRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreatecaseModule,
    OrderModule,ReactiveFormsModule,BsDatepickerModule.forRoot()
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [CaseComponent,SearchfiltPipe],
 exports: [SearchfiltPipe],
  providers: [caseService, NgxSpinnerService, FieldValidation,Pm_asset_site_service]
})
export class caseModule{

}