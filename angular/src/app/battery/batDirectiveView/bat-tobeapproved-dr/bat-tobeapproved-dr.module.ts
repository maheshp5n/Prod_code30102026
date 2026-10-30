import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatTobeapprovedDrComponent } from './bat-tobeapproved-dr.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';



@NgModule({
  declarations: [BatTobeapprovedDrComponent],
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,NgxSpinnerModule,
    BsDropdownModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatTobeapprovedDrComponent],
  providers:[NgxSpinnerService]
})
export class BatTobeapprovedDrModule { }
