import { NgModule, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDetailsRouting } from './userprofile-details.routing';
import { BsDropdownModule, ModalModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { UserprofileDetailsComponent } from './userprofile-details.component';
import { Loginservices } from '../../services/login.services';
import { UserService } from '../../services/user.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
    imports: [
      CommonModule,
      UserDetailsRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      HttpClientModule,
      BsDropdownModule,
      NgxSpinnerModule,
      PopoverModule.forRoot(),
      ActionhistoryModule
  
    ],
    declarations: [UserprofileDetailsComponent],
    providers: [Loginservices, NgxSpinnerService,UserService],
  
  })
export class UserDetailsModule{


}