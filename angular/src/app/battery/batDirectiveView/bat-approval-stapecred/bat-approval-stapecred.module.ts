import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalCreditComponent } from '../bat-approval-credit/bat-approval-credit.component';
import { FormsModule } from '@angular/forms';
import { BatApprovalStapecredComponent } from './bat-approval-stapecred.component';



@NgModule({
  declarations: [BatApprovalStapecredComponent],
  imports: [
    CommonModule,
    FormsModule,
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatApprovalStapecredComponent]
})
export class BatApprovalStapecredModule { }
