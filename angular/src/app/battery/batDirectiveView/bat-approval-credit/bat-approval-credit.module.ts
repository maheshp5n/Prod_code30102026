import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalCreditComponent } from './bat-approval-credit.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [BatApprovalCreditComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatApprovalCreditComponent]
})
export class BatApprovalCreditModule { }
