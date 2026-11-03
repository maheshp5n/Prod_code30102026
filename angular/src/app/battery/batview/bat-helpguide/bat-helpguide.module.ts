import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatHelpguideComponent } from './bat-helpguide.component';
import { BatHelpguideRouting } from './bat-helpguide.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { cityService } from '../../../services/cityService.service';
import { FieldValidation } from '../../../services/validation.services';
import { Loginservices } from '../../../services/login.services';
import { PagefilterService } from '../../../services/pagefilterservice';



@NgModule({
  declarations: [BatHelpguideComponent],
  imports: [
    BatHelpguideRouting,
    CommonModule,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule,
  ],
  providers: [cityService, NgxSpinnerService, FieldValidation,Loginservices,PagefilterService]
  
})
export class BatHelpguideModule { }
