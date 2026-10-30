import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchresultdisplayComponent } from './searchresultdisplay.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [SearchresultdisplayComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule
  ],
  exports:[SearchresultdisplayComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers:[NgxSpinnerService]
})
export class SearchresultdisplayModule { }
