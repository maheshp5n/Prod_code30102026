import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatViewAllSchDetailsRouting } from './bat-view-all-sch-details.routing';
import { BatViewAllSchDetailsComponent } from './bat-view-all-sch-details.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';




@NgModule({
  declarations: [BatViewAllSchDetailsComponent],
  imports: [
    CommonModule,
    BatViewAllSchDetailsRouting,
    NgxSpinnerModule
  ],
  
  providers: [NgxSpinnerService]
})

export class BatViewAllSchDetailsModule { 

}
