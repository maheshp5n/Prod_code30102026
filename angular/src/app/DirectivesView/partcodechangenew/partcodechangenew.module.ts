import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PartcodechangenewComponent } from './partcodechangenew.component';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxSpinnerModule } from 'ngx-spinner';



@NgModule({
  declarations: [PartcodechangenewComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgSelectModule,
    NgxSpinnerModule
  ],
  exports :[PartcodechangenewComponent]
})
export class PartcodechangenewModule { }
