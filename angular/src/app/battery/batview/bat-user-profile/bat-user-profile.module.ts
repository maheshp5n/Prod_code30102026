import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatUserProfileRouting } from './bat-user-profile.routing';
import { BatUserProfileComponent } from './bat-user-profile.component';
import { FormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatUserProfileDirectiveModule } from '../../batDirectiveView/bat-user-profile-directive/bat-user-profile-directive.module';



@NgModule({
  declarations: [BatUserProfileComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatUserProfileRouting,
    ModalModule,
    NgxSpinnerModule,
    AlertModule,
    BatUserProfileDirectiveModule
  ],
  providers:[NgxSpinnerService]
})
export class BatUserProfileModule { }
