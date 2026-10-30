import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ModalModule, BsDatepickerModule, TimepickerModule, TooltipModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { BtCreatePodComponent } from './bt-create-pod.component';
import { Loginservices } from '../../../services/login.services';
import { PdfViewerModule } from 'ng2-pdf-viewer';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    TooltipModule,ReactiveFormsModule,PdfViewerModule

  ],
  declarations: [BtCreatePodComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreatePodComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe]
})
export class BtCreatePODModule{

}