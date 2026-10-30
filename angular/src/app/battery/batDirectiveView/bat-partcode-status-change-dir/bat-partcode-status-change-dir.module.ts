import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatPartcodeStatusChangeDirComponent } from './bat-partcode-status-change-dir.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [BatPartcodeStatusChangeDirComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    NgSelectModule,
    RouterModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatPartcodeStatusChangeDirComponent],
  providers:[NgxSpinnerService]
})
export class BatPartcodeStatusChangeDirModule { }
