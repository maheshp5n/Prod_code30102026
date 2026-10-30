import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PartchangemapprovalComponent } from './partchangemapproval.component';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [PartchangemapprovalComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgSelectModule,
    NgxSpinnerModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[PartchangemapprovalComponent],
  providers:[NgxSpinnerService]
})
export class PartchangemapprovalModule { }
