import { CommonModule } from "@angular/common";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { ligalEntityService } from "../../services/ligalentity.services";
import { Loginservices } from "../../services/login.services";
import { FieldValidation } from "../../services/validation.services";
import { RouterModule } from '@angular/router';
import { ProutLinItmComponent } from './prout-lin-itm.component';
import { BillOfmaterialService } from '../../services/billOfMaterial.service';
import { AssetsService } from '../../services/assets.services';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgSelectModule
  ],
  declarations: [ProutLinItmComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ProutLinItmComponent],
  providers: [NgxSpinnerService, Loginservices, FieldValidation,BillOfmaterialService,AssetsService],
})
export class PrductItemModule{

}