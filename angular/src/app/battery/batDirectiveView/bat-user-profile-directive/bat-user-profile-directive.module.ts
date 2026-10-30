import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatUserProfileDirectiveComponent } from './bat-user-profile-directive.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AlertModule, ModalModule } from 'ngx-bootstrap';
import { GlobalSearchPipeModule } from '../../../pipe/global-search-pipe.module';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [BatUserProfileDirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    GlobalSearchPipeModule,
    NgSelectModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatUserProfileDirectiveComponent],
  providers:[NgxSpinnerService]
})
export class BatUserProfileDirectiveModule { }
