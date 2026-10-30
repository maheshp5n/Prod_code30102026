import { SapdocdisplayComponent } from './sapdocdisplay.component';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { URLSafeModule } from '../../pipe/url-safe-pipe.module';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [SapdocdisplayComponent],
  imports: [
    CommonModule,
    BsDropdownModule,
    ModalModule,
    URLSafeModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [SapdocdisplayComponent]
})
export class SapdocdisplayModule { }
