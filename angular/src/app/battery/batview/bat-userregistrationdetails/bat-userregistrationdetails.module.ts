import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDropdownModule, ModalModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { BatUserregistrationdetailsComponent } from './bat-userregistrationdetails.component';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BatUserRegistraionDetailsRouting } from './bat-userregistrationdetails.routing';
import { UserService } from '../../../services/user.services';

@NgModule({
    imports: [
      CommonModule,
      BatUserRegistraionDetailsRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      HttpClientModule,
      BsDropdownModule,
      NgxSpinnerModule,
      PopoverModule.forRoot(),
      BtActionhistoryModule
  
    ],
    declarations: [BatUserregistrationdetailsComponent],
    providers: [Loginservices, NgxSpinnerService,UserService],
  
  })
export class BatUserRegistrationDetailsModule{


}