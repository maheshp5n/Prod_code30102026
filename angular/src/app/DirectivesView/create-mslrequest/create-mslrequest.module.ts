
import { CommonModule } from "@angular/common";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { ligalEntityService } from "../../services/ligalentity.services";
import { Loginservices } from "../../services/login.services";
import { FieldValidation } from "../../services/validation.services";
import { CreateMslrequestComponent } from './create-mslrequest.component';
import { AssetsService } from '../../services/assets.services';
import { mslRequestService } from '../../services/mslRequest.services';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
      CommonModule,
      NgSelectModule,
      FormsModule,
      TypeaheadModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule
    ],
    declarations: [CreateMslrequestComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CreateMslrequestComponent],
    providers: [ligalEntityService, TypeaheadConfig,Loginservices,FieldValidation,AssetsService,mslRequestService,ServiceResourceService],
  })
export class createMslRqstModule{

}