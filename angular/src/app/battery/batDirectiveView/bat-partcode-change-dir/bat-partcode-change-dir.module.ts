import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatPartcodeChangeDirComponent } from './bat-partcode-change-dir.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BatPartcodeChangeDirComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    NgSelectModule,
    NgxSpinnerModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers:[NgxSpinnerService],
  exports:[BatPartcodeChangeDirComponent]
})
export class BatPartcodeChangeDirModule { }
