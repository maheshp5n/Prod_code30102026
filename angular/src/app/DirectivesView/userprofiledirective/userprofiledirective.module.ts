import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlertModule, ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { UserprofiledirectiveComponent } from './userprofiledirective.component';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [UserprofiledirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    AlertModule,
    ModalModule,
    NgxSpinnerModule,
    NgSelectModule,
    GlobalSearchPipeModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[UserprofiledirectiveComponent],
  providers:[NgxSpinnerService]
})
export class UserprofiledirectiveModule { }
